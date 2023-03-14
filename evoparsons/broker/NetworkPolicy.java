package evoparsons.broker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.rewrite.handler.Rule;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ajax.JSON;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import evoparsons.broker.Log.FileLog;
import evoparsons.repo.Events;
import evoparsons.repo.EventsNoopRepo;
import evoparsons.repo.IRepo;
import evoparsons.repo.Instructor;
import evoparsons.rmishared.Auth;
import evoparsons.rmishared.BrokerClient;
import evoparsons.rmishared.BrokerUIInterface;
import evoparsons.rmishared.Fragment;
import evoparsons.rmishared.ParsonsEvaluation;
import evoparsons.rmishared.ParsonsPuzzle;
import evoparsons.rmishared.Stats;

public interface NetworkPolicy {
    void startInterface(Config.Network networkConfig, Config config, BrokerUIInterface broker);

    public static NetworkPolicy RMI = 
        (networkConfig, config, broker) -> {
            Log log = config.getLog();
            System.setProperty("java.rmi.server.hostname", networkConfig.host);	
            try {
                Registry registry;
                try {
                    registry = LocateRegistry.getRegistry(networkConfig.port);
                    registry.list(); //TODO: is this necessary?
                }
                catch (RemoteException ex)
                {
                    registry = LocateRegistry.createRegistry(networkConfig.port);
                }                
                registry
                    .rebind(BrokerClient.SERVICE_NAME, 
                        UnicastRemoteObject.exportObject(broker, networkConfig.port));
                log.log("[RMI] Started on %s:%d", networkConfig.host, networkConfig.port);
            } catch (RemoteException e) {
                log.err("[RMI] Cannot start on %s:%d", networkConfig.host, networkConfig.port);
                e.printStackTrace();
                System.exit(1);
            }
        };

    @SuppressWarnings("serial") 
    public static class InstructorServlet extends HttpServlet
    {
        BrokerUIInterface broker;
        Config config;
        Log log;
        IRepo<String, Instructor> instructorRepo; 
        public InstructorServlet(Config config, BrokerUIInterface broker) {
            this.broker = broker;
            this.config = config;
            this.log = config.getLog();
            this.instructorRepo = config.getRepoOrDefault("repo.instructor", null);
        }

        private void onNewInstructor(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String iid = "";
            String isig = "";
            try {
                Map<String, Object> requestJson = (Map<String, Object>)JSON.parse(request.getReader());
                iid = (String)requestJson.get("iid");
                isig = (String)requestJson.get("isig");
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);    
                Map<String, Object> respJson = new HashMap<>();
                respJson.put("error", "Input object should be in format {'iid':'<instructor id>', 'isig':'<instructor signature>'}");
                response.getWriter().print(JSON.toString(respJson));
                return;
            }
            if (iid == null || iid.isEmpty() || isig == null || isig.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);    
                Map<String, Object> respJson = new HashMap<>();
                respJson.put("error", "iid and isig should be nonempty");
                response.getWriter().print(JSON.toString(respJson));
                return;                        
            }
            Instructor instructor = this.instructorRepo.get(iid);
            if ((instructor != null) && (instructor.isig != null) && instructor.isig.equals(isig)) {
                response.setStatus(HttpServletResponse.SC_OK);
                Map<String, Object> respJson = new HashMap<>();
                respJson.put("iid", instructor.iid);
                respJson.put("isig", instructor.isig);
                response.getWriter().print(JSON.toString(respJson));    
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Map<String, Object> respJson = new HashMap<>();
                respJson.put("err", true);
                respJson.put("msg", "Auth failed");
                response.getWriter().print(JSON.toString(respJson));
            }            
        }

        private void getStudents(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String iid = "";
            String isig = "";
            Object[] ssigsArray = null;
            List<String> ssigs;
            try {
                Map<String, Object> requestJson = (Map<String, Object>)JSON.parse(request.getReader());
                iid = (String)requestJson.get("iid");
                isig = (String)requestJson.get("isig");
                ssigsArray = (Object[])requestJson.get("ssigs");
                ssigs = Arrays.stream(ssigsArray).map(String::valueOf).collect(Collectors.toList());
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);    
                Map<String, Object> respJson = new HashMap<>();
                respJson.put("error", "Input object should be in format {'iid':'<instructor id>', 'isig':'<instructor signature>', 'ssigs':[ 'ssig1', ...]}");
                response.getWriter().print(JSON.toString(respJson));
                return;
            }
            if (iid == null || iid.isEmpty() || isig == null || isig.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);    
                Map<String, Object> respJson = new HashMap<>();
                respJson.put("error", "iid and isig should be nonempty");
                response.getWriter().print(JSON.toString(respJson));
                return;                        
            }
            Instructor instructor = this.instructorRepo.get(iid);
            if ((instructor != null) && (instructor.isig != null) && instructor.isig.equals(isig)) {
                Map<String, List<Stats>> students = this.broker.getStudentStats(iid, isig, ssigs, null);
                Map<String, Object> respStudentsJson = new HashMap<>();
                for (String ssig: students.keySet()) {
                    List<Stats> stats = students.get(ssig);
                    List<Map<String, Object>> performances = 
                        stats.stream().map(s -> {
                            Map<String, Object> performance = new HashMap<>();
                            performance.put("puzzlesSeen", s.puzzlesSeen);
                            performance.put("puzzlesSolved", s.puzzlesSolved);
                            performance.put("duration", s.durationMs);    
                            performance.put("inactivityMs", s.inactivityMs);   
                            return performance;
                        }).collect(Collectors.toList());                 
                    //performance.put("started", stat.start);
                    //TODO: advanced props
                    respStudentsJson.put(ssig, performances);
                }
                response.setStatus(HttpServletResponse.SC_OK);
                Map<String, Object> respJson = new HashMap<>();
                respJson.put("iid", instructor.iid);
                respJson.put("isig", instructor.isig);
                respJson.put("stats", respStudentsJson);
                response.getWriter().print(JSON.toString(respJson));    
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Map<String, Object> respJson = new HashMap<>();
                respJson.put("err", true);
                respJson.put("msg", "Auth failed");
                response.getWriter().print(JSON.toString(respJson));
            }            
        }

        @Override
        protected void doPost( HttpServletRequest request,
                                HttpServletResponse response ) throws ServletException,
                                                            IOException
        {
            //String res1 = request.getReader().lines().collect(Collectors.joining());
            try 
            {
            response.setContentType("application/json");
            String pathInfo = request.getPathInfo();
            if (pathInfo == null) pathInfo = "/";
            switch (pathInfo)
            {
                case("/"): {
                    //new user 
                    onNewInstructor(request, response);
                    return;
                }
                case("/students"): {
                    //new user 
                    getStudents(request, response);
                    return;
                }
                default: break;
            }
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);    
            Map<String, Object> respJson = new HashMap<>();
            respJson.put("error", "API is not found");
            response.getWriter().print(JSON.toString(respJson));  
            } catch(Exception e) 
            {
                try (StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw))
                {
                    e.printStackTrace(pw);                
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);    
                    Map<String, Object> respJson = new HashMap<>();
                    respJson.put("error", sw.toString());
                    response.getWriter().print(JSON.toString(respJson));      
                }
            }
        }
    }        

    @SuppressWarnings("serial") 
    public static class StudentServlet extends HttpServlet
    {
        BrokerUIInterface broker;
        Config config;
        Log log;
        IRepo<String, Events> eventsRepo;
        public StudentServlet(Config config, BrokerUIInterface broker) {
            this.broker = broker;
            this.config = config;
            this.log = config.getLog();
            this.eventsRepo = 
                config.getRepoOrDefault("repo.events", EventsNoopRepo.class);
        }
        
        private void onNewStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String sid = "";
            String ssig = "";
            String skey = "";
            try {
                Map<String, Object> requestJson = (Map<String, Object>)JSON.parse(request.getReader());
                sid = (String)requestJson.get("sid");
                ssig = (String)requestJson.get("ssig");
                skey = (String)requestJson.get("skey");
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);    
                Map<String, Object> respJson = new HashMap<>();
                respJson.put("error", "Input object should be in format {'sid':'<student id>', 'ssig':'<student signature>', 'skey':'<instructor signature optional>'}");
                response.getWriter().print(JSON.toString(respJson));
                return;
            }
            if (sid == null || sid.isEmpty() || ssig == null || ssig.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);    
                Map<String, Object> respJson = new HashMap<>();
                respJson.put("error", "sid and ssig should be nonempty");
                response.getWriter().print(JSON.toString(respJson));
                return;                        
            }
            response.setStatus(HttpServletResponse.SC_OK);
            Student student = broker.authenticateStudent(sid, ssig, skey);
            Auth auth = student.getAuth();
            Stats stats = student.getStats();
            Map<String, Object> respJson = new HashMap<>();
            respJson.put("id", auth.getSid());
            //respJson.put("ip", request.getRemoteAddr());
            respJson.put("sessionId", auth.newSession());
            respJson.put("solved", stats.puzzlesSolved);
            respJson.put("seen", stats.puzzlesSeen);
            respJson.put("duration", stats.durationMs);
            respJson.put("inactivityMs", stats.inactivityMs);
            response.getWriter().print(JSON.toString(respJson));
        }

        //https://www.geeksforgeeks.org/minimum-insertions-sort-array/
        private int minMovesForShuffling(List<Integer> fragmentIds)
        {
            List<Integer> fragmentsWithoutDistractorIds = fragmentIds.stream().filter(id -> id >= 0).collect(Collectors.toList());
            int[] lis = new int[fragmentsWithoutDistractorIds.size()];
            for (int i = 0; i < lis.length; i++) 
                lis[i] = 1; 
            for (int i = 1; i < lis.length; i++) 
                for (int j = 0; j < i; j++) {
                    int arrI = fragmentsWithoutDistractorIds.get(i);
                    int arrJ = fragmentsWithoutDistractorIds.get(j);
                    if (arrI >= arrJ && (lis[i] < (lis[j] + 1))) 
                        lis[i] = lis[j] + 1; 
                }
            int maxLis = Arrays.stream(lis).max().orElse(0);
            return (fragmentsWithoutDistractorIds.size() - maxLis)
                + (fragmentIds.size() - fragmentsWithoutDistractorIds.size());
        }

        private void onNewEval(String sid, int puzzleId, HttpServletRequest request, HttpServletResponse response) throws IOException
        {
            ParsonsEvaluation eval = null;
            try {
                Map<String, Object> requestJson = (Map<String, Object>)JSON.parse(request.getReader());
                int moves = (int)(long)requestJson.get("moves");
                //String design = (String)requestJson.get("design");
                List<Integer> shuffling = 
                    Arrays.stream((Object[])requestJson.get("shuffling")).map(o -> 
                        (int)(long)o).collect(Collectors.toList());   
                int minMoves = minMovesForShuffling(shuffling);             
                double fitness = ((double)moves)/minMoves;
                // double fitness = 0.0;                
                // if (fitnessObj instanceof Long)
                //     fitness = (double)fitnessObj;
                // else fitness = (double)fitnessObj;
                long timeInMs = (long)requestJson.get("timeInMs");
                long inactivityMs = (long)requestJson.get("inactivityMs");
                boolean gaveUp = (boolean)requestJson.get("gaveUp");                
                eval = new ParsonsEvaluation(sid, puzzleId, moves, timeInMs, inactivityMs, fitness, gaveUp, System.currentTimeMillis());
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);    
                Map<String, Object> respJson = new HashMap<>();
                respJson.put("error", e.toString());
                response.getWriter().print(JSON.toString(respJson));
                return;
            }
            broker.setParsonsEvaluation(eval);
            response.setStatus(HttpServletResponse.SC_CREATED); //TODO: add Location header and get eval func  
            Map<String, Object> respJson = new HashMap<>();
            respJson.put("ok", true);
            response.getWriter().print(JSON.toString(respJson));                  
        }

        // private void getEvents(String iid, String ssig, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //     eventsRepo.get
        // }
        private void onNewEvents(String sid, int puzzleId, HttpServletRequest request, HttpServletResponse response) throws IOException
        {            
            try {
                String text = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                eventsRepo.update(Collections.singletonList(new Events(sid, puzzleId, text)));
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);    
                Map<String, Object> respJson = new HashMap<>();
                respJson.put("error", e.getMessage());
                response.getWriter().print(JSON.toString(respJson));
                return;
            }
            response.setStatus(HttpServletResponse.SC_CREATED); //TODO: add Location header and get eval func  
            Map<String, Object> respJson = new HashMap<>();
            respJson.put("ok", true);
            response.getWriter().print(JSON.toString(respJson));                  
        }

        
        private static Pattern puzzlePattern = Pattern.compile("^/(?<sid>\\w*)/puzzle/?$");
        private static Pattern statsPattern = Pattern.compile("^/(?<sid>\\w*)/stats/?$");
        private static Pattern evalPattern = Pattern.compile("^/(?<sid>\\w*)/puzzle/(?<puzzleId>[1-9]\\d*|0)/eval/?$");
        private static Pattern eventsPattern = Pattern.compile("^/(?<sid>\\w*)/puzzle/(?<puzzleId>[1-9]\\d*|0)/events/?$");

        @Override
        protected void doGet( HttpServletRequest request,
                                HttpServletResponse response ) throws ServletException,
                                                            IOException
        {
            try {
                response.setContentType("application/json");        
                String pathInfo = request.getPathInfo();
                if (pathInfo == null) pathInfo = "/";             
                Matcher matcher = puzzlePattern.matcher(pathInfo);
                if (matcher.matches())
                {
                    //obtaining new puzzle
                    String sid = URLDecoder.decode(matcher.group("sid"), StandardCharsets.UTF_8.name());
                    ParsonsPuzzle puzzle = broker.getParsonsPuzzle(sid);
                    List<Fragment> fragments = puzzle.buildFragments();
                    Map<String, Object> respJson = new HashMap<>();
                    respJson.put("id", puzzle.id);
                    respJson.put("title", puzzle.title);
                    respJson.put("description", puzzle.description);
                    //respJson.put("program", puzzle.program);
                    //List<Map<String, Object>> distractersJson = new ArrayList<>();
                    List<Map<String, Object>> fragmentsJson = new ArrayList<>();
                    fragments.forEach(fragment -> {
                        Map<String, Object> dMap = new HashMap<>();
                        dMap.put("id", fragment.index);   
                        dMap.put("lines", fragment.lines);
                        if (fragment.distracterId.isPresent()) {
                            dMap.put("distracterId", fragment.distracterId.get());
                        }
                        fragmentsJson.add(dMap);
                    });
                    // puzzle.distracters.forEach(d -> {
                    //     Map<String, Object> dMap = new HashMap<>();
                    //     dMap.put("id", d.distracterId);
                    //     dMap.put("line", d.distractedLine);
                    //     distractersJson.add(dMap);
                    // });
                    //respJson.put("distracters", distractersJson);
                    respJson.put("fragments", fragmentsJson);
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().print(JSON.toString(respJson));
                    return;                        
                }
                Matcher statsMatcher = statsPattern.matcher(pathInfo);
                if (statsMatcher.matches())
                {
                    String sid = URLDecoder.decode(statsMatcher.group("sid"), StandardCharsets.UTF_8.name());
                    Stats stats = broker.getStudentStats(sid);
                    Map<String, Object> respJson = new HashMap<>();
                    respJson.put("solved", stats.puzzlesSolved);
                    respJson.put("seen", stats.puzzlesSeen);
                    respJson.put("duration", stats.durationMs);
                    respJson.put("inactivityMs", stats.inactivityMs);
                    //respJson.put("start", stats.start.getTime());
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().print(JSON.toString(respJson));                                
                    return;
                }            
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);    
                Map<String, Object> respJson = new HashMap<>();
                respJson.put("error", "API is not found");
                response.getWriter().print(JSON.toString(respJson));   
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);    
                Map<String, Object> respJson = new HashMap<>();
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);                
                respJson.put("error", e.getMessage());
                respJson.put("stack", sw.toString());
                response.getWriter().print(JSON.toString(respJson));   
            }
        }
        
        @Override
        protected void doPost( HttpServletRequest request,
                                HttpServletResponse response ) throws ServletException,
                                                            IOException
        {
            //String res1 = request.getReader().lines().collect(Collectors.joining());
            try 
            {
            response.setContentType("application/json");
            String pathInfo = request.getPathInfo();
            if (pathInfo == null) pathInfo = "/";
            switch (pathInfo)
            {
                case("/"): {
                    //new user 
                    onNewStudent(request, response);
                    return;
                }
                default: break;
            }
            Matcher evalMatcher = evalPattern.matcher(pathInfo);
            if (evalMatcher.matches())
            {
                //obtaining new puzzle
                String sid = URLDecoder.decode(evalMatcher.group("sid"), StandardCharsets.UTF_8.name());
                int puzzleId = Integer.valueOf(evalMatcher.group("puzzleId"));
                onNewEval(sid, puzzleId, request, response);
                return;                        
            }
            Matcher eventsMatcher = eventsPattern.matcher(pathInfo);
            if (eventsMatcher.matches())
            {
                //obtaining new puzzle
                String sid = URLDecoder.decode(eventsMatcher.group("sid"), StandardCharsets.UTF_8.name());
                int puzzleId = Integer.valueOf(eventsMatcher.group("puzzleId"));
                onNewEvents(sid, puzzleId, request, response);
                return;                        
            }
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);    
            Map<String, Object> respJson = new HashMap<>();
            respJson.put("error", "API is not found");
            response.getWriter().print(JSON.toString(respJson));    
            } catch (Exception e) 
            {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);    
                Map<String, Object> respJson = new HashMap<>();
                respJson.put("error", e.getMessage());
                response.getWriter().print(JSON.toString(respJson));
            }            
        }
    }         

    public static NetworkPolicy REST = 
        (networkConfig, config, broker) -> {
            final Log log = config.getLog();
            // new Thread(() -> 
			// {
				try (FileLog fileLog = Log.file(Paths.get(config.getOutputFolder(), "web.log").toString())) {				
					org.eclipse.jetty.util.log.Log.setLog(new Logger(){
						@Override public void debug(Throwable arg0) { }
						@Override public void debug(String arg0, Object... arg1) {	}
						@Override public void debug(String arg0, long arg1) { }
						@Override public void debug(String arg0, Throwable arg1) { }
						@Override public Logger getLogger(String arg0) { return this; }
						@Override public String getName() { return "web.log";}
						@Override public void ignore(Throwable arg0) {}
						@Override public void info(Throwable arg0) { arg0.printStackTrace(fileLog.out); }
						@Override public void info(String arg0, Object... arg1) { fileLog.log(arg0, arg1); }
						@Override public void info(String arg0, Throwable arg1) { fileLog.log(arg0); arg1.printStackTrace(fileLog.out); }
						@Override public boolean isDebugEnabled() { return false; }
						@Override public void setDebugEnabled(boolean arg0) {	}
						@Override public void warn(Throwable arg0) { arg0.printStackTrace(fileLog.out); }
						@Override public void warn(String arg0, Object... arg1) {fileLog.log(arg0, arg1); }
						@Override public void warn(String arg0, Throwable arg1) {fileLog.log(arg0); arg1.printStackTrace(fileLog.out); }
					});

					log.log("[REST] Starting on %s:%d", networkConfig.host, networkConfig.port);
                    Server server = new Server();

                    if (networkConfig.cert != null && networkConfig.key != null) {
                        log.log("[REST] setting up SSL cert: %s. key: %s", networkConfig.cert, networkConfig.key);
                        SslContextFactory sslContextFactory = new SslContextFactory();
                        sslContextFactory.setTrustStorePath(networkConfig.cert);
                        sslContextFactory.setKeyStorePath(networkConfig.key);
                        sslContextFactory.setExcludeProtocols("SSLv3");
                        
                        ServerConnector sslConnector = new ServerConnector(server, sslContextFactory);
                        sslConnector.setPort(networkConfig.port);     
                        sslConnector.setHost(networkConfig.host);
                        server.addConnector(sslConnector);
                        log.log("[REST] SSL was configured");
                    }

                    HandlerList handlers = new HandlerList();
                    
                    RewriteHandler redirectHandler = new RewriteHandler();
                    //MovedPermanentlyRule movedRule = new MovedPermanentlyRule();
                    //movedRule.setRegex("http://www.company.com/dump/.*");
                    //movedRule.setReplacement("https://api.company.com/dump/");
                    redirectHandler.addRule(new Rule() {

						@Override
						public String matchAndApply(String target, HttpServletRequest request, HttpServletResponse response)
								throws IOException {
                            if (target.equals("/"))
                            {
                                response.setHeader("Location", "/index.html");
                                response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
                                response.getOutputStream().flush(); // no output / content
                                response.getOutputStream().close();
                                return "/index.html";
                            }                            
							return null;
						}

                    });                    
                    handlers.addHandler(redirectHandler);                  

                    ServletContextHandler apiHandler = new ServletContextHandler();
                    apiHandler.setVirtualHosts(new String[] { networkConfig.host });
					apiHandler.setServer(server);
                    apiHandler.setContextPath("/");
                    if (networkConfig.www != null)
                    {
                        Path contentPath = null;
                        if (networkConfig.www.startsWith("https://api.github."))
                        {
                            contentPath = Paths.get(config.getOutputFolder(), "www", networkConfig.policyName);
                            String auth = config.get("gitAuth", "");
                            evoparsons.github.Client.download(networkConfig.www, contentPath.toString(), auth, log);
                        } else if (networkConfig.www.startsWith("file://")) {
                            contentPath = Paths.get(new URL(networkConfig.www).toURI());
                        } else {
                            log.err("[REST] cannot create www folder from %s", networkConfig.www);
                            System.exit(1);
                        }
                        final String staticContentPath = 
                            //NetworkPolicy.class.getResource("rest/index.html").toExternalForm();
                            //new URL(NetworkPolicy.class.getResource("rest/index.html"), ".").toExternalForm();
                            contentPath.normalize().toUri().toString();
                        log.log("[REST] WebUI content by path: %s", staticContentPath);                                                    
                        apiHandler.setResourceBase(staticContentPath);        
                        apiHandler.addServlet(DefaultServlet.class, "/*");
                        apiHandler.setWelcomeFiles(new String[] { "index.html" });  
                    }
                    if (networkConfig.servlets.size() == 0) {
                        log.err("[REST} Interface %s does not have servlets", networkConfig.policyPath);
                    }
                    networkConfig.servlets.stream()
                        .forEach(servletId -> 
                        {
                            Servlet servlet = config.getInstance(servletId, config, broker);
                            apiHandler.addServlet(new ServletHolder(servlet), config.get(servletId + ".path", "/*").trim());
                        });
                    //apiHandler.addServlet(new ServletHolder(new StudentServlet(config, broker)), "/api/student/*");
                    //apiHandler.addServlet(new ServletHolder(new InstructorServlet(config, broker)), "/api/instructor/*");
                    handlers.addHandler(apiHandler);                  
					server.setHandler(handlers);		
					server.start();
					//server.join();
				} catch (Exception e) {
                    log.err("[REST] Cannot start service");
                    e.printStackTrace();
                    System.exit(1);
				}
			//}).start();;							
            
        };
}
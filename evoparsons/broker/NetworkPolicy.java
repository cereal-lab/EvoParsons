package evoparsons.broker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.rewrite.handler.Rule;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ajax.JSON;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;

import evoparsons.broker.Log.FileLog;
import evoparsons.rmishared.BrokerClient;
import evoparsons.rmishared.BrokerInterface;
import evoparsons.rmishared.ParsonsEvaluation;
import evoparsons.rmishared.ParsonsPuzzle;
import evoparsons.rmishared.Stats;

public interface NetworkPolicy {
    void startInterface(Log log, Config.Network networkConfig, Config config, BrokerInterface broker);

    public static NetworkPolicy RMI = 
        (log, networkConfig, config, broker) -> {
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
    public static class StudentServlet extends HttpServlet
    {
        BrokerInterface broker;
        Config config;
        public StudentServlet(Config config, BrokerInterface broker) {
            this.broker = broker;
            this.config = config;
        }

        private void onNewStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String login = "";
            try {
                Map<String, Object> requestJson = (Map<String, Object>)JSON.parse(request.getReader());
                login = (String)requestJson.get("login");
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);    
                Map<String, Object> respJson = new HashMap<>();
                respJson.put("error", "Input object should be in format {'login':<nonEmptyLogin>}");
                response.getWriter().print(JSON.toString(respJson));
                return;
            }
            if (login == null || login.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);    
                Map<String, Object> respJson = new HashMap<>();
                respJson.put("error", "Login should be nonempty");
                response.getWriter().print(JSON.toString(respJson));
                return;                        
            }
            response.setStatus(HttpServletResponse.SC_OK);
            int id = broker.getStudentID(login);
            Map<String, Object> respJson = new HashMap<>();
            respJson.put("id", id);
            respJson.put("ip", request.getRemoteAddr());
            respJson.put("sessionId", UUID.randomUUID().toString());
            response.getWriter().print(JSON.toString(respJson));
        }

        private void onNewEval(int studentId, int puzzleId, HttpServletRequest request, HttpServletResponse response) throws IOException
        {
            ParsonsEvaluation eval = null;
            try {
                Map<String, Object> requestJson = (Map<String, Object>)JSON.parse(request.getReader());
                Object fitnessObj = requestJson.get("fitness");
                double fitness = 0.0;
                if (fitnessObj instanceof Long)
                    fitness = (double)(long)fitnessObj;
                else fitness = (double)fitnessObj;
                long timeInMs = (long)requestJson.get("timeInMs");
                boolean gaveUp = (boolean)requestJson.get("gaveUp");                
                eval = new ParsonsEvaluation(studentId, puzzleId, fitness, timeInMs, gaveUp, System.currentTimeMillis());
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

        private void onNewAttempt(int studentId, int puzzleId, HttpServletRequest request, HttpServletResponse response) throws IOException
        {
            Stats stats = broker.getStudentStats(studentId);
            int attemptId = 0;
            synchronized (stats)
            {
                attemptId = 
                    stats.attemptsPerPuzzle.getOrDefault(puzzleId, 0);
                    stats.attemptsPerPuzzle.put(puzzleId, attemptId + 1);
            }
            File file = Paths.get(config.getOutputFolder(), "data", String.valueOf(studentId), String.valueOf(puzzleId), "attempts", String.format("%d.json", attemptId)).toFile().getAbsoluteFile();
            file.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(file)) {
                String text = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                writer.write(text);
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

        // private void onNewEvent(int studentId, String sessionId, HttpServletRequest request, HttpServletResponse response) throws IOException
        // {
        //     File file = Paths.get(config.getOutputFolder(), "data", String.valueOf(studentId), String.format("%s.json", sessionId)).toFile().getAbsoluteFile();
        //     file.getParentFile().mkdirs();
        //     try (FileWriter writer = new FileWriter(file)) {
        //         String text = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        //         writer.append(System.lineSeparator());
        //         writer.append(text);
        //     } catch (Exception e) {
        //         response.setStatus(HttpServletResponse.SC_BAD_REQUEST);    
        //         Map<String, Object> respJson = new HashMap<>();
        //         respJson.put("error", e.getMessage());
        //         response.getWriter().print(JSON.toString(respJson));
        //         return;
        //     }
        //     response.setStatus(HttpServletResponse.SC_CREATED); //TODO: add Location header and get eval func  
        //     Map<String, Object> respJson = new HashMap<>();
        //     respJson.put("ok", true);
        //     response.getWriter().print(JSON.toString(respJson));                  
        // }

        // private void onGeneralInfo(int studentId, HttpServletRequest request, HttpServletResponse response) throws IOException
        // {
        //     synchronized (generalInfo)
        //     {
        //         int generalInfoId = generalInfo.getOrDefault(studentId, 0);
        //         File file = Paths.get(config.getOutputFolder(), "data", String.valueOf(studentId), "sessions", String.format("%d.json", generalInfoId)).toFile().getAbsoluteFile();
        //         file.getParentFile().mkdirs();
        //         try (FileWriter writer = new FileWriter(file)) {
        //             String text = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        //             writer.write(text);
        //             generalInfo.put(studentId, generalInfoId + 1);
        //         } catch (Exception e) {
        //             response.setStatus(HttpServletResponse.SC_BAD_REQUEST);    
        //             Map<String, Object> respJson = new HashMap<>();
        //             respJson.put("error", e.getMessage());
        //             response.getWriter().print(JSON.toString(respJson));
        //             return;
        //         }
        //     }
        //     response.setStatus(HttpServletResponse.SC_CREATED); //TODO: add Location header and get eval func  
        //     Map<String, Object> respJson = new HashMap<>();
        //     respJson.put("ok", true);
        //     response.getWriter().print(JSON.toString(respJson));                  
        // }        

        
        private static Pattern puzzlePattern = Pattern.compile("^/(?<studentId>[1-9]\\d*|0)/puzzle/?$");
        private static Pattern statsPattern = Pattern.compile("^/(?<studentId>[1-9]\\d*|0)/stats/?$");
        private static Pattern evalPattern = Pattern.compile("^/(?<studentId>[1-9]\\d*|0)/puzzle/(?<puzzleId>[1-9]\\d*|0)/eval/?$");
        private static Pattern attemptPattern = Pattern.compile("^/(?<studentId>[1-9]\\d*|0)/puzzle/(?<puzzleId>[1-9]\\d*|0)/attempt/?$");
        //private static Pattern eventPattern = Pattern.compile("^/(?<studentId>[1-9]\\d*|0)/(?<sessionId>.*?)/event/?$");
        //private static Pattern generalInfoPattern = Pattern.compile("^/(?<studentId>[1-9]\\d*|0)/info/?$");
        @Override
        protected void doGet( HttpServletRequest request,
                                HttpServletResponse response ) throws ServletException,
                                                            IOException
        {
            response.setContentType("application/json");        
            String pathInfo = request.getPathInfo();
            if (pathInfo == null) pathInfo = "/";             
            Matcher matcher = puzzlePattern.matcher(pathInfo);
            if (matcher.matches())
            {
                //obtaining new puzzle
                int studentId = Integer.valueOf(matcher.group("studentId"));
                ParsonsPuzzle puzzle = broker.getParsonsPuzzle(studentId);
                Map<String, Object> respJson = new HashMap<>();
                respJson.put("id", puzzle.id);
                respJson.put("title", puzzle.title);
                respJson.put("description", puzzle.description);
                respJson.put("program", puzzle.program);
                List<Map<String, Object>> distractersJson = new ArrayList<>();
                puzzle.distracters.forEach(d -> {
                    Map<String, Object> dMap = new HashMap<>();
                    dMap.put("id", d.distracterId);
                    dMap.put("line", d.distractedLine);
                    distractersJson.add(dMap);
                });
                respJson.put("distracters", distractersJson);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().print(JSON.toString(respJson));
                return;                        
            }
            Matcher statsMatcher = statsPattern.matcher(pathInfo);
            if (statsMatcher.matches())
            {
                int studentId = Integer.valueOf(statsMatcher.group("studentId"));
                Stats stats = broker.getStudentStats(studentId);
                Map<String, Object> respJson = new HashMap<>();
                respJson.put("solved", stats.puzzlesSolved);
                respJson.put("seen", stats.puzzlesSeen);
                respJson.put("duration", stats.duration);
                //respJson.put("start", stats.start.getTime());
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().print(JSON.toString(respJson));                                
                return;
            }            
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);    
            Map<String, Object> respJson = new HashMap<>();
            respJson.put("error", "API is not found");
            response.getWriter().print(JSON.toString(respJson));                    
        }
        
        @Override
        protected void doPost( HttpServletRequest request,
                                HttpServletResponse response ) throws ServletException,
                                                            IOException
        {
            //String res1 = request.getReader().lines().collect(Collectors.joining());
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
                int studentId = Integer.valueOf(evalMatcher.group("studentId"));
                int puzzleId = Integer.valueOf(evalMatcher.group("puzzleId"));
                onNewEval(studentId, puzzleId, request, response);
                return;                        
            }
            Matcher attemptMatcher = attemptPattern.matcher(pathInfo);
            if (attemptMatcher.matches())
            {
                //obtaining new puzzle
                int studentId = Integer.valueOf(attemptMatcher.group("studentId"));
                int puzzleId = Integer.valueOf(attemptMatcher.group("puzzleId"));
                onNewAttempt(studentId, puzzleId, request, response);
                return;                        
            }
            // Matcher eventMatcher = eventPattern.matcher(pathInfo);
            // if (eventMatcher.matches())
            // {
            //     //obtaining new puzzle
            //     int studentId = Integer.valueOf(evalMatcher.group("studentId"));
            //     String sessionId = evalMatcher.group("sessionId");
            //     onNewEvent(studentId, sessionId, request, response);
            //     return;                        
            // }    
            // Matcher generalInfoMatcher = generalInfoPattern.matcher(pathInfo);
            // if (generalInfoMatcher.matches())
            // {
            //     //obtaining new puzzle
            //     int studentId = Integer.valueOf(evalMatcher.group("studentId"));
            //     onGeneralInfo(studentId, request, response);
            //     return;                        
            // }                       
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);    
            Map<String, Object> respJson = new HashMap<>();
            respJson.put("error", "API is not found");
            response.getWriter().print(JSON.toString(respJson));                    
        }
    }         

    public static NetworkPolicy REST = 
        (log, networkConfig, config, broker) -> {
            new Thread(() -> 
			{
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

					log.log("[REST] Started on %s:%d", networkConfig.host, networkConfig.port);
                    Server server = new Server(networkConfig.port);
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
                    final String staticContentPath = 
                        //NetworkPolicy.class.getResource("rest/index.html").toExternalForm();
                        new URL(NetworkPolicy.class.getResource("rest/index.html"), ".").toExternalForm();
                    log.log("[REST] WebUI content by path: %s", staticContentPath);                                                    
					apiHandler.setBaseResource(Resource.newResource(staticContentPath));
                    apiHandler.addServlet(new ServletHolder(new StudentServlet(config, broker)), "/api/student/*");
                    apiHandler.addServlet(DefaultServlet.class, "/*");
                    apiHandler.setWelcomeFiles(new String[] { "index.html" });  
                    handlers.addHandler(apiHandler);                  
					server.setHandler(handlers);					
					server.start();
					server.join();
				} catch (Exception e) {
                    log.err("[REST] Cannot start service");
                    e.printStackTrace();
                    System.exit(1);
				}
			}).start();;							
            
        };
}
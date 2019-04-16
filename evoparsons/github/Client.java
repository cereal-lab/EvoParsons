package evoparsons.github;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.DatatypeConverter;

import org.eclipse.jetty.util.ajax.JSON;

import evoparsons.broker.Log;

/*
Using v3 github API to download folders and files

*/
public class Client {

	public static void downloadFolderWithSvn(String url, String folder, Log log) {
		try 
		{
			log.log("Downloading %s to %s", url, folder);
			String line = "";
			Process process = 
				Runtime.getRuntime().exec(String.format("svn checkout %s %s", url, folder));
			try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()) ))
			{
				while ((line = in.readLine()) != null) log.log(line);
			}
			int errorCode = process.waitFor();
			if (errorCode > 0) 
			{
				log.err("Cannot download to folder %s: %d", folder, errorCode);
				System.exit(1);
			}
		} catch (Exception e) 
		{
			log.err("Error downloading to folder %s", folder);
			e.printStackTrace();
			System.exit(1);
		}
	}

    private static void downloadFileFromJson(Map<String, Object> p, String outputPath, Log log) throws IOException {        
        String base64EncContent = (String)p.get("content");
        if (base64EncContent != null)
        {
            //found file
            byte[] bytes = DatatypeConverter.parseBase64Binary(base64EncContent);
            try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                fos.write(bytes);
                //fos.close(); There is no more need for this line since you had created the instance of "fos" inside the try. And this will automatically close the OutputStream
            }
            return;
        } 
        String downloadUrl = (String)p.get("download_url");
        if (downloadUrl != null) 
        {
            download(downloadUrl, outputPath, log);
            return;
        }
        String url = (String)p.get("url");
        if (url != null)
        {
            //download subfolders
            download(url, outputPath, log);
        }
        //noop othervise 
        //TODO: logging - this is only possible if protocol of github v3 API changes
        return;
    }
    public static void download(String url, String outputPath, Log log)
    {        
        try 
        {
            /* NEXT code is for debugging through proxy 
              I  used Fiddler on windows system to trace http request */
            /*
        System.setProperty("java.net.useSystemProxies", "true");
        TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
        };

    // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

    // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

    // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        */
        HttpURLConnection conn = (HttpURLConnection)new URL(url).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/vnd.github.v3+json");
        conn.setRequestProperty("User-Agent", "EvoParsonsBot");
        conn.setRequestProperty("Content-Type", "application/json");
        try (InputStream stream = conn.getInputStream()) {
            if (url.startsWith("https://api.")) {
                //api format
                Object parsed;
                try (InputStreamReader reader = new InputStreamReader(stream))
                {
                    parsed = JSON.parse(reader);
                }
                if (parsed instanceof Map) // parsed is an object 
                {
                    Map<String, Object> p = (Map<String, Object>)parsed;
                    String fileOutputPath = Paths.get(outputPath, p.get("name").toString()).toString();
                    downloadFileFromJson(p, fileOutputPath, log);
                }
                if (parsed instanceof Object[])
                {
                    //assuming directory 
                    //- we do not donwload recursivelly all directories but only files in current dir
                    Object[] p = (Object[])parsed;
                    for (int i = 0; i < p.length; i++)
                    {
                        //download each file
                        Map<String, Object> pf = (Map<String, Object>)p[i];
                        String fileOutputPath = Paths.get(outputPath, pf.get("name").toString()).toString();
                        downloadFileFromJson(pf, fileOutputPath, log);
                    }
                }
            } else {
                //assuming raw format
                Path p = Paths.get(outputPath);
                p.getParent().toFile().mkdirs();
                Files.copy(stream, p, StandardCopyOption.REPLACE_EXISTING);
            }
            //System.out.println(responseBody);
        }
        } catch (Exception e) {
			log.err("Error downloading %s", outputPath);
			e.printStackTrace();
			System.exit(1);            
        }
    }

}
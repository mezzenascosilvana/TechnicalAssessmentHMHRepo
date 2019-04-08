package common;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractTest {

    private static final Logger LOGGER = Logger.getLogger(AbstractTest.class.getName());

   
    /***
     * The HTTP client send a request with a URL and receives a response with the
     * available repositories.
     *
     * @param requestURL
     * @return String
     * @throws org.json.JSONException
     * @throws Exception 
     */
    public JSONObject getHttpResponse(String requestURL) throws JSONException, Exception {
        
    	Properties p= new Properties();
		FileInputStream fi = new FileInputStream("global.properties");
        p.load(fi);
        
        
        JSONObject result = null;
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(p.getProperty("zomato_url") + requestURL); 
        LOGGER.log(Level.INFO, "Endpoint :" + request);
        request.addHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader("user-key", p.getProperty("token"));
        HttpResponse response;

        try {
            response = client.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String retSrc = EntityUtils.toString(entity);
                JSONObject resultJ = new JSONObject(retSrc); // Convert String to JSON Object
                result = resultJ;
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Exception occur", ex);
        }
        
        return result;
    }
    
    
}

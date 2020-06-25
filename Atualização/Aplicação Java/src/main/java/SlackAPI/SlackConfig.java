package SlackAPI;

	
  import com.fasterxml.jackson.databind.ObjectMapper;
  import org.apache.http.client.methods.HttpPost;
  import org.apache.http.entity.StringEntity;
  import org.apache.http.impl.client.CloseableHttpClient;
  import org.apache.http.impl.client.HttpClients;
  import java.io.IOException;


public class SlackConfig {
    
    // Url webhook do slack 
    private static String slackWebhookUrl = "https://hooks.slack.com/services/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";
  
    public static void sendMessage(SlackMessage message) {
        
        // Cria servidor
        CloseableHttpClient client = HttpClients.createDefault();
        
        // Post do webhook
        HttpPost httpPost = new HttpPost(slackWebhookUrl);
  
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(message);

            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
  
            client.execute(httpPost);
            client.close();
        } catch (IOException e) {
           e.printStackTrace();
        }
    }    
    
}

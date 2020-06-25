
package SlackAPI;

public class SlackApp {
    
    public static void main(String[] args) {
        
      SlackMessage slackMessage = SlackMessage.builder()
            .channel("geral")
            .text("Teste")
            .build();
      SlackConfig.sendMessage(slackMessage);
    }
}

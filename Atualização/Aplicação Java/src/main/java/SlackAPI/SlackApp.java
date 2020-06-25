package SlackAPI;

import com.mycompany.projeto_moss.Dashboard;

public class SlackApp {
    
    public static void main(String[] args) {
    
    Dashboard d1 = new Dashboard();
    d1.getCpu();
    d1.getDisco();
    
        
      SlackMessage slackMessage = SlackMessage.builder()
            .channel("geral")
            .text("Teste")
            .build();
      SlackConfig.sendMessage(slackMessage);
    }
}
package SlackAPI;

import lombok.*;
import java.io.Serializable;
  
@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Getter
@Setter

public class SlackMessage implements Serializable {
        
    private String username;
    private String text;
    private String channel;


    public String getUsername() {
        return username;
    }

    public String getText() {
        return text;
    }
    
    public String getChannel() {
        return channel;
    }
          
    
    
}


package SlackAPI;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

public class SlackApp {
    
    public static void main(String[] args) {
        
    private String ramTotal;
    private String ramDisp;
    private String cpu;
    private String disco;
    private SystemInfo si;
    private HardwareAbstractionLayer hal;
    private OperatingSystem os;
    
    GlobalMemory memory = hal.getMemory();
    this.ramTotal = FormatUtil.formatBytes(memory.getTotal());
    long tot = memory.getTotal();
    long disp = memory.getAvailable();
    this.ramDisp = String.format("%.1f%%", 100 - (100d * disp / tot));
        
      SlackMessage slackMessage = SlackMessage.builder()
            .channel("geral")
            .text(si)
            .build();
      SlackConfig.sendMessage(slackMessage);
    
    }
    
}

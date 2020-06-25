/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.JDBC;

import org.apache.commons.dbcp2.BasicDataSource;
import Log.TXT.Log;
import java.time.LocalDateTime;


public class Connection {
    
    private BasicDataSource dataSource;

    public Connection() {
        Log.writeLog("Iniciando conexão com o Banco de Dados");
        try {
        Log.writeLog("Conectado Com o Banco De Dados");
        dataSource = new BasicDataSource();
        dataSource​.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource​.setUrl("jdbc:sqlserver://svrmoss.database.windows.net:1433;database=BD2ADSA;user=mossadmin@svrmoss;password={your_password_here};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");  
        dataSource​.setUsername("mossadmin");
        dataSource​.setPassword("#Gfgrupo8");
        } catch (Exception e) {
            Log.writeLog("Falha na Conexão com o Banco de Dados.");
        }
       
        
    }

    public BasicDataSource getDataSource() {
        return dataSource;
    }

        
    
}

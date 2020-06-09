/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.JDBC;

import org.apache.commons.dbcp2.BasicDataSource;
import Log.TXT.Log;


public class Connection {
    
    private BasicDataSource dataSource;

    public Connection() {
        
        Log.writeLog("Iniciando conexão com o Banco de Dados");
        dataSource = new BasicDataSource();
        dataSource​.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource​.setUrl("jdbc:mysql://localhost:3306/teste?useTimezone=true&serverTimezone=UTC");  
        dataSource​.setUsername("root");
        dataSource​.setPassword("bandtec");
        
    }

    public BasicDataSource getDataSource() {
        return dataSource;
    }

        
    
}

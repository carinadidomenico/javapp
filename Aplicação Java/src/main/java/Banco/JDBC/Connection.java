/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.JDBC;

import org.apache.commons.dbcp2.BasicDataSource;


public class Connection {
    private BasicDataSource dataSource;

    public Connection() {
        dataSource = new BasicDataSource();
        dataSource​.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource​.setUrl("jdbc:mysql://localhost:3306/meubanco?useTimezone=true&serverTimezone=UTC");  
        dataSource​.setUsername("root");
        dataSource​.setPassword("bandtec");
        
    }

    public BasicDataSource getDataSource() {
        return dataSource;
    }

        
    
}

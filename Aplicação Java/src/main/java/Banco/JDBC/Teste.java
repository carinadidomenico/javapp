/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.JDBC;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Aluno
 */
public class Teste {
  

    Connection con = new Connection();
    JdbcTemplate template = new JdbcTemplate(con.getDataSource());
    Timer timer = new Timer();
    public void ArmazenamentoDados(){
         int delay = 2000;   // delay de 2 seg.
        int interval = 5000;  // intervalo de 1 seg. 
        timer.scheduleAtFixedRate(new TimerTask(){
    public void run(){
    template.update("INSERT INTO LEITURA (ORIGEM,VALOR,DATA_HORA) VALUES (?,?,?)", "RAM", 20, LocalDateTime.now());
                List todasOcorrencias = template.queryForList("SELECT * FROM LEITURA;");
                System.out.println(todasOcorrencias);
    }}, delay, interval);
     }
    public static void eu(){
        System.out.println("eu");}
    public void ParaArmazenamento(){
    timer.cancel();
    }
}

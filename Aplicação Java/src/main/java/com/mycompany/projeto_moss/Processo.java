/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projeto_moss;

import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Aluno
 */
public class Processo {

    private Point point = new Point();

    

    public static void main(String[] args) {
         final long time = 1000;
        Timer timer = new Timer();
        TimerTask tarefa = new TimerTask() {
            public void run() {
                try {

                    Dashboard att = new Dashboard();

                    System.out.println(att.getSo());
                    System.out.println(att.getProcesso());

                    System.out.println(att.getCpu());
                    if (!att.getCpu().equals("0,0%")) {
                        System.out.println(att.getCpu());

                    }
                    System.out.println(att.getRamDisp());
                    System.out.println(att.getRamTotal());
                    System.out.println(att.getDisco());

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        timer.scheduleAtFixedRate(tarefa, time, time);

        Dashboard listar = new Dashboard();
        System.out.println(listar.runPid());
        System.out.println("===========");
        System.out.println(listar.runThread());

//          java.awt.EventQueue.invokeLater(new Runnable() 
//            public void run() {
//                new Processo().setVisible(true);
//            }
//        });
    }

}

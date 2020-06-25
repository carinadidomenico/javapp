///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.mycompany.projeto_moss;
//
//import java.awt.Point;
//import java.util.Timer;
//import java.util.TimerTask;
//
///**
// *
// * @author Aluno
// */
//public class Processo {
//
//    private Point point = new Point();
//
//    public static void main(String[] args) {
//        int delay = 0;
//        int interval = 60000;
//        Dashboard att = new Dashboard();
//
//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            public void run() {
//                try {
//                    att.InsertDadosMonitoracao(att);
//                    
//
//                    System.out.println("Sistema Operacional:");
//                    System.out.println(att.getSo());
//                    System.out.println("-----");
//                    System.out.println("Processo:");
//                    System.out.println(att.getProcesso());
//                    System.out.println("-----");
//
//                    System.out.println("Uso CPU");
//                    System.out.println(att.getCpu());
//                    System.out.println("-----");
//                    System.out.println("Se o uso CPU for != 0%:");
//                    if (!att.getCpu().equals("0,0%")) {
//                        System.out.println(att.getCpu());
//
//                    }
//                    System.out.println("------");
//                    System.out.println("RAM Disponível");
//                    System.out.println(att.getRamDisp());
//                    System.out.println("------");
//                    System.out.println("RAM Total:");
//                    System.out.println(att.getRamTotal());
//                    System.out.println("------");
//                    System.out.println("Disco: ");
//                    System.out.println(att.getDisco());
//                    System.out.println("-----");
//
//                } catch (Exception e) {
//                    System.out.println("Não sei:");
//                    e.printStackTrace();
//                    System.out.println("------");
//                }
//
//            }
//        }, delay, interval);
//
//        Dashboard listar = new Dashboard();
//        System.out.println("Run Pid:");
//        System.out.println(listar.runPid());
//        System.out.println("===========");
//        System.out.println("Run Thread:");
//        System.out.println(listar.runThread());
//
//
//    }
//
//}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Log.TXT;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Giulianno Menegasse
 */
public class Log {
    
    private static void verifyExistence(File arquivo) {
       
    File directory = new File ("./MOSS");
    boolean drExists = directory.exists();
    boolean arqExists = arquivo.exists();
 
    
    try{
        if(!drExists) {
            directory.mkdir();  
        }
           
    if (!arqExists) {
        
        arquivo.createNewFile();

    }else{
        arquivo.createNewFile();
    }
    
    }catch (IOException erro1){
      System.out.println("Erro na Classe Log: " + erro1.getMessage());
    }
     
   }

public static void writeLog(String Message) {
    
    try{
        File arquivo = new File("./MOSS/Moss.txt");
       
        Format formater = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date data = new Date();
        String logDados = formater.format(data);
        
        verifyExistence(arquivo);


        FileWriter fileWriter = new FileWriter(arquivo, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        
        bufferedWriter.write("" + logDados + " | " + Message);
        bufferedWriter.newLine();
        
        bufferedWriter.close();
        fileWriter.close();
     
    }catch (IOException erro2){
        System.out.println(erro2.getMessage());
    }
}

}


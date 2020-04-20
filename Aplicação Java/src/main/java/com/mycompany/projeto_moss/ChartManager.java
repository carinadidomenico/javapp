/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projeto_moss;

import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author PICHAU
 */
public class ChartManager {

    public XYDataset createDataset() {
        
        DefaultXYDataset ds = new DefaultXYDataset();
        
        double[][] data = {{0.1, 0.2, 0.4}, {1, 2, 3}};
        
        ds.addSeries("series1", data);
        
        return ds;
    }
}

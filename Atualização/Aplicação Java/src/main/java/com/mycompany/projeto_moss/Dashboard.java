/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projeto_moss;

import Banco.JDBC.Connection;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.util.Arrays;
import java.util.List;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import oshi.*;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HWDiskStore;
import oshi.software.os.OperatingSystem.ProcessSort;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;
import oshi.software.os.OSProcess;
import Log.TXT.Log;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.jdbc.core.JdbcTemplate;
import oshi.hardware.GlobalMemory;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.util.FormatUtil;
import oshi.util.Util;

/**
 *
 * @author HENRIQUE
 */
public class Dashboard extends javax.swing.JFrame {
    
    
    private static int posX;
    private static int posY;
    private static Dashboard dashboard;
    private static JFreeChart chart;
    private static String informacoes = "";
    private static String processos = "";

    private String so;
    private String processador;
    private String ramTotal;
    private String ramDisp;
    private String cpu;
    private String disco;
    private int delay = 0;   // tempo de espera antes da 1ª execução da tarefa.
    private int interval = 2000;  // intervalo no qual a tarefa será executada.
    

    private SystemInfo si;
    private HardwareAbstractionLayer hal;
    private OperatingSystem os;
    Timer timer = new Timer();
    Connection con = new Connection();
    JdbcTemplate template = new JdbcTemplate(con.getDataSource());

    /**
     * Creates new form Dashboard
     */
    
    public Dashboard() {

        initComponents();
        Log.writeLog("Iniciando Monitoramento de Dados...");

        setColor(btn_3);
        ind_3.setOpaque(true);

        si = new SystemInfo();
        hal = si.getHardware();
        os = si.getOperatingSystem();

        // atribuindo informações do sistema operacional
        lbl_so.setText(os.getFamily());
        lbl_version.setText(os.getVersion().getVersion() + " build " + os.getVersion().getBuildNumber());
        lbl_arq.setText(os.getBitness() + " bits");

        // atribuindo informações do processador
        lbl_processor.setText(hal.getProcessor().getName());
        lbl_family.setText(hal.getProcessor().getFamily());
        lbl_threads.setText(hal.getProcessor().getVendor());
        lbl_freq.setText((hal.getProcessor().getMaxFreq() * 0.000000001) + " GHz");

        // sensores de temperatura
        lbl_temp.setText(String.valueOf(hal.getSensors().getCpuTemperature()));
        lbl_volt.setText(String.valueOf(hal.getSensors().getCpuVoltage()));
        lbl_vel.setText(hal.getSensors().getFanSpeeds().length + "");

        // informações da memória
        lbl_memDisp.setText(Long.toString(hal.getMemory().getAvailable()).substring(0, 4) + " MB");
        lbl_memPag.setText(hal.getMemory().getPageSize() + " MB");
        lbl_memTot.setText(Long.toString(hal.getMemory().getTotal()).substring(0, 4) + " MB");
        hal.getMemory().getPhysicalMemory();
        hal.getMemory().getVirtualMemory();

        // informações do disco
        HWDiskStore[] discos = hal.getDiskStores();
        System.out.println(discos[0]);

        for (int x = 0; x < discos.length; x++) {
            JLabel lbl_diskData = new javax.swing.JLabel();

            lbl_diskData.setFont(new java.awt.Font("Segoe UI", 0, 12));
            lbl_diskData.setForeground(new java.awt.Color(255, 255, 255));
            lbl_diskData.setText(discos[x].getModel());
            jp_discos.add(lbl_diskData);
            jp_discos.updateUI();
        }

        // informações página Processos
//        Dashboard att = new Dashboard();
//        lblLabelProcessos.setText(att.getProcesso());

        // definindo visibilidade dos painéis
        jp_processos.setVisible(false);
        jp_recursos.setVisible(false);
        jp_hardware.setVisible(true);
        //inserts no banco de dados 
        Log.writeLog("Insert Dados Fixos");
        Log.writeLog("Inserindo Dados Fixos da maquina no banco de Dados");
//        template.update("INSERT INTO MAQUINAFIXO (memoriaTotal,sistemaOperacional,processador) VALUES (?,?,?)",(Long.toString(hal.getMemory().getTotal()).substring(0, 4)), (os.getFamily()), (hal.getProcessor().getName()));
//        List todasOcorrencias = template.queryForList("SELECT *FROM MAQUINAFIXO ;");
//        template.update("INSERT INTO DADOSMONITORACAO (cpu,disco,memoriaUso ) VALUES (?,?,?)", cpu, 0, ramDisp);
//        List todasMonitora = template.queryForList("SELECT *FROM DADOSMONITORACAO;");
//        System.out.println(todasMonitora);
//        System.out.println(todasOcorrencias);
        //Monitorando 
        this.so = (String.valueOf(os));

        CentralProcessor cp = hal.getProcessor();
        this.processador = cp.toString();

        GlobalMemory memory = hal.getMemory();
        this.ramTotal = FormatUtil.formatBytes(memory.getTotal());
        long tot = memory.getTotal();
        long disp = memory.getAvailable();
        this.ramDisp = String.format("%.1f%%", 100 - (100d * disp / tot));

        long[] prevTicks = cp.getSystemCpuLoadTicks();
        Util.sleep(2000);
        this.cpu = String.format("%.1f%%", cp.getSystemCpuLoadBetweenTicks(prevTicks) * 100);
        FileSystem fileSystem = os.getFileSystem();
        OSFileStore[] fsArray = fileSystem.getFileStores();
        String hdUsando = "";
        for (OSFileStore fs : fsArray) {
            long usable = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            hdUsando = (String.format("%.1f%%",
                    100 - (100d * usable / total)));
        }
        this.disco = hdUsando;
    }

    public String getSo() {
        return so;
    }

    public String getProcesso() {
        return processador;
    }

    public String getRamTotal() {
        return ramTotal;
    }

    public String getRamDisp() {
        return ramDisp;
    }

    public String getCpu() {
        return cpu;
    }

    public String getDisco() {
        return disco;
    }

    public String runPid() {
        listarProcessos(os, hal.getMemory());
        return informacoes;
    }

    public String runThread() {
        contarProcessos(os, hal.getMemory());
        return processos;
    }

    private static void listarProcessos(OperatingSystem os, GlobalMemory memory) {
        Log.writeLog("Listando Processos...");

        List<OSProcess> procs = Arrays.asList(os.getProcesses(20, OperatingSystem.ProcessSort.CPU));

        for (int i = 0; i < procs.size() && i < 20; i++) {
            OSProcess p = procs.get(i);
            informacoes += (String.format("%5d              %5.1f                 %4.1f              %9s             %9s                 %s", p.getProcessID(),
                    100d * (p.getKernelTime() + p.getUserTime()) / p.getUpTime(),
                    100d * p.getResidentSetSize() / memory.getTotal(), FormatUtil.formatBytes(p.getVirtualSize()),
                    FormatUtil.formatBytes(p.getResidentSetSize()), p.getName()));
            informacoes += "\n";

        }
        informacoes += ("\n");
    }

    private static void contarProcessos(OperatingSystem os, GlobalMemory memory) {
        processos += ("\n Processos: " + os.getProcessCount() + ",      Threads: " + os.getThreadCount());
    }

    public void checarComponentes(Dashboard att) {
        Log.writeLog("Checando componentes");
        Double cpu = Double.valueOf(att.getCpu().substring(0, 3).replaceAll(",", "."));
        Double ram = Double.valueOf(att.getRamDisp().substring(0, 4).replaceAll(",", "."));
        Double disco = Double.valueOf(att.getDisco().substring(0, 3).replaceAll(",", "."));

        if (cpu >= 80) {

        }
    }

    public void InsertDadosFixos(Dashboard att) {
         //inserts no banco de dados 
        
        Log.writeLog("Inserindo Dados Fixos da maquina no banco de Dados");
       template.update("INSERT INTO MAQUINAFIXO (memoriaTotal,sistemaOperacional,processador) VALUES (?,?,?)",(Long.toString(hal.getMemory().getTotal()).substring(0, 4)), (os.getFamily()), (hal.getProcessor().getName()));
        List todasOcorrencias = template.queryForList("SELECT *FROM MAQUINAFIXO ;");
        System.out.println(todasOcorrencias);
        
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        side_pane = new javax.swing.JPanel();
        btn_3 = new javax.swing.JPanel();
        ind_3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        btn_4 = new javax.swing.JPanel();
        ind_4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jp_processos = new javax.swing.JPanel();
        lblProcessos = new javax.swing.JLabel();
        lblLabelProcessos = new javax.swing.JLabel();
        lblUsoCpu = new javax.swing.JLabel();
        lblUsoMemoria = new javax.swing.JLabel();
        lblMemoria = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        lblDisco = new javax.swing.JLabel();
        pgbCpu = new javax.swing.JProgressBar();
        pgbDisco = new javax.swing.JProgressBar();
        pgbRam = new javax.swing.JProgressBar();
        jp_hardware = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lbl_so = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbl_version = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbl_processor = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbl_family = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbl_threads = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lbl_freq = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbl_arq = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        lbl_temp = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lbl_volt = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lbl_vel = new javax.swing.JLabel();
        lbl_memDisp = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        lbl_memTot = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lbl_memPag = new javax.swing.JLabel();
        jp_discos = new javax.swing.JPanel();
        jp_recursos = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(32, 32, 32));
        setResizable(false);
        addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                formComponentAdded(evt);
            }
        });
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        side_pane.setBackground(new java.awt.Color(32, 32, 32));
        side_pane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_3.setBackground(new java.awt.Color(32, 32, 32));
        btn_3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_3MousePressed(evt);
            }
        });

        ind_3.setOpaque(false);
        ind_3.setPreferredSize(new java.awt.Dimension(3, 43));

        javax.swing.GroupLayout ind_3Layout = new javax.swing.GroupLayout(ind_3);
        ind_3.setLayout(ind_3Layout);
        ind_3Layout.setHorizontalGroup(
            ind_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        ind_3Layout.setVerticalGroup(
            ind_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
        );

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Sistema e Hardware");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout btn_3Layout = new javax.swing.GroupLayout(btn_3);
        btn_3.setLayout(btn_3Layout);
        btn_3Layout.setHorizontalGroup(
            btn_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_3Layout.createSequentialGroup()
                .addComponent(ind_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btn_3Layout.setVerticalGroup(
            btn_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_3Layout.createSequentialGroup()
                .addComponent(ind_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(btn_3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        side_pane.add(btn_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 140, -1));

        btn_4.setBackground(new java.awt.Color(32, 32, 32));
        btn_4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_4MousePressed(evt);
            }
        });

        ind_4.setOpaque(false);
        ind_4.setPreferredSize(new java.awt.Dimension(3, 43));

        javax.swing.GroupLayout ind_4Layout = new javax.swing.GroupLayout(ind_4);
        ind_4.setLayout(ind_4Layout);
        ind_4Layout.setHorizontalGroup(
            ind_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        ind_4Layout.setVerticalGroup(
            ind_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
        );

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Processos");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel11MousePressed(evt);
            }
        });

        javax.swing.GroupLayout btn_4Layout = new javax.swing.GroupLayout(btn_4);
        btn_4.setLayout(btn_4Layout);
        btn_4Layout.setHorizontalGroup(
            btn_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_4Layout.createSequentialGroup()
                .addComponent(ind_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jLabel11)
                .addContainerGap(46, Short.MAX_VALUE))
        );
        btn_4Layout.setVerticalGroup(
            btn_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_4Layout.createSequentialGroup()
                .addComponent(ind_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(btn_4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        side_pane.add(btn_4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 140, -1));
        side_pane.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        jPanel1.add(side_pane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 600));

        jp_processos.setBackground(new java.awt.Color(40, 40, 40));

        lblLabelProcessos.setFont(new java.awt.Font("Lucida Grande", 0, 30)); // NOI18N
        lblLabelProcessos.setForeground(new java.awt.Color(255, 255, 255));
        lblLabelProcessos.setText("Teste");

        lblUsoCpu.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        lblUsoCpu.setForeground(new java.awt.Color(255, 255, 255));
        lblUsoCpu.setText("CPU em Uso");

        lblUsoMemoria.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        lblUsoMemoria.setForeground(new java.awt.Color(255, 255, 255));
        lblUsoMemoria.setText("RAM em Uso");

        lblMemoria.setFont(new java.awt.Font("Lucida Grande", 0, 30)); // NOI18N
        lblMemoria.setForeground(new java.awt.Color(255, 255, 255));
        lblMemoria.setText("Teste");

        jLabel22.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Disco em Uso");

        lblDisco.setFont(new java.awt.Font("Lucida Grande", 0, 30)); // NOI18N
        lblDisco.setForeground(new java.awt.Color(255, 255, 255));
        lblDisco.setText("Teste");

        pgbCpu.setString("");
        pgbCpu.setStringPainted(true);

        javax.swing.GroupLayout jp_processosLayout = new javax.swing.GroupLayout(jp_processos);
        jp_processos.setLayout(jp_processosLayout);
        jp_processosLayout.setHorizontalGroup(
            jp_processosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_processosLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jp_processosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jp_processosLayout.createSequentialGroup()
                        .addComponent(lblUsoMemoria)
                        .addGap(34, 34, 34)
                        .addComponent(lblMemoria, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pgbCpu, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pgbRam, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jp_processosLayout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(18, 18, 18)
                        .addComponent(lblDisco, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pgbDisco, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jp_processosLayout.createSequentialGroup()
                        .addComponent(lblUsoCpu)
                        .addGap(44, 44, 44)
                        .addComponent(lblLabelProcessos, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblProcessos, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jp_processosLayout.setVerticalGroup(
            jp_processosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_processosLayout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(lblProcessos, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp_processosLayout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addGroup(jp_processosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsoCpu)
                    .addComponent(lblLabelProcessos, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(pgbCpu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jp_processosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(lblDisco, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pgbDisco, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(jp_processosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsoMemoria)
                    .addComponent(lblMemoria, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addComponent(pgbRam, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jPanel1.add(jp_processos, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, 1180, 600));

        jp_hardware.setBackground(new java.awt.Color(40, 40, 40));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("INFORMAÇÕES DO SISTEMA");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Sistema Operacional: ");

        lbl_so.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_so.setForeground(new java.awt.Color(255, 255, 255));
        lbl_so.setText("lbl_so");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("INFORMAÇÕES DO HARDWARE");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Versão:");

        lbl_version.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_version.setForeground(new java.awt.Color(255, 255, 255));
        lbl_version.setText("lbl_version");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Processador:");

        lbl_processor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_processor.setForeground(new java.awt.Color(255, 255, 255));
        lbl_processor.setText("lbl_processor");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Família:");

        lbl_family.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_family.setForeground(new java.awt.Color(255, 255, 255));
        lbl_family.setText("lbl_family");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Fabricante:");

        lbl_threads.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_threads.setForeground(new java.awt.Color(255, 255, 255));
        lbl_threads.setText("lbl_threads");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Frequência Máxima:");

        lbl_freq.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_freq.setForeground(new java.awt.Color(255, 255, 255));
        lbl_freq.setText("lbl_freq");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Arquitetura:");

        lbl_arq.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_arq.setForeground(new java.awt.Color(255, 255, 255));
        lbl_arq.setText("lbl_arq");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("INFORMAÇÕES DA MEMÓRIA");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("INFORMAÇÕES DO DISCO");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Temperatura CPU:");

        lbl_temp.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_temp.setForeground(new java.awt.Color(255, 255, 255));
        lbl_temp.setText("lbl_temp");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Voltagem CPU:");

        lbl_volt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_volt.setForeground(new java.awt.Color(255, 255, 255));
        lbl_volt.setText("lbl_volt");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Velocidade do Cooler:");

        lbl_vel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_vel.setForeground(new java.awt.Color(255, 255, 255));
        lbl_vel.setText("lbl_vel");

        lbl_memDisp.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_memDisp.setForeground(new java.awt.Color(255, 255, 255));
        lbl_memDisp.setText("lbl_memDisp");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Memória Disponível:");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Memória Total:");

        lbl_memTot.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_memTot.setForeground(new java.awt.Color(255, 255, 255));
        lbl_memTot.setText("lbl_memTot");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Memória Paginada:");

        lbl_memPag.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_memPag.setForeground(new java.awt.Color(255, 255, 255));
        lbl_memPag.setText("lbl_memPag");

        jp_discos.setBackground(new java.awt.Color(40, 40, 40));

        javax.swing.GroupLayout jp_hardwareLayout = new javax.swing.GroupLayout(jp_hardware);
        jp_hardware.setLayout(jp_hardwareLayout);
        jp_hardwareLayout.setHorizontalGroup(
            jp_hardwareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_hardwareLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jp_hardwareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jp_hardwareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jSeparator1)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jp_hardwareLayout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lbl_so)))
                    .addGroup(jp_hardwareLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_version))
                    .addGroup(jp_hardwareLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_arq))
                    .addGroup(jp_hardwareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jp_hardwareLayout.createSequentialGroup()
                            .addComponent(jLabel20)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lbl_memTot))
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator3))
                    .addGroup(jp_hardwareLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_memDisp))
                    .addGroup(jp_hardwareLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_memPag)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                .addGroup(jp_hardwareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jp_hardwareLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_vel))
                    .addGroup(jp_hardwareLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_volt))
                    .addGroup(jp_hardwareLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_temp))
                    .addGroup(jp_hardwareLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_family))
                    .addGroup(jp_hardwareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jSeparator2)
                        .addComponent(jLabel4))
                    .addGroup(jp_hardwareLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_processor))
                    .addGroup(jp_hardwareLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_threads))
                    .addGroup(jp_hardwareLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_freq))
                    .addGroup(jp_hardwareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jSeparator4)
                        .addComponent(jLabel15))
                    .addComponent(jp_discos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(289, 289, 289))
        );
        jp_hardwareLayout.setVerticalGroup(
            jp_hardwareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_hardwareLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jp_hardwareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jp_hardwareLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jp_hardwareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(lbl_processor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jp_hardwareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(lbl_family))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jp_hardwareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(lbl_threads))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jp_hardwareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(lbl_freq)))
                    .addGroup(jp_hardwareLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jp_hardwareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(lbl_so))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jp_hardwareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(lbl_version))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jp_hardwareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(lbl_arq))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jp_hardwareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(lbl_temp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jp_hardwareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(lbl_volt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jp_hardwareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(lbl_vel))
                .addGap(42, 42, 42)
                .addGroup(jp_hardwareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jp_hardwareLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jp_hardwareLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jp_hardwareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jp_discos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jp_hardwareLayout.createSequentialGroup()
                        .addGroup(jp_hardwareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(lbl_memTot))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jp_hardwareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(lbl_memDisp))
                        .addGap(11, 11, 11)
                        .addGroup(jp_hardwareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(lbl_memPag))))
                .addContainerGap(147, Short.MAX_VALUE))
        );

        jPanel1.add(jp_hardware, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, 1180, 600));

        jp_recursos.setBackground(new java.awt.Color(40, 40, 40));

        javax.swing.GroupLayout jp_recursosLayout = new javax.swing.GroupLayout(jp_recursos);
        jp_recursos.setLayout(jp_recursosLayout);
        jp_recursosLayout.setHorizontalGroup(
            jp_recursosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1180, Short.MAX_VALUE)
        );
        jp_recursosLayout.setVerticalGroup(
            jp_recursosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        jPanel1.add(jp_recursos, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, 1180, 600));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_formComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentAdded

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
//        posX = getX();
//        posY = getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
//        Rectangle rectangle = getBounds();
//        
//        dashboard.setBounds(evt.getXOnScreen() - posX, evt.getYOnScreen() - posY, rectangle.width, rectangle.height);
    }//GEN-LAST:event_formMouseDragged

    private void btn_3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_3MousePressed
        // TODO add your handling code here:
        setColor(btn_3);
        ind_3.setOpaque(true);
        resetColor(new JPanel[]{btn_4}, new JPanel[]{ind_4});
        panelsVisibility(2);
    }//GEN-LAST:event_btn_3MousePressed

    private void btn_4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_4MousePressed
        // TODO add your handling code here:
        setColor(btn_4);
        ind_4.setOpaque(true);
        resetColor(new JPanel[]{btn_3}, new JPanel[]{ind_3});
        panelsVisibility(3);
    }//GEN-LAST:event_btn_4MousePressed

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        // TODO add your handling code here:
        setColor(btn_3);
        ind_3.setOpaque(true);
        resetColor(new JPanel[]{btn_4}, new JPanel[]{ind_4});
        panelsVisibility(2);
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MousePressed
        // TODO add your handling code here:
        Dashboard att2 = new Dashboard();
         //inserts no banco de dados 
        att2.InsertDadosFixos(att2);
     
    timer.scheduleAtFixedRate(new TimerTask() {
      public void run() {
            
    
        setColor(btn_4);
        ind_4.setOpaque(true);
        resetColor(new JPanel[]{btn_3}, new JPanel[]{ind_3});
        panelsVisibility(3);
        Log.writeLog("Iniciando a aba Processos");
        Dashboard att = new Dashboard();
        Long cpuTotal = hal.getProcessor().getMaxFreq();
//        lblUsoCpuTotal.setText(Long.toString(cpuTotal).substring(0,2) + " Ghz");
        lblLabelProcessos.setText(att.getCpu());
        lblDisco.setText(att.getDisco());
//        lblPainelProcessos.setText(att.runPid());
        Double memTotal = (hal.getMemory().getTotal()) * 0.00001;
        Double memDisp = (hal.getMemory().getAvailable()) * 0.00001;
        Double memUso = memTotal - memDisp;
        lblMemoria.setText(att.getRamDisp());
//        lblProcessoMemTotal.setText(Double.toString(memTotal).substring(0,4) + " MB");
//        lblProcMemUso.setText(Double.toString(memUso).substring(0,4) + " MB");
        CentralProcessor cp = hal.getProcessor();
        long[] prevTicks = cp.getSystemCpuLoadTicks();
        Util.sleep(2000);
        Double valorCpu = cp.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
        Integer valorCpuNumber = valorCpu.intValue();
        pgbCpu.setValue(valorCpuNumber);
        pgbCpu.setMinimum(0);
        pgbCpu.setMaximum(100);
        
        FileSystem fileSystem = os.getFileSystem();
        OSFileStore[] fsArray = fileSystem.getFileStores();
        Double hdUsando = 0.0;
        for (OSFileStore fs : fsArray) {
            long usable = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            hdUsando = (100 - (100d * usable / total));
        };
        Integer discoUsando = hdUsando.intValue();
        pgbDisco.setValue(discoUsando);
        pgbDisco.setMinimum(0);
        pgbDisco.setMaximum(100);
        
        GlobalMemory memory = hal.getMemory();
        ramTotal = FormatUtil.formatBytes(memory.getTotal());
        long tot = memory.getTotal();
        long disp = memory.getAvailable();
        Double ramDisp = (100 - (100d * disp / tot));
        Integer ramDispon = ramDisp.intValue();
        pgbRam.setValue(ramDispon);
        pgbRam.setMinimum(0);
        pgbRam.setMaximum(100);
        Log.writeLog("Insert na tabela Dados Monitoração");
        template.update("INSERT INTO DADOSMONITORACAO (cpu,disco,memoriaUso ) VALUES (?,?,?)", valorCpuNumber,discoUsando, ramDispon);
        List todasMonitora = template.queryForList("SELECT *FROM DADOSMONITORACAO;");
        System.out.println(todasMonitora);
          }
    }, delay, interval);
    }//GEN-LAST:event_jLabel11MousePressed
      
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */

        dashboard = new Dashboard();

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                dashboard.setVisible(true);
            }
        });
    }

    private void setColor(JPanel pane) {
        pane.setBackground(new Color(50, 50, 50));
    }

    private void resetColor(JPanel[] pane, JPanel[] indicators) {
        for (int i = 0; i < pane.length; i++) {
            pane[i].setBackground(new Color(32, 32, 32));

        }
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setOpaque(false);
        }
    }

    private void panelsVisibility(int a) {
        if (a == 2) {
            jp_hardware.setVisible(true);
            jp_processos.setVisible(false);
            jp_recursos.setVisible(false);
        } else if (a == 3) {
            jp_processos.setVisible(true);
            jp_hardware.setVisible(false);
            jp_recursos.setVisible(false);
        } else if (a == 4) {
            jp_recursos.setVisible(true);
            jp_hardware.setVisible(false);
            jp_processos.setVisible(false);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btn_3;
    private javax.swing.JPanel btn_4;
    private javax.swing.JPanel ind_3;
    private javax.swing.JPanel ind_4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JPanel jp_discos;
    private javax.swing.JPanel jp_hardware;
    private javax.swing.JPanel jp_processos;
    private javax.swing.JPanel jp_recursos;
    private javax.swing.JLabel lblDisco;
    private javax.swing.JLabel lblLabelProcessos;
    private javax.swing.JLabel lblMemoria;
    private javax.swing.JLabel lblProcessos;
    private javax.swing.JLabel lblUsoCpu;
    private javax.swing.JLabel lblUsoMemoria;
    private javax.swing.JLabel lbl_arq;
    private javax.swing.JLabel lbl_family;
    private javax.swing.JLabel lbl_freq;
    private javax.swing.JLabel lbl_memDisp;
    private javax.swing.JLabel lbl_memPag;
    private javax.swing.JLabel lbl_memTot;
    private javax.swing.JLabel lbl_processor;
    private javax.swing.JLabel lbl_so;
    private javax.swing.JLabel lbl_temp;
    private javax.swing.JLabel lbl_threads;
    private javax.swing.JLabel lbl_vel;
    private javax.swing.JLabel lbl_version;
    private javax.swing.JLabel lbl_volt;
    private javax.swing.JProgressBar pgbCpu;
    private javax.swing.JProgressBar pgbDisco;
    private javax.swing.JProgressBar pgbRam;
    private javax.swing.JPanel side_pane;
    // End of variables declaration//GEN-END:variables
}

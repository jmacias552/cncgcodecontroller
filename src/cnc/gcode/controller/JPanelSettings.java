/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnc.gcode.controller;

import java.awt.Dimension;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author patrick
 */
public class JPanelSettings extends javax.swing.JPanel implements IGUIEvent{
    
    public static final String[] homeing= {"upper left","upper right","lower left","lower right" }; //0= upper left; 1= upper right; 2= lower left; 3= lower right;

    private IEvent GUIEvent=null;
    
    /**
     * Creates new form JPanelSettings
     */
    public JPanelSettings() {
        initComponents();
    }
    
    @Override
    public void setGUIEvent(IEvent event)
    {
        GUIEvent=event;
    }

    @Override
    public void updateGUI(boolean serial, boolean isworking)
    {
        jBImport.setEnabled(!isworking);
        //Akt Text
        jLSHomeing.setText(homeing[Integer.parseInt(Database.HOMEING.get())]); //Homeing
        jLSFastFeedrate.setText(Database.MAXFEEDRATE.get());
        jLSWorkSpace.setText("");
        for(int i=0; i<3;i++ )
            jLSWorkSpace.setText(jLSWorkSpace.getText() +CommandParsing.axesName[i]+" = "+ Database.getWorkspace(i)+"   ");        
        jLSCNCStart.setText(Tools.convertToMultiline(Database.STARTCODE.get()));
        jLSCNCToolChange.setText(Tools.convertToMultiline(Database.TOOLCHANGE.get()));
        jLSCNCSpindleON.setText(Tools.convertToMultiline(Database.SPINDLEON.get()));
        jLSCNCSpindleOFF.setText(Tools.convertToMultiline(Database.SPINDLEOFF.get()));
        jLSCNCG0Feedrate.setText(Database.GOFEEDRATE.get());
        jLSCNCToolSize.setText(Database.TOOLSIZE.get());
        jLSCNCOptimiserTime.setText(Database.OPTIMISATIONTIMEOUT.get());
        jLSALOptions.setText(Tools.convertToMultiline("Zero height: "+Database.ALZERO+
                "\nMax depth: "+Database.ALMAXPROPDEPTH+
                "\nSafe height: "+Database.ALSAVEHEIGHT+
                "\nClearence: "+Database.ALCLEARENCE+
                "\nFeedrate: "+Database.ALFEEDRATE));
        jLSALDistance.setText(Tools.convertToMultiline("Distance: "+Database.ALDISTANACE+       
                "\nMax XY Move Length: "+Database.ALMAXMOVELENGTH));
        jLSALStart.setText(Tools.convertToMultiline(Database.ALSTARTCODE.get()));
        jLSARC.setText(Database.ARCSEGMENTLENGTH.get());
        jLSBacklash.setText("");
        for(int i=0; i<3;i++ )
            jLSBacklash.setText(jLSBacklash.getText() +CommandParsing.axesName[i]+" = "+ Database.getBacklash(i)+"   ");        
    }
    
    private void fireupdateGUI()
    {
        if(GUIEvent==null)
            throw new RuntimeException("GUI EVENT NOT USED!");
        GUIEvent.fired();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jBSHomeing = new javax.swing.JButton();
        jLSHomeing = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jBSFastFeedrate = new javax.swing.JButton();
        jLSFastFeedrate = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jBSWorkSpace = new javax.swing.JButton();
        jLSWorkSpace = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jBSCNCStart = new javax.swing.JButton();
        jLSCNCStart = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jBSCNCToolChange = new javax.swing.JButton();
        jLSCNCToolChange = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jBSCNCSpindleON = new javax.swing.JButton();
        jLSCNCSpindleON = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jBSCNCSpindleOFF = new javax.swing.JButton();
        jLSCNCSpindleOFF = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jBSCNCG0Feedrate = new javax.swing.JButton();
        jLSCNCG0Feedrate = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jBSCNCToolSize = new javax.swing.JButton();
        jLSCNCToolSize = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jBSCNCOptimiserTime = new javax.swing.JButton();
        jLSCNCOptimiserTime = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jBSALOptions = new javax.swing.JButton();
        jLSALOptions = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jBSALDistance = new javax.swing.JButton();
        jLSALDistance = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jBSALStart = new javax.swing.JButton();
        jLSALStart = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jBSARC = new javax.swing.JButton();
        jLSARC = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jBSBacklash = new javax.swing.JButton();
        jLSBacklash = new javax.swing.JLabel();
        jBexport = new javax.swing.JButton();
        jBImport = new javax.swing.JButton();

        jLabel2.setText("Homeing:");

        jBSHomeing.setText("Change");
        jBSHomeing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSettingsActionPerformed(evt);
            }
        });

        jLSHomeing.setText("Settings Text");

        jLabel19.setText("Fast Move Feedrate:");

        jBSFastFeedrate.setText("Change");
        jBSFastFeedrate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSettingsActionPerformed(evt);
            }
        });

        jLSFastFeedrate.setText("Settings Text");

        jLabel20.setText("Size of Workingspace:");

        jBSWorkSpace.setText("Change");
        jBSWorkSpace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSettingsActionPerformed(evt);
            }
        });

        jLSWorkSpace.setText("Settings Text");

        jLabel21.setText("CNC/StartGCode");

        jBSCNCStart.setText("Change");
        jBSCNCStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSettingsActionPerformed(evt);
            }
        });

        jLSCNCStart.setText("Settings Text");

        jLabel32.setText("CNC/Tool Change:");

        jBSCNCToolChange.setText("Change");
        jBSCNCToolChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSettingsActionPerformed(evt);
            }
        });

        jLSCNCToolChange.setText("Settings Text");

        jLabel35.setText("CNC/Spindle ON:");

        jBSCNCSpindleON.setText("Change");
        jBSCNCSpindleON.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSettingsActionPerformed(evt);
            }
        });

        jLSCNCSpindleON.setText("Settings Text");

        jLabel36.setText("CNC/Spindle OFF:");

        jBSCNCSpindleOFF.setText("Change");
        jBSCNCSpindleOFF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSettingsActionPerformed(evt);
            }
        });

        jLSCNCSpindleOFF.setText("Settings Text");

        jLabel33.setText("CNC/G0 Feedrate:");

        jBSCNCG0Feedrate.setText("Change");
        jBSCNCG0Feedrate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSettingsActionPerformed(evt);
            }
        });

        jLSCNCG0Feedrate.setText("Settings Text");

        jLabel34.setText("CNC/Paint Tool Size:");

        jBSCNCToolSize.setText("Change");
        jBSCNCToolSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSettingsActionPerformed(evt);
            }
        });

        jLSCNCToolSize.setText("Settings Text");

        jLabel39.setText("CNC/Optimise Time:");

        jBSCNCOptimiserTime.setText("Change");
        jBSCNCOptimiserTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSettingsActionPerformed(evt);
            }
        });

        jLSCNCOptimiserTime.setText("Settings Text");

        jLabel37.setText("Autolevel/Options:");

        jBSALOptions.setText("Change");
        jBSALOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSettingsActionPerformed(evt);
            }
        });

        jLSALOptions.setText("Settings Text");

        jLabel41.setText("Autolevel/Distance:");

        jBSALDistance.setText("Change");
        jBSALDistance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSettingsActionPerformed(evt);
            }
        });

        jLSALDistance.setText("Settings Text");

        jLabel38.setText("Autolevel/Start GCode:");

        jBSALStart.setText("Change");
        jBSALStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSettingsActionPerformed(evt);
            }
        });

        jLSALStart.setText("Settings Text");

        jLabel40.setText("ARC/ Max Sigment Length:");

        jBSARC.setText("Change");
        jBSARC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSettingsActionPerformed(evt);
            }
        });

        jLSARC.setText("Settings Text");

        jLabel42.setText("Backlash Correction:");

        jBSBacklash.setText("Change");
        jBSBacklash.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSettingsActionPerformed(evt);
            }
        });

        jLSBacklash.setText("Settings Text");

        jBexport.setText("Export");
        jBexport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBexportActionPerformed(evt);
            }
        });

        jBImport.setText("Import");
        jBImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBImportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39)
                            .addComponent(jLabel2)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20)
                            .addComponent(jLabel32)
                            .addComponent(jLabel35)
                            .addComponent(jLabel36)
                            .addComponent(jLabel33)
                            .addComponent(jLabel34)
                            .addComponent(jLabel21)
                            .addComponent(jLabel37)
                            .addComponent(jLabel38)
                            .addComponent(jLabel41)
                            .addComponent(jLabel40)
                            .addComponent(jLabel42))
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jBSALDistance)
                                .addGap(50, 50, 50)
                                .addComponent(jLSALDistance))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jBSCNCStart)
                                    .addComponent(jBSCNCOptimiserTime)
                                    .addComponent(jBSCNCToolChange)
                                    .addComponent(jBSALStart)
                                    .addComponent(jBSCNCSpindleOFF)
                                    .addComponent(jBSCNCSpindleON)
                                    .addComponent(jBSALOptions)
                                    .addComponent(jBSHomeing)
                                    .addComponent(jBSCNCToolSize)
                                    .addComponent(jBSCNCG0Feedrate)
                                    .addComponent(jBSFastFeedrate)
                                    .addComponent(jBSWorkSpace)
                                    .addComponent(jBSARC)
                                    .addComponent(jBSBacklash))
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLSBacklash)
                                    .addComponent(jLSARC)
                                    .addComponent(jLSCNCToolSize)
                                    .addComponent(jLSCNCOptimiserTime)
                                    .addComponent(jLSALOptions)
                                    .addComponent(jLSCNCStart)
                                    .addComponent(jLSFastFeedrate)
                                    .addComponent(jLSWorkSpace)
                                    .addComponent(jLSCNCToolChange)
                                    .addComponent(jLSALStart)
                                    .addComponent(jLSCNCSpindleON)
                                    .addComponent(jLSCNCSpindleOFF)
                                    .addComponent(jLSHomeing)
                                    .addComponent(jLSCNCG0Feedrate)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBexport)
                        .addGap(18, 18, 18)
                        .addComponent(jBImport)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBSHomeing)
                    .addComponent(jLabel2)
                    .addComponent(jLSHomeing))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jBSFastFeedrate)
                    .addComponent(jLSFastFeedrate))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jBSWorkSpace)
                    .addComponent(jLSWorkSpace))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jBSCNCStart)
                    .addComponent(jLSCNCStart))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jBSCNCToolChange)
                    .addComponent(jLSCNCToolChange))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(jBSCNCSpindleON)
                    .addComponent(jLSCNCSpindleON))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(jBSCNCSpindleOFF)
                    .addComponent(jLSCNCSpindleOFF))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jBSCNCG0Feedrate)
                    .addComponent(jLSCNCG0Feedrate))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(jBSCNCToolSize)
                    .addComponent(jLSCNCToolSize))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(jBSCNCOptimiserTime)
                    .addComponent(jLSCNCOptimiserTime))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(jBSALOptions)
                    .addComponent(jLSALOptions))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBSALDistance)
                    .addComponent(jLSALDistance)
                    .addComponent(jLabel41))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(jLSALStart)
                    .addComponent(jBSALStart))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(jLSARC)
                    .addComponent(jBSARC))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(jLSBacklash)
                    .addComponent(jBSBacklash))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBexport)
                    .addComponent(jBImport))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSettingsActionPerformed

        //HOMEING
        if(evt.getSource()==jBSHomeing)
        {
            int options= JOptionPane.showOptionDialog(this, "Select homeing corner", "Homeing", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, homeing,0);
            if(options!=JOptionPane.CLOSED_OPTION)
            Database.HOMEING.set(""+options);
        }

        //MAXFEEDRATE
        if(evt.getSource()==jBSFastFeedrate)
        {
            Double[] d=Tools.getValues(new String[]{"Set the Feetrate for the fast move:"}, new Double[]{Database.MAXFEEDRATE.getsaved()}, new Double[]{Double.MAX_VALUE}, new Double[]{0.0});
            if(d!= null) Database.MAXFEEDRATE.set(Tools.dtostr(d[0]));
        }

        //WORKINGSPACE
        if(evt.getSource()==jBSWorkSpace)
        {
            Double[] values = new Double[3];
            String[] messages= new String[3];

            for(int i=0; i<3;i++ )
            {
                values[i] = Database.getWorkspace(i).getsaved();
                messages[i] = "Set Size for the "+CommandParsing.axesName[i]+" axis";
            }
            values= Tools.getValues(messages, values, new Double[]{Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE}, new Double[]{0.0,0.0,0.0});

            if(values!= null)
            for(int i=0; i<3;i++ )
                Database.getWorkspace(i).set(Tools.dtostr(values[i]));
        }
        
        //StartCode
        if(evt.getSource()==jBSCNCStart)
        {
            JTextArea textArea = new JTextArea(Database.STARTCODE.get()); 
            JScrollPane scrollArea = new JScrollPane(textArea); 
            scrollArea.setPreferredSize(new Dimension(100, 100));

            if(JOptionPane.showConfirmDialog
                    (
                    this,
                    new Object[]{"Enter the commands which will be executed when milling is started:", scrollArea},
                    "Tool change:",
                    JOptionPane.OK_CANCEL_OPTION
                    )
                    ==JOptionPane.OK_OPTION)
                Database.STARTCODE.set(textArea.getText().trim());
        }

        //Toolchange
        if(evt.getSource()==jBSCNCToolChange)
        {
            JTextArea textArea = new JTextArea(Database.TOOLCHANGE.get()); 
            JScrollPane scrollArea = new JScrollPane(textArea); 
            scrollArea.setPreferredSize(new Dimension(100, 100));

            if(JOptionPane.showConfirmDialog
                    (
                    this,
                    new Object[]{"Enter the commands to chnage the Tool:", scrollArea,"Hint: '?' will be replaced with the Tool Number"},
                    "Tool change:",
                    JOptionPane.OK_CANCEL_OPTION
                    )
                    ==JOptionPane.OK_OPTION)
                Database.TOOLCHANGE.set(textArea.getText().trim());
        }
        
        //Spindle ON
        if(evt.getSource()==jBSCNCSpindleON)
        {
            JTextArea textArea = new JTextArea(Database.SPINDLEON.get()); 
            JScrollPane scrollArea = new JScrollPane(textArea); 
            scrollArea.setPreferredSize(new Dimension(100, 100));

            if(JOptionPane.showConfirmDialog
                    (
                    this,
                    new Object[]{"Enter the commands to turn the spindle on:", scrollArea,"Hint: '?' will be replaced with the Origenal Command Number!"},
                    "Spindle ON:",
                    JOptionPane.OK_CANCEL_OPTION
                    )
                    ==JOptionPane.OK_OPTION)
                Database.SPINDLEON.set(textArea.getText().trim());
        }

        //Spindle OFF
        if(evt.getSource()==jBSCNCSpindleOFF)
        {
            JTextArea textArea = new JTextArea(Database.SPINDLEOFF.get()); 
            JScrollPane scrollArea = new JScrollPane(textArea); 
            scrollArea.setPreferredSize(new Dimension(100, 100));

            if(JOptionPane.showConfirmDialog
                    (
                    this,
                    new Object[]{"Enter the commands to turn the spindle off:", scrollArea},
                    "Spindle OFF:",
                    JOptionPane.OK_CANCEL_OPTION
                    )
                    ==JOptionPane.OK_OPTION)
                Database.SPINDLEOFF.set(textArea.getText().trim());
        }
        
        
        //G0Feedrate
        if(evt.getSource()==jBSCNCG0Feedrate)
        {
            Double[] d=Tools.getValues(new String[]{"Set the Feetrate for the G0 move:"}, new Double[]{Database.GOFEEDRATE.getsaved()}, new Double[]{Database.MAXFEEDRATE.getsaved()}, new Double[]{0.0});
            if(d!= null) Database.GOFEEDRATE.set(Tools.dtostr(d[0]));
        }
        
        //Tooldiameter
        if(evt.getSource()==jBSCNCToolSize)
        {
            Double[] d=Tools.getValues(new String[]{"Set the Toolsize for CNC Milling Simulation:"}, new Double[]{Database.TOOLSIZE.getsaved()}, new Double[]{Double.MAX_VALUE}, new Double[]{0.0});
            if(d!= null) Database.TOOLSIZE.set(Tools.dtostr(d[0]));
        }
        
        //Tooldiameter
        if(evt.getSource()==jBSCNCOptimiserTime)
        {
            Double[] d=Tools.getValues(new String[]{"Set the Timeout in Secounds for Optimising:"}, new Double[]{Database.OPTIMISATIONTIMEOUT.getsaved()}, new Double[]{Double.MAX_VALUE}, new Double[]{0.0});
            if(d!= null) Database.OPTIMISATIONTIMEOUT.set(Tools.dtostr(d[0]));
        }
        
        //AL Options
        if(evt.getSource()==jBSALOptions)
        {
            Double[] values= Tools.getValues(
                new String[]{ //message
                    /*ALZERO*/          "The absolut position where the Autoleveling is correcting to. \nSo after level correction this Z value will habe the proped value. (Normally its 0)",
                    /*ALMAXPROPDEPTH*/  "How deep the system try to prope. (You should home you system before Autoleveling.) \nIn absolute position it is \"Zero height\" - this value.",
                    /*ALSAVEHEIGHT*/    "Absulute hight where the CNC can move safely without problems. \nThe first Proping will also start from this position!",
                    /*ALCLEARENCE*/     "The clearence to the object between tow propings.",
                    /*ALFEEDRATE*/      "The feedrate used for the proping",
                },
                new Double[]{ //value
                    Database.ALZERO.getsaved(),
                    Database.ALMAXPROPDEPTH.getsaved(),
                    Database.ALSAVEHEIGHT.getsaved(),
                    Database.ALCLEARENCE.getsaved(),
                    Database.ALFEEDRATE.getsaved(),
                },
                new Double[]{ //max
                    /*ALZERO*/          Double.MAX_VALUE,
                    /*ALMAXPROPDEPTH*/  Database.WORKSPACE2.getsaved(),
                    /*ALSAVEHEIGHT*/    Database.WORKSPACE2.getsaved(),
                    /*ALCLEARENCE*/     Database.WORKSPACE2.getsaved(),    
                    /*ALFEEDRATE*/      Double.MAX_VALUE,
                },
                new Double[]{ //min
                    /*ALZERO*/          -Double.MAX_VALUE,
                    /*ALMAXPROPDEPTH*/  0.0,
                    /*ALSAVEHEIGHT*/    0.0-Database.WORKSPACE2.getsaved(),                    
                    /*ALCLEARENCE*/     0.0,
                    /*ALFEEDRATE*/      0.0,
                });

            if(values!= null)
            {
                Database.ALZERO.set(Tools.dtostr(values[0]));
                Database.ALMAXPROPDEPTH.set(Tools.dtostr(values[1]));
                Database.ALSAVEHEIGHT.set(Tools.dtostr(values[2]));
                Database.ALCLEARENCE.set(Tools.dtostr(values[3]));
                Database.ALFEEDRATE.set(Tools.dtostr(values[4]));
            }
        }

        
        //AL Distance
        if(evt.getSource()==jBSALDistance)
        {
            Double[] values= Tools.getValues(
                new String[]{ //message
                    /*ALDISTANACE*/         "The maximum distance between two props",
                    /*ALMAXMOVELENGTH*/     "The maxiumum Length of a XY move bevore it get split",
                },
                new Double[]{ //value
                    Database.ALDISTANACE.getsaved(),
                    Database.ALMAXMOVELENGTH.getsaved(),
                },
                new Double[]{ //max
                    /*ALDISTANACE*/         Double.MAX_VALUE,
                    /*ALMAXMOVELENGTH*/     Double.MAX_VALUE,
                },
                new Double[]{ //min
                    /*ALDISTANACE*/         Double.MIN_VALUE, //Not 0 
                    /*ALMAXMOVELENGTH*/     Double.MIN_VALUE, //Not 0 
                });

            if(values!= null)
            {
                Database.ALDISTANACE.set(Tools.dtostr(values[0]));
                Database.ALMAXMOVELENGTH.set(Tools.dtostr(values[1]));
            }
        }
        
        
        //AutoLavel StartCode
        if(evt.getSource()==jBSALStart)
        {
            JTextArea textArea = new JTextArea(Database.ALSTARTCODE.get()); 
            JScrollPane scrollArea = new JScrollPane(textArea); 
            scrollArea.setPreferredSize(new Dimension(100, 100));

            if(JOptionPane.showConfirmDialog
                    (
                    this,
                    new Object[]{"Enter the commands which will be executed when autoleveling is started:", scrollArea},
                    "Tool change:",
                    JOptionPane.OK_CANCEL_OPTION
                    )
                    ==JOptionPane.OK_OPTION)
                Database.ALSTARTCODE.set(textArea.getText().trim());
        }
        
        //ARC
        if(evt.getSource()==jBSARC)
        {
            Double[] d=Tools.getValues(new String[]{"Set the Maximum Segment length for ARC to linear move converstion \n The lower the value the more communication is nessesarry:"}, new Double[]{Database.ARCSEGMENTLENGTH.getsaved()}, new Double[]{Double.MAX_VALUE}, new Double[]{Double.MIN_VALUE});
            if(d!= null) Database.ARCSEGMENTLENGTH.set(Tools.dtostr(d[0]));
        }

        //Backlash
        if(evt.getSource()==jBSBacklash)
        {
            Double[] values = new Double[3];
            String[] messages= new String[3];

            for(int i=0; i<3;i++ )
            {
                values[i] = Database.getBacklash(i).getsaved();
                messages[i] = "Set the Backlash for the "+CommandParsing.axesName[i]+" axis:";
            }
            values= Tools.getValues(messages, values, new Double[]{Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE}, new Double[]{0.0,0.0,0.0});

            if(values!= null)
            for(int i=0; i<3;i++ )
                Database.getBacklash(i).set(Tools.dtostr(values[i]));
        }
        
        fireupdateGUI();
    }//GEN-LAST:event_jBSettingsActionPerformed

    private void jBexportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBexportActionPerformed
        JFileChooser fc= Database.getFileChooser();
        fc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".ois")||f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Settings files (*.ois)";
            }
        });
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);

        if(fc.showSaveDialog(this)!=JFileChooser.APPROVE_OPTION)
            return;
        
        File f=fc.getSelectedFile();
        if(f.getName().lastIndexOf('.')==-1)
            f=new File(f.getPath()+".ois");

        if(!Database.save(f))
            JOptionPane.showMessageDialog(this, "Cannot export Settings!");
    }//GEN-LAST:event_jBexportActionPerformed

    private void jBImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBImportActionPerformed
        JFileChooser fc= Database.getFileChooser();
        fc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".ois")||f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Settings files (*.ois)";
            }
        });
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);

        if(fc.showOpenDialog(this)!=JFileChooser.APPROVE_OPTION)
            return;
        
        if(!Database.load(fc.getSelectedFile()))
            JOptionPane.showMessageDialog(this, "Cannot import Settings!");
        
        fireupdateGUI();
        
    }//GEN-LAST:event_jBImportActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBImport;
    private javax.swing.JButton jBSALDistance;
    private javax.swing.JButton jBSALOptions;
    private javax.swing.JButton jBSALStart;
    private javax.swing.JButton jBSARC;
    private javax.swing.JButton jBSBacklash;
    private javax.swing.JButton jBSCNCG0Feedrate;
    private javax.swing.JButton jBSCNCOptimiserTime;
    private javax.swing.JButton jBSCNCSpindleOFF;
    private javax.swing.JButton jBSCNCSpindleON;
    private javax.swing.JButton jBSCNCStart;
    private javax.swing.JButton jBSCNCToolChange;
    private javax.swing.JButton jBSCNCToolSize;
    private javax.swing.JButton jBSFastFeedrate;
    private javax.swing.JButton jBSHomeing;
    private javax.swing.JButton jBSWorkSpace;
    private javax.swing.JButton jBexport;
    private javax.swing.JLabel jLSALDistance;
    private javax.swing.JLabel jLSALOptions;
    private javax.swing.JLabel jLSALStart;
    private javax.swing.JLabel jLSARC;
    private javax.swing.JLabel jLSBacklash;
    private javax.swing.JLabel jLSCNCG0Feedrate;
    private javax.swing.JLabel jLSCNCOptimiserTime;
    private javax.swing.JLabel jLSCNCSpindleOFF;
    private javax.swing.JLabel jLSCNCSpindleON;
    private javax.swing.JLabel jLSCNCStart;
    private javax.swing.JLabel jLSCNCToolChange;
    private javax.swing.JLabel jLSCNCToolSize;
    private javax.swing.JLabel jLSFastFeedrate;
    private javax.swing.JLabel jLSHomeing;
    private javax.swing.JLabel jLSWorkSpace;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    // End of variables declaration//GEN-END:variables
}

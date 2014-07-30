/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnc.gcode.controller;

import cnc.gcode.controller.communication.Communication;
import java.awt.Dimension;
import java.io.File;
import java.util.LinkedList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author patrick
 */
public class JPanelSettings extends javax.swing.JPanel implements IGUIEvent, ISettingsFeedback{
    
    public static final String[] homing = {"upper left","upper right","lower left","lower right" }; //0= upper left; 1= upper right; 2= lower left; 3= lower right;

    private IEvent GUIEvent = null;
    private LinkedList<ISettingFeedback> returnedFeedbackValues = null;
    /**
     * Creates new form JPanelSettings
     */
    public JPanelSettings() {
        initComponents();
    }
    
    @Override
    public void setGUIEvent(IEvent event)
    {
        GUIEvent = event;
    }

    @Override
    public void updateGUI(boolean serial, boolean isworking)
    {
        jBImport.setEnabled(!isworking);
        //Akt Text
        jLSHomeing.setText(homing[Integer.parseInt(Database.HOMING.get())]); //Homeing
        jLSFastFeedrate.setText(Database.MAXFEEDRATE.get());
        jLSWorkSpace.setText("");
        for(int i = 0; i < 3;i++ )
        {
            jLSWorkSpace.setText(jLSWorkSpace.getText() + CommandParsing.axesName[i] + " = " + Database.getWorkspace(i)+"   ");        
        }
        jLSCNCStart.setText(Tools.convertToMultiline(Database.STARTCODE.get()));
        jLSCNCToolChange.setText(Tools.convertToMultiline(Database.TOOLCHANGE.get()));
        jLSCNCSpindleON.setText(Tools.convertToMultiline(Database.SPINDLEON.get()));
        jLSCNCSpindleOFF.setText(Tools.convertToMultiline(Database.SPINDLEOFF.get()));
        jLSCNCG0Feedrate.setText(Database.GOFEEDRATE.get());
        jLSCNCToolSize.setText(Database.TOOLSIZE.get());
        jLSCNCOptimiserTime.setText(Database.OPTIMISATIONTIMEOUT.get());
        jLSALOptions.setText(Tools.convertToMultiline("Zero height: " + Database.ALZERO +
                                                      "\nMax depth: " + Database.ALMAXPROBDEPTH +
                                                      "\nSafe height: " + Database.ALSAVEHEIGHT +
                                                      "\nClearence: " + Database.ALCLEARANCE +
                                                       "\nFeedrate: " + Database.ALFEEDRATE));
        jLSALDistance.setText(Tools.convertToMultiline("Distance: " + Database.ALDISTANCE +       
                                                        "\nMax XY Move Length: " + Database.ALMAXMOVELENGTH));
        jLSALStart.setText(Tools.convertToMultiline(Database.ALSTARTCODE.get()));
        jLSARC.setText(Database.ARCSEGMENTLENGTH.get());
        jLSBacklash.setText("");
        for(int i=0; i<3;i++ )
        {
            jLSBacklash.setText(jLSBacklash.getText() + CommandParsing.axesName[i] + " = " + Database.getBacklash(i) + "   ");       
        }
        jLSmodalG1.setText(Database.G1MODAL.get().equals("0")?"OFF":"ON");
        jLSComType.setText(Database.COMTYPE.get());
    }
    
    private void fireupdateGUI()
    {
        if(GUIEvent==null)
        {
            throw new RuntimeException("GUI EVENT NOT USED!");
        }
        GUIEvent.fired();
    }
    
    private LinkedList<ISettingFeedback> DisplaySettingPanel(String panelTitle,
                                                             JSettingEnum[] ids,
                                                             double[] currentValues,
                                                             double[] minValues,
                                                             double[] maxValues,
                                                             String[] descriptions)
    {
        returnedFeedbackValues = null;
        if(ids.length !=  currentValues.length && currentValues.length!= descriptions.length)
        {
            return null;
        }
        
        LinkedList<ISettingFeedback> settings = new LinkedList<ISettingFeedback>();
        for (int i = 0; i< currentValues.length; i++)
        {
            settings.add(new JSettingFeedback(ids[i],
                                              currentValues[i],
                                              minValues[i],
                                              maxValues[i],
                                              descriptions[i] ));
        }
        JSettingsDialog form = new JSettingsDialog(this, settings);
        form.setTitle(panelTitle);
        form.pack();
        form.setModal(true);
        form.setVisible(true);
        
        return returnedFeedbackValues;
    }
    
    private void HandleBacklashSettings()
    {
        Double[] values = new Double[3];
        String[] messages = new String[3];

        for(int i = 0; i < 3;i++ )
        {
            messages[i] = "Set the Backlash for the " + CommandParsing.axesName[i] + " axis:";
        }

        LinkedList<ISettingFeedback> updatedValues = DisplaySettingPanel("Backlash correction",
                                                                         new JSettingEnum[]{ //value
                                                                                            JSettingEnum.BL0,
                                                                                            JSettingEnum.BL1,
                                                                                            JSettingEnum.BL2
                                                                        },
                                                                        new double[]{ //value
                                                                                        Database.getBacklash(0).getsaved(),
                                                                                        Database.getBacklash(1).getsaved(),
                                                                                        Database.getBacklash(2).getsaved()
                                                                                    },
                                                                        new double[]{ //min 
                                                                                    0,
                                                                                    0,
                                                                                    0
                                                                        },
                                                                        new double[]{ //max
                                                                                        300,
                                                                                        300,
                                                                                        300
                                                                                    },
                                                                        new String[]{ //message
                                                                                    messages[0],
                                                                                    messages[1],
                                                                                    messages[2]
                                                                                });
        if(updatedValues != null)
        {
            for(int i = 0; i < 3;i++ )
            {
                Database.getBacklash(i).set(Tools.dtostr(updatedValues.get(i).getSettingValue()));
            }
        }
    }
    
    private void HandleWorkingSpaceSettings()
    {
        LinkedList<ISettingFeedback> updatedValues = DisplaySettingPanel("Workspace size",
                                                                             new JSettingEnum[]{ //value
                                                                                JSettingEnum.WORKSPACE0,
                                                                                JSettingEnum.WORKSPACE1,
                                                                                JSettingEnum.WORKSPACE2
                                                                            },
                                                                            new double[]{ //value
                                                                                            Database.getWorkspace(0).getsaved(),
                                                                                            Database.getWorkspace(1).getsaved(),
                                                                                            Database.getWorkspace(2).getsaved()
                                                                                        },
                                                                            new double[]{ //min 
                                                                                /*ALZERO*/          0,
                                                                                /*ALMAXPROBDEPTH*/  0,
                                                                                /*ALSAVEHEIGHT*/    0
                                                                            },
                                                                            new double[]{ //max
                                                                                            300,
                                                                                            300,
                                                                                            300
                                                                                        },
                                                                            new String[]{ //message
                                                                                        "Set Size for the " + CommandParsing.axesName[0] + " axis",
                                                                                        "Set Size for the " + CommandParsing.axesName[1] + " axis",
                                                                                        "Set Size for the " + CommandParsing.axesName[2] + " axis"
                                                                                    });
            if(updatedValues != null)
            {
                for(int i = 0; i < 3;i++ )
                {
                    Database.getWorkspace(i).set(Tools.dtostr(updatedValues.get(i).getSettingValue()));
                }
            }
    }
    
    private void HandleAutoLevelingSettings()
    {
        LinkedList<ISettingFeedback> updatedValues = DisplaySettingPanel("Auto leveling settings",
                                                                            new JSettingEnum[]{ //value
                                                                                JSettingEnum.ALZERO,
                                                                                JSettingEnum.ALMAXPROBDEPTH,
                                                                                JSettingEnum.ALSAVEHEIGHT,
                                                                                JSettingEnum.ALCLEARANCE,
                                                                                JSettingEnum.ALFEEDRATE,
                                                                            },
                                                                            new double[]{ //value
                                                                                            Database.ALZERO.getsaved(),
                                                                                            Database.ALMAXPROBDEPTH.getsaved(),
                                                                                            Database.ALSAVEHEIGHT.getsaved(),
                                                                                            Database.ALCLEARANCE.getsaved(),
                                                                                            Database.ALFEEDRATE.getsaved(),
                                                                                        },
                                                                            new double[]{ //min 
                                                                                /*ALZERO*/          -Double.MAX_VALUE,
                                                                                /*ALMAXPROBDEPTH*/  -1.0,
                                                                                /*ALSAVEHEIGHT*/    0.0-Database.WORKSPACE2.getsaved(),                    
                                                                                /*ALCLEARANCE*/     0.0,
                                                                                /*ALFEEDRATE*/      0.0,
                                                                            },
                                                                            new double[]{ //max
                                                                                            /*ALZERO*/          Double.MAX_VALUE,
                                                                                            /*ALMAXPROBDEPTH*/  Database.WORKSPACE2.getsaved(),
                                                                                            /*ALSAVEHEIGHT*/    Database.WORKSPACE2.getsaved(),
                                                                                            /*ALCLEARANCE*/     Database.WORKSPACE2.getsaved(),    
                                                                                            /*ALFEEDRATE*/      Double.MAX_VALUE,
                                                                                        },
                                                                            new String[]{ //message
                                                                                        /*ALZERO*/          "The absolute position where the Autoleveling is correcting. \nSo after level correction, this Z value will have the probed value. (Normally it is 0)",
                                                                                        /*ALMAXPROBDEPTH*/  "How deep the system tries to probe. (You should home your system before Autoleveling.) \nIn absolute position it is \"Zero height\" - this value.",
                                                                                        /*ALSAVEHEIGHT*/    "Absolute height where the CNC can move safely without problems. \nThe first probing will also start from this position!",
                                                                                        /*ALCLEARANCE*/     "The clearance to the object between two probes.",
                                                                                        /*ALFEEDRATE*/      "The feedrate used for the probing",
                                                                                    });
                                                                                            

            
            if(updatedValues != null)
            {
                    Database.ALZERO.set(Tools.dtostr(updatedValues.get(0).getSettingValue()));
                    Database.ALMAXPROBDEPTH.set(Tools.dtostr(updatedValues.get(1).getSettingValue()));
                    Database.ALSAVEHEIGHT.set(Tools.dtostr(updatedValues.get(2).getSettingValue()));
                    Database.ALCLEARANCE.set(Tools.dtostr(updatedValues.get(3).getSettingValue()));
                    Database.ALFEEDRATE.set(Tools.dtostr(updatedValues.get(4).getSettingValue()));
            }
    }
    
    
    private void HandleAutoLevelingDistanceSettings()
    {
        LinkedList<ISettingFeedback> updatedValues = DisplaySettingPanel("Workspace size",
                                                                             new JSettingEnum[]{ //value
                                                                                JSettingEnum.ALDISTANCE,
                                                                                JSettingEnum.ALMAXMOVELENGTH
                                                                            },
                                                                            new double[]{ //value
                                                                                Database.ALDISTANCE.getsaved(),
                                                                                Database.ALMAXMOVELENGTH.getsaved(),
                                                                            },
                                                                            new double[]{ //min 
                                                                                Double.MIN_VALUE,
                                                                                Double.MIN_VALUE
                                                                            },
                                                                            new double[]{ //max
                                                                                            300,
                                                                                            300
                                                                                        },
                                                                            new String[]{ //message
                                                                                          "The maximum distance between two probs",
                                                                                          "The maximum Length of a XY move before it gets split",
                                                                                        });
            if(updatedValues != null)
            {
                Database.ALDISTANCE.set(Tools.dtostr(updatedValues.get(0).getSettingValue()));
                Database.ALMAXMOVELENGTH.set(Tools.dtostr(updatedValues.get(1).getSettingValue()));
            }
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
        jBSHoming = new javax.swing.JButton();
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
        jLabel43 = new javax.swing.JLabel();
        jBSmodalG1 = new javax.swing.JButton();
        jLSmodalG1 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jBSComType = new javax.swing.JButton();
        jLSComType = new javax.swing.JLabel();

        jLabel2.setText("Homing:");

        jBSHoming.setText("Change");
        jBSHoming.addActionListener(new java.awt.event.ActionListener() {
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

        jLabel39.setText("CNC/Optimize Time:");

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

        jLabel40.setText("ARC/ Max Segment Length:");

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

        jLabel43.setText("Allow modal G1:");

        jBSmodalG1.setText("Change");
        jBSmodalG1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSettingsActionPerformed(evt);
            }
        });

        jLSmodalG1.setText("Settings Text");

        jLabel44.setText("Device connected:");

        jBSComType.setText("Change");
        jBSComType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSettingsActionPerformed(evt);
            }
        });

        jLSComType.setText("Settings Text");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
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
                            .addComponent(jBSWorkSpace)
                            .addComponent(jBSFastFeedrate)
                            .addComponent(jBSHoming)
                            .addComponent(jBSCNCStart)
                            .addComponent(jBSCNCToolChange)
                            .addComponent(jBSCNCSpindleON)
                            .addComponent(jBSCNCSpindleOFF)
                            .addComponent(jBSCNCG0Feedrate)
                            .addComponent(jBSCNCToolSize)
                            .addComponent(jBSCNCOptimiserTime)
                            .addComponent(jBSALOptions)
                            .addComponent(jBSALDistance)
                            .addComponent(jBSALStart)
                            .addComponent(jBSARC)
                            .addComponent(jBSBacklash)
                            .addComponent(jBSmodalG1)
                            .addComponent(jBSComType))
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLSHomeing)
                            .addComponent(jLSComType)
                            .addComponent(jLSFastFeedrate)
                            .addComponent(jLSWorkSpace)
                            .addComponent(jLSCNCStart)
                            .addComponent(jLSCNCToolChange)
                            .addComponent(jLSCNCSpindleON)
                            .addComponent(jLSCNCSpindleOFF)
                            .addComponent(jLSCNCG0Feedrate)
                            .addComponent(jLSCNCToolSize)
                            .addComponent(jLSCNCOptimiserTime)
                            .addComponent(jLSALOptions)
                            .addComponent(jLSALDistance)
                            .addComponent(jLSALStart)
                            .addComponent(jLSARC)
                            .addComponent(jLSBacklash)
                            .addComponent(jLSmodalG1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel44))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel43))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
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
                    .addComponent(jBSHoming)
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
                    .addComponent(jLabel43)
                    .addComponent(jLSmodalG1)
                    .addComponent(jBSmodalG1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(jLSComType)
                    .addComponent(jBSComType))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBexport)
                    .addComponent(jBImport))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSettingsActionPerformed

        //HOMING
        if(evt.getSource() == jBSHoming)
        {
            int options = JOptionPane.showOptionDialog(this,
                                                       "Select homing corner",
                                                       "Homing",
                                                       JOptionPane.YES_NO_CANCEL_OPTION,
                                                       JOptionPane.INFORMATION_MESSAGE,
                                                       null,
                                                       homing,
                                                       0);
            if(options != JOptionPane.CLOSED_OPTION)
            {
                Database.HOMING.set(""+options);
            }
        }

        //MAXFEEDRATE
        if(evt.getSource() == jBSFastFeedrate)
        {
            Double[] d = Tools.getValues(new String[]{"Set the feedrate for the fast move:"},
                                        new Double[]{Database.MAXFEEDRATE.getsaved()},
                                        new Double[]{Double.MAX_VALUE},
                                        new Double[]{0.0});
            if(d != null)
            {
                Database.MAXFEEDRATE.set(Tools.dtostr(d[0]));
            }
        }

        //WORKINGSPACE
        if(evt.getSource() == jBSWorkSpace)
        {
            HandleWorkingSpaceSettings();
        }
        
        //StartCode
        if(evt.getSource() == jBSCNCStart)
        {
            JTextArea textArea      = new JTextArea(Database.STARTCODE.get()); 
            JScrollPane scrollArea  = new JScrollPane(textArea); 
            scrollArea.setPreferredSize(new Dimension(100, 100));

            if(JOptionPane.showConfirmDialog
                                            (
                                            this,
                                            new Object[]{"Enter the command which will be executed when milling is started:",
                                                        scrollArea},
                                            "Tool change:",
                                            JOptionPane.OK_CANCEL_OPTION
                                            )
                                            == JOptionPane.OK_OPTION)
                Database.STARTCODE.set(textArea.getText().trim());
        }

        //Toolchange
        if(evt.getSource() == jBSCNCToolChange)
        {
            JTextArea textArea      = new JTextArea(Database.TOOLCHANGE.get()); 
            JScrollPane scrollArea  = new JScrollPane(textArea); 
            scrollArea.setPreferredSize(new Dimension(100, 100));

            if(JOptionPane.showConfirmDialog
                                            (
                                            this,
                                            new Object[]{"Enter the command to change the tool:",
                                                        scrollArea,
                                                        "Hint: '?' will be replaced with the tool number"},
                                            "Tool change:",
                                            JOptionPane.OK_CANCEL_OPTION
                                            )
                                            == JOptionPane.OK_OPTION)
                Database.TOOLCHANGE.set(textArea.getText().trim());
        }
        
        //Spindle ON
        if(evt.getSource() == jBSCNCSpindleON)
        {
            JTextArea textArea      = new JTextArea(Database.SPINDLEON.get()); 
            JScrollPane scrollArea  = new JScrollPane(textArea); 
            scrollArea.setPreferredSize(new Dimension(100, 100));

            if(JOptionPane.showConfirmDialog
                                            (
                                            this,
                                            new Object[]{"Enter the command to turn the spindle on:",
                                                        scrollArea,
                                                        "Hint: '?' will be replaced with the original command number!"},
                                            "Spindle ON:",
                                            JOptionPane.OK_CANCEL_OPTION
                                            )
                                            == JOptionPane.OK_OPTION)
                Database.SPINDLEON.set(textArea.getText().trim());
        }

        //Spindle OFF
        if(evt.getSource() == jBSCNCSpindleOFF)
        {
            JTextArea textArea          = new JTextArea(Database.SPINDLEOFF.get()); 
            JScrollPane scrollArea      = new JScrollPane(textArea); 
            scrollArea.setPreferredSize(new Dimension(100, 100));

            if(JOptionPane.showConfirmDialog
                                            (
                                            this,
                                            new Object[]{"Enter the command to turn the spindle off:", scrollArea},
                                            "Spindle OFF:",
                                            JOptionPane.OK_CANCEL_OPTION
                                            )
                                            == JOptionPane.OK_OPTION)
                Database.SPINDLEOFF.set(textArea.getText().trim());
        }
        
        
        //G0Feedrate
        if(evt.getSource() == jBSCNCG0Feedrate)
        {
            Double[] d = Tools.getValues(new String[]{"Set the feedrate for the G0 move:"},
                                        new Double[]{Database.GOFEEDRATE.getsaved()},
                                        new Double[]{Database.MAXFEEDRATE.getsaved()},
                                        new Double[]{0.0});
            if(d != null) 
            {
                Database.GOFEEDRATE.set(Tools.dtostr(d[0]));
            }
        }
        
        //Tooldiameter
        if(evt.getSource() == jBSCNCToolSize)
        {
            Double[] d = Tools.getValues(new String[]{"Set the toolsize for CNC milling simulation:"},
                                        new Double[]{Database.TOOLSIZE.getsaved()},
                                        new Double[]{Double.MAX_VALUE},
                                        new Double[]{0.0});
            if(d != null) 
            {
                Database.TOOLSIZE.set(Tools.dtostr(d[0]));
            }
        }
        
        //Tooldiameter
        if(evt.getSource() == jBSCNCOptimiserTime)
        {
            Double[] d = Tools.getValues(new String[]{"Set the timeout in seconds for optimizing:"},
                                        new Double[]{Database.OPTIMISATIONTIMEOUT.getsaved()},
                                        new Double[]{Double.MAX_VALUE},
                                        new Double[]{0.0});
            if(d != null)
            {
                Database.OPTIMISATIONTIMEOUT.set(Tools.dtostr(d[0]));
            }
        }
        
        //AL Options
        if(evt.getSource() == jBSALOptions)
        {
            HandleAutoLevelingSettings();
        }

        //AL Distance
        if(evt.getSource() == jBSALDistance)
        {
            HandleAutoLevelingDistanceSettings();
        }
        
        
        //AutoLavel StartCode
        if(evt.getSource( )== jBSALStart)
        {
            JTextArea textArea      = new JTextArea(Database.ALSTARTCODE.get()); 
            JScrollPane scrollArea  = new JScrollPane(textArea); 
            scrollArea.setPreferredSize(new Dimension(100, 100));

            if(JOptionPane.showConfirmDialog
                    (
                    this,
                    new Object[]{"Enter the commands that will be executed when autoleveling starts:",
                                scrollArea},
                    "Tool change:",
                    JOptionPane.OK_CANCEL_OPTION
                    )
                    == JOptionPane.OK_OPTION)
                Database.ALSTARTCODE.set(textArea.getText().trim());
        }
        
        //ARC
        if(evt.getSource() == jBSARC)
        {
            Double[] d = Tools.getValues(new String[]{"Set the maximum segment length for ARC to linear move conversion \n The lower the value the more communication is needed:"},
                                        new Double[]{Database.ARCSEGMENTLENGTH.getsaved()},
                                        new Double[]{Double.MAX_VALUE},
                                        new Double[]{Double.MIN_VALUE});
            if(d != null)
            {
                Database.ARCSEGMENTLENGTH.set(Tools.dtostr(d[0]));
            }
        }

        //Backlash
        if(evt.getSource() == jBSBacklash)
        {
            HandleBacklashSettings();
        }

        //Modal G1
        if(evt.getSource() == jBSmodalG1)
        {
            int options = JOptionPane.showOptionDialog(this,
                                                       "Select G1 modal mode:",
                                                       "Modal G1",
                                                       JOptionPane.YES_NO_CANCEL_OPTION,
                                                       JOptionPane.INFORMATION_MESSAGE,
                                                       null,
                                                       new String[] {"OFF", "ON"},
                                                       0);
            if(options != JOptionPane.CLOSED_OPTION)
            {
                Database.G1MODAL.set("" + options);
            }
        }
        
        //ComType
        if(evt.getSource() == jBSComType)
        {
            int options = JOptionPane.showOptionDialog(this,
                                                        "Select type of communication",
                                                        "Device Type",
                                                        JOptionPane.YES_NO_CANCEL_OPTION,
                                                        JOptionPane.INFORMATION_MESSAGE,
                                                        null,
                                                        Communication.values(),
                                                        0);
            if(options != JOptionPane.CLOSED_OPTION)
            {
                Database.COMTYPE.set(Communication.values()[options].toString());
            }
        }
        
        fireupdateGUI();
    }//GEN-LAST:event_jBSettingsActionPerformed

    private void jBexportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBexportActionPerformed
        JFileChooser fc = Database.getFileChooser();
        fc.setFileFilter(new FileFilter() 
        {
            @Override
            public boolean accept(File f) 
            {
                return f.getName().toLowerCase().endsWith(".ois")||f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Settings files (*.ois)";
            }
        });
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);

        if(fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
        {
            return;
        }
        
        File f = fc.getSelectedFile();
        if(f.getName().lastIndexOf('.') == -1)
        {
            f = new File(f.getPath()+".ois");
        }

        if(Database.save(f) == false)
        {
            JOptionPane.showMessageDialog(this, "Cannot export Settings!");
        }
    }//GEN-LAST:event_jBexportActionPerformed

    private void jBImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBImportActionPerformed
        JFileChooser fc = Database.getFileChooser();
        fc.setFileFilter(new FileFilter() 
        {
            @Override
            public boolean accept(File f) 
            {
                return f.getName().toLowerCase().endsWith(".ois")||f.isDirectory();
            }

            @Override
            public String getDescription() 
            {
                return "Settings files (*.ois)";
            }
        });
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);

        if(fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
        {
            return;
        }
        
        if(Database.load(fc.getSelectedFile()) == false)
        {
            JOptionPane.showMessageDialog(this, "Cannot import settings!");
        }
        
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
    private javax.swing.JButton jBSComType;
    private javax.swing.JButton jBSFastFeedrate;
    private javax.swing.JButton jBSHoming;
    private javax.swing.JButton jBSWorkSpace;
    private javax.swing.JButton jBSmodalG1;
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
    private javax.swing.JLabel jLSComType;
    private javax.swing.JLabel jLSFastFeedrate;
    private javax.swing.JLabel jLSHomeing;
    private javax.swing.JLabel jLSWorkSpace;
    private javax.swing.JLabel jLSmodalG1;
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
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    // End of variables declaration//GEN-END:variables

    @Override
    public void settingsUpdated(LinkedList<ISettingFeedback> settingValues) {
        returnedFeedbackValues = settingValues;
    }
}

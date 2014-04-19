/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UI;

import Cloudlet_Scheduler_Space_Shared.CloudletSchedulerSSImprovedRoundRobin;
import Cloudlet_Scheduler_Space_Shared.CloudletSchedulerSSShortestCloudletFirst;
import Cloudlet_Scheduler_Space_Shared.CloudletSchedulerSSShortestRemainingTimeJobFirst;
import Cloudlet_Scheduler_Time_Shared.CloudletSchedulerTSImprovedRoundRobin;
import Cloudlet_Scheduler_Time_Shared.CloudletSchedulerTSLongestTimeFirst;
import Cloudlet_Scheduler_Time_Shared.CloudletSchedulerTSSJFImprovedWithIRR;
import Cloudlet_Scheduler_Time_Shared.CloudletSchedulerTSSRTFImprovedWithIRR;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import org.cloudbus.cloudsim.CloudletScheduler;

/**
 *
 * @author Tamojit9
 */
public class VmPanel extends javax.swing.JPanel {
    
    Map<String, CloudletScheduler> mapping = new HashMap<>();

    /**
     * Creates new form VmPanel
     */
    public VmPanel() {
        initComponents();
        done.setText("    " + "Done");
        addSchedulingPolicy("CloudletSchedulerSSImprovedRoundRobin", new CloudletSchedulerSSImprovedRoundRobin());
        addSchedulingPolicy("CloudletSchedulerSSShortestCloudletFirst", new CloudletSchedulerSSShortestCloudletFirst());
        addSchedulingPolicy("CloudletSchedulerSSShortestRemainingTimeJobFirst", 
                new CloudletSchedulerSSShortestRemainingTimeJobFirst());
        addSchedulingPolicy("CloudletSchedulerTSImprovedRoundRobin", 
                new CloudletSchedulerTSImprovedRoundRobin());
        addSchedulingPolicy("CloudletSchedulerTSSJFImprovedWithIRR", 
                new CloudletSchedulerTSSJFImprovedWithIRR());
        addSchedulingPolicy("CloudletSchedulerTSSRTFImprovedWithIRR", 
                new CloudletSchedulerTSSRTFImprovedWithIRR());
        addSchedulingPolicy("CloudletSchedulerTSLongestTimeFirst", 
                new CloudletSchedulerTSLongestTimeFirst());
        done.onClick(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent me) {
                try {
                    int n = (int) vmID.getValue();
                    if(n <= 0) throw new Exception();
                    while(n-- > 0) {
                        if(gen.getSelectedItem().equals("Random")) addVmRandomly();
                        else addVm();
                    }
                    deActivate(false);
                    done.setVisible(false);
                } catch(Exception e) {
                    e.printStackTrace();
                    new MessageBox().show("Entered Data Incorrect", MessageBox.warning);
                }
            }
        });
    }
    
    void addVm() throws Exception {
        if (((int) vmID.getValue() <= 0) || ((int) ram.getValue() <= 0) || ((int) mips.getValue() <= 0)
                || ((int) npes.getValue() <= 0) || ((int) size.getValue() <= 0)) {
            throw new Exception();
        }
        int vmId = (int) vmID.getValue(), npeS = (int) npes.getValue(), Ram = (int) ram.getValue(), bw = (int) bW.getValue(), Storag = (int) storage.getValue(),
                siz = (int) size.getValue(), mip = (int) mips.getValue();
        System.out.println(mapping.get(scheduler.getSelectedItem()));
        Simulate.addVm(vmId, mip, siz, Ram, bw, npeS, mapping.get(scheduler.getSelectedItem()));
    }
    
    int rand() {
        return (int) (1 + Math.random());
    }
    
    void addVmRandomly() throws Exception {
        int vmId = (int) vmID.getValue()*rand(), npeS = 10*rand(), 
                Ram = (int) ram.getValue()*rand(), bw = (int) bW.getValue()*rand(), 
                Storag = (int) storage.getValue()*rand(),
                siz = (int) size.getValue()*rand(), mip = (int) mips.getValue()*rand();
        Simulate.addVm(vmId, mip, siz, Ram, bw, npeS, mapping.get(scheduler.getSelectedItem()));
    }
    
    void deActivate(boolean val) {
        Component[] c = getComponents();
        for (Component component : c) {
            if(component instanceof JComboBox) {
                if(((JComboBox)(component)).getToolTipText() != null
                       && ((JComboBox)(component)).getToolTipText().equals("gen")) continue;
            }
            component.setEnabled(val);
        }
        repaint();
    }
    
    @SuppressWarnings("unchecked")
    final void addSchedulingPolicy(String policy, CloudletScheduler cs) {
        scheduler.addItem(policy);
        mapping.put(policy, cs);
    }
    
    void iniVmPanel(int n) {
        
    }
    
    @Override
    public void paint(Graphics g) {
        try {
            g.drawImage(ImageIO.read(getClass().getResource("/UI/bg1.jpg")), 0, 0, getWidth(), getHeight(), null);
            paintChildren(g);
        } catch (IOException ex) {
            Logger.getLogger(CloudMod.class.getName()).log(Level.SEVERE, null, ex);
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
        npes = new javax.swing.JSpinner();
        scheduler = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        ram = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        bW = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        storage = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        vmID = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        size = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        mips = new javax.swing.JSpinner();
        done = new UI.Button();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        gen = new javax.swing.JComboBox();

        jLabel2.setBackground(new java.awt.Color(113, 159, 8));
        jLabel2.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel2.setText("              No. Of PEs :");

        npes.setFont(new java.awt.Font("Segoe UI", 0, 24));
        npes.setValue(1);

        scheduler.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel1.setBackground(new java.awt.Color(113, 159, 8));
        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel1.setText(" Cloudlet Scheduler Policy :");

        jLabel3.setBackground(new java.awt.Color(113, 159, 8));
        jLabel3.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel3.setText("                       Ram :");

        ram.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        ram.setValue(1024/2);

        jLabel4.setBackground(new java.awt.Color(113, 159, 8));
        jLabel4.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel4.setText("             Band Width :");

        bW.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        bW.setValue(512);

        jLabel5.setBackground(new java.awt.Color(113, 159, 8));
        jLabel5.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel5.setText("                  Storage :");

        storage.setFont(new java.awt.Font("Segoe UI", 0, 24));
        storage.setValue(1024);

        jLabel6.setBackground(new java.awt.Color(113, 159, 8));
        jLabel6.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel6.setText("No. Of Vms :");

        vmID.setFont(new java.awt.Font("Segoe UI", 0, 24));
        vmID.setValue(1);

        jLabel8.setBackground(new java.awt.Color(113, 159, 8));
        jLabel8.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel8.setText("                       Size :");

        size.setFont(new java.awt.Font("Segoe UI", 0, 24));
        size.setValue(1024);

        jLabel9.setBackground(new java.awt.Color(113, 159, 8));
        jLabel9.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel9.setText("                      Mips :");

        mips.setFont(new java.awt.Font("Segoe UI", 0, 24));
        mips.setValue(1024);

        javax.swing.GroupLayout doneLayout = new javax.swing.GroupLayout(done);
        done.setLayout(doneLayout);
        doneLayout.setHorizontalGroup(
            doneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 212, Short.MAX_VALUE)
        );
        doneLayout.setVerticalGroup(
            doneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 57, Short.MAX_VALUE)
        );

        jLabel10.setBackground(new java.awt.Color(113, 159, 8));
        jLabel10.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel10.setText("     Method of Generation :");

        gen.setFont(new java.awt.Font("Segoe UI", 0, 18));
        gen.setToolTipText("gen");
        gen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Manual", "Random" }));
        gen.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                genItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(160, 160, 160))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(jLabel9)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(mips, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                        .addComponent(jLabel5)
                                                        .addGap(23, 23, 23))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel8)
                                                        .addGap(25, 25, 25)))
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(size)
                                                    .addComponent(storage, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(40, 40, 40)))
                                .addComponent(done, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(bW, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addGap(18, 18, 18)
                                    .addComponent(ram, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(143, 143, 143))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel6)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(npes)
                                    .addComponent(vmID, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(scheduler, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(gen, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gen, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(vmID, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(npes)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(scheduler, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ram)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(bW)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(storage)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(size, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(mips)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(done, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void genItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_genItemStateChanged
        // TODO add your handling code here:
        if(gen.getSelectedItem().toString().equals("Random")) {
            deActivate(false);
        } else {
            deActivate(true);
        }
    }//GEN-LAST:event_genItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner bW;
    private UI.Button done;
    private javax.swing.JComboBox gen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSpinner mips;
    private javax.swing.JSpinner npes;
    private javax.swing.JSpinner ram;
    private javax.swing.JComboBox scheduler;
    private javax.swing.JSpinner size;
    private javax.swing.JSpinner storage;
    private javax.swing.JSpinner vmID;
    // End of variables declaration//GEN-END:variables
}

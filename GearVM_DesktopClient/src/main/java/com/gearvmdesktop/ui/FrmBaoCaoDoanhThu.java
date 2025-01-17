/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gearvmdesktop.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formdev.flatlaf.FlatLightLaf;
import com.gearvmdesktop.barchart.chart.ModelChart;
import com.gearvmdesktop.service.ReportService;
import com.gearvmstore.GearVM.model.response.MonthlyFinanceReportResponseModel;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FrmBaoCaoDoanhThu extends javax.swing.JFrame {

    private JTextField jTextField;

    /**
     * Creates new form Main
     */
    public FrmBaoCaoDoanhThu() throws IOException {
        initComponents();
        getContentPane().setBackground(new Color(250, 250, 250));
        chart.addLegend("Doanh Thu", new Color(245, 189, 135));
        chart.addLegend("Chi Phí", new Color(135, 189, 245));
        chart.addLegend("Tiền Lợi Nhuận", new Color(189, 135, 245));
        chart.addLegend("Tiền Lỗ", new Color(139, 229, 222));

        List<MonthlyFinanceReportResponseModel> financeReportList = getMonthlyFinanceReports("2023");
        for (MonthlyFinanceReportResponseModel finaceReport : financeReportList) {
            chart.addData(new ModelChart("Tháng " + finaceReport.getMonth(),
                    new double[]{finaceReport.getRevenue(), finaceReport.getCost(),
                            finaceReport.getProfit(), finaceReport.getLoss()}));
        }

//        chart.addData(new ModelChart("Tháng 1", new double[]{500, 200, 80, 89}));
//        chart.addData(new ModelChart("Tháng 2", new double[]{600, 750, 90, 150}));
//        chart.addData(new ModelChart("Tháng 3", new double[]{200, 350, 460, 900}));
//        chart.addData(new ModelChart("Tháng 4", new double[]{480, 150, 750, 700}));
//        chart.addData(new ModelChart("Tháng 5", new double[]{350, 540, 300, 150}));
//        chart.addData(new ModelChart("Tháng 6", new double[]{190, 280, 81, 200}));
//        chart.addData(new ModelChart("Tháng 7", new double[]{500, 200, 80, 89}));
//        chart.addData(new ModelChart("Tháng 8", new double[]{600, 750, 90, 150}));
//        chart.addData(new ModelChart("Tháng 9", new double[]{200, 350, 460, 900}));
//        chart.addData(new ModelChart("Tháng 10", new double[]{480, 150, 750, 700}));
//        chart.addData(new ModelChart("Tháng 11", new double[]{350, 540, 300, 150}));
//        chart.addData(new ModelChart("Tháng 12", new double[]{190, 280, 81, 200}));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        FlatLightLaf.setup();
        Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
        setMaximumSize(DimMax);
        setTitle("Báo Cáo Doanh Thu");
        setLocationRelativeTo(null);
        setExtendedState(MAXIMIZED_BOTH);

        chart = new com.gearvmdesktop.barchart.chart.Chart();
        jTextField = new javax.swing.JTextField(15);
        jButton1 = new javax.swing.JButton();
        Box box = Box.createHorizontalBox();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        chart.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        chart.setSize(1500, 1000);

        jTextField.setText("2023");
        jButton1.setText("Thống Kê Theo Năm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    jButton1ActionPerformed(evt);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        box.add(Box.createHorizontalStrut(1200));
        box.add(jTextField);
        box.add(Box.createHorizontalStrut(50));
        box.add(jButton1);
        box.add(Box.createHorizontalStrut(50));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(box)
                                        .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, DimMax.width - 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        )
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(30, Short.MAX_VALUE)
                                .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, DimMax.height - 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(box)
                                .addGap(10, 10, 10))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        chart.start();
    }//GEN-LAST:event_formWindowOpened

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) throws IOException {//GEN-FIRST:event_jButton1ActionPerformed
        chart.clear();
        List<MonthlyFinanceReportResponseModel> financeReportList = getMonthlyFinanceReports(jTextField.getText());
        for (MonthlyFinanceReportResponseModel finaceReport : financeReportList) {
            chart.addData(new ModelChart("Tháng " + finaceReport.getMonth(),
                    new double[]{finaceReport.getRevenue(), finaceReport.getCost(),
                            finaceReport.getProfit(), finaceReport.getLoss()}));
        }
        chart.start();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmBaoCaoDoanhThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmBaoCaoDoanhThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmBaoCaoDoanhThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmBaoCaoDoanhThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new FrmBaoCaoDoanhThu().setVisible(true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.gearvmdesktop.barchart.chart.Chart chart;
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables

    private List<MonthlyFinanceReportResponseModel> getMonthlyFinanceReports(String year) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        BufferedReader rd = ReportService.getMonthlyFinanceReports(year);
        return Arrays.asList(mapper.readValue(rd, MonthlyFinanceReportResponseModel[].class));
    }
}

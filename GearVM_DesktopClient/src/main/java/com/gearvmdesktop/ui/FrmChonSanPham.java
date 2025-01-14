/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.gearvmdesktop.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formdev.flatlaf.FlatLightLaf;
import com.gearvmdesktop.service.ProductService;
import com.gearvmstore.GearVM.model.Product;
import org.json.JSONException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class FrmChonSanPham extends JFrame implements ActionListener, MouseListener {
    private String newProductId;
    private static final String tableName = "products/";
    private static JTextField txtTim;
    private static JTable tableSanPham;
    private static DefaultTableModel modelSanPham;
    private final JButton btnChonSanPham;
    private final JPanel pnExcel;
    private final JButton btnImport;
    private final JButton btnExport;
    private final JButton btnSave;
    private final JButton btnCancel;
    private JComboBox<String> cmbChon;
    private JButton btnTim;
    private JButton btnSua;
    private JButton btnThem;
    private JButton btnXoa;
    private JButton btnChiTiet;
    private Label lblDonGia;
    private Label lblNhaCungCap;
    private Label lblSoLuong;
    private Label lblTenSanPham;
    private Label lblLoaiHang;
    private Label lblMaSanPham;
    private JPanel pnChucNang;
    private JPanel pnThongTin;
    private JScrollPane pntblHangHoa;
    private JTextField txtDonGia;
    private JTextField txtNhaCungCap;
    private JTextField txtSoLuong;
    private JTextField txtTenSanPham;
    private JTextField txtLoaiHang;
    private JPanel pnlTimKiem;
    private JTextField txtMaSanPham;

    public FrmChonSanPham() throws IOException {
        super("Chọn Sản Phẩm");

        FlatLightLaf.setup();
        Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
        setMaximumSize(DimMax);
        setExtendedState(MAXIMIZED_BOTH);

        pntblHangHoa = new JScrollPane();
        tableSanPham = new JTable();
        pnlTimKiem = new JPanel();
        pnThongTin = new JPanel();
        lblMaSanPham = new Label();
        txtMaSanPham = new JTextField();
        lblTenSanPham = new Label();
        txtTenSanPham = new JTextField();
        lblLoaiHang = new Label();
        txtLoaiHang = new JTextField();
        lblNhaCungCap = new Label();
        txtNhaCungCap = new JTextField();
        lblDonGia = new Label();
        txtDonGia = new JTextField();
        lblSoLuong = new Label();
        txtSoLuong = new JTextField();

        pnChucNang = new JPanel();
        btnThem = new JButton();
        btnSua = new JButton();
        btnXoa = new JButton();
        btnChiTiet = new JButton();

        pnExcel = new JPanel();
        btnImport = new JButton();
        btnExport = new JButton();
        btnSave = new JButton();
        btnCancel = new JButton();

        String[] header = {" Mã Sản Phẩm", "Tên Sản Phẩm", "Loại Hàng", "Nhà Cung Cấp", "Đơn Giá", "Số Lượng Tồn"};
        modelSanPham = new DefaultTableModel(header, 0);
        tableSanPham = new JTable(modelSanPham) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                Color color1 = new Color(219, 243, 255);
                Color color2 = Color.WHITE;
                if (!c.getBackground().equals(getSelectionBackground())) {
                    Color coleur = (row % 2 == 0 ? color1 : color2);
                    c.setBackground(coleur);
                    coleur = null;
                }
                return c;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableSanPham.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        tableSanPham.setGridColor(getBackground());
        tableSanPham.setRowHeight(tableSanPham.getRowHeight() + 20);
        tableSanPham.setSelectionBackground(new Color(255, 255, 128));
        tableSanPham.setSelectionForeground(Color.BLACK);
        JTableHeader tableHeader = tableSanPham.getTableHeader();
        tableHeader.setBackground(new Color(0, 148, 224));
        tableHeader.setFont(new Font("Tahoma", Font.BOLD, 12));
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setPreferredSize(new Dimension(WIDTH, 30));
        tableSanPham.setColumnSelectionAllowed(false);
        tableSanPham.setName("tblThongTinNhanVien"); // NOI18N
        pntblHangHoa.setViewportView(tableSanPham);
        tableSanPham.getColumnModel().getSelectionModel()
                .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        pnThongTin.setBorder(BorderFactory.createTitledBorder("Thông tin chi tiết:"));
        pnThongTin.setToolTipText("Info of selected table object");

        lblMaSanPham.setText("Mã Sản Phẩm:");

        lblTenSanPham.setText("Tên Sản Phẩm:");

        lblLoaiHang.setText("Loại hàng");

        lblNhaCungCap.setText("Nhà cung cấp");

        lblDonGia.setText("Đơn giá");

        lblSoLuong.setText("Số lượng");

        GroupLayout pnThongTinLayout = new GroupLayout(pnThongTin);
        pnThongTin.setLayout(pnThongTinLayout);
        pnThongTinLayout.setHorizontalGroup(pnThongTinLayout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(pnThongTinLayout.createSequentialGroup().addContainerGap()
                        .addGroup(pnThongTinLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lblNhaCungCap, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblLoaiHang, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblTenSanPham, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblMaSanPham, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblDonGia, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblSoLuong, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnThongTinLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(pnThongTinLayout.createSequentialGroup().addComponent(txtMaSanPham,
                                        GroupLayout.PREFERRED_SIZE, 169,
                                        GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtSoLuong, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtDonGia, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTenSanPham, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtLoaiHang, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtNhaCungCap, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap()));
        pnThongTinLayout.setVerticalGroup(pnThongTinLayout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(pnThongTinLayout.createSequentialGroup().addContainerGap().addGroup(pnThongTinLayout
                                .createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addGroup(pnThongTinLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(txtMaSanPham, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblMaSanPham, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnThongTinLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(txtTenSanPham, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblTenSanPham, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnThongTinLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(txtLoaiHang, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblLoaiHang, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnThongTinLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(txtNhaCungCap, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblNhaCungCap, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnThongTinLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(txtDonGia, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblDonGia, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnThongTinLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(txtSoLuong, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblSoLuong, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(28, Short.MAX_VALUE)));

        pnChucNang.setBorder(BorderFactory.createTitledBorder("Chức năng:"));
        pnExcel.setBorder(BorderFactory.createTitledBorder("Xử lý excel:"));

        ClassLoader classLoader = getClass().getClassLoader();
        URL iconThem = classLoader.getResource("assets/them.png");
        URL iconSua = classLoader.getResource("assets/capnhat.png");
        URL iconXoa = classLoader.getResource("assets/xoa.png");
        URL iconChiTiet = classLoader.getResource("assets/info.png");
        URL iconXuatFile = classLoader.getResource("assets/xuatexcel.png");
        URL iconNhapFile = classLoader.getResource("assets/docfile.png");
        URL iconLuu = classLoader.getResource("assets/luu.png");
        URL iconHuy = classLoader.getResource("assets/huy.png");
        URL iconChon = classLoader.getResource("assets/chon.png");
        URL iconTim = classLoader.getResource("assets/timkiem.png");


        btnThem.setText("THÊM");
        btnThem.setIcon(new ImageIcon(iconThem));

        btnSua.setText("SỬA");
        btnSua.setIcon(new ImageIcon(iconSua));

        btnXoa.setText("XÓA");
        btnXoa.setIcon(new ImageIcon(iconXoa));

        btnChiTiet.setText("CHI TIẾT");
        btnChiTiet.setIcon(new ImageIcon(iconChiTiet));

        btnSave.setText("LƯU");
        btnSave.setIcon(new ImageIcon(iconLuu));

        btnCancel.setText("HỦY");
        btnCancel.setIcon(new ImageIcon(iconHuy));

        btnImport.setText("NHẬP FILE");
        btnImport.setIcon(new ImageIcon(iconNhapFile));

        btnExport.setText("XUẤT FILE");
        btnExport.setIcon(new ImageIcon(iconXuatFile));

        // Chức năng button
        btnThem.setBackground(new Color(0, 148, 224));
        btnThem.setForeground(Color.WHITE);
        btnThem.setFocusPainted(false);
        btnSua.setBackground(new Color(0, 148, 224));
        btnSua.setForeground(Color.WHITE);
        btnSua.setFocusPainted(false);
        btnXoa.setBackground(new Color(0, 148, 224));
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setFocusPainted(false);
        btnChiTiet.setBackground(new Color(0, 148, 224));
        btnChiTiet.setForeground(Color.WHITE);
        btnChiTiet.setFocusPainted(false);

        // Excel button
        btnSave.setBackground(new Color(0, 148, 224));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        btnCancel.setBackground(new Color(0, 148, 224));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFocusPainted(false);
        btnImport.setBackground(new Color(0, 148, 224));
        btnImport.setForeground(Color.WHITE);
        btnImport.setFocusPainted(false);
        btnExport.setBackground(new Color(0, 148, 224));
        btnExport.setForeground(Color.WHITE);
        btnExport.setFocusPainted(false);

        GroupLayout pnChucNangLayout = new GroupLayout(pnChucNang);
        pnChucNang.setLayout(pnChucNangLayout);
        pnChucNangLayout.setHorizontalGroup(pnChucNangLayout
                .createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addGroup(GroupLayout.Alignment.LEADING, pnChucNangLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnThem)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(btnSua)
                        .addGap(48, 48, 48))
                .addGroup(GroupLayout.Alignment.LEADING, pnChucNangLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnChiTiet)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(btnXoa)
                        .addGap(48, 48, 48)));

        pnChucNangLayout.setVerticalGroup(pnChucNangLayout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(pnChucNangLayout.createSequentialGroup().addGap(10, 10, 10)
                        .addGroup(pnChucNangLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(btnThem).addComponent(btnSua))
                        .addGap(5)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnChucNangLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(btnChiTiet).addComponent(btnXoa))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(15)));

        javax.swing.GroupLayout pnExcelLayout = new javax.swing.GroupLayout(pnExcel);
        pnExcel.setLayout(pnExcelLayout);
        pnExcelLayout.setHorizontalGroup(pnExcelLayout
                .createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addGroup(GroupLayout.Alignment.LEADING, pnExcelLayout.createSequentialGroup()
                        .addGap(50)
//                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnImport)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btnExport)
                        .addGap(50)));
//                .addGroup(GroupLayout.Alignment.LEADING, pnExcelLayout.createSequentialGroup()
//                        .addGap(40)
//                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnSave)
//                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btnCancel)
//                        .addGap(70)));
        pnExcelLayout.setVerticalGroup(pnExcelLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnExcelLayout.createSequentialGroup().addGap(10, 10, 10)
                        .addGroup(pnExcelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                                .addComponent(btnImport)
                                .addComponent(btnExport))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5)));
//                        .addGroup(pnExcelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                                .addComponent(btnSave).addComponent(btnCancel))
//                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                        .addGap(15)));

        pnThongTin.setBackground(new Color(219, 243, 255));
        pnChucNang.setBackground(new Color(219, 243, 255));
        pnExcel.setBackground(new Color(219, 243, 255));

        Box b = Box.createHorizontalBox();
        String[] tim = {"Mã Sản Phẩm", "Tên Sản Phẩm", "Loại Hàng", "Nhà Cung Cấp", "Đơn Giá", "Số Lượng Tồn"};
        cmbChon = new JComboBox<String>(tim);
        txtTim = new JTextField(15);
        cmbChon.setSize(20, txtTim.getPreferredSize().height);
        btnTim = new JButton("TÌM KIẾM", new ImageIcon(iconTim));
        btnTim.setBackground(new Color(0, 148, 224));
        btnTim.setForeground(Color.WHITE);
        btnTim.setFocusPainted(false);
        btnChonSanPham = new JButton("CHỌN SẢN PHẨM", new ImageIcon(iconChon));
        btnChonSanPham.setBackground(new Color(0, 148, 224));
        btnChonSanPham.setForeground(Color.WHITE);
        btnChonSanPham.setFocusPainted(false);

//        b.add(cmbChon);
//        b.add(Box.createHorizontalStrut(10));
        b.add(txtTim);
        b.add(Box.createHorizontalStrut(10));
        b.add(btnTim);
        b.add(Box.createHorizontalStrut(30));
        b.add(btnChonSanPham);
        b.add(Box.createHorizontalStrut(30));
        pnlTimKiem.add(b);

        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        panel.add(getContentPane());
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(pntblHangHoa, GroupLayout.DEFAULT_SIZE, 950, Short.MAX_VALUE)
                                .addComponent(pnlTimKiem))
                        .addContainerGap(20, 20)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addGroup(layout
                                        .createSequentialGroup()
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pnThongTin, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap())
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(pnChucNang, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(pnExcel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                ))));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup().addContainerGap()
                                        .addComponent(pntblHangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, 670,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(pnlTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2))
                                .addGroup(layout.createSequentialGroup().addGap(14, 14, 14)
                                        .addComponent(pnThongTin, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(pnChucNang, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 20, 20)
                                        .addComponent(pnExcel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 27, Short.MAX_VALUE)))
                        .addContainerGap()));

        pack();

        pntblHangHoa.setBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "DANH SÁCH SẢN PHẨM: "));
        lblMaSanPham.setFont(new Font("Tahoma", Font.BOLD, 12));
        txtMaSanPham.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblTenSanPham.setFont(new Font("Tahoma", Font.BOLD, 12));
        txtTenSanPham.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblLoaiHang.setFont(new Font("Tahoma", Font.BOLD, 12));
        txtLoaiHang.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNhaCungCap.setFont(new Font("Tahoma", Font.BOLD, 12));
        txtNhaCungCap.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblDonGia.setFont(new Font("Tahoma", Font.BOLD, 12));
        txtDonGia.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblSoLuong.setFont(new Font("Tahoma", Font.BOLD, 12));
        txtSoLuong.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnThem.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnSua.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnXoa.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnChiTiet.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnImport.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnExport.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnSave.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnCancel.setFont(new Font("Tahoma", Font.BOLD, 12));
        txtTim.setFont(new Font("Tahoma", Font.BOLD, 12));
        cmbChon.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnTim.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnChonSanPham.setFont(new Font("Tahoma", Font.BOLD, 12));
        tableSanPham.setFont(new Font("Tahoma", Font.PLAIN, 14));

        btnSave.setEnabled(false);
        btnCancel.setEnabled(false);

        tableSanPham.getColumnModel().getColumn(0).setPreferredWidth(20);
        tableSanPham.getColumnModel().getColumn(1).setPreferredWidth(165);


        txtSoLuong.setEditable(false);
        txtMaSanPham.setEditable(false);

        tableSanPham.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableSanPham.setDefaultEditor(Object.class, null);
        tableSanPham.getTableHeader().setReorderingAllowed(false);

        btnThem.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);
        btnTim.addActionListener(this);
        btnChiTiet.addActionListener(this);
        btnChonSanPham.addActionListener(this);
        btnTim.addActionListener(this);
        tableSanPham.addMouseListener(this);

        readDatabaseToTable();
        GUI.disableWarning();

        setContentPane(panel);
        setVisible(true);
    }

    private boolean validInput() {
        String tenLk = txtTenSanPham.getText();
        String loaiHang = txtLoaiHang.getText();
        String nhaCC = txtNhaCungCap.getText();
        String gialk = txtDonGia.getText();
//        String soLuong = txtSoLuong.getText();
        if (tenLk.trim().length() > 0) {
            if (!(tenLk.matches("[^\\@\\!\\$\\^\\&\\*\\(\\)]+"))) {
                JOptionPane.showMessageDialog(this, "Tên Sản Phẩm không chứa ký tự đặc biệt", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Tên Sản Phẩm không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (loaiHang.trim().length() > 0) {
            if (!(tenLk.matches("[^\\@\\!\\$\\^\\&\\*\\(\\)]+"))) {
                JOptionPane.showMessageDialog(this, "Tên loại hàng không chứa ký tự đặc biệt", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Tên loại hàng không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (nhaCC.trim().length() > 0) {
            if (!(tenLk.matches("[^\\@\\!\\$\\^\\&\\*\\(\\)]+"))) {
                JOptionPane.showMessageDialog(this, "Tên nhà cung cấp không chứa ký tự đặc biệt", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Tên cung cấp không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (gialk.trim().length() > 0) {
            try {
                double x = Double.parseDouble(gialk);
                if (x <= 0) {
                    JOptionPane.showMessageDialog(this, "Giá Sản Phẩm phải lớn hơn 0", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error: Giá Sản Phẩm phải nhập số", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Giá Sản Phẩm không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThem)) {
            if (!validInput()) {
                return;
            } else {
                int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc không?", "Cảnh báo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    try {
                        if (postRequest()) {
                            JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!", "Thành công",
                                    JOptionPane.INFORMATION_MESSAGE);
                            readDatabaseToTable();
                        } else {
                            JOptionPane.showMessageDialog(this, "Thêm sản phẩm thất bại!", "Thất bại",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException | JSONException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
        if (o.equals(btnSua)) {
            if (!validInput()) {
                return;
            } else {
                int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc không?", "Cảnh báo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    try {
                        if (putRequest()) {
                            JOptionPane.showMessageDialog(this, "Sửa sản phẩm mã số " + txtMaSanPham.getText() + " thành công!", "Thành công",
                                    JOptionPane.INFORMATION_MESSAGE);
                            readDatabaseToTable();
                        } else {
                            JOptionPane.showMessageDialog(this, "Sửa sản phẩm mã số " + txtMaSanPham.getText() + " thất bại!", "Thất bại",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException | JSONException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
        if (o.equals(btnXoa)) {
            int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc không?", "Cảnh báo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                try {
                    if (deleteRequest()) {
                        JOptionPane.showMessageDialog(this, "Xóa sản phẩm mã số " + txtMaSanPham.getText() + " thành công!", "Thành công",
                                JOptionPane.INFORMATION_MESSAGE);
                        readDatabaseToTable();
                        emptyTextField();
                    } else {
                        JOptionPane.showMessageDialog(this, "Xóa sản phẩm mã số " + txtMaSanPham.getText() + " thất bại!", "Thất bại",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        if (o.equals(btnChiTiet)) {
            try {
                new FrmChiTietSanPham(getRequest());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (o.equals(btnChonSanPham)) {
            int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc không?", "Cảnh báo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                selectProduct();
            }
        }
        if (o.equals(btnTim)) {
            try {
                readDatabaseFilterToTable();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tableSanPham.getSelectedRow();
        txtMaSanPham.setText(modelSanPham.getValueAt(row, 0).toString().trim());
        txtTenSanPham.setText(modelSanPham.getValueAt(row, 1).toString().trim());
        txtLoaiHang.setText(modelSanPham.getValueAt(row, 2).toString().trim());
        txtNhaCungCap.setText(modelSanPham.getValueAt(row, 3).toString().trim());
        String tien[] = modelSanPham.getValueAt(row, 4).toString().split(",");
        String donGia = "";
        for (int i = 0; i < tien.length; i++)
            donGia += tien[i];
        txtDonGia.setText(donGia);
        txtSoLuong.setText(modelSanPham.getValueAt(row, 5).toString().trim());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getClickCount() == 2 && tableSanPham.getSelectedRow() != -1) {
            int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc không?", "Cảnh báo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                selectProduct();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void readDatabaseToTable() throws IOException {
        emptyTable();
        ObjectMapper mapper = new ObjectMapper();
        // Get all products
        BufferedReader rd = ProductService.getAllRequest(tableName + "/get-all");
        List<Product> listProduct = Arrays.asList(mapper.readValue(rd, Product[].class));
        DecimalFormat df = new DecimalFormat("#,##0");
        for (Product p : listProduct) {
            modelSanPham.addRow(new Object[]{p.getId(), p.getName(), p.getType(),
                    p.getBrand(), df.format(p.getPrice()), p.getQuantity()});
        }
    }

    public static void readDatabaseFilterToTable() throws IOException {
        emptyTable();
        ObjectMapper mapper = new ObjectMapper();
        // Get all products
        BufferedReader rd = ProductService.getAllByFilterRequest(tableName, txtTim.getText());
        List<Product> listProduct = Arrays.asList(mapper.readValue(rd, Product[].class));
        DecimalFormat df = new DecimalFormat("#,##0");
        for (Product p : listProduct) {
            modelSanPham.addRow(new Object[]{p.getId(), p.getName(), p.getType(),
                    p.getBrand(), df.format(p.getPrice()), p.getQuantity()});
        }
    }

    public void selectProduct() {
        int row = tableSanPham.getSelectedRow();
        String productId = tableSanPham.getValueAt(row, 0).toString().trim();
        String productName = tableSanPham.getValueAt(row, 1).toString().trim();

        FrmKhoHang.setTextFieldAfterSelectingProduct(productId, productName);
        setVisible(false);
        dispose();
    }

    private void emptyTextField() {
        txtMaSanPham.setText(null);
        txtTenSanPham.setText(null);
        txtDonGia.setText(null);
        txtSoLuong.setText(null);
        txtNhaCungCap.setText(null);
        txtLoaiHang.setText(null);

    }

    public Product getRequest() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        BufferedReader rd = ProductService.getRequest(tableName, txtMaSanPham.getText());
        return mapper.readValue(rd, Product.class);
    }

    public boolean postRequest() throws IOException, JSONException {
        ObjectMapper mapper = new ObjectMapper();
        Product p = new Product();
        p.setName(txtTenSanPham.getText());
        p.setType(txtLoaiHang.getText());
        p.setBrand(txtNhaCungCap.getText());
        p.setQuantity(Integer.parseInt(txtSoLuong.getText()));
        p.setPrice(Double.parseDouble(txtDonGia.getText()));
        BufferedReader rd = ProductService.postRequest(p);
        Product product = mapper.readValue(rd, Product.class);
        return product != null;
    }

    public boolean putRequest() throws IOException, JSONException {
        Product p = new Product();
        p.setId(Long.parseLong(txtMaSanPham.getText()));
        p.setName(txtTenSanPham.getText());
        p.setType(txtLoaiHang.getText());
        p.setBrand(txtNhaCungCap.getText());
        p.setQuantity(Integer.parseInt(txtSoLuong.getText()));
        p.setPrice(Double.parseDouble(txtDonGia.getText()));
        return ProductService.putRequest(p);
    }

    public boolean deleteRequest() throws IOException {
        Product p = new Product();
        p.setId(Long.parseLong(txtMaSanPham.getText()));
        return ProductService.deleteRequest(p);
    }

    public static void emptyTable() {
        DefaultTableModel dm = (DefaultTableModel) tableSanPham.getModel();
        dm.setRowCount(0);
    }

}

package com.gearvmdesktop.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.formdev.flatlaf.FlatLightLaf;
import com.gearvmdesktop.service.OrderService;
import com.gearvmdesktop.service.ProductService;
import com.gearvmstore.GearVM.model.OrderStatus;
import com.gearvmstore.GearVM.model.PaymentMethod;
import com.gearvmstore.GearVM.model.Product;
import com.gearvmstore.GearVM.model.dto.order.PrintOrderDto;
import com.gearvmstore.GearVM.model.dto.order.UpdateOrderStatusAndEmployee;
import com.gearvmstore.GearVM.model.response.EmployeeResponseModel;
import com.gearvmstore.GearVM.model.response.GetOrderResponse;
import com.gearvmstore.GearVM.model.response.OrderItemResponseModel;
import com.gearvmstore.GearVM.model.response.ProductResponseModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.json.JSONException;
import org.springframework.util.ResourceUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FrmChiTietDonHang extends JFrame implements ActionListener {
    private static final String tableNameProduct = "products/";
    private static final String tableNameOrder = "orders/";
    private static JTable tableSanPham;
    private static DefaultTableModel modelSanPham;
    private final JTextField txtMaDonHang;
    private final JLabel lblTenKhachHang;
    private final JLabel lblMaDonHang;
    private final JTextField txtTenKhachHang;
    private final JLabel lblSdtKhachHang;
    private final JTextField txtSdtKhachHang;
    private final JLabel lblMaThanhToan;
    private final JTextField txtMaThanhToan;
    private final JLabel lblNgayLapDonHang;
    private final JTextField txtNgayLapDonHang;
    private final JLabel lblNgaySuaDonHang;
    private final JTextField txtNgaySuaDonHang;
    private final JLabel lblMaNhanVien;
    private final JTextField txtMaNhanVien;
    private final JLabel lblTenNhanVien;
    private final JTextField txtTenNhanVien;
    private final JLabel lblTongTien;
    private final JTextField txtTongTien;
    private final JComboBox<String> cmbTrangThai;
    private final JLabel lblTrangThai;
    private final JButton btnXacNhan;
    private final JButton btnTuChoi;
    private final JButton btnThanhCong;
    private final JButton btnThatBai;

    private final JButton btnPrint;
    private final JButton btnXemThanhToan;
    private final JButton btnThayDoiTrangThai;
    private final JLabel lblDiaChi;
    private final JTextField txtDiaChi;
    private final JLabel lblEmail;
    private final JTextField txtEmail;
    private final JLabel lblPhuongThucThanhToan;
    private final JTextField txtPhuongThucThanhToan;
    private final JLabel lblHinhThucMuaHang;
    private final JTextField txtHinhThucMuaHang;
    private final JLabel lblMaKhuyenMai;
    private final JTextField txtMaKhuyenMai;
    private final JLabel lblPhanTramKhuyenMai;
    private final JTextField txtPhanTramKhuyenMai;

    public FrmChiTietDonHang(GetOrderResponse getOrderResponse) throws IOException {
        super("Chi Tiết Đơn Hàng");

        FlatLightLaf.setup();
        Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
        setMaximumSize(DimMax);
        setExtendedState(MAXIMIZED_BOTH);

        Box b = Box.createHorizontalBox();
        Box b1 = Box.createVerticalBox();
        Box b2 = Box.createHorizontalBox();

        String[] colHeader = {"Mã Sản Phẩm", "Tên Sản Phẩm", "Loại Hàng", "Nhà Cung Cấp", "Đơn Giá", "Số Lượng Sản Phẩm", "Số Lượng Tồn Kho", "Thành Tiền"};
        modelSanPham = new DefaultTableModel(colHeader, 0);
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
        tableSanPham.setGridColor(getBackground());
        tableSanPham.setRowHeight(tableSanPham.getRowHeight() + 20);
        tableSanPham.setSelectionBackground(new Color(255, 255, 128));
        tableSanPham.setSelectionForeground(Color.BLACK);
        JTableHeader tableHeader = tableSanPham.getTableHeader();
        tableHeader.setBackground(new Color(0, 148, 224));
        tableHeader.setFont(new Font("Tahoma", Font.BOLD, 12));
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setPreferredSize(new Dimension(WIDTH, 30));
        tableSanPham.setBounds(50, 50, 700, 600);
        tableSanPham.setPreferredScrollableViewportSize(new Dimension(800, 500));

        JScrollPane tblscroll = new JScrollPane(tableSanPham);
        tblscroll.setBorder(BorderFactory.createTitledBorder("GIỎ HÀNG: "));
        b1.add(Box.createVerticalStrut(10));
        b1.add(tblscroll);
        b1.add(Box.createVerticalStrut(20));

        Box b3 = Box.createVerticalBox();
        Box b4 = Box.createHorizontalBox();
        Box b5 = Box.createHorizontalBox();
        Box b6 = Box.createHorizontalBox();
        Box b7 = Box.createHorizontalBox();
        Box b8 = Box.createHorizontalBox();
        Box b9 = Box.createHorizontalBox();
        Box b10 = Box.createHorizontalBox();
        Box b11 = Box.createHorizontalBox();
        Box b12 = Box.createHorizontalBox();
        Box b13 = Box.createHorizontalBox();

        Box b17 = Box.createHorizontalBox();
        Box b18 = Box.createHorizontalBox();
        Box b19 = Box.createHorizontalBox();
        Box b20 = Box.createHorizontalBox();
        Box b21 = Box.createHorizontalBox();
        Box b22 = Box.createHorizontalBox();
        Box b23 = Box.createHorizontalBox();

        b2.add(Box.createHorizontalStrut(100));
        b2.add(b3);

        b4.add(lblMaDonHang = new JLabel("Mã Đơn Hàng:"));
        b4.add(Box.createHorizontalStrut(10));
        b4.add(txtMaDonHang = new JTextField());
        b3.add(b4);

        // Shipping detail
        b5.add(lblTenKhachHang = new JLabel("Tên Khách Hàng:"));
        b5.add(Box.createHorizontalStrut(10));
        b5.add(txtTenKhachHang = new JTextField());
        b3.add(b5);

        b6.add(lblSdtKhachHang = new JLabel("SDT Khách Hàng:"));
        b6.add(Box.createHorizontalStrut(10));
        b6.add(txtSdtKhachHang = new JTextField());
        b3.add(b6);

        b17.add(lblDiaChi = new JLabel("Địa Chỉ Giao Hàng:"));
        b17.add(Box.createHorizontalStrut(10));
        b17.add(txtDiaChi = new JTextField());
        b3.add(b17);

        b18.add(lblEmail = new JLabel("Email Khách Hàng:"));
        b18.add(Box.createHorizontalStrut(10));
        b18.add(txtEmail = new JTextField());
        b3.add(b18);

        b7.add(lblMaThanhToan = new JLabel("Mã Thanh Toán Stripe:"));
        b7.add(Box.createHorizontalStrut(10));
        b7.add(txtMaThanhToan = new JTextField());
        b3.add(b7);

        b20.add(lblHinhThucMuaHang = new JLabel("Hình Thức Mua Hàng:"));
        b20.add(Box.createHorizontalStrut(10));
        b20.add(txtHinhThucMuaHang = new JTextField());
        b3.add(b20);

        b8.add(lblNgayLapDonHang = new JLabel("Ngày Lập Đơn Hàng:"));
        b8.add(Box.createHorizontalStrut(10));
        b8.add(txtNgayLapDonHang = new JTextField());
        b3.add(b8);


        b9.add(lblNgaySuaDonHang = new JLabel("Ngày Sửa Đơn Hàng:"));
        b9.add(Box.createHorizontalStrut(10));
        b9.add(txtNgaySuaDonHang = new JTextField());
        b3.add(b9);

        b10.add(lblMaNhanVien = new JLabel("Mã Nhân Viên Phụ Trách:"));
        b10.add(Box.createHorizontalStrut(10));
        b10.add(txtMaNhanVien = new JTextField());
        b3.add(b10);

        b11.add(lblTenNhanVien = new JLabel("Tên Nhân Viên Phụ Trách:"));
        b11.add(Box.createHorizontalStrut(10));
        b11.add(txtTenNhanVien = new JTextField());
        b3.add(b11);

        b19.add(lblPhuongThucThanhToan = new JLabel("Phương Thức Thanh Toán:"));
        b19.add(Box.createHorizontalStrut(10));
        b19.add(txtPhuongThucThanhToan = new JTextField());
        b3.add(b19);

        b22.add(lblMaKhuyenMai = new JLabel("Mã Khuyến Mãi:"));
        b22.add(Box.createHorizontalStrut(10));
        b22.add(txtMaKhuyenMai = new JTextField());
        b3.add(b22);

        b23.add(lblPhanTramKhuyenMai = new JLabel("Phần Trăm Khuyến Mãi:"));
        b23.add(Box.createHorizontalStrut(10));
        b23.add(txtPhanTramKhuyenMai = new JTextField());
        b3.add(b23);

        b12.add(lblTongTien = new JLabel("Tổng Tiền Thanh Toán:"));
        b12.add(Box.createHorizontalStrut(10));
        b12.add(txtTongTien = new JTextField());
        b3.add(b12);

        String[] trangThai = {"Đang chờ thanh toán", "Đang chờ xác nhận", "Đang giao hàng", "Giao hàng thành công", "Giao hàng thất bại", "Đơn hàng bị từ chối"};
        cmbTrangThai = new JComboBox<String>(trangThai);
        b13.add(lblTrangThai = new JLabel("Trạng Thái Đơn Hàng:"));
        b13.add(Box.createHorizontalStrut(10));
        b13.add(cmbTrangThai);
        b3.add(b13);

        b3.add(Box.createVerticalStrut(5));

        Box b14 = Box.createHorizontalBox();
        Box b15 = Box.createHorizontalBox();
        Box b16 = Box.createHorizontalBox();

        ClassLoader classLoader = getClass().getClassLoader();
        URL iconXacNhan = classLoader.getResource("assets/xacNhanGiaoHang.png");

        b14.add(btnXacNhan = new JButton("XÁC NHẬN GIAO HÀNG", new ImageIcon(iconXacNhan)));
        btnXacNhan.setBackground(new Color(0, 148, 224));
        btnXacNhan.setForeground(Color.WHITE);
        btnXacNhan.setFocusPainted(false);


        URL iconTuChoi = classLoader.getResource("assets/TuChoiGiaoHang.png");

        b14.add(Box.createHorizontalStrut(80));
        b14.add(btnTuChoi = new JButton("TỪ CHỐI GIAO HÀNG", new ImageIcon(iconTuChoi)));
        btnTuChoi.setBackground(new Color(0, 148, 224));
        btnTuChoi.setForeground(Color.WHITE);
        btnTuChoi.setFocusPainted(false);
        b3.add(b14);

        URL iconThanhCong = classLoader.getResource("assets/GiaoHangThanhCong.png");

        b15.add(btnThanhCong = new JButton("GIAO HÀNG THÀNH CÔNG", new ImageIcon(iconThanhCong)));
        btnThanhCong.setBackground(new Color(0, 148, 224));
        btnThanhCong.setForeground(Color.WHITE);
        btnThanhCong.setFocusPainted(false);

        URL iconThatBai = classLoader.getResource("assets/giaohangthatbai2.png");

        b15.add(Box.createHorizontalStrut(65));
        b15.add(btnThatBai = new JButton("GIAO HÀNG THẤT BẠI", new ImageIcon(iconThatBai)));
        btnThatBai.setBackground(new Color(0, 148, 224));
        btnThatBai.setForeground(Color.WHITE);
        btnThatBai.setFocusPainted(false);
        b3.add(b15);

        URL iconTrangThai = classLoader.getResource("assets/trangthaigiaohang.png");

        b16.add(btnThayDoiTrangThai = new JButton("THAY ĐỔI TRẠNG THÁI ĐƠN HÀNG", new ImageIcon(iconTrangThai)));
        btnThayDoiTrangThai.setBackground(new Color(0, 148, 224));
        btnThayDoiTrangThai.setForeground(Color.WHITE);
        btnThayDoiTrangThai.setFocusPainted(false);

        URL iconChiTiet = classLoader.getResource("assets/chitiet.png");

        b16.add(Box.createHorizontalStrut(20));
        b16.add(btnXemThanhToan = new JButton("XEM CHI TIẾT THANH TOÁN", new ImageIcon(iconChiTiet)));
        btnXemThanhToan.setBackground(new Color(0, 148, 224));
        btnXemThanhToan.setForeground(Color.WHITE);
        btnXemThanhToan.setFocusPainted(false);
        b3.add(b16);

        b21.add(Box.createHorizontalStrut(20));
        b21.add(btnPrint = new JButton("IN HÓA ĐƠN", new ImageIcon(iconChiTiet)));
        btnPrint.setBackground(new Color(0, 148, 224));
        btnPrint.setForeground(Color.WHITE);
        btnPrint.setFocusPainted(false);
        b3.add(b21);

        b2.add(Box.createHorizontalStrut(50));
        b.add(Box.createHorizontalStrut(20));
        b.add(b1);
        b.add(b2);
        b.setSize(1400, 400);
        add(b);

        lblMaDonHang.setPreferredSize(lblTenNhanVien.getPreferredSize());
        lblTrangThai.setPreferredSize(lblTenNhanVien.getPreferredSize());
        lblNgayLapDonHang.setPreferredSize(lblTenNhanVien.getPreferredSize());
        lblMaNhanVien.setPreferredSize(lblTenNhanVien.getPreferredSize());
        lblNgaySuaDonHang.setPreferredSize(lblTenNhanVien.getPreferredSize());
        lblMaThanhToan.setPreferredSize(lblTenNhanVien.getPreferredSize());
        lblTenKhachHang.setPreferredSize(lblTenNhanVien.getPreferredSize());
        lblTongTien.setPreferredSize(lblTenNhanVien.getPreferredSize());
        lblSdtKhachHang.setPreferredSize(lblTenNhanVien.getPreferredSize());
        lblDiaChi.setPreferredSize(lblTenNhanVien.getPreferredSize());
        lblEmail.setPreferredSize(lblTenNhanVien.getPreferredSize());
        lblPhuongThucThanhToan.setPreferredSize(lblTenNhanVien.getPreferredSize());
        lblHinhThucMuaHang.setPreferredSize(lblTenNhanVien.getPreferredSize());
        lblMaKhuyenMai.setPreferredSize(lblTenNhanVien.getPreferredSize());
        lblPhanTramKhuyenMai.setPreferredSize(lblTenNhanVien.getPreferredSize());

        b4.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        b5.setBorder(new EmptyBorder(new Insets(5, 10, 10, 10)));
        b6.setBorder(new EmptyBorder(new Insets(5, 10, 10, 10)));
        b7.setBorder(new EmptyBorder(new Insets(5, 10, 10, 10)));
        b8.setBorder(new EmptyBorder(new Insets(5, 10, 10, 10)));
        b9.setBorder(new EmptyBorder(new Insets(5, 10, 10, 10)));
        b10.setBorder(new EmptyBorder(new Insets(5, 10, 10, 10)));
        b11.setBorder(new EmptyBorder(new Insets(5, 10, 10, 10)));
        b12.setBorder(new EmptyBorder(new Insets(5, 10, 10, 10)));
        b13.setBorder(new EmptyBorder(new Insets(5, 10, 10, 10)));
        b14.setBorder(new EmptyBorder(new Insets(5, 10, 10, 45)));
        b15.setBorder(new EmptyBorder(new Insets(5, 10, 10, 45)));
        b16.setBorder(new EmptyBorder(new Insets(5, 10, 10, 20)));
        b17.setBorder(new EmptyBorder(new Insets(5, 10, 10, 10)));
        b18.setBorder(new EmptyBorder(new Insets(5, 10, 10, 10)));
        b19.setBorder(new EmptyBorder(new Insets(5, 10, 10, 10)));
        b20.setBorder(new EmptyBorder(new Insets(5, 10, 10, 10)));
        b21.setBorder(new EmptyBorder(new Insets(5, 10, 10, 10)));
        b22.setBorder(new EmptyBorder(new Insets(5, 10, 10, 10)));
        b23.setBorder(new EmptyBorder(new Insets(5, 10, 10, 10)));

        btnXacNhan.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnTuChoi.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnThanhCong.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnThatBai.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnThayDoiTrangThai.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnXemThanhToan.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnPrint.setFont(new Font("Tahoma", Font.BOLD, 12));

        readDatabaseToTable(getOrderResponse);
        LoadDataToTextField(getOrderResponse);

        txtMaDonHang.setEditable(false);
        txtTenKhachHang.setEditable(false);
        txtSdtKhachHang.setEditable(false);
        txtDiaChi.setEditable(false);
        txtEmail.setEditable(false);
        txtMaThanhToan.setEditable(false);
        txtNgayLapDonHang.setEditable(false);
        txtNgaySuaDonHang.setEditable(false);
        txtMaNhanVien.setEditable(false);
        txtTenNhanVien.setEditable(false);
        txtPhuongThucThanhToan.setEditable(false);
        txtTongTien.setEditable(false);
        txtHinhThucMuaHang.setEditable(false);
        txtMaKhuyenMai.setEditable(false);
        txtPhanTramKhuyenMai.setEditable(false);

        btnThanhCong.addActionListener(this);
        btnThatBai.addActionListener(this);
        btnXacNhan.addActionListener(this);
        btnTuChoi.addActionListener(this);
        btnXemThanhToan.addActionListener(this);
        btnThayDoiTrangThai.addActionListener(this);
        btnPrint.addActionListener(this);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThayDoiTrangThai)) {
            int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc không?", "Cảnh báo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                try {
                    if (patchOrderStatusAndEmployee(GUI_NhanVien.getEmployeeInfo())) {
                        JOptionPane.showMessageDialog(this, "Sửa đơn hàng mã số " + txtMaDonHang.getText() + " thành công!", "Thành công",
                                JOptionPane.INFORMATION_MESSAGE);
                        refreshTextField();
                    } else {
                        JOptionPane.showMessageDialog(this, "Sửa đơn hàng mã số " + txtMaDonHang.getText() + " thất bại!", "Thất bại",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (IOException | JSONException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        if (o.equals(btnXacNhan)) {
            int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc không?", "Cảnh báo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                try {
                    if (patchOrderStatus(0, GUI_NhanVien.getEmployeeInfo())) {
                        JOptionPane.showMessageDialog(this, "Sửa đơn hàng mã số " + txtMaDonHang.getText() + " thành công!", "Thành công",
                                JOptionPane.INFORMATION_MESSAGE);
                        refreshTextField();
                    } else {
                        JOptionPane.showMessageDialog(this, "Sửa đơn hàng mã số " + txtMaDonHang.getText() + " thất bại!", "Thất bại",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (IOException | JSONException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        if (o.equals(btnTuChoi)) {
            int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc không?", "Cảnh báo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                try {
                    if (patchOrderStatus(1, GUI_NhanVien.getEmployeeInfo())) {
                        JOptionPane.showMessageDialog(this, "Sửa đơn hàng mã số " + txtMaDonHang.getText() + " thành công!", "Thành công",
                                JOptionPane.INFORMATION_MESSAGE);
                        refreshTextField();
                    } else {
                        JOptionPane.showMessageDialog(this, "Sửa đơn hàng mã số " + txtMaDonHang.getText() + " thất bại!", "Thất bại",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (IOException | JSONException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        if (o.equals(btnThanhCong)) {
            int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc không?", "Cảnh báo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                try {
                    if (patchOrderStatus(2, GUI_NhanVien.getEmployeeInfo())) {
                        JOptionPane.showMessageDialog(this, "Sửa đơn hàng mã số " + txtMaDonHang.getText() + " thành công!", "Thành công",
                                JOptionPane.INFORMATION_MESSAGE);
                        refreshTextField();
                    } else {
                        JOptionPane.showMessageDialog(this, "Sửa đơn hàng mã số " + txtMaDonHang.getText() + " thất bại!", "Thất bại",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (IOException | JSONException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        if (o.equals(btnThatBai)) {
            int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc không?", "Cảnh báo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                try {
                    if (patchOrderStatus(3, GUI_NhanVien.getEmployeeInfo())) {
                        JOptionPane.showMessageDialog(this, "Sửa đơn hàng mã số " + txtMaDonHang.getText() + " thành công!", "Thành công",
                                JOptionPane.INFORMATION_MESSAGE);
                        refreshTextField();
                    } else {
                        JOptionPane.showMessageDialog(this, "Sửa đơn hàng mã số " + txtMaDonHang.getText() + " thất bại!", "Thất bại",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (IOException | JSONException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        if (o.equals(btnXemThanhToan)) {
            openPaymentDetailOnStripe();
        }
        if (o.equals(btnPrint)) {

            try {
                printOrder();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (JRException ex) {
                throw new RuntimeException(ex);
            }

        }
    }

    private void printOrder() throws IOException, JRException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        BufferedReader rd = OrderService.getReportOrder(Long.parseLong(txtMaDonHang.getText().toString()));

        List<PrintOrderDto> printOrderDto = List.of(mapper.readValue(rd, PrintOrderDto[].class));

//        String path = "C:\\Users\\HP\\Desktop";

        JFileChooser fileDialog = new JFileChooser();
        fileDialog.setCurrentDirectory(new File(System.getProperty("user.dir")));
        //filter the files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF(.pdf)", ".pdf");
        fileDialog.setAcceptAllFileFilterUsed(false);
        fileDialog.addChoosableFileFilter(filter);
        int result = fileDialog.showSaveDialog(null);
        //if the user click on save in Jfilechooser
        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileDialog.getSelectedFile();
            String filePath = file.getAbsolutePath();
            if (!(filePath.endsWith(".pdf"))) {
                filePath += ".pdf";
            }
            try {
                //load file and compile it
                File jasperFile = ResourceUtils.getFile("classpath:InHoaDon.jrxml");
                JasperReport jasperReport = JasperCompileManager.compileReport(jasperFile.getAbsolutePath());
                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(printOrderDto);

                Map<String, Object> parameters = new HashMap<>();
                parameters.put("orderId", txtMaDonHang.getText().toString());
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

                JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);

                JOptionPane.showMessageDialog(null, "Ghi file thành công!!", "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ghi file thất bại!!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void readDatabaseToTable(GetOrderResponse getOrderResponse) throws IOException {
        emptyTable();
        DecimalFormat df = new DecimalFormat("#,##0");

        List<OrderItemResponseModel> orderItems = getOrderResponse.getOrderItems();
        for (OrderItemResponseModel orderItem : orderItems) {
            ProductResponseModel product = orderItem.getProduct();
            double rowPrice = orderItem.getPrice() * orderItem.getQuantity();
            Product productDb = getProductRequest(product.getId().toString());

            modelSanPham.addRow(new Object[]{product.getId(), product.getName(), product.getType(),
                    product.getBrand(), df.format(orderItem.getPrice()), orderItem.getQuantity(), productDb.getQuantity(), df.format(rowPrice)});
        }
    }

    public static void emptyTable() {
        DefaultTableModel dm = (DefaultTableModel) tableSanPham.getModel();
        dm.setRowCount(0);
    }

    public Product getProductRequest(String productId) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        BufferedReader rd = ProductService.getRequest(tableNameProduct, productId);
        return mapper.readValue(rd, Product.class);
    }

    public void refreshTextField() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        BufferedReader rd = OrderService.getRequest(tableNameOrder, txtMaDonHang.getText());
        GetOrderResponse order = mapper.readValue(rd, GetOrderResponse.class);

        LoadDataToTextField(order);
    }

    public void LoadDataToTextField(GetOrderResponse order) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("k:mm dd-MM-yyyy");
        DecimalFormat df = new DecimalFormat("#,##0");

        if (order.getOrderStatus() == OrderStatus.PAYMENT_PENDING) cmbTrangThai.setSelectedIndex(0);
        else if (order.getOrderStatus() == OrderStatus.PAYMENT_DONE) cmbTrangThai.setSelectedIndex(1);
        else if (order.getOrderStatus() == OrderStatus.SHIPPING) cmbTrangThai.setSelectedIndex(2);
        else if (order.getOrderStatus() == OrderStatus.SHIP_SUCCESS) cmbTrangThai.setSelectedIndex(3);
        else if (order.getOrderStatus() == OrderStatus.SHIP_FAIL) cmbTrangThai.setSelectedIndex(4);
        else if (order.getOrderStatus() == OrderStatus.REJECTED) cmbTrangThai.setSelectedIndex(5);

        String paymentMethod = null;
        if (order.getPayment().getPaymentMethod() == PaymentMethod.STRIPE)
            paymentMethod = "Chuyển khoản qua dịch vụ Stripe";
        else if (order.getPayment().getPaymentMethod() == PaymentMethod.BANK)
            paymentMethod = "Chuyển khoản qua ngân hàng";
        else if (order.getPayment().getPaymentMethod() == PaymentMethod.COD)
            paymentMethod = "Trả tiền khi giao hàng";
        else if (order.getPayment().getPaymentMethod() == PaymentMethod.MOMO)
            paymentMethod = "Chuyển khoản qua Momo";
        else if (order.getPayment().getPaymentMethod() == PaymentMethod.CASH)
            paymentMethod = "Thanh toán trực tiếp bằng tiền mặt";
        else if (order.getPayment().getPaymentMethod() == PaymentMethod.SWIPE_CARD)
            paymentMethod = "Thanh toán trực tiếp bằng quẹt thẻ ngân hàng";

        try {
            txtMaDonHang.setText(order.getId().toString());

            if (order.getShippingDetail() != null) {
                txtTenKhachHang.setText(order.getShippingDetail().getName());
                txtSdtKhachHang.setText(order.getShippingDetail().getPhoneNumber());
                txtDiaChi.setText(order.getShippingDetail().getAddress());
                txtEmail.setText(order.getShippingDetail().getEmail());

            } else {
                txtTenKhachHang.setText(order.getCustomer().getName());
                txtSdtKhachHang.setText(order.getCustomer().getPhoneNumber());
            }

            if (order.isDirect())
                txtHinhThucMuaHang.setText("Mua hàng tại cửa hàng");
            else
                txtHinhThucMuaHang.setText("Mua hàng qua website");

            txtMaThanhToan.setText(order.getPayment().getPaymentDescription());
            txtPhuongThucThanhToan.setText(paymentMethod);
            txtNgayLapDonHang.setText(dateFormat.format(order.getCreatedDate()));
            txtNgaySuaDonHang.setText(dateFormat.format(order.getUpdatedDate()));

            if (order.getEmployee() != null) {
                txtMaNhanVien.setText(order.getEmployee().getId().toString());
                txtTenNhanVien.setText(order.getEmployee().getName());
            }

            if (order.getDiscount() != null) {
                txtMaKhuyenMai.setText(order.getDiscount().getCode());
                txtPhanTramKhuyenMai.setText(order.getDiscount().getPercentageDiscount() + "%");
            }

            txtTongTien.setText(df.format(order.getTotalPrice()));
        } catch (NullPointerException e) {
        }
    }

    public boolean patchOrderStatus(int i, EmployeeResponseModel e) throws IOException, JSONException {
        OrderStatus orderStatus = null;
        if (i == 0) orderStatus = OrderStatus.SHIPPING;
        if (i == 1) orderStatus = OrderStatus.REJECTED;
        if (i == 2) orderStatus = OrderStatus.SHIP_SUCCESS;
        if (i == 3) orderStatus = OrderStatus.SHIP_FAIL;

        UpdateOrderStatusAndEmployee updateOrderStatusAndEmployee = new UpdateOrderStatusAndEmployee();
        updateOrderStatusAndEmployee.setEmployee(e);
        updateOrderStatusAndEmployee.setOrderStatus(orderStatus);

        return OrderService.patchOrderStatus(txtMaDonHang.getText(), updateOrderStatusAndEmployee);
    }

    public boolean patchOrderStatusAndEmployee(EmployeeResponseModel e) throws IOException, JSONException {
        String orderId = txtMaDonHang.getText();
        UpdateOrderStatusAndEmployee updateOrderStatusAndEmployee = new UpdateOrderStatusAndEmployee();

        if (cmbTrangThai.getSelectedIndex() == 0)
            updateOrderStatusAndEmployee.setOrderStatus(OrderStatus.PAYMENT_PENDING);
        else if (cmbTrangThai.getSelectedIndex() == 1)
            updateOrderStatusAndEmployee.setOrderStatus(OrderStatus.PAYMENT_DONE);
        else if (cmbTrangThai.getSelectedIndex() == 2)
            updateOrderStatusAndEmployee.setOrderStatus(OrderStatus.SHIPPING);
        else if (cmbTrangThai.getSelectedIndex() == 3)
            updateOrderStatusAndEmployee.setOrderStatus(OrderStatus.SHIP_SUCCESS);
        else if (cmbTrangThai.getSelectedIndex() == 4)
            updateOrderStatusAndEmployee.setOrderStatus(OrderStatus.SHIP_FAIL);
        else if (cmbTrangThai.getSelectedIndex() == 5)
            updateOrderStatusAndEmployee.setOrderStatus(OrderStatus.REJECTED);

        updateOrderStatusAndEmployee.setEmployee(e);

        return OrderService.patchOrderStatusAndEmployee(orderId, updateOrderStatusAndEmployee);
    }

    public void openPaymentDetailOnStripe() {
        OrderService.openPaymentDetailOnStripe(txtMaThanhToan.getText());
    }
}

package com.gearvmdesktop.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.formdev.flatlaf.FlatLightLaf;
import com.gearvmdesktop.service.OrderService;
import com.gearvmstore.GearVM.model.OrderStatus;
import com.gearvmstore.GearVM.model.response.GetOrderListResponse;
import com.gearvmstore.GearVM.model.response.GetOrderResponse;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.RectangleReadOnly;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class TestPrint extends JFrame implements ActionListener, MouseListener {
    private static final String tableName = "orders/";
    private static JTable tableDonHang;
    private static DefaultTableModel modelDonHang;
    private JButton btnTim, btnReset;
    private JComboBox<String> cmbChon;
    private static JTextField txtTim;
    private JLabel lblHiddenId;
    private JLabel lblHiddenName;

    public static void main(String[] args) throws IOException, DocumentException, PrinterException {
        TestPrint testPrint = new TestPrint();
        JFrame frame = new JFrame("My Window");
        JPanel donHang = testPrint.createPanelDonHang();
        frame.setContentPane(donHang);
        frame.pack();
        frame.setVisible(true);

        // create a PDF document with A4 page size
        Document document = new Document(new RectangleReadOnly(PageSize.A4.rotate()));
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("myWindow.pdf"));
        document.open();
        PdfContentByte cb = writer.getDirectContent();

        // get the size of the UI component and create a rectangle for the PDF document
        Dimension size = donHang.getSize();
        RectangleReadOnly rect = new RectangleReadOnly(0, 0, size.width, size.height);

        // create a Printable object that will print the UI component to the PDF document
        Printable printable = new Printable() {
            public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
                if (page > 0) {
                    return NO_SUCH_PAGE;
                }

                // calculate the scaling factor for the content
                double scaleX = pf.getImageableWidth() / rect.getWidth();
                double scaleY = pf.getImageableHeight() / rect.getHeight();
                double scale = Math.min(scaleX, scaleY);

                // translate to the origin of the UI component and scale the content
                g.translate((int) pf.getImageableX(), (int) pf.getImageableY());
                donHang.print(g);

                return PAGE_EXISTS;
            }
        };

        // print the UI component to the PDF document using the Printable object
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(printable);
        job.print();

        // add content to the PDF document and close it
        document.add(new RectangleReadOnly(rect));
        document.close();
    }

    public JPanel createPanelDonHang() throws IOException {
        FlatLightLaf.setup();
        setTitle("FrmBanHang");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel p = new JPanel();


        Box b1 = Box.createVerticalBox();
        Box b2 = Box.createVerticalBox();
        Box b3 = Box.createHorizontalBox();
        Box btim = Box.createHorizontalBox();
        Box bNut = Box.createHorizontalBox();


        String[] colHeader = {"Mã Đơn Hàng", "Tên Khách Hàng", "SĐT Khách Hàng", "Trạng Thái", "Ngày Lập Đơn Hàng", "Thành Tiền"};
        modelDonHang = new DefaultTableModel(colHeader, 0);
        tableDonHang = new JTable(modelDonHang) {
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
        };
        tableDonHang.setGridColor(getBackground());
        tableDonHang.setRowHeight(tableDonHang.getRowHeight() + 20);
        tableDonHang.setSelectionBackground(new Color(255, 255, 128));
        tableDonHang.setSelectionForeground(Color.BLACK);
        JTableHeader tableHeader = tableDonHang.getTableHeader();
        tableHeader.setBackground(new Color(0, 148, 224));
        tableHeader.setFont(new Font("Tahoma", Font.BOLD, 12));
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setPreferredSize(new Dimension(WIDTH, 30));
        JScrollPane tblscroll = new JScrollPane(tableDonHang);
        tableDonHang.setPreferredScrollableViewportSize(new Dimension(1200, 600));
        b1.add(b2);
        b1.add(tblscroll);
        tblscroll.setBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "DANH SÁCH ĐƠN HÀNG: "));

        tableDonHang.getColumnModel().getSelectionModel()
                .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tableDonHang.setDefaultEditor(Object.class, null);

        String[] tim = {"Mã Đơn Hàng", "Tên Khách Hàng", "SĐT Khách Hàng", "Trạng Thái", "Ngày Lập Đơn Hàng", "Thành Tiền"};
        cmbChon = new JComboBox<String>(tim);
        btim.add(cmbChon);
        btim.add(Box.createHorizontalStrut(10));
        txtTim = new JTextField();
        cmbChon.setSize(200, txtTim.getPreferredSize().height);
        btim.add(txtTim);
        btim.add(Box.createHorizontalStrut(10));

        ClassLoader classLoader = getClass().getClassLoader();

        URL iconTim = classLoader.getResource("assets/timkiem.png");
        URL iconLoad = classLoader.getResource("assets/lammoi.png");


        btnTim = new JButton("TÌM KIẾM", new ImageIcon(iconTim));
        btnTim.setBackground(new Color(0, 148, 224));
        btnTim.setForeground(Color.WHITE);

        btnReset = new JButton(new ImageIcon(iconLoad));
        btnReset.setBackground(new Color(0, 148, 224));
        btnReset.setForeground(Color.WHITE);

        btnTim.setFocusPainted(false);
        btim.add(btnTim);
        btim.add(Box.createHorizontalStrut(300));

//        bNut.add(btnGiao);
//        bNut.add(btnHuy);
        bNut.add(btnReset);

        JPanel p1 = new JPanel();
        JPanel pNorth = new JPanel();

        add(p);

        p.add(pNorth, BorderLayout.NORTH);
        pNorth.add(b3);
        b3.add(btim);
        b3.add(bNut);

        p.add(p1, BorderLayout.CENTER);
        p1.add(b1);

        cmbChon.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnTim.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnReset.setFont(new Font("Tahoma", Font.BOLD, 12));

        tableDonHang.addMouseListener(this);
        btnTim.addActionListener(this);
        btnReset.addActionListener(this);

        readDatabaseToTable();
        return p;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnReset)) {
            try {
                readDatabaseToTable();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (o.equals(btnTim)) {
            try {
                readDatabaseToTableByFilter();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static void readDatabaseToTable() throws IOException {
        emptyTable();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        BufferedReader rd = OrderService.getAllOnlineOrdersAndPaidDirectOrders();
        List<GetOrderListResponse> getOrderListResponse = Arrays.asList(mapper.readValue(rd, GetOrderListResponse[].class));

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("k:mm dd-MM-yyyy");
        DecimalFormat df = new DecimalFormat("#,##0");


        for (GetOrderListResponse o : getOrderListResponse) {
            String orderStatusString = "";
            if (o.getOrderStatus() == OrderStatus.PAYMENT_PENDING) orderStatusString = "Đang chờ thanh toán";
            else if (o.getOrderStatus() == OrderStatus.PAYMENT_DONE) orderStatusString = "Đang chờ xác nhận";
            else if (o.getOrderStatus() == OrderStatus.SHIPPING) orderStatusString = "Đang giao hàng";
            else if (o.getOrderStatus() == OrderStatus.SHIP_SUCCESS) orderStatusString = "Giao hàng thành công";
            else if (o.getOrderStatus() == OrderStatus.SHIP_FAIL) orderStatusString = "Giao hàng thất bại";
            else if (o.getOrderStatus() == OrderStatus.REJECTED) orderStatusString = "Đơn hàng bị từ chối";

            modelDonHang.addRow(new Object[]{
                    o.getId(), o.getCustomer().getName(), o.getCustomer().getPhoneNumber(),
                    orderStatusString, dateFormat.format(o.getCreatedDate()), df.format(o.getTotalPrice())
            });
        }
    }

    public static void readDatabaseToTableByFilter() throws IOException {
        emptyTable();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        BufferedReader rd = OrderService.getAllByFilterRequest("orders/", txtTim.getText());
        List<GetOrderListResponse> getOrderListResponse = Arrays.asList(mapper.readValue(rd, GetOrderListResponse[].class));

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("k:mm dd-MM-yyyy");
        DecimalFormat df = new DecimalFormat("#,##0");


        for (GetOrderListResponse o : getOrderListResponse) {
            String orderStatusString = "";
            if (o.getOrderStatus() == OrderStatus.PAYMENT_PENDING) orderStatusString = "Đang chờ thanh toán";
            else if (o.getOrderStatus() == OrderStatus.PAYMENT_DONE) orderStatusString = "Đang chờ xác nhận";
            else if (o.getOrderStatus() == OrderStatus.SHIPPING) orderStatusString = "Đang giao hàng";
            else if (o.getOrderStatus() == OrderStatus.SHIP_SUCCESS) orderStatusString = "Giao hàng thành công";
            else if (o.getOrderStatus() == OrderStatus.SHIP_FAIL) orderStatusString = "Giao hàng thất bại";
            else if (o.getOrderStatus() == OrderStatus.REJECTED) orderStatusString = "Đơn hàng bị từ chối";

            modelDonHang.addRow(new Object[]{
                    o.getId(), o.getCustomer().getName(), o.getCustomer().getPhoneNumber(),
                    orderStatusString, dateFormat.format(o.getCreatedDate()), df.format(o.getTotalPrice())
            });
        }
    }

    public static void emptyTable() {
        DefaultTableModel dm = (DefaultTableModel) tableDonHang.getModel();
        dm.setRowCount(0);
    }

    public GetOrderResponse getRequest(String id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        BufferedReader rd = OrderService.getRequest(tableName, id);
        return mapper.readValue(rd, GetOrderResponse.class);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tableDonHang.getSelectedRow();
        if (e.getClickCount() == 2 && tableDonHang.getSelectedRow() != -1) {
            try {
                GetOrderResponse getOrderResponse = getRequest(tableDonHang.getValueAt(row, 0).toString().trim());
                new FrmChiTietDonHang(getOrderResponse);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

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
}

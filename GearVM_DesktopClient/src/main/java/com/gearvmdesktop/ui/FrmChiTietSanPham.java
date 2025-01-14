package com.gearvmdesktop.ui;

import com.formdev.flatlaf.FlatLightLaf;
import com.gearvmdesktop.service.ProductService;
import com.gearvmstore.GearVM.model.Product;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class FrmChiTietSanPham extends JFrame implements ActionListener {
    private JButton browseButton;
    private JButton saveButton;
    private JButton cancelButton;
    private JLabel imageLbl;
    private JTextArea idTxt;
    private JTextArea nameTxt;
    private JTextArea descriptionTxt;
    private JScrollPane scrollPane;
    private File selectedFile = null;
    private String productId = null;

    public FrmChiTietSanPham(Product product) throws IOException {
//        prepareGUI();
        super("Chi tiết sản phẩm");
        productId = product.getId().toString();
        GUI.disableWarning();
        FlatLightLaf.setup();
        setSize(1000, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        ClassLoader classLoader = getClass().getClassLoader();

        URL iconChonAnh = classLoader.getResource("assets/chonanh.png");

        browseButton = new JButton("Chọn ảnh",new ImageIcon(iconChonAnh));
        browseButton .setBackground(new Color(0, 148, 224));
        browseButton .setForeground(Color.WHITE);
        browseButton .setFocusPainted(false);
        browseButton.setBounds(200, 480, 150, 40);

        URL iconLuu = classLoader.getResource("assets/luu.png");

        saveButton = new JButton("Lưu",new ImageIcon(iconLuu));
        saveButton .setBackground(new Color(0, 148, 224));
        saveButton .setForeground(Color.WHITE);
        saveButton .setFocusPainted(false);
        saveButton.setBounds(400, 480, 100, 40);

        URL iconHuy = classLoader.getResource("assets/huy.png");

        cancelButton = new JButton("Hủy",new ImageIcon(iconHuy));
        cancelButton .setBackground(new Color(0, 148, 224));
        cancelButton .setForeground(Color.WHITE);
        cancelButton .setFocusPainted(false);
        cancelButton.setBounds(520, 480, 100, 40);
        imageLbl = new JLabel();
        imageLbl.setBounds(10, 10, 600, 450);
        Border imageBorder = BorderFactory.createTitledBorder("Hình ảnh sản phẩm: ");


        idTxt = new JTextArea("Mã sản phẩm: " + product.getId());
        idTxt.setBounds(650, 10, 200, 50);
        idTxt.setOpaque(false);
        idTxt.setBackground(Color.WHITE);
        idTxt.setEditable(false);
        nameTxt = new JTextArea("Tên sản phẩm: " + product.getName());
        nameTxt.setBounds(650, 30, 300, 50);
        nameTxt.setLineWrap(true);
        nameTxt.setOpaque(false);
        nameTxt.setBackground(Color.WHITE);
        nameTxt.setEditable(false);

        descriptionTxt = new JTextArea(product.getDescription());
        descriptionTxt.setLineWrap(true);
        scrollPane = new JScrollPane(descriptionTxt);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(650, 100, 300, 360);
        Border descriptionBorder = BorderFactory.createTitledBorder("Mô tả sản phẩm: ");
        scrollPane.setBorder(descriptionBorder);

        add(browseButton);
        add(saveButton);
        add(cancelButton);
        add(imageLbl);
        add(idTxt);
        add(nameTxt);
        add(scrollPane);

        if (!product.getImageUri().equalsIgnoreCase("empty-product.jpg"))
            imageLbl.setIcon(getImage(product.getImageUri()));
        browseButton.addActionListener(this);
        saveButton.addActionListener(this);
        cancelButton.addActionListener(this);
        setVisible(true);
    }

    private ImageIcon getImage(String imageUri) throws IOException {
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
        Region region = Region.AP_SOUTHEAST_1;
        S3Client s3 = S3Client.builder()
                .region(region)
                .credentialsProvider(credentialsProvider)
                .build();

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket("gearvm")
                .key(imageUri)
                .build();

        ResponseInputStream<GetObjectResponse> input = s3.getObject(getObjectRequest);

        BufferedImage br = ImageIO.read(input);
        ImageIcon imageIcon = new ImageIcon(br);
        Image tempImg = imageIcon.getImage();
        Image newImg = tempImg.getScaledInstance(imageLbl.getWidth(), imageLbl.getHeight(), Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }

    public ImageIcon ResizeImage(String ImagePath) {
        ImageIcon imageIcon = new ImageIcon(ImagePath);
        Image tempImg = imageIcon.getImage();
//        Image newImg = tempImg.getScaledInstance(imageLbl.getWidth(), imageLbl.getHeight(), Image.SCALE_SMOOTH);
//       Image newImg = tempImg.getScaledInstance(800, 600, Image.SCALE_DEFAULT);

        return new ImageIcon(tempImg);

    }

    private void putObjectS3() throws IOException {
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
        Region region = Region.AP_SOUTHEAST_1;
        S3Client s3 = S3Client.builder()
                .region(region)
                .credentialsProvider(credentialsProvider)
                .build();
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + ".jpg";
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket("gearvm")
                .key(fileName)
                .build();
        s3.putObject(objectRequest, RequestBody.fromFile(selectedFile));
        ProductService.patchImageUriRequest(fileName, productId);
    }

    private boolean patchDescriptionRequest() throws IOException {
        String description = descriptionTxt.getText();
        return ProductService.patchDescriptionRequest(description, productId);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(browseButton)) {
            JFileChooser file = new JFileChooser();
            file.setCurrentDirectory(new File(System.getProperty("user.dir")));
            //filter the files
            FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
            file.addChoosableFileFilter(filter);
            int result = file.showSaveDialog(null);
            //if the user click on save in Jfilechooser
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = file.getSelectedFile();
                String path = selectedFile.getAbsolutePath();
                imageLbl.setIcon(ResizeImage(path));
            }
            //if the user click on cancel in Jfilechooser
            else if (result == JFileChooser.CANCEL_OPTION) {
                JOptionPane.showMessageDialog(this, "Không có ảnh được chọn!", "Thất bại",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        if (o.equals(saveButton)) {
            try {
                if (patchDescriptionRequest()) {
                    JOptionPane.showMessageDialog(this, "Sửa mô tả sản phẩm thành công!", "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                    if (selectedFile != null)
                        putObjectS3();
                } else {
                    JOptionPane.showMessageDialog(this, "Sửa mô tả sản phẩm thất bại!", "Thất bại",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
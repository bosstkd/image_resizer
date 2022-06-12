import com.lowagie.text.DocumentException;
import resizer.ImageResizer;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class ImgRez extends JFrame {

    private final JPanel contentPane;
    private final JTextField tf_x;
    private final JTextField tf_y;

    JComboBox cb_format = new JComboBox();

    //*****************************************

    public final static String IMAGE_TYPE_JPEG = "jpeg";

    public final static String IMAGE_TYPE_GIF = "gif";

    public final static String IMAGE_TYPE_PNG = "png";

    //*****************************************

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {

                Properties props = new Properties();

                props.put("logoString", "DZ 0670 29 85 33");

                try {
                    ImgRez frame = new ImgRez();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public ImgRez() {
        setResizable(false);
        setTitle("Amine Image Resizer ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 404, 147);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        tf_x = new JTextField();
        tf_x.addKeyListener(new KeyAdapter() {

            public void keyReleased(KeyEvent arg0) {
                try {
                    String dtj = "";
                    try {
                        int i = Integer.parseInt(tf_x.getText().toString());
                    } catch (Exception exp) {

                        dtj = tf_x.getText();
                        tf_x.setText(dtj.substring(0, tf_x.getText().toString().length() - 1));
                    }
                } catch (Exception e) {

                }
            }
        });
        tf_x.setFont(new Font("MV Boli", Font.PLAIN, 14));
        tf_x.setBounds(54, 34, 70, 32);
        contentPane.add(tf_x);
        tf_x.setColumns(10);

        tf_y = new JTextField();
        tf_y.addKeyListener(new KeyAdapter() {

            public void keyReleased(KeyEvent arg0) {
                try {
                    String dtj = "";
                    try {
                        int i = Integer.parseInt(tf_y.getText().toString());
                    } catch (Exception exp) {

                        dtj = tf_y.getText();
                        tf_y.setText(dtj.substring(0, tf_y.getText().toString().length() - 1));
                    }
                } catch (Exception e) {

                }
            }
        });
        tf_y.setFont(new Font("MV Boli", Font.PLAIN, 14));
        tf_y.setBounds(150, 34, 70, 32);
        contentPane.add(tf_y);
        tf_y.setColumns(10);

        JButton btnNewButton = new JButton("Redimensionner");

        btnNewButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {

                if (tf_x.getText().equals("") || tf_y.getText().equals("")) {
                    tf_x.setText("0");
                    tf_y.setText("0");
                }
                try {
                    if (cb_format.getSelectedIndex() == 0) {
                        logo(new Dimension(Integer.parseInt(tf_x.getText()), Integer.parseInt(tf_y.getText())),
                                "D:/SIGLE.png", IMAGE_TYPE_JPEG, ".jpg");
                    } else {
                        logo(new Dimension(Integer.parseInt(tf_x.getText()), Integer.parseInt(tf_y.getText())),
                                "D:/SIGLE.png", IMAGE_TYPE_PNG, ".png");
                    }
                } catch (DocumentException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btnNewButton.setFont(new Font("MV Boli", Font.PLAIN, 14));
        btnNewButton.setBounds(230, 77, 148, 25);
        contentPane.add(btnNewButton);

        JLabel lblX = new JLabel("X");
        lblX.setBounds(134, 43, 19, 14);
        contentPane.add(lblX);

        JLabel lblEntrLaTaille = new JLabel("Entr\u00E9 la Taille en pixel ainsi que le format de sorti");
        lblEntrLaTaille.setFont(new Font("MV Boli", Font.PLAIN, 11));
        lblEntrLaTaille.setBounds(10, 11, 315, 14);
        contentPane.add(lblEntrLaTaille);

        cb_format.setModel(new DefaultComboBoxModel(new String[] { ".jpg", ".png" }));
        cb_format.setFont(new Font("MV Boli", Font.PLAIN, 12));
        cb_format.setBounds(312, 34, 66, 32);
        contentPane.add(cb_format);

        JLabel lblTaille = new JLabel("Taille");
        lblTaille.setFont(new Font("MV Boli", Font.PLAIN, 12));
        lblTaille.setBounds(10, 34, 46, 32);
        contentPane.add(lblTaille);

        JLabel lblFormat = new JLabel("Format");
        lblFormat.setFont(new Font("MV Boli", Font.PLAIN, 12));
        lblFormat.setBounds(256, 34, 46, 32);
        contentPane.add(lblFormat);

        JLabel lblContacteAekhotmailfr = new JLabel("Contacte : a-ek@hotmail.fr");
        lblContacteAekhotmailfr.setBounds(10, 83, 160, 14);
        contentPane.add(lblContacteAekhotmailfr);
    }


    public static void logo(Dimension dms, String pictureName, String compressionType, String format)
            throws DocumentException, IOException {

        String ce_trouve = "";

        final JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images or PDF", "jpg", "png", "pdf");

        chooser.setMultiSelectionEnabled(true);

        chooser.setFileFilter(filter);

        int returnVal = chooser.showOpenDialog(chooser);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File[] fs = chooser.getSelectedFiles();

            ImageResizer imageResizer = new ImageResizer();
            String convertedList = imageResizer.convertAndGetFilesList(fs, dms, pictureName, compressionType, format);
            JOptionPane.showMessageDialog(null, "Redimentionnement Effectuer avec succ�es Emplacement :" + ce_trouve,
                    "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class ImageViewerGUI extends JFrame implements ActionListener {
    JButton selectFileButton;
    JButton showImageButton;
    JButton resizeButton;
    JButton grayscaleButton;
    JButton brightnessButton;
    JButton closeButton;
    JButton showResizeButton;
    JButton showBrightnessButton;
    JButton backButton;
    JTextField widthTextField;
    JTextField heightTextField;
    JTextField brightnessTextField;
    String filePath = "C:\\Users\\lenovo\\Desktop\\images";
    File file;
    JFileChooser fileChooser = new JFileChooser(filePath);
    int h = 900;
    int w = 1200;
    float brightenFactor = 1;

    ImageViewerGUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Image Viewer");
        this.setSize(700, 300);
        this.setVisible(true);
        this.setResizable(true);

        mainPanel();
    }

    public void mainPanel() {
        // Create main panel for adding to Frame
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 0, 0, 0));
        // Create Grid panel for adding buttons to it, then add it all to main panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3, 2));
        //Button Defining
        selectFileButton = new JButton("Choose Image");
        showImageButton = new JButton("Show Image");
        resizeButton = new JButton("Resize");
        grayscaleButton = new JButton("Gray Scale");
        brightnessButton = new JButton("Brightness");
        closeButton = new JButton("Exit");
        //Actionlistener adding
        selectFileButton.addActionListener(this);
        showImageButton.addActionListener(this);
        brightnessButton.addActionListener(this);
        grayscaleButton.addActionListener(this);
        resizeButton.addActionListener(this);
        closeButton.addActionListener(this);
        //Title Label
        JLabel title = new JLabel("Image Viewer");
        title.setHorizontalAlignment(JLabel.CENTER);
        // Adding all buttons to Grid panel
        buttonsPanel.add(this.selectFileButton);
        buttonsPanel.add(this.showImageButton);
        buttonsPanel.add(this.brightnessButton);
        buttonsPanel.add(this.grayscaleButton);
        buttonsPanel.add(this.resizeButton);
        buttonsPanel.add(this.closeButton);

        // add Grid panel that contains 6 buttons to main panel
        mainPanel.add(title);
        mainPanel.add(buttonsPanel);
        // add main panel to our frame
        this.add(mainPanel);
    }

    public void resizePanel() {
        JPanel resizePanel = new JPanel();
        resizePanel.setLayout(new GridLayout(3, 1));
        //Create TextField
        widthTextField = new JTextField();
        heightTextField = new JTextField();
        //Divide panel into 3 parts
        JPanel firstPart = new JPanel(new GridLayout(1, 1));
        JPanel secondPart = new JPanel(new GridLayout(2, 3));
        JPanel thirdPart = new JPanel(new GridLayout(3, 6));
        //Create first part
        firstPart.add(new JLabel("Resize Section", JLabel.CENTER));
        //create two section for second part
        //create first section
        secondPart.add(new JLabel("Width:", JLabel.CENTER));

        widthTextField = new JTextField();
        secondPart.add(widthTextField);
        secondPart.add(new JLabel(""));
        //create second section
        secondPart.add(new JLabel("Height:", JLabel.CENTER));

        heightTextField = new JTextField();
        secondPart.add(heightTextField);
        secondPart.add(new JLabel(""));
        //Define button
        showResizeButton = new JButton("Show Result");
        backButton = new JButton("Back");
        //Assign ActionListener
        backButton.addActionListener(this);
        showResizeButton.addActionListener(this);
        // create third part UI

        for (int i = 0; i < 7; i++) thirdPart.add(new JLabel(""));

        thirdPart.add(backButton);
        thirdPart.add(new JLabel(""));
        thirdPart.add(new JLabel(""));
        thirdPart.add(showResizeButton);

        for (int i = 0; i < 7; i++) thirdPart.add(new JLabel(""));
        //Add parts
        resizePanel.add(firstPart);
        resizePanel.add(secondPart);
        resizePanel.add(thirdPart);
        //Add
        this.add(resizePanel);
    }

    public void brightnessPanel() {
        JPanel brightnessPanel = new JPanel();
        brightnessPanel.setLayout(new GridLayout(2, 2, this.getWidth() / 6, this.getHeight() / 6));
        // Label
        brightnessPanel.add(new JLabel("Enter f\n(must be entered between 0 and 1)", JLabel.CENTER));
        //Add input field
        brightnessTextField = new JTextField();
        brightnessPanel.add(brightnessTextField);
        //Add back Button
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        brightnessPanel.add(backButton);
        //Add result Button
        showBrightnessButton = new JButton("Result");
        showBrightnessButton.addActionListener(this);
        brightnessPanel.add(showBrightnessButton);

        this.add(brightnessPanel);
    }

    public void chooseFileImage() {
        fileChooser.setAcceptAllFileFilterUsed(false);

        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            try {
                ImageIO.read(file);
            } catch (IOException e) {
                throw new RuntimeException("File not found or something wrong!");
            }
        }
    }

    public void showOriginalImage() throws Exception {
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        // Get image from file
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
        } catch (IOException e) {
            throw new IOException("  ===>  File does not exist or something else is happening!");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("  ===>  You should choose a file first!");
        }
        tempPanel.add(new JLabel(new ImageIcon(bufferedImage.getScaledInstance(bufferedImage.getWidth(), bufferedImage.getHeight(), Image.SCALE_DEFAULT))));

        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }

    public void grayScaleImage() throws Exception {
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        //Make it gray
        ImageFilter grayFilter = new GrayFilter(true, 0);
        //get image from file
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
        } catch (IOException e) {
            throw new IOException("  ===>  File does not exist or something else is happening!");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("  ===>  You should choose a file first!");
        }
        //Produce grayscale
        ImageProducer producer = new FilteredImageSource(bufferedImage.getSource(), grayFilter);
        Image image = Toolkit.getDefaultToolkit().createImage(producer);
        //Add to panel
        tempPanel.add(new JLabel(new ImageIcon(image)));

        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }

    public void showResizeImage(int w, int h) throws Exception {
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        //get image from file
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
        } catch (IOException e) {
            throw new IOException("  ===>  File does not exist or something else is happening!");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("  ===>  You should choose a file first!");
        }
        tempPanel.add(new JLabel(new ImageIcon(bufferedImage.getScaledInstance(w, h, Image.SCALE_DEFAULT))));
        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }

    public void showBrightnessImage(float f) throws Exception {
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();

        RescaleOp op = new RescaleOp(f, 0, null);
        //get image from file
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
            bufferedImage = op.filter(bufferedImage, bufferedImage);
        } catch (IOException e) {
            throw new IOException("  ===>  File does not exist or something else is happening!");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("  ===>  You should choose a file first!");
        }
        //Add
        tempPanel.add(new JLabel(new ImageIcon(bufferedImage)));
        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == resizeButton) {
            this.getContentPane().removeAll();
            this.resizePanel();
            this.revalidate();
            this.repaint();
        } else if (e.getSource() == showImageButton) {
            try {
                showOriginalImage();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == grayscaleButton) {
            try {
                grayScaleImage();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == showResizeButton) {
            try {
                w = Integer.parseInt(widthTextField.getText());
                if (!(0 < w && w < 1920))
                    throw new IllegalArgumentException("Enter desired width greater than 0 and lower than 1920!");
                h = Integer.parseInt(heightTextField.getText());
                if (!(0 < h && h < 1080))
                    throw new IllegalArgumentException("Enter desired height greater than 0 and lower than 1080!");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            try {
                showResizeImage(w, h);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == brightnessButton) {
            this.getContentPane().removeAll();
            this.brightnessPanel();
            this.revalidate();
            this.repaint();
        } else if (e.getSource() == showBrightnessButton) {
            try {
                brightenFactor = Float.parseFloat(brightnessTextField.getText());
                if (brightenFactor < 0 || brightenFactor > 1) throw new IllegalArgumentException();
            } catch (Exception ex) {
                throw new RuntimeException("  ===>  Enter a number between 0.0 and 1.0!");
            }
            try {
                showBrightnessImage(brightenFactor);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == selectFileButton) {
            chooseFileImage();
        } else if (e.getSource() == closeButton) {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        } else if (e.getSource() == backButton) {
            this.getContentPane().removeAll();
            this.mainPanel();
            this.revalidate();
            this.repaint();
        }
    }
}
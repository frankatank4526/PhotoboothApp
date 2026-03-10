package photobooth.view;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class PhotoboothGUIView extends JFrame implements PhotoboothView {

  private final JLabel statusLabel;
  private final InfoPanel infoPanel;
  private final ControlPanel controlPanel;
  private final MainPanel mainPanel;
  private PhotoboothFeatures features;
  private boolean cameraOn = false;
  /**
   * Constructor for PhotoboothGUIView.
   */
  public PhotoboothGUIView() {
    super("Photobooth");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    statusLabel = new JLabel();
    statusLabel.setHorizontalAlignment(JLabel.CENTER);

    add(statusLabel, BorderLayout.NORTH);

    infoPanel = new InfoPanel();
    controlPanel = new ControlPanel();
    mainPanel = new MainPanel();

    infoPanel.updateTitleText("Photobooth :)");
    add(infoPanel, BorderLayout.NORTH);
    add(mainPanel, BorderLayout.CENTER);
    JButton startButton = new JButton("Start/Stop");
    startButton.addActionListener(e -> {
      cameraOn = !cameraOn;
      if (cameraOn) {
        features.startCameraClicked();
      }
      else {
        features.stopCamera();
      }


    } );


    add(startButton, BorderLayout.SOUTH);


    pack();
    refresh();


  }

  @Override
  public void displayMessage(String message) {
    // Make the dialogue prettier later
    JOptionPane.showMessageDialog(this, message);

  }
  @Override
  public void addFeatures(PhotoboothFeatures features) {
    this.features = features;
  }

  @Override
  public void refresh() {
    infoPanel.repaint();

    mainPanel.repaint();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void updateDisplayFrame(BufferedImage image) {
    mainPanel.updateImage(image);
  }
}

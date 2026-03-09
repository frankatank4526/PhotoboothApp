package photobooth.view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PhotoboothGUIView extends JFrame implements PhotoboothView {

  private final JLabel statusLabel;
  private final InfoPanel infoPanel;
  private final ControlPanel controlPanel;
  private final MainPanel mainPanel;
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

    infoPanel.updateTitleText("YUMMEY");
    add(infoPanel, BorderLayout.NORTH);


    pack();
    refresh();


  }

  @Override
  public void addFeatures(PhotoboothFeatures features) {

  }

  @Override
  public void refresh() {
    infoPanel.repaint();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }
}

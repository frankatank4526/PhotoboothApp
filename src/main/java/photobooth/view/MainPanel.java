package photobooth.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * JPanel that displays live video feed, saved pictures, etc. Intended to be the
 * "main display" in the center of the screen.
 */
public class MainPanel extends JPanel {

  private BufferedImage image;


  @Override
  public Dimension getPreferredSize() {
    return new Dimension(800, 600);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (image != null) {
      // https://stackoverflow.com/questions/23457754/how-to-flip-bufferedimage-in-java
      g.drawImage(image, 0, 0, this.getWidth(),  this.getHeight(),
          this.image.getWidth(), 0, 0, this.image.getHeight(), null);
    }
  }

  public void updateImage(BufferedImage image) {
    this.image = image;
    repaint();
  }
}

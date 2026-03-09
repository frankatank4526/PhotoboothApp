package photobooth.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * JPanel that displays basic information to users. Intended to be placed at top of Frame.
 * Personal reference for layout types: https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html
 * For this panel: https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html
 */
public class InfoPanel extends JPanel {
  JLabel title;
  String titleText = "";

  public InfoPanel() {
    title = new JLabel();
    title.setHorizontalAlignment(JLabel.LEFT);
    setSize(1200, 300);
    add(title);

  }

  @Override
  public Dimension getPreferredSize(){
    return new Dimension(1200, 300);
  }
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    title.setBackground(Color.GRAY);
    title.setForeground(Color.BLACK);
    title.setText(titleText);
    /*
    Later, use https://docs.oracle.com/javase/8/docs/api/java/awt/Font.html#createFont-int-java.io.InputStream-
    as reference to create new font that isn't boring.
     */
    title.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
  }

  protected void updateTitleText(String titleText) {
    this.titleText = titleText;
  }
}

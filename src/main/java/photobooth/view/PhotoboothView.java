package photobooth.view;

import java.awt.image.BufferedImage;

/**
 * Interface for graphical Photobooth View. Implementations should use JSwing to implement a
 * GUI that allows users to take photos and print them.
 * TODO: extend this interface. Make a version in which users can modify (draw on) photos.
 */
public interface PhotoboothView {

  /**
   * Displays a message to the user.
   * @param message the message to be shown
   */
  void displayMessage(String message);
  /**
   * Register callbacks for user actions.
   */
  void addFeatures(PhotoboothFeatures features);

  /**
   * Redraw the view after a model or selection change.
   */
  void refresh();

  /**
   * Make the window visible.
   */
  void makeVisible();

  /**
   * Updates the live feed frames.
   * @param image image to be displayed
   */
  void updateDisplayFrame(BufferedImage image);

}

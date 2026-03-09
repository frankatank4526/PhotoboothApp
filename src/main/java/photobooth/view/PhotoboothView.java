package photobooth.view;

/**
 * Interface for graphical Photobooth View. Implementations should use JSwing to implement a
 * GUI that allows users to take photos and print them.
 * TODO: extend this interface. Make a version in which users can modify (draw on) photos.
 */
public interface PhotoboothView {

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


}

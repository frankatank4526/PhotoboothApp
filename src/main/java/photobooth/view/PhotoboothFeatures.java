package photobooth.view;

/**
 * Callbacks that the GUI view invokes when the user interacts with it.
 **/
public interface PhotoboothFeatures {

  /**
   * Called when user "takes photo" via the GUI. Executes necessary
   * functionality for photo to be taken and saved.
   */
  void photoCaptureClicked();

  /**
   * Called when user "prints photo" via the GUI. Executes necessary
   * functionality for photo to be printed.
   */
  void photoPrintClicked();

  /**
   * Called when user "deletes the photo just taken" via the GUI. Executes necessary
   * functionality for photo to be deleted.
   */
  void recentPhotoDeleteClicked();

  /**
   * Called when user "deletes all photos" via the GUi. Executes necessary
   * functionality for deleting all photos.
   */
  void allPhotoDeleteClicked();





}

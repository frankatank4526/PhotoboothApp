package photobooth.model;

/**
 * Listener for model status events. Controllers subscribe to know when
 * photos are taken, printed, and deleted.
 */
public interface PhotoboothListener {

  /**
   * Called when photo is captured.
   */
  void photoCaptured();

  /**
   * Called when photo is printed.
   */
  void photoPrinted();

  /**
   * Called when all photos have been deleted.
   */
  void allPhotosDeleted();

  /**
   * Called when most recent photo has been deleted.
   */
  void recentPhotoDeleted();
}

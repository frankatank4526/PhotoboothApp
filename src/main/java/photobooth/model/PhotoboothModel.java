package photobooth.model;

import java.io.IOException;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.opencv.opencv_core.IplImage;

/**
 * This interface represents the model of this Photobooth app. It deals with functionality
 * relating to capturing photos, storing photos, and printing photos.
 */
public interface PhotoboothModel {

  /**
   * Starts camera.
   *
   * @throws IllegalStateException if camera cannot be accessed
   */
  void startCamera() throws FrameGrabber.Exception;

  /**
   * Stops camera.
   *
   * @throws IllegalStateException if camera cannot be accessed
   */
  void stopCamera() throws FrameGrabber.Exception;

  /**
   * Gets the next frame to display on video feed.
   *
   * @return the next frame as an IplImage
   * @throws FrameGrabber.Exception if the next frame cannot be grabbed
   */
  IplImage getFrame() throws FrameGrabber.Exception;

  /**
   * Snaps a photo.
   *
   * @param filename name to save photo as
   * @throws IllegalStateException if components (i.e. external camera) are not set up properly
   */
  void takePhoto(String filename);

  /**
   * Prints a photo. Takes in an idx, which indicates which photo to print.
   *
   * @param idx the index describing which photo to print (0-indexed)
   * @throws IllegalArgumentException if index is negative or out of bounds
   */
  void printPhoto(int idx);

  /**
   * Loads the photo that is in "temporary storage."
   *
   * @param filename the name of the file (photo) to be loaded
   * @throws IllegalStateException if there is no such photo available
   */
  IplImage loadPhoto(String filename);

  /**
   * Deletes the most recently taken photo. Does nothing if there is no photo stored
   */
  void deletePhoto() throws IOException;

  /**
   * Deletes all photos stored.
   */
  void deleteAllPhotos() throws IOException;


}

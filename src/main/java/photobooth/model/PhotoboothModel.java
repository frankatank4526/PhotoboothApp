package photobooth.model;

/**
 * This interface represents the model of this Photobooth app. It deals with functionality
 * relating to capturing photos, storing photos, and printing photos.
 */
public interface PhotoboothModel {


  /**
   * Snaps a photo.
   *
   * @throws IllegalStateException if components (i.e. external camera) are not set up properly
   */
  void takePhoto();

  /**
   * Prints a photo. Takes in an idx, which indicates which photo to print.
   *
   * @param idx the index describing which photo to print (0-indexed)
   * @throws IllegalArgumentException if index is negative or out of bounds
   */
  void printPhoto(int idx);

  /**
   * Saves the photo that is in "temporary storage."
   *
   * @throws IllegalStateException if there is no such photo available
   */
  void savePhoto();

  /**
   * Deletes the photo in temporary storage. Does nothing if there is no photo stored
   */
  void deletePhoto();

  /**
   * Deletes a photo at a given index.
   *
   * @param idx the index of the photo (0-indexed)
   */
  void deletePhoto(int idx);


}

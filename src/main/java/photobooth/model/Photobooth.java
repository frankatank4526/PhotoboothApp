package photobooth.model;

import java.io.IOException;
import java.nio.file.Paths;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.IplImage;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * First implementation of the PhotoboothModel interface.
 * References: <a href="https://www.baeldung.com/java-capture-image-from-webcam">...</a>
 *              <a href="https://stackoverflow.com/questions/276292/capturing-image-from-webcam-in-java">...</a>
 *              <a href="https://stackoverflow.com/questions/25641324/webcam-stream-in-opencv-using-java">...</a>
 */
public class Photobooth implements PhotoboothModel {
  private final FrameGrabber grabber;
  private boolean cameraOn = false;
  private int fileCount = 0;

  /**
   * Constructor for a Photobooth.
   *
   * @param port 0 for default built-in webcam, 1 for second accessible camera (e.g. usb camera),
   *             2 for third, etc.
   */
  public Photobooth(int port, int frameWidth, int frameHeight) {
    grabber = new OpenCVFrameGrabber(port);
    grabber.setImageWidth(frameWidth);
    grabber.setImageHeight(frameHeight);

  }
  private IplImage convertFrame(Frame frame) {
      OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
      return converter.convert(frame);
    }

  @Override
  public void startCamera() {
    try {
      grabber.start();
      cameraOn = true;
    } catch (FrameGrabber.Exception error) {
      throw new IllegalStateException("Camera failed to start");
    }
  }

  @Override
  public void stopCamera() {
    try {
      grabber.stop();
      cameraOn = false;
      grabber.release();
    } catch (FrameGrabber.Exception error) {
      throw new IllegalStateException("Camera failed to stop");
    }
  }

  @Override
  public IplImage getFrame() {
    try {
      return convertFrame(grabber.grab());
    }
    catch (FrameGrabber.Exception error) {
      throw new IllegalStateException("Camera failed to grab");
    }

  }

  @Override
  public void takePhoto(String filename) {
    if (!cameraOn) {
      throw new IllegalStateException("Cannot take photo; camera is off");
    }
      IplImage img = getFrame();
      fileCount++;
      opencv_imgcodecs.cvSaveImage("photo_" + fileCount + ".jpg", img);



  }

  @Override
  public void printPhoto(int idx) { // Later, need to add whether it's printing
                                    // original image or modified image.

  }

  @Override
  public IplImage loadPhoto(String filename) {
    return opencv_imgcodecs.cvLoadImage(filename);
  }

  @Override
  public void deletePhoto() throws IOException {
    if (fileCount > 0) {
      Path filePath = Paths.get("photo_" + fileCount + ".jpg");
      if (Files.deleteIfExists(filePath)) {
        fileCount--;
        // When implementing pub-sub pattern, notify subscribers here.
      }
      else {
        // Notify failure here.
      }
    }

  }

  @Override
  public void deleteAllPhotos() throws IOException {
    int count = fileCount;
    if (fileCount > 0) {
      for (int i = 0; i < count; i++) {
        deletePhoto();
      }

    }
  }
}

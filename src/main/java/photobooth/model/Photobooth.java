package photobooth.model;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.IplImage;

/**
 * Implementation of the PhotoboothModel interface.
 * Used <a href="https://www.baeldung.com/java-capture-image-from-webcam">...</a> as reference.
 */
public class Photobooth implements PhotoboothModel {
  private final FrameGrabber grabber;
  private boolean cameraOn = false;

  /**
   * Constructor for a Photobooth.
   *
   * @param port 0 for default built-in webcam, 1 for second accessible camera (e.g. usb camera),
   *             2 for third, etc.
   */
  public Photobooth(int port) {
    grabber = new OpenCVFrameGrabber(port);

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
    } catch (FrameGrabber.Exception error) {
      throw new IllegalStateException("Camera failed to stop");
    }
  }

  @Override
  public void takePhoto() {
    if (!cameraOn) {
      throw new IllegalStateException("Cannot take photo; camera is off");
    }
    Frame frame = grabber.grab();
    OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
    IplImage img = converter.convert(frame);
    opencv_imgcodecs.cvSaveImage("selfie.jpg", img);
  }

  @Override
  public void printPhoto(int idx) {

  }

  @Override
  public void savePhoto() {

  }

  @Override
  public void deletePhoto() {

  }

  @Override
  public void deletePhoto(int idx) {

  }
}

package photobooth.controller;

import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.SwingWorker;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.javacv.Java2DFrameConverter;
import photobooth.model.PhotoboothListener;
import photobooth.model.PhotoboothModel;
import photobooth.view.PhotoboothFeatures;
import photobooth.view.PhotoboothView;

/**
 * Controller for the Photobooth app. Mediates communication between model and view.
 */
public class PhotoboothController implements PhotoboothListener, PhotoboothFeatures {
  private SwingWorker<Void, BufferedImage> liveFeedWorker;
  private boolean liveFeedOn = false;
  private PhotoboothModel model;
  private PhotoboothView view;

  public PhotoboothController(PhotoboothModel model, PhotoboothView view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("model and view cannot be null");
    }
    this.model = model;
    this.view = view;
    model.addListener(this);
    view.addFeatures(this);


  }

  @Override
  public void photoCaptured() {
    stopCamera();
    // do more stuff... display image, prompt stuff, etc.
  }

  @Override
  public void photoPrinted() {

  }

  @Override
  public void allPhotosDeleted() {

  }

  @Override
  public void recentPhotoDeleted() {

  }

  @Override
  public void startCameraClicked() {
    /*
    Starting the camera after stopping it once seems to take some time.
    For production, consider keeping the video "recording" as opposed to stopping the
    grabber every time.
     */
    try {
      model.startCamera();
      liveFeedOn = true;
      liveFeedWorker = new SwingWorker<Void, BufferedImage>() {
        @Override
        protected Void doInBackground() throws Exception {
          while (liveFeedOn) {
            try {
              Frame frame = model.getFrame();

              if (frame != null) {
                // https://stackoverflow.com/questions/31873704/javacv-how-to-convert-iplimage-tobufferedimage
                Java2DFrameConverter paintConverter = new Java2DFrameConverter();
                BufferedImage bufferedImage = paintConverter.getBufferedImage(frame, 1);
                view.updateDisplayFrame(bufferedImage);
                view.refresh();

              }
            } catch (IllegalStateException e) {
              // skip the frame
              System.err.println("skipped frame");
              break;
            }

          }
          return null;
        }
        @Override
        protected void done() {
          // TODO: send image to view to update camera frame when not showing live feed.
          System.out.println("swingworker finished");
        }
      };
      liveFeedWorker.execute();

    } catch (Exception e) {
      view.displayMessage("Error starting camera.");
    }
  }

  @Override
  public void stopCamera() {
    try {

      liveFeedOn = false;
      liveFeedWorker.cancel(true);

      model.stopCamera();
    } catch (Exception e) {
      view.displayMessage("Error stopping camera");
    }
  }

  @Override
  public void photoCaptureClicked() {
    model.takePhoto("testing");
  }

  @Override
  public void photoPrintClicked() {
    model.printPhoto(1);

  }

  @Override
  public void recentPhotoDeleteClicked() {

  }

  @Override
  public void allPhotoDeleteClicked() {

  }
}

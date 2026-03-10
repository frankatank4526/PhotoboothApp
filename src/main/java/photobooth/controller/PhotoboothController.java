package photobooth.controller;

import java.awt.image.BufferedImage;
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
  private SwingWorker<Void, Mat> liveFeedWorker;
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

    liveFeedWorker = new SwingWorker<Void, Mat>() {
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
          }  catch(IllegalStateException e){
            // skip the frame
          }

        }
        return null;
      }
    };
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
      liveFeedWorker.execute();

    } catch (Exception e) {
      view.displayMessage("Error starting camera.");
    }
  }

  @Override
  public void stopCamera() {
    try {
      model.stopCamera();
      liveFeedWorker.cancel(true);
      liveFeedOn = false;
    } catch (Exception e) {
      view.displayMessage("Error stopping camera");
    }
  }

  @Override
  public void photoCaptureClicked() {

  }

  @Override
  public void photoPrintClicked() {

  }

  @Override
  public void recentPhotoDeleteClicked() {

  }

  @Override
  public void allPhotoDeleteClicked() {

  }
}

package photobooth.model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.nio.file.Paths;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.IplImage;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * First implementation of the PhotoboothModel interface.
 * References: <a href="https://www.baeldung.com/java-capture-image-from-webcam">...</a>
 * <a href="https://stackoverflow.com/questions/276292/capturing-image-from-webcam-in-java">...</a>
 * <a href="https://stackoverflow.com/questions/25641324/webcam-stream-in-opencv-using-java">...</a>
 * <p>
 * Setting a Printer
 * <a href="https://stackoverflow.com/questions/11787662/in-java-how-do-i-change-or-set-a-default-printer"> </a>
 *
 * Printing Using PrintJob
 * <a href="https://stackoverflow.com/questions/10479621/how-to-print-image-in-java#:~:text=Sorted%20by:,4%2C9693%2028%2037"></a>
 * <a href="https://stackoverflow.com/questions/5338423/print-a-image-with-actual-size-in-java"></a>
 *
 * Converting IplImage to Image:
 * <a href="https://stackoverflow.com/questions/31873704/javacv-how-to-convert-iplimage-tobufferedimage"></a>
 *
 * Fixing Maven Dependency Issues (for personal reference):
 * <a href="https://stackoverflow.com/questions/15046764/intellij-idea-not-recognizing-classes-specified-in-maven-dependencies"></a>
 *
 * OpenCV Docs:
 * <a href="https://bytedeco.org/javacv/apidocs/org/bytedeco/javacv/OpenCVFrameGrabber.html#grab--"></a>
 * <a href="https://docs.opencv.org/3.4/javadoc/org/opencv/videoio/VideoCapture.html#grab()"></a>
 *
 *
 */
/*
TODO: Basic Printing implementation, Setup JSwing
 */
public class Photobooth implements PhotoboothModel {
  private final FrameGrabber grabber;
  private boolean cameraOn = false;
  private int fileCount = 0;
  private PrinterJob printerJob;
  private PhotoboothListener photoboothListener;

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
    printerJob = PrinterJob.getPrinterJob(); // Sets PrintService to default printer
  }

  private IplImage convertFrame(Frame frame) {
    OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
    return converter.convert(frame);
  }

  @Override
  public void addListener(PhotoboothListener listener) {
    photoboothListener = listener;
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
      //grabber.release(); move for when closing app at end
    } catch (FrameGrabber.Exception error) {
      throw new IllegalStateException("Camera failed to stop");
    }
  }

  @Override
  public Frame getFrame() {
    try {
      return grabber.grab();
    } catch (FrameGrabber.Exception error) {
      throw new IllegalStateException("Camera failed to grab");
    }
  }
  @Override
  public IplImage grabAndConvert() {
    try {
      return convertFrame(grabber.grab());
    } catch (FrameGrabber.Exception error) {
      throw new IllegalStateException("Camera failed to grab");
    }

  }

  @Override
  public void takePhoto(String filename) {
    if (!cameraOn) {
      throw new IllegalStateException("Cannot take photo; camera is off");
    }
    IplImage img = grabAndConvert();
    fileCount++;
    opencv_imgcodecs.cvSaveImage("photo_" + fileCount + ".jpg", img);


  }

  @Override
  public void setupPrinter(String printerName) {
    // Source - https://stackoverflow.com/a/27854339
    // Posted by Pankaj Bansal
    // Retrieved 2026-02-19, License - CC BY-SA 3.0

    PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
    System.out.println("Number of printers configured: " + printServices.length);
    for (PrintService printer : printServices) {
      System.out.println("Printer: " + printer.getName());
      if (printer.getName().equals(printerName)) {
        try {
          printerJob.setPrintService(printer);
        } catch (PrinterException ex) {
          throw new IllegalArgumentException("Printer " + printerName + " doesn't exist");
        }
      }
    }

  }

  @Override
  public void printPhoto(int idx) {
    // Later, need to add whether it's printing original image or modified image.
    if (fileCount < 0 || idx >= fileCount) {
      throw new IllegalArgumentException("Invalid index: " + idx);
    }
    // Source - see references section, under "Converting IplImage to Image"
    IplImage iplImage = loadPhoto("photo_" + idx + ".jpg");
    OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
    Java2DFrameConverter paintConverter = new Java2DFrameConverter();
    Frame frame = grabberConverter.convert(iplImage);
    Image imageToPrint = paintConverter.getBufferedImage(frame,1);

    // Source - see references section, under "Printing using PrintJob"
    printerJob.setPrintable(new Printable() {
      public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
          throws PrinterException {
        if (pageIndex != 0) {
          return NO_SUCH_PAGE;
        }
        graphics.drawImage(imageToPrint, 0, 0, imageToPrint.getWidth(null) * 9,
            imageToPrint.getHeight(null) * 9, null);
        return PAGE_EXISTS;
      }
    });
    if (printerJob.printDialog()) {
      try {
        printerJob.print();
      } catch (PrinterException prt) {
        prt.printStackTrace();
        System.err.println("Printer failed to print");
      }
    }


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
      } else {
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

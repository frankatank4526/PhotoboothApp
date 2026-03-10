package photobooth;

import photobooth.controller.PhotoboothController;
import photobooth.model.Photobooth;
import photobooth.model.PhotoboothModel;
import photobooth.view.PhotoboothGUIView;

/**
 * Main class for the Photobooth app. Used to run the code.
 */
public class PhotoboothApp {


public static void main(String[] args) {

  PhotoboothGUIView view = new PhotoboothGUIView();
  PhotoboothModel model = new Photobooth(0, 800, 600);
  PhotoboothController controller = new PhotoboothController(model, view);

  view.setVisible(true);

  view.refresh();

}

}

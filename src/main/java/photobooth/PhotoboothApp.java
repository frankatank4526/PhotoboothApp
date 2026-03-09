package photobooth;

import photobooth.view.PhotoboothGUIView;

/**
 * Main class for the Photobooth app. Used to run the code.
 */
public class PhotoboothApp {


public static void main(String[] args) {

  PhotoboothGUIView view = new PhotoboothGUIView();
  view.setVisible(true);

  view.refresh();

}

}

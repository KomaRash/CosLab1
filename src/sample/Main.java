package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.opencv.core.Core;

public class Main {

    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        ImageLoader imageLoader = new ImageLoader();
  // imageLoader.Load("aa", 1472);

    //    imageLoader.Load("aa", 1467);
     imageLoader.Load("aa", 1460);
    //   imageLoader.Load("aa", 1470);


    }
}

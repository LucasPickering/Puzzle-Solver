package puzzlesolver.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("main_menu.fxml"));
    primaryStage.setTitle("Puzzle-O-Matic!");
    primaryStage.setResizable(false);
    primaryStage.setScene(new Scene(root, root.prefWidth(400), root.prefHeight(300)));
    primaryStage.show();
  }


  public static void main(String[] args) {
    launch(args);
  }
}

package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class CaseApplication extends Application{

	@Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("CaseList.fxml"));

            primaryStage.setTitle("Case Planner");
            primaryStage.setScene(new Scene(root));
            primaryStage.getIcons().add(new Image("/view/case.png"));
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}

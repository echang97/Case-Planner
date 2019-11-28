package view;

import controller.DatabaseConnection;
import controller.DatabaseController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class CaseApplication extends Application{

    private static TrayIcon trayIcon;
    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

	@Override
    public void start(Stage primaryStage) throws AWTException, IOException {
	    if (SystemTray.isSupported()){
            Platform.setImplicitExit(false);
            makeTrayItems();
        }

        try {
            Parent root = FXMLLoader.load(getClass().getResource("CaseList.fxml"));
            this.stage = primaryStage;
            stage.setTitle("Case Planner");
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/view/case.png"));
            showStage();

            Timer t = new Timer();
            // Timer set for 1 hour (3600000)
            // For testing use 30 seconds (30000)
            t.scheduleAtFixedRate(new NotificationTask(),1,30000);


        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void showStage(){
        stage.show();
    }

    private void makeTrayItems() throws AWTException, IOException {
	    SystemTray tray = SystemTray.getSystemTray();
        java.awt.Image img = ImageIO.read(getClass().getResource("case.png"));
        trayIcon = new TrayIcon(img, "Case Planner");
        trayIcon.setImageAutoSize(true);
	    PopupMenu popup = new PopupMenu();

	    MenuItem openItem = new MenuItem("Open");
        MenuItem exitItem = new MenuItem("Exit");
//        MenuItem notificationItem = new MenuItem("Test Notification");
//        MenuItem dumpAllItem = new MenuItem("Dump all notifications");
        openItem.addActionListener(e -> Platform.runLater(this::showStage));
        exitItem.addActionListener(e -> System.exit(1));
//        notificationItem.addActionListener
//                (e -> trayIcon.displayMessage("Case Planner","Hello! :)", TrayIcon.MessageType.INFO));
//        dumpAllItem.addActionListener
//                (e -> dumpAll());
        popup.add(openItem);
//        popup.add(notificationItem);
//        popup.add(dumpAllItem);
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);
        tray.add(trayIcon);
    }

    private void dumpAll(){
        try{
            DatabaseConnection database = new DatabaseConnection();
            DatabaseController.checkNotificationsInDB(database);
        }catch (SQLException s){
            s.printStackTrace();
        }
    }

    public static TrayIcon getTrayIcon() {
        return trayIcon;
    }

    private class NotificationTask extends TimerTask{
        @Override
        public void run() {
            try{
                DatabaseConnection database = new DatabaseConnection();
                DatabaseController.checkNotificationsInDB(database);
            }catch (SQLException s){
                s.printStackTrace();
            }
        }
    }
}

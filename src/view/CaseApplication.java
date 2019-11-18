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

import java.awt.*;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class CaseApplication extends Application{

    private static TrayIcon trayIcon;
    private Stage stage;

	@Override
    public void start(Stage primaryStage) throws AWTException{
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
            //3600000
            t.scheduleAtFixedRate(new NotificationTask(),0,30000);


        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void showStage(){
        stage.show();
    }

    private void makeTrayItems() throws AWTException{
	    SystemTray tray = SystemTray.getSystemTray();
        java.awt.Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");
        trayIcon = new TrayIcon(icon);
	    PopupMenu popup = new PopupMenu();

	    MenuItem openItem = new MenuItem("Open");
        MenuItem exitItem = new MenuItem("Exit");
        MenuItem notificationItem = new MenuItem("Test Notification");
        openItem.addActionListener(e -> Platform.runLater(this::showStage));
        exitItem.addActionListener(e -> System.exit(1));
        notificationItem.addActionListener
                (e -> trayIcon.displayMessage("Case Planner","Hello! :)", TrayIcon.MessageType.INFO));
        popup.add(openItem);
        popup.add(notificationItem);
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);
        tray.add(trayIcon);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static TrayIcon getTrayIcon() {
        return trayIcon;
    }

    private class NotificationTask extends TimerTask{
        @Override
        public void run() {
            try {
                DatabaseConnection database = new DatabaseConnection();
                DatabaseController.checkNotificationsInDB(database);
            }catch (SQLException s){
                System.out.println("Something went wrong: " + s);
            }
        }
    }
}

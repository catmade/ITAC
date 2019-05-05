package menu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import menu.ui.Menu;

import java.io.IOException;

/**
 * @author Seven
 * @description 启动主程序
 * @date 2019-05-05
 */
public class Launch extends Application {
    public static void main(String[] args) throws IOException {
        launch(null);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Menu.class.getResource("menu.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}

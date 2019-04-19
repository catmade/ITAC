package coding;

import coding.fano.Fano;
import coding.ui.Home;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launch extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Home.class.getResource("home.fxml"));

        stage.setTitle("三种编码");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        //launch(args);
        new Fano(new int[]{2500, 2500, 1250, 1250, 625, 625, 625, 625}, 1000);
    }
}

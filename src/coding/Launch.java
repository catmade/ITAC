package coding;

import coding.fano.Fano;
import coding.shannon.Shannon;
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
        launch(args);
//        int[] ints = {2500, 250 ty0, 1250, 1250, 625, 625, 625, 625};
//        new Fano(ints, 1000);
//        String  s = "";
        //double a = 0.3;
        //System.out.println(a);
        //0.25 0.25 0.2 0.15 0.10 0.05

        //Shannon测试
        //double dous[]={0.25,0.25,0.20,0.15,0.10,0.05};
        //new Shannon(dous);
    }
}

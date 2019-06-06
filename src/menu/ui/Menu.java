package menu.ui;

import capacity.ui.SecondWork;
import coding.ui.FirstWork;
import correct.ui.ThirdWork;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Seven
 * @description 主菜单的控制类
 * @date 2019-05-05
 */
public class Menu extends Application {

    /**
     * 编码
     */
    private Stage codingStage = new Stage();

    /**
     * 计算信道容量
     */
    private Stage channelStage = new Stage();

    /**
     * 纠错编码
     */
    private Stage correctStage = new Stage();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCoding;

    @FXML
    private Button btnCalcChannel;

    @FXML
    private Button btnCorrect;

    @FXML
    private AnchorPane apRoot;

    @FXML
    void initialize() {
        assert btnCoding != null : "fx:id=\"btnCoding\" was not injected: check your FXML file 'menu.fxml'.";
        assert btnCalcChannel != null : "fx:id=\"btnCalcChannel\" was not injected: check your FXML file 'menu.fxml'.";
        assert btnCorrect != null : "fx:id=\"btnCorrect\" was not injected: check your FXML file 'menu.fxml'.";
        assert apRoot != null : "fx:id=\"apRoot\" was not injected: check your FXML file 'menu.fxml'.";

        btnCoding.setOnMouseClicked(event -> {
            if (!codingStage.isShowing()) {
                try {
                    codingStage.setScene(new Scene(FXMLLoader.load(
                            FirstWork.class.getResource("first.fxml"))));
                    codingStage.setTitle("第一次作业 - 香农、赫夫曼、费诺编码");

                    codingStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnCalcChannel.setOnMouseClicked(event -> {
            if (!channelStage.isShowing()) {
                try {
                    channelStage.setScene(new Scene(FXMLLoader.load(
                            SecondWork.class.getResource("second.fxml"))));
                    channelStage.setTitle("第二次作业 - 计算信道容量");
                    channelStage.setResizable(false);
                    channelStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnCorrect.setOnMouseClicked(event -> {
            if (!correctStage.isShowing()) {
                try {
                    correctStage.setScene(new Scene(FXMLLoader.load(
                            ThirdWork.class.getResource("third.fxml"))));
                    correctStage.setTitle("第三次作业 - 纠错编码");
                    correctStage.setResizable(false);
                    correctStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Menu.class.getResource("menu.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("《信息论与编码》程序设计");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        launch(null);
    }
}

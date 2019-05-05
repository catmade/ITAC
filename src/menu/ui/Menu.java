package menu.ui;

import coding.ui.Home;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
public class Menu {

    /**
     * 编码
     */
    private Stage codingStage = new Stage();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCoding;

    @FXML
    private AnchorPane apRoot;

    @FXML
    void initialize() {
        assert btnCoding != null : "fx:id=\"btnCoding\" was not injected: check your FXML file 'menu.fxml'.";
        assert apRoot != null : "fx:id=\"apRoot\" was not injected: check your FXML file 'menu.fxml'.";

        btnCoding.setOnMouseClicked(event -> {
            if(!codingStage.isShowing()){
                try {
                    codingStage.setScene(new Scene(FXMLLoader.load(Home.class.getResource("home.fxml"))));
                    codingStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

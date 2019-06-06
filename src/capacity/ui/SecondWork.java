package capacity.ui;

import capacity.Channel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SecondWork {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea taInput;

    @FXML
    private Button btnAction;

    @FXML
    private ListView<Channel.Result> lvResult;

    @FXML
    void initialize() {
        assert taInput != null : "fx:id=\"taInput\" was not injected: check your FXML file 'second.fxml'.";
        assert btnAction != null : "fx:id=\"btnAction\" was not injected: check your FXML file 'second.fxml'.";
        assert lvResult != null : "fx:id=\"lvResult\" was not injected: check your FXML file 'second.fxml'.";
        setListener();
    }

    /**
     * 设置监听
     */
    private void setListener() {
        btnAction.setOnMouseClicked(event -> {
            lvResult.getItems().clear();
            String text = taInput.getText();
            int row = 1, colunm;
            for (Character c : text.toCharArray()) {
                if (c == '\n') {
                    row++;
                }
            }

            String[] splits = text.split("[\\s+]+");
            colunm = splits.length / row;

            double[] doubles = new double[splits.length];
            for (int i = 0; i < splits.length; i++) {
                try {
                    doubles[i] = Double.parseDouble(splits[i]);
                } catch (NumberFormatException e) {
                    return;
                }
            }

            Channel channel = new Channel(row, colunm, doubles);
            List<Channel.Result> results = channel.getResults();
            lvResult.getItems().addAll(results);
        });
    }

}

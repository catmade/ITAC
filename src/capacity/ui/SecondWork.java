package capacity.ui;

import capacity.Channel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Label kind1;

    @FXML
    private Label kind2;

    @FXML
    private Label c1;

    @FXML
    private Label c2;

    @FXML
    private Label time1;

    @FXML
    private Label time2;

    @FXML
    void initialize() {
        assert kind1 != null : "fx:id=\"kind1\" was not injected: check your FXML file 'second.fxml'.";
        assert kind2 != null : "fx:id=\"kind2\" was not injected: check your FXML file 'second.fxml'.";
        assert c1 != null : "fx:id=\"c1\" was not injected: check your FXML file 'second.fxml'.";
        assert c2 != null : "fx:id=\"c2\" was not injected: check your FXML file 'second.fxml'.";
        assert time1 != null : "fx:id=\"time1\" was not injected: check your FXML file 'second.fxml'.";
        assert time2 != null : "fx:id=\"time2\" was not injected: check your FXML file 'second.fxml'.";

        assert taInput != null : "fx:id=\"taInput\" was not injected: check your FXML file 'second.fxml'.";
        assert btnAction != null : "fx:id=\"btnAction\" was not injected: check your FXML file 'second.fxml'.";
        setListener();
    }

    /**
     * 设置监听
     */
    private void setListener() {
        btnAction.setOnMouseClicked(event -> {
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
            List<String[]> results = channel.resultsToString();
            if (results == null){
                kind1.setText("无法计算");
                return;
            }
            if (results.size() == 1) {
                kind1.setText(results.get(0)[0]);
                c1.setText(results.get(0)[1]);
                time1.setText(results.get(0)[2]);
            }
            if (results.size() == 2) {
                kind1.setText(results.get(0)[0]);
                c1.setText(results.get(0)[1]);
                time1.setText(results.get(0)[2]);

                kind2.setText(results.get(1)[0]);
                c2.setText(results.get(1)[1]);
                time2.setText(results.get(1)[2]);
            }
        });
    }

}

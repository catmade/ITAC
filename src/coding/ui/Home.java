package coding.ui;

import coding.Node;
import coding.fano.Fano;
import coding.shannon.Shannon;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

public class Home {

    /**
     * 费诺编码
     */
    private Fano fano;

    /**
     * 香农编码
     */
     private Shannon shannon;

    private double[] doubles;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnBegin;

    @FXML
    private Label warning;

    @FXML
    private TextArea editorInput;

    @FXML
    private TableView<?> tabFano;

    @FXML
    void initialize() {
        assert btnBegin != null : "fx:id=\"btnBegin\" was not injected: check your FXML file 'home.fxml'.";
        assert editorInput != null : "fx:id=\"editorInput\" was not injected: check your FXML file 'home.fxml'.";
        assert tabFano != null : "fx:id=\"tabFano\" was not injected: check your FXML file 'home.fxml'.";
        assert warning != null : "fx:id=\"warning\" was not injected: check your FXML file 'home.fxml'.";

        btnBegin.setOnMouseClicked(event -> {
            String s = editorInput.getText();
            //检查是否全为数字
            boolean allNumber = true;
            char[] cs = s.toCharArray();
            for (int i = 0; i < s.length(); i++){
                if(cs[i] == '\t' || cs[i] == '\n'|| cs[i] == ' ' || (cs[i] >= '0' && cs[i] <= '9')){
                    allNumber = true;
                }else {
                    allNumber = false;
                }
            }
            if(!allNumber){
                warning.setText("请不要输入数字以外的其他符号");
            } else {
                //“\\s+”正则表达式，可以匹配所有空格、\n和\t;
                String[] stringNums = s.split("\\s+");
                try {
                    stringToDouble(stringNums);
                    warning.setVisible(false);
                    fano = new Fano(doubles);
                    fillTable();
                } catch (NumberFormatException e) {
                    warning.setText("请检查数字的格式是否输入正确");
                } catch (Exception e) {
                    warning.setText("概率之和不等于1，请核对数据");
                }
            }
        });

    }

    private void fillTable() {
        Node[] nodes = fano.getNodes();
        for (Node node : nodes) {
            System.out.println(node.codes);
        }
    }


    private void stringToDouble (String[ ] stringNums) throws NumberFormatException,Exception{
        double count = 0;
        doubles = new double[stringNums.length];
        for (int i = 0; i < doubles.length; i++) {
            doubles[i] = Double.parseDouble(stringNums[i]);
            count += doubles[i];
        }
        if(count != 1){
            throw new Exception("概率之和不为等于1");
        }
    }
}


package coding.ui;

import coding.Node;
import coding.fano.Fano;
import coding.shannon.Shannon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Home {

    /**
     * 费诺编码
     */
    private Fano fano;

    /**
     * 费诺编码填充数据
     */
    private ObservableList<Node> fanoData;

    /**
     * 香农编码
     */
    private Shannon shannon;

    /**
     * 表格的填充数据
     */
    private ObservableList<Node> tabData;

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
    private TableView<Node> tabFano;

    @FXML
    private TableColumn<Node, String> colFanoSymbol;

    @FXML
    private TableColumn<Node, Double> colFanoP;

    @FXML
    private TableColumn<Node, String> colFanoCodes;

    @FXML
    private TableColumn<Node, Integer> colFanoLength;

    @FXML
    void initialize() {
        assert btnBegin != null : "fx:id=\"btnBegin\" was not injected: check your FXML file 'home.fxml'.";
        assert editorInput != null : "fx:id=\"editorInput\" was not injected: check your FXML file 'home.fxml'.";
        assert warning != null : "fx:id=\"warning\" was not injected: check your FXML file 'home.fxml'.";

        assert tabFano != null : "fx:id=\"tabFano\" was not injected: check your FXML file 'home.fxml'.";
        assert colFanoSymbol != null : "fx:id=\"colFanoSymbol\" was not injected: check your FXML file 'home.fxml'.";
        assert colFanoP != null : "fx:id=\"colFanoP\" was not injected: check your FXML file 'home.fxml'.";
        assert colFanoCodes != null : "fx:id=\"colFanoCodes\" was not injected: check your FXML file 'home.fxml'.";
        assert colFanoLength != null : "fx:id=\"colFanoLength\" was not injected: check your FXML file 'home.fxml'.";



        btnBegin.setOnMouseClicked(this::beginEncode);
    }

    private void stringToDouble(String[] stringNums) throws NumberFormatException, Exception {
        double count = 0;
        doubles = new double[stringNums.length];
        for (int i = 0; i < doubles.length; i++) {
            doubles[i] = Double.parseDouble(stringNums[i]);
            count += doubles[i];
        }
        if (count != 1) {
            throw new Exception("概率之和不为等于1");
        }
    }

    private void beginEncode(MouseEvent event) {
        String s = editorInput.getText();
        //检查是否全为数字
        boolean allNumber = true;
        char[] cs = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            if (cs[i] == '\t' || cs[i] == '\n' || cs[i] == ' ' || (cs[i] >= '0' && cs[i] <= '9')) {
                allNumber = true;
            } else {
                allNumber = false;
            }
        }
        if (!allNumber) {
            warning.setText("请不要输入数字以外的其他符号");
        } else {
            //“\\s+”正则表达式，可以匹配所有空格、\n和\t;
            String[] stringNums = s.split("\\s+");
            try {
                stringToDouble(stringNums);
                warning.setVisible(false);
                readyToFill();
                fillFanoTable();
                fillShannonTable();
            } catch (NumberFormatException e) {
                warning.setText("请检查数字的格式是否输入正确");
            } catch (Exception e) {
                warning.setText("概率之和不等于1，请核对数据");
            }
        }
    }

    /**
     * 填充数据前的准备，需要先初始化对象
     */
    private void readyToFill() {
        fano = new Fano(doubles);
        shannon = new Shannon(doubles);
        for (int i = 0; i < doubles.length; i++) {
            fano.setNodesSymbol(i, "a" + (i + 1));
            shannon.setNodesSymbol(i, "a" + (i + 1));
        }
        fanoData = FXCollections.observableArrayList(
                new ArrayList<Node>(Arrays.asList(fano.getNodes()))
        );
    }

    /**
     * 填充Shannon编码表格
     */
    private void fillShannonTable() {

    }

    /**
     * 填充Fano编码表格
     */
    private void fillFanoTable() {
        Node[] nodes = fano.getNodes();
        System.out.print("fano codes:");
        for (Node node : nodes) {
            System.out.print(node.codes);
        }
        colFanoSymbol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        colFanoP.setCellValueFactory(new PropertyValueFactory<>("p"));
        colFanoCodes.setCellValueFactory(new PropertyValueFactory<>("codes"));
        colFanoLength.setCellValueFactory(new PropertyValueFactory<>("ki"));

        tabFano.setItems(fanoData);
    }

}





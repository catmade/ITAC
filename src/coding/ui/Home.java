package coding.ui;

import coding.Node;
import coding.fano.Fano;
import coding.huffman.Huffman;
import coding.shannon.Shannon;
import coding.shannon.ShannonNode;
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

/**
 * @author Seven
 * @description 编码界面的控制类
 * @date 2019-05-05
 */
public class Home {

    /**
     * 赫夫曼编码
     */
    private Huffman huffman;

    /**
     * 赫夫曼编码填充数据
     */
    private ObservableList<Node> huffmanData;

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
     * 香农编码填充数据
     */
    private ObservableList<ShannonNode> shannonData;

    /**
     * 概率
     */
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
    private TableView<Node> tabHuffman;

    @FXML
    private TableColumn<Node, String> colHuffmanSymbol;

    @FXML
    private TableColumn<Node, Double> colHuffmanP;

    @FXML
    private TableColumn<Node, String> colHuffmanCodes;

    @FXML
    private TableColumn<Node, Integer> colHuffmanLength;

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
    private TableView<ShannonNode> tabShannon;

    @FXML
    private TableColumn<ShannonNode, String> colShannonSymbol;

    @FXML
    private TableColumn<ShannonNode, Double> colShannonP;

    @FXML
    private TableColumn<ShannonNode, Double> colShannonpa;

    @FXML
    private TableColumn<ShannonNode, Integer> colShannonLength;

    @FXML
    private TableColumn<ShannonNode, String> colShannonCodes;

    @FXML
    private Label lbShannonHx;

    @FXML
    private Label lbShannonK;

    @FXML
    private Label lbShannonP;

    @FXML
    private Label lbFanoHx;

    @FXML
    private Label lbFanoK;

    @FXML
    private Label lbFanoP;

    @FXML
    private Label lbHuffmanHx;

    @FXML
    private Label lbHuffmanK;

    @FXML
    private Label lbHuffmanP;

    @FXML
    void initialize() {
        assert btnBegin != null : "fx:id=\"btnBegin\" was not injected: check your FXML file 'home.fxml'.";
        assert editorInput != null : "fx:id=\"editorInput\" was not injected: check your FXML file 'home.fxml'.";
        assert warning != null : "fx:id=\"warning\" was not injected: check your FXML file 'home.fxml'.";

        assert tabHuffman != null : "fx:id=\"tabHuffman\" was not injected: check your FXML file 'home.fxml'.";
        assert colHuffmanSymbol != null : "fx:id=\"colHuffmanSymbol\" was not injected: check your FXML file 'home.fxml'.";
        assert colHuffmanP != null : "fx:id=\"colHuffmanP\" was not injected: check your FXML file 'home.fxml'.";
        assert colHuffmanCodes != null : "fx:id=\"colHuffmanCodes\" was not injected: check your FXML file 'home.fxml'.";
        assert colHuffmanLength != null : "fx:id=\"colHuffmanLength\" was not injected: check your FXML file 'home.fxml'.";

        assert tabFano != null : "fx:id=\"tabFano\" was not injected: check your FXML file 'home.fxml'.";
        assert colFanoSymbol != null : "fx:id=\"colFanoSymbol\" was not injected: check your FXML file 'home.fxml'.";
        assert colFanoP != null : "fx:id=\"colFanoP\" was not injected: check your FXML file 'home.fxml'.";
        assert colFanoCodes != null : "fx:id=\"colFanoCodes\" was not injected: check your FXML file 'home.fxml'.";
        assert colFanoLength != null : "fx:id=\"colFanoLength\" was not injected: check your FXML file 'home.fxml'.";

        assert tabShannon != null : "fx:id=\"tabShannon\" was not injected: check your FXML file 'home.fxml'.";
        assert colShannonSymbol != null : "fx:id=\"colShannonSymbol\" was not injected: check your FXML file 'home.fxml'.";
        assert colShannonP != null : "fx:id=\"colShannonP\" was not injected: check your FXML file 'home.fxml'.";
        assert colShannonpa != null : "fx:id=\"colShannonpa\" was not injected: check your FXML file 'home.fxml'.";
        assert colShannonLength != null : "fx:id=\"colShannonLength\" was not injected: check your FXML file 'home.fxml'.";
        assert colShannonCodes != null : "fx:id=\"colShannonCodes\" was not injected: check your FXML file 'home.fxml'.";

        assert lbShannonHx != null : "fx:id=\"lbShannonHx\" was not injected: check your FXML file 'home.fxml'.";
        assert lbShannonK != null : "fx:id=\"lbShannonK\" was not injected: check your FXML file 'home.fxml'.";
        assert lbShannonP != null : "fx:id=\"lbShannonP\" was not injected: check your FXML file 'home.fxml'.";
        assert lbFanoHx != null : "fx:id=\"lbFanoHx\" was not injected: check your FXML file 'home.fxml'.";
        assert lbFanoK != null : "fx:id=\"lbFanoK\" was not injected: check your FXML file 'home.fxml'.";
        assert lbFanoP != null : "fx:id=\"lbFanoP\" was not injected: check your FXML file 'home.fxml'.";
        assert lbHuffmanHx != null : "fx:id=\"lbHuffmanHx\" was not injected: check your FXML file 'home.fxml'.";
        assert lbHuffmanK != null : "fx:id=\"lbHuffmanK\" was not injected: check your FXML file 'home.fxml'.";
        assert lbHuffmanP != null : "fx:id=\"lbHuffmanP\" was not injected: check your FXML file 'home.fxml'.";

        btnBegin.setOnMouseClicked(this::beginEncode);
    }

    private void stringToDouble(String[] stringNums) {
        doubles = new double[stringNums.length];
        for (int i = 0; i < doubles.length; i++) {
            doubles[i] = Double.parseDouble(stringNums[i]);
            //规避浮点数陷阱
            doubles[i] = Math.round(doubles[i] * 1000000000) / 1000000000.0;
        }
    }

    private void beginEncode(MouseEvent event) {
        String s = editorInput.getText();

        if("".equals(s)){
            return;
        }

        //检查是否全为数字
        boolean allNumber = true;
        char[] cs = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            if (cs[i] == '\t' || cs[i] == '\n' || cs[i] == ' ' || cs[i] == '.' || (cs[i] >= '0' && cs[i] <= '9')) {
                allNumber = true;
            } else {
                allNumber = false;
                break;
            }
        }
        if (!allNumber) {
            warning.setText("请不要输入数字以外的其他符号");
        } else {
            //“\\s+”正则表达式，可以匹配所有空格、\n和\t;
            String[] stringNums = s.split("\\s+");
            stringToDouble(stringNums);
            warning.setVisible(false);

            Arrays.sort(doubles);
            double temp;
            for (int i = 0; i < doubles.length / 2; i++) {
                temp = doubles[i];
                doubles[i] = doubles[doubles.length - 1 - i];
                doubles[doubles.length - 1 - i] = temp;
            }
            readyToFill();
            setFanoTableCol();
            setShannonTableCol();
            setHuffmanTableCol();
        }
    }

    /**
     * 填充数据前的准备，需要先初始化对象
     */
    private void readyToFill() {
        fano = new Fano(doubles);
        shannon = new Shannon(doubles);
        huffman = new Huffman(doubles);
        for (int i = 0; i < doubles.length; i++) {
            fano.setNodesSymbol(i, "a" + (i + 1));
            shannon.setNodesSymbol(i, "a" + (i + 1));
            huffman.setNodesSymbol(i, "a" + (i + 1));
        }
        fanoData = FXCollections.observableArrayList(
                new ArrayList<>(Arrays.asList(fano.getNodes()))
        );
        shannonData = FXCollections.observableArrayList(
                new ArrayList<>(Arrays.asList(shannon.getNodes()))
        );
        huffmanData = FXCollections.observableArrayList(
                new ArrayList<>(Arrays.asList(huffman.getNodes()))
        );
    }

    /**
     * 设置Huffman编码表格
     */
    private void setHuffmanTableCol() {
        colHuffmanSymbol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        colHuffmanP.setCellValueFactory(new PropertyValueFactory<>("p"));
        colHuffmanCodes.setCellValueFactory(new PropertyValueFactory<>("codes"));
        colHuffmanLength.setCellValueFactory(new PropertyValueFactory<>("ki"));
        tabHuffman.setItems(huffmanData);

        lbHuffmanHx.setText("信源熵：" + huffman.getHx());
        lbHuffmanK.setText("平均码长：" + huffman.getK());
        lbHuffmanP.setText("编码效率：" + huffman.getP() * 100);
    }

    /**
     * 设置Shannon编码表格
     */
    private void setShannonTableCol() {
        colShannonSymbol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        colShannonP.setCellValueFactory(new PropertyValueFactory<>("p"));
        colShannonpa.setCellValueFactory(new PropertyValueFactory<>("aj"));
        colShannonLength.setCellValueFactory(new PropertyValueFactory<>("ki"));
        colShannonCodes.setCellValueFactory(new PropertyValueFactory<>("codes"));
        tabShannon.setItems(shannonData);

        lbShannonHx.setText("信源熵：" + shannon.getHx());
        lbShannonK.setText("平均码长：" + shannon.getK());
        lbShannonP.setText("编码效率：" + shannon.getP() * 100);
    }

    /**
     * 设置Fano编码表格
     */
    private void setFanoTableCol() {
        colFanoSymbol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        colFanoP.setCellValueFactory(new PropertyValueFactory<>("p"));
        colFanoCodes.setCellValueFactory(new PropertyValueFactory<>("codes"));
        colFanoLength.setCellValueFactory(new PropertyValueFactory<>("ki"));
        tabFano.setItems(fanoData);

        lbFanoHx.setText("信源熵：" + fano.getHx());
        lbFanoK.setText("平均码长：" + fano.getK());
        lbFanoP.setText("编码效率：" + fano.getP() * 100);
    }
}




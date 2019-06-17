package correct.ui;

import correct.GF2Matrix;
import correct.Group;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Seven
 * @description 纠错编码的控制类
 * @date 2019-06-04
 */
public class ThirdWork {

    /**
     * 线性分组码
     */
    private Group group;

    /**
     * 匹配矩阵的正则表达式
     */
    private static final Pattern MATRIX_FORMAT = Pattern.compile("^[0|1|\\s+]+$");

    /**
     * 匹配空白符的正则表达式
     */
    private static final Pattern BLANKSPACE_FORMAT = Pattern.compile("^[\\s+]$");

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfReceive;

    @FXML
    private Label lbAdjoint;

    @FXML
    private Button btnDecode;

    @FXML
    private Label lbEstimate;

    @FXML
    private TextArea txaGen;

    @FXML
    private Button btnInit;

    @FXML
    private TextArea txaSysGen;

    @FXML
    private Label lbIsError;

    @FXML
    private TextArea txaCheck;

    @FXML
    private Button btnEncode;

    @FXML
    private TableView<Group.ErrorPattern> tabErrorPattern;

    @FXML
    private TextField tfReallyMsg;

    @FXML
    private TextField tfCode;

    @FXML
    private Label lbCorrectMsg;

    @FXML
    private TableColumn<Group.ErrorPattern, String> colErrorPattern;

    @FXML
    private TableColumn<Group.ErrorPattern, String> colAdjoint;

    @FXML
    void initialize() {
        assert tfReceive != null : "fx:id=\"tfReceive\" was not injected: check your FXML file 'third.fxml'.";
        assert tfCode != null : "fx:id=\"tfCode\" was not injected: check your FXML file 'third.fxml'.";
        assert lbAdjoint != null : "fx:id=\"lbAdjoint\" was not injected: check your FXML file 'third.fxml'.";
        assert btnDecode != null : "fx:id=\"btnDecode\" was not injected: check your FXML file 'third.fxml'.";
        assert lbEstimate != null : "fx:id=\"lbEstimate\" was not injected: check your FXML file 'third.fxml'.";
        assert txaGen != null : "fx:id=\"txaGen\" was not injected: check your FXML file 'third.fxml'.";
        assert btnInit != null : "fx:id=\"btnInit\" was not injected: check your FXML file 'third.fxml'.";
        assert txaSysGen != null : "fx:id=\"txaSysGen\" was not injected: check your FXML file 'third.fxml'.";
        assert lbIsError != null : "fx:id=\"lbIsError\" was not injected: check your FXML file 'third.fxml'.";
        assert txaCheck != null : "fx:id=\"txaCheck\" was not injected: check your FXML file 'third.fxml'.";
        assert btnEncode != null : "fx:id=\"btnEncode\" was not injected: check your FXML file 'third.fxml'.";
        assert tabErrorPattern != null : "fx:id=\"tabErrorPattern\" was not injected: check your FXML file 'third.fxml'.";
        assert tfReallyMsg != null : "fx:id=\"tfReallyMsg\" was not injected: check your FXML file 'third.fxml'.";
        assert lbCorrectMsg != null : "fx:id=\"lbCorrectMsg\" was not injected: check your FXML file 'third.fxml'.";
        assert colErrorPattern != null : "fx:id=\"colErrorPattern\" was not injected: check your FXML file 'third.fxml'.";
        assert colAdjoint != null : "fx:id=\"colAdjoint\" was not injected: check your FXML file 'third.fxml'.";
        setOnClickListener();
    }

    /**
     * 设置点击的监听事件
     */
    private void setOnClickListener() {
        btnInit.setOnMouseClicked(event -> {
            txaSysGen.setText("");
            txaCheck.setText("");
            String G = txaGen.getText();
            int[][] g = getMultiRowsMatrix(G);
            if (g == null){
                return;
            }
            group = new Group(g);
            GF2Matrix gs = group.getGs();
            // 如果没有系统生成矩阵
            if (gs == null) {
                txaCheck.setText(group.getH().toString());
                txaGen.setText(group.getG().toString());
            } else {
                txaSysGen.setText(gs.toString());
                txaCheck.setText(group.getHs().toString());
            }
            colErrorPattern.setCellValueFactory(new PropertyValueFactory<>("e"));
            colAdjoint.setCellValueFactory(new PropertyValueFactory<>("s"));
            ObservableList<Group.ErrorPattern> data = FXCollections.observableArrayList(group.getTabData());
            tabErrorPattern.setItems(data);
        });

        btnEncode.setOnMouseClicked(event -> {
            if (group == null) {
                return;
            }
            int[] temp = getOneRowMatrix(tfReallyMsg.getText());
            GF2Matrix m = new GF2Matrix(temp);
            // 如果有系统生成矩阵，则使用系统生成矩阵生成码字
            GF2Matrix g = group.getGs() == null ? group.getG() : group.getGs();
            GF2Matrix c = GF2Matrix.multiplyMod2(m, g);
            tfCode.setText(c.toString());
        });

        btnDecode.setOnMouseClicked(event -> {
            // 将所有的标签信息清空
            lbAdjoint.setText("");
            lbIsError.setText("");
            lbEstimate.setText("");
            lbCorrectMsg.setText("");
            if (group == null) {
                return;
            }
            int[] temp = getOneRowMatrix(tfReceive.getText());
            GF2Matrix r = new GF2Matrix(temp);
            // 如果存在系统生成矩阵，则说明存在系统校验矩阵，则使用系统校验矩阵校验
            GF2Matrix h = group.getGs() == null ? group.getH() : group.getHs();
            GF2Matrix s = GF2Matrix.multiplyMod2(r, GF2Matrix.transpose(h));
            lbAdjoint.setText(s.toString());
            // 判断接收向量是否为码字
            if (s.isZero()) {
                lbIsError.setText("否");
                lbCorrectMsg.setText(r.toString().substring(0, group.getK()));
            } else {
                lbIsError.setText("是");
                GF2Matrix e = group.getErrorPattern().get(s);
                if (e == null) {
                    lbEstimate.setText("无法计算码字估值");
                    return;
                }
                // 纠错估值
                GF2Matrix estimate = GF2Matrix.addMod2(r, e);
                lbEstimate.setText(estimate.toString());
                lbCorrectMsg.setText(estimate.toString().substring(0, group.getK()));
            }

        });
    }

    /**
     * 根据文本获取矩阵（行数等于1）， "01001" -> {0, 1, 0, 0, 1}
     *
     * @param text 文本
     * @return 单行矩阵
     */
    private int[] getOneRowMatrix(String text) {
        int[] result = new int[text.length()];
        for (int i = 0; i < result.length; i++) {
            result[i] = Integer.parseInt(String.valueOf(text.charAt(i)));
        }
        return result;
    }

    /**
     * 根据文本获取矩阵行数大于1, "011\n101\n110\n001" -> {{0,1,1},{1,0,1},{1,1,0},{0,0,1}}
     *
     * @param text 文本
     * @return 多行矩阵
     */
    private int[][] getMultiRowsMatrix(String text) {
        // 如果字符串不满足格式，则返回null
        if (!MATRIX_FORMAT.matcher(text).matches()) {
            System.out.println("输入格式错误");
            return null;
        }

        // 去掉字符串最后的所有空字符
        while (BLANKSPACE_FORMAT.matcher(text.substring(text.length() - 1)).matches()) {
            text = text.substring(0, text.length() - 1);
        }
        int row = 1, column;
        for (Character c : text.toCharArray()) {
            if (c == '\n') {
                row++;
            }
        }
        String[] splits = text.split("[\\s+]+");
        column = splits.length / row;

        int[] ints = new int[splits.length];
        for (int i = 0; i < splits.length; i++) {
            try {
                ints[i] = Integer.parseInt(splits[i]);
            } catch (NumberFormatException e) {
                return null;
            }
        }

        int[][] raw = new int[row][column];
        int index = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                raw[i][j] = ints[index++];
            }
        }
        return raw;
    }
}

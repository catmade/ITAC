package coding;

import javafx.beans.property.StringProperty;

/**
 * @description 概率模型的每个节点
 * @author Seven
 * @date 2019-04-19
 */
public class Node {
    /**
     * 每个节点的符号
     */
    public String symbol;

    /**
     * 概率
     */
    public double p;

    /**
     * 计算完成后的编码
     */
    public String codes;

    /**
     * 码长
     */
    public int ki = 0;

    /**
     * 当前节点的下标（fano编码使用）
     */
    public int index;

    /**
     * 创建一个节点
     * @param p 概率
     * @param index 当前节点的下标
     */
    public Node(double p, int index) {
        codes = "";
        this.p = p;
        this.index = index;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public int getKi() {
        return ki;
    }

    public void setKi(int ki) {
        this.ki = ki;
    }
}

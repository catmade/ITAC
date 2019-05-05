package coding;

/**
 * @author Seven
 * @description 编码类，其他编码需要继承此类
 * @date 2019-05-04
 */
public class Code {
    /**
     * 信源熵
     */
    protected double hx = 0;

    /**
     * 平均码长
     */
    protected double k = 0;

    /**
     * 编码效率
     */
    protected double p = 0;

    /**
     * 信息率
     */
    protected double r = 0;

    /**
     * 计算编码的一些数据，默认为是对单符号信源编二进制码
     * @param nodes 每个符号的信息
     */
    protected void initData(Node[] nodes){
        for (Node node : nodes) {
            node.ki = node.codes.length();
            hx += -1 * node.p * (Math.log(node.p) / Math.log(2));
            k += node.ki * node.p;
        }
        k = Math.round(k * 1000000000) / 1000000000.0;
        p = hx / k;
    }

    public double getHx() {
        return hx;
    }

    public double getK() {
        return k;
    }

    public double getP() {
        return p;
    }
}

package coding;

/**
 * @description 概率模型的每个节点
 * @author Seven
 * @date 2019-04-19
 */
public class Node {

    /**
     * 概率
     */
    public double p;

    /**
     * 计算完成后的编码
     */
    public String codes;

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

}

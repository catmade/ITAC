package coding;

/**
 * @description 概率模型的每个节点
 * @author Seven
 * @date 2019-04-19
 */
public class Node {
    /**
     * 概率用分数表示，取分母
     */
    public int denominator;

    /**
     * 概率用分数表示，取分子
     */
    public int p;

    /**
     * 计算完成后的编码
     */
    public String codes;

    /**
     * 计算完成后的编码
     */
    public int index;

    /**
     * 创建一个节点
     * @param denominator 概率的分数形式的分子
     * @param p 概率的分数形式的分子
     * @param index 当前节点的下标
     */
    public Node(int denominator, int p, int index) {
        codes = "";
        this.denominator = denominator;
        this.p = p;
        this.index = index;
    }
}

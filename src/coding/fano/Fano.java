package coding.fano;

import coding.Node;

/**
 * @author Seven
 * @description 费诺编码
 * @date 2019-04-19
 */
public class Fano {

    private Node[] nodes;

    /**
     * 构造函数
     * @param p 整型数组
     */
    public Fano(double[] p) {
        nodes = new Node[p.length];
        for(int i = 0; i < nodes.length; i++){
            nodes[i] = new Node(p[i], i);
        }
        divide(nodes);
        for (Node node : nodes) {
            node.ki = node.codes.length();
        }
    }

    /**
     * 用递归对数据进行分组并编码
     * @param ns 数据节点，由概率、下标、编码组成
     */
    private void divide(Node[] ns) {
        if (ns.length == 1) {
            return;
        }

        int middleIndex = findMiddleIndex(ns);
        divide(getFirst(ns, middleIndex));
        divide(getSecond(ns, middleIndex));
    }

    /**
     * 得到数组的第一组
     *
     * @param ns           整型数组
     * @param middleIndex 分割下标
     * @return 从第一个开始到下标所在元素（包括）的所有数组
     */
    private Node[] getFirst(Node[] ns, final int middleIndex) {
        if(ns.length == 2){
            nodes[ns[0].index].codes += "0";
            return new Node[]{ns[0]};
        }

        Node[] first = new Node[middleIndex + 1];
        for (int i = 0; i <= middleIndex; i++) {
            first[i] = ns[i];
            nodes[ns[i].index].codes += "0";
        }
        return first;
    }

    /**
     * 得到数组的第二组
     *
     * @param ns          整型数组
     * @param middleIndex 分割下标
     * @return 从下标所在元素的后一个开始的剩下所有元素
     */
    private Node[] getSecond(Node[] ns, final int middleIndex) {
        if(ns.length == 2){
            nodes[ns[1].index].codes += "1";
            return new Node[]{ns[1]};
        }

        Node[] second = new Node[ns.length - middleIndex - 1];
        for (int i = 0; i < second.length; i++) {
            second[i] = ns[i + 1 + middleIndex];
            nodes[second[i].index].codes += "1";
        }
        return second;
    }

    /**
     * @return 合适的分割下标
     * @description 计算将数组分成两组的合适的分割下标，从后一个开始分割
     * @author Seven
     * @date 2019/4/19
     * @params p 整型数组
     */
    private int findMiddleIndex(Node[] ns) {
        double[] delta = new double[ns.length];
        //设min为无穷大，这里因为概率之和为一，大于1的数都可认为是无穷大
        double min = 10;

        int resultIndex = 0;
        for (int i = 0; i < ns.length - 1; i++) {
            delta[i] = getDelte(ns, i);
            if (min > delta[i]) {
                min = delta[i];
                resultIndex = i;
            }
        }
        return resultIndex;
    }

    /**
     * 整型数组分成两组后，求两组和的差
     * @param ns 节点数组
     * @param middleIndex 分割位置，middleIndex后的数组为一组，其他的为前一组
     * @return 两组差值
     */
    private double getDelte(Node[] ns, int middleIndex) {
        double first = 0, second = 0;
        for (int i = 0; i <= middleIndex; i++) {
            first += ns[i].p;
        }
        for (int j = middleIndex + 1; j <= ns.length - middleIndex - 1; j++) {
            second += ns[j].p;
        }
        return Math.abs(first - second);
    }

    /**
     * 获得节点数组
     * @return 节点数组
     */
    public Node[] getNodes() {
        return nodes;
    }

    /**
     * 为每个节点赋值一个符号名称
     * @param index 节点的下标
     * @param symbol 符号
     */
    public void setNodesSymbol(int index, String symbol) {
        this.nodes[index].symbol = symbol;
    }
}












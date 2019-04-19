package coding.fano;

import coding.Node;

/**
 * @author Seven
 * @description 费诺编码
 * @date 2019-04-19
 */
public class Pro {


    /**
     * @param p 概率
     * @return void
     * @description 初始化
     * @author Seven
     * @date 2019/4/19
     */
    public Pro(int[] p, int den) {
        Node[] nodes = new Node[p.length];
        for(int i = 0; i < nodes.length; i++){
            nodes[i] = new Node(den, p[i], i);
        }
        divide(nodes);
        for(Node node : nodes) {
            System.out.print(node.codes + " ");
        }
    }

    /**
     * 初始化codes数组
     *
     * @return
     */
    private void divide(Node[] pro) {
        if (pro.length == 1) {
            return;
        }

        int middleIndex = findMiddleIndex(pro);
        divide(getFirst(pro, middleIndex));
        divide(getSecond(pro, middleIndex));
    }


    public int[] getP() {
        return p;
    }

    public String[] getCodes() {
        return codes;
    }

    /**
     * 得到数组的第一组
     *
     * @param p           整型数组
     * @param middleIndex 分割下标
     * @return 从第一个开始到下标所在元素（包括）的所有数组
     */
    private int[] getFirst(int[] p, int middleIndex) {
        int[] first = new int[middleIndex + 1];
        for (int i = 0; i <= middleIndex; i++) {
            first[i] = p[i];
            codes[i] += "0";
        }
        return first;
    }

    /**
     * 得到数组的第二组
     *
     * @param p           整型数组
     * @param middleIndex 分割下标
     * @return 从下标所在元素的后一个开始的剩下所有元素
     */
    private int[] getSecond(int[] p, int middleIndex) {
        int[] second = new int[p.length - middleIndex - 1];
        for (int i = 0; i < second.length; i++) {
            second[i] = p[second.length + middleIndex];
            codes[i + middleIndex] += "1";
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
    private int findMiddleIndex(Node[] nodes) {
        int[] delta = new int[nodes.length];
        int min = 0;
        for (Node node : nodes) {
            min += node.p;
        }
        int resultIndex = 0;
        for (int i = 0; i < nodes.length - 1; i++) {
            delta[i] = getDelte(p, i);
            if (min > delta[i]) {
                min = delta[i];
                resultIndex = i;
            }
        }
        return resultIndex;
    }

    /**
     * @return 两组差值
     * @description 整型数组分成两组后，求两组和的差
     * @author Seven
     * @date 2019/4/19
     * @params p 数组
     * @params middleIndex 分割位置，middleIndex后的数组为一组，其他的为前一组
     */
    private int getDelte(int[] p, int middleIndex) {
        int first = 0, second = 0;
        for (int i = 0; i <= middleIndex; i++) {
            first += p[i];
        }
        for (int j = middleIndex + 1; j <= p.length - middleIndex - 1; j++) {
            second += p[j];
        }
        return Math.abs(first - second);
    }


}












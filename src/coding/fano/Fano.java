package coding.fano;

/**
 * @author Seven
 * @description 费诺编码
 * @date 2019-04-19
 */
public class Fano {
    /**
     * 概率用分数表示，取分母
     */
    public static int denominator;

    /**
     * 概率用分数表示，取分子
     */
    private int[] p;

    /**
     * 计算完成后的编码
     */
    private String[] codes;

    /**
     * @param p 概率
     * @return void
     * @description 初始化
     * @author Seven
     * @date 2019/4/19
     */
    public Fano(int... p) {
        this.p = p;
        codes = new String[p.length];
        for (String c : codes) {
            c = "*";
        }
        divide(p);
        for (String s : codes) {
            System.out.print(s + " ");
        }
    }

    /**
     * 初始化codes数组
     *
     * @return
     */
    private void divide(int[] pro) {
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
    private int findMiddleIndex(int[] p) {
        int[] delta = new int[p.length];
        int min = 0;
        for (int ii : p) {
            min += ii;
        }
        int resultIndex = 0;
        for (int i = 0; i < p.length - 1; i++) {
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












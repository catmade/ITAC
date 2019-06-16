package capacity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Seven
 * @description 信道类
 * @date 2019-05-22
 */
public class Channel {

    /**
     * 结果
     */
    private List<Result> results;

    /**
     * 信道容量单位
     */
    private String unit = "bit/sign";

    /**
     * 信道转化矩阵
     */
    private final double[][] p;

    /**
     * 信道矩阵的行数(n)和列数(m)
     */
    private final int row, column;

    /**
     * 初始化信道矩阵
     *
     * @param row    行数(n)
     * @param column 列数(m)
     * @param ps     数据
     */
    public Channel(int row, int column, double[] ps) {
        this.row = row;
        this.column = column;
        this.p = new double[row][column];
        results = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                p[i][j] = ps[index++];
            }
        }
        calcCapacity();
    }

    public List<Result> getResults() {
        return results;
    }

    /**
     * 计算信道容量
     */
    private void calcCapacity() {
        Kind[] kinds = getKind();
        for (Kind kind : kinds) {
            if (kind == Kind.ordinary) {
                results.add(calcOrdinary());
            } else {
                results.add(calcSpecial(kind));
            }
        }
    }

    /**
     * 求一般信道的容量
     *
     * @return 计算结果
     */
    private Result calcOrdinary() {
        // 输入符号xi的概率
        double[] p = new double[row];
        // 反条件概率，即接收为yj的情况下发送为xi的概率，q(xi|yj) = r(xi)r(yj|xi) /
        double[] q = new double[column];
        double[] a = new double[row];

        double u = 0;
        double iu = 0;
        double e = 1;
        double c = 0;

        double channel;
        int iteratorTime = 0;
        long timer = System.nanoTime();

        do {
            iteratorTime++;
            // 假定初始概率分布为均匀分布
            for (int i = 0; i < row; i++) {
                p[i] = 1.0 / row;
            }
            // 计算反条件概率
            for (int i = 0; i < column; i++) {
                for (int j = 0; j < row; j++) {
                    q[i] += p[j] * this.p[j][i];
                }
            }
            for (int i = 0; i < a.length; i++) {
                a[i] = 1;
            }
            // 计算中间值
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    a[i] *= Math.pow(this.p[i][j] / q[j], this.p[i][j]);
                }
            }
            for (int i = 0; i < row; i++) {
                u += p[i] * a[i];
            }
            channel = log2(u);
            iu = log2(max(a));
            c = iu - channel;
            for (int i = 0; i < row; i++) {
                p[i] = p[i] * a[i] / u;
            }
        } while (c > e);
        long l = (System.nanoTime() - timer) / 100;
        return new IteratorResult(Kind.ordinary, channel, l, iteratorTime, p);
    }

    /**
     * 求特殊信道的信道容量
     *
     * @param kind 特殊信道的类别
     * @return 计算结果
     */
    private Result calcSpecial(Kind kind) {
        double c = 0;
        long timer = System.nanoTime();
        switch (kind) {
            case oneToOne:
            case extensiable:
                c = log2(row);
                break;
            case mergable:
                c = log2(column);
                break;
            case strong:
                double average = this.p[0][0];
                double p = 1 - average;
                p = solveDoubleTrap(p);
                c = log2(row) + average * log2(average) + p * log2(p / (row - 1));
                break;
            case symmetry:
                c = log2(column) - H(this.p[0]);
                break;
            case quasi:
                double raw = 0;
                // TODO
                c = -raw - H(this.p[0]);
                break;
            default:
        }
        long time = (System.nanoTime() - timer) / 1000;
        return new Result(kind, c, time);
    }

    /**
     * 计算数组中的最大值
     *
     * @param x 数组
     * @return 最大值
     */
    private double max(double[] x) {
        // 使用冒泡排序从小到大排序
        double temp = 0;
        for (int j = 0; j < x.length - 1; j++) {
            for (int i = 0; i < x.length - 1 - j; i++) {
                if (x[i] > x[i + 1]) {
                    temp = x[i];
                    x[i] = x[i + 1];
                    x[i + 1] = temp;
                }
            }
        }
        // 取排序后的最后一个数即为最大值
        return x[x.length - 1];
    }

    /**
     * 判断信道类型
     *
     * @return 信道类型
     */
    private Kind[] getKind() {
        List<Kind> kinds = new ArrayList<>();
        if (isOneToOne()) {
            kinds.add(Kind.oneToOne);
        } else if (isExtensiable()) {
            kinds.add(Kind.extensiable);
        } else if (isMergable()) {
            kinds.add(Kind.mergable);
        } else if (isStrong()) {
            kinds.add(Kind.strong);
        } else if (isSymmetry()) {
            kinds.add(Kind.symmetry);
        } else if (isQuasi()) {
            kinds.add(Kind.quasi);
        }
        kinds.add(Kind.ordinary);
        return (Kind[]) kinds.toArray(new Kind[kinds.size()]);
    }

    //判断元素是否属于数组
    private boolean isBelong(List p,double x){
        for (int i = 0; i <p.size(); i++) {
            if(x==(double)p.get(i)) {
                return true;
            }
        }
        return false;
    }

    //转置矩阵
    private double[][] reverseArray(double temp [][]) {
        double[][] p1=new double[temp[0].length][temp.length];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                p1[j][i]=temp[i][j];
            }
        }
        return  p1;
    }


    private boolean isQuasi() {
        //行可排列性
        List<Double> listR = new ArrayList<>();
        for (int i = 1; i < row; i++) {
            for (int k = 0; k < p[0].length; k++) {
                listR.add(p[0][k]);
            }
            for (int j = 0; j < column; j++) {
                if (isBelong(listR, p[i][j])) {
                    listR.remove(p[i][j]);
                } else
                    return false;
            }
        }
        return false;
    }


    private boolean isSymmetry() {
        double[][] p1=reverseArray(p);
        //建立集合判断
        List<Double> listR = new ArrayList<>();
        List<Double> listC = new ArrayList<>();
        //行可排列性
        for (int i = 1; i < row; i++) {
            for (int k = 0; k < p[0].length; k++) {
                listR.add(p[0][k]);
            }
            for (int j = 0; j < column; j++) {
                if(isBelong(listR,p[i][j])){
                    listR.remove(p[i][j]);
                }
                else
                    return false;
            }
        }
        //列可排列性
        for (int i = 1; i < column; i++) {
            for (int k = 0; k < p1[0].length; k++) {
                listC.add(p1[0][k]);
            }
            for (int j = 0; j < row; j++) {
                if(isBelong(listC,p[j][i])){
                    listC.remove(p[j][i]);
                }
                else
                    return false;
            }
        }
        return true;
    }

    private boolean isStrong() {
        if(column!=row)
            return false;
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row; j++) {
                if(i==j){
                    if(p[i][j]!=p[0][0])
                        return false;
                }
                else{
                    if(p[i][j]!=p[0][1])
                        return false;
                }
            }
        }
        double sum=0;
        for (int i = 0; i < column; i++) {
            sum+=p[0][i];
        }
        if(Math.abs(sum-1)>0.0000001)
            return false;
        return true;
    }

    private boolean isMergable() {
        int cnt;
        //判断矩阵是否由0、1组成
        for (int i = 0; i <column ; i++) {
            for (int j = 0; j <row ; j++) {
                if(p[j][i]!=0&&p[j][i]!=1) {
                    return false;
                }
            }
        }
        for (int i=0;i<column;i++){
            cnt=0;
            for (int j=0;j<row;j++){
                if(p[j][i]==1) {
                    cnt++;
                }
            }
            if(cnt<1) {
                return false;
            }
        }
        for (int i = 0; i <row ; i++) {
            cnt=0;
            for (int j = 0; j <column ; j++) {
                if(p[i][j]==1) {
                    cnt++;
                }
            }
            if(cnt!=1) {
                return false;
            }
        }
        return true;
    }

    private boolean isExtensiable() {
        int cnt;
        for (int i=0;i<column;i++){
            cnt=0;
            for (int j=0;j<row;j++){
                if(p[j][i]!=0) {
                    cnt++;
                }
            }
            if(cnt!=1) {
                return false;
            }
        }
        return true;
    }

    private boolean isOneToOne() {
        //判断矩阵是否由0、1组成
        for (int i = 0; i <column ; i++) {
            for (int j = 0; j <row ; j++) {
                if(p[j][i]!=0&&p[j][i]!=1) {
                    return false;
                }
            }
        }
        if(p[0][0]==1) {
            for (int i = 0; i < column; i++) {
                for (int j = 0; j < row; j++) {
                    if(i==j&&p[j][i]!=1) {
                        return false;
                    }
                }
            }
        }
        else {
            for (int i = 0; i < column; i++) {
                for (int j = row - 1; j >= 0; j--) {
                    if (((i + j) == row-1) && (p[j][i] != 1)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 解决浮点数陷阱问题，不解决的话，会在数值加减和数值比较时产生意想不到的问题
     * 如：将0.9999999999转为1.0（假定理想值为1.0）
     *
     * @param n 原来的数
     * @return 修复后的数
     */
    private double solveDoubleTrap(double n) {
        return n;
    }

    /**
     * 求以2为底的对数
     *
     * @param exponent 指数
     * @return 对数值
     */
    private double log2(double exponent) {
        return Math.log(exponent) / Math.log(2);
    }

    /**
     * 求自信息量
     *
     * @param p 概率(0<p<=1)
     * @return 自信息量值
     */
    private double I(double p) {
        // TODO 检测概率的范围
        return -p * log2(p);
    }

    private double H(double[] ps) {
        // TODO 检测概率和为1
        double result = 0;
        for (double p : ps) {
            result += I(p);
        }
        return result;
    }

    /**
     * 信道种类
     */
    public enum Kind {
        /**
         * 一般信道
         */
        ordinary,

        /**
         * 离散无噪声信道，具有一一对应关系的无噪声信道
         */
        oneToOne,

        /**
         * 离散无噪声信道，具有扩展性能的无噪声信道
         */
        extensiable,

        /**
         * 离散无噪声信道，具有归并性能的无噪声信道
         */
        mergable,

        /**
         * 强对称离散信道
         */
        strong,

        /**
         * 对称离散信道
         */
        symmetry,

        /**
         * 准对称离散信道
         */
        quasi;

        @Override
        public String toString() {
            switch (this) {
                case ordinary:
                    return "一般信道";
                case oneToOne:
                    return "具有一一对应关系的无噪声信道";
                case extensiable:
                    return "具有扩展性能的无噪声信道";
                case mergable:
                    return "具有归并性能的无噪声信道";
                case strong:
                    return "强对称离散信道";
                case symmetry:
                    return "对称离散信道";
                case quasi:
                    return "准对称离散信道";
                default:
                    return null;
            }
        }
    }

    /**
     * 结果类
     */
    public class Result {
        /**
         * 信道类别
         */
        private Kind kind;

        /**
         * 信道容量
         */
        private double c;

        /**
         * 耗时（微秒）
         */
        private long time;

        public Result(Kind kind, double c, long time) {
            this.kind = kind;
            this.c = c;
            this.time = time;
        }

        public Kind getKind() {
            return kind;
        }

        public double getC() {
            return c;
        }

        public long getT() {
            return time;
        }

        @Override
        public String toString() {
            return "信道类别：" + kind + "\n" +
                    "信道容量：" + String.format("%.4f", c) + unit + "\n" +
                    "耗时:    " + time + "(微秒)" + "\n";
        }
    }

    /**
     * 迭代算法的结果
     */
    public class IteratorResult extends Result {
        /**
         * 如果使用迭代算法的话，会有迭代次数
         */
        private int iterator;

        /**
         * 最佳信源分布
         */
        private double[] p;

        public IteratorResult(Kind kind, double c, long time, int iterator, double[] p) {
            super(kind, c, time);
            this.iterator = iterator;
            this.p = p;
        }

        public int getIterator() {
            return iterator;
        }

        public double[] getP() {
            return p;
        }

        @Override
        public String toString() {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < p.length; i++) {
                 s.append("p[").append(i + 1).append("] = ").append(p[i]).append("\n");
            }
            return super.toString() + "迭代次数：" + iterator + "\n" +
                    "最佳信源分布：\n" + s.toString();
        }
    }
}
















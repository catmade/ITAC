package capacity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Seven
 * @description 信道类
 * @date 2019-05-22
 */
public class Channel {

    /**
     * 特殊方法计算的信道容量
     */
    private double specialC = -1;

    /**
     * 特殊方法耗时
     */
    private long specialTime;

    /**
     * 用一般方法计算得出的信道容量
     */
    private double ordinaryC = -1;

    /**
     * 一般方法耗时
     */
    private long ordinaryTime;

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

    private Kind[] kinds;

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
        quasi,

        /**
         * 无法计算的信道
         */
        none;

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
                case none:
                    return "无法计算的信道";
                default:
                    return null;
            }
        }
    }

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
        int index = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                p[i][j] = ps[index++];
            }
        }
        // TODO delete
        printMatrix("信道矩阵", this.p);
        calcCapacity();
    }

    private double getSpecialC() {
        return specialC;
    }

    private Kind[] getKinds() {
        return kinds;
    }

    public List<String[]> resultsToString() {
        List<String[]> results = new ArrayList<>();
        if (ordinaryC != -1){
            results.add(new String[]{Kind.ordinary.toString(), ordinaryC + "", ordinaryTime + ""});
        }
        if (specialC != -1){
            results.add(new String[]{kinds[0].toString(), specialC + "", specialTime + ""});
        }
        if (results.size() == 0){
            return null;
        }
        return results;
    }

    /**
     * 计算信道容量
     */
    private void calcCapacity() {
        kinds = getKind();
        if (kinds.length == 1 && kinds[0] == Kind.none) {
            return;
        }
        for (Kind kind : kinds) {
            Calendar timer = Calendar.getInstance();
            switch (kind) {
                case ordinary:
                    calcOrdinary();
                    break;
                case oneToOne:
                case extensiable:
                    specialC = log2(row);
                    break;
                case mergable:
                    specialC = log2(column);
                    break;
                case strong:
                    double average = this.p[0][0];
                    double p = 1 - average;
                    p = solveDoubleTrap(p);
                    specialC = log2(row) + average * log2(average) + p * log2(p / (row - 1));
                    break;
                case symmetry:
                    specialC = log2(column) - H(this.p[0]);
                    break;
                case quasi:
                    break;
                default:
            }
            long time = Calendar.getInstance().getTimeInMillis() - timer.getTimeInMillis();
            System.out.println("信道类型：" + kind);
            if (kind == Kind.ordinary){
                ordinaryTime = time;
                System.out.println("信道容量：" + ordinaryC);
                System.out.println("耗时：" + ordinaryTime);
            } else if (kind != Kind.none){
                specialTime = time;
                System.out.println("信道容量：" + specialC);
                System.out.println("耗时：" + specialTime);

            }
        }
    }

///    private void calcOrdinary() {
//        // 1.求βj
//        double[] bj = new double[column];
//
//        // 2.求信道容量，
//        double count = 0;
//        for (double b : bj) {
//            count += Math.pow(2, b);
//        }
//        specialC = log2(count);
//
//        // 3.求p(bj);
//        double[] pbj = new double[column];
//        for (int j = 0; j < pbj.length; j++) {
//            pbj[j] = Math.pow(2, bj[j] - specialC);
//        }
//
//        // 4.求p(ai)
//        double[] pai = new double[row];
//        // 构造方程组矩阵
//        double[][] matrix = new double[column][row + 1];
//        for (int i = 0; i < row; i++) {
//            for (int j = 0; j < column; j++) {
//                matrix[j][i] = this.p[i][j];
//            }
//        }
//        for (int i = 0; i < matrix.length; i++){
//            matrix[i][row] = pbj[i];
//        }
//        // TODO delete
//        printMatrix("矩阵方程" ,matrix);
//
//        // 求解方程组
//        pai = Matrix.getResult(matrix);
//
//        // 判断信道容量是否存在，即p(ai)是否全部>=0，
//        boolean exist = true;
//        for (double p : pai) {
//            if (p < 0) {
//                exist = false;
//                break;
//            }
//        }
//
//        // 如果不存在，调整p(ai)，再重新求解
//        if (!exist) {
//
//        }
///    }

    /**
     * 求一般的情况
     */
    private void calcOrdinary() {

        // 具体的迭代方法
        double[] p = new double[row];

        double[] q = new double[row];

        double[] a = new double[row];

        double u = 0;

        double iu = 0;
        double e = 1;
        double c = 0;

        do {

            // 1.求βj
            for (int i = 0; i < p.length; i++) {
                p[i] = 1.0 / p.length;
            }

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < this.p[i].length; j++) {
                    q[i] += p[i] * this.p[j][i];
                }
            }

            for (int i = 0; i < row; i++) {
                a[i] = 1;
            }

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    a[i] *= Math.pow(this.p[i][j] / q[j], this.p[i][j]);
                }
            }

            for (int i = 0; i < row; i++) {
                u += p[i] * a[i];
            }
            ordinaryC = log2(u);

            iu = log2(max(a));

            c = iu - ordinaryC;

            for (int i = 0; i < row; i++) {
                p[i] = p[i] * a[i] / u;
            }

        } while (c > e);
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

        if (row == column) {
            kinds.add(Kind.ordinary);
        }

        if (kinds.size() == 0) {
            kinds.add(Kind.none);
        }
        return (Kind[]) kinds.toArray(new Kind[kinds.size()]);
    }

    private boolean isQuasi() {
        return false;
    }

    private boolean isSymmetry() {
        return false;
    }

    private boolean isStrong() {
        if (row == column) {

        }
        return false;
    }

    private boolean isMergable() {
        return false;
    }

    private boolean isExtensiable() {
        return true;
    }

    private boolean isOneToOne() {
        if (row == column) {
            // 矩阵的某一行的概率和
            double count;
            for (int i = 0; i < row; i++) {
                count = 0;
                for (double d : p[i]) {
                    count += d;
                }
                count = solveDoubleTrap(column);
                if (!(count == 1 && p[i][i] == 1)) {
                    return false;
                }
            }
        }
        return false;
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
     * 打印矩阵
     *
     * @param title  标题
     * @param matrix 矩阵
     */
    private void printMatrix(String title, double[][] matrix) {
        // 打印矩阵
        System.out.println("=============================" + title + "===========================");
        String s = null;
        for (double[] row : matrix) {
            for (double n : row) {
                s = String.format("%-7s", n);
                System.out.print(s + " ");
            }
            System.out.println();
        }
        System.out.println("============================================================================\n\n");
    }
}
















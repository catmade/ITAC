package capacity;

/**
 * @author Seven
 * @description 信道类
 * @date 2019-05-22
 */
public class Channel {

    /**
     * 信道容量
     */
    private double capacity;

    /**
     * 信道
     */
    private double[][] p;

    /**
     * 信道矩阵的行数和列数
     */
    private int row, column;

    /**
     * 信道种类
     */
    enum Kind {
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
        quasi
    }

    /**
     * 初始化信道矩阵
     *
     * @param row    行数
     * @param column 列数
     * @param ps     数据
     */
    Channel(int row, int column, double[] ps) {
        this.row = row;
        this.column = column;
        this.p = new double[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                p[i][j] = ps[i + j];
            }
        }
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    /**
     * 计算信道容量
     */
    private void calcCapacity() {
        switch (getKind()) {
            case ordinary:
                break;
            case oneToOne:
                break;
            case extensiable:
                break;
            case mergable:
                break;
            case strong:
                break;
            case symmetry:
                break;
            case quasi:
                break;
            default:
        }
    }

    /**
     * 判断信道类型
     *
     * @return 信道类型
     */
    private Kind getKind() {
        if (isOneToOne()) {
            return Kind.oneToOne;
        }
        if (isExtensiable()) {
            return Kind.extensiable;
        }
        if (isMergable()) {
            return Kind.mergable;
        }
        if (isStrong()) {
            return Kind.strong;
        }
        if (isSymmetry()) {
            return Kind.symmetry;
        }
        if (isQuasi()) {
            return Kind.quasi;
        }
        return Kind.ordinary;
    }

    private boolean isQuasi() {
        return false;
    }

    private boolean isSymmetry() {
        return false;
    }

    private boolean isStrong() {
        return false;
    }

    private boolean isMergable() {
        return false;
    }

    private boolean isExtensiable() {
        return false;
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

}

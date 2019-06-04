package correct;

/**
 * @author Seven
 * @description Galoi域，GF(2)
 * @date 2019-06-03
 */
public class GF2Matrix {
    /**
     * 矩阵
     */
    private final int[][] g;

    /**
     * 矩阵的行数和列数
     */
    private final int row, column;

    public GF2Matrix(int[] g) {
        this.g = new int[1][g.length];
        this.g[0] = g;
        row = 1;
        column = g.length;
    }

    public GF2Matrix(int[][] g) {
        this.g = g;
        row = g.length;
        column = g[0].length;
    }

    public int[][] getG() {
        return g;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    /**
     * 判断矩阵是否为0矩阵
     *
     * @return 是否为0矩阵
     */
    public boolean isZero() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (g[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断矩阵是否为单位矩阵
     *
     * @return 是否为单位矩阵
     */
    public boolean isUnit() {
        if (row != column) {
            return false;
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (i == j) {
                    if (g[i][j] != 1) {
                        return false;
                    }
                } else if (g[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (row == 1){
            for (int i = 0; i < column; i++) {
                result.append(g[0][i]);
            }
            return result.toString();
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.append(g[i][j]).append(" ");
            }
            result.append("\n");
        }
        // 返回的结果是去掉最后一个回车符的字符串
        return result.substring(0, result.length());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof GF2Matrix) {
            GF2Matrix that = (GF2Matrix) obj;
            if (that.row != this.row || that.column != this.column) {
                return false;
            }
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    if (that.g[i][j] != this.g[i][j]) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    /**
     * 求矩阵的转置
     *
     * @param m 原矩阵
     * @return 矩阵的转置
     */
    public static GF2Matrix transpose(GF2Matrix m) {
        int[][] g = m.getG();
        int[][] raw = new int[m.getColumn()][m.getRow()];
        for (int i = 0; i < raw.length; i++) {
            for (int j = 0; j < raw[0].length; j++) {
                raw[i][j] = g[j][i];
            }
        }
        return new GF2Matrix(raw);
    }

    /**
     * 求矩阵的模2加法
     *
     * @param a a
     * @param b b
     * @return a + b
     */
    public static GF2Matrix addMod2(GF2Matrix a, GF2Matrix b) {
        if (a.getColumn() != b.getColumn() || a.getRow() != b.getRow()) {
            throw new ArithmeticException("a为" + a.getRow() + "行" + a.getColumn()
                    + "列矩阵，b为" + b.getRow() + "行" + b.getColumn() + "列矩阵。"
                    + "两个矩阵的行数和列数不相等，无法相加");
        }
        int[][] as = a.getG();
        int[][] bs = b.getG();
        int[][] cs = new int[a.getRow()][a.getColumn()];
        for (int i = 0; i < a.getRow(); i++) {
            for (int j = 0; j < a.getColumn(); j++) {
                cs[i][j] = (as[i][j] + bs[i][j]) % 2;
            }
        }
        return new GF2Matrix(cs);
    }

    /**
     * 求矩阵的模2乘法
     *
     * @param a a
     * @param b b
     * @return a × b
     */
    public static GF2Matrix multiplyMod2(GF2Matrix a, GF2Matrix b) {
        if (a.getColumn() != b.getRow()) {
            throw new ArithmeticException("a为" + a.getRow() + "行" + a.getColumn()
                    + "列矩阵，b为" + b.getRow() + "行" + b.getColumn() + "列矩阵。"
                    + "两个矩阵的行数和列数不匹配，无法相乘");
        }
        int[][] as = a.getG();
        int[][] bs = b.getG();
        int[][] cs = new int[a.getRow()][b.getColumn()];
        for (int i = 0; i < a.getRow(); i++) {
            for (int j = 0; j < b.getColumn(); j++) {
                for (int k = 0; k < a.getColumn(); k++) {
                    cs[i][j] += as[i][k] * bs[k][j];
                }
                cs[i][j] %= 2;
            }
        }
        return new GF2Matrix(cs);
    }

    /**
     * 求矩阵的最小汉明距离
     *
     * @param m
     * @return
     */
    public static int getMinDistance(GF2Matrix m) {
        return 0;
    }

    /**
     * 判断矩阵是否可逆
     *
     * @param m 矩阵
     * @return 是否可逆
     */
    private static boolean reversible(GF2Matrix m) {
        return false;
    }
}

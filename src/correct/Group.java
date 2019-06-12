package correct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Seven
 * @description 线性分组码
 * @date 2019-06-03
 */
public class Group {

    /**
     * (n, k)线性分组码的生成矩阵
     */
    private GF2Matrix G;

    /**
     * (n, k)线性分组码的系统生成矩阵
     */
    private GF2Matrix Gs;

    /**
     * 校验矩阵 G乘H的转置 = 0
     */
    private GF2Matrix H;

    /**
     * 系统校验矩阵 G乘H的转置 = 0
     */
    private GF2Matrix Hs;

    /**
     * 码字长度和信息向量长度
     */
    private final int n, k;

    /**
     * 差错向量，长度为 n（差错图样）,e = y - u
     */
    private int[] e;

    /**
     * 伴随式，长度为 k
     */
    private int[] s;

    /**
     * 信息向量，长度为 k (用x或者m表示)
     */
    private int[] x;

    /**
     * 码字，长度为 n（用u或者c表示）
     */
    private int[] u;

    /**
     * 输出值，长度为 n
     */
    private int[] y;

    /**
     * 差错图案表，键为伴随式s，值为差错图案e
     */
    private Map<GF2Matrix, GF2Matrix> errorPattern;

    /**
     * 用来填充表格信息
     */
    private List<ErrorPattern> tabData;

    public GF2Matrix getG() {
        return G;
    }

    public GF2Matrix getGs() {
        return Gs;
    }

    public GF2Matrix getH() {
        return H;
    }

    public GF2Matrix getHs() {
        return Hs;
    }

    /**
     * @param G 生成矩阵
     */
    public Group(int[][] G) {
        this.G = new GF2Matrix(G);
        this.n = this.G.getColumn();
        this.k = this.G.getRow();
        System.out.println("输入的矩阵：\n" + this.G);
        tryToGenerateGs();
        generateH();
        generateErrorPattern();
    }

    /**
     * 生成校验矩阵
     */
    private void generateH() {
        GF2Matrix g = (Gs == null ? G : Gs);
        int[][] G = g.getG();
        int row = G.length;
        int column = G[0].length;
        // 校验矩阵，前几列位I，后几列为单位矩阵
        int[][] H = new int[column - row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column - row; j++) {
                H[j][i] = G[i][row + j];
            }
        }
        for (int i = 0; i < column - row; i++) {
            for (int j = 0; j < column - row; j++) {
                H[i][row + j] = i == j ? 1 : 0;
            }
        }

        if (this.Gs == null) {
            this.H = new GF2Matrix(H);
            System.out.println("生成矩阵：\n" + this.H);
        } else {
            this.Hs = new GF2Matrix(H);
            System.out.println("系统生成矩阵：\n" + this.Hs);
        }
    }

    /**
     * 尝试将矩阵化简为系统生成矩阵
     */
    private void tryToGenerateGs() {
        // 判断是否可以化简为系统生成矩阵
        int[][] simplified = GF2Matrix.simplify(G).getG();
        boolean canBeSimplified = true;
        for (int i = 0; i < simplified.length; i++) {
            for (int j = 0; j < simplified.length; j++) {
                if (i == j && simplified[i][j] == 1) {
                    continue;
                }
                if (i != j && simplified[i][j] == 0) {
                    continue;
                }
                canBeSimplified = false;
                break;
            }
        }
        System.out.println("化简后的矩阵\n" + new GF2Matrix(simplified));
        if (canBeSimplified) {
            Gs = new GF2Matrix(simplified);
        }
    }

    public List<ErrorPattern> getTabData() {
        return tabData;
    }

    public int getN() {
        return n;
    }

    public int getK() {
        return k;
    }

    /**
     * @param G 生成矩阵
     * @param H 校验矩阵
     */
    public Group(int[][] G, int[][] H) {
        this.G = new GF2Matrix(G);
        this.n = this.G.getColumn();
        this.k = this.G.getRow();
        this.H = new GF2Matrix(H);
        generateErrorPattern();
    }

    /**
     * 生成差错图案表
     */
    private void generateErrorPattern() {
        errorPattern = new HashMap<>(16);
        tabData = new ArrayList<>();
        GF2Matrix H = this.Gs == null ? this.H : this.Hs;
        GF2Matrix s, e;
        for (int i = 0; i <= n; i++) {
            int[] a = new int[n];
            if (i != 0) {
                a[i - 1] = 1;
            }
            e = new GF2Matrix(a);
            s = GF2Matrix.multiplyMod2(e, GF2Matrix.transpose(H));
            errorPattern.put(s, e);
            tabData.add(new ErrorPattern(s, e));
        }
    }

    public Map<GF2Matrix, GF2Matrix> getErrorPattern() {
        return errorPattern;
    }

    /**
     * 判断是否为码字，判断码字的伴随式是否全0
     *
     * @param t 输出值
     * @return 是否为码字
     */
    private boolean isCode(int[] t) {
        GF2Matrix y = new GF2Matrix(t);
        // 求伴随式
        GF2Matrix s = GF2Matrix.multiplyMod2(y, GF2Matrix.transpose(H));
        return s.isZero();
    }

    /**
     * bean类，填充表格的数据
     */
    public class ErrorPattern {
        /**
         * 伴随式
         */
        private GF2Matrix s;

        /**
         * 差错图案
         */
        private GF2Matrix e;

        public GF2Matrix getS() {
            return s;
        }

        public GF2Matrix getE() {
            return e;
        }

        public ErrorPattern(GF2Matrix s, GF2Matrix e) {
            this.s = s;
            this.e = e;
        }
    }
}

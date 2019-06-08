package utils;

public class MatrixRank {

    /**
     * 求矩阵的最简行阶梯式
     *
     * @param m 矩阵
     * @return 最简行阶梯
     */
    public static int[][] simple(final int[][] m) {
        int column = m[0].length;
        int row = m.length;

        int[][] result = m;

        // j为第i行的第j个不为0个位置
        int j = 0;

        int i1 = 0;
        // 倍率
        int rate;

        for (int i = 0; i < row; i++) {
            while (j < column) {
                if (result[i][j] != 0) {
                    break;
                }
                j += 1;
            }
            if (j < column) {
                while (i1 < row) {
                    if (result[i1][j] != 0 && i1 != i) {
                        rate = result[i][j] / result[i1][j];
                        for (int j1 = 0; j1 < column; j1++) {
                            result[i1][j1] = (result[i][j1] - result[i1][j1] * rate + 2) % 2;
                        }
                    }
                    i1 += 1;
                }
            }
        }
        return result;
    }

    /*
    public static int Rank(double[][] Matrix, int error_, int List) {
        int n = List;
        int m = Matrix.length;
        int i = 0;
        int i1;
        int j = 0;
        int j1;
        double temp1;
        if (m > n) {
            i = m;
            m = n;
            n = i;
            i = 1;
        }
        m -= 1;
        n -= 1;
        double[][] temp = new double[m + 1][n + 1];
        if (i == 0) {
            for (i = 0; i <= m; i++) {
                for (j = 0; j <= n; j++) {
                    temp[i][j] = Matrix[i][j];
                }

            }
        } else {
            for (i = 0; i <= m; i++) {
                for (j = 0; j <= n; j++) {
                    temp[i][j] = Matrix[j][i];
                }
            }
        }
        if (m == 0) {
            i = 0;
            while (i <= n) {
                if (Matrix[0][i] != 0) {
                    return 1;
                }
                i += 1;
            }
            return 0;
        }
        double error0;
        if (error_ == -1) {
            error0 = Math.pow(0.1, 10);
        } else {
            error0 = Math.pow(0.1, error_);
        }
        i = 0;
        while (i <= m) {
            j = 0;
            while (j <= n) {
                if (temp[i][j] != 0) {
                    error0 *= temp[i][j];
                    i = m;
                    break;
                }
                j += 1;
            }
            i += 1;
        }
        double error1;
        for (i = 0; i <= m; i++) {
            j = 0;
            while (j <= n) {
                if (temp[i][j] != 0) {
                    break;
                }
                j += 1;
            }
            if (j <= n) {
                i1 = 0;
                while (i1 <= m) {
                    if (temp[i1][j] != 0 && i1 != i) {
                        temp1 = temp[i][j] / temp[i1][j];
                        error1 = Math.abs((temp[i][j] - temp[i1][j] * temp1)) * 100;
                        error1 += error0;
                        for (j1 = 0; j1 <= n; j1++) {
                            temp[i1][j1] = temp[i][j1] - temp[i1][j1] * temp1;
                            if (Math.abs(temp[i1][j1]) < error1) {
                                temp[i1][j1] = 0;
                            }
                        }

                    }
                    i1 += 1;
                }
            }
        }

        print(temp);

        i1 = 0;
        for (i = 0; i <= m; i++) {
            for (j = 0; j <= n; j++) {
                if (temp[i][j] != 0) {
                    i1 += 1;
                    break;
                }
            }
        }
        return i1;
    }
    */
    private static void print(int[][] temp) {
        for (int[] is : temp) {
            for (int i : is) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args)//测试
    {
        double[][] TestMatrix = {
                {1, 22, 34, 22},
                {1, 11, 5, 21},
                {0, 1, 5, 11},
                {7, 2, 13, 19}};
        double[][] TestMatrix2 = {
                {1, 2, 3}, {4, 7, 8}, {2, 9, 1}
        };
        double[][] TestMatrix3 = {
                {1, 2}, {3, 3}
        };
        int[][] Test4 = {
                {1, 0, 1, 1, 0},
                {0, 1, 0, 1, 1},
                {1, 1, 0, 1, 0}
        };
        int[][] Test5 = {
                {1, 1, 0, 0, 1},
                {0, 1, 1, 0, 1},
                {0, 0, 1, 1, 1}
        };
        int[][] Test6 = {
                {1, 0, 0, 0, 1},
                {0, 1, 0, 0, 1},
                {0, 0, 1, 1, 1}
        };

        /// String str = String.valueOf(Rank(Test4, -1, 5));

        print(Test6);
        print(simple(Test6));
    }
}
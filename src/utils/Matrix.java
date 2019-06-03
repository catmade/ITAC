package utils;

/**
 * 作者：一任天然
 * 来源：CSDN
 * 原文：https://blog.csdn.net/yirentianran/article/details/2203181
 * 版权声明：本文为博主原创文章，转载请附上博文链接！
 * 功能：解N元一次方程组(矩阵)
 */
public class Matrix {

    /**
     * @param args 必须为N行N+1列
     */

    public static void main(String[] args) {

        int n = 4;
        double[][] matrix = {
                {6, 0, 7, 9, 20},
                {0, 3, 0, 10, 50},
                {0, 4, 0, 11, 31},
                {0, 8, 9, 0, 10}
        };


//   double[][] matrix = { { 6, 0, 7, 9, 20 ,99}, { 0, 3, 0, 10, 50 ,56},
//   { 0, 4, 0, 11, 31 ,4}, { 0, 8, 9, 0, 10 ,78} ,{3,6,23,76,34,0}};

        // 消元前
        System.out.println("消元前");
        printMatrix(n, matrix);
        simple(n, matrix);
        // 消元后
        System.out.println("消元后");
        printMatrix(n, matrix);

        getResult(matrix);
    }

    private static void simple(int n, double[][] matrix) {
        for (int k = 0; k < n; k++) {
            if (matrix[k][k] == 0) {
                changeRow(n, k, matrix);
            }

            for (int i = 0; i < n; i++) {
                // 记录对角线元素，作为除数
                double temp = matrix[i][k];
                for (int j = 0; j < n + 1; j++) {
                    // i<k时,i行已经计算完成
                    if (i < k) {
                        break;
                    }
                    if (temp == 0) {
                        continue;
                    }
                    if (temp != 1) {
                        matrix[i][j] /= temp;
                    }

                    if (i > k) {
                        matrix[i][j] -= matrix[k][j];
                    }
                }
            }
        }

    }

    public static double[] getResult(double[][] matrix) {
        int n = matrix.length;
        double[] result = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double temp = matrix[i][n];
            for (int j = n - 1; j >= 0; j--) {
                if (i < j && matrix[i][j] != 0) {
                    temp = temp - result[j] * matrix[i][j];
                }
            }
            temp /= matrix[i][i];
            result[i] = temp;

        }
        return result;
    }

    /**
     * 对角线上元素为０时候和下行交换
     * @param n
     * @param k
     * @param matrix
     */
    private static void changeRow(int n, int k, double[][] matrix) {
        double[] temp = new double[n + 1];
        for (int i = k; i < n; i++) {
            // 已到最后一列,不能继续交换
            if (i + 1 == n && matrix[k][k] == 0) {
                System.out.println("无解或有不唯一解！");
                System.exit(1);
            }

            for (int j = 0; j < n + 1; j++) {
                temp[j] = matrix[k][j];
                matrix[k][j] = matrix[i + 1][j];
                matrix[i + 1][j] = temp[j];
            }
            if (matrix[k][k] != 0) {
                return;
            }
        }
    }

    private static void printMatrix(int n, double[][] matrix) {
        // 打印矩阵
        System.out.println("============================================================================");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n + 1; j++) {
                if (j == n)
                    System.out.print(" = " + matrix[i][j]);
                else
                    System.out.print("(" + matrix[i][j] + ") * X" + (j + 1) + " + ");
            }
            System.out.println();
        }
        System.out.println("============================================================================");
    }
}

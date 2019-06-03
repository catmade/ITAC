import capacity.Channel;

/**
 * @author Seven
 * @description 测试类
 * @date 2019-05-27
 */
public class Test {

    public static void main(String[] args) {
        double[][] matrix1 = {
                {0.5, 0.25, 0.125, 0.125},
                {0.25, 0.5, 0.125, 0.125}
        };

        double[][] matrix2 = {
                {0.5, 0.25, 0, 0.25},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0.25, 0, 0.25, 0.5}
        };

        //double C = C(matrix2).getSpecialC();
        //System.out.println(C);
    }

    private static Channel C(double[][] matrix) {
        double[] ps = new double[matrix.length * matrix[0].length];
        int index = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                 ps[index++] = matrix[i][j];
            }
        }

        return new Channel(matrix.length, matrix[0].length, ps);
    }
}
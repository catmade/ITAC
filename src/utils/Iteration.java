package utils;

import java.io.*;

public class Iteration {
    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\Seven\\Desktop\\DesktopFiles\\IDE\\Intelij IDEA\\ITAC\\src\\capacity\\diedai.txt";

        // 从文本文件中读取数据显示到屏幕上
        String s;

        int i = 0;

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));

        while ((s = br.readLine()) != null) {
            i++;
        }

        // 显示矩阵的行数
        String[][] r1 = new String[i][];

        BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        int l = 0;
        while ((s = br2.readLine()) != null) {
            r1[l] = s.split("[\\s+]");
            l++;
        }
        double[][] r = new double[i][r1[0].length];
        System.out.println("文本文件中的信道传递矩阵为 :");
        for (int j = 0; j < i; j++) {
            for (int k = 0; k < r[0].length; k++) {
                r[j][k] = Double.parseDouble(r1[j][k]);
                System.out.print(r[j][k] + " ");
            }
            System.out.println();
        }

    }

    public static void iterate(double[][] r) {
        int time = 0;
        long timer = System.nanoTime();
        // 矩阵的行数和列数
        int row = r.length, column = r[0].length;

        // 输入符号xi的概率
        double[] p = new double[row];

        // 反条件概率，即接收为yj的情况下发送为xi的概率，q(xi|yj) = r(xi)r(yj|xi) /
        double[] q = new double[column];

        double[] a = new double[row];

        double u = 0;


        double capacity = 0;
        double iu = 0;
        double e = 1;
        double c = 0;

        do {
            time++;
            // 假定初始概率分布为均匀分布
            for (int i = 0; i < row; i++) {
                p[i] = 1.0 / row;
            }

            // 计算反条件概率
            for (int i = 0; i < column; i++) {
                for (int j = 0; j < row; j++) {
                    q[i] += p[j] * r[j][i];
                }
            }

            for (int i = 0; i < a.length; i++) {
                a[i] = 1;
            }

            // 计算中间值
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    a[i] *= Math.pow(r[i][j] / q[j], r[i][j]);
                }
            }

            for (int i = 0; i < row; i++) {
                u += p[i] * a[i];
            }
            capacity = Math.log10(u) / Math.log10(2.0);
            iu = Math.log10(max(a)) / Math.log10(2.0);
            c = iu - capacity;

            for (int i = 0; i < row; i++) {
                p[i] = p[i] * a[i] / u;
            }
            System.out.println("c=" + c + ", e=" + e);
        } while (c > e);

        System.out.println("最佳信源分布：");
        print(p);
        System.out.println("矩阵：");
        print(r);
        System.out.println("此矩阵信道容量是 :" + capacity);
        System.out.println("迭代次数 :" + time);
        System.out.println("耗时（微秒） :" + (System.nanoTime() - timer) / 1000);
    }

    /**
     * 计算数组中的最大值
     *
     * @param x 数组
     * @return 最大值
     */
    private static double max(double[] x) {
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

    private static void print(double[][] x) {
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[0].length; j++) {
                System.out.print(x[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void print(double[] p) {
        for (int i = 0; i < p.length; i++) {
            System.out.println("p[" + (i + 1) + "] = " + p[i]);
        }
    }
}
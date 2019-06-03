package utils;

import java.io.*;

class Iteration {
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
        Iteration t = new Iteration();
        t.iterate(r);
    }

    public void iterate(double[][] r) {
        // 具体的迭代方法
        double[] p = new double[r.length];

        double[] q = new double[r.length];

        double[] a = new double[r.length];

        double u = 0;

        // 信道容量

        double capacity = 0;
        double iu = 0;
        double e = 1;
        double c = 0;

        do {

            for (int i = 0; i < r.length; i++) {
                p[i] = 1.0 / r.length;
            }

            for (int i = 0; i < r.length; i++) {
                for (int j = 0; j < r[i].length; j++) {
                    q[i] += p[i] * r[j][i];
                }
            }

            for (int i = 0; i < r.length; i++) {
                for (int j = 0; j < r[i].length; j++) {
                    a[i] = 1;
                }
            }

            for (int i = 0; i < r.length; i++) {
                for (int j = 0; j < r[i].length; j++) {
                    a[i] *= Math.pow(r[i][j] / q[j], r[i][j]);
                }
            }

            for (int i = 0; i < r.length; i++) {
                u += p[i] * a[i];
            }
            capacity = Math.log10(u) / Math.log10(2.0);

            iu = Math.log10(max(a)) / Math.log10(2.0);

            c = iu - capacity;

            for (int i = 0; i < r.length; i++) {
                p[i] = p[i] * a[i] / u;
            }

        } while (c > e);

        System.out.println("此矩阵信道容量是 :" + capacity);
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
}
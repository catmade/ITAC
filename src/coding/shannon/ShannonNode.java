package coding.shannon;

import coding.Node;

/**
 * p(ai)   pa(aj)  ki  码字
 * 0.25    0.000   2   00
 * 0.25    0.250   2   01
 * 0.20    0.500   3   100
 * 0.15    0.700   3   101
 * 0.10    0.850   4   1101
 * 0.05    0.950   5   11110
 */
public class ShannonNode extends Node {
    /**
     * 第i个码字累加概率
     */
    public double aj;

    public ShannonNode(double p) {
        super(p, 0);
    }

    public double getAj() {
        return aj;
    }

    public void setAj(double aj) {
        this.aj = aj;
    }
}

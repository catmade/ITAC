package coding.huffman;

import java.util.List;

public class HuffmanTreeNode implements Comparable{
    private double p;
    private HuffmanTreeNode left;
    private HuffmanTreeNode right;
    private HuffmanTreeNode parent;
    public int bcode;

    public HuffmanTreeNode(double p){
        setP(p);
    }

    public HuffmanTreeNode getParent() {
        return parent;
    }

    public void setParent(HuffmanTreeNode parent) {
        this.parent = parent;
    }

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

    public HuffmanTreeNode getLeft() {
        return left;
    }

    public void setLeft(HuffmanTreeNode left) {
        this.left = left;
    }

    public HuffmanTreeNode getRight() {
        return right;
    }

    public void setRight(HuffmanTreeNode right) {
        this.right = right;
    }

    @Override
    public int compareTo(Object o) {
        HuffmanTreeNode that= (HuffmanTreeNode) o;
        return Double.compare(this.p,that.p);
    }
}

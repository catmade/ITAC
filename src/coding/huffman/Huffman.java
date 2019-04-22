package coding.huffman;

import coding.Node;
import coding.huffman.HuffmanTreeNode;

import java.util.PriorityQueue;

/**
 * @author Anonymous
 * @description 赫夫曼编码
 * @date 2019-04-21
 */
public class Huffman {
    private Node[] nodes;

    private void encodingHuffman() {
        //初始化优先级队列
        PriorityQueue pq = new PriorityQueue();
        for (int i = 0; i < nodes.length; i++) {
            pq.add(new HuffmanTreeNode(nodes[i].p));
        }
        int pqlength = pq.size();
        for (int i = 0; i < pqlength - 1; i++) {
            //取出最小两个节点
            HuffmanTreeNode node1 = (HuffmanTreeNode) pq.poll();
            HuffmanTreeNode node2 = (HuffmanTreeNode) pq.poll();
            HuffmanTreeNode nodeParent = new HuffmanTreeNode(node1.getP() + node2.getP());
            //建立新节点并设置父子节点关系
            node1.setParent(nodeParent);
            node2.setParent(nodeParent);
            if (node1.getP() < node2.getP()) {
                nodeParent.setLeft(node1);
                nodeParent.setRight(node2);
                node1.bcode = 0;
                node2.bcode = 1;
            } else {
                nodeParent.setLeft(node2);
                nodeParent.setRight(node1);
                node1.bcode = 1;
                node2.bcode = 0;
            }
            pq.add(nodeParent);
        }
        HuffmanTreeNode rootNode = (HuffmanTreeNode) pq.poll();
        showTree(rootNode,"0");


    }

    public void showTree(HuffmanTreeNode node,String formerCodes) {
        if (node.getLeft() == null&&node.getRight()==null) {
            //TODO 最后一位编码加入
            System.out.println(formerCodes + " ");
        } else {
            if (node.getP()==1){
                showTree(node.getRight(),formerCodes);
                showTree(node.getLeft(),formerCodes);
            }
            else{
                showTree(node.getRight(),formerCodes + "0");
                showTree(node.getLeft(),formerCodes+"1");
            }
        }

    }

    public Huffman(double[] p) {
        nodes = new Node[p.length];
        for (int i = 0; i < p.length; i++) {
            nodes[i] = new Node(p[i], 0);
        }
        encodingHuffman();
        //showCodes();
    }

    public void showCodes() {
        for (int i = 0; i < nodes.length; i++) {
            System.out.print(nodes[i].p + " ");
        }
        System.out.println();
        for (int i = 0; i < nodes.length; i++) {
            System.out.print(nodes[i].codes + " ");
        }
    }

    public Node[] getNodes() {
        return nodes;
    }

    /**
     * 为每个节点赋值一个符号名称
     *
     * @param index  节点的下标
     * @param symbol 符号
     */
    public void setNodesSymbol(int index, String symbol) {
        this.nodes[index].symbol = symbol;
    }

    public static void main(String[] args) {
        new Huffman(new double[]{0.25,0.25,0.2,0.15,0.10,0.05});
    }
}

package coding.huffman;

import coding.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author Anonymous
 * @description 赫夫曼编码
 * @date 2019-04-21
 */
public class Huffman {
    private Node[] nodes;

    private List<Integer> help;

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
        showTree(rootNode,"");


    }

    private void showTree(HuffmanTreeNode node, String formerCodes) {
        if (node.getLeft() == null&&node.getRight()==null) {
            for(int i = 0; i < nodes.length; i++){
                if(nodes[i].getP() == node.getP()){
                    if(help.indexOf(i) == -1){
                        help.add(i);
                        nodes[i].setCodes(formerCodes);
                        break;
                    }
                }
            }
        } else {
            showTree(node.getRight(),formerCodes + "0");
            showTree(node.getLeft(),formerCodes+"1");
        }

    }

    public Huffman(double[] p) {
        nodes = new Node[p.length];
        help = new ArrayList<>();
        for (int i = 0; i < p.length; i++) {
            nodes[i] = new Node(p[i], 0);
        }
        encodingHuffman();
        for (Node node : nodes) {
            node.ki = node.codes.length();
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
}

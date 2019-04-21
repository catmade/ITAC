package coding.huffman;

import coding.Node;

/**
 * @author Anonymous
 * @description 赫夫曼编码
 * @date 2019-04-21
 */
public class Huffman {
    private Node[] nodes;
    private void encodingHuffman() {



    }

    public Huffman(double[] p) {
        nodes = new Node[p.length];
        for (int i = 0; i < p.length; i++) {
            nodes[i] = new Node(p[i], 0);
        }
        encodingHuffman();
        showCodes();
    }

    public void showCodes()
    {
        for (int i=0;i<nodes.length;i++)
        {
            System.out.print(nodes[i].p + " ");
        }
        System.out.println();
        for (int i=0;i<nodes.length;i++)
        {
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
}

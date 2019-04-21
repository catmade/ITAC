package coding.shannon;



public class Shannon {

    private ShannonNode[] nodes;

    //将double转换为二进制，返回String bin
    private String doubleToBinaryString(double d,int length) {
        String bin="";
        for (int i=0;i<length;i++) {
            bin += (int)Math.floor(d*2);
            d=d*2-Math.floor(d*2);
        }
        return bin;
    }
    //计算香农编码
    private void encodingShannon() {
        //初始化第一组数据
        nodes[0].aj=0;
        nodes[0].ki=2;
        nodes[0].codes=doubleToBinaryString(nodes[0].aj,nodes[0].ki);
        //根据香农编码计算码长ki以及编码codes
        for(int i=1;i<nodes.length;i++){
            nodes[i].aj=nodes[i-1].aj+nodes[i-1].p;
            nodes[i].ki=(int)Math.ceil(-(Math.log(nodes[i].p)/Math.log(2)));
            nodes[i].codes=doubleToBinaryString(nodes[i].aj,nodes[i].ki);
        }
    }
    //构造方法,接收用户输入double[]
    public Shannon(double[] p){
        //初始化结点
        nodes=new ShannonNode[p.length];
        for (int i=0;i<nodes.length;i++){
            nodes[i] = new ShannonNode( p[i]);
        }
        //编码,并将结果保存在nodes[]里
        encodingShannon();

        //showCodes();
    }

    //将编码以及码位打印到屏幕上
    public void showCodes()
    {
        for (int i=0;i<nodes.length;i++)
        {
            System.out.print(nodes[i].ki + " ");
        }
        System.out.println();
        for (int i=0;i<nodes.length;i++)
        {
            System.out.print(nodes[i].codes + " ");
        }
    }

    public ShannonNode[] getNodes() {
        return nodes;
    }

    /**
     * 为每个节点赋值一个符号名称
     * @param index 节点的下标
     * @param symbol 符号
     */
    public void setNodesSymbol(int index, String symbol) {
        this.nodes[index].symbol = symbol;
    }
}

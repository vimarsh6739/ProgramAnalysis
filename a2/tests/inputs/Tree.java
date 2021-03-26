class Tree {
    public static void main(String[] args) {
        
        Node c1;
        Node c2;
        Node a1;
        Node a2;
        Node t1; 
        Node t2; 
        Node t3;
        boolean ret;
        int v;
        
        c1 = new Operand();
        c2 = new Operand();
        a1 = new AddOperator();
        a2 = new AddOperator();
        v=100;
        ret = c1.setVal(v);
        v=200;
        ret = c2.setVal(v);
        
        ret = a1.addLeftNode(c2);
        ret = a2.addLeftNode(c1);

        ret = a1.addRightNode(a2);
        ret = a2.addRightNode(c1);
        v = a1.getVal();
        t1 = a1.lt;
        t2 = a2.lt;
        t3 = a2.rt;

        /* t1 alias? c1 */
        /* t1 alias? c2 */
        /* t1 alias? t2 */
        /* t2 alias? t3 */
        /* t1 alias? t3 */
        System.out.println(v);
    }
}

class Node {
    Node lt;
    Node rt;

    public boolean addLeftNode(Node lt){
        Node t; boolean b;
        b = true;
        t = this;
        /* lt alias? t */
        t.lt = lt;
        return b;
    }

    public boolean addRightNode(Node rt){
        Node t; 
        boolean b;
        b = true;
        t = this;
        /* rt alias? t */
        t.rt = rt;
        return b;
    }

    public int getVal() {
        int x;
        x=0;
        return x;
    }

    public boolean setVal(int v){
        boolean b;
        b=true;
        return b;
    }
}

class AddOperator extends Node{
    
    public int getVal(){
        int a;
        int b; 
        int s;
        a = lt.getVal();
        b = rt.getVal();
        s = a+b;
        return s;
    }
}

class Operand extends Node{
    int val;
    
    public boolean setVal(int v){
        Operand t; 
        boolean b;
        b = true;
        t = this;
        t.val = v;
        return b;
    }

    public int getVal(){
        return val;
    }
}

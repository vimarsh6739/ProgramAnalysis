class LinkedList {
    public static void main(String[] args) {
        List head ; List l1; List l2; List l3;
        List t1; List t2; List t3;
	    Element el01 ;
	    Element el02 ;
	    Element el03 ;
        boolean ret;
        int v;
        head = new List();
        el01 = new Element();
        el02 = new Element();
        el03 = new Element();
        
        v=100;
        ret = el01.init(v);
        v=200;
        ret = el02.init(v);
        v=300;
        ret = el03.init(v);
        
        ret = head.init(el01);
        
        l1 = head.push_back(el02);
        l2 = head.push_back(el03);
        l3 = head.push_back(el03);
        t1 = head.next;
        t2 = l1.next;
        t3 = l2.next;
        /* t1 alias? l1 */
        /* t2 alias? l2 */
        /* t3 alias? l3 */
        System.out.println(v);
    }
}

class Element{
    int val;

    public boolean init(int v){
        boolean r;
        r=true;
        val=v;
        return r;
    }

    public int getVal(){
        return val;
    }

    public boolean equal(Element other){
        Element e;
        boolean ret;
        boolean b1;
        boolean b2;
        boolean f;
        int val1m;int val1;
        int val2;int val2m;
        int one;
        e=this;
        one=1;
        ret = true;
        val1=e.val;
        val1m = val1 - one;
        val2 = other.getVal();
        val2m = val2 - one;
        b1 = val1m < val2;
        b2 = val2m < val1;
        f = b1 && b2;
        ret = f;
        return ret;
    }
}

class List{
    Element elem;
    List next;
    boolean end;
    
    public boolean init(Element e1){
        boolean ret;
        end=true;
        elem = e1; 
        /* elem alias? e1 */
        ret=true;
        return ret;
    }

    public List push_back(Element e){
        boolean ret;
        List ptr;
        List tmp;
        List aux;
        boolean isNotEnd;
        boolean FALSE;
        boolean isEnd;
        FALSE=false;
        ptr = this;
        ret=true;
        isEnd = ptr.end;
        isNotEnd = !isEnd;

        while(isNotEnd){
            tmp = ptr.next;
            ptr = tmp;
            isEnd=ptr.end;
            isNotEnd = !isEnd;
        }

        ptr.end=FALSE;
        aux = new List();
        ret = aux.init(e);

        /* ptr alias? aux */
        /* tmp alias? ptr */
        /* tmp alias? aux */
        ptr.next = aux;
        tmp = ptr.next;
        return aux;
    }
}
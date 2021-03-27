class FieldSensitivity1 {
    public static void main(String[] args) {
        A a; 
        int[] x;
        int[] y;
        int v;
        v=100;
        a = new A();
        x = new int[v];
        x = a.setF(x);
        y = a.getF();
        /* x alias? y*/
        System.out.println(v);
    }
}

class A {

	int[] f;
    
    public int[] setF(int[] x){
        int len;int zero;
        len = x.length;
        zero=0;
        f = new int[len];
        /* x alias? f */
        f[zero]=len;
        return f;
    }
	public int[] getF() { return f;}
	public int[] id(int[] x) { return x;}
}

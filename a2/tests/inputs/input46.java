class input3{
    public static void main(String[] args){
        
        Me a1;
        Me a2;
        Me a11;
        Me a22;
        int k;
        
        a1 = new Me(); 
        a2 = new Me(); 

      	k = a1.foo(a2);
      	a11 = a1.x;
      	a22 = a2.x;
		/*a11 alias? a22*/
		
		a1 = a2;
		/*a1 alias? a2*/
        {}
    }    
}

class Me{
    Me x;
    int y;
    public int foo(Me x1){
		x = new Me();
		x1.x = x;
		return y;
	}
}
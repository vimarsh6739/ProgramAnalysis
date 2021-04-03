class input1{
    public static void main(String[] args){
    
        input1 a;
        input1 b;
        input1 c;
        input1 d;

        a = new input1();
        b = new input1();

        a = b;
        /*a alias? b*/
        c = a;
        /*c alias? b*/
        d = b;
        /*c alias? d*/
        d = new input1();
        /*c alias? d*/
        {}
    }    
}


class input4{
    public static void main(String[] args){
        
        boolean condition;
        input1 a1;
        input1 a2;
        input1 a3;
        input1 a4;

        a1 = new input1(); // R1
        a2 = new input1(); // R2

        condition = true;
        while(condition){
            a3 = a1;
            a1 = a2;
            a2 = a3;
            condition = false;
        }
        /*a1 alias? a2*/
        /*a3 alias? a4*/
		/*a1 alias? a3*/
        /*a1 alias? a4*/
        {}
    }    
}
class input1{}
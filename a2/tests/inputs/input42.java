class input2{
    public static void main(String[] args){
        
        Me a1;
        Me a2;
        Me a11;
        Me a22;
        Me p;
        Me q;
        boolean condition;

        a1 = new Me();
        a2 = new Me();
        /*a1 alias? a2*/

        condition = true;
        if(condition){
            a11 = new Me();
            a1.x = a11;
        }
        else{
            a22 = new Me();
            a2.x = a22;
        }
        p = a1.x;
        q = a2.x;
        /*a11 alias? a22*/
        /*p alias? q*/
        p = q;
        /*p alias? q*/
        {}
    }    
}

class Me{
    Me x;
}

class input5{
    public static void main(String[] args){

        Me a1;
        Me a2;
        Me a11;
        Me a12;
        Me p;
        Me q;
        Me r;
        Me s;
        int x;
        int y;
        boolean condition;

        a1 = new Me();
        a2 = new Me();

        x = 7;
        y = 3;
        condition = x<y;

        if(condition){
            a11 = new Me();
            a12 = new Me();
            a1.x = a11;
            a1.y = a12;
        }
        else{
            a11 = new Me();
            a12 = new Me();
            a2.x = a11;
            a2.y = a12;
        }

        while(condition){
            while(condition){
                condition = false;
                a1.x = a11;
                a1.y = a12;
            }
        }

        p = a2.x;
        q = a2.y;
        r = a1.x;
        s = a1.y;
        /*a11 alias? a12*/
        /*a1 alias? p*/
        /*a1 alias? q*/
        /*r alias? a11*/
        {}
    }    
}

class Me{
    Me x;
    Me y;
}
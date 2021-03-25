
class Inheritance {
    public static void main(String[] args) {
        
    }
}

class A{
    public boolean foo(){
        boolean b1;
        b1=true;
        return b1;
    }
}

class B extends A{
    public boolean b(){
        boolean b1;
        b1=true;
        return b1;
    }
}


class C extends B{
    public boolean foo(){
        boolean b1;
        b1=true;
        return b1;
    }
}
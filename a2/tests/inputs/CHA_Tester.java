class CHA_Tester {
    public static void main(String[] args) {
        int x;
        x = 5;
        System.out.println(x);
    }    
}

class B extends A{
    A f0;
}

class H extends D{
    H f0;
}

class D extends B{
    D f0;
    D f1;
}

class A{
    int[] arr;
    D f0;
}


class C extends B{
    C f1;
    B f2;
}

class E extends D{
    A f0;
    B f1;
    D f2;
    E f4;
}

class F{
    B f0;
}

class G extends F{
    G f1;
    F f0;
}

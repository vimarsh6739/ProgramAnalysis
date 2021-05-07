class input4
{
   public static void main (String [] args)
  {
       B a1;
       A a2;
       A a3;
       A a4;
       int y;
       int z;
       a1 = new B();//R1
       y = a1.foo(a1); //R1.f0 = R2, R1.f1 = R2       
       a2 = a1.f0;  //R2    
       a3 = a1.getf1();//R2
       
       z = a2.foo(a3);//R2.f0 = R3, R2.f0 = R3          
       a4 = a2.f0;//R3 
       /*a2 alias? a1*/
       /*a1 alias? a3*/
       /*a2 alias? a3*/
      /*a2 alias? a4*/  
      /*a1 alias? a4*/  
      /*a3 alias? a4*/
      {}     
  }
}

class A
{
  A f0;
  int q;
  public int foo (A x)
 {
    f0 = new A();//R3
    x.f0 = f0;
    q = 1;
    return q;
 }
}
class B extends A
{
  B f1;
  int p;
  public int foo (A x)
 {
    f1 = new B();//R2
    x.f0 = f1;
    p = 1;
    return p;
 }
  public A getf1()
 {
    return f1;
 }
}


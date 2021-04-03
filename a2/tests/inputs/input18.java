class input5
{
   public static void main (String [] args)
  {
       B a1;
       A a2;
       A a3;
       A a4;
       A a5;
       int y;
       boolean count;
       boolean z;
       int t;
       t = 1; 
       count = true;          
       z = true;
       a1 = new B();//R1
       y = a1.foo(a1); //R1.f1 = R2, R1.f0 = R2     
       a2 = a1.f0; //R2     
       a3 = a1.getf1();//R2       
       t = a2.foo(a3); //R2.f0 = R3, R2.f0 = R3         
       a4 = a2.f0;//R3
      
       while (count)
       {
          if(z)
           {
             a4 = new A();//R4
             z = false;
           }   
          else
           {
             a4 = a3;//{R2,R3}
             z = true;
           }
         count = false;
       } 
        
       a5 = a4;//{R4,R2,R3}
       /*a2 alias? a4*/    
       /*a2 alias? a3*/
       /*a4 alias? a3*/ 
       /*a5 alias? a4*/  
       System.out.println(count);
  }
}

class A
{
  A f0;
  int q;
  public int foo (A x)
 {
    f0 = new A();//R3
    x.f0 = f0;//
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


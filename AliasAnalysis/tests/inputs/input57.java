class input1
{
  public static void main (String [] args)
   {
      X m;
      X n;
      X p;
      X q;
      m = new X(); //R1
      n = new X(); //R2
      p = m; //R1      
      p = n;//R2, R1   
      q = p; //R2, R1     
      n = m;// R1
      /*m alias? n*/
      /*p alias? m*/
      /*p alias? n*/
      /*q alias? p*/
        
      {}
    }
}

class X
{
  int r;
  public int X() 
   {
     r = 1;
     return r;
   } 
}


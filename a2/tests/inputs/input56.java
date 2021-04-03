class input2{
	public static void main(String[] args){
		A a1;
		A a2;
		A a3;
		boolean flag;
		a1 = new A();
		a2 = new A();
		a3 = new A();
		flag=true;
		if(flag){
			a1 = a2;
		}else{
			a1 = a3;
		}
		/* a1 alias? a3 */
		/* a1 alias? a2 */
		/* a2 alias? a3 */
		flag=false;
	}
}

class A{
	int z;
}

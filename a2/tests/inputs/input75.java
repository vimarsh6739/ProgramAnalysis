class input75{
	public static void main(String[] args){
		Parent1 p;
		Parent1 q;
		Child1 r;
		Child1 s;
		
		p = new Parent1();
		r = new Child1();

		/* p alias? q */
		/* p alias? r */
		/* p alias? s */
		/* q alias? r */
		/* q alias? s */
		/* r alias? s */
		q = new Parent1();
		q.f = p;
		r.f = q;
		p = r.f;
		/* p alias? q */
		/* p alias? r */
		/* p alias? s */
		/* q alias? r */
		/* q alias? s */
		/* r alias? s */
		p.f = r;
		s = new Child1();
		r.q = s;
		s.q = r;
		r = r.q;
		/* p alias? q */
		/* p alias? r */
		/* p alias? s */
		/* q alias? r */
		/* q alias? s */
		/* r alias? s */
		p = p.f;
	}
}

class Child1 extends Parent1{
	Child1 q;
} 

class Parent1{
	Parent1 f;
}
package com.ron.test;

interface Face{
	public void printS();
}
abstract class A {
	private int k = 2;
	public int i = 1;
	public void printI() {
		System.out.println("i=" + i + " k=" + k);
	}
}
class B extends A implements Face{
	int s = 3;
//	B(){
//	   i = 2;	
//	}
	int i = 2;
	public void printS() {
		System.out.println("s=" + s );
	}
	
}
class C extends A{
	
}
public class Test{
	public static void main(String[] args) {
		A b = new B();
		C c = new C();
		b.i = 33;
		((Face)b).printS();
		b.printI();
		c.printI();
//		System.out.println(b.k);
	}
}
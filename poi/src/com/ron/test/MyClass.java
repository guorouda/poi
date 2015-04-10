package com.ron.test;

interface Faces{
	public void tells();
}

class Base{
	private  int i;
	public int j;

	public void tellMe(){
		i++;
		System.out.println("i: " + i);
	}
}

class Upper extends Base implements Faces{
	public int j = 100;

	public void tells(){
		System.out.println(j);
	}
}

public class MyClass{

	public static void main(String[] args){
		Base b = new Upper();
		b.tellMe();
		((Faces)b).tells();
//		b.tells();
	}
}
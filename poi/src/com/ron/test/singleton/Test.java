package com.ron.test.singleton;

class  LazySingleton {
    private   static  LazySingleton m_instance = null ;
    
    private String banner;
    private LazySingleton(){};
    synchronized public static LazySingleton getInstance(){
       if(m_instance==null)
           m_instance=new LazySingleton();
       return m_instance;
   }

   public void setBanner(String s){
	   this.banner = s;
   }
   public void println(){
	   System.out.println("Singleton prints itself!" + banner);
   }
}

public class Test{
	public static void main(String[] args){
		LazySingleton ls = LazySingleton.getInstance();
		ls.setBanner("I love u!");
		ls.println();
		LazySingleton.getInstance().println();

	}
}

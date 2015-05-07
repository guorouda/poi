 package com.ron.test;

import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Comparator;


class Info{
	String text;
	int waittime;

	public Info(String text, int waittime){
		this.text = text;
		this.waittime = waittime;
	}

	public void setText(String text){
		this.text = text;
	}
	public String getText(){
		return text;
	}
	public void setWaitim(int waittime){
		this.waittime = waittime;
	}
	public int getWaittime(){
		return waittime;
	}
}

class PlayThread implements Runnable{
	
		String name;
		int priority;
		
		List<Info> infoList = null;

		public PlayThread(String name, int priority, List infoList){
			this.name = name;
			this.priority = priority;
			this.infoList = infoList;
			
			Collections.sort (Waits.getPlayThreadList(), new PlayThreadComparator()); 
		}

		public int getPriority(){
			return priority;
		}
		
		public  String getName(){
			return name;
		}

		@Override
		public String toString(){
			return getName() + ":" + getPriority() + "";
		}
		public synchronized void go() throws InterruptedException {
			Waits.setPlayingthread(Waits.getPlayThreadList().get(0));
			Waits.getPlayThreadList().remove(0);
			notify();
		}

		public void run(){
		synchronized(this){
				if(Waits.getPlayingThread() != null){
					if(Waits.getPlayingThread().getPriority() > priority){
						try {
							Waits.getPlayThreadList().add(this);
							wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
					}
				}else{
				}
			}
			
			Waits.setPlayingthread(this);
			
			try{
				Thread.sleep(300);
			}catch(InterruptedException e){
				e.printStackTrace();
			}

			do{
				for(Info s:infoList){
					System.out.println(this.getName() + ":" + s.getText());
					try{
						for(int j = 0; j < s.getWaittime()/500 + 1; j++){
							Thread.sleep(500); 
							synchronized(this){
								if(Waits.getPlayingThread() != this){
									Waits.getPlayThreadList().add(this);
									Collections.sort (Waits.getPlayThreadList(), new PlayThreadComparator()); 
									wait();
									System.out.println(this.getName() + ":" + s.getText() + ":" + (j+1) * 500 + "ms");
								}else{
									System.out.println("..."); 
								}
							}
						}
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}	
			}while(Waits.getPlayThreadList().size() == 0);

			try{
				Waits.getPlayThreadList().get(0).go();
			}catch(InterruptedException e){
				e.printStackTrace();
			}

		}
}

class PlayThreadComparator implements Comparator<PlayThread> {

	@Override
	public int compare(PlayThread o1, PlayThread o2 ) {
		return (o2.priority - o1.priority ); 
	}
}

public class Waits {
	
	private static List<PlayThread> playThreadList = new LinkedList<PlayThread>();
	private static PlayThread playingThread = null; 
	
	public static void setPlayingthread(PlayThread playThread){
		playingThread = playThread;
	}
	
	public static PlayThread getPlayingThread(){
		return playingThread;
	}
	public static List<PlayThread> getPlayThreadList(){
		return playThreadList;
	}

	public static void main(String[] args) {

		List<Info> list = new ArrayList<Info>();
		Info info = new Info("aaaaa0", 1050);
		list.add(info);
		info = new Info("aaaaa1", 1050);
		list.add(info);
		info = new Info("aaaaa2", 2050);
		list.add(info);
		info = new Info("aaaaa3", 1050);
		list.add(info);
		info = new Info("aaaaa4", 1050);
		list.add(info);
		info = new Info("aaaaa5", 1050);
		list.add(info);
		
		PlayThread  playThread = new PlayThread("cityplaylist", 0, list);
		new Thread(playThread).start();

		try{
			Thread.sleep(7500);
		}catch(InterruptedException e){
			e.printStackTrace();
		}


		List<Info> list1 = new ArrayList<Info>();
		info = new Info("a0", 500);
		list1.add(info);
		info = new Info("a1", 500);
		list1.add(info);
		info = new Info("a2", 500);
		list1.add(info);
		info = new Info("a3", 500);
		list1.add(info);
		info = new Info("a4", 500);
		list1.add(info);
		info = new Info("a5", 500);
		list1.add(info);
		
		playThread = new PlayThread("cityinsertplaylist", 5, list1);
		new Thread(playThread).start();

		try{
			Thread.sleep(100);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		playThread = new PlayThread("Provinceinsertplaylist", 2, list1);
		new Thread(playThread).start();

	}
}

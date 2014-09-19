package com.threading;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class UsingBlockingQueue {
	public static void main(String args[]){
		BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();

		Thread producer = new Thread(new Producer(queue), "Producer");
		Thread consumer = new Thread(new Consumer(queue), "Consumer");
		
		producer.start();
		consumer.start();
	}
	
	static class Producer implements Runnable{

		private final BlockingQueue<Integer> queue;
		
		public Producer(BlockingQueue<Integer> queue){
			this.queue = queue;
		}
		
		@Override
		public void run() {
			for(int i=0;i<7;i++){
				System.out.println("Producer: "+i);
				try {
					queue.put(i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	static class Consumer implements Runnable{

		private final BlockingQueue<Integer> queue;
		
		public Consumer(BlockingQueue<Integer> queue){
			this.queue = queue;
		}
		
		@Override
		public void run() {
			while(true){
				try {
					System.out.println("Consumed: "+ queue.take());
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
}




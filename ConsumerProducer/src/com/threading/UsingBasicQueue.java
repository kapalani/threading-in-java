package com.threading;

import java.util.*;

public class UsingBasicQueue {
	public static void main(String args[]){
		Queue<Integer> queue = new LinkedList<Integer>();

		Thread producer = new Thread(new Producer(queue, 4), "Producer");
		Thread consumer = new Thread(new Consumer(queue, 4), "Consumer");
		
		producer.start();
		consumer.start();
	}
}

class Producer implements Runnable{

	private final Queue<Integer> queue;
	private final int size;
	
	public Producer(Queue<Integer> queue, int size){
		this.queue = queue;
		this.size = size;
	}
	
	@Override
	public void run() {
		for(int i=0;i<7;i++){
			System.out.println("Producer: "+i);
			try {
				produce(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void produce(int i) throws InterruptedException{
		while(queue.size()==size){
			synchronized (queue) {
				System.out.println("Queue is full "+Thread.currentThread().getName()+" is waiting, size "+queue.size());
				queue.wait();
			}
		}
		synchronized (queue) {
			queue.offer(i);
			queue.notifyAll();
		}
	}
	
}



class Consumer implements Runnable{

	private final Queue<Integer> queue;
	private final int size;
	
	public Consumer(Queue<Integer> queue, int size){
		this.queue = queue;
		this.size = size;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				System.out.println("Consumed: "+ consume());
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private int consume() throws InterruptedException{
		while(queue.isEmpty()){
			synchronized (queue) {
				System.out.println("Queue is empty "+Thread.currentThread().getName()+" is waiting, size: "+queue.size());
				queue.wait();
			}
		}
		
		synchronized (queue) {
			queue.notifyAll();
			return (int) queue.poll();
		}
	}
	
}



package com.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SampleThreadPool {
	public static void main(String[] args){
		ExecutorService pool = Executors.newFixedThreadPool(2);
		
		for(int i=0;i<10;i++)
			pool.submit(new ThreadSample(i));
		pool.shutdown();
		System.out.println("All task submitted");
		try {
			pool.awaitTermination(1, TimeUnit.HOURS);
		} catch (InterruptedException e) {
		}
		
		System.out.println("All tasks completed");
	}
}

class ThreadSample implements Runnable{

	private int i;
	
	public ThreadSample(int i) {
		this.i = i;
	}
	
	@Override
	public void run() {
		System.out.println("Starting: "+i+" by: "+Thread.currentThread());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Completd: "+ i+" by:"+Thread.currentThread());
		
	}
	
}
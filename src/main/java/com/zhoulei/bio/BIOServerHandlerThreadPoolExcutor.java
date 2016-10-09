package com.zhoulei.bio;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BIOServerHandlerThreadPoolExcutor {
	private ThreadPoolExecutor poolExecutor;
	
	public BIOServerHandlerThreadPoolExcutor(int maxPoolSize,int queueSize){
		//创建核心线程是cpu数量，的线程池
		poolExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors()
				, maxPoolSize, 120L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
		
		
	}
	public void execute(Runnable task){
		poolExecutor.execute(task);
		
	}

}

package org.joining;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.common.NamedThreadsFactory;
import org.tasks.LoopTaskI;




public class JoiningExecutorThreads {

	public static void main(String[] args) throws InterruptedException {
		String currentThreadName = Thread.currentThread().getName();
		System.out.println("[" + currentThreadName + "] Main thread starts here...");
		
		ExecutorService execService = Executors.newCachedThreadPool(new NamedThreadsFactory());
		
		CountDownLatch doneSignal = new CountDownLatch(2);
		
		execService.execute(new LoopTaskI(null));
		execService.execute(new LoopTaskI(doneSignal));
		execService.execute(new LoopTaskI(doneSignal));
		execService.execute(new LoopTaskI(null));
		
		execService.shutdown();
		
		try {
			System.out.println("Main thread before continuing");
			doneSignal.await();
			System.out.println("[" + currentThreadName + "] " + currentThreadName + 
					" GOT THE SIGNAL TO CONTINUE ...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("[" + currentThreadName + "] Main thread ends here...");
	}
}

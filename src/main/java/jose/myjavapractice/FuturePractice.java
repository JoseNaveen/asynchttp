package jose.myjavapractice;

import java.util.Deque;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutionException;
import java.util.stream.*;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

public class FuturePractice {
	
	
	public static void GNBExample() {
		for (int i=0;i<20;i++) {
			new Thread(new Runnable() {
				public void run() {
					System.out.println("Thread " + Thread.currentThread().getId() + " waiting "
							+ " for object");
					try {
					GNB gnb = GNB.getInstance();
					System.out.println("Thread " + Thread.currentThread().getId() + " Got object and sleeping");
					Thread.sleep(1000);
					}
					catch (Exception e) {
						//do nothing
						System.out.println("Exception while waiting for object or sleeping");
					}
					System.out.println("Thread " + Thread.currentThread().getId() + " releasing");
					GNB.releaseInstance();
				}
			}).start();
		}
	}
	public static void atomicLongThreadExample() {
		AtomicLong counter = new AtomicLong(0);
		for (int i=0;i<100;i++) {
			new Thread(new Runnable() {
				public void run() {
					System.out.println("Thread "+ Thread.currentThread().getId() + " got " + counter.incrementAndGet());
				}
			}).start();
		}
	}
	
	public static void futureExample() throws InterruptedException, ExecutionException, TimeoutException {
		CompletableFuture<String> completableFuture
		  = CompletableFuture.supplyAsync(() -> {
			  try {
				System.out.println("Going for sleep " + Thread.currentThread().getName());
				Thread.sleep(2000);
				System.out.println("Awake");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			  return "Hello";
		  });
		CompletableFuture<Void> future = completableFuture
		  .thenAccept(s -> System.out.println(Thread.currentThread().getName() + " Then accept() got: " + s));
		
		System.out.println("Making a get blocking call");
		completableFuture.get(3000, TimeUnit.MILLISECONDS);
		System.out.println(Thread.currentThread().getName() + " Blocking call returns");
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
		
		/*FuturePractice.futureExample();
		Map<String,String> mp = System.getenv();
		mp.keySet();
		for (String s: mp.keySet()) {
			//System.out.println("Key: " +s +  "\nValue: " + mp.get(s));
		}*/
		
		FuturePractice.GNBExample();
		
	}

}

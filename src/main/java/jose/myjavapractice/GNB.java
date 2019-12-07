package jose.myjavapractice;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GNB {
	
	private static final Lock lock = new ReentrantLock();
	private static final Condition notFull = lock.newCondition();
	private static final Condition notEmpty = lock.newCondition();
	private static int MAX_OBJECTS = 5;
	private static int count = 0;
	
	private GNB() {
		
	}
	
	public static GNB getInstance() throws InterruptedException {
		
		lock.lock();
		GNB gnb = null;
		try {
			while(GNB.count == GNB.MAX_OBJECTS) {
				System.out.println("Full. Thread " + Thread.currentThread().getId() + " sleeping now");
				GNB.notEmpty.await();
				System.out.println("Thread " + Thread.currentThread().getId() + " awoke");
			}
			GNB.count++;
			System.out.println("while creating: " + GNB.count);
			gnb = new GNB();
		} finally {
			lock.unlock();
		}
		return gnb;
		
	}
	
	public static void releaseInstance() {
		lock.lock();
		GNB.count--;
		System.out.println("after releasing: " + GNB.count);
		GNB.notEmpty.signal();
		lock.unlock();
	}

}

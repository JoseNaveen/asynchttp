package jose.myjavapractice;

import java.util.ArrayList;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.LinkedList;
import java.util.List;

public class MockServer extends Thread implements IMockServer,Observable {

	private Thread t;
	public Thread getT() {
		return t;
	}
	private List<Observer> observers = new ArrayList<Observer>();
	
	private static List<MockServer> instances = new ArrayList<MockServer>();


	private ConcurrentLinkedDeque<String> queue1 = new ConcurrentLinkedDeque<String>();
	
	public String waitForReqMsg() {
		// TODO Auto-generated method stub
		if (this.queue1.isEmpty()) {
			return null;
		}
		else {
			return this.queue1.remove();
		}
	}
	
	public MockServer() {
		//create and start the thread for mock server
		this.start();
		
	}
	
	synchronized public static MockServer getInstance() {
		if (MockServer.instances.isEmpty()) {
			MockServer ms = new MockServer();
			MockServer.instances.add(ms);
			return ms;
		}
		else {
			System.out.println("Already running...");
			return MockServer.instances.get(0);
		}
	}
	
	@Override
	public void run() {
		System.out.println("...Started..");
		int i = 0;
		while (i<50) {
			System.out.println("Putting item in queue");
			this.queue1.add("data");
			this.enqueueRequest("data" + i);
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i = i + 1;
		}
				
	}
	
	public void start() {
		if (t == null) {
			t = new Thread(this,"thread-1");
			System.out.println("Starting the thread now...");
			t.start();
		}
	}
	

	public IMockServer getMockServer() {
		// TODO Auto-generated method stub
		return this;
	}
	
	synchronized public List<String> getItems() {
		List<String> msg = new ArrayList<String>();
		while (true) {
			if (!this.queue1.isEmpty()) {
				msg.add(this.queue1.remove());
			}
			else {
				break;
			}
		}
		return msg;
	}

	public void register(Observer o) {
		// TODO Auto-generated method stub
		this.observers.add(o);
		
	}

	public void notifyObservers(String data) {
		// TODO Auto-generated method stub
		List<Observer> tmpList = new ArrayList<Observer>(this.observers);
		for (Observer o: tmpList) {
			o.update(data);
		}
		
	}
	
	public void enqueueRequest(String data) {
		this.queue1.add(data);
		this.notifyObservers(data);
	}

}

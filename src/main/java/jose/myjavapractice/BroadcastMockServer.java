package jose.myjavapractice;

import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class BroadcastMockServer extends ObservableMockServer implements IMockServer,Observer {

	static private BroadcastMockServer instance;
	private List<Observer> observers = new ArrayList<Observer>();
	private ConcurrentLinkedDeque<String> queue1 = new ConcurrentLinkedDeque<String>();
	public BroadcastMockServer(IMockServer mockServer) {
		super(mockServer);
		// TODO Auto-generated constructor stub
	}
	
	
	public void update(String data) {
		// TODO Auto-generated method stub
		this.queue1.add(data);
		System.out.println("I got data " + new Date().toString());
	}

	public String waitForReqMsg() {
		// TODO Auto-generated method stub
		List<String> li = new ArrayList<String>();
		Date start = new Date();
		while (true) {
			if (this.queue1.isEmpty()) {
				try {
					if (new Date().getTime() - start.getTime() > 10) {
						break;
					}
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				li.add(this.queue1.remove());
			}
		}
		return li.toString();
	}

	

}

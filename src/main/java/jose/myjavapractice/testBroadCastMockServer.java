package jose.myjavapractice;

public class testBroadCastMockServer {
	
	public static void main(String[] args) {
		
		IMockServer brd = new BroadcastMockServer(MockServer.getInstance());
		IMockServer brd2 = new BroadcastMockServer(MockServer.getInstance());
		MockServer ms = (MockServer) brd.getMockServer();
		MockServer ms2 = (MockServer) brd2.getMockServer();
		System.out.println(brd.waitForReqMsg());
		System.out.println(brd2.waitForReqMsg());
		try {
			ms.getT().join();
			System.out.println("Thread has died");
			ms2.getT().join();
			System.out.println("2THread has died");
		} catch (InterruptedException e1) {
			System.out.println("Interrupted while waiting for the thread");
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(brd.waitForReqMsg());
		System.out.println(brd2.waitForReqMsg());
		
	}

}

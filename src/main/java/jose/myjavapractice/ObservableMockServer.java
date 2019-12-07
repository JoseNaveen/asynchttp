package jose.myjavapractice;



public abstract class ObservableMockServer implements IMockServer,Observer {
	
	protected IMockServer mockServer;
	
	public IMockServer getMockServer() {
		return mockServer;
	}

	public ObservableMockServer(IMockServer mockServer) {
		this.mockServer = mockServer;
		((Observable) this.mockServer).register((Observer)this);
	}
	
	

}

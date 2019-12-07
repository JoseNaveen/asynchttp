package jose.myjavapractice;

public interface Observable {
	public void register(Observer o);
	public void notifyObservers(String data);
}

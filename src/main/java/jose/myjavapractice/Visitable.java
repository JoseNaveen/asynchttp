package jose.myjavapractice;

public interface Visitable {
	public void accept(Visitor v);
	public String accept(AnotherVisitor v);
}

package jose.myjavapractice;

import java.util.Deque;
import java.util.LinkedList;

public class ReferenceTest {
	
	private String name = null;
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Integer getMessageId() {
		return messageId;
	}



	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}



	private Integer messageId = 0;
	
	
	
	public static void main(String[] args) {
		Deque<ReferenceTest> testDeq = new LinkedList<ReferenceTest>();
		
		ReferenceTest r1 = new ReferenceTest();
		r1.setName("jose");
		r1.setMessageId(1);
		
		ReferenceTest r2 = new ReferenceTest();
		r2.setName("naveen");
		r2.setMessageId(2);
		
		testDeq.add(r1);
		testDeq.add(r2);
		ReferenceTest r3 = null;
		r2.setName("Naveen");
		r2.setMessageId(3);
		while (true) {
			if (testDeq.isEmpty()) {
				break;
			}
			else {
				r3 = testDeq.remove();
				System.out.println(r3.getName());
				System.out.println(r3.getMessageId());
			}
		}
		
		
	}

}

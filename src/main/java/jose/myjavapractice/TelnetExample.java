package jose.myjavapractice;

import static net.sf.expectit.matcher.Matchers.contains;
import static net.sf.expectit.matcher.Matchers.regexp;

import java.io.IOException;
import org.apache.commons.net.telnet.TelnetClient;

import net.sf.expectit.Expect;
import net.sf.expectit.ExpectBuilder;


public class TelnetExample {
    public static void main(String[] args) throws IOException {
        TelnetClient telnet = new TelnetClient();
        telnet.connect("10.15.10.46", 8055);


        StringBuilder wholeBuffer = new StringBuilder();
        Expect expect = new ExpectBuilder()
                .withOutput(telnet.getOutputStream())
                .withInputs(telnet.getInputStream())
                .withEchoOutput(wholeBuffer)
                .withEchoInput(wholeBuffer)
                .withExceptionOnFailure()
                .build();
        try {
        	expect.expect(contains("login:"));
            String response = wholeBuffer.toString();
            System.out.println(response);
            expect.sendLine("tenv");
            expect.expect(contains("password:"));
            response = wholeBuffer.toString();
            System.out.println(response);
            expect.sendLine("tenv");
            expect.expect(contains("N]: "));
            response = wholeBuffer.toString();
            System.out.println(response);
            expect.sendLine("Y");
            expect.expect(regexp("debugCli\\> "));
            response = wholeBuffer.toString();
            System.out.println(response);
            expect.sendLine("show procs");
            expect.expect(contains("debugCli\\>"));
            response = wholeBuffer.toString();
            System.out.println(response);
            expect.sendLine("set -pid 24");
            expect.expect(contains("debugCli\\>"));
            response = wholeBuffer.toString();
            System.out.println(response);
            expect.sendLine("show groups");
            expect.expect(contains("debugCli\\>"));
            expect.sendLine("exit");
            expect.expect(contains("n]: "));
            expect.sendLine("y");
            response = wholeBuffer.toString();
            System.out.println(response);
        	
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
        finally {
        	expect.close();
        	telnet.disconnect();
        }
    }
}
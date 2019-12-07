package jose.myjavapractice;

import static net.sf.expectit.filter.Filters.removeColors;
import static net.sf.expectit.filter.Filters.removeNonPrintable;
import static net.sf.expectit.matcher.Matchers.contains;
import static net.sf.expectit.matcher.Matchers.regexp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import expect4j.Expect4j;
import net.sf.expectit.Expect;
import net.sf.expectit.ExpectBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Pipe;
import java.util.Hashtable;
import java.util.Properties;


public class SshTrial {
	 public static void main(String[] args) throws JSchException, IOException, InterruptedException {
	        JSch jSch = new JSch();
	        Session session = jSch.getSession("kuberadmin", "10.15.10.46");
	       
	        Properties config = new Properties();
	        config.put("StrictHostKeyChecking", "no");
	        //jSch.addIdentity(System.getProperty("user.home") + "/.ssh/id_rsa");
	        session.setPassword("affirmed");
	        session.setConfig(config);
	        session.connect();
	        Channel channel = session.openChannel("shell");
	        channel.connect();

	        Expect expect = new ExpectBuilder()
	                .withOutput(channel.getOutputStream())
	                .withInputs(channel.getInputStream(), channel.getExtInputStream())
	                .build();
	        try {
	            expect.expect(contains("$"));
	            expect.sendLine("stty -echo");
	            expect.expect(contains("$"));
	            expect.sendLine("pwd");
	            System.out.println("pwd1:" + expect.expect(contains("\n")).getBefore());
	            expect.expect(contains("$"));
	            expect.sendLine("kc get pods -n fed-amf");
	            System.out.println(expect.expect(contains("$")).getBefore());
	            expect.sendLine("kc exec amf-cc-1-0-0-185-dbg-66b76f9866-7kgs6 -c amf-cc -n fed-amf -it -- bash -c '/usr/bin/telnet 0.0.0.0 24255'");
	            System.out.println("sent kc exec command");
	            Thread.sleep(5000);
	            System.out.println(expect.expect(contains("login")).getBefore());
	            
	            expect.sendLine("tenv");
	            System.out.println("Sent login: tenv");
	            //System.out.println("pwd1:" + expect.expect(contains("\n")).getBefore());
	            System.out.println(expect.expect(contains("password")).getBefore());
	            /*
	            expect.expect(contains("login: "));
	            
	            
	            expect.expect(contains("password: "));
	            expect.sendLine("tenv");
	            System.out.println(expect.expect(contains("\n")).getBefore());
	            expect.expect(contains("N]:"));
	            expect.sendLine("Y");
	            System.out.println(expect.expect(contains("\n")).getBefore());
	            expect.expect(contains("debugCli>"));
	            expect.sendLine("show procs");
	            System.out.println(expect.expect(contains("\n")).getBefore());
	            expect.sendLine("exit");
	            expect.expect(contains("n]:"));
	            expect.sendLine("y");
	            expect.expect(contains("$"));
	            */
	            expect.sendLine("exit");
	        } finally {
	            expect.close();
	            channel.disconnect();
	            session.disconnect();
	        }
	    }

}

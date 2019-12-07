package jose.myjavapractice;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.oro.text.regex.MalformedPatternException;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
 
import expect4j.Closure;
import expect4j.Expect4j;
import expect4j.ExpectState;
import expect4j.matches.Match;
import expect4j.matches.RegExpMatch;
 
public class SSHClient {
 
    private static final int COMMAND_EXECUTION_SUCCESS_OPCODE = -2;
    private static String ENTER_CHARACTER = "\r";
    private static final int SSH_PORT = 22;
    private List<String> lstCmds = new ArrayList<String>();
    private static String[] linuxPromptRegEx = new String[]{"\\>","$","#","login: ","password: ","N]:","debugCli> ","n]: "};
 
    private Expect4j expect = null;
    private StringBuilder buffer = new StringBuilder();
    private String userName;
    private String password;
    private String host;
 
    /**
     *
     * @param host
     * @param userName
     * @param password
     */
    public SSHClient(String host, String userName, String password) {
        this.host = host;
        this.userName = userName;
        this.password = password;
    }
    /**
     *
     * @param cmdsToExecute
     * @throws InterruptedException 
     */
    public String execute(List<String> cmdsToExecute) throws InterruptedException {
        this.lstCmds = cmdsToExecute;
 
        Closure closure = new Closure() {
            public void run(ExpectState expectState) throws Exception {
            	Thread.sleep(2000);
            	String data = expectState.getBuffer();
            	System.out.println(data);
                buffer.append(data);
              
            }
        };
        Closure loginClosure = new Closure() {
        	 public void run(ExpectState expectState) throws Exception {
        		 buffer.append(expectState.getBuffer());
                 expect.send("tenv");
                 expect.send(ENTER_CHARACTER);
             }
        };
        List<Match> lstPattern =  new ArrayList<Match>();
        for (String regexElement : linuxPromptRegEx) {
            try {
                Match mat = new RegExpMatch(regexElement, closure);
                lstPattern.add(mat);
            } catch (MalformedPatternException e) {
                e.printStackTrace();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
 
        try {
            expect = SSH();
            boolean isSuccess = true;
            for(String strCmd : lstCmds) {
            	isSuccess = isSuccess(lstPattern,strCmd);
                if (!isSuccess) {
                    isSuccess = isSuccess(lstPattern,strCmd);
                }
                //Thread.sleep(3000);
                //System.out.println(buffer.toString());
            }
            System.out.println("All commands executed");
            checkResult(expect.expect(lstPattern));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
        //Thread.sleep(25000);
        //System.out.println(expect.getLastState().getBuffer());
        return buffer.toString();
    }
    /**
     *
     * @param objPattern
     * @param strCommandPattern
     * @return
     */
    private boolean isSuccess(List<Match> objPattern,String strCommandPattern) {
        try {
        	System.out.println("Checking for pattern: "+objPattern.toString());
        	Thread.sleep(2000);
            boolean isFailed = checkResult(expect.expect(objPattern));
            if (!isFailed) {
                expect.send(strCommandPattern);
                expect.send(ENTER_CHARACTER);
                
                //System.out.println("Sent command: "+strCommandPattern);
                return true;
            }
            return false;
        } catch (MalformedPatternException ex) {
            ex.printStackTrace();
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    /**
     *
     * @param hostname
     * @param username
     * @param password
     * @param port
     * @return
     * @throws Exception
     */
    private Expect4j SSH() throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(userName, host, SSH_PORT);
        if (password != null) {
            session.setPassword(password);
        }
        Hashtable<String,String> config = new Hashtable<String,String>();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect(60000);
        ChannelShell channel = (ChannelShell) session.openChannel("shell");
        Expect4j expect = new Expect4j(channel.getInputStream(), channel.getOutputStream());
        channel.connect();
        System.out.println("Connected");
        return expect;
    }
    /**
     *
     * @param intRetVal
     * @return
     */
    private boolean checkResult(int intRetVal) {
        if (intRetVal == COMMAND_EXECUTION_SUCCESS_OPCODE) {
        	// System.out.println("checkResult failed");
            return true;
        }
        //System.out.println("checkResult success");
        return false;
    }
    /**
     *
     */
    private void closeConnection() {
        if (expect!=null) {
            expect.close();
        }
    }
    /**
     *
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        SSHClient ssh = new SSHClient("10.15.10.46", "kuberadmin", "affirmed");
        //ssh.expect = ssh.SSH();
        //ssh.expect.expect("#");
        //ssh.expect.send("kc exec amf-cc-1-0-0-185-dbg-66b76f9866-7kgs6 -c amf-cc -n fed-amf -it -- bash -c '/usr/bin/telnet 0.0.0.0 24255'");
        List<String> cmdsToExecute = new ArrayList<String>();
        //cmdsToExecute.add("/usr/bin/ls");
        //cmdsToExecute.add("/usr/bin/pwd");
        cmdsToExecute.add("kc get pods -n fed-amf");
        cmdsToExecute.add("kc exec amf-cc-1-0-0-185-dbg-66b76f9866-7kgs6 -c amf-cc -n fed-amf -it -- bash -c '/usr/bin/telnet 0.0.0.0 24255'");
        cmdsToExecute.add("tenv");
        cmdsToExecute.add("tenv");
        cmdsToExecute.add("Y");
        cmdsToExecute.add("show procs");
        cmdsToExecute.add("exit");
        cmdsToExecute.add("y");
        cmdsToExecute.add("pwd");
        
        String outputLog = ssh.execute(cmdsToExecute);
        //System.out.println(outputLog);
        ssh.closeConnection();
        
    }
}
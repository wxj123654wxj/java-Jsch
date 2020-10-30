import com.jcraft.jsch.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Shell {
    public static void shell(){
        try {
            JSch jsch=new JSch();
            String host = "192.168.123.1";
            String user = "admin";

            Session session = jsch.getSession(user, host, 22);
            String password = "admin";
            String command = "nvram show";
            session.setPassword(password);
            UserInfo userInfo = new UserInfo() {
                @Override
                public String getPassphrase() {
                    System.out.println("getPassphrase");
                    return null;
                }
                @Override
                public String getPassword() {
                    System.out.println("getPassword");
                    return null;
                }
                @Override
                public boolean promptPassword(String s) {
                    System.out.println("promptPassword:"+s);
                    return false;
                }
                @Override
                public boolean promptPassphrase(String s) {
                    System.out.println("promptPassphrase:"+s);
                    return false;
                }
                @Override
                public boolean promptYesNo(String s) {
                    System.out.println("promptYesNo:"+s);
                    return true;//notice here!
                }
                @Override
                public void showMessage(String s) {
                    System.out.println("showMessage:"+s);
                }
            };

            session.setUserInfo(userInfo);
            session.connect(30000);
            Channel channel = session.openChannel("exec");

            ((ChannelExec)channel).setCommand(command);
            channel.setInputStream(System.in);

            channel.setOutputStream(System.out);
            ((ChannelExec)channel).setErrStream(System.err);

            InputStream in=channel.getInputStream();

            channel.connect(3000);

            byte[] tmp=new byte[1024];
            while(true){
                while(in.available()>0){
                    int i=in.read(tmp, 0, 1024);
                    if(i<0)break;
                    System.out.print(new String(tmp, 0, i));
                }
                if(channel.isClosed()){
                    if(in.available()>0) continue;
                    System.out.println("exit-status: "+channel.getExitStatus());
                    break;
                }
                try{Thread.sleep(1000);}catch(Exception ee){}
            }
            channel.disconnect();
            session.disconnect();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        shell();
    }

}


import com.sun.deploy.util.StringUtils;
import com.sun.xml.internal.ws.util.StreamUtils;

import java.io.*;

public class input {

    public static void main(String[] args) throws IOException {
        ByteArrayInputStream nvram_show = new ByteArrayInputStream("nvram show".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(nvram_show);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10240);
        byteArrayOutputStream.write("nvram show".getBytes());

//        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream();
        System.out.println(System.in);
        System.out.println(byteArrayOutputStream);

    }
}

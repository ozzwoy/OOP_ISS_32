import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSide {
    public static void main(String[] args) throws IOException {

        Socket client = null;

        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try {
            client = new Socket(InetAddress.getLocalHost(), 8189);

            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());

            Cat cat = new Cat("Tom", "Black", 10);
            out.writeObject(cat);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in != null)
                in.close();
            if(out != null)
                out.close();
            if(client != null)
                client.close();
        }
    }
}

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSide {

    public static void main(String[] args) throws IOException {

        ServerSocket server = null;
        Socket client = null;

        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        Cat cat = new Cat();

        try {
            server = new ServerSocket(8189);
            client = server.accept();

            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());

            cat = (Cat) in.readObject();
            cat.printInfo();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
            if (client != null)
                client.close();
            if (server != null)
                server.close();
        }
    }
}

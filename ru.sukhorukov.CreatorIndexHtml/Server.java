import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by plasmorf on 08.10.2015.
 */
public class Server {

    public Server () {

        ServerSocket socket = null;
        ArrayList<java.net.Socket> socks = new ArrayList<>();
        ArrayList<Thread> treatds = new ArrayList<>();

        try {
            socket = new ServerSocket();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        while(true) {
            try {
                socks.add(socket.accept());
            } catch (IOException e) {
                e.printStackTrace();
            }

            treatds.add(new Thread(new Processing(socks[socks.size()-1])));

        }
    }

}

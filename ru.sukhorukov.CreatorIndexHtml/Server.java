import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by plasmorf on 08.10.2015.
 */
public class Server implements Runnable {
    int port;
    ArrayList<java.net.Socket> socks = new ArrayList<>();
    ArrayList<Thread> treatds = new ArrayList<>();

    Server(int port){
        this.port = port;
    };

    public void run () {

        ServerSocket socket = null;
        socks = new ArrayList<>();
        treatds = new ArrayList<>();

        try {
            socket = new ServerSocket(port);

        } catch (IOException e1) {
            e1.printStackTrace();
        }

        while(true) {
            try {
                socks.add(socket.accept());
            } catch (IOException e) {
                e.printStackTrace();
            }

            treatds.add(new Thread(new Processing(socks.get(socks.size() - 1))));

        }
    }

    int getThredCount(){
        return treatds.size();
    }


}

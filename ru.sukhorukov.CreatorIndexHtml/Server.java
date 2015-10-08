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
        System.out.println("Created server. port"+port);
    };

    public void run () {

        System.out.println("Server running");

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

            System.out.println("Start new thread");
            treatds.add(new Thread(new Processing(socks.get(socks.size() - 1))));
            treatds.get(treatds.size() - 1).start();

        }
    }

    int getThredCount(){
        return treatds.size();
    }


}

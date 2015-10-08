import java.net.ServerSocket;

/**
 * Created by plasmorf on 08.10.2015.
 */
public class Processing implements Runnable {
    ServerSocket socket;

    Processing(ServerSocket psocket){
        socket = psocket;
    }

    @Override
    public void run() {

    }


}

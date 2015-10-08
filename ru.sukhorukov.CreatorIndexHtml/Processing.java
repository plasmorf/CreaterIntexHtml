import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by plasmorf on 08.10.2015.
 */
public class Processing implements Runnable {
    Socket socket;

    Processing(Socket psocket){
        socket = psocket;

    }

    @Override
    public void run() {
        InputStreamReader in = null;
        OutputStreamWriter out = null;
        StringBuilder queryStr = new StringBuilder();
        char c;

        try {
            in = new InputStreamReader(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            out = new OutputStreamWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while((c = (char) in.read())!=-1 && c != 10 && c!=13){

                queryStr.append((char)c);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //получаем команду и ее аргументы

        String cmd = queryStr.toString().split(" ")[0].trim().toUpperCase();

        if (cmd.compareTo("GET") == 1)  {
            getResource(out);
        }
        else if (cmd.compareTo("HEAD") == 1){
            getHead(out);
        }


    }

    private void getResource(OutputStreamWriter out){

    };

    private void getHead(OutputStreamWriter out){

    };

}

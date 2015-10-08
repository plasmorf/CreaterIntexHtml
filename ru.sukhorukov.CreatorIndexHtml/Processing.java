import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;

/**
 * Created by plasmorf on 08.10.2015.
 */
public class Processing implements Runnable {
    Socket socket;

    Processing(Socket psocket){
        socket = psocket;
        System.out.println("Create processing");
    }

    @Override
    public void run() {
        System.out.println("Run processing");

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

        System.out.println("Reciving query: "+ queryStr.toString());
        //получаем команду и ее аргументы

        String[] arg = queryStr.toString().split(" ");
        String cmd = arg[0].trim().toUpperCase();
        String path = null;
        try {
            path = URLDecoder.decode(arg[1], "windows-1251");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        switch (cmd){
            case "GET" : {getResource(path, out, QueryType.GET); break;}
            case "HEAD": {getResource(path, out, QueryType.HEAD); break;}
            default:
                try {
                    out.write("501 Not implemented");
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        try {
            out.flush();

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getResource(String path, OutputStreamWriter out, QueryType queryType) {
        System.out.println("GET recieved");
        File file = new File(path);

        if (!file.exists()){
            try {
                out.write("404 Not Found");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (file.isDirectory()) {
            FileDir fd = new FileDir(path, out, queryType);

            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ;
        }
        else{
            InputStreamReader isr = null;
            char buf[] = new char[4096];
            int len;

            try {
                isr = new InputStreamReader(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            try {
                out.write("HTTP/1.0 200 OK\r\n");
                //минимально необходимые заголовки, тип и длина
                String contentType = new MimetypesFileTypeMap().getContentType(file);
                out.write("Content-Type: " + contentType + "\r\n");
                out.write("Content-Length: " + file.length() + "\r\n");
                out.write("Connection: close \r\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                while((len = isr.read(buf)) >= 0)
                    out.write(buf);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //file.
        }

    };


}

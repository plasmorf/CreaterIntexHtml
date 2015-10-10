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
    String startPath;

    Processing(Socket psocket, String startPath){
        socket = psocket;
        this.startPath = startPath;
        System.out.println("Create processing");
    }

    @Override
    public void run() {
        System.out.println("Run processing");

        InputStreamReader in = null;
        OutputStream out = null;
        StringBuilder queryStr = new StringBuilder();
        int c;

        try {
            in = new InputStreamReader(socket.getInputStream());

            out = socket.getOutputStream();

             while ((c = in.read()) != -1 && c != 10 && c != 13) {

                queryStr.append((char) c);

             }

             System.out.println("Reciving query: " + queryStr.toString());
             //получаем команду и ее аргументы

             String[] arg = queryStr.toString().split(" ");
             String cmd = arg[0].trim().toUpperCase();
             String path = null;
             try {
                 path = URLDecoder.decode(arg[1], "utf-8");
                 if (startPath.contains(path)){
                     path = startPath;
                 }
                 else {
                     path = startPath + path ;
                 }
             } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
             }

             switch (cmd) {
                    case "GET": {
                        getResource(path, out, QueryType.GET);
                        break;
                    }
                    case "HEAD": {
                        getResource(path, out, QueryType.HEAD);
                        break;
                    }
                    default:
                        try {
                            out.write("501 Not implemented".getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }

                out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendFile(File file, OutputStream out)throws IOException{
        BufferedInputStream isr = new BufferedInputStream(new FileInputStream(file));
        int data;

        out.write("HTTP/1.0 200 OK\r\n".getBytes());
        String contentType = new MimetypesFileTypeMap().getContentType(file);

        System.out.println("ContentType = " + contentType);

        out.write(("Content-Type: " + contentType + "\r\n").getBytes());
        out.write(("Content-Length: " + file.length() + "\r\n").getBytes());
        out.write("Connection: close \r\n\r\n".getBytes());

        while((data = isr.read()) >= 0)
            out.write(data);

        isr.close();

    }

    private void getResource(String path, OutputStream out, QueryType queryType) throws IOException{
        System.out.println("GET recieved: "+ path);
        File file = new File(path);

        if (!file.exists()){

            out.write("HTTP/1.0 200 OK\r\n".getBytes());
            out.write("404 Not Found\r\n".getBytes());
        }
        else if (file.isDirectory()) {
            File indexFile = new File(path+"index.html");

            if (indexFile.exists()){
//                sendIndex(indexFile, out);
                sendFile(indexFile, out);
            }
            else {
                FileDir fd = new FileDir(path, new OutputStreamWriter(out), queryType);
            }

        }
        else{
            sendFile(file, out);
        }

    };


}

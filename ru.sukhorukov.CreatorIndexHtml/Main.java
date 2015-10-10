import java.io.*;

/**
 * Created by Администратор on 03.10.2015.
 */
public class Main {
    public static void main(String[] args) {

        Server s = new Server(8888, args[0]);
        s.run();

/*
        File f = new File(args[0] + "index.html");
        OutputStream s = null;
        try {
            s = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        };

        FileDir fd = new FileDir(args[0], s);

        try {
            s.flush();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        };
*/

    }
}

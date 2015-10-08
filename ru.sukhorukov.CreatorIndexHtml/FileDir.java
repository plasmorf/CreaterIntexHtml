import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by Администратор on 03.10.2015.
 */
public class FileDir {
    private String name;

    public FileDir(String path, OutputStreamWriter os, QueryType queryType){
        ArrayList<FileProps> files;
        ArrayList<String> text = new ArrayList<>();
        String[] pname = path.split(File.pathSeparator);

        name = pname[pname.length-1]; // substring(path.lastIndexOf(File.pathSeparator), path.length());

        // Заполняем массив и сортируем его
        files = sort(fillList(path));

        text.add("HTTP/1.0 200 OK\r\n");
        //минимально необходимые заголовки, тип и длина
        text.add("Content-Type: text/html \r\n"); //text/html
        //пустая строка отделяет заголовки от тела
        text.add("\r\n");


        text.add(HtmlWrap.getHead(path, "html/text", 0));

        if (queryType == QueryType.GET) {
            // Тело
            text.add("  <body>\n");
            text.add("    <table width='100%'>\n");
            text.add("      <tr><td><a href='../'>..</a></tr>\n");

            files.forEach(e -> {
                try {
                    if (e.isDir == 1) {
                        text.add("      <tr><td><a href=\"" + URLEncoder.encode(e.name, "windows-1251") + "/\">" + e.name + "</a></td></tr>\n");
                    } else {
                        text.add("      <tr><td><a href=\"" + URLEncoder.encode(e.name, "windows-1251") + "\">" + e.name + "</a></td>" +
                                "<td>" + e.size + "</td>" +
                                "<td>" + e.modifyDate + "</td></tr>\n");
                    }
                    ;
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
            });


            text.add("    </table>\n");
            text.add("  </body>\n");
            }

        text.add(HtmlWrap.getTail());

        text.forEach(e -> {
            try {
                os.write(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        try {
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        };

    };

    private ArrayList<FileProps> fillList(String path){
        File []fList;
        File Fl = new File(path);
        ArrayList<FileProps> files;
        FileProps f;

        files = new ArrayList<FileProps>();

        fList = Fl.listFiles();

        for(int i=0; i < fList.length; i++)
        {
            f = new FileProps();
            f.name = fList[i].getName();
            f.size = fList[i].length();
            f.modifyDate = new Date(fList[i].lastModified());
            f.isDir = fList[i].isDirectory()?1:0;
            files.add(f);
        }

        return files;
    };

    private ArrayList<FileProps> sort(ArrayList<FileProps> p){
      p.sort((a,b)->{
        if (a.isDir == b.isDir) {
            return a.name.compareToIgnoreCase(b.name);
            }
        else {
            return 1-a.isDir*2;
            }
        });

      return p;
    };


}

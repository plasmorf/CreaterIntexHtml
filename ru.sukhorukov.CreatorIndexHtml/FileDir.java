import java.io.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by Администратор on 03.10.2015.
 */
public class FileDir {
    private String name;

    public FileDir(String path, OutputStream os){
        ArrayList<FileProps> files;
        ArrayList<String> text = new ArrayList<>();
        String[] pname = path.split(File.pathSeparator);

        name = pname[pname.length-1]; // substring(path.lastIndexOf(File.pathSeparator), path.length());

        // Заполняем массив и сортируем его
        files = sort(fillList(path));

        // Заголовок
        text.add("<!DOCTYPE HTML>");
        text.add("<head>");
        text.add("<meta http-equiv=\\\"content-type\\\" content=\\\"text/html; charset=utf-8\\\" />");
        text.add("<title>Список файлов в папке " + path + "</title>");
        text.add("</head>");

        // Тело
        text.add("<body>");
        text.add("<table width=\"100%\">");
        text.add("<tr><td><a href=\"../index.html\">..</a></tr>");

        files.forEach(e -> {
            if (e.isDir == 1) {
                text.add("<tr><td><a href=\"" + e.name + "/index.html\">" + e.name + "</a></td></tr>");
            } else {
                text.add("<tr><td><a href=\"" + e.name + "\">" + e.name + "</a></td>" +
                        "<td>" + e.size + "</td>" +
                        "<td>" + e.modifyDate + "</td></tr>");
            }
            ;


        });


        text.add("</table>");

        OutputStreamWriter s = new OutputStreamWriter(os);

        text.forEach(e -> {
            try {
                s.write(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        try {
            s.flush();
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

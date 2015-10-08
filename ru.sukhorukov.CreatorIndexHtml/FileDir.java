import java.io.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by Администратор on 03.10.2015.
 */
public class FileDir {
    private String name;

    public OutputStreamWriter FileDir(String path, OutputStreamWriter os){
        name = path.substring(path.lastIndexOf(File.pathSeparator), path.length());
        sort(fillList(path));

        OutputStreamWriter s = new OutputStreamWriter( );
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
            return a.isDir;
            }
        });

      return p;
    };


}

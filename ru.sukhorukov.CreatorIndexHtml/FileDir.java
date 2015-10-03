import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.SortedSet;

/**
 * Created by Администратор on 03.10.2015.
 */
public class FileDir {
    private String name;
    private ArrayList<String> subDirs;
    private ArrayList<FileProps> files;

    FileDir(String path){
        name = path.substring(path.lastIndexOf(File.pathSeparator), path.length());

        setLists(path, subDirs, files);

    };

    public void getIndexHtml(String[] HtmlText){

    };

    private void sorfFiles(ArrayList<FileProps> pFileList){

    };

    private void sortDir(ArrayList<String> pDirList){
        SortedSet<String> s = new SortedSet<String>() {
        }
    };

    private String[] setLists(String path, ArrayList<String> pDirList, ArrayList<FileProps> pFileList){
        File []fList;
        FileProps f;
        File Fl = new File(path);

        pDirList = new ArrayList<String>();
        pFileList = new ArrayList<FileProps>();

        fList = Fl.listFiles();

        for(int i=0; i < fList.length; i++)
        {
            //Нужны только папки в место isFile() пишим isDirectory()
            if(fList[i].isDirectory()){
                pDirList.add(fList[i].getName());
            };

            if (fList[i].isFile()){

                f = new FileProps();
                f.name = (fList[i].getName();
                f.size = fList[i].length();
                f.modifyDate = new Date(fList[i].lastModified());
                pFileList.add(f);
            }
        }
    };

}

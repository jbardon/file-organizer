package fr.jbardon.perso.fileorganizer;

import com.sun.jdi.InvalidTypeException;
import fr.jbardon.perso.fileorganizer.folderaction.FolderAction;
import fr.jbardon.perso.fileorganizer.folderaction.FolderActionPrint;

import static java.lang.System.getProperty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class App 
{
public static void main(String[] args) throws FileNotFoundException,
      IOException, org.xml.sax.SAXException, InvalidTypeException {
 
	//System.out.println(getProperty("user.dir"));

    DirectoryRecursiveExplorer explorer = new DirectoryRecursiveExplorer(
        Paths.get("../sample/organized")
    );

    Set<File> files = explorer.getFilesInDirectory();
    FileDateMap fileOrganizer = new FileDateMap(files, 6);
    fileOrganizer.calulateFolders();

    System.out.println("File organizer configuration");
    System.out.println("- Max files in folders: " + fileOrganizer.getMaxElementsPerDate());
    System.out.println("- Tolerence: " + fileOrganizer.getElementsPerDateTolerence());

    List<LinkedHashSet<File>> calculatedFolders = fileOrganizer.getCalculatedFolders();
    FolderAction actionOnFolder = new FolderActionPrint();

    for(LinkedHashSet<File> folder : calculatedFolders){
        actionOnFolder.onFolderCreation(folder);
    }

    /*
    Path file = Paths.get("../sample/organized/2015/03/31-03-2015-1.txt");
    BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

    System.out.println("creationTime: " + attr.creationTime());
    System.out.println("lastAccessTime: " + attr.lastAccessTime());
    System.out.println("lastModifiedTime: " + attr.lastModifiedTime());

    System.out.println("isDirectory: " + attr.isDirectory());
    System.out.println("isOther: " + attr.isOther());
    System.out.println("isRegularFile: " + attr.isRegularFile());
    System.out.println("isSymbolicLink: " + attr.isSymbolicLink());
    System.out.println("size: " + attr.size());
    */
  }
}

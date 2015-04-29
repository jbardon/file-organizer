package fr.jbardon.perso.fileorganizer;

import com.sun.jdi.InvalidTypeException;
import fr.jbardon.perso.fileorganizer.controller.Controller;
import fr.jbardon.perso.fileorganizer.model.DirectoryRecursiveExplorer;
import fr.jbardon.perso.fileorganizer.model.FileDateMap;
import fr.jbardon.perso.fileorganizer.model.folderaction.FolderAction;
import fr.jbardon.perso.fileorganizer.model.folderaction.FolderActionPrint;
import fr.jbardon.perso.fileorganizer.views.MainWindow;

import javax.naming.ldap.Control;

import static java.lang.System.getProperty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class App 
{
public static void main(String[] args) throws FileNotFoundException,
      IOException, org.xml.sax.SAXException, InvalidTypeException {


    /*
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
    */

    Controller controller = new Controller();
    MainWindow window = new MainWindow(controller);
    controller.setGui(window);

    window.display();
  }
}

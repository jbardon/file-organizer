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

    Controller controller = new Controller();
    MainWindow window = new MainWindow(controller);
    controller.setGui(window);

    window.display();
  }
}

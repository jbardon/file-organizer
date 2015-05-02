package fr.jbardon.perso.fileorganizer.model;

import com.sun.jdi.InvalidTypeException;
import fr.jbardon.perso.fileorganizer.model.folderaction.FolderAction;
import fr.jbardon.perso.fileorganizer.model.folderaction.FolderActionHardDrive;
import fr.jbardon.perso.fileorganizer.model.folderaction.FolderActionTree;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.*;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by jeremy on 29/04/15.
 */
public class Model extends Observable {

    private List<LinkedHashSet<File>> calculatedFolders;
    private DefaultMutableTreeNode displayTree;

    private String inputDirectory;
    private String outputDirectory;

    public Model(String inputDir, String outputDir){
        this.displayTree = new DefaultMutableTreeNode("No nodes");
        this.calculatedFolders = new ArrayList<LinkedHashSet<File>>();

        this.inputDirectory = inputDir;
        this.outputDirectory = outputDir;
    }

    public void computeFolderOrganization() throws InvalidTypeException {
        DirectoryRecursiveExplorer explorer = new DirectoryRecursiveExplorer(
            Paths.get(this.inputDirectory)
        );

        Set<File> files = explorer.getFilesInDirectory();
        FileDateMap fileOrganizer = new FileDateMap(files, 6);
        fileOrganizer.calulateFolders();

        System.out.println("File organizer configuration");
        System.out.println("- Max files in folders: " + fileOrganizer.getMaxElementsPerDate());
        System.out.println("- Tolerence: " + fileOrganizer.getElementsPerDateTolerence());

        this.calculatedFolders = fileOrganizer.getCalculatedFolders();
        FolderActionTree actionOnFolder = new FolderActionTree();

        for(LinkedHashSet<File> folder : this.calculatedFolders){
            actionOnFolder.onFolderCreation(folder);
        }

        this.displayTree = actionOnFolder.getTreeNode();

        super.setChanged();
        super.notifyObservers(this);
    }

    public void applyFolderOrganization(){
        FolderActionHardDrive actionOnFolder = new FolderActionHardDrive();

        for(LinkedHashSet<File> folder : this.calculatedFolders){
            actionOnFolder.onFolderCreation(folder);
        }
    }

    public DefaultMutableTreeNode getDisplayTree(){
        return this.displayTree;
    }
}

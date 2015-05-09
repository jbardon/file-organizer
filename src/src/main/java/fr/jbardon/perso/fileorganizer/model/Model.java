package fr.jbardon.perso.fileorganizer.model;

import com.sun.jdi.InvalidTypeException;
import fr.jbardon.perso.fileorganizer.model.folderaction.FolderAction;
import fr.jbardon.perso.fileorganizer.model.folderaction.FolderActionHardDrive;
import fr.jbardon.perso.fileorganizer.model.folderaction.FolderActionTree;
import fr.jbardon.perso.fileorganizer.views.ObserverNotification;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.*;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by jeremy on 29/04/15.
 */
public class Model extends Observable {

    private LinkedHashMap<String,LinkedHashSet<File>> calculatedFolders;
    private DefaultMutableTreeNode displayTree;

    // DirectoryRecursiveExplorer parameters
    private String inputDirectory;
    private String outputDirectory;

    private int maxFiles;
    private int tolerance;

    // Copy progress
    private int nbFiles;
    private int currentFile;

    public Model(String inputDir, String outputDir, int maxFiles, int tolerance){

        this.displayTree = new DefaultMutableTreeNode("No nodes");
        this.calculatedFolders = new LinkedHashMap<String,LinkedHashSet<File>>();

        this.inputDirectory = inputDir;
        this.outputDirectory = outputDir;

        this.maxFiles = maxFiles;
        this.tolerance = tolerance;

        this.nbFiles = 0;
        this.currentFile = 0;
    }

    public void computeFolderOrganization() throws InvalidTypeException {
        DirectoryRecursiveExplorer explorer = new DirectoryRecursiveExplorer(
            Paths.get(this.inputDirectory)
        );

        Set<File> files = explorer.getFilesInDirectory();
        FileDateMap fileOrganizer = new FileDateMap(files, this.maxFiles, this.tolerance);
        this.calculatedFolders = fileOrganizer.calculateFolders();

        System.out.println("File organizer configuration");
        System.out.println("- Max files in folders: " + fileOrganizer.getMaxElementsPerDate());
        System.out.println("- Tolerence: " + fileOrganizer.getElementsPerDateTolerence());

        FolderActionTree actionOnFolder = new FolderActionTree();

        for(Map.Entry<String, LinkedHashSet<File>> entry : this.calculatedFolders.entrySet()){
            actionOnFolder.onFolderCreation(entry.getKey(), entry.getValue());
        }

        this.displayTree = actionOnFolder.getTreeNode();

        super.setChanged();
        super.notifyObservers(ObserverNotification.DISPLAY_TREE);
    }

    public void applyFolderOrganization(){
        FolderActionHardDrive actionOnFolder = new FolderActionHardDrive(
            this.outputDirectory
        );

        this.nbFiles = this.calculatedFolders.size();

        for(Map.Entry<String, LinkedHashSet<File>> entry : this.calculatedFolders.entrySet()){
            actionOnFolder.onFolderCreation(entry.getKey(), entry.getValue());

            this.currentFile++;

            super.setChanged();
            super.notifyObservers(ObserverNotification.COPY_PROGRESS);
        }

        this.nbFiles = 0;
        this.currentFile = 0;

        System.out.println("End of copy");
    }

    public DefaultMutableTreeNode getDisplayTree(){
        return this.displayTree;
    }

    public int[] getCopyProgress(){
        return new int[]{
            this.currentFile, this.nbFiles
        };
    }
}

package fr.jbardon.perso.fileorganizer;

import com.sun.jdi.InvalidTypeException;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

/**
 * Created by jeremy on 26/04/15.
 */
public class DirectoryRecursiveExplorer {

    private File directory;
    private Set<File> filesInDirectory;

    public DirectoryRecursiveExplorer(Path directoryToExplore) throws InvalidTypeException {
        this.directory = directoryToExplore.toFile();

        if(!this.directory.isDirectory()){
            throw new InvalidTypeException("Path \"" + this.directory.getAbsolutePath() + "\" is not directory");
        }

        this.filesInDirectory = new HashSet<File>();
    }

    public Set<File> exploreDirectory(){

        this.filesInDirectory = new LinkedHashSet<File>();

        Queue<File> filesToCount = new LinkedList<File>();
        filesToCount.addAll(Arrays.asList(this.directory.listFiles()));

        while(!filesToCount.isEmpty()){
            File currentFile = filesToCount.remove();

            if(currentFile.isFile()){
                this.filesInDirectory.add(currentFile);
            }
            else if(currentFile.isDirectory()){
                filesToCount.addAll(Arrays.asList(currentFile.listFiles()));
            }
        }

        return this.filesInDirectory;
    }

    public Set<File> getFilesInDirectory() {

        if(this.filesInDirectory.isEmpty()){
            this.exploreDirectory();
        }

        return filesInDirectory;
    }

    public String getTopExploredDirectory(){
        return this.directory.getAbsolutePath();
    }
}

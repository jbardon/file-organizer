package fr.jbardon.perso.fileorganizer.model;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;

/**
 * Created by jeremy on 26/04/15.
 */
public class DirectoryRecursiveExplorer {

    private File directory;
    private Set<File> filesInDirectory;

    public DirectoryRecursiveExplorer(Path directoryToExplore) throws NoSuchElementException {
        this.directory = directoryToExplore.toFile();

        if(!this.directory.isDirectory()){
            throw new NoSuchElementException("Path \"" + this.directory.getAbsolutePath() + "\" is not directory");
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

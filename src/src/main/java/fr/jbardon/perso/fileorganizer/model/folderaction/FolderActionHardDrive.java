package fr.jbardon.perso.fileorganizer.model.folderaction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedHashSet;

/**
 * Created by jeremy on 27/04/15.
 */
public class FolderActionHardDrive implements FolderAction {

    private String targetDirectory;

    public FolderActionHardDrive(String targetDir){
        this.targetDirectory = targetDir;
    }

    @Override
    public void onFolderCreation(String dirName, LinkedHashSet<File> files) {

        // Create folder for theses files
        File currentDirectory = new File(
                targetDirectory + "/" + ((File)files.toArray()[0]).lastModified()
        );

        currentDirectory.mkdir();

        // TODO Use calculated folder name
        // TODO Handle sub directories copy and creation

        // Copy all files to target
        for(File currentFile : files){
            //System.out.println(currentFile.getAbsolutePath() + " to " + currentDirectory.getAbsolutePath() + "/" + currentFile.getName());
            System.out.println("Copy " + currentFile.getName() + "...");
            try {
                Files.copy(
                    Paths.get(currentFile.getAbsolutePath()),
                        Paths.get(currentDirectory.getAbsolutePath() + "/" + currentFile.getName())
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

package fr.jbardon.perso.fileorganizer.model.folderaction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by jeremy on 27/04/15.
 */
public class FolderActionHardDrive implements FolderAction {

    private String targetDirectory;

    public FolderActionHardDrive(String targetDir){
        this.targetDirectory = targetDir;

        File targetFile = new File(targetDir);
        if(!targetFile.exists()){
            targetFile.mkdir();
        }
    }

    @Override
    public void onFolderCreation(String dirName, LinkedHashSet<File> files) {

        List<String> date = Arrays.asList(dirName.split("/"));

        File yearDir = new File(targetDirectory + "/" + date.get(0));
        if(!yearDir.exists()){
            yearDir.mkdir();
        }

        File monthDir = new File(yearDir.getAbsolutePath() + "/" + date.get(1));
        if(!monthDir.exists()){
            monthDir.mkdir();
        }        
        
        if(date.size() >= 3){
            File dayDir = new File(monthDir.getAbsolutePath() + "/" + date.get(2));
            dayDir.mkdir();
        }

        // Copy all files to target
        for(File currentFile : files){
            System.out.println("Copy " + currentFile.getName() + "...");
            try {
                Files.copy(
                    Paths.get(currentFile.getAbsolutePath()),
                        Paths.get(targetDirectory + "/" + dirName + currentFile.getName())
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

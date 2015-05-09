package fr.jbardon.perso.fileorganizer.model.folderaction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
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

            Path sourceFile = Paths.get(currentFile.getAbsolutePath()),
                 targetFile = Paths.get(targetDirectory + "/" + dirName + currentFile.getName());
            try {
                Files.copy(
                    sourceFile,
                    targetFile,
                    StandardCopyOption.COPY_ATTRIBUTES
                );

                // Preserve last modified and access time after to copy
                BasicFileAttributes attrs = Files.readAttributes(sourceFile, BasicFileAttributes.class);

                Files.setAttribute(targetFile, "creationTime", attrs.creationTime());
                Files.setAttribute(targetFile, "lastModifiedTime", attrs.lastModifiedTime());
                Files.setAttribute(targetFile, "lastAccessTime", attrs.lastAccessTime());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

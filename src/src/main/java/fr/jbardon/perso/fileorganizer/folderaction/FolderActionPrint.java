package fr.jbardon.perso.fileorganizer.folderaction;

import java.io.File;
import java.util.Date;
import java.util.LinkedHashSet;

/**
 * Created by jeremy on 27/04/15.
 */
public class FolderActionPrint implements FolderAction {
    @Override
    public void onFolderCreation(LinkedHashSet<File> files) {

        System.out.print("> Folder from ");
        System.out.print(new Date(((File) (files.toArray()[0])).lastModified()));
        System.out.print(" to ");
        System.out.println(new Date(((File)(files.toArray()[files.size()-1])).lastModified()));

        for(File file : files){
            System.out.println(".. " + file.getName());
        }
    }
}

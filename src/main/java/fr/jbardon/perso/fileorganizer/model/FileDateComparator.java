package fr.jbardon.perso.fileorganizer.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Comparator;

/**
 * Created by jeremy on 26/04/15.
 */
public class FileDateComparator implements Comparator<File> {

    @Override
    public int compare(File fileA, File fileB) {

        BasicFileAttributes attributesA, attributesB;

        // Get file A attributes
        try {
            attributesA = Files.readAttributes(
                    Paths.get(fileA.getAbsolutePath()),
                    BasicFileAttributes.class
            );

        }
        catch (IOException e) {
            return -1;
        }

        // Get file B attributes
        try {
            attributesB = Files.readAttributes(
                    Paths.get(fileB.getAbsolutePath()),
                    BasicFileAttributes.class
            );

        }
        catch (IOException e) {
            return 1;
        }

        // Compare files dates
        FileTime dateA = attributesA.lastModifiedTime(),
                 dateB = attributesB.lastModifiedTime();

        return dateA.compareTo(dateB);
    }
}

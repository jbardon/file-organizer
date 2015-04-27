package fr.jbardon.perso.fileorganizer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.*;

/**
 * Created by jeremy on 26/04/15.
 */
public class FileDateMap {

    // Data structures
    private LinkedHashMap<Date, LinkedHashSet<File>> content;
    private List<File> filesList;

    // Configuration elements
    private final static int DEFAULT_MAX_ELEMENTS_PER_DATE = 8;
    private int maxElementsPerDate;

    private final static int DEFAULT_ELEMENTS_PER_DATE_TOLERENCE = 2;
    private int elementsPerDateTolerence;

    // Constructors
    public FileDateMap(Set<File> elementsSet, int maxElementsPerDate, int elementsPerDateTolerence){
        this.content = new LinkedHashMap<Date, LinkedHashSet<File>>();
        this.maxElementsPerDate = maxElementsPerDate;
        this.elementsPerDateTolerence = elementsPerDateTolerence;

        // Sort the list of files by date
        this.filesList = new ArrayList<File>(elementsSet);
        Collections.sort(this.filesList, new FileDateComparator());
    }

    public FileDateMap(Set<File> elementsSet, int maxElementsPerDate){
        this(
                elementsSet,
                maxElementsPerDate,
                FileDateMap.DEFAULT_ELEMENTS_PER_DATE_TOLERENCE
        );
    }

    public FileDateMap(Set<File> elementsSet){
        this(elementsSet, FileDateMap.DEFAULT_MAX_ELEMENTS_PER_DATE);
    }

    public void calulateFolders(){
        this.content.clear();

        this.storeFileByDate();
        this.mergeFilesInFolders();
    }

    /**
     * Store all files in the internal structure
     * content which has the last modified date as key
     */
    private void storeFileByDate(){

        for(File currentFile : this.filesList){
            Date lastModified = new Date(currentFile.lastModified());

            LinkedHashSet<File> sameIntervalFiles = this.content.get(lastModified);

            // No files for the current file date, create an entry
            if(sameIntervalFiles == null){
                sameIntervalFiles = new LinkedHashSet<File>();
                this.content.put(lastModified, sameIntervalFiles);
            }

            sameIntervalFiles.add(currentFile);
        }
    }

    /**
     * Set files in sub-folders by taking into
     * account max element per date and tolerence
     */
    private void mergeFilesInFolders(){
        Iterator contentIterator = this.content.entrySet().iterator();
        Map.Entry currentDate = (Map.Entry) contentIterator.next();

        while (contentIterator.hasNext()){

            Map.Entry nextDate = (Map.Entry) contentIterator.next();
            LinkedHashSet<File> nextFiles = (LinkedHashSet<File>) nextDate.getValue();
            LinkedHashSet<File> currentFiles = (LinkedHashSet<File>) currentDate.getValue();

            if(currentFiles.size() < this.maxElementsPerDate + this.elementsPerDateTolerence){
                if(currentFiles.size() + nextFiles.size() <= this.maxElementsPerDate + this.elementsPerDateTolerence){
                    currentFiles.addAll(nextFiles);
                    contentIterator.remove();

                    // Next file can may be also added in
                    // the current folder if the maximum has
                    // not been reached
                    continue;
                }
            }

            currentDate = nextDate;
        }
    }

    // Getters
    public int getMaxElementsPerDate() {
        return maxElementsPerDate;
    }

    public int getElementsPerDateTolerence() {
        return elementsPerDateTolerence;
    }

    // Setters
    public void setDefaultMaxElementsPerDate(){
        this.setMaxElementPerDate(FileDateMap.DEFAULT_MAX_ELEMENTS_PER_DATE);
    }
    public void setMaxElementPerDate(int maxElementsPerDate) {
        this.maxElementsPerDate = maxElementsPerDate;
    }

    public void setDefaultElementsPerDateTolerence(){
        this.setElementsPerDateTolerence(FileDateMap.DEFAULT_ELEMENTS_PER_DATE_TOLERENCE);
    }
    public void setElementsPerDateTolerence(int elementsPerDateTolerence) {
        this.elementsPerDateTolerence = elementsPerDateTolerence;
    }

    public List<LinkedHashSet<File>> getCalculatedFolders(){
        return new ArrayList(this.content.values());
    }
}

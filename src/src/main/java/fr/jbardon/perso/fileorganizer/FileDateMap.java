package fr.jbardon.perso.fileorganizer;

import java.io.File;
import java.util.*;

/**
 * Created by jeremy on 26/04/15.
 */
public class FileDateMap {

    // Data structures
    private HashMap<Date, LinkedHashSet<File>> content;
    private List<File> filesList;

    // Configuration elements
    private final static int DEFAULT_MAX_ELEMENTS_PER_DATE = 8;
    private int maxElementsPerDate;

    private final static int DEFAULT_ELEMENTS_PER_DATE_TOLERENCE = 2;
    private int elementsPerDateTolerence;

    // Constructors
    public FileDateMap(Set<File> elementsSet, int maxElementsPerDate, int elementsPerDateTolerence){
        this.content = new HashMap<Date, LinkedHashSet<File>>();
        this.maxElementsPerDate = maxElementsPerDate;
        this.elementsPerDateTolerence = elementsPerDateTolerence;

        // Sort the list of files by date
        this.filesList = new ArrayList<File>(elementsSet);
        Collections.sort(this.filesList, new FileDateComparator());
    }

    public FileDateMap(Set<File> elementsSet, int maxElementsPerDate){
        this(
                elementsSet,
                FileDateMap.DEFAULT_MAX_ELEMENTS_PER_DATE,
                FileDateMap.DEFAULT_ELEMENTS_PER_DATE_TOLERENCE
        );
    }

    public FileDateMap(Set<File> elementsSet){
        this(elementsSet, FileDateMap.DEFAULT_MAX_ELEMENTS_PER_DATE);
    }

    public void compute(){
        this.content.clear();

        for(int i = 0; i < this.filesList.size(); i++){

        }
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
}

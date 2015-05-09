package fr.jbardon.perso.fileorganizer.model;

import java.io.File;
import java.util.*;

/**
 * Created by jeremy on 26/04/15.
 */
public class FileDateMap {

    // Data structures
    private List<File> filesList;

    // Configuration elements
    private final static int DEFAULT_MAX_ELEMENTS_PER_DATE = 8;
    private int maxElementsPerDate;

    private final static int DEFAULT_ELEMENTS_PER_DATE_TOLERENCE = 2;
    private int elementsPerDateTolerence;

    // Constructors
    public FileDateMap(Set<File> elementsSet, int maxElementsPerDate, int elementsPerDateTolerence){
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

    public LinkedHashMap<String,LinkedHashSet<File>> calculateFolders(){
        LinkedHashMap<Date, LinkedHashSet<File>> content = this.storeFileByDate();
        LinkedList<LinkedHashSet<File>> directories = this.mergeFilesInFolders(content);
        LinkedHashMap<String,LinkedHashSet<File>> subDirs = this.makeFolderOrganization(directories);
        return subDirs;
    }

    /**
     * Store all files in the internal structure
     * content which has the last modified date as key
     */
    private LinkedHashMap<Date, LinkedHashSet<File>> storeFileByDate(){

        LinkedHashMap<Date, LinkedHashSet<File>> content = new LinkedHashMap<Date, LinkedHashSet<File>>();

        for(File currentFile : this.filesList){
            Date lastModified = new Date(currentFile.lastModified());

            LinkedHashSet<File> sameIntervalFiles = content.get(lastModified);

            // No files for the current file date, create an entry
            if(sameIntervalFiles == null){
                sameIntervalFiles = new LinkedHashSet<File>();
                content.put(lastModified, sameIntervalFiles);
            }

            sameIntervalFiles.add(currentFile);
        }

        return content;
    }

    /**
     * Set files in sub-folders by taking into
     * account max element per date and tolerence
     */
    private LinkedList<LinkedHashSet<File>> mergeFilesInFolders(LinkedHashMap<Date, LinkedHashSet<File>> content){

        // The algorithm need to know 3 following items but the
        // iterator only allows to remove the last so switch to a list..
        Collection<LinkedHashSet<File>> contentCollection =  content.values();

        LinkedList<LinkedHashSet<File>> contentList = new LinkedList<LinkedHashSet<File>>();
        contentList.addAll(contentCollection);

        // Algorithm
        ListIterator contentIterator = contentList.listIterator();

        while (contentIterator.hasNext()){

            LinkedHashSet<File> currentFiles = (LinkedHashSet<File>) contentIterator.next();
            if(!contentIterator.hasNext()){
                continue; // exit: following algorithm need to know the next element
            }

            if (currentFiles.size() < this.maxElementsPerDate) {

                // Get previous files
                LinkedHashSet<File> previousFiles = null;
                contentIterator.previous();

                if(contentIterator.hasPrevious()){
                    previousFiles = (LinkedHashSet<File>) contentIterator.previous();
                    contentIterator.next();
                }

                // Get next files
                contentIterator.next();
                LinkedHashSet<File> nextFiles = (LinkedHashSet<File>) contentIterator.next();

                // Reset iterator on currentDate
                contentIterator.previous();

                // No merge if the files are not in the same year
                // or are in the same month
                long currentLastModified = currentFiles.iterator().next().lastModified();
                long nextLastModified = nextFiles.iterator().next().lastModified();

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(currentLastModified);

                int currentYear = calendar.get(Calendar.YEAR),
                    currentMonth = calendar.get(Calendar.MONTH);

                calendar.setTimeInMillis(nextLastModified);
                int nextYear = calendar.get(Calendar.YEAR);

                if(currentYear != nextYear){
                    continue;
                }

                if(previousFiles != null){
                    long previousLastModified = previousFiles.iterator().next().lastModified();

                    calendar.setTimeInMillis(previousLastModified);
                    int previousMonth = calendar.get(Calendar.MONTH),
                        previousYear = calendar.get(Calendar.YEAR);

                    // Merge with previous if they are not in the same month
                    if(currentMonth == previousMonth && previousYear == currentYear)
                    {
                        if(currentFiles.size() <= this.maxElementsPerDate
                            && previousFiles.size() <= this.maxElementsPerDate) {
                            previousFiles.addAll(currentFiles);

                            contentIterator.previous();
                            contentIterator.remove();
                        }

                        continue;
                    }

                }

                // Merge current and next directories if they don't have
                // more than max element (added)
                if (currentFiles.size() + nextFiles.size() <= this.maxElementsPerDate) {
                    currentFiles.addAll(nextFiles);
                    contentIterator.next();
                    contentIterator.remove();

                    // Next file can may be also added in
                    // the current folder if the maximum has
                    // not been reached
                    contentIterator.previous();
                }

                // Tolerence: merge two directories if the next is bigger than
                // the maximum and new size for the two is not bigger than max + tolerence
                else if(previousFiles != null && nextFiles.size() > this.maxElementsPerDate){
                    if(previousFiles.size() + currentFiles.size() <= this.maxElementsPerDate + this.elementsPerDateTolerence){
                        previousFiles.addAll(currentFiles);
                        contentIterator.previous();
                        contentIterator.remove();
                    }
                }
            }
        }

        return contentList;
    }

    /**
     * Turn list of folders into structured folder list
     * with years and months
     */
    private LinkedHashMap<String,LinkedHashSet<File>> makeFolderOrganization(LinkedList<LinkedHashSet<File>> directories) {

        LinkedHashMap<String,LinkedHashSet<File>> calculatedDirs = new LinkedHashMap<String,LinkedHashSet<File>>();
        Iterator contentIterator = directories.iterator();

        while (contentIterator.hasNext()){
            LinkedHashSet<File> currentFiles = (LinkedHashSet<File>) contentIterator.next();

            // Retrieve year and month for the first file and
            // the last file to determinate a folder name
            long currentLastModified = currentFiles.iterator().next().lastModified();
            long nextLastModified = ((File)currentFiles.toArray()[currentFiles.size() - 1]).lastModified();

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(currentLastModified);

            int firstYear = calendar.get(Calendar.YEAR),
                firstMonth = calendar.get(Calendar.MONTH) + 1, // January = 0
                firstDay = calendar.get(Calendar.DAY_OF_MONTH);

            calendar.setTimeInMillis(nextLastModified);
            int lastMonth = calendar.get(Calendar.MONTH) + 1,
                lastDay = calendar.get(Calendar.DAY_OF_MONTH);

            // Find folder name
            StringBuilder folderName = new StringBuilder("");
            folderName.append(firstYear + "/");

            if(firstMonth != lastMonth){
                folderName.append(firstMonth + "-");
                folderName.append(lastMonth + "/");
            }
            else {
                folderName.append(firstMonth + "/");

                if(firstDay == lastDay && currentFiles.size() > this.maxElementsPerDate){
                    folderName.append(firstDay + "/");
                }
            }

            LinkedHashSet<File> existingKey = calculatedDirs.get(folderName.toString());
            if(existingKey != null){
                existingKey.addAll(currentFiles);
            }
            else {
                calculatedDirs.put(folderName.toString(), currentFiles);
            }
        }

        return calculatedDirs;
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

    // TODO change
    public List<LinkedHashSet<File>> getCalculatedFolders(){
        return new ArrayList<LinkedHashSet<File>>();
    }
}

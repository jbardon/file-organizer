# file-organizer

### Organize files automatically in folders with metadata.

This program organize any types of files with their metadata (**lastModifiedTime** because creationTime is changed after all copy) and it **preserve old metadata**.  

MainWindow | Preview Dialog
:------:|:------:
![MainWindow](http://img15.hostingpics.net/pics/257836MainWindow.png)|![PreviewDialog](http://img15.hostingpics.net/pics/280994PreviewDialog.png)

It is possible to **select input and output directories** through the interface and get a preview of the arrangment. In addition, you can **adjust the two variables of the algorithm** (maxElementsPerDate and elementsPerDateTolerence).  

Screenshots are available in [screenshots directory](https://github.com/jbardon/file-organizer/tree/master/screenshots) and an example for testing (automatically selected by default) in [sample directory](https://github.com/jbardon/file-organizer/tree/master/sample).

### Run it
file-organizer is a maven project, use your favorite IDE or type the following command in your favorite prompt:

```
$ mvn compile
$ mvn exec:java
``` 

## Algorithm notes

Implemented in the **FileDateMap** class, can be found in [fileorganizer/model](https://github.com/jbardon/file-organizer/blob/master/src/main/java/fr/jbardon/perso/fileorganizer/model/FileDateMap.java).

### Treatment phases
* **storeFileByDate**: files are stored by date (hashmap with date as key)
* **mergeFilesInFolders**: most important part which makes groups with multiple dates
* **makeFolderOrganization**: compute folders names and makes the output directory hierarchy

### Variables
* **maxElementsPerDate**  
Maximum of files allowed in a single folder (used during mergeFilesInFolders)  
  
* **elementsPerDateTolerence**  
In the case of **[5, 3, 35]** (numbers of files per date) if the maximum is set to **7** the algorithm will not merge **5** and **3**.  
It is possible to go through it by adding a tolerance: with a tolerence of **1**, the maximum will be **7 + 1 = 8** so the algorithm will perform the merge between **5** and **3**.  
  - This not works if the two folders are not in the same year and month. 
  - Tolerence is only used when the following date is too big (35 in the example), it avoids to get tiny folders (3 in the example)

### Key points
* The output directory hierarchy begins with the year, then the month and a day (if necessary)
  - There are one folder per year, if no files are dated from a given year the folder will not be created 
  
* Until there are not enough files to reach the maximum for one folder (and dates have the same year), dates directories will be merged into one directory
  - Month can be merged, the result folder will be named "1-4" if it contains files from January to April for example
  - Years can't be merged
  - If there are too much files on one day a folder with the day number will be created and added in the month directory
  
## Issues
* Interface problem in the preview (positionning and progress bar update)
* In some situations it is possible to find ***2014/01-04*** and ***2014/04***

## Edit file metadata in command line

```
touch -t Ymdhm filename
```

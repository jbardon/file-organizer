package fr.jbardon.perso.fileorganizer.model.folderaction;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.text.DateFormatSymbols;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by jeremy on 02/05/15.
 */
public class FolderActionTree implements FolderAction {

    // Tree's root
    private DefaultMutableTreeNode treeNode;

    // First out is the year, second is the month
    private ArrayDeque<DefaultMutableTreeNode> previousNodes;

    private String[] monthNames; // English month

    public FolderActionTree(){
        this.treeNode = new DefaultMutableTreeNode("Your folder");
        this.previousNodes = new ArrayDeque<DefaultMutableTreeNode>(2);

        DateFormatSymbols symbols = new DateFormatSymbols(Locale.ENGLISH);
        this.monthNames = symbols.getMonths();
    }

    @Override
    public void onFolderCreation(String dirName, LinkedHashSet<File> files) {

        List<String> date = Arrays.asList(dirName.split("/"));

        // First call: no previous year & month
        if(this.previousNodes.isEmpty()){
            DefaultMutableTreeNode yearNode = new DefaultMutableTreeNode(
                date.get(0)
            );
            this.treeNode.add(yearNode);
            this.previousNodes.push(yearNode);

            DefaultMutableTreeNode monthNode = new DefaultMutableTreeNode(
                this.monthNames[Integer.parseInt(date.get(1)) - 1]
            );
            this.previousNodes.push(monthNode);

            yearNode.add(monthNode);
        }

        // Get previous year and month to create the new year or month
        // if not equal to the previous ones
        DefaultMutableTreeNode monthNode = this.previousNodes.pop();
        String previousMonth = (String) monthNode.getUserObject();

        DefaultMutableTreeNode yearNode = this.previousNodes.pop();
        String previousYear = (String) yearNode.getUserObject();

        if(!previousYear.equals(date.get(0))){
            DefaultMutableTreeNode newYearNode = new DefaultMutableTreeNode(
                date.get(0)
            );
            this.treeNode.add(newYearNode);

            monthNode = new DefaultMutableTreeNode(
                this.monthNames[Integer.parseInt(date.get(1)) - 1]
            );
            newYearNode.add(monthNode);

            yearNode = newYearNode;
        }
        else {
            String currentMonth = this.monthNames[Integer.parseInt(date.get(1)) - 1];
            if (!currentMonth.equals(previousMonth)) {

                monthNode = new DefaultMutableTreeNode(
                    this.monthNames[Integer.parseInt(date.get(1)) - 1]
                );

                yearNode.add(monthNode);
            }
        }

        this.previousNodes.push(yearNode);

        // A lot of file for a single day: create a directory for it
        DefaultMutableTreeNode insertNode = monthNode;
        if(date.size() >= 3){
            insertNode = new DefaultMutableTreeNode(
                date.get(2)
            );
            monthNode.add(insertNode);
        }

        // List all files
        for(File currentFile : files){
            DefaultMutableTreeNode currentNode = new DefaultMutableTreeNode(
                    currentFile.getName()
            );
            insertNode.add(currentNode);
        }

        this.previousNodes.push(monthNode);
    }

    public DefaultMutableTreeNode getTreeNode() {
        return treeNode;
    }
}

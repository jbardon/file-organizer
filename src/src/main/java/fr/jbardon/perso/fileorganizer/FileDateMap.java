package fr.jbardon.perso.fileorganizer;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by jeremy on 26/04/15.
 */
public class FileDateMap {

    private HashMap<Date, LinkedHashSet<File>> content;

    public FileDateMap(Set<File> elementsSet){
        this.content = new HashMap<Date, LinkedHashSet<File>>();


    }
}

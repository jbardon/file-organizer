package fr.jbardon.perso.fileorganizer.folderaction;

import java.io.File;
import java.util.LinkedHashSet;

/**
 * Created by jeremy on 27/04/15.
 */
public interface FolderAction {
    public void onFolderCreation(LinkedHashSet<File> files);
}

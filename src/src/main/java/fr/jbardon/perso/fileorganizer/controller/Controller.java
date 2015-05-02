package fr.jbardon.perso.fileorganizer.controller;

import com.sun.jdi.InvalidTypeException;
import fr.jbardon.perso.fileorganizer.model.Model;
import fr.jbardon.perso.fileorganizer.views.MainWindow;
import fr.jbardon.perso.fileorganizer.views.dialogs.PreviewDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by jeremy on 29/04/15.
 */
public class Controller implements ActionListener {

    private MainWindow window;
    private Model model;

    public void setGui(MainWindow window){
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonSource = (JButton) e.getSource();

        switch(buttonSource.getText()) {

            case MainWindow.BROWSE_BUTTON_TEXT:
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int directorySelected = fileChooser.showOpenDialog(null);
                if(directorySelected != JFileChooser.APPROVE_OPTION){
                    return;
                }

                File selectedDirectory = fileChooser.getSelectedFile();

                switch(buttonSource.getToolTipText()){
                    case MainWindow.BROWSE_INDIR_TOOLTIP:
                        this.window.updateInputDirPath(selectedDirectory.getAbsolutePath());
                        break;

                    case MainWindow.BROWSE_OUTDIR_TOOLTIP:
                        this.window.updateOutputDirPath(selectedDirectory.getAbsolutePath());
                        break;
                }

                break;

            case MainWindow.PREVIEW_BUTTON_TEXT:

                this.model = new Model(
                    this.window.getInputDirPath(),
                    this.window.getOutputDirPath()
                );

                this.model.addObserver(this.window);

                try {
                    this.model.computeFolderOrganization();
                } catch (InvalidTypeException e1) {
                    e1.printStackTrace();
                }

                break;

            case PreviewDialog.BUTTON_APPLY_TEXT:
                this.model.applyFolderOrganization();
                break;
        }
    }
}

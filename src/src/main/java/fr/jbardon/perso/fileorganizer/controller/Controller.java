package fr.jbardon.perso.fileorganizer.controller;

import com.sun.jdi.InvalidTypeException;
import fr.jbardon.perso.fileorganizer.model.Model;
import fr.jbardon.perso.fileorganizer.views.MainWindow;
import fr.jbardon.perso.fileorganizer.views.ProgressTask;
import fr.jbardon.perso.fileorganizer.views.dialogs.PreviewDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observer;

/**
 * Created by jeremy on 29/04/15.
 */
public class Controller implements ActionListener {

    private MainWindow window;
    private Model model;

    public void setGui(MainWindow window){
        this.window = window;
    }

    public void addModelObserver(Observer observer){
        this.model.addObserver(observer);
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
                    this.window.getOutputDirPath(),
                    this.window.getMaxFiles(),
                    this.window.getTolerance()
                );

                this.model.addObserver(this.window);

                try {
                    this.model.computeFolderOrganization();
                } catch (InvalidTypeException e1) {
                    e1.printStackTrace();
                }

                break;

            case PreviewDialog.BUTTON_APPLY_TEXT:

                ProgressTask progressDisplay = new ProgressTask();
                progressDisplay.addPropertyChangeListener(this.window.getPreviewDialog());

                this.model.addObserver(progressDisplay);
                progressDisplay.execute();

                this.model.applyFolderOrganization();

                break;
        }
    }
}

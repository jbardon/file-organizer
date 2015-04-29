package fr.jbardon.perso.fileorganizer.controller;

import fr.jbardon.perso.fileorganizer.model.Model;
import fr.jbardon.perso.fileorganizer.views.MainWindow;

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
                this.window.updateDirectoryPath(selectedDirectory.getAbsolutePath());

                break;

            case MainWindow.PREVIEW_BUTTON_TEXT:
                this.window.showPreviewDialog();
                break;
        }
    }
}

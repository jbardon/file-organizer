package fr.jbardon.perso.fileorganizer.views;

import fr.jbardon.perso.fileorganizer.controller.Controller;
import fr.jbardon.perso.fileorganizer.views.dialogs.PreviewDialog;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jeremy on 28/04/15.
 */
public class MainWindow extends JFrame {

    public final static String NO_DIRECTORY_SELECTED = "No directory selected...";
    public final static String PREVIEW_BUTTON_TEXT = "Preview..";
    public final static String BROWSE_BUTTON_TEXT = "Browse..";

    private JTextField textSelectedFolder;
    private JSpinner spinnerMaxFiles;
    private JSpinner spinnerTolerence;

    public MainWindow(Controller controller){

        super("File organizer");

        // Interface
        JPanel panelRoot = new JPanel();
        panelRoot.setLayout(new BorderLayout());
        panelRoot.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panelRoot);

        // Select folder
        JPanel panelFolder = new JPanel();
        panelFolder.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        panelFolder.setLayout(new BorderLayout());

        this.textSelectedFolder = new JTextField(MainWindow.NO_DIRECTORY_SELECTED);
        this.textSelectedFolder.setEditable(false);

        JButton buttonBrowseFolder = new JButton(MainWindow.BROWSE_BUTTON_TEXT);
        buttonBrowseFolder.addActionListener(controller);

        panelFolder.add(new JLabel("Directory: "), BorderLayout.WEST);
        panelFolder.add(this.textSelectedFolder, BorderLayout.CENTER);
        panelFolder.add(buttonBrowseFolder, BorderLayout.EAST);

        // Spinners
        JPanel panelOptions = new JPanel();
        panelOptions.setLayout(new BorderLayout());
        panelOptions.setBorder(BorderFactory.createTitledBorder("Options"));

        JPanel panelOptionBorder = new JPanel();
        panelOptionBorder.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelOptionBorder.setLayout(new BoxLayout(panelOptionBorder, BoxLayout.X_AXIS));

        this.spinnerMaxFiles = new JSpinner(new SpinnerNumberModel(8, 1, 100, 1));
        this.spinnerTolerence = new JSpinner(new SpinnerNumberModel(2, 0, 10, 1));

        panelOptionBorder.add(new JLabel("Max files: "));
        panelOptionBorder.add(this.spinnerMaxFiles);
        panelOptionBorder.add(new JLabel("Tolerence: "));
        panelOptionBorder.add(this.spinnerTolerence);

        panelOptions.add(panelOptionBorder, BorderLayout.WEST);

        // Buttons
        JPanel panelButtons = new JPanel();
        panelButtons.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panelButtons.setLayout(new BorderLayout());

        JButton buttonPreview = new JButton(MainWindow.PREVIEW_BUTTON_TEXT);
        buttonPreview.addActionListener(controller);

        panelButtons.add(new JLabel(""), BorderLayout.CENTER);
        panelButtons.add(buttonPreview, BorderLayout.EAST);

        // End stuff
        panelRoot.add(panelFolder, BorderLayout.NORTH);
        panelRoot.add(panelOptions, BorderLayout.CENTER);
        panelRoot.add(panelButtons, BorderLayout.SOUTH);

        this.setPreferredSize(new Dimension(400, 200));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void display(){
        this.pack();
        this.setVisible(true);
    }

    public void showPreviewDialog(){
        PreviewDialog dialog = new PreviewDialog(this);
        dialog.display();
    }

    public void updateDirectoryPath(String path){
        this.textSelectedFolder.setText(path);
    }
}

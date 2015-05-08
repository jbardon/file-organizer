package fr.jbardon.perso.fileorganizer.views;

import fr.jbardon.perso.fileorganizer.controller.Controller;
import fr.jbardon.perso.fileorganizer.model.Model;
import fr.jbardon.perso.fileorganizer.views.dialogs.PreviewDialog;
import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by jeremy on 28/04/15.
 */
public class MainWindow extends JFrame implements Observer {

    public final static String NO_DIRECTORY_SELECTED = "No directory selected...";
    public final static String PREVIEW_BUTTON_TEXT = "Preview..";
    public final static String BROWSE_BUTTON_TEXT = "Browse..";

    public final static String BROWSE_INDIR_TOOLTIP = "Input directory";
    public final static String BROWSE_OUTDIR_TOOLTIP = "Output directory";

    private Controller controller;

    private JTextField textInputFolder;
    private JTextField textOutputFolder;

    private JSpinner spinnerMaxFiles;
    private JSpinner spinnerTolerence;

    public MainWindow(Controller controller){

        super("File organizer");
        this.controller = controller;

        // Interface
        JPanel panelRoot = new JPanel();
        panelRoot.setLayout(new BorderLayout());
        panelRoot.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panelRoot);

        // Select folder
        JPanel panelFolder = new JPanel();
        panelFolder.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        panelFolder.setLayout(new BorderLayout());

        // > IN
        JPanel panelInputFolder = new JPanel();
        panelInputFolder.setLayout(new BorderLayout());

        this.textInputFolder = new JTextField(/*MainWindow.NO_DIRECTORY_SELECTED*/"../sample/organized");
        this.textInputFolder.setEditable(false);

        JButton buttonBrowseInFolder = new JButton(MainWindow.BROWSE_BUTTON_TEXT);
        buttonBrowseInFolder.setToolTipText(MainWindow.BROWSE_INDIR_TOOLTIP);
        buttonBrowseInFolder.addActionListener(controller);

        panelInputFolder.add(new JLabel("Input:  "), BorderLayout.WEST);
        panelInputFolder.add(this.textInputFolder, BorderLayout.CENTER);
        panelInputFolder.add(buttonBrowseInFolder, BorderLayout.EAST);

        // > OUT
        JPanel panelOutputFolder = new JPanel();
        panelOutputFolder.setLayout(new BorderLayout());

        this.textOutputFolder = new JTextField(/*MainWindow.NO_DIRECTORY_SELECTED*/"../sample/result");
        this.textOutputFolder.setEditable(false);

        JButton buttonBrowseOutFolder = new JButton(MainWindow.BROWSE_BUTTON_TEXT);
        buttonBrowseOutFolder.setToolTipText(MainWindow.BROWSE_OUTDIR_TOOLTIP);
        buttonBrowseOutFolder.addActionListener(controller);

        panelOutputFolder.add(new JLabel("Output: "), BorderLayout.WEST);
        panelOutputFolder.add(this.textOutputFolder, BorderLayout.CENTER);
        panelOutputFolder.add(buttonBrowseOutFolder, BorderLayout.EAST);        

        panelFolder.add(panelInputFolder, BorderLayout.NORTH);
        panelFolder.add(panelOutputFolder, BorderLayout.SOUTH);

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

        this.setPreferredSize(new Dimension(400, 225));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void display(){
        this.pack();
        this.setVisible(true);
    }

    public void updateInputDirPath(String path){
        this.textInputFolder.setText(path);
    }

    public void updateOutputDirPath(String path){
        this.textOutputFolder.setText(path);
    }

    public String getInputDirPath(){
        return this.textInputFolder.getText();
    }

    public String getOutputDirPath(){
        return this.textOutputFolder.getText();
    }

    public int getMaxFiles(){
        return (int)this.spinnerMaxFiles.getValue();
    }

    public int getTolerance(){
        return (int)this.spinnerTolerence.getValue();
    }

    @Override
    public void update(Observable o, Object arg) {
        Model model = (Model) o;

        PreviewDialog dialog = new PreviewDialog(this, controller, model.getDisplayTree());
        dialog.display();
    }
}

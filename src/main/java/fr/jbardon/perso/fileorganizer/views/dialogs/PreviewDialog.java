package fr.jbardon.perso.fileorganizer.views.dialogs;

import fr.jbardon.perso.fileorganizer.controller.Controller;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by jeremy on 29/04/15.
 */
public class PreviewDialog extends JDialog implements PropertyChangeListener {

    public final static String BUTTON_APPLY_TEXT = "Apply";
    private JProgressBar progressCopy;
    private JButton buttonApply;


    public PreviewDialog(JFrame parent, Controller controller, DefaultMutableTreeNode folders) {
        super(parent, "File organization preview", true);

        // Interface
        JPanel panelRoot = new JPanel();
        panelRoot.setLayout(new BorderLayout());
        panelRoot.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panelRoot);

        // Tree
        JTree treeFolders = new JTree(folders);
        treeFolders.setMaximumSize(new Dimension(200, 100));
        JScrollPane scrollTree = new JScrollPane(treeFolders);

        panelRoot.add(scrollTree, BorderLayout.NORTH);

        // Progress bar
        progressCopy = new JProgressBar();
        panelRoot.add(progressCopy, BorderLayout.CENTER);

        // Bottom button
        buttonApply = new JButton(PreviewDialog.BUTTON_APPLY_TEXT);
        buttonApply.addActionListener(controller);
        panelRoot.add(buttonApply, BorderLayout.SOUTH);

        // End stuff
        this.setPreferredSize(new Dimension(300, 300));
    }

    public void display(){
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if("progress".equals(evt.getPropertyName())) {
            int progress = (Integer) evt.getNewValue();
            this.progressCopy.setValue(progress);
        }

        //this.getContentPane().setCursor(Cursor.getPredefinedCursor(
        //        (copyProgress[0] == copyProgress[1]) ? Cursor.DEFAULT_CURSOR : Cursor.WAIT_CURSOR
        //));
    }
}

package fr.jbardon.perso.fileorganizer.views.dialogs;

import fr.jbardon.perso.fileorganizer.controller.Controller;
import fr.jbardon.perso.fileorganizer.model.Model;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by jeremy on 29/04/15.
 */
public class PreviewDialog extends JDialog {

    public final static String BUTTON_APPLY_TEXT = "Apply";

    public PreviewDialog(JFrame parent, Controller controller, DefaultMutableTreeNode folders) {
        super(parent, "File organization preview", true);

        // Interface
        JPanel panelRoot = new JPanel();
        panelRoot.setLayout(new BorderLayout());
        panelRoot.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panelRoot);

        // Tree
        JTree treeFolders = new JTree(folders);
        //treeFolders.setMinimumSize(new Dimension(200, 100));
        JScrollPane scrollTree = new JScrollPane(treeFolders);

        panelRoot.add(scrollTree, BorderLayout.NORTH);

        // Progress bar
        JProgressBar progressCopy = new JProgressBar();
        panelRoot.add(progressCopy, BorderLayout.CENTER);

        // Bottom button
        JButton buttonApply = new JButton(PreviewDialog.BUTTON_APPLY_TEXT);
        buttonApply.addActionListener(controller);
        panelRoot.add(buttonApply, BorderLayout.SOUTH);

        // End stuff
        this.setPreferredSize(new Dimension(300, 300));
    }

    public void display(){
        this.pack();
        this.setVisible(true);
    }
}

package fr.jbardon.perso.fileorganizer.views.dialogs;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jeremy on 29/04/15.
 */
public class PreviewDialog extends JDialog {

    public PreviewDialog(JFrame parent) {
        super(parent, "File organization preview", true);

        // Interface
        JPanel panelRoot = new JPanel();
        panelRoot.setLayout(new BorderLayout());
        panelRoot.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panelRoot);

        // End stuff
        this.setPreferredSize(new Dimension(300, 300));
    }

    public void display(){
        this.pack();
        this.setVisible(true);
    }
}

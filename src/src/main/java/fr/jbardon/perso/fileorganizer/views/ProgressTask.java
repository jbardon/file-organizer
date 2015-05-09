package fr.jbardon.perso.fileorganizer.views;

import fr.jbardon.perso.fileorganizer.model.Model;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by jeremy on 09/05/15.
 */
public class ProgressTask extends SwingWorker implements Observer {

    private boolean stopTask = false;

    @Override
    protected Object doInBackground() throws Exception {
        while(!this.stopTask){
            Thread.sleep(2000);
        }
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        ObserverNotification notif = (ObserverNotification) arg;

        switch(notif){
            case COPY_PROGRESS:
                Model model = (Model) o;
                int[] copyProgress = model.getCopyProgress();

                this.setProgress(copyProgress[0]);

                if(copyProgress[0] == copyProgress[1]){
                    this.stopTask = true;
                }

                break;
        }
    }

    @Override
    public void done(){
        JOptionPane.showMessageDialog(null, "All files has been copied");
    }
}

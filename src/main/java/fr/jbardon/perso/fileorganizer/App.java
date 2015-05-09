package fr.jbardon.perso.fileorganizer;

import fr.jbardon.perso.fileorganizer.controller.Controller;
import fr.jbardon.perso.fileorganizer.views.MainWindow;

public class App 
{
    public static void main(String[] args) {
        Controller controller = new Controller();
        MainWindow window = new MainWindow(controller);
        controller.setGui(window);

        window.display();
    }
}

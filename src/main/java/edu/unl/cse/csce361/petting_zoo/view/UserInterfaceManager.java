package edu.unl.cse.csce361.petting_zoo.view;

import edu.unl.cse.csce361.petting_zoo.view.textview.TextUserInterface;

public class UserInterfaceManager {
    private static UserInterface ui = null;

    static void setUI(UserInterface userInterface) {
        ui = userInterface;
    }

    public static UserInterface getUI() {
        if (ui == null) {
            ui = new TextUserInterface();  // TODO: replace with dynamically-chosen UI
        }
        return ui;
    }
}

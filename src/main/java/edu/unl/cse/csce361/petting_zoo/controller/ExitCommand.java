package edu.unl.cse.csce361.petting_zoo.controller;

import edu.unl.cse.csce361.petting_zoo.view.UserInterfaceManager;

public class ExitCommand implements Command {

    @Override
    public void execute() {
        UserInterfaceManager.getUI().endGame();
    }

    @Override
    public String toString() {
        return "Exit game";
    }
}

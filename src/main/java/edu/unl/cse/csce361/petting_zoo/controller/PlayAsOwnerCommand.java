package edu.unl.cse.csce361.petting_zoo.controller;

import edu.unl.cse.csce361.petting_zoo.view.UserInterfaceManager;

import java.util.LinkedList;
import java.util.List;

public class PlayAsOwnerCommand implements Command {
    @Override
    public void execute() {
        List<Command> newMenu = new LinkedList<>();
        newMenu.add(new GoBackCommand());
        newMenu.addAll(PlayerMenus.ownerMenu);
        UserInterfaceManager.getUI().replaceCommands(newMenu);
    }

    @Override
    public String toString() {
        return "Play as petting zoo owner";
    }
}

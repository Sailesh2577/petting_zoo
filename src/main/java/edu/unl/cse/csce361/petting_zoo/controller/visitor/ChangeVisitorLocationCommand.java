package edu.unl.cse.csce361.petting_zoo.controller.visitor;

import edu.unl.cse.csce361.petting_zoo.controller.Command;
import edu.unl.cse.csce361.petting_zoo.controller.GoBackCommand;
import edu.unl.cse.csce361.petting_zoo.controller.PlayerMenus;
import edu.unl.cse.csce361.petting_zoo.view.UserInterfaceManager;

import java.util.LinkedList;
import java.util.List;

public class ChangeVisitorLocationCommand implements Command {

    @Override
    public void execute() {
        List<Command> newMenu = new LinkedList<>();
        newMenu.add(new GoBackCommand());
        newMenu.addAll(PlayerMenus.buyMenu);
        UserInterfaceManager.getUI().replaceCommands(newMenu);
    }

    @Override
    public String toString() {
        return "Buy an animal";
    }
}

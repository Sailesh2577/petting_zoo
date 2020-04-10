package edu.unl.cse.csce361.petting_zoo.controller.owner;

import edu.unl.cse.csce361.petting_zoo.controller.Command;
import edu.unl.cse.csce361.petting_zoo.controller.PlayAsOwnerCommand;
import edu.unl.cse.csce361.petting_zoo.model.PettingZoo;
import edu.unl.cse.csce361.petting_zoo.view.UserInterfaceManager;

import java.util.LinkedList;
import java.util.List;

public class BoughtAnimalSuccessfully implements Command{

    @Override
    public void execute() {
        List<Command> newMenu = new LinkedList<>();
        newMenu.add(new PlayAsOwnerCommand());
        UserInterfaceManager.getUI().replaceCommands(newMenu);
    }

    @Override
    public String toString() {
        return "You have successfully purchased your animal, the bank balance is now ¤" + PettingZoo.getPettingZoo().getBankBalance() + "\nPlease press 1 to return to Owner menu";
    }
}

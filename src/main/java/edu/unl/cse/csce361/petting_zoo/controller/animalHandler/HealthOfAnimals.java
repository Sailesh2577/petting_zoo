package edu.unl.cse.csce361.petting_zoo.controller.animalHandler;

import edu.unl.cse.csce361.petting_zoo.controller.Command;
import edu.unl.cse.csce361.petting_zoo.controller.GoBackCommand;
import edu.unl.cse.csce361.petting_zoo.view.UserInterfaceManager;

import java.util.LinkedList;
import java.util.List;

public class HealthOfAnimals implements Command {
    @Override
    public String toString() {
        return "Check health of the animals";
    }

    @Override
    public void execute() {
        List<Command> newMenu = new LinkedList<>();
        newMenu.add(new GoBackCommand());
        newMenu.addAll(AnimalHealthMenu.healthButtonsMenu);
        UserInterfaceManager.getUI().replaceCommands(newMenu);
    }
}
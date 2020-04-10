package edu.unl.cse.csce361.petting_zoo.controller.owner;

import edu.unl.cse.csce361.petting_zoo.controller.Command;
import edu.unl.cse.csce361.petting_zoo.controller.GoBackCommand;
import edu.unl.cse.csce361.petting_zoo.view.UserInterfaceManager;

import java.util.LinkedList;
import java.util.List;

public class SeeAnimalStatisticsCommand implements Command{
    //Todo: Add observer listener


    //following command pattern
    @Override
    public void execute(){
        //Use MVC Pattern to use the view to print the statistics
        //TextUserInterface.showInformation(PettingZoo.getPettingZoo().seeStatistics())
        List<Command> newMenu = new LinkedList<>();
        newMenu.add(new GoBackCommand());
        UserInterfaceManager.getUI().replaceCommands(newMenu);
    }
    @Override
    public String toString() {
        return "See animal Statistics";
    }
}

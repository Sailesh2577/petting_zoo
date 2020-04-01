package edu.unl.cse.csce361.petting_zoo.view.javafxview;

import edu.unl.cse.csce361.petting_zoo.controller.Command;
import javafx.scene.control.Button;

import java.util.HashMap;
import java.util.Map;

public class CommandButton extends Button {

    private static final Map<Command, CommandButton> commandMap = new HashMap<>();

    public static CommandButton getButtonFor(Command command) {
        if(!commandMap.containsKey(command)) {
            commandMap.put(command, new CommandButton(command));
        }
        return commandMap.get(command);
    }

    private Command command;

    private CommandButton(Command command) {
        super(command.toString());
        this.command = command;
        setOnAction(actionEvent -> this.command.execute());
    }
}

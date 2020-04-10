package edu.unl.cse.csce361.petting_zoo.controller;

import edu.unl.cse.csce361.petting_zoo.model.PettingZoo;

public class UpdateCommand implements Command {

    @Override
    public void execute() {
        PettingZoo.getPettingZoo().updateZooStatus();
    }

    @Override
    public String toString() {
        return "Update Zoo";
    }
}

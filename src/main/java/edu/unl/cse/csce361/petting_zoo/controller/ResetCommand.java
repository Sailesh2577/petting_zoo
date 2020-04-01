package edu.unl.cse.csce361.petting_zoo.controller;

import edu.unl.cse.csce361.petting_zoo.model.PettingZoo;

public class ResetCommand implements Command {
    @Override
    public void execute() {
        PettingZoo.resetPettingZoo(PettingZoo.getPettingZoo());
    }

    @Override
    public String toString() {
        return "Reset game";
    }
}

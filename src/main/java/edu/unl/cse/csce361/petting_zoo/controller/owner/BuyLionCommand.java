package edu.unl.cse.csce361.petting_zoo.controller.owner;

import edu.unl.cse.csce361.petting_zoo.controller.Command;
import edu.unl.cse.csce361.petting_zoo.model.AnimalBuilder;
import edu.unl.cse.csce361.petting_zoo.model.AnimalEntity;
import edu.unl.cse.csce361.petting_zoo.model.PettingZoo;

public class BuyLionCommand implements Command{
    @Override
    public void execute() {
        //Using chain calling and the builder pattern to create an animal
        AnimalEntity lion = (AnimalEntity) new AnimalBuilder("lion").setName("Cow R. D. Lee").setMale().build();
        lion.addObserver(PettingZoo.getPettingZoo());
        PettingZoo.getPettingZoo().buyAnimal(lion, lion.getPrice());
    }

    @Override
    public String toString() {
        return "Lion    : ¤100/kg";
    }
}

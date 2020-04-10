package edu.unl.cse.csce361.petting_zoo.controller.owner;

import edu.unl.cse.csce361.petting_zoo.controller.Command;
import edu.unl.cse.csce361.petting_zoo.model.AnimalBuilder;
import edu.unl.cse.csce361.petting_zoo.model.AnimalEntity;
import edu.unl.cse.csce361.petting_zoo.model.PettingZoo;

public class BuyTigerCommand implements Command{

    @Override
    public void execute() {
        //Using chain calling and the builder pattern to create an animal
        AnimalEntity tiger = (AnimalEntity) new AnimalBuilder("Tiger").setName("Tigress").setFemale().build();
        tiger.addObserver(PettingZoo.getPettingZoo());
        PettingZoo.getPettingZoo().buyAnimal(tiger, tiger.getPrice());
    }

    @Override
    public String toString() {
        return "Tiger   : ¤100/kg";
    }
}

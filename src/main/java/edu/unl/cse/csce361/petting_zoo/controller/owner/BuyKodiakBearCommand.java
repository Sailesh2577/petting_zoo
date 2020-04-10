package edu.unl.cse.csce361.petting_zoo.controller.owner;

import edu.unl.cse.csce361.petting_zoo.controller.Command;
import edu.unl.cse.csce361.petting_zoo.model.AnimalBuilder;
import edu.unl.cse.csce361.petting_zoo.model.AnimalEntity;
import edu.unl.cse.csce361.petting_zoo.model.PettingZoo;

public class BuyKodiakBearCommand implements Command{

    public void execute() {
        //Using chain calling and the builder pattern to create an animal
        AnimalEntity bear = (AnimalEntity) new AnimalBuilder("Kodiak_Bear").setName("Cody").setMale().build();
        bear.addObserver(PettingZoo.getPettingZoo());
        PettingZoo.getPettingZoo().buyAnimal(bear, bear.getPrice());
    }

    @Override
    public String toString() {
        return "Bear    : ¤75/kg";
    }
}

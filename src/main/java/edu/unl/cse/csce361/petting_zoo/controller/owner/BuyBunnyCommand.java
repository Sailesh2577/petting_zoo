package edu.unl.cse.csce361.petting_zoo.controller.owner;

import edu.unl.cse.csce361.petting_zoo.controller.Command;
import edu.unl.cse.csce361.petting_zoo.model.AnimalBuilder;
import edu.unl.cse.csce361.petting_zoo.model.AnimalEntity;
import edu.unl.cse.csce361.petting_zoo.model.PettingZoo;

public class BuyBunnyCommand implements Command{
    @Override
    public void execute() {
        //Using chain calling and the builder pattern to create an animal
        AnimalEntity bunny = (AnimalEntity) new AnimalBuilder("white_bunny").setName("Hoppy").setFemale().setCarnivore().build();
        bunny.addObserver(PettingZoo.getPettingZoo());
        PettingZoo.getPettingZoo().buyAnimal(bunny, bunny.getPrice());
    }

    @Override
    public String toString() {
        return "Bunny   : ¤1/kg";
    }
}

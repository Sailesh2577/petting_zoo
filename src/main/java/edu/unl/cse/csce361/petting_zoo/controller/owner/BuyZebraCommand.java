package edu.unl.cse.csce361.petting_zoo.controller.owner;

import edu.unl.cse.csce361.petting_zoo.controller.Command;
import edu.unl.cse.csce361.petting_zoo.model.AnimalBuilder;
import edu.unl.cse.csce361.petting_zoo.model.AnimalEntity;
import edu.unl.cse.csce361.petting_zoo.model.PettingZoo;

public class BuyZebraCommand implements Command{
    @Override
    public void execute() {
        //Using chain calling and the builder pattern to create an animal
        AnimalEntity zebra = (AnimalEntity) new AnimalBuilder("Zebra").setName("Marty").setFemale().build();
        zebra.addObserver(PettingZoo.getPettingZoo());
        PettingZoo.getPettingZoo().buyAnimal(zebra, zebra.getPrice());
    }
    @Override
    public String toString() {
        return "Zebra   : ¤80/kg";
    }
}

package edu.unl.cse.csce361.petting_zoo.controller.owner;

import edu.unl.cse.csce361.petting_zoo.controller.Command;
import edu.unl.cse.csce361.petting_zoo.model.AnimalBuilder;
import edu.unl.cse.csce361.petting_zoo.model.AnimalEntity;
import edu.unl.cse.csce361.petting_zoo.model.PettingZoo;

public class BuyGirrafeCommand implements Command{

    @Override
    public void execute() {
        //Using chain calling and the builder pattern to create an animal
        AnimalEntity giraffe = (AnimalEntity) new AnimalBuilder("giraffe").setName("Melman").setMale().build();
        giraffe.addObserver(PettingZoo.getPettingZoo());
        PettingZoo.getPettingZoo().buyAnimal(giraffe, giraffe.getPrice());
    }

    @Override
    public String toString() {
        return "Giraffe : ¤50/kg";
    }
}

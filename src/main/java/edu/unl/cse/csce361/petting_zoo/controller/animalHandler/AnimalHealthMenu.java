package edu.unl.cse.csce361.petting_zoo.controller.animalHandler;

import edu.unl.cse.csce361.petting_zoo.controller.Command;
import edu.unl.cse.csce361.petting_zoo.controller.animalHandler.healthButtons.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AnimalHealthMenu {

    public static final List<Command> healthButtonsMenu = Collections.unmodifiableList(Arrays.asList(
            new AnimalsLocationButton(),
               new AnimalsHungerButton(),
               new AnimalsStressButton(),
           new AnimalsAtTheBarnButton(),
            new AnimalsAtPettingEnclosureButton(),
            new AnimalsAtFeedingEnclosureButton(),
            new AnimalsAtViewingEnclosureButton()
    ));
}

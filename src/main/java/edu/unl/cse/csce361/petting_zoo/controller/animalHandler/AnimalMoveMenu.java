package edu.unl.cse.csce361.petting_zoo.controller.animalHandler;

import edu.unl.cse.csce361.petting_zoo.controller.Command;
import edu.unl.cse.csce361.petting_zoo.controller.animalHandler.healthButtons.*;
import edu.unl.cse.csce361.petting_zoo.controller.animalHandler.moveButtons.MoveAnimalsToFeedingEnclosureButton;
import edu.unl.cse.csce361.petting_zoo.controller.animalHandler.moveButtons.MoveAnimalsToPettingEnclosureButton;
import edu.unl.cse.csce361.petting_zoo.controller.animalHandler.moveButtons.MoveAnimalsToTheBarnButton;
import edu.unl.cse.csce361.petting_zoo.controller.animalHandler.moveButtons.MoveAnimalsToViewingEnclosureButton;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AnimalMoveMenu {

    public static final List<Command> moveButtonsMenu = Collections.unmodifiableList(Arrays.asList(
            new MoveAnimalsToTheBarnButton(),
            new MoveAnimalsToPettingEnclosureButton(),
            new MoveAnimalsToFeedingEnclosureButton(),
            new MoveAnimalsToViewingEnclosureButton()

    ));
}

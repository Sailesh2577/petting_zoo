package edu.unl.cse.csce361.petting_zoo.controller.animalHandler.healthButtons;

import edu.unl.cse.csce361.petting_zoo.controller.Command;
import edu.unl.cse.csce361.petting_zoo.model.AnimalEntity;
import edu.unl.cse.csce361.petting_zoo.model.HibernateUtil;
import edu.unl.cse.csce361.petting_zoo.view.UserInterfaceManager;
import org.hibernate.query.Query;

public class AnimalsHungerButton implements Command {


    @Override
    public String toString() {
        return "Hunger of Animals";
    }

    @Override
    public void execute() {
        Query query = HibernateUtil.getSession().createQuery("from AnimalEntity");
        StringBuilder outputStr = new StringBuilder();

        outputStr.append(toString());
        outputStr.append(": \n");
        for (Object result : query.getResultList()) {
            AnimalEntity animalEntityObject = (AnimalEntity) result;
            outputStr.append(animalEntityObject.getName());
            outputStr.append("'s hunger level is: ");
            outputStr.append(animalEntityObject.getHungerLevel());
            outputStr.append("\n");
        }
        UserInterfaceManager.getUI().showInformation(outputStr.toString());
    }
}




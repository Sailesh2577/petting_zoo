package edu.unl.cse.csce361.petting_zoo.controller.animalHandler.moveButtons;

import edu.unl.cse.csce361.petting_zoo.controller.Command;
import edu.unl.cse.csce361.petting_zoo.model.AnimalEntity;
import edu.unl.cse.csce361.petting_zoo.model.HibernateUtil;
import edu.unl.cse.csce361.petting_zoo.view.UserInterfaceManager;
import org.hibernate.query.Query;

public class MoveAnimalsToPettingEnclosureButton implements Command {


    @Override
    public String toString() {
        return "Move animals to Petting Enclosure";
    }

    @Override
    public void execute() {
        Query query = HibernateUtil.getSession().createQuery("from AnimalEntity");
        StringBuilder outputStr = new StringBuilder();
        int count = 0;
        outputStr.append(toString());
        outputStr.append(": \n");
        for (Object result : query.getResultList()) {
            AnimalEntity animalEntityObject = (AnimalEntity) result;
            if(animalEntityObject.getLocation().getName().equalsIgnoreCase("Petting Enclosure 1")) {
                count++;
                outputStr.append(count);
                outputStr.append(") ");
                outputStr.append(animalEntityObject.getName());
                outputStr.append(" of type: ");
                outputStr.append(animalEntityObject.getType());
                outputStr.append("\n");
            }
        }
        outputStr.append("\n");
        outputStr.append("Number of Animals at Petting Enclosure 1: ");
        outputStr.append(count);
        UserInterfaceManager.getUI().showInformation(outputStr.toString());
    }
}


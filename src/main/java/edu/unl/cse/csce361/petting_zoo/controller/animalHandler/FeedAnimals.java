package edu.unl.cse.csce361.petting_zoo.controller.animalHandler;

import edu.unl.cse.csce361.petting_zoo.controller.Command;
import edu.unl.cse.csce361.petting_zoo.model.AnimalEntity;
import edu.unl.cse.csce361.petting_zoo.model.HibernateUtil;
import edu.unl.cse.csce361.petting_zoo.model.PettingZoo;
import edu.unl.cse.csce361.petting_zoo.view.UserInterfaceManager;
import org.hibernate.query.Query;

public class FeedAnimals implements Command {
    @Override
    public String toString() {
        return "Feed the animals";
    }

    @Override
    public void execute() {
        Query query = HibernateUtil.getSession().createQuery("from AnimalEntity");
        StringBuilder outputStr = new StringBuilder();

        outputStr.append("Feeding the animals: \n");
        for (Object result : query.getResultList()) {
            AnimalEntity animalEntityObject = (AnimalEntity) result;
            outputStr.append("Feeding ");
            outputStr.append(animalEntityObject.getName());
            outputStr.append("\n");
        }
        outputStr.append("Fed all the animals!");
        UserInterfaceManager.getUI().showInformation(outputStr.toString());
        PettingZoo.feedAllAnimals(PettingZoo.getPettingZoo());
    }
}


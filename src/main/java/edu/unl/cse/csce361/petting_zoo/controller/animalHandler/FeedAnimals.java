package edu.unl.cse.csce361.petting_zoo.controller.animalHandler;

import edu.unl.cse.csce361.petting_zoo.controller.Command;
import edu.unl.cse.csce361.petting_zoo.model.AnimalEntity;
import edu.unl.cse.csce361.petting_zoo.model.HibernateUtil;
import edu.unl.cse.csce361.petting_zoo.model.PettingZoo;
import edu.unl.cse.csce361.petting_zoo.view.UserInterfaceManager;
import org.hibernate.query.Query;
import edu.unl.cse.csce361.petting_zoo.model.PettingZoo;


public class FeedAnimals implements Command {
    @Override
    public String toString() {
        return "Feed the animals";
    }

    @Override
    public void execute() {
        HibernateUtil.getSession().beginTransaction();
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
        javax.persistence.Query query1 = HibernateUtil.getSession().createQuery("UPDATE AnimalEntity SET hunger = hunger - 1");
        query1.executeUpdate();
        HibernateUtil.getSession().save(PettingZoo.getPettingZoo());
        HibernateUtil.getSession().getTransaction().commit();
    }
}


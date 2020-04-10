package edu.unl.cse.csce361.petting_zoo.controller.animalHandler.moveButtons;

import edu.unl.cse.csce361.petting_zoo.controller.Command;
import edu.unl.cse.csce361.petting_zoo.model.AnimalEntity;
import edu.unl.cse.csce361.petting_zoo.model.HibernateUtil;
import edu.unl.cse.csce361.petting_zoo.model.PettingZoo;
import edu.unl.cse.csce361.petting_zoo.view.UserInterfaceManager;
import org.hibernate.query.Query;

public class MoveAnimalsToPettingEnclosureButton implements Command {


    @Override
    public String toString() {
        return "Move animals to Petting Enclosure";
    }

    @Override
    public void execute() {
        HibernateUtil.getSession().beginTransaction();
        Query query = HibernateUtil.getSession().createQuery("from AnimalEntity");
        StringBuilder outputStr = new StringBuilder();

        outputStr.append("Moving the animals: \n");
        for (Object result : query.getResultList()) {
            AnimalEntity animalEntityObject = (AnimalEntity) result;
            outputStr.append("Moving ");
            outputStr.append(animalEntityObject.getName());
            outputStr.append("\n");
        }
        outputStr.append("Moved all the animals to Petting Enclosure 1!");
        UserInterfaceManager.getUI().showInformation(outputStr.toString());
        javax.persistence.Query query1 = HibernateUtil.getSession().createQuery("UPDATE AnimalEntity SET location_name = 'Petting Enclosure 1'");
        query1.executeUpdate();
        HibernateUtil.getSession().save(PettingZoo.getPettingZoo());
        HibernateUtil.getSession().getTransaction().commit();
    }
}


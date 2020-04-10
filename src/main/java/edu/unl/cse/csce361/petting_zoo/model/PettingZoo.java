package edu.unl.cse.csce361.petting_zoo.model;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unused")
@Entity
public class PettingZoo implements Observer {
    public static PettingZoo getPettingZoo() {
        PettingZoo zoo;
        Query query = HibernateUtil.getSession().createQuery("from PettingZoo");
        //noinspection rawtypes
        List results = query.getResultList();
        if (results.size() > 0) {
            zoo = (PettingZoo) results.get(0);
        } else {
            zoo = initializeZoo();
        }
        return zoo;
    }

    public static PettingZoo resetPettingZoo(PettingZoo oldZoo) {
        deleteZoo(oldZoo);
        return initializeZoo();
    }

    @Id
    @GeneratedValue
    private Integer id;

    /**
     * The petting zoo's liquid assets. The amount of Zorkmids the petting zoo has to spend on zoo business.
     * If the balance falls below ¤0 (alternatively, 0zm) then the petting zoo cannot feed the animals nor pay the
     * animal handlers.
     */
    @Column
    private int bankBalance;

    /**
     * How interested the public is in attending the petting zoo. Used to determine how many visitors visit.
     */
    @Column
    private double publicInterest;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zoo")
    private Set<AnimalEntity> animals;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zoo")
    private Set<LocationEntity> locations;

    @Column
    private Date lastUpdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(int bankBalance) {
        this.bankBalance = bankBalance;
    }

    public double getPublicInterest() {
        return publicInterest;
    }

    public void setPublicInterest(double publicInterest) {
        this.publicInterest = publicInterest;
    }

    public Set<AnimalEntity> getAnimals() {
        return Collections.unmodifiableSet(animals);
    }

    public void setAnimals(Set<AnimalEntity> animals) {
        this.animals = animals;
    }

    public Set<LocationEntity> getLocations() {
        return Collections.unmodifiableSet(locations);
    }

    public void setLocations(Set<LocationEntity> locations) {
        this.locations = locations;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void buyAnimal(Animal animal, double price) {
        LocationEntity stalls = locations.stream()
                .filter(location -> location.getType() == LocationType.STALLS)
                .collect(Collectors.toList()).get(0);
        HibernateUtil.getSession().beginTransaction();
        bankBalance -= price;
        animals.add((AnimalEntity) animal);
        ((AnimalEntity) animal).setZoo(this);
        ((AnimalEntity) animal).setLocation(stalls);
        HibernateUtil.getSession().save(this);
        HibernateUtil.getSession().save(animals);
        HibernateUtil.getSession().getTransaction().commit();
    }

    public void sellAnimal(Animal animal, int price) {
        HibernateUtil.getSession().beginTransaction();
        bankBalance += price;
        //noinspection RedundantCast
        HibernateUtil.getSession().remove((AnimalEntity) animal);
        HibernateUtil.getSession().getTransaction().commit();
    }

    public void updateZooStatus() {
        Date previousUpdate = lastUpdate;
        Date currentUpdate = new Date();
        HibernateUtil.getSession().beginTransaction();
        for (AnimalEntity animal : animals) {
            HibernateUtil.getSession().update(animal);
        }
        setBankBalance(bankBalance);        /* ******** REPLACE WITH A COMPUTATION BASED ON FORMULA **********/
        setPublicInterest(publicInterest);  /* ******** REPLACE WITH A COMPUTATION BASED ON FORMULA **********/
        setLastUpdate(currentUpdate);
        HibernateUtil.getSession().save(this);
        HibernateUtil.getSession().getTransaction().commit();
    }

    private static PettingZoo initializeZoo() {
        PettingZoo zoo;
        HibernateUtil.getSession().beginTransaction();
        zoo = new PettingZoo();
        zoo.setBankBalance(5000);
        zoo.setPublicInterest(10.0);
        zoo.setLastUpdate(new Date());
        LocationEntity theBarn = new LocationEntity("The Barn", LocationType.STALLS);
        Set<LocationEntity> initialLocations = Stream.of(
                theBarn,
                new LocationEntity("Petting Enclosure 1", LocationType.HANDS_ON),
                new LocationEntity("Feeding Enclosure 1", LocationType.HAND_FEEDING),
                new LocationEntity("Viewing Enclosure 1", LocationType.HANDS_OFF)
        ).collect(Collectors.toSet());
        zoo.setLocations(initialLocations);
        for (LocationEntity location: initialLocations) {
            location.setZoo(zoo);
        }
        //using chain calling to follow the builder pattern
        AnimalEntity owl = (AnimalEntity) new AnimalBuilder("brea_owl").setName("Hooter").setFemale().build();
        owl.addObserver(zoo);   //adding the observer for the observer pattern
        owl.setLocation(theBarn);
        zoo.setAnimals(Stream.of(owl).collect(Collectors.toSet()));
        owl.setZoo(zoo);
        HibernateUtil.getSession().save(zoo);
        HibernateUtil.getSession().getTransaction().commit();
        return zoo;
    }

    private static void deleteZoo(PettingZoo zoo) {
        HibernateUtil.getSession().beginTransaction();
        for (AnimalEntity animal : zoo.getAnimals()) {
            HibernateUtil.getSession().remove(animal);
        }
        for (LocationEntity location : zoo.getLocations()) {
            HibernateUtil.getSession().remove(location);
        }
        HibernateUtil.getSession().remove(zoo);
        HibernateUtil.getSession().getTransaction().commit();
    }

    //the Observer for the observer pattern

    @Override
    public void update(Observable observable, Object o) {
        //Should print but can't print in model area.
    }
}

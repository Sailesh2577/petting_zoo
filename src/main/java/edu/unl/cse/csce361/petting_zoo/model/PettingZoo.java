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
        System.out.println("Inside reset");
        System.out.println(oldZoo);
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
        System.out.println("Inside initialize");
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

        AnimalEntity owl = (AnimalEntity) new AnimalBuilder("brea_owl").setName("Hooter").setSex(AnimalEntity.Sex.FEMALE).build();
        owl.setLocation(theBarn);
        owl.setZoo(zoo);

        AnimalEntity mammoth = (AnimalEntity) new AnimalBuilder("Mammoth").setName("Mamu").setSex(AnimalEntity.Sex.MALE).build();
        mammoth.setLocation(theBarn);
        mammoth.setZoo(zoo);

        AnimalEntity sloth = (AnimalEntity) new AnimalBuilder("sloth").setName("slou").setSex(AnimalEntity.Sex.FEMALE).build();
        sloth.setLocation(theBarn);
        sloth.setZoo(zoo);

        AnimalEntity saberToothedCat = (AnimalEntity) new AnimalBuilder("Saber-Toothed Cat").setName("Sabu").setSex(AnimalEntity.Sex.MALE).build();
        saberToothedCat.setLocation(theBarn);
        saberToothedCat.setZoo(zoo);

        AnimalEntity direWolf = (AnimalEntity) new AnimalBuilder("Dire Wolf").setName("Dolf").setSex(AnimalEntity.Sex.MALE).build();
        direWolf.setLocation(theBarn);
        direWolf.setZoo(zoo);

        zoo.setAnimals(Stream.of(owl, mammoth, sloth, saberToothedCat, direWolf).collect(Collectors.toSet()));
        HibernateUtil.getSession().save(zoo);
        HibernateUtil.getSession().getTransaction().commit();

//        AnimalEntity owl = (AnimalEntity) new AnimalBuilder("brea_owl").build();
//        owl.setName("Hooter");                          /* ********     ELIMINATE THESE LINES      **********/
//        owl.setSex(AnimalEntity.Sex.FEMALE);            /* ******** WITH ANIMALBUILDER CHAIN CALLS **********/
//        owl.setLocation(theBarn);
//        zoo.setAnimals(Stream.of(owl).collect(Collectors.toSet()));
//        owl.setZoo(zoo);
//        HibernateUtil.getSession().save(zoo);
//        HibernateUtil.getSession().getTransaction().commit();
//
//        HibernateUtil.getSession().beginTransaction();
//        AnimalEntity mammoth = (AnimalEntity) new AnimalBuilder("Mammoth").build();
//        mammoth.setName("Mamu");                          /* ********     ELIMINATE THESE LINES      **********/
//        mammoth.setSex(AnimalEntity.Sex.MALE);            /* ******** WITH ANIMALBUILDER CHAIN CALLS **********/
//        mammoth.setLocation(theBarn);
//        zoo.setAnimals(Stream.of(mammoth).collect(Collectors.toSet()));
//        mammoth.setZoo(zoo);
//        HibernateUtil.getSession().save(zoo);
//        HibernateUtil.getSession().getTransaction().commit();
//
//        HibernateUtil.getSession().beginTransaction();
//        AnimalEntity sloth = (AnimalEntity) new AnimalBuilder("Sloth").build();
//        sloth.setName("Slou");                          /* ********     ELIMINATE THESE LINES      **********/
//        sloth.setSex(AnimalEntity.Sex.FEMALE);            /* ******** WITH ANIMALBUILDER CHAIN CALLS **********/
//        sloth.setLocation(theBarn);
//        zoo.setAnimals(Stream.of(sloth).collect(Collectors.toSet()));
//        sloth.setZoo(zoo);
//        HibernateUtil.getSession().save(zoo);
//        HibernateUtil.getSession().getTransaction().commit();
//
//        HibernateUtil.getSession().beginTransaction();
//        AnimalEntity saberToothedCat = (AnimalEntity) new AnimalBuilder("Saber-Toothed Cat").build();
//        saberToothedCat.setName("Sabu");                          /* ********     ELIMINATE THESE LINES      **********/
//        saberToothedCat.setSex(AnimalEntity.Sex.FEMALE);            /* ******** WITH ANIMALBUILDER CHAIN CALLS **********/
//        saberToothedCat.setLocation(theBarn);
//        zoo.setAnimals(Stream.of(saberToothedCat).collect(Collectors.toSet()));
//        saberToothedCat.setZoo(zoo);
//        HibernateUtil.getSession().save(zoo);
//        HibernateUtil.getSession().getTransaction().commit();
//
//        HibernateUtil.getSession().beginTransaction();
//        AnimalEntity direWolf = (AnimalEntity) new AnimalBuilder("Dire Wolf").build();
//        direWolf.setName("Dolf");                          /* ********     ELIMINATE THESE LINES      **********/
//        direWolf.setSex(AnimalEntity.Sex.MALE);            /* ******** WITH ANIMALBUILDER CHAIN CALLS **********/
//        direWolf.setLocation(theBarn);
//        zoo.setAnimals(Stream.of(direWolf).collect(Collectors.toSet()));
//        direWolf.setZoo(zoo);
//        HibernateUtil.getSession().save(zoo);
//        HibernateUtil.getSession().getTransaction().commit();
        return zoo;
    }

    private static void deleteZoo(PettingZoo zoo) {
        System.out.println("Inside Delete");
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

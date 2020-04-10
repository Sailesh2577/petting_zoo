package edu.unl.cse.csce361.petting_zoo.model;

import javax.persistence.*;
import java.util.Observable;
import java.util.Optional;

@Entity
public class AnimalEntity extends Observable implements Animal {
    @Id
    @GeneratedValue
    private int tagNumber;

    @Column
    private String name;

    @Column
    private AnimalType type;

    @Column
    private Sex sex;

    @Column
    private int massInKg;

    @Column
    private double price;

    @Column
    private String species;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private LocationEntity location;                // depends on concretion for database purposes

    @Column
    private String reactionToPetting;

    @Column
    private String reactionToFeeding;

    @Column
    private String reactionToWatching;

    public AnimalEntity() {                                         // 0-argument constructor
    }

    public AnimalEntity(String name, AnimalType type, Sex sex,      // convenience constructor
                        int massInKg, double price, String species,
                        String reactionToPetting, String reactionToFeeding, String reactionToWatching) {
        setName(name);
        setType(type);
        setSex(sex);
        setMassInKg(massInKg);
        setPrice(price);
        setSpecies(species);
        setReactionToPetting(reactionToPetting);
        setReactionToFeeding(reactionToFeeding);
        setReactionToWatching(reactionToWatching);
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private PettingZoo zoo;

    @Override
    public int getTagNumber() {
        return tagNumber;
    }

    @SuppressWarnings("unused")
    public void setTagNumber(int tagNumber) {
        this.tagNumber = tagNumber;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AnimalType getType() {
        return type;
    }

    public void setType(AnimalType type) {
        this.type = type;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    @Override
    public int getMassInKg() {
        return massInKg;
    }

    public void setMassInKg(int massInKg) {
        this.massInKg = massInKg;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    @Override
    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    @Override
    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }

    public String getReactionToPetting() {
        return reactionToPetting;
    }

    public void setReactionToPetting(String reactionToPetting) {
        this.reactionToPetting = reactionToPetting;
    }

    public String getReactionToFeeding() {
        return reactionToFeeding;
    }

    public void setReactionToFeeding(String reactionToFeeding) {
        this.reactionToFeeding = reactionToFeeding;
    }

    public String getReactionToWatching() {
        return reactionToWatching;
    }

    public void setReactionToWatching(String reactionToWatching) {
        this.reactionToWatching = reactionToWatching;
    }

    public PettingZoo getZoo() {
        return zoo;
    }

    public void setZoo(PettingZoo zoo) {
        this.zoo = zoo;
    }

    @Override
    public boolean isMale() {
        return getSex() == Sex.MALE;
    }

    @Override
    public boolean isFemale() {
        return getSex() == Sex.FEMALE;
    }

    @Override
    public boolean isCarnivore() {
        return getType() == AnimalType.CARNIVORE;
    }

    @Override
    public boolean isHerbivore() {
        return getType() == AnimalType.HERBIVORE;
    }

    protected String getSubjectivePersonalPronoun() {
        if (isFemale()) return "She";
        if (isMale()) return "He";
        return "It";
    }

    protected String getObjectivePersonalPronoun() {
        if (isFemale()) return "Her";
        if (isMale()) return "Him";
        return "It";
    }

    @SuppressWarnings("unused")
    protected String getPossessivePronoun() {
        if (isMale()) return "His";
        if (isFemale()) return "Her";
        return "Its";
    }

    @Override
    public String pet() {
        String defaultResponse =
                getSubjectivePersonalPronoun() + " lets you pet " + getObjectivePersonalPronoun().toLowerCase() + ".";
        return Optional.ofNullable(getReactionToPetting()).orElse(defaultResponse);
    }

    @Override
    public String feed() {
        String defaultResponse = getSubjectivePersonalPronoun() + " eats.";
        return Optional.ofNullable(getReactionToFeeding()).orElse(defaultResponse);
    }

    @Override
    public String watch() {
        String defaultResponse = getSubjectivePersonalPronoun() + " does what " + getSpecies() + "s do.";
        return Optional.ofNullable(getReactionToWatching()).orElse(defaultResponse);
    }

    @Override
    public void moveTo(Location newLocation) {
        HibernateUtil.getSession().beginTransaction();
        @SuppressWarnings("unused") LocationEntity oldLocation = location;
        location = (LocationEntity) newLocation;
        HibernateUtil.getSession().save(this);
        HibernateUtil.getSession().getTransaction().commit();
    }

    @Override
    public String toString() {
        return getName() + " the " + getSpecies();
    }

    public enum AnimalType {
        UNKNOWN,    // default value
        HERBIVORE,
        CARNIVORE
    }

    public enum Sex {
        UNKNOWN,    // default value
        FEMALE,
        MALE
    }
}

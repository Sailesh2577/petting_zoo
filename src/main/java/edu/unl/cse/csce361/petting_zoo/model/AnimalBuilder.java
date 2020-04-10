package edu.unl.cse.csce361.petting_zoo.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;
import java.util.Properties;

public class AnimalBuilder extends Observable {
    private AnimalEntity.AnimalType type;
    private AnimalEntity.Sex sex;
    private int massInKg;
    private String name;
    private String species;
    private double pricePerKg;
    private String reactionToPetting;
    private String reactionToFeeding;
    private String reactionToWatching;
    private Integer hunger;
    private Integer stress;
    public AnimalBuilder() {
        setVacuousDefaults();
    }

    /**
     * Retrieves species defaults from archetype file.
     * @param species   snake_case name of species whose archetype will be loaded
     */
    public AnimalBuilder(String species) {
        String speciesWithSpaces = species.replace("_", " ");
        try (InputStream input = AnimalBuilder.class.getClassLoader()
                .getResourceAsStream("archetypes/" + species + ".properties")) {
            Properties archetype = new Properties();
            if (input != null) {
                archetype.load(input);
                this.species = speciesWithSpaces;
                name = "the " + speciesWithSpaces;
                massInKg = Integer.parseInt(archetype.getProperty("massInKg"));
                pricePerKg = Integer.parseInt(archetype.getProperty("pricePerKg"));
                hunger = Integer.parseInt(archetype.getProperty("hunger"));
                stress = Integer.parseInt(archetype.getProperty("stress"));

                switch (archetype.getProperty("type").toLowerCase()) {
                    case "carnivore":
                        type = AnimalEntity.AnimalType.CARNIVORE;
                        break;
                    case "herbivore":
                        type = AnimalEntity.AnimalType.HERBIVORE;
                        break;
                    default:
                        type = AnimalEntity.AnimalType.UNKNOWN;
                        break;
                }
                sex = AnimalEntity.Sex.UNKNOWN;
                reactionToPetting = archetype.getProperty("reactionToPetting");
                reactionToFeeding = archetype.getProperty("reactionToFeeding");
                reactionToWatching = archetype.getProperty("reactionToWatching");
            } else {
                System.err.println("Unable to find archetype for " + species + ". Using vacuous defaults.");
                setVacuousDefaults();
                this.species = speciesWithSpaces;
                name = "the nondescript " + speciesWithSpaces;
            }
        } catch (IOException exception) {
            System.err.println("Encountered " + exception + ". Using vacuous defaults.");
            setVacuousDefaults();
            this.species = speciesWithSpaces;
            name = "the nondescript" + speciesWithSpaces;
        }
    }


    private void setVacuousDefaults() {
        type = AnimalEntity.AnimalType.UNKNOWN;
        sex = AnimalEntity.Sex.UNKNOWN;
        massInKg = 1;
        pricePerKg = 100;
        name = "that animal";
        species = "unknown";
        hunger = 10;
        stress = 20;
        reactionToPetting = reactionToFeeding = reactionToWatching = null;
    }

    public Animal build() {
        return new AnimalEntity(name, type, sex, massInKg, ((double)pricePerKg*(double)massInKg), species,
                reactionToPetting, reactionToFeeding, reactionToWatching, hunger, stress);
    }

    public AnimalEntity.AnimalType getType() {
        return type;
    }

    public AnimalBuilder setType(AnimalEntity.AnimalType type) {
        this.type = type;
        return this;
    }

    /*The following is the using the builder and the observer pattern
        the builder pattern allows for chain calling when creating an animal
        the observer pattern calls notifyObservers that an update has been changed when appropriate
     */

    public AnimalBuilder setCarnivore(){
        //if there has been a change then notify the observer
        if(this.type != AnimalEntity.AnimalType.CARNIVORE) {
            //mark value as changed
            setChanged();
            notifyObservers(type);
        }
        this.type = AnimalEntity.AnimalType.CARNIVORE;
        return this;
    }

    public AnimalBuilder setHerbivore(){
        if(this.type != AnimalEntity.AnimalType.HERBIVORE) {
            //mark value as changed
            setChanged();
            notifyObservers(type);
        }
        this.type = AnimalEntity.AnimalType.HERBIVORE;
        return this;
    }

    public AnimalBuilder setMale(){
        if(this.sex != AnimalEntity.Sex.MALE) {
            //mark value as changed
            setChanged();
            notifyObservers(sex);
        }
            this.sex = AnimalEntity.Sex.MALE;
            return this;
    }

    public AnimalBuilder setFemale(){
        if(this.sex != AnimalEntity.Sex.FEMALE) {
            //mark value as changed
            setChanged();
            notifyObservers(sex);
        }
        this.sex = AnimalEntity.Sex.FEMALE;
        return this;
    }

    public AnimalBuilder setMassInKg(int massInKg){

        if(this.massInKg != massInKg) {
            //mark value as changed
            setChanged();
            notifyObservers(massInKg);
        }
        this.massInKg = massInKg;
        return this;
    }

    /*Note: we don't need a set price because price is determined based on other inputs*/

    public AnimalBuilder setPricePerKg(double pricePerKg){

        if(this.pricePerKg != pricePerKg) {
            //mark value as changed
            setChanged();
            notifyObservers(pricePerKg);
        }

        this.pricePerKg = pricePerKg;
        return this;
    }

    public AnimalBuilder setName(String name) {

        if(!this.name.equals(name)) {
            //mark value as changed
            setChanged();
            notifyObservers(name);
        }

        this.name = name;
        return this;
    }

    public int getHungerLevel() {
        return hunger;
    }

    public int getStressLevel() {
        return stress;
    }

    public void setHungerLevel(int hunger) {this.hunger = hunger; }

    public void setStressLevel(int stress) {this.stress = stress; }

    public AnimalBuilder setSpecies(String species){

        if(!this.species.equals(species)) {
            //mark value as changed
            setChanged();
            notifyObservers(species);
        }

        this.species = species;
        return this;
    }

    public AnimalBuilder setReactionToPetting(String reaction){

        if(!this.reactionToPetting.equals(reaction)) {
            //mark value as changed
            setChanged();
            notifyObservers(reaction);
        }

        this.reactionToPetting = reaction;
        return this;
    }

    public AnimalBuilder setReactionToFeeding(String reaction){

        if(!this.reactionToFeeding.equals(reaction)) {
            //mark value as changed
            setChanged();
            notifyObservers(reaction);
        }

        this.reactionToFeeding = reaction;
        return this;
    }

    public AnimalBuilder setReactionToWatching(String reaction){

        if(!this.reactionToWatching.equals(reaction)) {
            //mark value as changed
            setChanged();
            notifyObservers(reaction);
        }

        this.reactionToWatching = reaction;
        return this;
    }



}

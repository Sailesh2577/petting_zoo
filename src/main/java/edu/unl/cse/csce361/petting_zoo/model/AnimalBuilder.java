package edu.unl.cse.csce361.petting_zoo.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AnimalBuilder {
    private AnimalEntity.AnimalType type;
    private AnimalEntity.Sex sex;
    private int massInKg;
    private String name;
    private String species;
    private int pricePerKg;
    private String reactionToPetting;
    private String reactionToFeeding;
    private String reactionToWatching;

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
                reactionToPetting = reactionToFeeding = reactionToWatching = null;
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
        reactionToPetting = reactionToFeeding = reactionToWatching = null;
    }

    public Animal build() {
        return new AnimalEntity(name, type, sex, massInKg, species,
                reactionToPetting, reactionToFeeding, reactionToWatching);
    }
}

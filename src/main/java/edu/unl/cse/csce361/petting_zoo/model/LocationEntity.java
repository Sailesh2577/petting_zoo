package edu.unl.cse.csce361.petting_zoo.model;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
public class LocationEntity implements Location {
    @Id
    @Column(length = 128)
    private String name;

    @Column
    private LocationType type;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "location")
    private Set<AnimalEntity> fauna;                            // depends on concretion for database purposes

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private PettingZoo zoo;

    public LocationEntity() {                                   // 0-argument constructor
    }

    public LocationEntity(String name, LocationType type) {     // convenience constructor
        setName(name);
        setType(type);
        setFauna(new HashSet<>());
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public LocationType getType() {
        return type;
    }

    public void setType(LocationType type) {
        this.type = type;
    }

    public Set<AnimalEntity> getFauna() {
        return Collections.unmodifiableSet(fauna);
    }

    public void setFauna(Set<AnimalEntity> animals) {
        this.fauna = animals;
    }

    public PettingZoo getZoo() {
        return zoo;
    }

    public void setZoo(PettingZoo zoo) {
        this.zoo = zoo;
    }

    @Override
    public Set<Animal> getAnimals() {
        return Collections.unmodifiableSet(getFauna());
    }

    @Override
    public String toString() {
        return getName() + " (" + getType() + ")";
    }
}

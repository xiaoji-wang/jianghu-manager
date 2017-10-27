package models.character;

import models.thing.ThingDrop;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "monster")
public class Monster extends Attributes {
    private Long id;
    private String name;
    private String description;
    private String word;
    private Set<ThingDrop> thingDrops = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "monster_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "monster")
    public Set<ThingDrop> getThingDrops() {
        return thingDrops;
    }

    public void setThingDrops(Set<ThingDrop> thingDrops) {
        this.thingDrops = thingDrops;
    }
}

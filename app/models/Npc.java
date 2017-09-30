package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "npc")
public class Npc extends Character {
    private Long id;
    private String name;
    private String title;
    private String description;
    private String word;
    private Boolean attackAble = false;
    private SceneCell sceneCell;
    private Set<ThingDrop> thingDrops = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "npc_id")
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scene_cell_id")
    public SceneCell getSceneCell() {
        return sceneCell;
    }

    public void setSceneCell(SceneCell sceneCell) {
        this.sceneCell = sceneCell;
    }

    @Column(name = "attack_able")
    public Boolean getAttackAble() {
        return attackAble;
    }

    public void setAttackAble(Boolean attackAble) {
        this.attackAble = attackAble;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//
//    public Set<ThingDrop> getThings() {
//        return things;
//    }
//
//    public void setThings(Set<ThingDrop> things) {
//        this.things = things;
//    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "npc")
    public Set<ThingDrop> getThingDrops() {
        return thingDrops;
    }

    public void setThingDrops(Set<ThingDrop> thingDrops) {
        this.thingDrops = thingDrops;
    }
}

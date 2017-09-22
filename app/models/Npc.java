package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "npc")
public class Npc extends Character {
    private String name;
    private String description;
    private String word;
    private Boolean attackAble = false;
    private SceneCell sceneCell;

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
}

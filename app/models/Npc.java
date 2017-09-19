package models;

import javax.persistence.*;

@Entity
@Table(name = "npc")
public class Npc extends Character{
    private String name;
    private String description;
    private String word;

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
}

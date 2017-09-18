package models;

import javax.persistence.*;

@Entity
@Table(name = "npc")
public class Npc {
    private Long id;
    private String name;
    private String description;
    private String word;
    private SceneCell sceneCell;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scene_cell_id")
    public SceneCell getSceneCell() {
        return sceneCell;
    }

    public void setSceneCell(SceneCell sceneCell) {
        this.sceneCell = sceneCell;
    }
}

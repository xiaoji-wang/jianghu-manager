package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "scene_cell")
public class SceneCell {
    private Long id;
    private String name;
    private String description;
    private String color;
    private Integer x;
    private Integer y;
    private Boolean arrive;
    private Scene scene;
    private Set<Npc> npcs = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scene_cell_id")
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Boolean getArrive() {
        return arrive;
    }

    public void setArrive(Boolean arrive) {
        this.arrive = arrive;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scene_id")
    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sceneCell")
    public Set<Npc> getNpcs() {
        return npcs;
    }

    public void setNpcs(Set<Npc> npcs) {
        this.npcs = npcs;
    }
}

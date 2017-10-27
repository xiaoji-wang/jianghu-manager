package models.scene;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "scene")
public class Scene {
    private Long id;
    private String name;
    private String description;
    private Integer width;
    private Integer height;
    //    private Integer layer;
    private Set<SceneCell> sceneCells = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scene_id")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "scene")
    public Set<SceneCell> getSceneCells() {
        return sceneCells;
    }

    public void setSceneCells(Set<SceneCell> sceneCells) {
        this.sceneCells = sceneCells;
    }

//    public Integer getLayer() {
//        return layer;
//    }
//
//    public void setLayer(Integer layer) {
//        this.layer = layer;
//    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}

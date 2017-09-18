package models;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scene_id")
    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}

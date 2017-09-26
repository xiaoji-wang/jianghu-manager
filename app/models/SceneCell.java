package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "scene_cell")
public class SceneCell {
    private Long id;
    private String name;
    private String description;
    private String color;
    private Boolean arrive;
    private Integer layer;
    private Long east;
    private Long northEast;
    private Long northWest;
    private Long west;
    private Long southWest;
    private Long southEast;
    private Scene scene;
//    private Set<Npc> npcs = new HashSet<>();

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

//    public Integer getX() {
//        return x;
//    }
//
//    public void setX(Integer x) {
//        this.x = x;
//    }
//
//    public Integer getY() {
//        return y;
//    }
//
//    public void setY(Integer y) {
//        this.y = y;
//    }

    @Column(name = "east_id")
    public Long getEast() {
        return east;
    }

    public void setEast(Long east) {
        this.east = east;
    }

    @Column(name = "north_east_id")
    public Long getNorthEast() {
        return northEast;
    }

    public void setNorthEast(Long northEast) {
        this.northEast = northEast;
    }

    @Column(name = "north_west_id")
    public Long getNorthWest() {
        return northWest;
    }

    public void setNorthWest(Long northWest) {
        this.northWest = northWest;
    }

    @Column(name = "west_id")
    public Long getWest() {
        return west;
    }

    public void setWest(Long west) {
        this.west = west;
    }

    @Column(name = "south_west_id")
    public Long getSouthWest() {
        return southWest;
    }

    public void setSouthWest(Long southWest) {
        this.southWest = southWest;
    }

    @Column(name = "south_east_id")
    public Long getSouthEast() {
        return southEast;
    }

    public void setSouthEast(Long southEast) {
        this.southEast = southEast;
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

    public Integer getLayer() {
        return layer;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sceneCell")
//    public Set<Npc> getNpcs() {
//        return npcs;
//    }
//
//    public void setNpcs(Set<Npc> npcs) {
//        this.npcs = npcs;
//    }
}

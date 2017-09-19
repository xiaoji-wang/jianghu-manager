package models;

import javax.persistence.*;

@Entity
@Table(name = "thing")
public class Thing {
    private Long id;
    private String name;
    private String description;
    private Integer price;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thing_id")
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}

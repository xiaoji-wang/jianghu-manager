package models.kung.fu;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wxji on 2017-10-23.
 */
@Entity
@Table(name = "kung_fu")
public class KungFu {
    private Long id;
    private String name;
    private String description;
    private Set<KungFuAct> kungFuActs = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kung_fu_id")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "kungFu")
    public Set<KungFuAct> getKungFuActs() {
        return kungFuActs;
    }

    public void setKungFuActs(Set<KungFuAct> kungFuActs) {
        this.kungFuActs = kungFuActs;
    }
}

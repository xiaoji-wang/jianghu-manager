package models.calculation;

import javax.persistence.*;

/**
 * Created by wxji on 2017-10-27.
 */
@Entity
@Table(name = "formula")
public class Formula {
    private Long id;
    private Integer initial = 1;
    private Integer coefficient = 1;
    private Type type;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "formula_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInitial() {
        return initial;
    }

    public void setInitial(Integer initial) {
        this.initial = initial;
    }

    public Integer getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Integer coefficient) {
        this.coefficient = coefficient;
    }

    @Enumerated(EnumType.STRING)
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        LINEAR, INDEX, LOGARITHM
    }
}

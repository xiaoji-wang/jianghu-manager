package models.character;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

/**
 * Created by wxji on 2017-09-19.
 */
@MappedSuperclass
public abstract class Attributes {
    private Integer level;
    private Integer hp;
    private Integer exp;
    private Integer minAttack;
    private Integer maxAttack;
    private Integer minDefense;
    private Integer maxDefense;
    private BigDecimal critRate;
    private BigDecimal dodgeRate;

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    @Column(name = "min_attack")
    public Integer getMinAttack() {
        return minAttack;
    }

    public void setMinAttack(Integer minAttack) {
        this.minAttack = minAttack;
    }

    @Column(name = "max_attack")
    public Integer getMaxAttack() {
        return maxAttack;
    }

    public void setMaxAttack(Integer maxAttack) {
        this.maxAttack = maxAttack;
    }

    @Column(name = "min_defense")
    public Integer getMinDefense() {
        return minDefense;
    }

    public void setMinDefense(Integer minDefense) {
        this.minDefense = minDefense;
    }

    @Column(name = "max_defense")
    public Integer getMaxDefense() {
        return maxDefense;
    }

    public void setMaxDefense(Integer maxDefense) {
        this.maxDefense = maxDefense;
    }

    @Column(name = "crit_rate")
    public BigDecimal getCritRate() {
        return critRate;
    }

    public void setCritRate(BigDecimal critRate) {
        this.critRate = critRate;
    }

    @Column(name = "dodge_rate")
    public BigDecimal getDodgeRate() {
        return dodgeRate;
    }

    public void setDodgeRate(BigDecimal dodgeRate) {
        this.dodgeRate = dodgeRate;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "drop_thing",
//            joinColumns = {@JoinColumn(name = "character_id", referencedColumnName = "character_id")},
//            inverseJoinColumns = {@JoinColumn(name = "thing_id", referencedColumnName = "thing_id")})
//    public Set<Thing> getDropThings() {
//        return dropThings;
//    }
//
//    public void setDropThings(Set<Thing> dropThings) {
//        this.dropThings = dropThings;
//    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "character")
//    public Set<ThingDrop> getThings() {
//        return things;
//    }
//
//    public void setThings(Set<ThingDrop> things) {
//        this.things = things;
//    }
}

package models.kung.fu;

import models.calculation.Formula;

import javax.persistence.*;

/**
 * Created by wxji on 2017-10-23.
 */
@Entity
@Table(name = "kung_fu_act")
public class KungFuAct {
    private Long id;
    private KungFu kungFu;
    private String name;
    private String description;
    private Boolean isAOE = false;
    private Boolean isHeal = false;
    private Boolean isSustained = false;
    private Boolean isDistance = false;
    private Integer cd = 0;
    private Integer sequence = 0;
    private Formula value;
    private Formula ki;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kung_fu_act_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kung_fu_id")
    public KungFu getKungFu() {
        return kungFu;
    }

    public void setKungFu(KungFu kungFu) {
        this.kungFu = kungFu;
    }

    @Column(name = "is_aoe")
    public Boolean getAOE() {
        return isAOE;
    }

    public void setAOE(Boolean AOE) {
        isAOE = AOE;
    }

    @Column(name = "is_heal")
    public Boolean getHeal() {
        return isHeal;
    }

    public void setHeal(Boolean heal) {
        isHeal = heal;
    }

    @Column(name = "is_sustained")
    public Boolean getSustained() {
        return isSustained;
    }

    public void setSustained(Boolean sustained) {
        isSustained = sustained;
    }

    public Integer getCd() {
        return cd;
    }

    public void setCd(Integer cd) {
        this.cd = cd;
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

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    @Column(name = "is_distance")
    public Boolean getDistance() {
        return isDistance;
    }

    public void setDistance(Boolean distance) {
        isDistance = distance;
    }

    @OneToOne
    @JoinColumn(name = "value")
    public Formula getValue() {
        return value;
    }

    public void setValue(Formula value) {
        this.value = value;
    }

    @OneToOne
    @JoinColumn(name = "ki")
    public Formula getKi() {
        return ki;
    }

    public void setKi(Formula ki) {
        this.ki = ki;
    }
}

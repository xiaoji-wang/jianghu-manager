package models.kung.fu;

import javax.persistence.*;

/**
 * Created by wxji on 2017-10-23.
 */
@Entity
@Table(name = "character_kung_fu_act")
public class CharacterKungFuAct {
    private Long id;
    private CharacterKungFu characterKungFu;
    private KungFuAct kungFuAct;
    private String value;
    private Integer ki;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_kung_fu_act_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_kung_fu_id")
    public CharacterKungFu getCharacterKungFu() {
        return characterKungFu;
    }

    public void setCharacterKungFu(CharacterKungFu characterKungFu) {
        this.characterKungFu = characterKungFu;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kung_fu_act_id")
    public KungFuAct getKungFuAct() {
        return kungFuAct;
    }

    public void setKungFuAct(KungFuAct kungFuAct) {
        this.kungFuAct = kungFuAct;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getKi() {
        return ki;
    }

    public void setKi(Integer ki) {
        this.ki = ki;
    }
}

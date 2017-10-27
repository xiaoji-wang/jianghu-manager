package models.kung.fu;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wxji on 2017-10-23.
 */
@Entity
@Table(name = "character_kung_fu")
public class CharacterKungFu {
    private Long id;
    private KungFu kungFu;
    private Set<CharacterKungFuAct> characterKungFuActs = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_kung_fu_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kung_fu_id")
    public KungFu getKungFu() {
        return kungFu;
    }

    public void setKungFu(KungFu kungFu) {
        this.kungFu = kungFu;
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "characterKungFu")
    public Set<CharacterKungFuAct> getCharacterKungFuActs() {
        return characterKungFuActs;
    }

    public void setCharacterKungFuActs(Set<CharacterKungFuAct> characterKungFuActs) {
        this.characterKungFuActs = characterKungFuActs;
    }
}

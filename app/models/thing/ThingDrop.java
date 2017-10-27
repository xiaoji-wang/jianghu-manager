package models.thing;

import models.character.Monster;
import models.character.Npc;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by wxji on 2017-09-30.
 */
@Entity
@Table(name = "thing_drop")
public class ThingDrop {
    private Long id;
    private Npc npc;
    private Monster monster;
    private Thing thing;
    private BigDecimal dropRate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thing_drop_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "npc_id")
    public Npc getNpc() {
        return npc;
    }

    public void setNpc(Npc npc) {
        this.npc = npc;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monster_id")
    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thing_id")
    public Thing getThing() {
        return thing;
    }

    public void setThing(Thing thing) {
        this.thing = thing;
    }

    @Column(name = "drop_rate")
    public BigDecimal getDropRate() {
        return dropRate;
    }

    public void setDropRate(BigDecimal dropRate) {
        this.dropRate = dropRate;
    }
}

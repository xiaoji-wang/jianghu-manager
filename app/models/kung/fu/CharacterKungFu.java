package models.kung.fu;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by wxji on 2017-10-23.
 */
public class CharacterKungFu {
    private Long id;
    private KungFu kungFu;
    private Set<CharacterKungFuAct> characterKungFuActs = new HashSet<>();
}

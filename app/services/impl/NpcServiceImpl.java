package services.impl;

import com.google.inject.Inject;
import daos.NpcDao;
import services.NpcService;

/**
 * Created by wxji on 2017-09-18.
 */
public class NpcServiceImpl implements NpcService {
    @Inject
    private NpcDao npcDao;
}

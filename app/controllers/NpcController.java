package controllers;

import com.google.inject.Inject;
import controllers.base.BaseController;
import models.character.Npc;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Result;
import services.NpcService;

/**
 * Created by wxji on 2017-09-18.
 */
public class NpcController extends BaseController {

    @Inject
    private NpcService npcService;

    @Inject
    public NpcController(FormFactory factory) {
        super(factory.form());
    }

    @Transactional
    public Result createNpc() {
        Npc npc = npcService.createNpc(getJsonData());
        return json(npc);
    }

    @Transactional
    public Result getNpc() {
        return json(npcService.getNpc(getParams()));
    }
}

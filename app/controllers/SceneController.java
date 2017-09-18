package controllers;

import com.google.inject.Inject;
import controllers.base.BaseController;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Result;
import services.SceneCellService;
import services.SceneService;

/**
 * Created by wxji on 2017-09-18.
 */
public class SceneController extends BaseController {

    @Inject
    private SceneService sceneService;

    @Inject
    private SceneCellService sceneCellService;

    @Inject
    public SceneController(FormFactory factory) {
        super(factory.form());
    }

    @Transactional
    public Result getSceneCell(Long id) {
        return json(sceneCellService.getSceneCell(id, getParams()));
    }

    @Transactional
    public Result createSceneCell(Long id) {
        sceneCellService.createSceneCell(id, getJsonData());
        return success();
    }

    @Transactional
    public Result getScene() {
        return json(sceneService.getScene(getParams()));
    }
}

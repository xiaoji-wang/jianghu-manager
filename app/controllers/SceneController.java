package controllers;

import com.google.inject.Inject;
import controllers.base.BaseController;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Result;
import services.SceneService;

/**
 * Created by wxji on 2017-09-18.
 */
public class SceneController extends BaseController {

    @Inject
    private SceneService sceneService;

    @Inject
    public SceneController(FormFactory factory) {
        super(factory.form());
    }

    @Transactional
    public Result getSceneCell(Long id) {
        return json(sceneService.getSceneCell(id, getParams()));
    }

    @Transactional
    public Result createScene() {
        sceneService.createScene(getJsonData());
        return success();
    }

    @Transactional
    public Result saveSceneCell(Long id) {
        sceneService.saveSceneCell(id, getJsonData());
        return success();
    }

    @Transactional
    public Result getScene() {
        return json(sceneService.getScene(getParams()));
    }
}

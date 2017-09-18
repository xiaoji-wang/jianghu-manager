package services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import daos.SceneCellDao;
import daos.SceneDao;
import models.Scene;
import models.SceneCell;
import services.SceneCellService;

import java.util.Map;

import static util.Collection.map;

/**
 * Created by wxji on 2017-09-18.
 */
public class SceneCellServiceImpl implements SceneCellService {
    @Inject
    private SceneDao sceneDao;
    @Inject
    private SceneCellDao sceneCellDao;

    @Override
    public Map<String, Object> getSceneCell(Long id, Map<String, String> params) {
        return map("cells", sceneCellDao.get().where().eq("scene.id", id).list(params));
    }

    @Override
    public void createSceneCell(Long id, JsonNode jsonData) {
        sceneCellDao.getSession().createSQLQuery("delete from scene_cell where scene_id=:id").setLong("id", id).executeUpdate();
        Scene scene = sceneDao.get().byId(id);
        for (JsonNode row : jsonData.get("cells")) {
            for (JsonNode cell : row) {
                SceneCell sceneCell = new SceneCell();
                sceneCell.setScene(scene);
                sceneCell.setName(cell.has("name") ? cell.get("name").asText("") : "");
                sceneCell.setArrive(cell.get("arrive").asBoolean());
                sceneCell.setColor(cell.get("color").asText());
                sceneCell.setDescription(cell.has("description") ? cell.get("description").asText("") : "");
                sceneCell.setX(cell.get("x").asInt());
                sceneCell.setY(cell.get("y").asInt());
                sceneCellDao.insert(sceneCell);
            }
        }
    }
}

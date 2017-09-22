package services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import daos.NpcDao;
import daos.SceneCellDao;
import daos.SceneDao;
import models.Scene;
import models.SceneCell;
import services.SceneService;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static util.Collection.map;

/**
 * Created by wxji on 2017-09-18.
 */
public class SceneServiceImpl implements SceneService {
    @Inject
    private NpcDao npcDao;
    @Inject
    private SceneDao sceneDao;
    @Inject
    private SceneCellDao sceneCellDao;

    @Override
    public Map<String, Object> getScene(Map<String, String> params) {
        return sceneDao.get().resultList(params);
    }

    @Override
    public Map getSceneCell(Long id, Map<String, String> params) {
        Scene scene = sceneDao.get().byId(id);
        return map(
                "scene", scene,
                "cells", sceneCellDao.get().leftJoin("npcs").where().eq("scene", scene).list(params)
        );
    }

    @Override
    public void createSceneCell(Long id, JsonNode jsonData) {
        String sql = "update npc n set n.scene_cell_id = null where exists (select 1 from scene_cell sc where sc.scene_cell_id = n.scene_cell_id and sc.scene_id=:id)";
        sceneCellDao.getSession().createSQLQuery(sql).setLong("id", id).executeUpdate();
        sceneCellDao.getSession().createSQLQuery("delete from scene_cell where scene_id=:id").setLong("id", id).executeUpdate();
        Scene scene = sceneDao.get().byId(id);
        Set<Long> npcIds = new HashSet<>();
        for (JsonNode row : jsonData.get("cells")) {
            for (JsonNode cell : row) {
                npcIds.clear();
                SceneCell sceneCell = new SceneCell();
                sceneCell.setScene(scene);
                sceneCell.setName(cell.has("name") ? cell.get("name").asText("") : "");
                sceneCell.setArrive(cell.get("arrive").asBoolean());
                sceneCell.setColor(cell.get("color").asText());
                sceneCell.setDescription(cell.has("description") ? cell.get("description").asText("") : "");
                sceneCell.setX(cell.get("x").asInt());
                sceneCell.setY(cell.get("y").asInt());
                sceneCellDao.insert(sceneCell);
                for (JsonNode npc : cell.get("npcs")) {
                    npcIds.add(npc.get("id").asLong());
                }
                if (!npcIds.isEmpty()) {
                    npcDao.get().where().in("id", npcIds).list().forEach(n -> {
                        n.setSceneCell(sceneCell);
                        npcDao.update(n);
                    });
                }
            }
        }
    }
}

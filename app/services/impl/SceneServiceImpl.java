package services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import daos.SceneCellDao;
import daos.SceneDao;
import models.Scene;
import models.SceneCell;
import services.SceneService;

import java.util.Map;

import static util.Collection.map;

/**
 * Created by wxji on 2017-09-18.
 */
public class SceneServiceImpl implements SceneService {
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
    public void createScene(JsonNode jsonData) {
        Scene scene = new Scene();
        scene.setName(jsonData.get("name").asText());
        scene.setDescription(jsonData.get("description").asText());
        scene.setWidth(jsonData.get("width").asInt());
        scene.setHeight(jsonData.get("height").asInt());
        sceneDao.insert(scene);

        createSceneCell(scene);
    }

    private void createSceneCell(Scene scene) {
        SceneCell[][] map = new SceneCell[scene.getHeight()][scene.getWidth()];
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                SceneCell cell = new SceneCell();
                cell.setX(x);
                cell.setY(y);
                cell.setScene(scene);
                sceneCellDao.insert(cell);
                map[y][x] = cell;
                if (x > 0) {
                    cell.setWest(map[y][x - 1].getId());
                    map[y][x - 1].setEast(cell.getId());
                }
                if (y > 0) {
                    if (y % 2 == 0) {
                        cell.setNorthEast(map[y - 1][x].getId());
                        map[y - 1][x].setSouthWest(cell.getId());
                        if (x > 0) {
                            cell.setNorthWest(map[y - 1][x - 1].getId());
                            map[y - 1][x - 1].setSouthEast(cell.getId());
                        }
                    } else {
                        cell.setNorthWest(map[y - 1][x].getId());
                        map[y - 1][x].setSouthEast(cell.getId());
                        if (x < map[y].length - 1) {
                            cell.setNorthEast(map[y - 1][x + 1].getId());
                            map[y - 1][x + 1].setSouthWest(cell.getId());
                        }
                    }
                }
            }
        }
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                sceneCellDao.update(map[y][x]);
            }
        }
    }
}

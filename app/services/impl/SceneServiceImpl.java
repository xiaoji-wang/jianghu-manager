package services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import daos.SceneCellDao;
import daos.SceneDao;
import models.Scene;
import models.SceneCell;
import services.SceneService;

import java.util.HashMap;
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
        scene.setLayer(jsonData.get("layer").asInt());
        sceneDao.insert(scene);

        createSceneCell(scene);
    }

    private void createSceneCell(Scene scene) {
        SceneCell center = new SceneCell();
        center.setScene(scene);
        center.setCenter(true);
        sceneCellDao.insert(center);
        Map<Long, SceneCell> cells = new HashMap<>();
        cells.put(center.getId(), center);
        createSceneCell(scene, scene.getLayer(), center, cells);
    }

    private void createSceneCell(Scene scene, int layer, SceneCell center, Map<Long, SceneCell> cells) {
        if (layer > 0) {
            SceneCell east = createEastSceneCell(scene, center, cells);
            SceneCell southEast = createSouthEastSceneCell(scene, center, cells);
            SceneCell southWest = createSouthWestSceneCell(scene, center, cells);
            SceneCell west = createWestSceneCell(scene, center, cells);
            SceneCell northWest = createNorthWestSceneCell(scene, center, cells);
            SceneCell northEast = createNorthEastSceneCell(scene, center, cells);
            layer--;
            createSceneCell(scene, layer, east, cells);
            createSceneCell(scene, layer, southEast, cells);
            createSceneCell(scene, layer, southWest, cells);
            createSceneCell(scene, layer, west, cells);
            createSceneCell(scene, layer, northWest, cells);
            createSceneCell(scene, layer, northEast, cells);
        }
    }

    private SceneCell createEastSceneCell(Scene scene, SceneCell center, Map<Long, SceneCell> cells) {
        if (cells.containsKey(center.getEast())) {
            return cells.get(center.getEast());
        }
        SceneCell cell = new SceneCell();
        cell.setScene(scene);
        cell.setWest(center.getId());
        sceneCellDao.insert(cell);

        center.setEast(cell.getId());
        sceneCellDao.update(center);

        if (cells.containsKey(center.getNorthEast())) {
            SceneCell c = cells.get(center.getNorthEast());

            cell.setNorthWest(c.getId());
            if (c.getEast() != null) {
                cell.setNorthEast(c.getEast());
                cells.get(c.getEast()).setSouthWest(cell.getId());
                sceneCellDao.update(cells.get(c.getEast()));
            }
            sceneCellDao.update(cell);

            c.setSouthEast(cell.getId());
            sceneCellDao.update(c);
        }

        if (cells.containsKey(center.getSouthEast())) {
            SceneCell c = cells.get(center.getSouthEast());

            cell.setSouthWest(c.getId());
            if (c.getEast() != null) {
                cell.setSouthEast(c.getEast());
                cells.get(c.getEast()).setNorthWest(cell.getId());
                sceneCellDao.update(cells.get(c.getEast()));
            }
            sceneCellDao.update(cell);

            c.setNorthEast(cell.getId());
            sceneCellDao.update(c);
        }
        cells.put(cell.getId(), cell);
        return cell;
    }

    private SceneCell createSouthEastSceneCell(Scene scene, SceneCell center, Map<Long, SceneCell> cells) {
        if (cells.containsKey(center.getSouthEast())) {
            return cells.get(center.getSouthEast());
        }
        SceneCell cell = new SceneCell();
        cell.setScene(scene);
        cell.setNorthWest(center.getId());
        sceneCellDao.insert(cell);

        center.setSouthEast(cell.getId());
        sceneCellDao.update(center);

        if (cells.containsKey(center.getEast())) {
            SceneCell c = cells.get(center.getEast());

            cell.setNorthEast(c.getId());
            if (c.getSouthEast() != null) {
                cell.setEast(c.getSouthEast());
                cells.get(c.getSouthEast()).setWest(cell.getId());
                sceneCellDao.update(cells.get(c.getSouthEast()));
            }
            sceneCellDao.update(cell);

            c.setSouthWest(cell.getId());
            sceneCellDao.update(c);
        }

        if (cells.containsKey(center.getSouthWest())) {
            SceneCell c = cells.get(center.getSouthWest());

            cell.setWest(c.getId());
            if (c.getSouthEast() != null) {
                cell.setSouthWest(c.getSouthEast());
                cells.get(c.getSouthEast()).setNorthEast(cell.getId());
                sceneCellDao.update(cells.get(c.getSouthEast()));
            }
            sceneCellDao.update(cell);

            c.setEast(cell.getId());
            sceneCellDao.update(c);
        }
        cells.put(cell.getId(), cell);
        return cell;
    }

    private SceneCell createSouthWestSceneCell(Scene scene, SceneCell center, Map<Long, SceneCell> cells) {
        if (cells.containsKey(center.getSouthWest())) {
            return cells.get(center.getSouthWest());
        }
        SceneCell cell = new SceneCell();
        cell.setScene(scene);
        cell.setNorthEast(center.getId());
        sceneCellDao.insert(cell);

        center.setSouthWest(cell.getId());
        sceneCellDao.update(center);

        if (cells.containsKey(center.getSouthEast())) {
            SceneCell c = cells.get(center.getSouthEast());

            cell.setEast(c.getId());
            if (c.getSouthWest() != null) {
                cell.setSouthEast(c.getSouthWest());
                cells.get(c.getSouthWest()).setNorthWest(cell.getId());
                sceneCellDao.update(cells.get(c.getSouthWest()));
            }
            sceneCellDao.update(cell);

            c.setWest(cell.getId());
            sceneCellDao.update(c);
        }

        if (cells.containsKey(center.getWest())) {
            SceneCell c = cells.get(center.getWest());

            cell.setNorthWest(c.getId());
            if (c.getSouthWest() != null) {
                cell.setWest(c.getSouthWest());
                cells.get(c.getSouthWest()).setEast(cell.getId());
                sceneCellDao.update(cells.get(c.getSouthWest()));
            }
            sceneCellDao.update(cell);

            c.setSouthEast(cell.getId());
            sceneCellDao.update(c);
        }
        cells.put(cell.getId(), cell);
        return cell;
    }

    private SceneCell createWestSceneCell(Scene scene, SceneCell center, Map<Long, SceneCell> cells) {
        if (cells.containsKey(center.getWest())) {
            return cells.get(center.getWest());
        }
        SceneCell cell = new SceneCell();
        cell.setScene(scene);
        cell.setEast(center.getId());
        sceneCellDao.insert(cell);

        center.setWest(cell.getId());
        sceneCellDao.update(center);

        if (cells.containsKey(center.getSouthWest())) {
            SceneCell c = cells.get(center.getSouthWest());

            cell.setSouthEast(c.getId());
            if (c.getWest() != null) {
                cell.setSouthWest(c.getWest());
                cells.get(c.getWest()).setNorthEast(cell.getId());
                sceneCellDao.update(cells.get(c.getWest()));
            }
            sceneCellDao.update(cell);

            c.setNorthWest(cell.getId());
            sceneCellDao.update(c);
        }

        if (cells.containsKey(center.getNorthWest())) {
            SceneCell c = cells.get(center.getNorthWest());

            cell.setNorthEast(c.getId());
            if (c.getWest() != null) {
                cell.setNorthWest(c.getWest());
                cells.get(c.getWest()).setSouthEast(cell.getId());
                sceneCellDao.update(cells.get(c.getWest()));
            }
            sceneCellDao.update(cell);

            c.setSouthWest(cell.getId());
            sceneCellDao.update(c);
        }
        cells.put(cell.getId(), cell);
        return cell;
    }

    private SceneCell createNorthWestSceneCell(Scene scene, SceneCell center, Map<Long, SceneCell> cells) {
        if (cells.containsKey(center.getNorthWest())) {
            return cells.get(center.getNorthWest());
        }
        SceneCell cell = new SceneCell();
        cell.setScene(scene);
        cell.setSouthEast(center.getId());
        sceneCellDao.insert(cell);

        center.setNorthWest(cell.getId());
        sceneCellDao.update(center);

        if (cells.containsKey(center.getWest())) {
            SceneCell c = cells.get(center.getWest());

            cell.setSouthWest(c.getId());
            if (c.getNorthWest() != null) {
                cell.setWest(c.getNorthWest());
                cells.get(c.getNorthWest()).setEast(cell.getId());
                sceneCellDao.update(cells.get(c.getNorthWest()));
            }
            sceneCellDao.update(cell);

            c.setNorthEast(cell.getId());
            sceneCellDao.update(c);
        }

        if (cells.containsKey(center.getNorthEast())) {
            SceneCell c = cells.get(center.getNorthEast());

            cell.setEast(c.getId());
            if (c.getNorthWest() != null) {
                cell.setNorthEast(c.getNorthWest());
                cells.get(c.getNorthWest()).setSouthWest(cell.getId());
                sceneCellDao.update(cells.get(c.getNorthWest()));
            }
            sceneCellDao.update(cell);

            c.setWest(cell.getId());
            sceneCellDao.update(c);
        }
        cells.put(cell.getId(), cell);
        return cell;
    }

    private SceneCell createNorthEastSceneCell(Scene scene, SceneCell center, Map<Long, SceneCell> cells) {
        if (cells.containsKey(center.getNorthEast())) {
            return cells.get(center.getNorthEast());
        }
        SceneCell cell = new SceneCell();
        cell.setScene(scene);
        cell.setSouthWest(center.getId());
        sceneCellDao.insert(cell);

        center.setNorthEast(cell.getId());
        sceneCellDao.update(center);

        if (cells.containsKey(center.getEast())) {
            SceneCell c = cells.get(center.getEast());

            cell.setSouthEast(c.getId());
            if (c.getNorthEast() != null) {
                cell.setEast(c.getNorthEast());
                cells.get(c.getNorthEast()).setWest(cell.getId());
                sceneCellDao.update(cells.get(c.getNorthEast()));
            }
            sceneCellDao.update(cell);

            c.setNorthWest(cell.getId());
            sceneCellDao.update(c);
        }

        if (cells.containsKey(center.getNorthWest())) {
            SceneCell c = cells.get(center.getNorthWest());

            cell.setWest(c.getId());
            if (c.getNorthEast() != null) {
                cell.setNorthWest(c.getNorthEast());
                cells.get(c.getNorthEast()).setSouthEast(cell.getId());
                sceneCellDao.update(cells.get(c.getNorthEast()));
            }
            sceneCellDao.update(cell);

            c.setEast(cell.getId());
            sceneCellDao.update(c);
        }
        cells.put(cell.getId(), cell);
        return cell;
    }
}

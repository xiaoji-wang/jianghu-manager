package services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import daos.NpcDao;
import daos.SceneCellDao;
import daos.SceneDao;
import models.Scene;
import models.SceneCell;
import services.SceneService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public void createScene(JsonNode jsonData) {
        Scene scene = new Scene();
    }

    private void createSceneCell(SceneCell center, List<SceneCell> cells) {
        SceneCell east = createEastSceneCell(center, cells);
        SceneCell southEast = createSouthEastSceneCell(center, cells);
        SceneCell southWest = createSouthWestSceneCell(center, cells);
        SceneCell west = createWestSceneCell(center, cells);
        SceneCell northWest = createNorthWestSceneCell(center, cells);
        SceneCell northEast = createNorthEastSceneCell(center, cells);
    }

    private SceneCell createEastSceneCell(SceneCell center, List<SceneCell> cells) {
        Optional<SceneCell> op = cells.stream().filter(c -> c.getId().equals(center.getEast())).findAny();
        if (op.isPresent()) {
            return op.get();
        }
        SceneCell cell = new SceneCell();
        cell.setWest(center.getId());
        sceneCellDao.insert(cell);
        center.setEast(cell.getId());
        sceneCellDao.update(center);
        op = cells.stream().filter(c -> c.getId().equals(center.getNorthEast())).findFirst();
        if (op.isPresent()) {
            SceneCell northEast = op.get();
            cell.setNorthWest(northEast.getId());
            sceneCellDao.update(cell);
            northEast.setSouthEast(cell.getId());
            sceneCellDao.update(northEast);
        }
        op = cells.stream().filter(c -> c.getId().equals(center.getSouthEast())).findFirst();
        if (op.isPresent()) {
            SceneCell southEast = op.get();
            cell.setSouthWest(southEast.getId());
            sceneCellDao.update(cell);
            southEast.setNorthEast(cell.getId());
            sceneCellDao.update(southEast);
        }
        cells.add(cell);
        return cell;
    }

    private SceneCell createSouthEastSceneCell(SceneCell center, List<SceneCell> cells) {
        Optional<SceneCell> op = cells.stream().filter(c -> c.getId().equals(center.getSouthEast())).findAny();
        if (op.isPresent()) {
            return op.get();
        }
        SceneCell cell = new SceneCell();
        cell.setNorthWest(center.getId());
        sceneCellDao.insert(cell);
        center.setSouthEast(cell.getId());
        sceneCellDao.update(center);
        op = cells.stream().filter(c -> c.getId().equals(center.getEast())).findFirst();
        if (op.isPresent()) {
            SceneCell east = op.get();
            cell.setNorthEast(east.getId());
            sceneCellDao.update(cell);
            east.setSouthWest(cell.getId());
            sceneCellDao.update(east);
        }
        op = cells.stream().filter(c -> c.getId().equals(center.getSouthWest())).findFirst();
        if (op.isPresent()) {
            northWest(op.get(), cell);
        }
        cells.add(cell);
        return cell;
    }

    private SceneCell createSouthWestSceneCell(SceneCell center, List<SceneCell> cells) {
        Optional<SceneCell> op = cells.stream().filter(c -> c.getId().equals(center.getSouthWest())).findAny();
        if (op.isPresent()) {
            return op.get();
        }
        SceneCell cell = new SceneCell();
        cell.setNorthEast(center.getId());
        sceneCellDao.insert(cell);
        center.setSouthWest(cell.getId());
        sceneCellDao.update(center);
        op = cells.stream().filter(c -> c.getId().equals(center.getSouthEast())).findFirst();
        if (op.isPresent()) {
            SceneCell southEast = op.get();
            cell.setEast(southEast.getId());
            sceneCellDao.update(cell);
            southEast.setWest(cell.getId());
            sceneCellDao.update(southEast);
        }
        op = cells.stream().filter(c -> c.getId().equals(center.getWest())).findFirst();
        if (op.isPresent()) {
            SceneCell west = op.get();
            cell.setNorthWest(west.getId());
            sceneCellDao.update(cell);
            west.setSouthEast(cell.getId());
            sceneCellDao.update(west);
        }
        cells.add(cell);
        return cell;
    }

    private SceneCell createWestSceneCell(SceneCell center, List<SceneCell> cells) {
        Optional<SceneCell> op = cells.stream().filter(c -> c.getId().equals(center.getWest())).findAny();
        if (op.isPresent()) {
            return op.get();
        }
        SceneCell cell = new SceneCell();
        cell.setEast(center.getId());
        sceneCellDao.insert(cell);
        center.setWest(cell.getId());
        sceneCellDao.update(center);
        op = cells.stream().filter(c -> c.getId().equals(center.getSouthWest())).findFirst();
        if (op.isPresent()) {
            east(op.get(), cell);
        }
        op = cells.stream().filter(c -> c.getId().equals(center.getNorthWest())).findFirst();
        if (op.isPresent()) {
            SceneCell northWest = op.get();
            cell.setNorthEast(northWest.getId());
            sceneCellDao.update(cell);
            northWest.setSouthWest(cell.getId());
            sceneCellDao.update(northWest);
        }
        cells.add(cell);
        return cell;
    }

    private SceneCell createNorthWestSceneCell(SceneCell center, List<SceneCell> cells) {
        Optional<SceneCell> op = cells.stream().filter(c -> c.getId().equals(center.getNorthWest())).findAny();
        if (op.isPresent()) {
            return op.get();
        }
        SceneCell cell = new SceneCell();
        cell.setSouthEast(center.getId());
        sceneCellDao.insert(cell);
        center.setNorthWest(cell.getId());
        sceneCellDao.update(center);
        op = cells.stream().filter(c -> c.getId().equals(center.getWest())).findFirst();
        if (op.isPresent()) {
            SceneCell west = op.get();
            cell.setSouthWest(west.getId());
            sceneCellDao.update(cell);
            west.setNorthEast(cell.getId());
            sceneCellDao.update(west);
        }
        op = cells.stream().filter(c -> c.getId().equals(center.getNorthEast())).findFirst();
        if (op.isPresent()) {
            SceneCell northEast = op.get();
            cell.setEast(northEast.getId());
            sceneCellDao.update(cell);
            northEast.setWest(cell.getId());
            sceneCellDao.update(northEast);
        }
        cells.add(cell);
        return cell;
    }

    private SceneCell createNorthEastSceneCell(SceneCell center, List<SceneCell> cells) {
        Optional<SceneCell> op = cells.stream().filter(c -> c.getId().equals(center.getNorthEast())).findAny();
        if (op.isPresent()) {
            return op.get();
        }
        SceneCell cell = new SceneCell();
        cell.setSouthWest(center.getId());
        sceneCellDao.insert(cell);
        center.setNorthEast(cell.getId());
        sceneCellDao.update(center);
        op = cells.stream().filter(c -> c.getId().equals(center.getEast())).findFirst();
        if (op.isPresent()) {
            east(op.get(), cell);
        }
        op = cells.stream().filter(c -> c.getId().equals(center.getNorthWest())).findFirst();
        if (op.isPresent()) {
            northWest(op.get(), cell);
        }
        cells.add(cell);
        return cell;
    }

    private void east(SceneCell east, SceneCell cell) {
        cell.setSouthEast(east.getId());
        sceneCellDao.update(cell);
        east.setNorthWest(cell.getId());
        sceneCellDao.update(east);
    }

    private void northWest(SceneCell northWest, SceneCell cell) {
        cell.setWest(northWest.getId());
        sceneCellDao.update(cell);
        northWest.setEast(cell.getId());
        sceneCellDao.update(northWest);
    }

    @Override
    public void createSceneCell(Long id, JsonNode jsonData) {
//        String sql = "update npc n set n.scene_cell_id = null where exists (select 1 from scene_cell sc where sc.scene_cell_id = n.scene_cell_id and sc.scene_id=:id)";
//        sceneCellDao.getSession().createSQLQuery(sql).setLong("id", id).executeUpdate();
//        sceneCellDao.getSession().createSQLQuery("delete from scene_cell where scene_id=:id").setLong("id", id).executeUpdate();
//        Scene scene = sceneDao.get().byId(id);
//        Set<Long> npcIds = new HashSet<>();
//        for (JsonNode row : jsonData.get("cells")) {
//            for (JsonNode cell : row) {
//                npcIds.clear();
//                SceneCell sceneCell = new SceneCell();
//                sceneCell.setScene(scene);
//                sceneCell.setName(cell.has("name") ? cell.get("name").asText("") : "");
//                sceneCell.setArrive(cell.get("arrive").asBoolean());
//                sceneCell.setColor(cell.get("color").asText());
//                sceneCell.setDescription(cell.has("description") ? cell.get("description").asText("") : "");
//                sceneCell.setX(cell.get("x").asInt());
//                sceneCell.setY(cell.get("y").asInt());
//                sceneCellDao.insert(sceneCell);
//                for (JsonNode npc : cell.get("npcs")) {
//                    npcIds.add(npc.get("id").asLong());
//                }
//                if (!npcIds.isEmpty()) {
//                    npcDao.get().where().in("id", npcIds).list().forEach(n -> {
//                        n.setSceneCell(sceneCell);
//                        npcDao.update(n);
//                    });
//                }
//            }
//        }
    }
}

package services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.jrerdangjia.base.orm.Query;
import daos.NpcDao;
import models.Npc;
import services.NpcService;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by wxji on 2017-09-18.
 */
public class NpcServiceImpl implements NpcService {
    @Inject
    private NpcDao npcDao;

    @Override
    public Npc createNpc(JsonNode jsonData) {
        Npc npc;
        if (jsonData.has("id")) {
            npc = npcDao.get().byId(jsonData.get("id").asLong());
            createNpc(jsonData, npc);
            npcDao.update(npc);
        } else {
            npc = new Npc();
            createNpc(jsonData, npc);
            npcDao.insert(npc);
        }
        return npc;
    }

    @Override
    public Map<String, Object> getNpc(Map<String, String> params) {
        Query<Npc> query = npcDao.get();
        if (params.containsKey("id")) {
            query.where().eq("id", params.get("id"));
        }
        return query.resultList(params);
    }

    private void createNpc(JsonNode jsonData, Npc npc) {
        npc.setName(jsonData.has("name") ? jsonData.get("name").asText("") : "");
        npc.setDescription(jsonData.has("description") ? jsonData.get("description").asText("") : "");
        npc.setWord(jsonData.has("word") ? jsonData.get("word").asText("...") : "");
        npc.setLevel(jsonData.has("level") ? jsonData.get("level").asInt(1) : 1);
        npc.setExp(jsonData.has("exp") ? jsonData.get("exp").asInt(1) : 1);
        npc.setHp(jsonData.has("hp") ? jsonData.get("hp").asInt(10) : 10);
        npc.setMinAttack(jsonData.has("minAttack") ? jsonData.get("minAttack").asInt(1) : 1);
        npc.setMaxAttack(jsonData.has("maxAttack") ? jsonData.get("maxAttack").asInt(5) : 5);
        npc.setMinDefense(jsonData.has("minDefense") ? jsonData.get("minDefense").asInt(1) : 1);
        npc.setMaxDefense(jsonData.has("maxDefense") ? jsonData.get("maxDefense").asInt(5) : 5);
        npc.setCritRate(jsonData.has("critRate") ? new BigDecimal(jsonData.get("critRate").asDouble(0)) : BigDecimal.ZERO);
        npc.setDodgeRate(jsonData.has("dodgeRate") ? new BigDecimal(jsonData.get("dodgeRate").asDouble(0)) : BigDecimal.ZERO);
    }
}

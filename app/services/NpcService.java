package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.ImplementedBy;
import models.Npc;
import services.impl.NpcServiceImpl;

import java.util.Map;

/**
 * Created by wxji on 2017-09-18.
 */
@ImplementedBy(NpcServiceImpl.class)
public interface NpcService {
    Npc createNpc(JsonNode jsonData);

    Map<String, Object> getNpc(Map<String, String> params);
}

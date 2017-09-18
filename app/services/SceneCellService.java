package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.ImplementedBy;
import services.impl.SceneCellServiceImpl;

import java.util.Map;

/**
 * Created by wxji on 2017-09-18.
 */
@ImplementedBy(SceneCellServiceImpl.class)
public interface SceneCellService {
    Map<String, Object> getSceneCell(Long id, Map<String, String> params);

    void createSceneCell(Long id, JsonNode jsonData);
}

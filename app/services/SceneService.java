package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.ImplementedBy;
import services.impl.SceneServiceImpl;

import java.util.Map;

/**
 * Created by wxji on 2017-09-18.
 */
@ImplementedBy(SceneServiceImpl.class)
public interface SceneService {
    Map<String, Object> getScene(Map<String, String> params);

    Map getSceneCell(Long id, Map<String, String> params);

    void createScene(JsonNode jsonData);

    void saveSceneCell(Long id, JsonNode jsonData);
}

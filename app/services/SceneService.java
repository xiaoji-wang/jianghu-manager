package services;

import com.google.inject.ImplementedBy;
import services.impl.SceneServiceImpl;

import java.util.Map;

/**
 * Created by wxji on 2017-09-18.
 */
@ImplementedBy(SceneServiceImpl.class)
public interface SceneService {
    Map<String, Object> getScene(Map<String, String> params);
}

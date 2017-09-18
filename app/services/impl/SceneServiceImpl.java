package services.impl;

import com.google.inject.Inject;
import daos.SceneDao;
import services.SceneService;

import java.util.Map;

/**
 * Created by wxji on 2017-09-18.
 */
public class SceneServiceImpl implements SceneService {
    @Inject
    private SceneDao sceneDao;

    @Override
    public Map<String, Object> getScene(Map<String, String> params) {
        return sceneDao.get().resultList(params);
    }
}

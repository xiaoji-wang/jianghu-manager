package controllers.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.google.inject.Inject;
import play.data.DynamicForm;
import play.libs.ws.WSClient;
import play.mvc.Controller;
import play.mvc.Result;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static util.Collection.map;

/**
 * Created by wxji on 2016-12-22.
 */
public abstract class BaseController extends Controller {

    @Inject
    protected WSClient wsClient;

    protected DynamicForm form;

    public BaseController(DynamicForm form) {
        this.form = form;
    }

    protected Result json(Object obj) {
        try {
            return ok(toJson(obj)).as("application/json;charset=utf-8");
        } catch (JsonProcessingException e) {
            return badRequest(e.getMessage());
        }
    }

    protected String toJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Hibernate5Module hibernate5Module = new Hibernate5Module();
        hibernate5Module.configure(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION, false);
        mapper.registerModule(hibernate5Module);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return mapper.writeValueAsString(obj);
    }

    protected Result success(Object... msg) {
        Map map = map("success", true);
        map.putAll(map(msg));
        return json(map);
    }

    protected JsonNode getJsonData() {
        return request().body().asJson();
    }

    protected Map<String, String> getParams() {
        Map<String, String[]> params = request().queryString();
        Map<String, String> queryParams = new HashMap<>();
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            if (entry.getValue().length <= 0) {
                continue;
            }
            queryParams.put(entry.getKey(), entry.getValue()[0]);
        }
        return queryParams;
    }
}

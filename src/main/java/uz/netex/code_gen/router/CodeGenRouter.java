package uz.netex.code_gen.router;

import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import uz.netex.code_gen.common.JdlCode;

public class CodeGenRouter {
    private final Vertx vertx;

    public CodeGenRouter(Vertx vertx) {
        this.vertx = vertx;
    }

    public Router createRouter() {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.route().handler(StaticHandler.create());
        router.post("/generate").handler(this::codeGenHandler);
        return router;
    }

    private void codeGenHandler(RoutingContext context) {
        MultiMap multiMap = context.request().params();

        JsonObject json = new JsonObject();
        json
                .put("group_id", multiMap.get("group_id"))
                .put("artifact_id", multiMap.get("artifact_id"))
                .put("db_name", multiMap.get("db_name"))
                .put("db_user", multiMap.get("db_user"))
                .put("db_password", multiMap.get("db_password"))
                .put("db_schema", multiMap.get("db_schema"))
                .put("jdl", multiMap.get("jdl"));
        JdlCode code = new JdlCode(json.getString("jdl"));
        code.define();
    }
}
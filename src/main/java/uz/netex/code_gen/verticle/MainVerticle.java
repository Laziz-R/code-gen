package uz.netex.code_gen.verticle;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.apache.log4j.Logger;
import uz.netex.code_gen.router.CodeGenRouter;

public class MainVerticle extends AbstractVerticle {
    private static final Logger LOGGER = Logger.getLogger(MainVerticle.class);

    @Override
    public void start() throws Exception {
        createHttpServer();
    }

    private void createHttpServer() {
        String host = "localhost";
        int port = 8079;
        Router cdRouter = new CodeGenRouter(vertx).createRouter();

        vertx.createHttpServer()
                .requestHandler(cdRouter)
                .listen(port, host)
                .onSuccess(httpServer -> {
                    LOGGER.info(String.format("Server started ...\thttp://%s:%d/", host, port));
                })
                .onFailure(LOGGER::error);
    }

    private Future<JsonObject> readConfig() {
        String fileName = "config";
        ConfigStoreOptions storeOptions = new ConfigStoreOptions()
                .setType("file")
                .setFormat("yaml")
                .setConfig(new JsonObject().put("path", "./conf/" + fileName + ".yaml"));

        ConfigRetrieverOptions options = new ConfigRetrieverOptions().addStore(storeOptions);
        ConfigRetriever retriever = ConfigRetriever.create(vertx, options);
        return retriever.getConfig();
    }
}

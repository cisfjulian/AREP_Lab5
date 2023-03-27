package eci.arep;

import static spark.Spark.port;
import static spark.Spark.get;

import eci.arep.MongoServiceImpl;


public class SparkWebServer {

    static MongoServiceImpl mongoService = MongoServiceImpl.getInstance();
    public static void main(String... args){
        port(getPort());
        get("hello/:String", (req,res) ->
        {mongoService.create(req.params("String"));
            return mongoService.getString();
        });

    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4569;
    }
}

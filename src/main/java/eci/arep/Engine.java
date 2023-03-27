package eci.arep;


import eci.arep.RoundRobin;

import java.net.URL;

import static spark.Spark.*;




public class Engine {
    static RoundRobin roundRobin = RoundRobin.getInstance();
    public static void main(String... args){
        port(getPort());
        get("/hello/:String", (req, res) ->{
            String string = req.params("String");
            String server = roundRobin.getNextServer();
            return HttpConnection.get(server + "/hello/" + string);
        });
        get("/", (req, res) -> html());
    }


    private static String html(){
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<style>" +
                "body {\n" +
                "  max-width: 600px;\n" +
                "  margin: 0 auto;\n" +
                "  text-align: center;\n" +
                "  padding: 40px;\n" +
                "  background-color: #f2f2f2;\n" +
                "  border-radius: 10px;\n" +
                "  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);\n" +
                "}\n" +
                "\n" +
                "h1 {\n" +
                "  font-size: 36px;\n" +
                "  line-height: 1.2;\n" +
                "  font-weight: 700;\n" +
                "  color: #333;\n" +
                "  text-align: center;\n" +
                "  margin: 50px 0;\n" +
                "  text-shadow: 2px 2px #ddd;\n" +
                "}\n" +
                "\n" +
                ".button {\n" +
                "  background-color: #f44336;\n" +
                "  border: none;\n" +
                "  color: white;\n" +
                "  padding: 15px 32px;\n" +
                "  text-align: center;\n" +
                "  text-decoration: none;\n" +
                "  display: inline-block;\n" +
                "  font-size: 16px;\n" +
                "  margin: 4px 2px;\n" +
                "  cursor: pointer;\n" +
                "}" +
                "</style>" +
                "<head>\n" +
                "    <title>Mongo Logs</title>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>Mongo Log</h1>\n" +
                "    <form action=\"/hello\">\n" +
                "        <label for=\"newLog\">String:</label><br>\n" +
                "        <input type=\"text\" id=\"newLog\" name=\"newLog\" value=\"String\"><br><br>\n" +
                "        <input type=\"button\" class = button value=\"Submit\" onclick=\"loadGetMsg()\">\n" +
                "    </form>\n" +
                "<br>" +
                "    <div id=\"getrespmsg\"></div>\n" +
                "\n" +
                "    <script>\n" +
                "        function loadGetMsg() {\n" +
                "            let nameVar = document.getElementById(\"newLog\").value;\n" +
                "            const xhttp = new XMLHttpRequest();\n" +
                "            xhttp.onload = function() {\n" +
                "                document.getElementById(\"getrespmsg\").innerHTML =\n" +
                "                this.responseText;\n" +
                "            }\n" +
                "            xhttp.open(\"GET\", \"/hello/\"+nameVar);\n" +
                "            xhttp.send();\n" +
                "        }\n" +
                "    </script>\n" +
                "</body>\n" +
                "</html>";
    }

    private static int getPort() {
        if (System.getenv("PORT1") != null) {
            return Integer.parseInt(System.getenv("PORT1"));
        }
        return 4568;
    }

}

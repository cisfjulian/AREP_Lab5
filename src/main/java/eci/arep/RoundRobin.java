package eci.arep;

import java.util.ArrayList;
import java.util.List;

public class RoundRobin {

    private List<String> serverList = new ArrayList<>();
    private int currentIndex;

    private static RoundRobin instance;

    private RoundRobin() {
        this.serverList.add("http://172.31.57.61:33001");
        this.serverList.add("http://172.31.57.61:33002");
        this.serverList.add("http://172.31.57.61:33003");
    }

    public static RoundRobin getInstance(){
        if(instance == null){
            instance = new RoundRobin();
        }
        return instance;
    }

    public String getNextServer() {
        synchronized(serverList) {
            if (currentIndex >= serverList.size()) {
                currentIndex = 0;
            }

            String server = serverList.get(currentIndex);
            currentIndex++;

            return server;
        }
    }

}

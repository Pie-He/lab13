package sample.server;

import sample.core.Direction;
import sample.core.Icon;
import sample.core.Map;

public class Server {

    private Icon[][] map;
    private int[] pos = {1,1};

    public void createGame() {
        map = Map.map4;
    }

    public Icon[][] getGameView() {
        Icon[][] tmp = new Icon[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                tmp[i][j] = map[i][j];
            }
        }
        tmp[pos[0]][pos[1]] = Icon.HERO;
        return tmp;
    }

    public boolean movePlayer(Direction d) {
        switch (d) {
            case NORTH:
                pos[0]--;
                break;
            case SOUTH:
                pos[0]++;
        }
        return true;
    }
}

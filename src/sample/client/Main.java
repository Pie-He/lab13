package sample.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.core.Direction;
import sample.core.Icon;
import sample.server.Server;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application {
    private static final double TILE_SIZE = 20;
    private Stage stage;
    GridPane map;
    private Map<Icon, Image> imageMap = new HashMap<>();
    Server server;
    ImageView[][] mapTiles;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("maze game");
        this.stage = primaryStage;
        server = new Server();
        server.createGame();
        // prepare resources
        initGameFrame();
        initResources();
        addKeyControls();
        createAndShowGui();
    }

    private void createAndShowGui() {
        Icon[][] icons = server.getGameView();
        int numRows = icons.length;
        int numCols = icons[0].length;
        mapTiles = new ImageView[numRows][numCols];
        for (int r = 0; r < numRows; r++) {
            // maze contents
            for (int c = 0; c < numCols; c++) {
                mapTiles[r][c] = newImageView(icons[r][c]);
                map.add(mapTiles[r][c], c + 1, r + 1);
            }
        }
        System.out.println("hello");
    }

    private ImageView newImageView(Icon icon) {
        ImageView imageView = new ImageView(iconToImage(icon));
        imageView.setFitWidth(TILE_SIZE);
        imageView.setFitHeight(TILE_SIZE);
        return imageView;
    }

    private Image iconToImage(Icon icon) {
        Image image = imageMap.get(icon);
        if (image == null)
            throw new IllegalArgumentException(icon.toString());
        return image;
    }

    private void addKeyControls() {
        map.setOnKeyPressed(e -> {
            Direction d = null;
            boolean undoBool = false;
            switch (e.getCode()) {
                case W:
                    d = Direction.NORTH;
                    break;
                case A:
                    d = Direction.WEST;
                    break;
                case S:
                    d = Direction.SOUTH;
                    break;
                case D:
                    d = Direction.EAST;
                    break;
                case B:
                    undoBool = true;
                    break;
//                case K:
//                    server.killMonster();
//                    return;
//                case P:
//                    server.pickTreasure();
//                    return;
//                case C:
//                    showScore(server.checkScore());
//                    return;
            }
            if (d != null) {
                server.movePlayer(d);
                createAndShowGui();
            }
        });
    }

    private void initGameFrame() {
        map = new GridPane();
        this.stage.setScene(new Scene(map));
        this.stage.show();
        map.requestFocus();
    }

    private void initResources() {
        String path = "img/grass/";
        Image WALL_IMAGE = new Image(path + "wall.png");
        Image SPACE_IMAGE = new Image(path + "space.png");
        Image HERO_IMAGE = new Image(path + "hero.png");
        Image END_IMAGE = new Image(path + "end.png");
        Image FOOTPRINT_IMAGE = new Image(path + "footprint.png");
        Image MONSTER_IMAGE = new Image(path + "monster.png");
        Image TREASURE_IMAGE = new Image(path + "treasure.png");

        // add to image map
        imageMap.put(Icon.EMPTY, SPACE_IMAGE);
        imageMap.put(Icon.WALL, WALL_IMAGE);
        imageMap.put(Icon.HERO, HERO_IMAGE);
        imageMap.put(Icon.FOOTPRINT, FOOTPRINT_IMAGE);
        imageMap.put(Icon.MONSTER, MONSTER_IMAGE);
        imageMap.put(Icon.END, END_IMAGE);
        imageMap.put(Icon.TREASURE, TREASURE_IMAGE);
    }


    public static void main(String[] args) {
        launch(args);
    }
}

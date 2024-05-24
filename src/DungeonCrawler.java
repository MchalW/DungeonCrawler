import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class DungeonCrawler extends JFrame{
    Player player = new Player();
    Room currentRoom;

    List<Room> rooms;
    int titleSize = 32;
    DungeonCrawler(){
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        GamePanel gamePanel = new GamePanel();


        int currentFloor = 1;
        Floor floor1 = new Floor();

        Room start = new Room();
        start.fill(1,1,15,15, "X");
        start.fill(2,2,14,14, "O");
        start.fill(6,15,10,15, "O");
        start.exits[1] = true;

        Room corridorLR = new Room();
        corridorLR.fill(5,1,11,15, "X");
        corridorLR.fill(6,1,10,15, "O");
        corridorLR.exits[1] = true;
        corridorLR.exits[0] = true;

        Room cross = new Room();
        cross.fill(5,1,11,15, "X");
        cross.fill(1,5,15,11, "X");
        cross.fill(6,1,10,15, "O");
        cross.fill(1,6,15,10, "O");
        cross.exits[0] = true;
        cross.exits[1] = true;
        cross.exits[2] = true;
        cross.exits[3] = true;

        Room TRoad = new Room();
        cross.fill(5,1,11,15, "X");
        cross.fill(5,5,15,11, "X");
        cross.fill(6,1,10,15, "O");
        cross.fill(6,6,15,10, "O");

        start.rooms[1] = corridorLR;
        corridorLR.rooms[0] = start;
        corridorLR.rooms[1] = cross;

        floor1.floorMap[2][2] = start;
        floor1.floorMap[2][3] = corridorLR;
        floor1.floorMap[2][4] = TRoad;
        int[] playerPosition = {2, 2};

        player.x = 8;
        player.y = 8;


        boolean working = true;
        String nextMove;
        System.out.println("Movement instructions: ");
        System.out.println("W - Up");
        System.out.println("S - Down");
        System.out.println("A - Left");
        System.out.println("D - Rigth");

        currentRoom = floor1.floorMap[playerPosition[0]][playerPosition[1]];
        currentRoom.writeRoom(player.x, player.y);

        gamePanel.initializemap(currentRoom, player);
        add(gamePanel);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int[] XYbefore = {player.x, player.y};
                gamePanel.handleKeyPress(e, currentRoom, player);
                System.out.println(XYbefore[0]+", "+XYbefore[1]);
                System.out.println(player.x+", "+player.y);
                XYbefore[0] = XYbefore[0] - player.x;
                XYbefore[1] = XYbefore[1] - player.y;
                System.out.println(XYbefore[0]+", "+XYbefore[1]);
                if(XYbefore[0] == 14){
                    currentRoom = floor1.floorMap[playerPosition[0] + 1][playerPosition[1]];
                    playerPosition[0]++;
                    gamePanel.initializemap(currentRoom, player);
                    gamePanel.repaint();
                }
                else if(XYbefore[0] == -14){
                    currentRoom = floor1.floorMap[playerPosition[0] - 1][playerPosition[1]];
                    playerPosition[0]--;
                    gamePanel.initializemap(currentRoom, player);
                    gamePanel.repaint();
                }
                else if(XYbefore[1] == 14){
                    currentRoom = floor1.floorMap[playerPosition[0]][playerPosition[1] + 1];
                    playerPosition[1]++;
                    gamePanel.initializemap(currentRoom, player);
                    gamePanel.repaint();
                }
                else if(XYbefore[1] == -14){
                    currentRoom = floor1.floorMap[playerPosition[0]][playerPosition[1] - 1];
                    playerPosition[1]--;
                    gamePanel.initializemap(currentRoom, player);
                    gamePanel.repaint();
                }
                System.out.println(playerPosition[0]+", "+playerPosition[1]);
            }
        });


        pack();
        setVisible(true);
    }


}


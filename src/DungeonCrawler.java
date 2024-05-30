import java.util.*;
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
    Floor currentFloor;
    Floor testFloor;
    List<Room> rooms;
    Room start;
    Room corridorLR;
    Room cross;
    Room TRoad;
    Room turn;
    Room ending;
    Random rand = new Random();
    List<Room> presets = new ArrayList<Room>();

    int titleSize = 32;
    DungeonCrawler(){
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        GamePanel gamePanel = new GamePanel();


        int currentFloorNum = 1;
        currentFloor = new Floor();


        start = new Room();
        start.fill(1,1,15,15, "X");
        start.fill(2,2,14,14, "O");
        start.fill(6,15,10,15, "O");
        start.exits[1] = true;

        corridorLR = new Room();
        corridorLR.fill(5,1,11,15, "X");
        corridorLR.fill(6,1,10,15, "O");
        corridorLR.exits[1] = true;
        corridorLR.exits[0] = true;
        corridorLR.numOfExits = 2;

        cross = new Room();
        cross.fill(5,1,11,15, "X");
        cross.fill(1,5,15,11, "X");
        cross.fill(6,1,10,15, "O");
        cross.fill(1,6,15,10, "O");
        cross.exits[0] = true;
        cross.exits[1] = true;
        cross.exits[2] = true;
        cross.exits[3] = true;
        cross.numOfExits = 4;

        TRoad = new Room();
        TRoad.fill(5,1,11,15, "X");
        TRoad.fill(5,5,15,11, "X");
        TRoad.fill(6,1,10,15, "O");
        TRoad.fill(6,6,15,10, "O");
        TRoad.exits[0] = true;
        TRoad.exits[1] = true;
        TRoad.exits[3] = true;
        TRoad.numOfExits = 3;

        turn = new Room();
        turn.fill(5,1,11,11, "X");
        turn.fill(5,5,15,11, "X");
        turn.fill(6,1,10,10, "O");
        turn.fill(6,6,15,10, "O");
        turn.exits[0] = true;
        turn.exits[3] = true;
        turn.numOfExits = 2;

        ending = new Room();
        ending.fill(1,8,15,15, "X");
        ending.fill(5,1,11,9, "X");
        ending.fill(2,9,14,14, "O");
        ending.fill(6,1,10,9, "O");
        ending.exits[0] = true;
        ending.numOfExits = 1;


        start.rooms[1] = corridorLR;
        corridorLR.rooms[0] = start;
        corridorLR.rooms[1] = cross;



        currentFloor.floorMap[2][2] = start;
        currentFloor.floorMap[2][3] = corridorLR;
        currentFloor.floorMap[2][4] = turn;
        int[] playerPosition = {0, 5};

        presets.add(turn);
        presets.add(corridorLR);
        presets.add(cross);
        presets.add(TRoad);
        presets.add(ending);


        player.x = 7;
        player.y = 7;

        generateFloor(0,5);
        boolean working = true;
        String nextMove;
        System.out.println("Movement instructions: ");
        System.out.println("W - Up");
        System.out.println("S - Down");
        System.out.println("A - Left");
        System.out.println("D - Rigth");

        currentRoom = testFloor.floorMap[playerPosition[0]][playerPosition[1]];

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
                    currentRoom = testFloor.floorMap[playerPosition[0] + 1][playerPosition[1]];
                    playerPosition[0]++;
                    gamePanel.initializemap(currentRoom, player);
                    gamePanel.repaint();
                }
                else if(XYbefore[0] == -14){
                    currentRoom = testFloor.floorMap[playerPosition[0] - 1][playerPosition[1]];
                    playerPosition[0]--;
                    gamePanel.initializemap(currentRoom, player);
                    gamePanel.repaint();
                }
                else if(XYbefore[1] == 14){
                    currentRoom = testFloor.floorMap[playerPosition[0]][playerPosition[1] + 1];
                    playerPosition[1]++;
                    gamePanel.initializemap(currentRoom, player);
                    gamePanel.repaint();
                }
                else if(XYbefore[1] == -14){
                    currentRoom = testFloor.floorMap[playerPosition[0]][playerPosition[1] - 1];
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

    Room turnClockwise(Room room){
        Room spareRoom = room;
        room = new Room();
        for(int i = 0; i < 15; i++){
            for(int j = 0; j < 15; j++){
                room.roomTexture[i][j] = spareRoom.roomTexture[14-j][i];
            }
        }
        room.exits[0] = false;
        room.exits[1] = false;
        room.exits[2] = false;
        room.exits[3] = false;
        if(spareRoom.exits[0] == true){
            room.exits[2] = true;
        }
        if(spareRoom.exits[1] == true){
            room.exits[3] = true;
        }
        if(spareRoom.exits[2] == true){
            room.exits[1] = true;
        }
        if(spareRoom.exits[3] == true){
            room.exits[0] = true;
        }
        return room;
    }

    void generateFloor(int x, int y){
        testFloor = new Floor();
        testFloor.floorMap[x][y] = ending;
        testFloor.floorMap[1][3] = TRoad;
        int choice = rand.nextInt(3);
        System.out.println(choice);
        testFloor.floorMap[x][y] = turnClockwise(testFloor.floorMap[x][y]);
        testFloor.floorMap[x][y] = turnClockwise(testFloor.floorMap[x][y]);
        while (choice != 1){
            choice = rand.nextInt(6);
            System.out.println(choice);
            //testFloor.floorMap[x][y] = turnClockwise(testFloor.floorMap[x][y]);
        };
//        try{
//            System.out.println(x+" coords "+y);
//            if(testFloor.floorMap[x][y].exits[1] && testFloor.floorMap[x][y+1] == null){
//                generateRoom(x, y+1, 0);
//            }
//        }
//        catch(Exception e){
//            System.out.println("Error");
//        }
        if(testFloor.floorMap[x][y].exits[1] && testFloor.floorMap[x][y+1] == null){
            generateRoom(x, y+1, 0);
        }
    }

    private void generateRoom(int x, int y, int entr) {
        List<Room> tempRooms = new ArrayList<>();
        tempRooms.addAll(presets);
        int maxExits = 4;
        int minExits = 0;
        if(x + 1 == 7){maxExits--;}
        else{
            if(testFloor.floorMap[x+1][y] != null){
                if(testFloor.floorMap[x+1][y].exits[2]){minExits++;}
                else{maxExits--;}
            }
        }
        if(x - 1 == -1){maxExits--;}
        else{
            if(testFloor.floorMap[x-1][y] != null){
                if(testFloor.floorMap[x-1][y].exits[3]){minExits++;}
                else{maxExits--;}
            }
        }
        if(y + 1 == 7){maxExits--;}
        else{
            if(testFloor.floorMap[x][y+1] != null){
                if(testFloor.floorMap[x][y+1].exits[0]){minExits++;}
                else{maxExits--;}
            }
        }
        if(y - 1 == -1){maxExits--;}
        else{
            if(testFloor.floorMap[x][y-1] != null){
                if(testFloor.floorMap[x][y-1].exits[1]){minExits++;}
                else{maxExits--;}
            }
        }
        System.out.println(minExits+" exitsy "+maxExits);
        for(int i = 0; i < tempRooms.size(); i++){
            if(tempRooms.get(i).numOfExits < minExits || tempRooms.get(i).numOfExits > maxExits){tempRooms.remove(i);
            i = 0;}

        }
        System.out.println(x+" coords "+y);
        System.out.println(tempRooms.size());

        for(int i = 0; i < testFloor.floorMap.length; i++){
            for(int j = 0; j < testFloor.floorMap.length; j++){
                System.out.print(testFloor.floorMap[i][j]);
            }
            System.out.println("");
        }

        int i = 0;
        boolean isSet = false;
        while(i < tempRooms.size() || !isSet){
            int curWay = 0;
            int number = rand.nextInt(tempRooms.size());
            testFloor.floorMap[x][y] = tempRooms.get(number);
            while(curWay < 4){
                System.out.println(testFloor.floorMap[x][y].exits[0]+", "+testFloor.floorMap[x][y].exits[1]+", "+
                        testFloor.floorMap[x][y].exits[2]+", "+testFloor.floorMap[x][y].exits[3]);
                boolean found = true;
                if(testFloor.floorMap[x][y].exits[0]){
                    if(y - 1 == -1){found = false;}
                    else if(testFloor.floorMap[x][y - 1] == null){}
                    else if(!testFloor.floorMap[x][y - 1].exits[1]){found = false;}
                }
                if(testFloor.floorMap[x][y].exits[1]){
                    if(y + 1 == 7){found = false;}
                    else if(testFloor.floorMap[x][y + 1] == null){}
                    else if(!testFloor.floorMap[x][y + 1].exits[0]){found = false;}
                }
                if(testFloor.floorMap[x][y].exits[2]){
                    if(x - 1 == -1){found = false;}
                    else if(testFloor.floorMap[x - 1][y] == null){}
                    else if(!testFloor.floorMap[x - 1][y].exits[3]){found = false;}
                }
                if(testFloor.floorMap[x][y].exits[3]){
                    if(x + 1 == 7){found = false;}
                    else if(testFloor.floorMap[x + 1][y] == null){}
                    else if(!testFloor.floorMap[x + 1][y].exits[2]){found = false;}
                }
                if(found){
                    isSet = true;
                    break;
                }
                curWay++;
                testFloor.floorMap[x][y] = turnClockwise(testFloor.floorMap[x][y]);
            }
            if(isSet){
                break;
            }
            i++;
            tempRooms.remove(number);
        }

        if(testFloor.floorMap[x][y].exits[0]){
            if(testFloor.floorMap[x][y - 1] == null){
                generateRoom(x, y-1, 1);
            }
        }
        if(testFloor.floorMap[x][y].exits[1]){
            if(testFloor.floorMap[x][y + 1] == null){
                generateRoom(x, y+1, 0);
            }
        }
        if(testFloor.floorMap[x][y].exits[2]){
            if(testFloor.floorMap[x - 1][y] == null){
                generateRoom(x-1, y, 3);
            }
        }
        if(testFloor.floorMap[x][y].exits[3]){
            if(testFloor.floorMap[x + 1][y] == null){
                generateRoom(x+1, y, 2);
            }
        }
    }


}


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
    int[] exit;
    Random rand = new Random();
    int[] playerPosition;
    List<Room> presets = new ArrayList<Room>();
    int currentFloorNum;
    int currentColor;

    int titleSize = 32;
    DungeonCrawler(){
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        GamePanel gamePanel = new GamePanel();


        currentFloorNum = 1;
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
        playerPosition = new int[]{rand.nextInt(5) + 1, rand.nextInt(5) + 1};

        presets.add(turn);
        presets.add(corridorLR);
        presets.add(cross);
        presets.add(TRoad);
        presets.add(ending);


        player.x = 7;
        player.y = 7;

        generateFloor(playerPosition[0],playerPosition[1]);
        boolean working = true;
        String nextMove;

        currentRoom = testFloor.floorMap[playerPosition[0]][playerPosition[1]];

        gamePanel.initializemap(currentRoom, player, exit[0], exit[1], playerPosition[0], playerPosition[1], currentColor);
        add(gamePanel);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int[] XYbefore = {player.x, player.y};
                int floorBefore = currentFloorNum;
                gamePanel.handleKeyPress(e, currentRoom, player, currentFloorNum);
                //System.out.println(XYbefore[0]+", "+XYbefore[1]);
                //System.out.println(player.x+", "+player.y);
                XYbefore[0] = XYbefore[0] - player.x;
                XYbefore[1] = XYbefore[1] - player.y;
                //System.out.println(XYbefore[0]+", "+XYbefore[1]);
                if(XYbefore[0] == 14){
                    currentRoom = testFloor.floorMap[playerPosition[0] + 1][playerPosition[1]];
                    playerPosition[0]++;
                    gamePanel.initializemap(currentRoom, player, exit[0], exit[1], playerPosition[0], playerPosition[1], currentColor);
                    gamePanel.repaint();
                }
                else if(XYbefore[0] == -14){
                    currentRoom = testFloor.floorMap[playerPosition[0] - 1][playerPosition[1]];
                    playerPosition[0]--;
                    gamePanel.initializemap(currentRoom, player, exit[0], exit[1], playerPosition[0], playerPosition[1], currentColor);
                    gamePanel.repaint();
                }
                else if(XYbefore[1] == 14){
                    currentRoom = testFloor.floorMap[playerPosition[0]][playerPosition[1] + 1];
                    playerPosition[1]++;
                    gamePanel.initializemap(currentRoom, player, exit[0], exit[1], playerPosition[0], playerPosition[1], currentColor);
                    gamePanel.repaint();
                }
                else if(XYbefore[1] == -14){
                    currentRoom = testFloor.floorMap[playerPosition[0]][playerPosition[1] - 1];
                    playerPosition[1]--;
                    gamePanel.initializemap(currentRoom, player, exit[0], exit[1], playerPosition[0], playerPosition[1], currentColor);
                    gamePanel.repaint();
                }

                if(currentFloorNum < gamePanel.getCurrentFloorNum){
                    currentFloorNum++;
                    playerPosition = new int[]{rand.nextInt(5) + 1, rand.nextInt(5) + 1};
                    player.x = 7;
                    player.y = 7;
                    generateFloor(playerPosition[0],playerPosition[1]);
                    currentRoom = testFloor.floorMap[playerPosition[0]][playerPosition[1]];
                    gamePanel.initializemap(currentRoom, player, exit[0], exit[1], playerPosition[0], playerPosition[1], currentColor);
                    gamePanel.repaint();
                    player.x = 7;
                    player.y = 7;
                }
                //System.out.println(playerPosition[0]+", "+playerPosition[1]);
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
        if(spareRoom.exits[0]){
            room.exits[2] = true;
        }
        if(spareRoom.exits[1]){
            room.exits[3] = true;
        }
        if(spareRoom.exits[2]){
            room.exits[1] = true;
        }
        if(spareRoom.exits[3]){
            room.exits[0] = true;
        }
        return room;
    }

    void generateFloor(int x, int y){
        testFloor = new Floor();
        testFloor.floorMap[x][y] = start;
        currentColor = rand.nextInt(9);
        int choice = rand.nextInt(3);
        //System.out.println(choice);
        testFloor.floorMap[x][y] = turnClockwise(testFloor.floorMap[x][y]);
        testFloor.floorMap[x][y] = turnClockwise(testFloor.floorMap[x][y]);
        while (choice != 1){
            choice = rand.nextInt(6);
            //System.out.println(choice);
            testFloor.floorMap[x][y] = turnClockwise(testFloor.floorMap[x][y]);
            if(testFloor.floorMap[x][y].exits[0] && y == 0){
                choice = 0;
            }
            else if(testFloor.floorMap[x][y].exits[1] && y == 7){
                choice = 0;
            }
            else if(testFloor.floorMap[x][y].exits[2] && x == 0){
                choice = 0;
            }
            else if(testFloor.floorMap[x][y].exits[3] && x == 7){
                choice = 0;
            }
        };
        try{
            //System.out.println(x+" coords "+y);
            if(testFloor.floorMap[x][y].exits[1] && testFloor.floorMap[x][y+1] == null){
                generateRoom(x, y+1, 0);
            }
        }
        catch(Exception e){
            System.out.println("Error");
        }
        if(testFloor.floorMap[x][y].exits[0] && testFloor.floorMap[x][y-1] == null){
            generateRoom(x, y-1, 0);
        }
        if(testFloor.floorMap[x][y].exits[1] && testFloor.floorMap[x][y+1] == null){
            generateRoom(x, y+1, 1);
        }
        if(testFloor.floorMap[x][y].exits[2] && testFloor.floorMap[x-1][y] == null){
            generateRoom(x-1, y, 2);
        }
        if(testFloor.floorMap[x][y].exits[3] && testFloor.floorMap[x+1][y] == null){
            generateRoom(x+1, y, 3);
        }
        while(true){
            exit = new int[] {rand.nextInt(testFloor.floorMap.length),rand.nextInt(testFloor.floorMap.length)};
            if(testFloor.floorMap[exit[0]][exit[1]] != null &&
                    testFloor.floorMap[exit[0]][exit[1]] != testFloor.floorMap[playerPosition[0]][playerPosition[1]]){
                break;
            }
        }
        //generateAll();
        findExit(exit[0], exit[1],playerPosition[0], playerPosition[1]);
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
        //System.out.println(minExits+" exitsy "+maxExits);
        for(int i = 0; i < tempRooms.size(); i++){
            if(tempRooms.get(i).numOfExits < minExits || tempRooms.get(i).numOfExits > maxExits){tempRooms.remove(i);
            i = 0;}

        }
        //System.out.println(x+" coords "+y);
        //System.out.println(tempRooms.size());

//        for(int i = 0; i < testFloor.floorMap.length; i++){
//            for(int j = 0; j < testFloor.floorMap.length; j++){
//                System.out.print(testFloor.floorMap[i][j]);
//            }
//            System.out.println("");
//        }

        int i = 0;
        boolean isSet = false;
        while(i < tempRooms.size() || !isSet){
            int curWay = 0;
            int number = rand.nextInt(tempRooms.size());
            testFloor.floorMap[x][y] = tempRooms.get(number);
            while(curWay < 4){
                //System.out.println(testFloor.floorMap[x][y].exits[0]+", "+testFloor.floorMap[x][y].exits[1]+", "+
                        //testFloor.floorMap[x][y].exits[2]+", "+testFloor.floorMap[x][y].exits[3]);
                boolean found = true;
//                if(testFloor.floorMap[x][y].exits[0]){
//                    if(y - 1 == -1){found = false;}
//                    else if(testFloor.floorMap[x][y - 1] == null){}
//                    else if(!testFloor.floorMap[x][y - 1].exits[1]){found = false;}
//                }
//                if(testFloor.floorMap[x][y].exits[1]){
//                    if(y + 1 == 7){found = false;}
//                    else if(testFloor.floorMap[x][y + 1] == null){}
//                    else if(!testFloor.floorMap[x][y + 1].exits[0]){found = false;}
//                }
//                if(testFloor.floorMap[x][y].exits[2]){
//                    if(x - 1 == -1){found = false;}
//                    else if(testFloor.floorMap[x - 1][y] == null){}
//                    else if(!testFloor.floorMap[x - 1][y].exits[3]){found = false;}
//                }
//                if(testFloor.floorMap[x][y].exits[3]){
//                    if(x + 1 == 7){found = false;}
//                    else if(testFloor.floorMap[x + 1][y] == null){}
//                    else if(!testFloor.floorMap[x + 1][y].exits[2]){found = false;}
//                }
                if(y - 1 != -1){
                    if(testFloor.floorMap[x][y - 1] != null){
                        if(testFloor.floorMap[x][y - 1].exits[1] && !testFloor.floorMap[x][y].exits[0]){found = false;
                            //System.out.println("Fail not matching");
                             }
                        else if(!testFloor.floorMap[x][y - 1].exits[1] && testFloor.floorMap[x][y].exits[0]){found = false;
                            //System.out.println("Fail not matching");
                             }
                    }
                }
                else{
                    if(testFloor.floorMap[x][y].exits[0]){found = false; //System.out.println("Fail out of bounds");
                    }
                }
                if(y + 1 != 7){
                    if(testFloor.floorMap[x][y + 1] != null){
                        if(testFloor.floorMap[x][y + 1].exits[0] && !testFloor.floorMap[x][y].exits[1]){found = false;
                            //System.out.println("Fail not matching");
                             }
                        else if(!testFloor.floorMap[x][y + 1].exits[0] && testFloor.floorMap[x][y].exits[1]){found = false;
                            //System.out.println("Fail not matching");
                                 }
                    }
                }
                else{
                    if(testFloor.floorMap[x][y].exits[1]){found = false; //System.out.println("Fail out of bounds");
                    }
                }
                if(x - 1 != -1){
                    if(testFloor.floorMap[x - 1][y] != null){
                        if(testFloor.floorMap[x - 1][y].exits[3] && !testFloor.floorMap[x][y].exits[2]){found = false;
//                            System.out.println("Fail not matching");
                        }
                        else if(!testFloor.floorMap[x - 1][y].exits[3] && testFloor.floorMap[x][y].exits[2]){found = false;
//                            System.out.println("Fail not matching");
                        }
                    }
                }
                else{
                    if(testFloor.floorMap[x][y].exits[2]){found = false; //System.out.println("Fail out of bounds");
                    }
                }
                if(x + 1 != 7){
                    if(testFloor.floorMap[x + 1][y] != null){
                        if(testFloor.floorMap[x + 1][y].exits[2] && !testFloor.floorMap[x][y].exits[3]){found = false;
                            //System.out.println("Fail not matching");
                        }
                        else if(!testFloor.floorMap[x + 1][y].exits[2] && testFloor.floorMap[x][y].exits[3]){found = false;
                            //System.out.println("Fail not matching");
                        }
                    }
                }
                else{
                    if(testFloor.floorMap[x][y].exits[3]){found = false; //System.out.println("Fail out of bounds");
                    }
                }

                if(found){
                    isSet = true;
                    break;
                }
                else{
                    curWay++;
                    testFloor.floorMap[x][y] = turnClockwise(testFloor.floorMap[x][y]);
                }
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

    void generateAll(){
        for(int i = 0; i < testFloor.floorMap.length; i++){
            for(int j = 0; j < 15; j++){
                for(int k = 0; k < testFloor.floorMap[i].length; k++){
                    for(int l = 0; l < 15; l++){
                        if(testFloor.floorMap[i][k] == null){
                            System.out.print("\033[0;31m"+"N"+"\033[0;37m");
                        }
                        else if(testFloor.floorMap[i][k].roomTexture[j][l].equals("X")){
                            System.out.print("\033[0;33m"+"X"+"\033[0;37m");
                        }
                        else if(testFloor.floorMap[i][k].roomTexture[j][l].equals("O")){
                            System.out.print("\033[0;30m"+"X"+"\033[0;37m");
                        }
                        else{
                            System.out.print(testFloor.floorMap[i][k].roomTexture[j][l]);
                        }
                    }
                }
                System.out.println("");
            }
        }
    }


    int[][] findingWay;
    int[][] previous;
    void findExit(int x, int y, int currX, int currY){
        findingWay = new int[][]{
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0}};

        previous = new int[][]{
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0}};
        List<Integer> shortest = new ArrayList<Integer>() {};
        for(int i = 0; i < testFloor.floorMap.length; i++){
            for(int j = 0; j < testFloor.floorMap.length; j++){
                if(testFloor.floorMap[i][j] != null){
                    findingWay[i][j] = 99;
                    previous[i][j] = -1;
                }
                else{
                    findingWay[i][j] = -1;
                }
            }
        }
        findingWay[currX][currY] = 0;
        findPart(currX, currY, 0);
        int curX = x;
        int curY = y;
        while (curX != currX || curY != currY){
            shortest.add(previous[curX][curY]);
            if(previous[curX][curY] == 0){
                curY--;
            }
            else if(previous[curX][curY] == 1){
                curY++;
            }
            else if(previous[curX][curY] == 2){
                curX--;
            }
            else if(previous[curX][curY] == 3){
                curX++;
            }
        }

        System.out.println("Jak wyjść: ");

        for(int i = shortest.size() - 1; i >= 0; i--){
            switch (shortest.get(i)){
                case 0:
                    System.out.print("W prawo, ");
                    break;
                case 1:
                    System.out.print("W lewo, ");
                    break;
                case 2:
                    System.out.print("W dół, ");
                    break;
                case 3:
                    System.out.print("W górę, ");
                    break;
            }
        }
        System.out.println("");
        System.out.println("");
    }

    void findPart(int x, int y, int previousNumb){
        findingWay[x][y] = previousNumb;
//        System.out.println(testFloor.floorMap[x][y].exits[0]+", "+testFloor.floorMap[x][y].exits[1]+", "+
//                testFloor.floorMap[x][y].exits[2]+", "+testFloor.floorMap[x][y].exits[3]);
//        if(testFloor.floorMap[x][y].exits[0]){
//            System.out.println(testFloor.floorMap[x][y-1].shortestWay+", "+(previousNumb + 1)
//            +", "+x+", "+(y-1));
//            if(testFloor.floorMap[x][y-1].shortestWay > (previousNumb + 1)){
//                testFloor.floorMap[x][y-1].shortestWay = previousNumb + 1;
//                testFloor.floorMap[x][y-1].previous = 1;
//                findPart(x, y-1, previousNumb+1);
//            }
//        }
//        if(testFloor.floorMap[x][y].exits[1]){
//            System.out.println(testFloor.floorMap[x][y+1].shortestWay+", "+(previousNumb + 1)
//                    +", "+x+", "+(y+1));
//            if(testFloor.floorMap[x][y+1].shortestWay > (previousNumb + 1)){
//                testFloor.floorMap[x][y+1].shortestWay = previousNumb + 1;
//                testFloor.floorMap[x][y+1].previous = 0;
//                findPart(x, y+1, previousNumb+1);
//            }
//        }
//        if(testFloor.floorMap[x][y].exits[2]){
//            System.out.println(testFloor.floorMap[x-1][y].shortestWay+", "+(previousNumb + 1)
//                    +", "+(x-1)+", "+y);
//            if(testFloor.floorMap[x-1][y].shortestWay > (previousNumb + 1)){
//                testFloor.floorMap[x-1][y].shortestWay = previousNumb + 1;
//                testFloor.floorMap[x-1][y].previous = 3;
//                findPart(x-1, y, previousNumb+1);
//            }
//        }
//        if(testFloor.floorMap[x][y].exits[3]){
//            System.out.println(testFloor.floorMap[x+1][y].shortestWay+", "+(previousNumb + 1)
//                    +", "+(x+1)+", "+y);
//            if(testFloor.floorMap[x+1][y].shortestWay > (previousNumb + 1)){
//                testFloor.floorMap[x+1][y].shortestWay = previousNumb + 1;
//                testFloor.floorMap[x+1][y].previous = 2;
//                findPart(x+1, y, previousNumb+1);
//            }
//        }

        if(testFloor.floorMap[x][y].exits[0]){
            if(findingWay[x][y-1] == 99){
                findingWay[x][y-1] = previousNumb + 1;
                previous[x][y-1] = 1;
                findPart(x, y-1, previousNumb+1);
            }
            else if(findingWay[x][y-1] > (previousNumb + 1)){
                findingWay[x][y-1] = previousNumb + 1;
                previous[x][y-1] = 1;
                findPart(x, y-1, previousNumb+1);
            }
        }
        if(testFloor.floorMap[x][y].exits[1]){
            if(findingWay[x][y+1] == 99){
                findingWay[x][y+1] = previousNumb + 1;
                previous[x][y+1] = 0;
                findPart(x, y+1, previousNumb+1);
            }
            else if(findingWay[x][y+1] > (previousNumb + 1)){
                findingWay[x][y+1] = previousNumb + 1;
                previous[x][y+1] = 0;
                findPart(x, y+1, previousNumb+1);
            }
        }
        if(testFloor.floorMap[x][y].exits[2]){
            if(findingWay[x-1][y] == 99){
                findingWay[x-1][y] = previousNumb + 1;
                previous[x-1][y] = 3;
                findPart(x-1, y, previousNumb+1);
            }
            else if(findingWay[x-1][y] > (previousNumb + 1)){
                findingWay[x-1][y] = previousNumb + 1;
                previous[x-1][y] = 3;
                findPart(x-1, y, previousNumb+1);
            }
        }
        if(testFloor.floorMap[x][y].exits[3]){
            if(findingWay[x+1][y] == 99){
                findingWay[x+1][y] = previousNumb + 1;
                previous[x+1][y] = 2;
                findPart(x+1, y, previousNumb+1);
            }
            else if(findingWay[x+1][y] > (previousNumb + 1)){
                findingWay[x+1][y] = previousNumb + 1;
                previous[x+1][y] = 2;
                findPart(x+1, y, previousNumb+1);
            }
        }

    }


}


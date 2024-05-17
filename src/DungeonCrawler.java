import java.util.Scanner;
public class DungeonCrawler {

    DungeonCrawler(){
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

        start.rooms[1] = corridorLR;
        corridorLR.rooms[0] = start;

        Scanner reader = new Scanner(System.in);

        System.out.println("Choose your character: ");
        String character = reader.nextLine();

        if(character.length() > 1){
            System.out.println("incorrect character: ");
            return;
        }
        Player player = new Player();
        player.x = 8;
        player.y = 8;
        player.appearance = character;


        boolean working = true;
        String nextMove;
        System.out.println("Movement instructions: ");
        System.out.println("W - Up");
        System.out.println("S - Down");
        System.out.println("A - Left");
        System.out.println("D - Rigth");

        Room currentRoom = start;
        currentRoom.writeRoom(player.x, player.y, character);
        while(working){
            nextMove = "";
            try {
                nextMove = reader.nextLine();
                if(!nextMove.equals("w") && !nextMove.equals("s") && !nextMove.equals("a") && !nextMove.equals("d")){
                    working = false;
                }
            }catch (Exception e){
                working = false;
            }

            if(nextMove.equals("w")){
                if(player.x + 1 > 15){
                    currentRoom = currentRoom.rooms[2];
                    player.x = 0;
                }
                if(!currentRoom.roomTexture[player.x-2][player.y-1].equals("X")){
                    player.x--;
                }
            }
            else if(nextMove.equals("s")){
                if(player.x - 1 < 1){
                    currentRoom = currentRoom.rooms[3];
                    player.y = 16;
                }
                if(!currentRoom.roomTexture[player.x][player.y-1].equals("X")){
                    player.x++;
                }
            }
            else if(nextMove.equals("a")){
                if(player.y - 1 < 1){
                    currentRoom = currentRoom.rooms[0];
                    player.y = 16;
                }
                if(!currentRoom.roomTexture[player.x-1][player.y-2].equals("X")){
                    player.y--;
                }
            }
            else if(nextMove.equals("d")){
                if(player.y + 1 > 15){
                    currentRoom = currentRoom.rooms[1];
                    player.y = 0;
                }
                if(!currentRoom.roomTexture[player.x-1][player.y].equals("X")){
                    player.y++;
                }
            }
            currentRoom.writeRoom(player.x, player.y, character);
            System.out.println(player.x+", "+ player.y);
        }

    }
}

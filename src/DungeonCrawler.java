import java.util.Scanner;
public class DungeonCrawler {

    DungeonCrawler(){
        Room start = new Room();
        start.fill(1,1,15,15, "X");
        start.fill(2,2,14,14, "O");
        start.fill(6,15,10,15, "O");


        Scanner reader = new Scanner(System.in);

        System.out.println("Choose your character: ");
        String character = String.valueOf(reader.nextInt());
        if(character.length() > 1){
            System.out.println("incorrect character: ");
            return;
        }
        Player player = new Player();
        player.x = 8;
        player.y = 8;
        player.appearance = character;

        start.writeRoom(player.x, player.y);
    }
}

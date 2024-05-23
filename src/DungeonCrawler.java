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
    int titleSize = 32;
    DungeonCrawler(){
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        GamePanel gamePanel = new GamePanel();


        int currentFloor = 1;

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

        start.rooms[1] = corridorLR;
        corridorLR.rooms[0] = start;
        corridorLR.rooms[1] = cross;

        Scanner reader = new Scanner(System.in);

        System.out.println("Choose your character: ");
        String character = reader.nextLine();

        if(character.length() > 1){
            System.out.println("incorrect character: ");
            return;
        }

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

        add(gamePanel);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                gamePanel.handleKeyPress(e, currentRoom, player);
            }
        });


        pack();
        setVisible(true);
    }


}


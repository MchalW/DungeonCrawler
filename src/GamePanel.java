import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {
    String[][] roomTexture;
    Player player;

    int titleSize = 32;
    GamePanel(){
        setPreferredSize(new Dimension(15 * titleSize, 15 * titleSize));
        setBackground(Color.BLACK);
    }

    public void initializemap(Room currentRoom, Player play) {
        roomTexture = currentRoom.roomTexture;
        player = play;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int x = 0; x < 15; x++){
            for(int y = 0; y < 15; y++) {
                if (x == player.x && y == player.y) {
                    g.setColor(Color.CYAN);
                    g.fillRect(player.y * titleSize, player.x * titleSize, titleSize, titleSize);
                } else if (roomTexture[x][y] == "X") {
                    g.setColor(Color.RED);
                    g.fillRect(y * titleSize, x * titleSize, titleSize, titleSize);
                } else if (roomTexture[x][y] == "0") {
                    g.setColor(Color.BLUE);
                    g.fillRect(y * titleSize, x * titleSize, titleSize, titleSize);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(y * titleSize, x * titleSize, titleSize, titleSize);
                }
            }

        }
    }

    public void handleKeyPress(KeyEvent e, Room currentRoom, Player player) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                try{
                    if(!currentRoom.roomTexture[player.x-1][player.y].equals("X")){
                        player.x--;
                    }
                }
                catch (IndexOutOfBoundsException up){
                    player.x = 14;
                }
                break;
            case KeyEvent.VK_DOWN:
                try{
                    if(!currentRoom.roomTexture[player.x+1][player.y].equals("X")){
                        player.x++;
                    }
                }
                catch (IndexOutOfBoundsException up){
                player.x = 0;
                }
            break;
            case KeyEvent.VK_LEFT:
                try{
                    if(!currentRoom.roomTexture[player.x][player.y-1].equals("X")){
                        player.y--;
                    }
                }
                catch (IndexOutOfBoundsException up){
                    player.y = 14;
                }
                break;
            case KeyEvent.VK_RIGHT:
                try{
                    if(!currentRoom.roomTexture[player.x][player.y+1].equals("X")){
                        player.y++;
                    }
                }
                catch (IndexOutOfBoundsException up){
                    player.y = 0;
                    initializemap(currentRoom, player);                }
                break;
        }
        this.repaint();
    }
}

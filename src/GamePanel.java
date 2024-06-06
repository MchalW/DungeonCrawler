import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {
    String[][] roomTexture;
    Player player;
    int exitX;
    int exitY;
    int currentRoomX;
    int currentRoomY;
    int getCurrentFloorNum;
    int color;

    int titleSize = 32;
    GamePanel(){
        setPreferredSize(new Dimension(15 * titleSize, 15 * titleSize));
        setBackground(Color.BLACK);
    }

    public void initializemap(Room currentRoom, Player play, int exX, int exY, int curX, int curY, int curColor) {
        roomTexture = currentRoom.roomTexture;
        player = play;
        exitX = exX;
        exitY = exY;
        currentRoomX = curX;
        currentRoomY = curY;
        color = curColor;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int x = 0; x < 15; x++){
            for(int y = 0; y < 15; y++) {
                if (x == player.x && y == player.y) {
                    g.setColor(Color.CYAN);
                    g.fillRect(player.y * titleSize, player.x * titleSize, titleSize, titleSize);
                } else if (roomTexture[x][y] == "X") {
                    switch (color){
                        case 0:
                            g.setColor(Color.ORANGE);
                            break;
                        case 1:
                            g.setColor(Color.RED);
                            break;
                        case 2:
                            g.setColor(Color.YELLOW);
                            break;
                        case 3:
                            g.setColor(Color.BLUE);
                            break;
                        case 4:
                            g.setColor(Color.GREEN);
                            break;
                        case 5:
                            g.setColor(Color.LIGHT_GRAY);
                            break;
                        case 6:
                            g.setColor(Color.PINK);
                            break;
                        case 7:
                            g.setColor(Color.MAGENTA);
                            break;
                        case 8:
                            g.setColor(new Color(139,69,19));
                            break;
                    }
                    g.fillRect(y * titleSize, x * titleSize, titleSize, titleSize);
                } else if (roomTexture[x][y] == "0") {
                    g.setColor(Color.BLACK);
                    g.fillRect(y * titleSize, x * titleSize, titleSize, titleSize);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(y * titleSize, x * titleSize, titleSize, titleSize);
                }
                if(currentRoomX == exitX && currentRoomY == exitY){
                    g.setColor(Color.RED);
                    g.fillRect(7 * titleSize, 7 * titleSize, titleSize, titleSize);
                }
            }

        }
        //System.out.println(currentRoomX+", , "+currentRoomY);
        //System.out.println(exitX+", , "+exitY);
    }

    public void handleKeyPress(KeyEvent e, Room currentRoom, Player player, int floorNum) {
        getCurrentFloorNum = floorNum;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                try{
                    if(!currentRoom.roomTexture[player.x-1][player.y].equals("X")){
                        player.x--;
                    }
                    if(currentRoomX == exitX && currentRoomY == exitY && player.x == 7 &&  player.y == 7){
                        getCurrentFloorNum++;
                    }
                }
                catch (IndexOutOfBoundsException up){
                    player.x = 14;
                    currentRoomX--;
                }
                break;
            case KeyEvent.VK_DOWN:
                try{
                    if(!currentRoom.roomTexture[player.x+1][player.y].equals("X")){
                        player.x++;
                    }
                    if(currentRoomX == exitX && currentRoomY == exitY && player.x == 7 &&  player.y == 7){
                        getCurrentFloorNum++;
                    }
                }
                catch (IndexOutOfBoundsException up){
                player.x = 0;
                currentRoomX++;
                }
            break;
            case KeyEvent.VK_LEFT:
                try{
                    if(!currentRoom.roomTexture[player.x][player.y-1].equals("X")){
                        player.y--;
                    }
                    if(currentRoomX == exitX && currentRoomY == exitY && player.x == 7 &&  player.y == 7){
                        getCurrentFloorNum++;
                    }
                }
                catch (IndexOutOfBoundsException up){
                    player.y = 14;
                    currentRoomY--;
                }
                break;
            case KeyEvent.VK_RIGHT:
                try{
                    if(!currentRoom.roomTexture[player.x][player.y+1].equals("X")){
                        player.y++;
                    }
                    if(currentRoomX == exitX && currentRoomY == exitY && player.x == 7 &&  player.y == 7){
                        getCurrentFloorNum++;
                    }
                }
                catch (IndexOutOfBoundsException up){
                    player.y = 0;
                    currentRoomY++;
                    initializemap(currentRoom, player, exitX, exitY, currentRoomX, currentRoomY,color);                }
                break;
        }
        this.repaint();
    }
}

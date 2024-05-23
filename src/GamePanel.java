import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {

    int titleSize = 32;
    GamePanel(){
        setPreferredSize(new Dimension(15 * titleSize, 15 * titleSize));
        setBackground(Color.BLACK);
    }

    protected void paintComponent(Graphics g, Room curRoom, Player player) {
        super.paintComponent(g);

        for(int x = 0; x < 15; x++){
            for(int y = 0; y < 15; y++) {
                if (x == player.x - 1 && y == player.y - 1) {
                    g.setColor(Color.CYAN);
                    g.fillRect(player.x * titleSize, player.y * titleSize, titleSize, titleSize);
                } else if (curRoom.roomTexture[x][y] == "X") {
                    g.setColor(Color.RED);
                    g.fillRect(x * titleSize, y * titleSize, titleSize, titleSize);
                } else if (curRoom.roomTexture[x][y] == "0") {
                    g.setColor(Color.BLUE);
                    g.fillRect(x * titleSize, y * titleSize, titleSize, titleSize);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(x * titleSize, y * titleSize, titleSize, titleSize);
                }
            }

        }
    }

    public void handleKeyPress(KeyEvent e, Room currentRoom, Player player) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                //if()
                player.y--;
                break;
            case KeyEvent.VK_DOWN:
                player.y++;
                break;
            case KeyEvent.VK_LEFT:
                player.x--;
                break;
            case KeyEvent.VK_RIGHT:
                player.x++;
                break;
        }
        this.repaint();
    }
}

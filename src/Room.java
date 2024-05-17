public class Room {
    String[][] roomTexture = {
            {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
            {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
            {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
            {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
            {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
            {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
            {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
            {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
            {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
            {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
            {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
            {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
            {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
            {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
            {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},};

    boolean[] exits = {false, false, false, false};
    //                 left   right  up     down
    Room[] rooms =     {null, null, null, null};
    void fill(int leftUpX, int leftUpY, int rightDownX, int rightDownY, String material){
        for(int x = leftUpX; x <= rightDownX; x++){
            for(int y = leftUpY; y <= rightDownY; y++){
                roomTexture[x - 1][y - 1] = material;
            }
        }
    }

    void writeRoom(int playerX, int playerY, String player){
        for(int x = 0; x < 15; x++){
            for(int y = 0; y < 15; y++){
                if(x == playerX - 1 && y == playerY - 1){
                    System.out.print("\u001B[35m"+player+"\u001B[0m");
                }
                else if(roomTexture[x][y] == "X"){
                    System.out.print("\u001B[31m"+roomTexture[x][y]+"\u001B[0m");
                }
                else if(roomTexture[x][y] == "0"){
                    System.out.print("\u001B[34m"+roomTexture[x][y]+"\u001B[0m");
                }
                else{
                    System.out.print(roomTexture[x][y]);
                }

            }
            System.out.println("");
        }
        System.out.println("");
    }
}

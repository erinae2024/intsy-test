import java.util.*;

class Tile{
   protected String icon;
   protected ArrayList<Integer> coords = new ArrayList<>();

   Tile(String i, int x, int y){
        this.icon = i;
        this.coords.add(x);
        this.coords.add(y);
   }

   public String getIcon(){
        return this.icon;
   }

   public ArrayList<Integer> getCoords(){
        return this.coords;
   }

   public int getX(){
        return this.coords.get(0);
   }

   public int getY(){
        return this.coords.get(1);
   }
}

class Agent extends Tile{
    private ArrayList<Tile> tasks = new ArrayList<>();
    private int fatigue = 0;

    Agent(String i, int x, int y){
        super(i, x, y);
    }

    /*;
    public turn();
    public scan();
    public end();*/

    //this is basically scan() bc idk how to put it in agent
    public String scan(int MAX_ROW, int MAX_COL, ArrayList<Tile> boardTiles){
        int x = this.coords.get(0);
        int y = this.coords.get(1)+1;
        if(y == 0)
            return "//";
        
        if(y == MAX_COL)
            return "E";
        else{
           for(Tile curr : boardTiles){
                if(curr.getX() == x && curr.getY() == y){
                    return tile.getIcon();
                }
           }
           return null;
        }
    }


}

//x coord is row / what floor agent is on
//y coord is column / what tile agent is on

public class BoardTest {

    public static void printBoard(int MAX_ROW, int MAX_COL, ArrayList<Tile> boardTiles, Tile agent){
        for(int i = 0; i < MAX_ROW; i++){
            for(int j = 0; j < MAX_COL; j++){
                if(agent.getX() == i && agent.getY() == j){
                    if(j != 0 || j != MAX_COL-1)
                        System.out.print("[" + agent.getIcon() + "]"); //if agent is on a room tile
                    else if (j == 0)
                            System.out.print(agent.getIcon() + " "); //if agent is on a stairs tile
                        else
                            System.out.print(agent.getIcon()); //if agent is on elevator tile
                }
                
                else{ //print all other tiles
                    for(Tile curr : boardTiles){
                        if(i == curr.getX() && j == curr.getY())
                            System.out.print(curr.getIcon());
                    }
                }
            }

            System.out.println();
        }
    }
    public static void main(String[] args) {

        int MAX_ROW = 4;
        int MAX_COL = 4; //used for board-set up
        ArrayList<Tile> boardTiles = new ArrayList<>();

        for(int i = 0; i < MAX_ROW; i++){ //setting up basic board elements: stairs, elevators, rooms
            for(int j = 0; j < MAX_COL; j++){
                if(j == 0)
                    boardTiles.add(new Tile("//", i, j));
                else if (j == MAX_COL-1)
                    boardTiles.add(new Tile("E", i, j));
                else
                    boardTiles.add(new Tile("[ ]", i, j));
            }
        }

        
        Tile boss = new Tile("[B]", 2, 2); //setting up boss tile
        for(Tile curr : boardTiles){
            if(curr.getX() == boss.getX() && curr.getY() == boss.getY())
                boardTiles.set(boardTiles.indexOf(curr), boss);
        }

        Tile task1 = new Tile("[T]", 3, 1); //setting up task tile
        for(Tile curr : boardTiles){
            if(curr.getX() == task1.getX() && curr.getY() == task1.getY())
                boardTiles.set(boardTiles.indexOf(curr), task1);
        }

        Agent agent = new Agent("A", 2, 2); //setting up agent tile


        printBoard(MAX_ROW, MAX_COL, boardTiles, agent);
    

    }
    
}

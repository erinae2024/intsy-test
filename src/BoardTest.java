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

    /*public view();
    public turn();
    public scan();
    public end();*/


}

//x coord is row / what floor agent is on
//y coord is column / what tile agent is on

public class BoardTest {

    //this is basically scan() bc idk how to put it in agent
    public String getTileIcon(int x, int y, int MAX_ROW, int MAX_COL, ArrayList<Tile> boardTiles){
        if(y == 0)
            return "//";
        
        if(y == MAX_COL)
            return "E";
        else{
           for(Tile curr : boardTiles){
                if(curr.getX() == x && curr.getY() == y){
                    Tile tile = curr;
                    return tile.getIcon();
                }
           }
           return null;
        }
    }
    public static void main(String[] args) {

        int MAX_ROW;
        int MAX_COL; //used for board-set up
        ArrayList<Tile> boardTiles = new ArrayList<>();


        Tile boss = new Tile("[B]", 2, 2);
        boardTiles.add(boss);

        Tile task = new Tile("[T]", 3, 1);
        boardTiles.add(task);

        Agent agent = new Agent("A", 0, 1);

        System.out.println(boss.getIcon());
        System.out.println(task.getIcon());
        System.out.println(agent.getIcon());

    

    }
    
}

/* FORMER ARRAYLIST STUFF


    [PRINTBOARD FUNCTION]
 * public static void printBoard(int MAX_ROW, int MAX_COL, ArrayList<Tile> boardTiles, Agent agent){
        System.out.println();
        for(int i = 0; i < MAX_ROW; i++){
            for(int j = 0; j < MAX_COL; j++){
                if(agent.getX() == i && agent.getY() == j){
                    if(j != 0 && j != MAX_COL-1)
                        System.out.print("[" + agent.getIcon() + "]"); //if agent is on a room tile
                    else if (j == 0)
                            System.out.print(" " + agent.getIcon()); //if agent is on a stairs tile
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

    [REMOVE TASK FUNCITON]
    public static void removeTask(ArrayList<Tile> boardTiles, ArrayList<Tile> removeTaskTile, Agent agent){

        String taskIcon = "[T]";
        ArrayList<Tile> tasks = agent.getTasks();

        for(Tile curr : boardTiles){
            if(agent.getX() == curr.getX() && agent.getY() == curr.getY()){ //if agent is on curr's coords
                if(taskIcon.equals(curr.getIcon())){ //and curr's coords has the task icon
                    removeTaskTile.add(curr); //add to removeTaskTile list 
                    curr.setIcon("[ ]"); //and set curr's icon to an empty room
                }
                    
            }
        }

       tasks.removeAll(removeTaskTile); //remove task from taskTiles list
       agent.updateTasks(tasks); 

    }

      /* SETUP FOR boardTiles ArrayList ver
        for(int i = 0; i < MAX_ROW; i++){ 
            for(int j = 0; j < MAX_COL; j++){
                if(j == 0)
                    boardTiles.add(new Tile("//", i, j));
                else if (j == MAX_COL-1)
                    boardTiles.add(new Tile("E", i, j));
                else
                    boardTiles.add(new Tile("[ ]", i, j));
            }
        }
        */

         /*** SETTING UP UNIQUE BOARD ELEMENTS: BOSS, TASK,  ETC.  boardTiles ArrayList ver 

        Tile boss = new Tile("[B]", 2, 2); //setting up boss tile
        for(Tile curr : boardTiles){
            if(curr.getX() == boss.getX() && curr.getY() == boss.getY())
                boardTiles.set(boardTiles.indexOf(curr), boss);
        }

        Tile task1 = new Tile("[T]", 3, 1); //setting up task tile
        taskTiles.add(task1);
        for(Tile curr : boardTiles){
            if(curr.getX() == task1.getX() && curr.getY() == task1.getY())
                boardTiles.set(boardTiles.indexOf(curr), task1);
        }

        Tile task2 = new Tile("[T]", 3, 2); //setting up task tile
        taskTiles.add(task2);
        for(Tile curr : boardTiles){
            if(curr.getX() == task2.getX() && curr.getY() == task2.getY())
                boardTiles.set(boardTiles.indexOf(curr), task2);
        }
        

        [TEST STUFF]
             /* 
        agent.setX(3); agent.setY(1);

        removeTask(boardTiles, removeTaskTile, agent, MAX_ROW, MAX_COL);

        System.out.println("Agent Tasks Left: " + agent.tasksLeft());
        printBoard(MAX_ROW, MAX_COL, boardTiles, agent);
        System.out.println();


        agent.setX(3); agent.setY(2);

        removeTask(boardTiles, removeTaskTile, agent, MAX_ROW, MAX_COL);

        System.out.println("Agent Tasks Left: " + agent.tasksLeft());
        printBoard(MAX_ROW, MAX_COL, boardTiles, agent);
        System.out.println();

         agent.setX(0); agent.setY(1);

        removeTask(boardTiles, removeTaskTile, agent, MAX_ROW, MAX_COL);

        System.out.println("Agent Tasks Left: " + agent.tasksLeft());
        printBoard(MAX_ROW, MAX_COL, boardTiles, agent);
        System.out.println();
        */


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
        return icon;
   }

   public void setIcon(String s){
        this.icon = s;
   }

   public ArrayList<Integer> getCoords(){
        return coords;
   }

   public int getX(){
        return coords.get(0);
   }

   public int getY(){
        return coords.get(1);
   }
}

class Agent extends Tile{
    private ArrayList<Tile> tasks;
    private int fatigue = 0;
    //adding some movement counter attributes here
    private int movCount = 0;

    Agent(String i, int x, int y, ArrayList<Tile> tasks){
        super(i, x, y);
        this.tasks = tasks;
    }

   /* public void setX(int x){ 
        this.coords.set(0, x);
    }

    public void setY(int y){ 
        this.coords.set(1, y);
    }*/

    public void move(int row, int col){
        this.coords.set(0, row);
        this.coords.set(1, col);
        this.incMove();
    }
    
    public void updateTasks(ArrayList<Tile> tasks){
        this.tasks = tasks;
    }

    public ArrayList<Tile> getTasks(){
        return tasks;
    }
        

    public int tasksLeft(){
        return tasks.size();
    }

    //added fatigue
    public int getFatigue(){
        return this.fatigue;
    }

    public void incMove(){
        this.movCount++;
    }

    public int getMovCount(){
        return this.movCount;
    }

    /*;
    public turn();
    public scan();
    public end();*/

    //SCAN FUNCITOTN IN PROGRESSSSSS
    public String scan(int MAX_ROW, int MAX_COL, ArrayList<Tile> boardTiles, int row, int col){
        int x = row;
        int y = col;
        if(y == 0)
            return "//";
        
        if(y == MAX_COL)
            return "E";
        else{
           for(Tile curr : boardTiles){
                if(curr.getX() == x && curr.getY() == y){
                    return curr.getIcon();
                }
           }
           return null;
        }
    }

    public void end(){
        System.out.println();
        System.out.println("Agent has completed all tasks and returned back to Boss");
        System.out.println();
    }


}

//x coord is row / what floor agent is on
//y coord is column / what tile agent is on

public class BoardTest2 {

    /*
     * printBoard -> prints all tiles on the board
     * 
     * @param MAX_ROW - max amount of rows on the board
     * @param MAX_COL - max amount of columns on the board
     * @param boardTiles - all tiles on the board
     * @param agent - agent object/tile 
     */
    //2d array ver
    public static void printBoard(int MAX_ROW, int MAX_COL, Tile[][] boardTiles, Agent agent){
        System.out.println();
        for(int i = 0; i < MAX_ROW; i++){
            for(int j = 0; j < MAX_COL; j++){
                if(agent.getX() == i && agent.getY() == j){
                    if(j != 0 && j != MAX_COL-1)
                        System.out.print("[" + agent.getIcon() + "]"); //if agent is on a room tile
                    else if (j == 0)
                            System.out.print(agent.getIcon() + " "); //if agent is on a stairs tile
                        else
                            System.out.print(agent.getIcon()); //if agent is on elevator tile
                }
                
                else{ //print all other tiles
                        System.out.print(boardTiles[i][j].getIcon());
                    }
                }
                System.out.println();
            }

        }
    

    /*
     * removeTask -> if the agent's coords are the same as a task tile, this function removes the task from
     *               the agent's taskTiles list and the replaces the icon to an empty room "[ ]" on the boards
     * 
     * @param boardtiles - all tiles on the board
     * @param taskTiles - list of task tiles of agent
     * @param removeTaskTiles - contains task to be removed
     * @agent - agent object
     */
    public static void removeTask(Tile[][] boardTiles, ArrayList<Tile> removeTaskTile, Agent agent, int MAX_ROW, int MAX_COL){

        String taskIcon = "[T]";
        ArrayList<Tile> tasks = agent.getTasks();

        for(int i  = 0; i < MAX_ROW; i++){
            for(int j = 0; j < MAX_COL; j++){
                if(agent.getX() == i && agent.getY() == j){ //if agent is on curr's coords
                    if(taskIcon.equals(boardTiles[i][j].getIcon())){
                        removeTaskTile.add(boardTiles[i][j]);
                        boardTiles[i][j].setIcon("[ ]");
                    }
                }
            }
        }

       tasks.removeAll(removeTaskTile); //remove task from taskTiles list
       agent.updateTasks(tasks); 

    }


    /****************************************************  INTEGRATING DFS HERE ********************************************/

    //vectors to use for moving left, up, right, down respectively
    static int vRow[] = {0, 1, 0, -1};
    static int vCol[] = {-1, 0, 1, 0};

    static class pos {
        public int row,col;

        public pos(int row, int col){
            this.row = row;
            this.col = col;
        }

        public void setPos(int row, int col){
            this.row = row;
            this.col = col;
        }
    }


    static Boolean isVerticalMove(int row, int col, int MAX_COL){
        if(col != 0 && col != MAX_COL -1){
            return false;
        }
        return true;
    }

    //check if you can still explore that cell
    static Boolean isValid(Boolean visited[][], int row, int col, int MAX_ROW, int MAX_COL, Tile[][] grid, Agent agent){
        // if out of the grid
        if(row < 0 || col < 0 || row >= MAX_ROW || col >= MAX_COL){
            return false;
            
        }
    
        if (visited[row][col]){
            return false;
        }

        if(row != agent.getX()){
            return isVerticalMove(row, col, MAX_COL);
        }
        return true;
    }

    // DFS traversal on the matrix grid
    /*
     * DFS
     * agent - agent tile
     * Tile[][] grid - 2d array of grid
     * visited - flag for checking if grid has been visited
     * MAX_ROW - max num of rows
     * MAX_COL - max num of columns
     * removeTaskTile - arraylist to remove task tiles once agent has gone over them
     */
    static void DFS (Agent agent, Tile boss, Tile[][] grid, ArrayList<Tile> removeTaskTile, int MAX_ROW, int MAX_COL){

        boolean unvisitedExists = false;
        pos unvisited = new pos(0, 0); //initialize unvisited tile if there is left in the stack
        boolean endDFS = false;

        //initalize visited array
        Boolean visited [][] = new Boolean [MAX_ROW][MAX_COL];
        for(int i = 0; i < MAX_ROW; i++){
            for(int j = 0; j < MAX_COL; j++){
                visited[i][j] = false;
            }
        }

        //initialize a stack of positions
        Stack<pos> posStack = new Stack<pos>();

        int srow = agent.getX();
        int scol = agent.getY();
        
        //mark starting cell as visited and push it sa stack
        visited[srow][scol] = true;
        //agent.incMove();
        removeTask(grid,removeTaskTile, agent, MAX_ROW, MAX_COL);

        System.out.println();
        System.out.println("Starting DFS at (" + srow + ", " + scol + ")");
        System.out.println("Agent Tasks Left: " + agent.tasksLeft());
        System.out.println("Move Count: " + agent.getMovCount());
        printBoard(MAX_ROW, MAX_COL, grid, agent);
        System.out.println();
        System.out.println("====================");

        // time delay for execution
        try {
            Thread.sleep(3000); // 1 second
        } catch (InterruptedException e) { 
            Thread.currentThread().interrupt(); 
            return;
        }

        // push the neighbors of the current cell into the stack so it will be explored later on
        // in this case the neighbors are its neighbors on all four corners
        for(int i = 0; i < 4; i++){
            int adjR = srow + vRow[i]; // add the vector of the row
            int adjC = scol + vCol[i];
            if(isValid(visited, adjR, adjC, MAX_ROW, MAX_COL, grid, agent))
                posStack.push(new pos(adjR, adjC));
        }

        while(!posStack.empty() && endDFS == false){
            pos curr = posStack.pop(); // pop the ToS
            int row = curr.row;
            int col = curr.col;

            if(isValid(visited, row, col, MAX_ROW, MAX_COL, grid,agent)){
                visited[row][col] = true;
                /*agent.setX(row);
                agent.setY(col);*/
                agent.move(row, col);
                removeTask(grid, removeTaskTile, agent, MAX_ROW, MAX_COL);
                //agent.incMove();
                
                System.out.println("Agent Tasks Left: " + agent.tasksLeft());
                System.out.println("Move Count: " + agent.getMovCount());
                System.out.println("Moved to (" + row + ", " + col + ")");
                printBoard(MAX_ROW, MAX_COL, grid, agent);
                System.out.println();
                System.out.println("====================");


                // time delay for execution
                try {
                    Thread.sleep(3000); // 1 second
                } catch (InterruptedException e) { 
                        Thread.currentThread().interrupt(); 
                        return;
                }


                // push the neighbors of the current cell into the stack so it will be explored later on
                // in this case the neighbors are its neighbors on all four corners
                for(int i = 0; i < 4; i++){
                    int adjR = row + vRow[i]; // add the vector of the row
                    int adjC = col + vCol[i];
                    if(isValid(visited, adjR, adjC, MAX_ROW, MAX_COL, grid,agent))
                        posStack.push(new pos(adjR, adjC));
                    
                }

                if(agent.tasksLeft() == 0){ //if there are no tasks left
                    if(agent.getX() == boss.getX() && agent.getY() == boss.getY()){ //and agent is on boss tile
                        agent.end();
                        endDFS = true; //endDFS regardless if there is nodes left to be explored
                    }
                }

            }else if(posStack.size() == 1){
                unvisitedExists = true;
                unvisited.setPos(curr.row, curr.col);
            }
                    
        }

        if(agent.tasksLeft() != 0){ //if there are still tasks, move agent to unvisited tile. first by row, then by column.
                    if(unvisitedExists){
                        while(unvisited.row != agent.getX()){
                            if(unvisited.row > agent.getX()){
                                agent.move(agent.getX()+1, agent.getY());
                                removeTask(grid, removeTaskTile, agent, MAX_ROW, MAX_COL);
                                System.out.println("Moving to Unvisited Node");
                                System.out.println("Agent Tasks Left: " + agent.tasksLeft());
                                System.out.println("Move Count: " + agent.getMovCount());
                                System.out.println("Moved to (" + agent.getX() + ", " + agent.getY() + ")");
                                printBoard(MAX_ROW, MAX_COL, grid, agent);
                                System.out.println();
                                System.out.println("====================");
                            }

                            if(unvisited.row < agent.getX()){
                                agent.move(agent.getX()-1, agent.getY());
                                removeTask(grid, removeTaskTile, agent, MAX_ROW, MAX_COL);
                                System.out.println("Moving to Unvisited Node");
                                System.out.println("Agent Tasks Left: " + agent.tasksLeft());
                                System.out.println("Move Count: " + agent.getMovCount());
                                System.out.println("Moved to (" + agent.getX() + ", " + agent.getY() + ")");
                                printBoard(MAX_ROW, MAX_COL, grid, agent);
                                System.out.println();
                                System.out.println("====================");
                            }

                            try {
                                Thread.sleep(3000); // 1 second
                            } catch (InterruptedException e) { 
                                    Thread.currentThread().interrupt(); 
                                    return;
                            }
                        }

                        while(unvisited.col != agent.getY()){

                            if(unvisited.col > agent.getY()){
                                agent.move(agent.getX(), agent.getY()+1);
                                removeTask(grid, removeTaskTile, agent, MAX_ROW, MAX_COL);
                                System.out.println("Moving to Unvisited Node");
                                System.out.println("Agent Tasks Left: " + agent.tasksLeft());
                                System.out.println("Move Count: " + agent.getMovCount());
                                System.out.println("Moved to (" + agent.getX() + ", " + agent.getY() + ")");
                                printBoard(MAX_ROW, MAX_COL, grid, agent);
                                System.out.println();
                                System.out.println("====================");
                            }

                            if(unvisited.col < agent.getY()){
                                agent.move(agent.getX(), agent.getY()-1);
                                removeTask(grid, removeTaskTile, agent, MAX_ROW, MAX_COL);
                                System.out.println("Moving to Unvisited Node");
                                System.out.println("Agent Tasks Left: " + agent.tasksLeft());
                                System.out.println("Move Count: " + agent.getMovCount());
                                System.out.println("Moved to (" + agent.getX() + ", " + agent.getY() + ")");
                                printBoard(MAX_ROW, MAX_COL, grid, agent);
                                System.out.println();
                                System.out.println("====================");
                            }
                            
                            try {
                                Thread.sleep(3000); // 1 second
                            } catch (InterruptedException e) { 
                                    Thread.currentThread().interrupt(); 
                                    return;
                            }

                        }
                    }
                
            }
    }


    public static void main(String[] args) {

        /*** SETTING UP BOARD & BASIC ELEMENTS: STAIRS, ELEVATORS, ROOMS ****/

        int MAX_ROW = 4;
        int MAX_COL = 4; 
        //ArrayList<Tile> boardTiles = new ArrayList<>(); //boardTiles ArrayList ver
        Tile boardTiles[][] = new Tile[MAX_ROW][MAX_COL];

        ArrayList<Tile> taskTiles = new ArrayList<>(); //task tiles for agent
       ArrayList<Tile> removeTaskTile = new ArrayList<>(); //used to avoid ConcurrentModificationException when removing task from agent's tasks

      

        /* SETUP FOR boardTiles 2d array ver */
        for(int i = 0; i < MAX_ROW; i++){ 
            for(int j = 0; j < MAX_COL; j++){
                if(j == 0)
                    boardTiles[i][j] = new Tile("//", i, j);
                else if (j == MAX_COL-1)
                    boardTiles[i][j] = new Tile("E", i, j);
                else
                    boardTiles[i][j] = new Tile("[ ]", i, j);
            }
        }

       

        /*** SETTING UP UNIQUE BOARD ELEMENTS: BOSS, TASK, ETC.  boardTiles 2d array ver */

        Tile boss = new Tile("[B]", 2, 1); //setting up boss tile
        boardTiles[boss.getX()][boss.getY()] = boss;
        
        Tile task1 = new Tile("[T]", 1, 1); //setting up task tile
        boardTiles[task1.getX()][task1.getY()] = task1;
        taskTiles.add(task1);

      //  Tile task2 = new Tile("[T]", 3, 2); //setting up task tile
      //  boardTiles[task2.getX()][task2.getY()] = task2;
      //  taskTiles.add(task2);



        /* SETTING UP AGENT */
        Agent agent = new Agent("A", 0, 1, taskTiles); //setting up agent tile
        
        //DFS


        System.out.println("DFS TRAVERSAL");

        while(agent.tasksLeft() != 0){
            DFS(agent, boss, boardTiles, removeTaskTile, MAX_ROW, MAX_COL);

            if((agent.getX() != boss.getX()))
            DFS(agent, boss, boardTiles, removeTaskTile, MAX_ROW, MAX_COL);

            if((agent.getY() != boss.getY()))
            DFS(agent, boss, boardTiles, removeTaskTile, MAX_ROW, MAX_COL);

            
        }
        
        
           


       // System.out.println("Agent Tasks Left: " + agent.tasksLeft());
        //System.out.println();
       // printBoard(MAX_ROW, MAX_COL, boardTiles, agent);
       // System.out.println();
        //System.out.println(agent.scan(MAX_ROW, MAX_COL, boardTiles));
        
        
   


    }
    
}
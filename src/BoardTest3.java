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

    public void move(int row, int col, int MAX_COL){
        if(col == MAX_COL - 1){
            this.coords.set(0, row);
            this.coords.set(1, col);
            this.incMove();
            //this.incMove();
        } else if(col == 0){
            this.coords.set(0, row);
            this.coords.set(1, col);
            this.incFatigue();
            //this.incMove();
        }
    
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

    public void incFatigue(){
        this.fatigue++;
    }

    public void incMove(){
        this.movCount++;
    }

    public int getMovCount(){
        return this.movCount;
    }

    public Tile scan(int MAX_ROW, int MAX_COL, Tile[][] boardTiles, int incRow, int incCol){
        for(int i = 0; i < MAX_ROW; i++){
            for(int j = 0; j < MAX_COL; j++){
                if(this.getX()+incRow == i && this.getY()+incCol == j){
                    return boardTiles[i][j];
                }
            }
        }
        return new Tile("", this.getX()+incRow, this.getY()+incCol);
    }

    public void end(){
        System.out.println();
        System.out.println("Agent has completed all tasks and returned back to Boss");
        System.out.println();
    }


}

//x coord is row / what floor agent is on
//y coord is column / what tile agent is on

public class BoardTest3 {

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
   
    static void DFS (Agent agent, Tile boss, Tile[][] grid, Boolean[][] visited, ArrayList<Tile> removeTaskTile, int MAX_ROW, int MAX_COL, Scanner sc, boolean needEnter){

        boolean endDFS = false;

        

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
        System.out.println("Fatigue Points: " + agent.getFatigue());

        printBoard(MAX_ROW, MAX_COL, grid, agent);
        System.out.println();
        System.out.println("====================");

        

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
              

                while (agent.getX() != row || agent.getY() != col) {

                    int nextRow = agent.getX();
                    int nextCol = agent.getY();
                    
                    
                    
                    if (isVerticalMove(agent.getX(), agent.getY(), MAX_COL) && agent.getX() != row) { //move to correct row first if we can move vertically
                        if (row > agent.getX()){
                            nextRow = agent.getX() + 1;
                        }
                        else {
                            nextRow = agent.getX() - 1;
                        }
                    }
                    
                    else if (agent.getY() != col) { //else move to correct column
                        if (col > agent.getY()){
                            nextCol = agent.getY() + 1;
                        }
                        else {
                            nextCol = agent.getY() - 1;
                        }
                    }
                    
                    //if cant move vertically, move toward stairs/elevator
                    else if (!isVerticalMove(agent.getX(), agent.getY(), MAX_COL) && agent.getX() != row) {
                        
                        if (agent.scan(MAX_ROW, MAX_COL, grid, 0, 1).getIcon().equals("E")){
                            nextCol = agent.getY() + 1; //move toward elevator
                        }
                        else if(agent.scan(MAX_ROW, MAX_COL, grid, 0, -1).getIcon().equals("//")){
                            nextCol = agent.getY() + 1; //move toward stairs
                        }
                        else {
                            nextCol = agent.getY() + 1; //move toward elevator
                        }
                            
                        
                    }
                    
                    agent.move(nextRow, nextCol, MAX_COL);
                    removeTask(grid, removeTaskTile, agent, MAX_ROW, MAX_COL);


                    if (needEnter) {
                        sc.nextLine();

                        System.out.println("Target Position (" + row + ", " + col + ")");
                        System.out.println("Moved to (" + agent.getX() + ", " + agent.getY() + ")");
                        System.out.println("Agent Tasks Left: " + agent.tasksLeft());
                        System.out.println("Move Count: " + agent.getMovCount());
                        System.out.println("Fatigue Points: " + agent.getFatigue());
                        printBoard(MAX_ROW, MAX_COL, grid, agent);
                        System.out.println("====================");
                    }
                    else{

                        System.out.println("Target Position (" + row + ", " + col + ")");
                        System.out.println("Moved to (" + agent.getX() + ", " + agent.getY() + ")");
                        System.out.println("Agent Tasks Left: " + agent.tasksLeft());
                        System.out.println("Move Count: " + agent.getMovCount());
                        System.out.println("Fatigue Points: " + agent.getFatigue());
                        printBoard(MAX_ROW, MAX_COL, grid, agent);
                        System.out.println("====================");

                        try {
                            Thread.sleep(1000); // 1 second
                        }
                        catch (InterruptedException e){ 
                            Thread.currentThread().interrupt(); 
                        return;
                        }
                    }
                }

                if(agent.tasksLeft() == 0){ //if there are no tasks left
                    Tile tile = agent.scan(MAX_ROW, MAX_COL, grid, 0, 0);
                    String tileIcon = tile.getIcon();
                    if(tileIcon.equals("[B]")){ //and agent is on boss tile
                        agent.end();
                        endDFS = true; //endDFS regardless if there is nodes left to be explored
                    }
                }

            }
                
                
            
            /* Mark final position as visited if it's a regular room
            if (col != 0 && col != MAX_COL - 1) {
                visited[row][col] = true;
            }*/
        
    

                // push the neighbors of the current cell into the stack so it will be explored later on
                // in this case the neighbors are its neighbors on all four corners

                for(int i = 0; i < 4; i++){
                    Tile adjTile = agent.scan(MAX_ROW, MAX_COL, grid, vRow[i], vCol[i]);
                    if(isValid(visited, adjTile.getX(), adjTile.getY(), MAX_ROW, MAX_COL, grid,agent))
                        posStack.push(new pos(adjTile.getX(), adjTile.getY()));
                }

                
              
        }
                
    }
        
    


    public static void main(String[] args) {

        /*** SETTING UP BOARD & BASIC ELEMENTS: STAIRS, ELEVATORS, ROOMS ****/

        int MAX_ROW = 3;
        int MAX_COL = 3; 
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

        //Tile task2 = new Tile("[T]", 3, 2); //setting up task tile
        //boardTiles[task2.getX()][task2.getY()] = task2;
        //taskTiles.add(task2);



        /* SETTING UP AGENT */
        Agent agent = new Agent("A", 0, 1, taskTiles); //setting up agent tile
        
        /**** SETTING UP DFS ****/

        //initalize visited array
        Boolean visited [][] = new Boolean [MAX_ROW][MAX_COL];
        for(int i = 0; i < MAX_ROW; i++){
            for(int j = 0; j < MAX_COL; j++){
                visited[i][j] = false;
            }
        }

        /* MAIN EVENT STARTS HERE */
        System.out.println("==============");
        System.out.println("DFS TRAVERSAL");
        System.out.println("==============");


        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);

        int choice = 0;

        while(choice != 1 && choice != 2){

            System.out.println("[1] Automatic Viewing");
            System.out.println("[2] Manual Viewing");
            System.out.print("Enter number of choice: ");
            choice = sc2.nextInt();
            
            if(choice != 1 && choice != 2){
                System.out.println("Error: Invalid input!");
                System.out.println();
            }
        }
        
        

        if(choice == 1){

            while(agent.tasksLeft() != 0){

                for(int i = 0; i < MAX_ROW; i++){
                        for(int j = 0; j < MAX_COL; j++){
                            visited[i][j] = false;
                        }
                }

                DFS(agent, boss, boardTiles, visited, removeTaskTile, MAX_ROW, MAX_COL, sc, false);

            }

            while(!agent.scan(MAX_ROW, MAX_COL, boardTiles, 0, 0).getIcon().equals("[B]")){

                for(int i = 0; i < MAX_ROW; i++){
                        for(int j = 0; j < MAX_COL; j++){
                            visited[i][j] = false;
                        }
                    }
                
                DFS(agent, boss, boardTiles, visited, removeTaskTile, MAX_ROW, MAX_COL, sc, true);
            }

        }

        

        if(choice == 2){

            while(agent.tasksLeft() != 0){

                for(int i = 0; i < MAX_ROW; i++){
                        for(int j = 0; j < MAX_COL; j++){
                            visited[i][j] = false;
                        }
                }

                DFS(agent, boss, boardTiles, visited, removeTaskTile, MAX_ROW, MAX_COL, sc, true);

            }

            while(!agent.scan(MAX_ROW, MAX_COL, boardTiles, 0, 0).getIcon().equals("[B]")){

                for(int i = 0; i < MAX_ROW; i++){
                        for(int j = 0; j < MAX_COL; j++){
                            visited[i][j] = false;
                        }
                    }
                
                DFS(agent, boss, boardTiles, visited, removeTaskTile, MAX_ROW, MAX_COL, sc, true);
            }

        }

        sc.close();
        sc2.close();
    
    }
}

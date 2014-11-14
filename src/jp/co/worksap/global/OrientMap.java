package jp.co.worksap.global;

import java.util.ArrayList;
import java.util.HashMap;

public class OrientMap{
    //Constants for maximum allowed size for rows and columns
    private static final int MAX_ROWS = 10000;
    private static final int MAX_COLUMNS = 10000;

    //HashMap for memoization
    //@key   String    fromPosition.toString()+"#"+toPosition.toString()
    //@value Integer   minimum distance between fromPosition and toPosition
    private static HashMap<String, Integer> memoizedDistanceMap = new HashMap<String, Integer>();

    //2-D array representation for OrientBlock array (OPEN, CLOSED, START, GOAL)
    private OrientBlock[][] mapStruct;

    //remember start and goal positions for faster access
    private Position startPosition = null;
    private Position goalPosition = null;

    //List of CheckPoints in the Map
    private ArrayList<Position> checkList = new ArrayList<Position>();

    //Default constructor to initialize map
    //@params
    // numOfRows    - int Number of Rows in Map
    // numOfColumns - int Number of Columns in Map
    public OrientMap(int numOfRows, int numOfColumns) throws InputParseException{
        if(numOfRows > MAX_ROWS) throw new NumberOfRowsExceededException(MAX_ROWS);
        if(numOfColumns > MAX_COLUMNS) throw new NumberOfColumnsExceededException(MAX_COLUMNS);
        this.mapStruct = new OrientBlock[numOfRows][numOfColumns];
    }

    //Copy Constructor
    //@params
    // otherMap - OrientMap which needs to be copied
    public OrientMap(OrientMap otherMap){
        this.mapStruct = otherMap.getMapStruct();
        this.startPosition = otherMap.getStartPosition();
        this.goalPosition = otherMap.getGoal();
        this.checkList = otherMap.getCheckpoints();
    }

    //Private method to return 2-D OrientBlock Map
    //@return
    //  OrientBlock[][]
    private OrientBlock[][] getMapStruct(){
        return mapStruct;
    }

    //Method to return startPosition
    //@return
    //  Position
    public Position getStartPosition(){
        return startPosition;
    }

    //Method to return goalPosition
    //@return
    //  Position
    public Position getGoal(){
        return goalPosition;
    }

    //Method to get number of rows
    //@return
    //  int number of rows
    public int getNumOfRows(){
        return mapStruct.length;
    }

    //Method to get number of Columns
    //@return
    // int number of columns
    public int getNumOfColumns(){
        return mapStruct[0].length;
    }

    //Method to move the startPosition in the map to the specified position
    //@params
    //  toPlace - Position to shift start position to
    //@return
    //  int     - Distance moved (-1 if move is not possible)
    public int moveStart(Position toPlace){
        int distance = getDistance(startPosition, toPlace, new ArrayList<Position>());
        setBlock(toPlace, OrientBlock.START);
        setBlock(startPosition, OrientBlock.OPEN);
        checkList.remove(toPlace);
        startPosition = toPlace;
        return distance;
    }

    //Method to get distance between 2 positions in the map
    //Dijkstra's Algorithm
    //@param
    //  place1  - Position first position in the map
    //  place2  - Position second position in the map
    //  visited - ArrayList<Position> stores positions already visited for recursive calls
    //                create new for first call
    //@return
    //  int     - Least distance between positions (-1 if movement is not possible)
    public int getDistance(Position place1, Position place2, ArrayList<Position> visited){
        //key for memoized map
        String mapKey = place1.toString()+"#"+place2.toString();

        //return -1 if place1 or place2 are CLOSED
        if(getBlock(place1) == OrientBlock.CLOSE || getBlock(place2) == OrientBlock.CLOSE) return -1;
        
        //Terminating condition
        if(place1.equals(place2)) return 0;

        //check memoized list
        if(memoizedDistanceMap.containsKey(mapKey)) return memoizedDistanceMap.get(mapKey);
        
        int distance = Integer.MAX_VALUE;

        //check if movement is ever possible (true if not possible)
        boolean changeFlag = true;

        Position tempPosition;
        if(place2.getCordiY()-1 >= 0){
            tempPosition = new Position(place2.getCordiX(), place2.getCordiY()-1);
            //if not closed or visited
            if(!this.getBlock(tempPosition).equals(OrientBlock.CLOSE) && !visited.contains(tempPosition)){
                ArrayList<Position> tempVisited = new ArrayList<Position>(visited);
                tempVisited.add(tempPosition);
                //Add 1 to distance to point left of target
                int tempDistance = getDistance(place1, tempPosition, tempVisited)+1;
                if(tempDistance != 0 && tempDistance < distance){
                    distance = tempDistance;
                    changeFlag = false;
                }
            }
        }
        if(place2.getCordiY()+1 < this.getNumOfColumns()){
            tempPosition = new Position(place2.getCordiX(), place2.getCordiY()+1);
            if(!this.getBlock(tempPosition).equals(OrientBlock.CLOSE) && !visited.contains(tempPosition)){
                ArrayList<Position> tempVisited = new ArrayList<Position>(visited);
                tempVisited.add(tempPosition);
                int tempDistance = getDistance(place1, tempPosition, tempVisited)+1;
                if(tempDistance != 0 && tempDistance < distance){
                    distance = tempDistance;
                    changeFlag = false;
                }
            }
        }
        if(place2.getCordiX()-1 >= 0){
            tempPosition = new Position(place2.getCordiX()-1, place2.getCordiY());
            if(!this.getBlock(tempPosition).equals(OrientBlock.CLOSE) && !visited.contains(tempPosition)){
                ArrayList<Position> tempVisited = new ArrayList<Position>(visited);
                tempVisited.add(tempPosition);
                int tempDistance = getDistance(place1, tempPosition, tempVisited)+1;
                if(tempDistance != 0 && tempDistance < distance){
                    distance = tempDistance;
                    changeFlag = false;
                }
            }
        }
        if(place2.getCordiX()+1 < this.getNumOfRows()){
            tempPosition = new Position(place2.getCordiX()+1, place2.getCordiY());
            if(!this.getBlock(tempPosition).equals(OrientBlock.CLOSE) && !visited.contains(tempPosition)){
                ArrayList<Position> tempVisited = new ArrayList<Position>(visited);
                tempVisited.add(tempPosition);
                int tempDistance = getDistance(place1, tempPosition, tempVisited)+1;
                if(tempDistance != 0 && tempDistance < distance){
                    distance = tempDistance;
                    changeFlag = false;
                }
            }
        }
        if(changeFlag) return -1;
        //put answer in memoized list
        memoizedDistanceMap.put(mapKey, distance);
        return distance;
    }

    //Method to set a particular block as CLOSE, OPEN, START or GOAL
    //@params
    //  row     - int 0 indexed row position
    //  column  - int 0 indexed column position
    //  block   - OrientBlock state to set the block to
    public void setBlock(int row, int column, OrientBlock block) throws InputParseException{
        // 0 indexed
        switch(block){
            case START:
                if(startPosition != null) throw new StartPositionException();
                startPosition = new Position(row, column);
                break;
            case GOAL:
                if(goalPosition != null) throw new GoalPositionException();
                goalPosition = new Position(row, column);
                break;
            case CHECKPOINT:
                checkList.add(new Position(row, column));
                break;
        }
        mapStruct[row][column] = block;
    }

    //Method to set a particular block as CLOSE, OPEN, START or GOAL
    //@params
    //  position  - Position to set the state of
    //  block     - OrientBlock state to set the block to
    public void setBlock(Position position, OrientBlock block){
        mapStruct[position.getCordiX()][position.getCordiY()] = block;
    }

    //Return the state of map at position
    //@param
    //  position    -  Position of the required block
    //@return
    //  OrientBlock -  State at position
    public OrientBlock getBlock(Position position){
        return mapStruct[position.getCordiX()][position.getCordiY()];
    }

    //Method to return the list of checkpoints in the map
    //@return
    //  ArrayList<Position>  - List of checkpoint positions
    public ArrayList<Position> getCheckpoints(){
        return checkList;
    }

    //Method to validate after setting all blocks
    //Checks if start and goal are defined
    public void validate() throws InputParseException{
        if(this.startPosition == null) throw new StartPositionException();
        if(this.goalPosition == null) throw new GoalPositionException();
    }


    ////TODO delete
    //public void print(){
    //    for(int oIter=0; oIter<getNumOfRows(); oIter++){
    //        for(int iIter=0; iIter<getNumOfColumns(); iIter++){
    //            System.out.print(mapStruct[oIter][iIter]+" ");
    //        }
    //        System.out.println();
    //    }
    //}
}

//Enum State of all positions in OrientMap
enum OrientBlock{
    START, GOAL, CHECKPOINT, OPEN, CLOSE;
}

//Class to define a Position
class Position{
    //private cordinates of x and y
    private int cordiX, cordiY;

    public Position(int cordiX, int cordiY){
        this.cordiX = cordiX;
        this.cordiY = cordiY;
    }
    public int getCordiX(){
        return this.cordiX;
    }
    public int getCordiY(){
        return this.cordiY;
    }
    @Override
    public boolean equals(Object object){
        if(!(object instanceof Position)) return false;
        Position other = (Position) object;
        return (this.getCordiX() == other.getCordiX() && this.getCordiY() == other.getCordiY());
    }

    //toString required for key in memoization
    @Override
    public String toString(){
        return "Position - x "+cordiX+" y "+cordiY;
    }
}

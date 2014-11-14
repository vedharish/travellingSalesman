package jp.co.worksap.global;

import java.util.ArrayList;
import java.util.HashMap;

public class OrientMap{
    private static HashMap<String, Integer> memoizedDistanceMap = new HashMap<String, Integer>();
    private OrientBlock[][] mapStruct;
    private static final int MAX_ROWS = 1000000;
    private static final int MAX_COLUMNS = 1000000;
    private Position startPosition = null;
    private Position goalPosition = null;
    private ArrayList<Position> checkList = new ArrayList<Position>();

    
    public OrientMap(int numOfRows, int numOfColumns) throws InputParseException{
        if(numOfRows > MAX_ROWS) throw new NumberOfRowsExceededException(MAX_ROWS);
        if(numOfColumns > MAX_COLUMNS) throw new NumberOfColumnsExceededException(MAX_COLUMNS);
        this.mapStruct = new OrientBlock[numOfRows][numOfColumns];
    }
    public OrientMap(OrientMap otherMap){
        this.mapStruct = otherMap.getMapStruct();
        this.startPosition = otherMap.getStartPosition();
        this.goalPosition = otherMap.getGoal();
        this.checkList = otherMap.getCheckpoints();
    }
    private OrientBlock[][] getMapStruct(){
        return mapStruct;
    }
    public Position getStartPosition(){
        return startPosition;
    }
    public Position getGoal(){
        return goalPosition;
    }
    public int getNumOfRows(){
        return mapStruct.length;
    }
    public int getNumOfColumns(){
        return mapStruct[0].length;
    }
    public int moveStart(Position toPlace){
        int distance = getDistance(startPosition, toPlace, new ArrayList<Position>());
        setBlock(toPlace, OrientBlock.START);
        setBlock(startPosition, OrientBlock.OPEN);
        checkList.remove(toPlace);
        startPosition = toPlace;
        return distance;
    }
    public int getDistance(Position place1, Position place2, ArrayList<Position> visited){
        //return -1 if place1 or place2 are CLOSED
        String mapKey = place1.toString()+"#"+place2.toString();

        if(getBlock(place1) == OrientBlock.CLOSE || getBlock(place2) == OrientBlock.CLOSE) return -1;
        if(place1.equals(place2)) return 0;

        if(memoizedDistanceMap.containsKey(mapKey)) return memoizedDistanceMap.get(mapKey);
        
        int distance = Integer.MAX_VALUE;
        boolean changeFlag = true;

        Position tempPosition;
        if(place2.getCordiY()-1 >= 0){
            tempPosition = new Position(place2.getCordiX(), place2.getCordiY()-1);
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
        memoizedDistanceMap.put(mapKey, distance);
        return distance;
    }
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
    public OrientBlock getBlock(Position position){
        return mapStruct[position.getCordiX()][position.getCordiY()];
    }
    public void setBlock(Position position, OrientBlock block){
        mapStruct[position.getCordiX()][position.getCordiY()] = block;
    }
    public ArrayList<Position> getCheckpoints(){
        return checkList;
    }
    public void validate() throws InputParseException{
        if(this.startPosition == null) throw new StartPositionException();
        if(this.goalPosition == null) throw new GoalPositionException();
    }


    //TODO delete
    public void print(){
        for(int oIter=0; oIter<getNumOfRows(); oIter++){
            for(int iIter=0; iIter<getNumOfColumns(); iIter++){
                System.out.print(mapStruct[oIter][iIter]+" ");
            }
            System.out.println();
        }
    }
}

enum OrientBlock{
    START, GOAL, CHECKPOINT, OPEN, CLOSE;
}
class Position{
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
    @Override
    public String toString(){
        return "Position - x "+cordiX+" y "+cordiY;
    }
}

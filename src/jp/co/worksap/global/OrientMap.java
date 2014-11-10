package jp.co.worksap.global;

import java.util.ArrayList;

public class OrientMap{
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
    public int getNumOfRows(){
        return mapStruct.length;
    }
    public int getNumOfColumns(){
        return mapStruct[0].length;
    }
    public int getDistance(Position place1, Position place2){
        //return -1 if place1 or place2 are CLOSED
        if(getBlock(place1) == OrientBlock.CLOSE || getBlock(place2) == OrientBlock.CLOSE) return -1;
        return 0;
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
}

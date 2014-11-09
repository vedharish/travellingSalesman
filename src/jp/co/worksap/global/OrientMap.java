package jp.co.worksap.global;

public class OrientMap{
    private OrientBlock[][] mapStruct;
    private static final int MAX_ROWS = 1000000;
    private static final int MAX_COLUMNS = 1000000;
    private Position startPosition = null;
    private Position goalPosition = null;

    
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
    public void setBlock(int row, int column, OrientBlock block) throws InputParseException{
        // 0 indexed
        if(block.equals(OrientBlock.START)){
            if(startPosition != null) throw new StartPositionException();
            startPosition = new Position(row, column);
        }else if(block.equals(OrientBlock.GOAL)){
            if(goalPosition != null) throw new GoalPositionException();
            goalPosition = new Position(row, column);
        }
        mapStruct[row][column] = block;
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

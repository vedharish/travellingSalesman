package jp.co.worksap.global;

public class OrientMap{
    private OrientBlock[][] mapStruct;
    
    public OrientMap(int numOfRows, int numOfColumns){
        this.mapStruct = new OrientBlock[numOfRows][numOfColumns];
    }
}

enum OrientBlock{
    START, GOAL, CHECKPOINT, OPEN, CLOSE;
}

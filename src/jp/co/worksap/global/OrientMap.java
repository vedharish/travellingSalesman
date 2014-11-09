package jp.co.worksap.global;

public class OrientMap{
    private OrientBlock[][] mapStruct;
    
    public OrientMap(int numOfRows, int numOfColumns){
        this.mapStruct = new OrientBlock[numOfRows][numOfColumns];
    }
    public int getNumOfRows(){
        return mapStruct.length;
    }
    public int getNumOfColumns(){
        return mapStruct[0].length;
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

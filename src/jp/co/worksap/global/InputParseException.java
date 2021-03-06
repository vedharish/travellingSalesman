package jp.co.worksap.global;

import java.io.IOException;

public class InputParseException extends IOException{
    public static final String exceptionString = "Exception --- ";
    public String toString(){
        return exceptionString+"Unable to read from input";
    }
}

class SpaceException extends InputParseException{
    public String toString(){
        return exceptionString+"The number of rows and number of columns must be space-seperated.";
    }
}

class IntegerException extends InputParseException{
    public String toString(){
        return exceptionString+"The number of rows and columns must be an integer.";
    }
}

class WrongNumRowsException extends InputParseException{
    public String toString(){
        return exceptionString+"Sufficient number of rows were not found in input.";
    }
}

class WrongNumColsException extends InputParseException{
    public String toString(){
        return exceptionString+"The given number of columns does not match as given in first line of input.";
    }
}

class StartPositionException extends InputParseException{
    public String toString(){
        return exceptionString+"Input should have one start position.";
    }
}

class GoalPositionException extends InputParseException{
    public String toString(){
        return exceptionString+"Input should have one goal position.";
    }
}

class NumberOfRowsExceededException extends InputParseException{
    int maxRows = -1;
    public NumberOfRowsExceededException(int maxRows){
        this.maxRows = maxRows;
    }
    public String toString(){
        if(maxRows == -1) return exceptionString+"The number of rows must not be large.";
        return exceptionString+"The number of rows must not exceed "+maxRows+".";
    }
}

class NumberOfColumnsExceededException extends InputParseException{
    int maxColumns = -1;
    public NumberOfColumnsExceededException(int maxColumns){
        this.maxColumns = maxColumns;
    }
    public String toString(){
        if(maxColumns == -1) return exceptionString+"The number of columns must not be large.";
        return exceptionString+"The number of columns must not exceed "+maxColumns+".";
    }
}

class StateNotRecognizedException extends InputParseException{
    int positionX = -1, positionY = -1;
    public StateNotRecognizedException(int positionX, int positionY){
        this.positionX = positionX;
        this.positionY = positionY;
    }
    public String toString(){
        if(positionX == -1 || positionY == -1) return exceptionString+"The map character cannot be recognized as any block state.";
        return exceptionString+"The map character at ("+positionX+","+positionY+") cannot be recognized as any block state.";
    }
}

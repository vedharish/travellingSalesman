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

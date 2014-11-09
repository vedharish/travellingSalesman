package jp.co.worksap.global;

import java.io.BufferedReader;
import java.io.IOException;

public class InputParser{
    public static OrientMap getOrientMap(BufferedReader inputReader) throws InputParseException{
        String[] inputStrArray;

        try{
            inputStrArray = inputReader.readLine().split("\\s+");
        }catch(IOException e){
            throw new InputParseException();
        }

        OrientMap mapObject = null;

        try{
            mapObject = new OrientMap(Integer.parseInt(inputStrArray[0]), Integer.parseInt(inputStrArray[1]));
        }catch(ArrayIndexOutOfBoundsException e){
            throw new InputParseException();
        }

        return mapObject;
    }
}

class InputParseException extends IOException{
    public static final String exceptionString = "Exception --- ";
    public String toString(){
        return exceptionString+"Unable to read from input";
    }
}

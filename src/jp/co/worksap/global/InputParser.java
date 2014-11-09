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
            throw new SpaceException();
        }catch(NumberFormatException e){
            throw new IntegerException();
        }

        String inputString;
        for(int iter=0; iter<mapObject.getNumOfRows(); iter++){
            try{
                inputString = inputReader.readLine();
            }catch(IOException e){
                throw new InputParseException();
            }
            if(inputString == null) throw new WrongNumRowsException();
            inputString = inputString.trim();
            if(inputString.length() != mapObject.getNumOfColumns()) throw new WrongNumColsException();
            mapObject.print();
        }

        return mapObject;
    }
}

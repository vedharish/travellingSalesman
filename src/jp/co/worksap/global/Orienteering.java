package jp.co.worksap.global;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Orienteering{
    public static void main(String[] args){
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        try{
            OrientMap mapObject = InputParser.getOrientMap(inputReader);
        }catch(InputParseException e){
            e.printStackTrace();
        }
    }
}

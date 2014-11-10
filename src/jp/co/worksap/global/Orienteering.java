package jp.co.worksap.global;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Orienteering{
    public static void main(String[] args){
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        OrientMap mapObject = null;
        try{
            mapObject = InputParser.getOrientMap(inputReader);
            mapObject.validate();

            mapObject.print();
            System.out.println(Solver.solve(mapObject));
        }catch(InputParseException e){
            e.printStackTrace();
        }
    }
}

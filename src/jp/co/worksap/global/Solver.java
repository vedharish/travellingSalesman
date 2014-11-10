package jp.co.worksap.global;

import java.util.ArrayList;

public class Solver{
    public static int solve(OrientMap mapObject){
        return mapObject.getDistance(new Position(0, 0), new Position(0, 4), new ArrayList<Position>());
    }
}

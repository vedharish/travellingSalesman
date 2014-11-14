package jp.co.worksap.global;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class Solver{
    public static final int PERMUTE_CHECKPOINTS = 9;

    public static int solve(final OrientMap mapObject){
        int totalDistance;
        ArrayList<Position> checkPoints = mapObject.getCheckpoints();
        if(checkPoints.size() > PERMUTE_CHECKPOINTS){
            totalDistance = 0;
            while(checkPoints.size() != 0){
                Collections.sort(checkPoints, new Comparator<Position>(){
                    @Override
                    public int compare(Position position1, Position position2){
                        Position startPosition = mapObject.getStartPosition();
                        Integer distance1 = mapObject.getDistance(startPosition, position1, new ArrayList<Position>());
                        Integer distance2 = mapObject.getDistance(startPosition, position2, new ArrayList<Position>());
                        return distance1.compareTo(distance2);
                    }
                });
                totalDistance += mapObject.moveStart(checkPoints.get(0));
            }
            totalDistance += mapObject.moveStart(mapObject.getGoal());
            return totalDistance;
        }else{
            totalDistance = Integer.MAX_VALUE;
            ArrayList<Position> checkPointsList = new ArrayList<Position>(checkPoints);
            ArrayList<String> permutationsList = new ArrayList<String>();
            int arr[] = new int[checkPointsList.size()];
            for(int iter=0; iter<checkPointsList.size(); iter++){
                arr[iter] = iter;
            }
            permute(arr, 0, permutationsList);
            Iterator<String> iter = permutationsList.iterator();
            while(iter.hasNext()){
                int tempDistance = 0;
                String[] numbers = iter.next().split("\\s+");
                iter.remove();
                OrientMap tempMapObject = new OrientMap(mapObject);
                for(int it=0; it<numbers.length; it++){
                    tempDistance += tempMapObject.moveStart(checkPointsList.get(Integer.parseInt(numbers[it])));
                    if(tempDistance > totalDistance) break;
                }
                tempDistance += tempMapObject.moveStart(mapObject.getGoal());
                if(tempDistance < totalDistance) totalDistance = tempDistance;
            }
            return totalDistance;
        }
    }

    public static void permute(int[] array, int k, ArrayList<String> permutations){
        for(int i=k; i<array.length; i++){
            int temp;
            temp = array[i];
            array[i] = array[k];
            array[k] = temp;
            permute(array, k+1, permutations);
            int temp2;
            temp2 = array[i];
            array[i] = array[k];
            array[k] = temp2;
        }
        if (k == array.length-1){
            StringBuilder sb = new StringBuilder();
            for(int iter=0; iter<array.length; iter++){
                sb.append(array[iter]+" ");
            }
            permutations.add(sb.toString());
        }
    }
}

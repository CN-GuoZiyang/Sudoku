import java.util.Random;

public class GetDifficultyArray{
    private static Random random;

    public static int[][] getDifficultyArray(String level){
        random = new Random();
        int randomValue = -1;
        int[][] diffArray = new int[][]{{0,0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0,0}};
        switch(level){
            case "EASY":
                randomValue = 4;
                break;
            case "MEDIUM":
                randomValue = 5;
                break;
            case "DIFFICULT":
                randomValue = 6;
                break;
            case "EVIL":
                randomValue = 7;
                break;
            default:
                break;
        }
        //System.out.println(randomValue);
        for(int block = 0; block < 9; block++){
            int[] randomSet = new int[randomValue];
            out:for(int i = 0; i < randomSet.length; i++){
                int randomNum = random.nextInt(9);
                //System.out.println(randomNum);
                for(int j = 0; j < i; j++){
                    if(randomSet[j] == randomNum){
                        i --;
                        continue out;
                    }
                }
                randomSet[i] = randomNum;
            }
            for(int i : randomSet){
                diffArray[block][i] = 1;
            }
        }
        int[][] newDiff = new int[9][9];
        for(int i = 0; i < 9; i++){
            int rowbase = (i % 3) * 3;
            int colbase = (i / 3) * 3;
            for(int j = 0; j < 9; j++){
                newDiff[rowbase + j / 3][colbase + j % 3] = diffArray[i][j];
            }
        }
        return newDiff;
    }
}
import java.util.Random;

public class GenerateSudoku {
    private Random random = new Random();

    /* 运行此程序300次，最大值是217，最小值11，平均约等于50
     * 阈值设置为220， 能满足大部分程序，二维矩阵不会置为0，重新再产生值。
     */
    private static final int MAX_CALL_RANDOM_ARRAY_TIMES = 220;

    //记录当前buildRandomArray()方法调用的次数
    private int currentTimes = 0;

    public int[][] generatePuzzleMatrix() {
        int[][] randomMatrix = new int[9][9];
        for (int row = 0; row < 9; row++) {
            if (row == 0) {
                currentTimes = 0;
                randomMatrix[row] = buildRandomArray();
            } else {
                int[] tempRandomArray = buildRandomArray();
                for (int col = 0; col < 9; col++) {
                    if (currentTimes < MAX_CALL_RANDOM_ARRAY_TIMES) {
                        if (!isCandidateNmbFound(randomMatrix, tempRandomArray,
                                row, col)) {
                            /*
                             * 将该行的数据置为0，并重新为其准备一维随机数数组
                             */
                            resetValuesInRowToZero(randomMatrix,row);
                            row -= 1;
                            col = 8;
                            tempRandomArray = buildRandomArray();
                        }
                    } else {
                        /*
                         * 将二维矩阵中的数值置为0，
                         * row赋值为-1 col赋值为8， 下一个执行的就是row =0 col=0，
                         *
                         * 重头开始
                         */
                        row = -1;
                        col = 8;
                        resetValuesToZeros(randomMatrix);
                        currentTimes = 0;
                    }
                }
            }
        }
        return randomMatrix;
    }

    private void resetValuesInRowToZero(int[][] matrix, int row)
    {
        for (int j = 0; j < 9; j++) {
            matrix[row][j] = 0;
        }

    }

    private void resetValuesToZeros(int[][] matrix) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                matrix[row][col] = 0;
            }
        }
    }

    private boolean isCandidateNmbFound(int[][] randomMatrix, int[] randomArray, int row, int col) {
        for (int i = 0; i < randomArray.length; i++) {
            /*
             * 试着给randomMatrix[row][col] 赋值,并判断是否合理
             */
            randomMatrix[row][col] = randomArray[i];
            if (noConflict(randomMatrix, row, col)) {
                return true;
            }
        }
        return false;
    }

    private boolean noConflict(int[][] candidateMatrix, int row, int col) {
        return noConflictInRow(candidateMatrix, row, col)
                && noConflictInColumn(candidateMatrix, row, col)
                && noConflictInBlock(candidateMatrix, row, col);
    }

    private boolean noConflictInRow(int[][] candidateMatrix, int row, int col) {
        /*
         * 因为产生随机数矩阵是按照先行后列，从左到右产生的 ，该行当前列后面的所有列的值都还是0， 所以在行比较的时候，
         * 只要判断该行当前列与之前的列有无相同的数字即可。
         *
         */
        int currentValue = candidateMatrix[row][col];
        for (int colNum = 0; colNum < col; colNum++) {
            if (currentValue == candidateMatrix[row][colNum]) {
                return false;
            }
        }
        return true;
    }

    private boolean noConflictInColumn(int[][] candidateMatrix, int row, int col) {
        /*
         * 与noConflictInRow(...)方法类似：
         *
         *
         * 因为产生随机数矩阵是按照先行后列，从左到右产生的，该列当前行后面的所有行的值都还是0，
         *
         * 所以在列比较的时候， 只要判断该列当前行与之前的行有无相同的数字即可。
         *
         */
        int currentValue = candidateMatrix[row][col];
        for (int rowNum = 0; rowNum < row; rowNum++) {
            if (currentValue == candidateMatrix[rowNum][col]) {
                return false;
            }
        }
        return true;
    }

    private boolean noConflictInBlock(int[][] candidateMatrix, int row, int col) {
        /*
         * 为了比较3 x 3 块里面的数是否合理， 需要确定是哪一个Block，我们先要求出3 x 3的起始点。 比如： Block 1
         * 的起始点是[0][0] Block 2 的起始点是[3]][0]
         *
         * ... Block 9 的起始点是[6][6]
         */
        int baseRow = row / 3 * 3;
        int baseCol = col / 3 * 3;
        for (int rowNum = 0; rowNum < 8; rowNum++) {
            if (candidateMatrix[baseRow + rowNum / 3][baseCol + rowNum % 3] == 0) {
                continue;
            }
            for (int colNum = rowNum + 1; colNum < 9; colNum++) {
                if (candidateMatrix[baseRow + rowNum / 3][baseCol + rowNum % 3] == candidateMatrix[baseRow
                        + colNum / 3][baseCol + colNum % 3]) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
     * 返回一个有1到9九个数随机排列的一维数组,
     */
    private int[] buildRandomArray() {
        currentTimes++;
        int[] array = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        int randomInt = 0;
        /*
         * 随机产生一个1到8的随机数，使得该下标的数值与下标为0的数值交换，
         *
         *  处理20次，能够获取一个有1到9九个数随机排列的一维数组,
         */
        for (int i = 0; i < 20; i++) {
            randomInt = random.nextInt(8) + 1;
            int temp = array[0];
            array[0] = array[randomInt];
            array[randomInt] = temp;
        }
        return array;
    }
}
package yibihuawan;


public class Checkpoint {
    public int row;
    public int column;

    public int startX;
    public int startY;

    public int testCount = 0;
    int step = 1;

    int[][] array;
    boolean hasWin = false;

    public int[][] getArray() {
        return array;
    }

    /**
     * array 0代表起点，1-n代表行走顺序，-1代表不可达
     *
     * @param array
     */
    public Checkpoint(int[][] array, int startX, int startY) {
        this.array = array;
        row = array.length;
        column = array[0].length;
        this.array[startX][startY] = 1;
        this.startX = startX;
        this.startY = startY;
    }

    public boolean hasWin() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (array[i][j] == 0) {
                    return false;
                }
            }
        }
        hasWin = true;
        return true;
    }

    public void solve(int nowX, int nowY) {
        testCount++;
        if (hasWin()) {
            return;
        }

        if (!hasWin && nowY > 0 && array[nowX][nowY - 1] == 0) {
            array[nowX][nowY - 1] = ++step;
            solve(nowX, nowY - 1);
        }//left
        if (!hasWin && nowY < column - 1 && array[nowX][nowY + 1] == 0) {
            array[nowX][nowY + 1] = ++step;
            solve(nowX, nowY + 1);
        }//right
        if (!hasWin && nowX > 0 && array[nowX - 1][nowY] == 0) {
            array[nowX - 1][nowY] = ++step;
            solve(nowX - 1, nowY);
        }//up
        if (!hasWin && nowX < row - 1 && array[nowX + 1][nowY] == 0) {
            array[nowX + 1][nowY] = ++step;
            solve(nowX + 1, nowY);
        }//down
        if (!hasWin && array[nowX][nowY] != 1) {
            array[nowX][nowY] = 0;
            step--;
        }

    }

    public void print() {
        System.out.println("try:" + testCount + "   result:" + (hasWin ? "Ok" : "No Answer") + "  step:" + step);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                System.out.print(array[i][j] == 0 ? "□" : array[i][j] == -1 ? "■" : array[i][j]);
                System.out.print("\t");
            }
            System.out.println("\n\n");
        }
    }

    public void print_plus() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (array[i][j] == -1) {
                    System.out.print("■");
                }
                if (array[i][j] == step) {
                    System.out.print("End");
                } else {
                    if (array[i][j] == 1) {
                        System.out.print("Start");
                    }
                    if (i < row - 1 && array[i][j] == array[i + 1][j] - 1) {
                        System.out.print("↓");
                    }
                    if (i > 0 && array[i][j] == array[i - 1][j] - 1) {
                        System.out.print("↑");
                    }
                    if (j < column - 1 && array[i][j] == array[i][j + 1] - 1) {
                        System.out.print("→");
                    }
                    if (j > 0 && array[i][j] == array[i][j - 1] - 1) {
                        System.out.print("←");
                    }
                }
                System.out.print("\t");
            }
            System.out.println("\n\n");
        }
    }

    public void caculate() {
        solve(startX, startY);
    }

    public static void main(String[] args) throws Exception {

        int[][] array = {
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0,},

                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1}};
//        array[0][1] = -1;
//        array[0][5] = -1;
//        array[3][3] = -1;
//        array[4][0] = -1;
//        array[5][4] = -1;
//        array[0][1] = -1;
//        array[1][1] = 1;
        Checkpoint question = new Checkpoint(array, 0, 0);

        question.caculate();

        question.print_plus();

    }

}
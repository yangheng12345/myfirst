package yibihuawan;

public class Checkpoint {
  //public Grid[][] grids;
  public int row;
  public int column;

  public int startX;
  public int startY;

  /*public int nowX;
  public int nowY;*/
  public int testCount = 0;
  int step = 1;

  int[][] array;
  boolean hasWin = false;

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

  public static void main(String[] args) throws Exception {

    //int[][] array = {
    //    {0,	  0,	0, 0,	0,    0},
    //};
    //Checkpoint question = new Checkpoint(array,0,0);

//    int[][] array = {
//        {0,	  -1,	0, 0,	0,    -1},
//        {0,     0,	0, 0,	0,    0 },
//        {0,	   0,	0, 0,	0,    0 },
//        {0,	   0,	0, -1,	0,    1,},
//
//        {-1,   0,	0, 0,	0,	    0},
//        {0,    0,	0, 0,	-1,	    0},
//        {0,    0,	0, 0,	0,	    0},
//        {0,    0,	0, 0,	0,	    0}};

    int[][] array = {
        {-1, 0, -1, -1},
        {0, 0, 0, 0},
        {0, 0, 1, 0},
        {-1, 0, 0, 0}
    };
    Checkpoint question = new Checkpoint(array, 2, 2);

    question.caculate();

    question.print();
    question.print_plus();
  }

  public int[][] getArray() { 
    return array;
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
    //MyPoint point = stack.peek();
    if (hasWin()) {
      return;
    }

    if (!hasWin && nowY > 0 && array[nowX][nowY - 1] == 0) {
      //System.out.println("←");
      array[nowX][nowY - 1] = ++step;
      solve(nowX, nowY - 1);
    }//left
    if (!hasWin && nowY < column - 1 && array[nowX][nowY + 1] == 0) {
      //System.out.println("→");
      array[nowX][nowY + 1] = ++step;
      solve(nowX, nowY + 1);
    }//right
    if (!hasWin && nowX > 0 && array[nowX - 1][nowY] == 0) {
      //System.out.println("↑");
      array[nowX - 1][nowY] = ++step;
      solve(nowX - 1, nowY);
    }//up
    if (!hasWin && nowX < row - 1 && array[nowX + 1][nowY] == 0) {
      //System.out.println("↓");
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
        System.out.print(array[i][j] == 0 ? "□" : array[i][j] == -1 ? "■" : array[i][j] + "");
        System.out.print("\t");
      }
      System.out.println("\n\n");
    }
  }

  public void print_plus() {
    //System.out.println("try:" + testCount + "   result:" + ( hasWin ?"Ok":"No Answer"));
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < column; j++) {
        System.out.print(array[i][j] + "   ");
        //if(array[i][j] == -1)
        //  System.out.print("■");
        //if(array[i][j] == step) {
        //  System.out.print("End");
        //}else {
        //  if(array[i][j] == 1) {
        //    System.out.print("Start");
        //  }
        //  if(i < row - 1 && array[i][j] == array[i + 1][j] - 1)
        //    System.out.print("↓");
        //  if(i > 0 && array[i][j] == array[i - 1][j] - 1)
        //    System.out.print("↑");
        //  if(j < column - 1 && array[i][j] == array[i][j + 1] - 1)
        //    System.out.print("→");
        //  if(j> 0 && array[i][j] == array[i][j - 1] - 1)
        //    System.out.print("←");
        //}
        //System.out.print("\t");
      }
      System.out.println("\n\n");
    }
  }

  //  public void print_plus2() throws Exception {
//    TreeMap<Integer, MyPosition> map = new TreeMap<Integer,MyPosition>();
//    for(int i = 0; i < row; i++) {
//      for(int j = 0; j < column; j++) {
//        map.put(array[i][j], new MyPosition(i,j));
//      }
//    }
//    Iterator iterator = map.keySet().iterator();
//    while (iterator.hasNext()) {
//      int key = (int) iterator.next();
//
//      for(int i = 0; i < row; i++) {
//        for(int j = 0; j < column; j++) {
//          if(array[i][j] < key) {
//            if(array[i][j] == -1)
//              System.out.print("■");
//            if(array[i][j] == step) {
//              System.out.print("End");
//            }else {
//              if(array[i][j] == 1) {
//                System.out.print("Start");
//              }
//              if(i < row - 1 && array[i][j] == array[i + 1][j] - 1)
//                System.out.print("↓");
//              if(i > 0 && array[i][j] == array[i - 1][j] - 1)
//                System.out.print("↑");
//              if(j < column - 1 && array[i][j] == array[i][j + 1] - 1)
//                System.out.print("→");
//              if(j> 0 && array[i][j] == array[i][j - 1] - 1)
//                System.out.print("←");
//            }
//          }else {
//            System.out.print(" ");
//          }
//          System.out.print("\t");
//        }
//        System.out.println("\n\n");
//      }
//      Thread.sleep(200);
//      for(int i = 0;i++ < 40;) {
//        System.out.println();
//      }
//    }
//    //System.out.println("try:" + testCount + "   result:" + ( hasWin ?"Ok":"No Answer"));
//    for(int i = 0; i < row; i++) {
//      for(int j = 0; j < column; j++) {
//        if(array[i][j] == -1)
//          System.out.print("■");
//        if(array[i][j] == step) {
//          System.out.print("End");
//        }else {
//          if(array[i][j] == 1) {
//            System.out.print("Start");
//          }
//          if(i < row - 1 && array[i][j] == array[i + 1][j] - 1)
//            System.out.print("↓");
//          if(i > 0 && array[i][j] == array[i - 1][j] - 1)
//            System.out.print("↑");
//          if(j < column - 1 && array[i][j] == array[i][j + 1] - 1)
//            System.out.print("→");
//          if(j> 0 && array[i][j] == array[i][j - 1] - 1)
//            System.out.print("←");
//        }
//        System.out.print("\t");
//      }
//      System.out.println("\n\n");
//    }
//  }
  public void caculate() {
    solve(startX, startY);
  }

}

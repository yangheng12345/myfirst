package yibihuawan;

import java.util.ArrayDeque;

public class MazeTest {
    /*
     * 使用递归求出路径。
     * */
    /*迷宫1
     * private char[][] maze = new char[][]{
        {'1','1','1','1','1','1','1','1','1','1','1'},
        {'1', 0,  0,  0,  0,  0, '1', 0,  0,  0, '1'},
        {'1', 0, '1', 0, '1','1','1', 0, '1', 0, '1'},
        {'e', 0, '1', 0,  0,  0,  0,  0, '1', 0, '1'},
        {'1', 0, '1','1','1','1','1', 0, '1', 0, '1'},
        {'1', 0, '1', 0, '1', 0,  0,  0, '1','1','1'},
        {'1', 0,  0,  0, '1', 0, '1','1','1', 0, '1'},
        {'1','1','1','1','1', 0, '1', 0,  0,  0, '1'},
        {'1', 0, '1','m','1', 0, '1',0, 0,  0, '1'},
        {'1', 0,  0,  0,  0,  0, '1', 0,  0,  0, '1'},
        {'1','1','1','1','1','1','1','1','1','1','1'},
    };*/

    /*
     * 迷宫2
    private char[][] maze = new char[][]{
            {'1',   '1', '1',    '1', '1', '1', '1', '1', '1', '1', '1'},
            {'1',   0, 0,    0, 0, 0, '1', 0, 0, 0, '1'},
            {'1',   0, '1',    0, 0, 0, '1', 0, '1', 0, '1'},
            {'e',   0, '1',    0, 0, 0, 0, 0, '1', 0, '1'},
            {'1',   0, '1',    '1', '1', '1', '1', 0, '1', 0, '1'},
            {'1',   0, '1',    0, '1', 0, 0, 0, '1', 0, '1'},
            {'1',   0, 0,    0, '1', 0, '1', 0, 0, 0, '1'},
            {'1',   '1', '1',    '1', '1', 0, '1', 0, 0, 0, '1'},
            {'1',   0, '1',    'm', '1', 0, '1', 0, 0, 0, '1'},
            {'1',   0, 0,    0, 0, 0, '1', 0, 0, 0, '1'},
            {'1',   '1', '1',    '1', '1', '1', '1', '1', '1', '1', '1'},
        };
        * /

    /*
     * 迷宫3
     *
     * */
    private char[][] maze = new char[][]{
            {'1','1','1','1','1','1','1','1','1','1','1'},
            {'1', 0,  0,  0,  0,  0, '1', 0,  0,  0, '1'},
            {'1', 0, '1', 0, '1','1','1', 0, '1', 0, '1'},
            {'e', 0, '1', 0,  0,  0,  0,  0, '1', 0, '1'},
            {'1', 0, '1','1','1','1','1', 0, '1', 0, '1'},
            {'1', 0, '1', 0, '1', 0,  0,  0, '1', 0,'1'},
            {'1', 0,  0,  0, '1', 0, '1', 0,'1',  0, '1'},
            {'1','1','1','1','1', 0, '1', 0,  '1',0, '1'},
            {'1', 0, '1','1','1', 0, '1',0,'1', 0, '1'},
            {'1', 0,  0,  0,  0,  0, '1', 0,  0, '1', '1'},
            {'1','1','1','1','1','1','1','1',0,'m','1'},
    };

    private char pass = '@';
    private char noPass = 'n';
    private boolean finish = false;

    //定义一个用于存储老鼠走过的路径的栈集合。
    ArrayDeque<MouseLoc> aDeque = new ArrayDeque<>();
    //定义一个用于保存老鼠位置的类。
    MouseLoc mLoc = new MouseLoc();
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        MazeTest mt = new MazeTest();
        mt.init();
    }

    void init(){
        //打印出初始状态。
        printMaze();
        //找到老鼠当前位置。
        this.findMouse();
        System.out.println("老鼠的初始位置为：(" + mLoc.x + "," + mLoc.y + ")");
        //把老鼠的初始位置加入到栈中。
        aDeque.offerFirst(mLoc);
        //开始搜寻出路(递归)。
        search(mLoc.x,mLoc.y);

        //寻路完毕，整理老鼠的路径。
        for(int i=0;i<maze.length;i++)
        {
            for(int j=0;j<maze[i].length;j++)
            {
                if(maze[i][j] == noPass){
                    maze[i][j] = 0;
                }
            }
        }
        System.out.println("寻路结果为：\n");
        printMaze();

        System.out.println("老鼠走过的路径为：");
        short stackSize = (short)aDeque.size();
        for(short i=0;i<stackSize;i++){
            MouseLoc ml = aDeque.removeLast();
            System.out.println((1+i)+"、("+ml.x+","+ml.y+")");
        }
    }// init method -- end.

    void search(int x,int y){
        int failCount = 0;
        // 上
        if(isLocCorr(x-1,y)){
            if (!finish && maze[x-1][y] == 0) {
                /*
                 * 如果检测到上方路段为可走空间，则去走，并
                 * 将此此空间标记为已走过路段。
                 * */
                aDeque.offerFirst(new MouseLoc(x - 1, y)); // step1. push into stack.
                maze[x][y] = pass;//step2. sign it.
                search(x-1,y);// step3. new search.
                if(maze[x-1][y] == noPass){
                    failCount++;
                }
            } else if (maze[x - 1][y] == 'e') {
                /*
                 * 如果检测到它是一个出口，则把当前位置标为已走过。
                 * 然后再把出口位置入栈，最后把出口位置标为已走过。
                 * 完成寻路。
                 * */
                maze[x][y] = pass;
                aDeque.offerFirst(new MouseLoc(x-1,y));
                maze[x - 1][y] = pass;
                finish = true;
                return;
            }else{
                failCount++;
            }
        }else{
            //如果位置检测不通过，也得让failCount的值自加1.
            failCount++;
        }


        // 下
        if(isLocCorr(x+1, y)){
            if (!finish && maze[x + 1][y] == 0) {
                aDeque.offerFirst(new MouseLoc(x + 1,y));
                maze[x][y] = pass;
                search(x+1,y);
                if(maze[x+1][y] == noPass){
                    failCount++;
                }
            } else if (maze[x + 1][y] == 'e') {
                maze[x][y] = pass;
                aDeque.offerFirst(new MouseLoc(x-1,y));
                maze[x+1][y] = pass;
                finish = true;
                return;
            }else{
                failCount++;
            }
        }else{
            //如果位置检测不通过，也得让failCount的值自加1.
            failCount++;
        }

        // 左
        if(isLocCorr(x, y-1)){
            if (!finish && maze[x][y - 1] == 0) {
                aDeque.offerFirst(new MouseLoc(x, y - 1));
                maze[x][y] = pass;
                search(x,y-1);
                if(maze[x][y-1] == noPass){
                    failCount++;
                }
            } else if (maze[x][y - 1] == 'e') {
                maze[x][y] = pass;
                aDeque.offerFirst(new MouseLoc(x,y-1));
                maze[x][y-1] = pass;
                finish = true;
                return;
            }else{
                failCount++;
            }
        }else{
            //如果位置检测不通过，也得让failCount的值自加1.
            failCount++;
        }

        // 右
        if(isLocCorr(x, y+1)){
            if (!finish && maze[x][y + 1] == 0) {
                aDeque.offerFirst(new MouseLoc(x, y + 1));
                maze[x][y] = pass;
                search(x,y+1);
                if(maze[x][y+1] == noPass){
                    failCount++;
                }
            } else if (maze[x][y + 1] == 'e') {
                maze[x][y] = pass;
                aDeque.offerFirst(new MouseLoc(x,y+1));
                maze[x][y+1] = pass;
                finish = true;
                return;
            }else{
                failCount++;
            }
        }else{
            //如果位置检测不通过，也得让failCount的值自加1.
            failCount++;
        }

        if(failCount == 4){
            MouseLoc m = aDeque.pop();
            maze[m.x][m.y] = noPass;
        }
    }// search method  --  end.

    void printMaze(){
        //专门打印迷宫阵列。
        for(int i=0;i<maze.length;i++)
        {
            for(int j=0;j<maze[i].length;j++)
            {
                System.out.print(maze[i][j]);
            }
            System.out.println("");
        }
    }

    private boolean isLocCorr(int x,int y){
        //用于判断给定的位置坐标是否超出迷宫范围。
        if(x<0 || y<0 || x>findYLength()-1 || y>findXLength()-1){
            return false;
        }
        return true;
    }

    private int findYLength() {
        //返回迷宫上下距离。(y)
        return maze.length;
    }

    private int findXLength() {
        //返回迷宫左右距离。(x)
        return maze[0].length;
    }

    void findMouse(){
        //用于寻找老鼠的起始位置。
        for(int i=0;i<maze.length;i++)
        {
            for(int j=0;j<maze[i].length;j++)
            {
                if(maze[i][j] == 'm'){
                    mLoc.x = i;
                    mLoc.y = j;
                    return;
                }
            }
        }
    }// findMouse method  -- end.

}


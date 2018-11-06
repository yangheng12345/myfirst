package yibihuawan;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class TestClass {
    static int w = 6;
    static int h = 6;

    public static void main(String[] args) {

        int a[][] = new int[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                a[i][j] = 0;
            }
        }
        a[1][0] = 1;
        a[4][1] = 1;
        a[1][3] = 1;
        a[5][3] = 1;
//        a[3][5] = 1;
        print(a, w, h);

        int startW = 3;
        int startH = 4;
        List<String> list = new ArrayList<>();
        int all = 32;
        ArrayDeque<MouseLoc> aDeque = new ArrayDeque<>();
        test(startW, startH, list, a, all, aDeque);
        System.out.println("test0000000");
        System.out.println("--------");
//        print(b, w, h);

    }

    private static void test(int nowW, int nowH, List<String> result, int[][] a, int all, ArrayDeque<MouseLoc> aDeque) {
        String step = nowW + " " + nowH;
        MouseLoc mouseLoc = new MouseLoc();

        if (aDeque.size() == 32) {
            return;
        }
        int failCount = 0;
        if (nowW > 0) {
            int ww = nowW - 1;
            if (a[ww][nowH] == 0) {
                all--;
                a[ww][nowH] = 1;
                result.add(step);
                mouseLoc.x = ww;
                mouseLoc.y = nowH;

                aDeque.offerFirst(mouseLoc);
//                if (all == 0) {
//                    return ;
//                } else {
                test(ww, nowH, result, a, all, aDeque);
//                }
            }else {
                failCount++;
            }
        } else {
            failCount++;
        }
        if (nowW < (w - 1)) {
            int ww = nowW + 1;
            if (a[ww][nowH] == 0) {
                all--;
                a[ww][nowH] = 1;
                result.add(step);
                mouseLoc.x = ww;
                mouseLoc.y = nowH;

                aDeque.offerFirst(mouseLoc);
//                if (all == 0) {
//                    return ;
//                } else {
                test(ww, nowH, result, a, all, aDeque);
//                }
            }else {
                failCount++;
            }
        } else {
            failCount++;
        }
        if (nowH > 0) {
            int hh = nowH - 1;
            if (a[nowW][hh] == 0) {
                a[nowW][hh] = 1;
                result.add(step);
                all--;
                mouseLoc.x = nowW;
                mouseLoc.y = hh;

                aDeque.offerFirst(mouseLoc);
//                if (all == 0) {
//                    return ;
//                } else {
                test(nowW, hh, result, a, all, aDeque);
//                }
            }else {
                failCount++;
            }
        } else {
            failCount++;
        }
        if (nowH < (h - 1)) {
            int hh = nowH + 1;
            if (a[nowW][hh] == 0) {
                a[nowW][hh] = 1;
                result.add(step);
                all--;
                mouseLoc.x = nowW;
                mouseLoc.y = hh;
                aDeque.offerFirst(mouseLoc);
//                if (all == 0) {
//                    return ;
//                } else {
                test(nowW, hh, result, a, all, aDeque);
//                }
            }else {
                failCount++;
            }
        } else {
            failCount++;
        }
        if (failCount == 4) {
            MouseLoc pop = aDeque.pop();
            a[pop.x][pop.y] = 0;
            all++;
        }
        return;
    }

    private static void print(int[][] a, int w, int h) {
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                System.out.print(" " + a[i][j] + " ");
            }
            System.out.println();
        }
    }
}

package game;
import Move.*;
import java.io.IOException;
import java.util.Random;

public class Comp extends Player{
    //This class contains computer player's data and function
    Comp(playerType id, Board b,String name){
        super(id,b,name);
    }
    public static void moveTo(){
        //this method generates a random move
        Random r = new Random();
        int xd = r.nextInt(8),yd=r.nextInt(8),xs=r.nextInt(8),ys=r.nextInt(8);
        move.sourceTile=Board.tiles[xs][ys];
        CalculateObj obj=new CalculateObj();
        while(!(obj.cond1&&obj.cond2)){
            obj.cond1=false;
            obj.cond2=false;
            xs=r.nextInt(8);
            ys=r.nextInt(8);
            move.sourceTile=Board.tiles[xs][ys];
            obj.setXandY(xs,ys);
            thread1 t1 = new thread1(obj);
            thread2 t2 = new thread2(obj);
            t1.start();
            t2.start();
            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while(true) {
            try{
                new move().moveTo(Board.tiles[xd][yd]);

                break;
            }
            catch(Move.IllegalMove i){
                xd = r.nextInt(8);
                yd=r.nextInt(8);
            }
            catch (IOException e){
                System.out.println("Io exc");
            }
        }
    }
}
class CalculateObj{
    Boolean cond1=false,cond2=false;//cond1 tile.p is not null and tile.piecetype is turn
    Boolean thread1Finish=false;
    int x,y;
    void setXandY(int x,int y){
        this.x=x;
        this.y=y;
    }

    synchronized void expression1(){
        if(Board.tiles[x][y].p!=null && Board.tiles[x][y].pieceType.ordinal()==Board.turn){
            cond1 = true;
            move.sourceTile=Board.tiles[x][y];
        }
        thread1Finish = true;
        notify();
    }
    synchronized void expression2(){
        if(!thread1Finish){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(cond1){
            cond2=moveStruct.check(move.generateMoves());
        }
        else{
            cond2 = false;
        }
        thread1Finish=false;
    }

}
class thread1 extends Thread{
    CalculateObj obj;
    thread1(CalculateObj obj){
        this.obj = obj;
    }

    @Override
    public void run() {
        obj.expression1();
    }
}
class thread2 extends Thread{
    CalculateObj obj;
    thread2(CalculateObj obj){
        this.obj = obj;
    }

    @Override
    public void run() {
        obj.expression2();
    }
}

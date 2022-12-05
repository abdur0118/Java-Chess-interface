package Move;
import game.Comp;
import game.Main;
import game.Player;
import java.io.*;
import java.util.ArrayList;

interface legal{
    boolean isLegal(Tile dest);
}

public class move implements moveStruct{
    //This class contains represents the operations on a board
    public static Tile sourceTile;
    public static Tile destTile;
    static void setSourceTile(Tile source){
        sourceTile=source;
    }

    static void clear(){
        sourceTile=null;
        destTile=null;
    }

    static void makeMove(Tile destination) throws IOException {
        //updates the destination tile
        move.destTile = destination;
        move.destTile.setTile(sourceTile.pieceType, sourceTile.p);
        //clears the source tile
        sourceTile.pieceType = null;
        sourceTile.p = null;
        move.sourceTile.getChildren().clear();
        move.clear();//clears the instance variables for futher use

        Board.turn = Board.turn==Player.playerType.BLACK.ordinal()?Player.playerType.WHITE.ordinal() : Player.playerType.BLACK.ordinal(); //changes the turn
        //gives the control to the computer if its computers turn
        if(Board.turn==Player.playerType.BLACK.ordinal()&& (!Main.Bhum)){
            Comp.moveTo();
        }
        if(Board.turn==Player.playerType.WHITE.ordinal()&&!Main.Whum){
            Comp.moveTo();
        }
    }
    static void removePiece(Tile destination){
        if(destination.p.equals("K")){
            Main.exit(destination);
        }
        //killing the piece
        destination.getChildren().clear();
        destination.p = null;
        destination.pieceType = null;
    }

    public void moveTo(Tile destination) throws IOException,IllegalMove {
        legal ref = (dest)->{
            ArrayList<ArrayList<Boolean>> moves;
            switch(sourceTile.p){
                case "B" : if(((dest.col+dest.row) != (sourceTile.col+ sourceTile.row)) && (Math.abs(dest.col-dest.row) != Math.abs(sourceTile.col- sourceTile.row)) ){
                    return false;
                }
                else{
                    moves =generateMoves();
                    return moves.get(dest.col).get(dest.row);
                }

                case "R": if(dest.col!=sourceTile.col && dest.row!= sourceTile.row){
                    return false;
                }
                else{
                    moves = generateMoves();
                    return moves.get(dest.col).get(dest.row);
                }
                case "P":
                case "Q":
                case "K":
                case "N": moves = generateMoves();
                    return moves.get(dest.col).get(dest.row);
            }
            return true;
        };
        if (ref.isLegal(destination)) {
            //if the move is valid
            if (destination.p == null) {
                makeMove(destination);//proceeds to make the move
            } else {
                if (destination.pieceType == move.sourceTile.pieceType) {
                    //the piece on destinaltion tile belongs to the same player as in the sourcetile
                    throw new IllegalMove();

                } else {
                    removePiece(destination);//kills the piece first
                    makeMove(destination);//proceeds to make the move
                }
            }
        }
        else{
            throw new IllegalMove();
        }
    }

    public static ArrayList<ArrayList<Boolean>> generateMoves(){
        //generate all the legal moves of the source tile in a 2 dimension boolean matrix
        ArrayList<ArrayList<Boolean>> moves = new ArrayList<>();
        for(int i=0;i<8;i++){
            moves.add(new ArrayList<>());
            for(int j=0;j<8;j++){
                moves.get(i).add(false);
            }

        }
        switch (sourceTile.p) {
            case "Q":
            case "B":
                int i = sourceTile.col  +1, j = sourceTile.row +1;
                while (i < 8 && j < 8) {
                    if(Board.tiles[i][j].p!=null && Board.tiles[i][j].pieceType==sourceTile.pieceType){
                        break;
                    }
                    if(Board.tiles[i][j].p!=null && Board.tiles[i][j].pieceType!=sourceTile.pieceType){

                        moves.get(i).set(j,true);

                        break;
                    }
                    moves.get(i).set(j, true);
                    i++;
                    j++;
                }
                i = sourceTile.col-1; j = sourceTile.row-1;
                while (i >= 0 && j >= 0) {
                    if(Board.tiles[i][j].p!=null && Board.tiles[i][j].pieceType==sourceTile.pieceType){
                        break;
                    }
                    if(Board.tiles[i][j].p!=null && Board.tiles[i][j].pieceType!=sourceTile.pieceType){
                        moves.get(i).set(j,true);
                        break;
                    }
                    moves.get(i).set(j, true);
                    i--;
                    j--;
                }
                i = sourceTile.col-1; j = sourceTile.row+1;
                while (i >= 0 && j < 8 ) {
                    if(Board.tiles[i][j].p!=null && Board.tiles[i][j].pieceType==sourceTile.pieceType){
                        break;
                    }
                    if(Board.tiles[i][j].p!=null && Board.tiles[i][j].pieceType!=sourceTile.pieceType){
                        moves.get(i).set(j,true);
                        break;
                    }
                    moves.get(i).set(j, true);
                    i--;
                    j++;
                }
                i = sourceTile.col+1; j = sourceTile.row-1;
                while (i < 8 && j >= 0) {
                    if(Board.tiles[i][j].p!=null && Board.tiles[i][j].pieceType==sourceTile.pieceType){
                        break;
                    }
                    if(Board.tiles[i][j].p!=null && Board.tiles[i][j].pieceType!=sourceTile.pieceType){
                        moves.get(i).set(j,true);
                        break;
                    }
                    moves.get(i).set(j, true);
                    i++;
                    j--;
                }
                if(!sourceTile.p.equals("Q")) {
                    return moves;
                }
            case "R": i = sourceTile.col+1;
                while (i <8 ) {
                    if(Board.tiles[i][sourceTile.row].p!=null && Board.tiles[i][sourceTile.row].pieceType==sourceTile.pieceType){
                        break;
                    }
                    if(Board.tiles[i][sourceTile.row].p!=null && Board.tiles[i][sourceTile.row].pieceType!=sourceTile.pieceType){
                        moves.get(i).set(sourceTile.row, true);
                        break;
                    }
                    moves.get(i).set(sourceTile.row, true);
                    i++;
                }
                i = sourceTile.row+1;
                while (i <8 ) {
                    if(Board.tiles[sourceTile.col][i].p!=null && Board.tiles[sourceTile.col][i].pieceType==sourceTile.pieceType){
                        break;
                    }
                    if(Board.tiles[sourceTile.col][i].p!=null && Board.tiles[sourceTile.col][i].pieceType!=sourceTile.pieceType){
                        moves.get(sourceTile.col).set(i, true);
                        break;
                    }
                    moves.get(sourceTile.col).set(i, true);
                    i++;
                }
                i = sourceTile.col-1;
                while (i >=0 ) {
                    if(Board.tiles[i][sourceTile.row].p!=null && Board.tiles[i][sourceTile.row].pieceType==sourceTile.pieceType){
                        break;
                    }
                    if(Board.tiles[i][sourceTile.row].p!=null && Board.tiles[i][sourceTile.row].pieceType!=sourceTile.pieceType){
                        moves.get(i).set(sourceTile.row, true);
                        break;
                    }
                    moves.get(i).set(sourceTile.row, true);
                    i--;
                }
                i = sourceTile.row-1;
                while (i >=0 ) {
                    if(Board.tiles[sourceTile.col][i].p!=null && Board.tiles[sourceTile.col][i].pieceType==sourceTile.pieceType){
                        break;
                    }
                    if(Board.tiles[sourceTile.col][i].p!=null && Board.tiles[sourceTile.col][i].pieceType!=sourceTile.pieceType){
                        moves.get(sourceTile.col).set(i, true);
                        break;
                    }
                    moves.get(sourceTile.col).set(i, true);
                    i--;
                }
                return moves;
            case "N":   int[][] eight = new int[8][2];
                eight[0][0]= sourceTile.col-1;      //x
                eight[0][1]= sourceTile.row+2;//y

                eight[1][0]= sourceTile.col-2;
                eight[1][1]= sourceTile.row+1;

                eight[2][0]= sourceTile.col-2;
                eight[2][1]= sourceTile.row-1;

                eight[3][0]= sourceTile.col-1;
                eight[3][1]= sourceTile.row-2;

                eight[4][0]= sourceTile.col+1;
                eight[4][1]= sourceTile.row-2;

                eight[5][0]= sourceTile.col+2;
                eight[5][1]= sourceTile.row-1;

                eight[6][0]= sourceTile.col+2;
                eight[6][1]= sourceTile.row+1;

                eight[7][0]= sourceTile.col+1;
                eight[7][1]= sourceTile.row+2;
                for(int count =0;count<8;count++){
                    if(eight[count][0]>=0&&eight[count][0]<8&&eight[count][1]<8&&eight[count][1]>=0&&sourceTile.pieceType!= Board.tiles[eight[count][0]][eight[count][1]].pieceType){
                        moves.get(eight[count][0]).set(eight[count][1],true);
                    }
                }
                return moves;
            case "K":eight = new int[8][2];
                eight[0][0]= sourceTile.col-1;      //x
                eight[0][1]= sourceTile.row+1;//y

                eight[1][0]= sourceTile.col-1;
                eight[1][1]= sourceTile.row;

                eight[2][0]= sourceTile.col-1;
                eight[2][1]= sourceTile.row-1;

                eight[3][0]= sourceTile.col;
                eight[3][1]= sourceTile.row-1;

                eight[4][0]= sourceTile.col+1;
                eight[4][1]= sourceTile.row-1;

                eight[5][0]= sourceTile.col+1;
                eight[5][1]= sourceTile.row;

                eight[6][0]= sourceTile.col+1;
                eight[6][1]= sourceTile.row+1;

                eight[7][0]= sourceTile.col;
                eight[7][1]= sourceTile.row+1;
                for(int count =0;count<8;count++){
                    if(eight[count][0]>=0&&eight[count][0]<8&&eight[count][1]<8&&eight[count][1]>=0&&sourceTile.pieceType!= Board.tiles[eight[count][0]][eight[count][1]].pieceType){
                        moves.get(eight[count][0]).set(eight[count][1],true);
                    }
                }
                return moves;
            case "P":if(sourceTile.pieceType== Player.playerType.WHITE){
                if(sourceTile.row==6&&Board.tiles[sourceTile.col][5].pieceType==null&&Board.tiles[sourceTile.col][4].pieceType==null){
                    moves.get(sourceTile.col).set(4, true);
                }
                if(sourceTile.row-1>=0&&Board.tiles[sourceTile.col][sourceTile.row-1].pieceType==null) {
                    moves.get(sourceTile.col ).set(sourceTile.row-1, true);
                }
                if(sourceTile.row-1>=0&&sourceTile.col+1<8&&Board.tiles[sourceTile.col+1][sourceTile.row-1].p!=null && Board.tiles[sourceTile.col+1][sourceTile.row-1].pieceType== Player.playerType.BLACK){
                    moves.get(sourceTile.col+1).set(sourceTile.row-1,true);
                }
                if(sourceTile.row-1>=0&&sourceTile.col-1>=0&&Board.tiles[sourceTile.col-1][sourceTile.row-1].p!=null && Board.tiles[sourceTile.col-1][sourceTile.row-1].pieceType== Player.playerType.BLACK){

                    moves.get(sourceTile.col-1).set(sourceTile.row-1,true);
                }
            }
                if(sourceTile.pieceType== Player.playerType.BLACK){
                    if(sourceTile.row==1&&Board.tiles[sourceTile.col][2].pieceType==null&&Board.tiles[sourceTile.col][3].pieceType==null){
                        moves.get(sourceTile.col).set(3, true);
                    }
                    if(sourceTile.row+1<8&&Board.tiles[sourceTile.col][sourceTile.row+1].pieceType==null) {
                        moves.get(sourceTile.col ).set(sourceTile.row+1, true);
                    }
                    if(sourceTile.row+1<8&&sourceTile.col+1<8&&Board.tiles[sourceTile.col+1][sourceTile.row+1].p!=null && Board.tiles[sourceTile.col+1][sourceTile.row+1].pieceType== Player.playerType.WHITE){
                        moves.get(sourceTile.col+1).set(sourceTile.row+1,true);
                    }
                    if(sourceTile.row+1<8&&sourceTile.col-1>=0&&Board.tiles[sourceTile.col-1][sourceTile.row+1].p!=null && Board.tiles[sourceTile.col-1][sourceTile.row+1].pieceType== Player.playerType.WHITE){

                        moves.get(sourceTile.col-1).set(sourceTile.row+1,true);
                    }
                }
                return moves;

        }
        return null;
    }
}


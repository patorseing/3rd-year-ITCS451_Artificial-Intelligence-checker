

import java.util.*;

public class YupuechPlayer extends CheckerPlayer implements CheckerBoardConstants {

	public YupuechPlayer(String name){
		super(name);
	}

	public void calculateMove(int[][] bs, int whosTurn){

		// your code should go here
		
		chosenMove = null; 
		
		Vector<Move> allMoves = Utils.getAllPossibleMoves(bs, whosTurn);
		
		if (allMoves.isEmpty()) {
			chosenMove = null;
			return;
		}
		
		int a = Integer.MIN_VALUE;	//alpha
		int b = Integer.MAX_VALUE;	//bata
		
		int depth = 3;
		
		chosenMove = allMoves.get(0);
		
		for(Move nextmove : allMoves) {
			int[][] nBs = Utils.copyBoardState(bs); // nBs = next boardState
			Utils.executeMove(whosTurn, nextmove, nBs);
			int min = min(nBs, whosTurn, a, b, depth-1);
			if (min == a && Math.random() > 0.5){
				a = min;
				chosenMove = nextmove;
			}
			if (min > a){
				a = min;
				chosenMove = nextmove;
			}
		}
	}
	
	public int min(int[][] bs, int whosTurn, int alpha, int beta, int depth){
		
		if (depth<=0) {
			return checkscore(bs,whosTurn);
		}
		
		Vector<Move> allMoves = Utils.getAllPossibleMoves(bs, whosTurn);
		int max;
		
		for(Move nextmove : allMoves) {
			int[][] nBs = Utils.copyBoardState(bs); // nBs = next boardState
			Utils.executeMove(whosTurn, nextmove, nBs);
			max = max(nBs, whosTurn, alpha, beta, depth - 1);
			beta = Math.min(alpha, max); //if
			
			if (alpha>=beta)  {			
				return beta;
			}
			
		}
		
		return beta;
	}

	 public int max(int[][] bs, int whosTurn, int alpha, int beta, int depth){
		 
		 if (depth<=0) {
			 return checkscore(bs,whosTurn);
		 }
			
		 Vector<Move> allMoves = Utils.getAllPossibleMoves(bs, whosTurn);
		 int min;
			
			for(Move nextmove : allMoves) {
				int[][] nBs = Utils.copyBoardState(bs); // nBs = next boardState
				Utils.executeMove(whosTurn, nextmove, nBs);
				min = min(nBs, whosTurn, alpha, beta, depth - 1);
				alpha = Math.max(alpha, min); //if
				
				if (alpha>=beta)  {			
					return alpha;
				}
				
			}
		return alpha;	 
	 }
	 
	 public int checkscore(int[][] bs, int whosTurn){
		 return (whosTurn == RED_PLAYER ? -1: 1) * scoreCheckerBoard(bs, whosTurn);
	 }
	 
	 public static int scoreCheckerBoard(int[][] boardState, int whosTurn){

		  // number of red pawns on the board
		  int redPawnCount = 0;
		  // number of black pawns on the board
		  int blackPawnCount = 0;
		  // number of red kings on the board
		  int redKingCount = 0;
		  // number of black kings on the board
		  int blackKingCount = 0;

		  // go through the board and count the pawns and kings for
		  // both the red and black player.
		  for(int row=0;row<BOARD_HEIGHT;row++){
		   for(int col=0;col<BOARD_WIDTH;col++){
		    int piece = boardState[row][col];
		    if(piece==RED_PAWN){
		     redPawnCount+=8-row;
		    }else if(piece==RED_KING){
		     redKingCount+=( 8 - Math.abs(row-4) ) * 10;
		    }else if(piece==BLACK_PAWN){
		     blackPawnCount+=row;
		    }else if(piece==BLACK_KING){
		     blackKingCount+=( 8 - Math.abs(row-4) ) * 10;
		    }
		   }
		  }
		  // use the tallies that have been calculated to get a final board score.
		  int score = (redPawnCount-blackPawnCount);
		  score += 2*(redKingCount-blackKingCount);

		  int r = redPawnCount + redKingCount;
		  int b = blackPawnCount + blackKingCount;

		  score = whosTurn == RED_PLAYER ? r * 2 - b : b * 2 - r;

		  return score;

		 }
}
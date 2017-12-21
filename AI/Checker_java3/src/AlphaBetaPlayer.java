

import java.util.*;

public class AlphaBetaPlayer extends CheckerPlayer implements CheckerBoardConstants {

	public AlphaBetaPlayer(String name){
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
		 return (whosTurn == RED_PLAYER ? -1: 1) * Utils.scoreCheckerBoard(bs, whosTurn);
	 }
}
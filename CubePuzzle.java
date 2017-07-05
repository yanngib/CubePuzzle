import java.util.List;

public class CubePuzzle {

/*
 * Here is the cube that we need to build with the 6 puzzle pieces P0 thru P5.
 * Each piece needs to go on a face of the cube and edges need to match with the adjacent pieces
 *                    
 *                    
 *                    E5
 *                  +-----+
 *                E6| F2  |E4                F0 thru F5 are the 6 facets of the cube
 *        E5    E6  |     |  E4              and E1 thru E12 are the 12 edges.
 *      +-----+-----+-E10-+-----+
 *      | F5  | F4  | F0  | F1  |
 *   E0 |    E7    E8    E9     | E0
 *      +-----+-----+-E11-+-----+
 *        E1    E2  |     |  E3
 *                E2| F3  |E3
 *                  +-----+
 *                    E1
 * 
 * 
 * Each piece is defined by a series of 0's and 1's. 1 indicates a dent.
 * For example:
 * 
 * P0: 
 *          +---+
 *        0 | 1 | 0   0
 *          +   +---+---+    
 *        0 |         1 |
 *      +---+       +---+
 *      | 1         | 0
 *      +---+---+   +---+
 *        0   0 | 1   1 |
 * 	            +---+---+
 *  Starting from the upper left corner, and going around the piece clockwise, 
 *  we get 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0
 * 
 * 
 */
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PuzzlePiece[] pieces = {
				new PuzzlePiece(0b010010110010), 
				new PuzzlePiece(0b000010110110),
				new PuzzlePiece(0b000110111101),
				new PuzzlePiece(0b101001010011),
				new PuzzlePiece(0b010001100010),
				new PuzzlePiece(0b001010011001)				
				};
		
		Cube myCube = new Cube();
		
		List<PuzzleSolution> solutions = myCube.Solve(pieces);
		
		for (PuzzleSolution solution : solutions) {
			solution.Print();
		}

	}
	



}


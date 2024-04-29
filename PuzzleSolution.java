
public class PuzzleSolution {
	
	OrientedPiece[] orientedPieces = new OrientedPiece[6];
	
	public void Print() {
		for (int i = 0; i < orientedPieces.length; i++) {
			System.out.format("F%d -> (P%d, %d)", i, orientedPieces[i].pieceIndex+1, orientedPieces[i].rotation * 90);
			if (i <= 4) 
				System.out.print(", ");
			else
				System.out.println();
		}
	}
}

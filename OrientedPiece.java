
public class OrientedPiece {
	
	public int pieceIndex;
	public int rotation; //0 = no rotation, 1 = +90 degree, 2 = +180 degree, 3 = +270 degree
	
	public OrientedPiece(int p, int r) { 
		pieceIndex = p;
		rotation = (r + 4) % 4;
	}	
}

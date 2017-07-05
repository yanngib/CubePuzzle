
public class PieceEdge {
	public int pieceIndex;
	public int orientation; //0 = top, 1 = right, 2 = bottom, 3 = left
	
	public PieceEdge(int p, int o) { 
		pieceIndex = p;
		orientation = o;
	}
}

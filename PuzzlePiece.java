
public class PuzzlePiece {
	
	private int contour = 0;
	public boolean used = false;
	
	public PuzzlePiece(int c) {
		contour = c;
	}
	
	public int EdgeBits(int rotation) {
		return ((contour << 1) + ((contour >> 11) & 1)) >> (3 * (3 - ((rotation + 4) % 4))) & 0b1111;
	}
}

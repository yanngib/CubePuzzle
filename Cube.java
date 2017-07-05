import java.util.*;

public class Cube {
	
//	//cube topology is defined by its edges joining its facets
//	FacetEdge[] edges = new FacetEdge[]{
//		new FacetEdge(1, 1, 5, 3), // E0
//		new FacetEdge(3, 2, 5, 2), // E1
//		new FacetEdge(3, 3, 4, 2), // E2
//		new FacetEdge(1, 2, 3, 1), // E3
//		new FacetEdge(1, 0, 2, 1), // E4
//		new FacetEdge(2, 0, 5, 0), // E5
//		new FacetEdge(2, 3, 4, 0), // E6
//		new FacetEdge(4, 3, 5, 1), // E7
//		new FacetEdge(0, 1, 1, 3), // E8
//		new FacetEdge(0, 3, 4, 1), // E9
//		new FacetEdge(0, 0, 2, 2), // E10
//		new FacetEdge(0, 2, 3, 0)  // E11
//	};

	public List<PuzzleSolution> Solve(PuzzlePiece[] pieces) {
		
		List<PuzzleSolution> solutions = new ArrayList<PuzzleSolution>();
		
		//Build hashmap of (edgebits, edges) 
		//where edgebits is the integer representing the 4 bits of the edge of a piece,
		//and edges is the list of piece edges that are equal to edgebits
		
		HashMap<Integer, List<PieceEdge>> mapEdges = new HashMap<Integer, List<PieceEdge>>();
		for (int key = 0b0000; key <= 0b1111; key++)
			mapEdges.put(key, new ArrayList<PieceEdge>());
		
		for (int i = 0; i < pieces.length; i++) {
			for (int orientation = 0; orientation < 4; orientation++) {
				mapEdges.get(pieces[i].EdgeBits(orientation)).add(new PieceEdge(i, orientation));
			}
		}
		
		//DFS working from facet F0 to F5
		//If we reach F5 and we find a matching piece, then we got a solution
		
		Stack<Node> s = new Stack<Node>();
		int previousFacetIndex = -1;
		
		//To keep track of which piece is on each facet
		OrientedPiece[] piecesOnFacet = new OrientedPiece[6];
		
		Node start = new Node(0, new OrientedPiece(0, 0));
		s.add(start);
		
		while (!s.empty())
		{
			Node node = s.pop();
			
			for (int index = node.facetIndex; index <= previousFacetIndex; index++) {
				pieces[piecesOnFacet[index].pieceIndex].used = false;
			}
			
			previousFacetIndex = node.facetIndex;
			piecesOnFacet[node.facetIndex] = node.orientedPiece;
			pieces[node.orientedPiece.pieceIndex].used = true;
			
			//System.out.format("node(%d, %d, %d)\n", node.facetIndex, node.orientedPiece.pieceIndex, node.orientedPiece.rotation);
			
			switch (node.facetIndex) {
			case 0: 
				//Next facet is F1
				//Match on E9
				for (int edgebits : GetMachingEdgeBits(pieces[piecesOnFacet[0].pieceIndex].EdgeBits(1))) {
					for (PieceEdge edge : mapEdges.get(edgebits)) {
						if (!pieces[edge.pieceIndex].used) {
							OrientedPiece orientedPiece = new OrientedPiece(edge.pieceIndex, 3 - edge.orientation);
							s.add(new Node(node.facetIndex + 1, orientedPiece));
						}
					}
				}
				break;
			case 1: 
				//Next facet is F2
				//Match on E10
				for (int edgebits : GetMachingEdgeBits(pieces[piecesOnFacet[0].pieceIndex].EdgeBits(0))) {
					for (PieceEdge edge : mapEdges.get(edgebits)) {
						if (!pieces[edge.pieceIndex].used){
							OrientedPiece orientedPiece = new OrientedPiece(edge.pieceIndex, 2 - edge.orientation);
							//match on E4
							if (Match(
									pieces[orientedPiece.pieceIndex].EdgeBits(1 - orientedPiece.rotation),
									pieces[piecesOnFacet[1].pieceIndex].EdgeBits(0 - piecesOnFacet[1].rotation)))
								s.add(new Node(node.facetIndex + 1, orientedPiece));
						}
					}
				}
				break;
			case 2:
				//Next facet is F3
				//Match on E11
				for (int edgebits : GetMachingEdgeBits(pieces[piecesOnFacet[0].pieceIndex].EdgeBits(2))) {
					for (PieceEdge edge : mapEdges.get(edgebits)) {
						if (!pieces[edge.pieceIndex].used){
							OrientedPiece orientedPiece = new OrientedPiece(edge.pieceIndex, 0 - edge.orientation);
							//match on E3
							if (Match(
									pieces[orientedPiece.pieceIndex].EdgeBits(1 - orientedPiece.rotation),
									pieces[piecesOnFacet[1].pieceIndex].EdgeBits(2 - piecesOnFacet[1].rotation)))
								s.add(new Node(node.facetIndex + 1, orientedPiece));
						}
					}
				}
				break;
			case 3:
				//Next facet is F4
				//Match on E8
				for (int edgebits : GetMachingEdgeBits(pieces[piecesOnFacet[0].pieceIndex].EdgeBits(3))) {
					for (PieceEdge edge : mapEdges.get(edgebits)) {
						if (!pieces[edge.pieceIndex].used){
							OrientedPiece orientedPiece = new OrientedPiece(edge.pieceIndex, 1 - edge.orientation);
							//match on E2 and E6
							if (Match(
									pieces[orientedPiece.pieceIndex].EdgeBits(2 - orientedPiece.rotation),
									pieces[piecesOnFacet[3].pieceIndex].EdgeBits(3 - piecesOnFacet[3].rotation))
							 && Match(
									pieces[orientedPiece.pieceIndex].EdgeBits(0 - orientedPiece.rotation),
									pieces[piecesOnFacet[2].pieceIndex].EdgeBits(3 - piecesOnFacet[2].rotation)))
								s.add(new Node(node.facetIndex + 1, orientedPiece));
						}
					}
				}
				break;
			case 4:
				//Next facet is F5
				//Match on E7
				for (int edgebits : GetMachingEdgeBits(pieces[piecesOnFacet[4].pieceIndex].EdgeBits(3))) {
					for (PieceEdge edge : mapEdges.get(edgebits)) {
						if (!pieces[edge.pieceIndex].used){
							OrientedPiece orientedPiece = new OrientedPiece(edge.pieceIndex, 1 - edge.orientation);
							//match on E0, E1 and E5
							if (Match(
									pieces[orientedPiece.pieceIndex].EdgeBits(3 - orientedPiece.rotation),
									pieces[piecesOnFacet[1].pieceIndex].EdgeBits(1 - piecesOnFacet[1].rotation))
							 && Match(
									pieces[orientedPiece.pieceIndex].EdgeBits(2 - orientedPiece.rotation),
									pieces[piecesOnFacet[3].pieceIndex].EdgeBits(2 - piecesOnFacet[3].rotation))
							 && Match(
									pieces[orientedPiece.pieceIndex].EdgeBits(0 - orientedPiece.rotation),
									pieces[piecesOnFacet[2].pieceIndex].EdgeBits(0 - piecesOnFacet[2].rotation)))
								s.add(new Node(node.facetIndex + 1, orientedPiece));
						}
					}
				}
				break;
			case 5:
				//we have a solution
				PuzzleSolution solution = new PuzzleSolution();
				for (int index = 0; index < piecesOnFacet.length; index++) {
					solution.orientedPieces[index] = new OrientedPiece(piecesOnFacet[index].pieceIndex, piecesOnFacet[index].rotation);
				}
				solutions.add(solution);
				
				break;
			}
			
		}
		
		return solutions;
	}
	
	private boolean Match(int edgebits1, int edgebits2) {
		
		edgebits2 = ReverseBits(edgebits2);
		
		if ((edgebits1 & 0b0110) + (edgebits2 & 0b0110) != 0b0110)
			return false;
		
		if ((edgebits1 >> 3) + (edgebits2 >> 3) == 2)
			return false;
		
		if ((edgebits2 & 1) + (edgebits1 & 1) == 2)
			return false;
		
		return true;
	}
	
	private int ReverseBits(int edgebits) {
		return (((edgebits & 1) << 3) + ((edgebits & 0b10) << 1) + ((edgebits & 0b100) >> 1) + ((edgebits & 0b1000) >> 3));
	}
	
	private List<Integer> GetMachingEdgeBits(int edgebits) {
		
		List<Integer> list = new ArrayList<Integer>();
		int matchingEdgeBits = 0b1111 - ReverseBits(edgebits);
		list.add(matchingEdgeBits);
		
		if ((matchingEdgeBits & 1) == 1)
			list.add(matchingEdgeBits & 0b1110);
		
		if ((matchingEdgeBits & 0b1000) == 0b1000)
			list.add(matchingEdgeBits & 0b0111);
		
		if (((matchingEdgeBits & 1) == 1) && ((matchingEdgeBits & 0b1000) == 0b1000))
			list.add(matchingEdgeBits & 0b0110);
		
		return list;
	}
}

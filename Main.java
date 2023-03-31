import java.util.Date;
import it.unisa.dia.gas.jpbc.*;

public class Main {
	public static void main(String[] args)  {
        EncodeKeyword encodeKeyword = new EncodeKeyword();
        double avg = 0.0;
        // Set the plaintext and query of the same length to be encrypted
        // The length of the plaintext and query should be equivalent to the number of rows of matrix b as described in Setup phase.
        String strPlaintext = "aaaaaaaaa";
        String strQuery = "a********";
        
        
		// Setup phase------------------------------------------------------------------------------
		//int[][] b = { {10, 15, 7,}, {20, 57, 30,}, {37, 56, 85,} };
        //int[][] b = { {10, 15, 7, 3}, {20, 57, 30, 4}, {37, 56, 85, 6}, {1, 1, 1, 1} };
        //int[][] b = { {10, 15, 7, 3, 1}, {20, 57, 30, 4, 1}, {37, 56, 85, 6, 1}, {6, 7, 8, 34, 1}, {3, 4, 6, 2, 1} };
        //int[][] b = { {10, 15, 7, 3, 1, 3}, {20, 57, 30, 4, 1, 1}, {37, 56, 85, 6, 1, 6}, {6, 7, 8, 34, 1, 4}, {3, 4, 6, 2, 1, 3}, {3, 4, 6, 2, 1, 45} };
        //int[][] b = { {10, 15, 7, 3, 1, 3, 1}, {20, 57, 30, 4, 1, 1, 6}, {37, 56, 85, 6, 1, 6, 3}, {6, 7, 8, 34, 1, 4, 5}, {3, 4, 6, 2, 1, 3, 33}, {3, 4, 6, 2, 1, 45, 1}, {3, 4, 6, 2, 1, 45, 4} };
        //int[][] b = { {10, 15, 7, 3, 1, 3, 1, 5}, {20, 57, 30, 4, 1, 1, 6, 7}, {37, 56, 85, 6, 1, 6, 3, 1}, {6, 7, 8, 34, 1, 4, 5, 4}, {3, 4, 6, 2, 1, 3, 33, 5}, {3, 4, 6, 2, 1, 45, 1, 6}, {3, 4, 6, 2, 1, 45, 4, 8}, {3, 4, 6, 2, 1, 45, 4, 2} };
        int[][] b = { {10, 15, 7, 3, 1, 3, 1, 5, 3}, {20, 57, 30, 4, 1, 1, 6, 7, 1}, {37, 56, 85, 6, 1, 6, 3, 1, 3}, {6, 7, 8, 34, 1, 4, 5, 4, 4}, {3, 4, 6, 2, 1, 3, 33, 5, 2}, {3, 4, 6, 2, 1, 45, 1, 6, 1}, {3, 4, 6, 2, 1, 45, 4, 8, 3}, {3, 4, 6, 2, 1, 45, 4, 2, 6}, {3, 4, 6, 2, 1, 45, 4, 2, 2} };
        int vectorLength = b.length;
		
		Element[][] B = new Element[b.length][b.length];
		
		for (int i = 0; i < B.length; i++) {
			for (int j = 0; j < B.length; j++) {
				B[i][j] = Setup.Zr.newRandomElement();
				B[i][j].set(b[i][j]);
			}
		}
		
		
				
		Element det = MatrixOps.matrixDeterminant(B);
		
		Element[][] BStar = new Element[b.length][b.length];
		BStar = Setup.setBstar(B, det);
		
		
		Element[][] g2_kappa_BStar = new Element[b.length][b.length];
		g2_kappa_BStar = Setup.setG2KappaBstar(BStar, Setup.kappa);
		
		
		
		// Encrypt phase----------------------------------------------------------------------------
		Element[] plaintext = new Element[b.length];
		for (int i = 0; i < b.length; i++) {
			plaintext[i] = Setup.Zr.newRandomElement();
		}
		
		plaintext = encodeKeyword.encodeKeyword(strPlaintext, vectorLength);
		
		Encrypt enc = new Encrypt();
		
		Element C1 = Setup.G2.newRandomElement();
		C1 = enc.setC1();
		Element[] C2 = new Element[b.length];
		C2 = enc.setC2(plaintext, g2_kappa_BStar);	
	
		
		// Trapdoor phase----------------------------------------------------------------------------
		Element[] query = new Element[b.length];
		for (int i = 0; i < b.length; i++) {
			query[i] = Setup.Zr.newRandomElement();
		}

		query = encodeKeyword.encodeKeyword(strQuery, vectorLength);
		
		
		
		KeyGen trapdoor = new KeyGen();
		
		Element K1 = Setup.G1.newRandomElement();
		K1 = trapdoor.setK1(det);
		Element[] K2 = new Element[b.length];
		K2 = trapdoor.setK2(query, B);	
		

		
		
		// Test phase--------------------------------------------------------------------------------
	    
		System.out.println("Test result: " + Test.decrypt(C1, C2, K1, K2));
	    
		
	}
}

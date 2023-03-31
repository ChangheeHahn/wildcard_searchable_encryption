import it.unisa.dia.gas.jpbc.*;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;


public class Setup {
	static Pairing pairing = PairingFactory.getPairing("d224.properties");
	static Field G1 = pairing.getG1();
	static Field G2 = pairing.getG2();
	static Field GT = pairing.getGT();
	static Field Zr = pairing.getZr();
	static Element g1 = G1.newRandomElement();
	static Element g2 = G2.newRandomElement();
	static Element t = GT.newRandomElement();
	static Element kappa = Setup.Zr.newRandomElement();
	
	public static Element[][] setBstar (Element[][] B, Element det) {
		Element[][] result = new Element[B.length][B.length];
		result = MatrixOps.inverse(B);
		for (int i = 0; i < B.length; i++) {
			for(int j = 0; j < B.length; j++) {
				result[i][j].mulZn(det);
			}
		}
		return result;
	}
	
	public static Element[][] setG2KappaBstar (Element[][] BStar, Element kappa) {
		Element[][] result = new Element[BStar.length][BStar.length];
		for (int i = 0; i < BStar.length; i++) {
			for(int j = 0; j < BStar.length; j++) {
				result[i][j] = G2.newRandomElement();
				result[i][j].set(g2).powZn( Zr.newOneElement().mulZn(kappa).mulZn(BStar[i][j]));
			}
		}
		return result;
	}
}

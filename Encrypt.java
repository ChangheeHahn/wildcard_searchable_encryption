import it.unisa.dia.gas.jpbc.*;

public class Encrypt {
	static Element alpha = Setup.Zr.newRandomElement();
	
	public Element setC1 () {
		Element C1 = Setup.g2;
		C1.powZn(alpha);
		return C1;
	}
	
	public Element[] setC2(Element[] y, Element[][] g2_kappa_BStar) {
		Element[] C2 = new Element[y.length];
		
		for (int i = 0; i < y.length; i++) 
			C2[i] = Setup.G2.newOneElement();
		
		
		for (int i = 0; i < y.length; i++) {
			for (int j = 0; j < y.length; j++) {
				C2[i].mul( g2_kappa_BStar[i][j].duplicate().powZn( y[j] ));
			}
		}
		
		for (int i = 0; i < y.length; i++) 
			C2[i].powZn(alpha);
			
			
		return C2;
	}
}

import it.unisa.dia.gas.jpbc.Element;

public class Test {
	
	public static boolean decrypt (Element C1, Element[] C2, Element K1, Element[] K2) {
		Element d1 = Setup.GT.newElement();
		Element d2 = Setup.GT.newOneElement();
		
		d1.set(Setup.pairing.pairing(K1, C1));
		
		for (int i = 0; i < C2.length; i++) {
			d2.mul(Setup.pairing.pairing(K2[i], C2[i]));	
			
		}
		
		
		if ( d2.isEqual(d1) ) {
			return true;
		}
		else {
			return false;
		}
		
		
	}
}

import it.unisa.dia.gas.jpbc.*;

public class KeyGen {
	static Element beta = Setup.Zr.newRandomElement();
	
	public Element setK1 (Element det) {
		Element K1 = Setup.G1.newRandomElement();
		K1.set(Setup.g1);
		K1.powZn(Setup.Zr.newOneElement().mulZn(beta).mulZn(det).mulZn(Setup.kappa));
		return K1;
	}
	
	public Element[] setK2(Element[] m,  Element[][] B) {
		Element[] K2 = new Element[m.length];
		Element[] tmp = new Element[m.length];
		Element[] wbar =  new Element[m.length];
		Element gamma = Setup.Zr.newOneElement();
		for (int i = 0; i < m.length; i++) {
			if (i == 0)
				gamma.mulZn( (Setup.Zr.newOneElement().mulZn(m[i])).powZn(Setup.Zr.newElement(2)) );
			else
				gamma.add( (Setup.Zr.newOneElement().mulZn(m[i])).powZn(Setup.Zr.newElement(2)) );
		}
		
		gamma.invert();
		
		for (int i = 0; i < m.length; i++) {
			wbar[i] = Setup.Zr.newRandomElement();
			wbar[i].set(Setup.Zr.newOneElement().mulZn(m[i]).mulZn(gamma) );
		}
		
		for (int i = 0; i < m.length; i++) {
			tmp[i] = Setup.Zr.newRandomElement();
		}
		tmp = MatrixOps.multiplyArrayMatrix(wbar, B);
		
		for (int i = 0; i < m.length; i++) {
			K2[i] = Setup.G1.newRandomElement();
			K2[i].set( Setup.g1).powZn(Setup.Zr.newOneElement().mulZn(tmp[i]).mulZn(beta)) ;
		}
		return K2;
	}
}

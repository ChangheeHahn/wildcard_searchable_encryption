import it.unisa.dia.gas.jpbc.Element;

public class EncodeKeyword {
	public Element[] encodeKeyword(String keyword, int vectorLength) {
		Element[] result = new Element[vectorLength];
		for (int i = 0; i < vectorLength; i++) 
			result[i] = Setup.Zr.newRandomElement();
		
		if (keyword.length() > vectorLength) {
			System.out.println("Encode fails. Keyword length should be equal or smaller than vector length.");
			return result;
		}
		else {
			for (int i = 0; i < vectorLength; i++) {
				if (keyword.charAt(i) == '*')
					result[i].set(Setup.Zr.newZeroElement());
				else
					result[i].set(keyword.charAt(i));
			}
			
			return result;
		}

	}
}

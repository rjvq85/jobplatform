package pt.criticalsoftware.domain.utils;

public class GenerateReferenceValue {

	public static String genReference(String letter, int id) {
		String finale = "";
		for (int i = 1; i < 1000000; i = i * 10) {
			finale = finale + id % 10;
			id = id / 10;
		}
		String genRef = new StringBuilder(finale).reverse().toString();
		return (letter + genRef);
	}

}

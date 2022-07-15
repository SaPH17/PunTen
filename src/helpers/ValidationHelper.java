package helpers;

public class ValidationHelper {
	public static boolean containAlphanum(String s) {
		boolean alpha = false;
		boolean num = false;
		
		for(int i = 0; i < s.length(); i++) {
			if(Character.isDigit(s.charAt(i))) {
				num = true;
			}
			
			if(Character.isLetter(s.charAt(i))) {
				alpha = true;
			}
		}
		
		return alpha && num;
	}
}

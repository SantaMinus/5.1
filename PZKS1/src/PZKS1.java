import java.util.regex.Pattern;

public class PZKS1 {
	public static void main(String[] args){
		GUI.paint();
	}
	
	static boolean compare(char c, char []arr){
		boolean bool=false;
		for(int i=0; i<arr.length; i++)
			if(c==arr[i]) bool=true;
		return bool;
	}
	
	static void validation(){
		String s = GUI.text.getText();
		System.out.println(s);
		Pattern p;
		p = Pattern.compile("[^a-z0-9\\+\\-\\*\\/\\(\\)\\.]", Pattern.CASE_INSENSITIVE);
		int openBr=0, closBr=0;
		BracketsValidator v = new BracketsValidator();
		
		
		if(!s.isEmpty()){
			if(p.matcher(s).find()){
				GUI.result.setText("Forbidden character");
			}
			if(s.startsWith(")") || s.startsWith("*") || s.startsWith("/") || s.startsWith("+") || s.startsWith(".")){
				GUI.result.setText("Wrong character at the beginning");
				//return;
			}
			if(s.endsWith("(") || s.endsWith("*") || s.endsWith("/") || s.endsWith("+") || s.endsWith("-") || s.endsWith(".")) {
				GUI.result.setText("Wrong character at the end");
				//return;
			}
			
			//brackets number and comma test
			for(int i=0; i<s.length(); i++){
				if(s.charAt(i)=='(') openBr++;
				if(s.charAt(i)==')') closBr++;
				if(s.charAt(i)=='.'){
					for(int j=i+1; j<s.length()-3 || (compare(s.charAt(j), new char[]{'+', '-', '*', '/', '(', ')'})); j++){
						if(s.charAt(j)=='.'){
							GUI.result.setText("More than 1 comma in the number. Position "+(j+1));
							//return;
						}
					}
				}
			}
			
			//wrong character test
			for(int i=0; i<s.length()-1; i++){
				if(Character.isLetter(s.charAt(i))  && (s.charAt(i+1)=='.' || s.charAt(i+1)=='(')){
					GUI.result.setText("Wrong character after letter. Position "+(i+2));
					return;
				}
				if(Character.isDigit(s.charAt(i)) && (s.charAt(i+1)=='(' || Character.isLetter(s.charAt(i+1)))){
					GUI.result.setText("Wrong character after digit. Position "+(i+2));
					return;
				}
				if(compare(s.charAt(i), new char[]{'+', '-', '*', '/'}) && compare(s.charAt(i+1), new char[]{')', '+', '-', '*', '/', '.'})){
					GUI.result.setText("Wrong character after operation. Position "+(i+2));
					return;
				}
				if(s.charAt(i)=='(' && compare(s.charAt(i+1), new char[]{'+', '*', '/', ')', '.'})){
					GUI.result.setText("Wrong character after opening bracket. Position "+(i+2));
					return;
				}
				if(s.charAt(i)==')' && (compare(s.charAt(i+1), new char[]{'(', '.'}) || Character.isDigit(s.charAt(i+1)) || Character.isLetter(s.charAt(i+1)))){
					GUI.result.setText("Wrong character after closing bracket. Position "+(i+2));
					return;
				}
				if(s.charAt(i)=='.' && (compare(s.charAt(i+1), new char[]{'+', '-', '*', '/', '(', ')', '.'}) || Character.isLetter(s.charAt(i+1)))){
					GUI.result.setText("Wrong character after comma. Position "+(i+2));
					return;
				}
				GUI.result.setText("The expression is valid");
			}

			if(openBr!=closBr){
				GUI.result.setText("Unequal number of opening and closing brackets");
			}
			
			boolean correct = v.validate(s);
			GUI.result.setText(GUI.result.getText() + "\nBrackets are used " + (correct ? "" : "not ") + "right");
		}
	}
}

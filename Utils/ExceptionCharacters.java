package Utils;

/**
 * for windows use only
 * @author DH
 *
 */
public enum ExceptionCharacters {
	backSlash ("005c"),
	slash ("002f"),
	colon("003a"),
	asterisk("002a"),
	questionMark("003f"),
	quote("0022"),
	lessThan("003c"),
	greaterThan("003e"),
	pipe("007c");
	
	private String value;
	
	ExceptionCharacters(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
	
	public static boolean contains(char ch){
		String s = OsuUtils.characterToUnicode(ch);
		for (ExceptionCharacters ec : ExceptionCharacters.values()){
			if (ec.getValue().equalsIgnoreCase(s)){
				return true;
			}
		}
		return false;
	}
	
}

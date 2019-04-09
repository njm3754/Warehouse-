
public class Security {
	private static Security instance;
	private Security() {};
	public boolean verifyManager(String check) {
		if(check == "manager")
			return true;
		return false;
	}
	
	public boolean verifyClerk(String check) {
		if(check == "clerk")
			return true;
		return false;
	}
	public static Security instance() {
		if(instance == null) {
			instance= new Security(); 
		}
		return instance;
	} 
}

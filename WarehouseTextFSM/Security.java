
public class Security {
	
	
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
}

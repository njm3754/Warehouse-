import java.util.*;
import java.text.*;
import java.io.*;
public class WarehouseContext {
	
	private int currentState;
	private static Warehouse warehouse;
	private static WarehouseContext instance;
	private int currentUser;
	private String userID;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	public static final int IsManager = 0;
	public static final int IsClerk = 1;
	public static final int IsClient = 2;

	//#region State Exit Codes
	public static final int LOGOUT = 0;
	public static final int BACK = 1;
	public static final int CLIENT_MENU = 2;
	public static final int SALESCLERK_MENU = 3;
	public static final int MANAGER_MENU = 4;
	//#endregion State Exit Codes

	public static final int ERROR_CODE = -1;

	private WarehouseState[] states;
	private int[][] nextState;

	public String getToken(String prompt) {
		do {
			try {
				System.out.println(prompt);
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
				if (tokenizer.hasMoreTokens()) {
					return tokenizer.nextToken();
				}
			} catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);
	}
	
	private boolean yesOrNo(String prompt) {
		String more = getToken(prompt + " (Y|y)[es] or anything else for no");
		if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
			return false;
		}
		return true;
	}

	private void retrieve() {
		try {
			Warehouse tempWarehouse = Warehouse.retrieve();
			if (tempWarehouse != null) {
				System.out.println(" The warehouse has been successfully retrieved from the file WarehouseData \n" );
				warehouse = tempWarehouse;
			} else {
				System.out.println("File doesnt exist; creating new warehouse" );
				warehouse = Warehouse.instance();
			}
		} catch(Exception cnfe) {
			cnfe.printStackTrace();
		}
	}

	public void setLogin(int code)
	{currentUser = code;}

	public void setUser(String uID)
	{ userID = uID;}

	public int getLogin()
	{ return currentUser;}

	public String getUser()
	{ return userID;}

	private WarehouseContext() { //constructor
		configureWarehouse();
	}

	//Set up the FSM and transition table;
	private void configureFSM()
	{
		//#region States
		states = new WarehouseState[4];
		states[0] = ManagerState.instance();
		states[1] = ManagerState.instance();
		states[2] = ManagerState.instance();
		states[3] = ManagerState.instance();

		/*states[1] = ClerkState.instance();
		states[2] = ClientState.instance(); 
		states[3]=  Loginstate.instance();*/

		//#endregion States

		//#region Transition Table
		nextState = new int[4][5];

		//#region ManagerState
		nextState[0][LOGOUT] = ERROR_CODE;
		nextState[0][BACK] = 3;
		nextState[0][CLIENT_MENU] = ERROR_CODE;
		nextState[0][SALESCLERK_MENU] = 1 ;
		nextState[0][MANAGER_MENU] = ERROR_CODE; //Current state is ManagerState
		//#endregion ManagerState

		//#region ClerkState
		nextState[1][LOGOUT] = 0;
		nextState[1][BACK] = 3;
		nextState[1][CLIENT_MENU] = 2;
		nextState[1][SALESCLERK_MENU] = ERROR_CODE;
		nextState[1][MANAGER_MENU] = ERROR_CODE;
		//#endregion ClerkState

		//#region ClientState
		nextState[2][LOGOUT] = 1;
		nextState[2][BACK] = 3;
		nextState[2][CLIENT_MENU] = ERROR_CODE;
		nextState[2][SALESCLERK_MENU] = ERROR_CODE;
		nextState[2][MANAGER_MENU] = ERROR_CODE;
		//#endregion ClientState

		//#region LoginState
		nextState[3][LOGOUT] = ERROR_CODE;
		nextState[3][BACK] = ERROR_CODE;
		nextState[3][CLIENT_MENU] = 2;
		nextState[3][SALESCLERK_MENU] = 1;
		nextState[3][MANAGER_MENU] = 0;
		//#endregion LoginState

		//#endregion Transition Table

		currentState = 3; //LoginState
	}

	private void configureWarehouse()
	{
		if (yesOrNo("Look for saved data and  use it?")) {
			retrieve();
		}
		else {
			warehouse = Warehouse.instance();
		}
	}
	
	public void changeState(int transition)
	{
		currentState = nextState[currentState][transition];
		if (currentState == ERROR_CODE) 
		{
			System.out.println("Error has occurred");
			terminate();
		}
		states[currentState].run();
	}

	private void terminate()
	{
		if (yesOrNo("Save data?")) {
			if (Warehouse.save()) {
				System.out.println(" The warehouse has been successfully saved in the file WarehouseData \n" );
			}
			else {
				System.out.println(" There has been an error in saving \n" );
			}
		}
		System.out.println(" Goodbye \n ");
		System.exit(0); //Java function for exiting program
	}

	public static WarehouseContext instance() {
		if (instance == null) {
			instance = new WarehouseContext();
		}
		return instance;
	}

	public void process(){
		states[currentState].run();
	}
	
	public static void main (String[] args){
		WarehouseContext context = WarehouseContext.instance();
		context.configureFSM();
		context.process();
	}
}

package fitnesseMain;

import java.util.HashMap;
import java.util.Map;



public class HCHContext {
	public static Map<String,String> nameAndHelp = new HashMap<String,String>();

	
	private HCHContext() {
	}

	private static volatile HCHContext klc = null;

	public static HCHContext getInstance() {
		synchronized (HCHContext.class) {
			if (klc == null) {
				klc = new HCHContext();
			}
		}
		return klc;
	}

}

package riskyken.utilities.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.logging.log4j.Level;

import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.utils.ModLogger;

public class UpdateCheck implements Runnable {

	public static boolean shouldCheckForUpdates = true;
	public static boolean needsUpdate = false;
	//public static final String UPDATE_URL = "http://ktech.no-ip.info/mods/risky-kens-utilities/update.txt";
	public static final String UPDATE_URL = "http://bit.ly/1hItnfS";
	public static boolean showUpdateInfo = false;
	
	public static String remoteVersion;
	public static String remoteVersionInfo;
	
	public static void checkForUpdates() {
		if (!shouldCheckForUpdates) { return; }
		
		(new Thread(new UpdateCheck())).start();
	}

	@Override
	public void run() {
		ModLogger.log("Starting Update Check");
		try {
			String location = UPDATE_URL;
			HttpURLConnection conn = null;
			while (location != null && !location.isEmpty()) {
				URL url = new URL(location);

				if (conn != null)
					conn.disconnect();

				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows; U; Windows NT 6.0; ru; rv:1.9.0.11) Gecko/2009060215 Firefox/3.0.11 (.NET CLR 3.5.30729)");
				conn.connect();
				location = conn.getHeaderField("Location");
			}

			if (conn == null)
				throw new NullPointerException();

			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line;

			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split("@");
				if (tokens[0] != null && !tokens[0].equals(""))
				{
					remoteVersion = tokens[0];
					remoteVersionInfo = tokens[1];
					break;
				}
			}
			conn.disconnect();
			reader.close();

			if (versionCompare(LibModInfo.VERSION, remoteVersion) < 0) {
				showUpdateInfo = true;
				
				ModLogger.log("Update needed. New version " + remoteVersion + " your version " + LibModInfo.VERSION);
			}
			else {
				showUpdateInfo = false;
				ModLogger.log("Is up to date");
			}
			
		} catch (Exception e) {
			ModLogger.log(Level.WARN, "Unable to read from remote version authority.");
			ModLogger.log(Level.WARN, e.toString());
			showUpdateInfo = false;
		}
		
	}
	
	private int versionCompare(String str1, String str2)
	{
	    String[] vals1 = str1.split("\\.");
	    String[] vals2 = str2.split("\\.");
	    int i = 0;
	    // set index to first non-equal ordinal or length of shortest version string
	    while (i < vals1.length && i < vals2.length && vals1[i].equals(vals2[i])) 
	    {
	      i++;
	    }
	    // compare first non-equal ordinal number
	    if (i < vals1.length && i < vals2.length) 
	    {
	        int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
	        return Integer.signum(diff);
	    }
	    // the strings are equal or one string is a substring of the other
	    // e.g. "1.2.3" = "1.2.3" or "1.2.3" < "1.2.3.4"
	    else
	    {
	        return Integer.signum(vals1.length - vals2.length);
	    }
	}
	
}

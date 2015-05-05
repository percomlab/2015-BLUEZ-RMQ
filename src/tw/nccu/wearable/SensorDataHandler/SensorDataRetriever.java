package tw.nccu.wearable.SensorDataHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class SensorDataRetriever {
	public SensorDataRetriever(){
	}
	public void waitForInput(String macAddr) throws IOException, InterruptedException{
		String cmd = "gatttool -b " + macAddr + " -I -t random";
		Process pl = Runtime.getRuntime().exec(cmd);
		pl = Runtime.getRuntime().exec(cmd);
		InputStream in = pl.getInputStream();
		OutputStream out = pl.getOutputStream();
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		char[] chars = new char[1024];
		int readlen = -1;
		String status = "disconnect";
		String cmdline1 = "[" + macAddr + "][LE]> ";
		String cmdline2 = "[" + macAddr + "][LE]>";
		while ((readlen = br.read(chars, 0, chars.length)) != -1) {
			String str = new String(chars, 0, readlen);
			// commandline output
			if (str.contains("Notification handle = 0x0010")){
				System.out.println(str.substring(40));
			}
			else{
				System.out.println(str);
			}
			
			if (cmdline1.equals(str)) {
				status = "connectting";
				out.write("connect\n".getBytes());
				out.flush();
				Thread.sleep(1000);
			} else if (status.equals("connectting")
					&& str.indexOf(cmdline2) >= 0) {
				status = "connected";
				out.write("char-write-req 0x0011 0100 -listen\n".getBytes());
				out.flush();
				Thread.sleep(1000);
			} 
		}		
	}
}

package edu.jhu.cs.oose.fall2012.group14.ihungry.internet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * The implementation of the internetUtil
 * @author lyang
 *
 */
public class InternetUtilImpl implements InternetUtil {
	Socket socket; // socket
	DataOutputStream out;
 	DataInputStream in;

 	private static String startSymbol = "[_!_<Start>_!_]";
 	private static String stopSymbol = "[_!_<Stop>_!_]";
 	
	@Override
	public void setSocket(Socket so) throws IOException {
		this.socket = so;
		out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		//dr = new BufferedReader(new InputStreamReader(in));
	}

	@Override
	public long receiveFile(String filename) throws Exception {
		int buffersize = 8092;
		FileOutputStream fos = new FileOutputStream(new File(filename));
		int length = 0;
		int totalsize = 0;
		
		totalsize = Integer.parseInt(this.receiveMessage());
		System.out.println(totalsize);
		
		
		byte[] inputByte = new byte[buffersize];
		int t = 0;
		
		while(t < totalsize){
			int readsize;
			readsize = totalsize - t > buffersize ? buffersize : totalsize - t ;
			length = in.read(inputByte, 0, readsize);
	        fos.write(inputByte, 0, length);
	        t += length;
		}
		fos.flush();
		fos.close();
		return totalsize;

	}

	@Override
	public long sendFile(String filename) throws Exception {
		File file = new File(filename);
		FileInputStream fis = new FileInputStream(file);
		int length;
		int totalsize = (int) file.length();
		//byte[] sendBytes = new byte[1024];
		byte[] sendbyte = new byte[8092];
		this.sendMsg("" + totalsize);
		
		while ((length = fis.read(sendbyte)) > 0) {
         	out.write(sendbyte, 0, length);
        }
		out.flush();
		
		/*while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0) {
         	out.write(sendBytes, 0, length);
         	out.flush();
        }*/
		fis.close();
		return totalsize;
		
	}

	@Override
	public String receiveMessage() throws Exception {
		String msg = "";
		String tm = "";
		while(!tm.contains(startSymbol)){
			tm = in.readUTF();
			//System.out.println(tm);
		}
		
		while(!tm.contains(stopSymbol)){
			tm = in.readUTF();//dr.readLine();
			if(!tm.contains(stopSymbol))
				msg += tm;
		}
		return msg;
	}

	@Override
	public void sendMsg(String msg) throws Exception {
		out.writeUTF(startSymbol);
		out.flush();
		out.writeUTF(msg);
		out.flush();
		out.writeUTF(stopSymbol);
		out.flush();
	}

}

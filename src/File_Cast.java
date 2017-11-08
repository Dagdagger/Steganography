import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import java.io.*;
import javax.swing.JOptionPane;


public class File_Cast extends ReceiverAdapter {
	

	   JChannel channel;
	   
	  protected String filename;

	    public File_Cast(String name, String filename) throws Exception{
	    	this.filename = filename + ".png";
	        channel = new JChannel();
	        channel.setReceiver(this);
	        channel.connect("FileCluster");
//	        channel.getState(null, 10000);
	        event();
	        channel.close();
	    }


	    private void event() throws Exception {
	        	JOptionPane.showMessageDialog(null, "You're about send a file to the server", "Alert!", JOptionPane.INFORMATION_MESSAGE);
	            sendFile();
	    }
		
		protected void sendFile() throws Exception {
		    FileInputStream in=new FileInputStream(filename);
		    try {
		        for(;;) {
		            byte[] buf=new byte[8096];
		            int bytes=in.read(buf);
		            if(bytes == -1)
		                break;
		            Message msg=new Message(null, buf);
		            channel.send(msg);
		        }
		    }
		    catch(Exception e) {
		        e.printStackTrace();
		    }
	
		}


		public void receive(Message msg) {
			
			System.out.println("Hello World");
		    
			try {
			byte[] buf=msg.getRawBuffer();
		    FileOutputStream fos = new FileOutputStream("c:/hello/" + filename);
		    fos.write(buf);
		    fos.close();
		    
		    }
		    catch(Throwable t) {
		        System.err.println(t);
		    }
		}

	}
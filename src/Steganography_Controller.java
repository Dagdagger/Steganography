import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


public class Steganography_Controller
{

	//Program Variables
	private Steganography_View	view;
	private Steganography		model;
	
	//Panel Displays
	private JPanel		decode_panel;
	private JPanel		encode_panel;
	//Panel Variables
	private JTextArea 	input;
	private JButton		encodeButton,decodeButton;
	private JLabel		image_input;
	//Menu Variables
	private JMenuItem 	encode;
	private JMenuItem 	decode;
	private JMenuItem	send;
	private JMenuItem	receive;
	private JMenuItem 	exit;
	
	//action event classes
	private Encode			enc;
	private Decode			dec;
	private Send			sen;
	private EncodeButton	encButton;
	private DecodeButton	decButton;
	
	//decode variable
	private String			stat_path = "";
	private String			stat_name = "";
	
	/*
	 *Constructor to initialize view, model and environment variables
	 *@param aView  A GUI class, to be saved as view
	 *@param aModel A model class, to be saved as model
	 */
	public Steganography_Controller(Steganography_View aView, Steganography aModel) throws IOException
	{
		
		//program variables
		view  = aView;
		model = aModel;
		
		//assign View Variables
		//2 views
		encode_panel	= view.getTextPanel();
		decode_panel	= view.getImagePanel();
		//2 data options
		input			= view.getText();
		image_input		= view.getImageInput();
		//2 buttons
		encodeButton	= view.getEButton();
		decodeButton	= view.getDButton();
		//menu
		encode			= view.getEncode();
		decode			= view.getDecode();
		send			= view.getSend();
		receive			= view.getReceive();
		exit			= view.getExit();
		
		//assign action events
		enc = new Encode();
		encode.addActionListener(enc);
		dec = new Decode();
		decode.addActionListener(dec);
		sen = new Send();
		send.addActionListener(sen);
		
		
		exit.addActionListener(new Exit());
		encButton = new EncodeButton();
		
		encodeButton.addActionListener(encButton);
		decButton = new DecodeButton();
		decodeButton.addActionListener(decButton);
		
		//encode view as default
		encode_view();
	}
	
	/*
	 *Updates the single panel to display the Encode View.
	 */
	private void encode_view()
	{
		update();
		view.setContentPane(encode_panel);
		view.setVisible(true);
	}
	
	/*
	 *Updates the single panel to display the Decode View.
	 */
	private void decode_view()
	{
		update();
		view.setContentPane(decode_panel);
		view.setVisible(true);
	}
	
	/*
	 *Encode Class - handles the Encode menu item
	 */
	private class Encode implements ActionListener
	{
		/*
		 *handles the click event
		 *@param e The ActionEvent Object
		 */
			/*
			 *handles the click event
			 *@param e The ActionEvent Object
			 */
			public void actionPerformed(ActionEvent e)
			{
				if (input.getText().length() <= 0){
					JOptionPane.showMessageDialog(view,"Please insert a message to be decoded",
							"Error", JOptionPane.INFORMATION_MESSAGE);
					return;
					
				}
				//start path of displayed File Chooser
				
			
				
				JFileChooser chooser = new JFileChooser("./");
				

		
				chooser.setAcceptAllFileFilterUsed(false);
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				chooser.setFileFilter(new Image_Filter());
				int returnVal = chooser.showOpenDialog(view);
				if (returnVal == JFileChooser.APPROVE_OPTION){
					File directory = chooser.getSelectedFile();
					String password = JOptionPane.showInputDialog("Please enter a password");
					try{
						String text = input.getText();
						String ext  = Image_Filter.getExtension(directory);
						String name = directory.getName();
						String path = directory.getPath();
						path = path.substring(0,path.length()-name.length()-1);
						name = name.substring(0, name.length()-4);
						
						String stegan = JOptionPane.showInputDialog(view,
										"Enter output file name:", "File name",
										JOptionPane.PLAIN_MESSAGE);
						
						if(model.encode(path,name,ext,stegan,text,password))
						{
							JOptionPane.showMessageDialog(view, "The Image was encoded Successfully!", 
								"Success!", JOptionPane.INFORMATION_MESSAGE);
							
						}
						else
						{
							JOptionPane.showMessageDialog(view, "The Image could not be encoded!", 
								"Error!", JOptionPane.INFORMATION_MESSAGE);
						}
						//display the new image
						decode_view();
						image_input.setIcon(new ImageIcon(ImageIO.read(new File(path + "/" + stegan + ".png"))));
						stat_path = path;
						stat_name = stegan;		
					}
					catch(Exception except) {
					//message if opening fails
					JOptionPane.showMessageDialog(view, "The File cannot be opened!", 
						"Error!", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
			
		}
	
	
	private class Send implements ActionListener{
		
	

		public void actionPerformed(ActionEvent e) {
		
			 if(input.getText().length() <= 0){
				JOptionPane.showMessageDialog(view, "Please encode a message!", 
					"Error!", JOptionPane.INFORMATION_MESSAGE);
					return;
			 }
			
			
			JFileChooser chooser = new JFileChooser("./");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setFileFilter(new Image_Filter());
			int returnVal = chooser.showOpenDialog(view);
			if (returnVal == JFileChooser.APPROVE_OPTION){
				File directory = chooser.getSelectedFile();
				String password = JOptionPane.showInputDialog("Please enter a password");
				try{
					String text = input.getText();
					String ext  = Image_Filter.getExtension(directory);
					String name = directory.getName();
					String path = directory.getPath();
					path = path.substring(0,path.length()-name.length()-1);
					name = name.substring(0, name.length()-4);
				
					
					String stegan = JOptionPane.showInputDialog(view,
									"Enter output file name:", "File name",
									JOptionPane.PLAIN_MESSAGE);
					
					if(model.encode(path,name,ext,stegan,text,password))
					{
						JOptionPane.showMessageDialog(view, "The Image was encoded Successfully!", 
							"Success!", JOptionPane.INFORMATION_MESSAGE);
					}
					
					else
					{
						JOptionPane.showMessageDialog(view, "The Image could not be encoded!", 
							"Error!", JOptionPane.INFORMATION_MESSAGE);
					}
				
				decode_view();
				image_input.setIcon(new ImageIcon(ImageIO.read(new File(path + "/" + stegan + ".png"))));
				stat_path = path;
				stat_name = stegan;
				}
				catch(Exception except) {
					//message if opening fails
					JOptionPane.showMessageDialog(view, "The File cannot be opened!", 
						"Error!", JOptionPane.INFORMATION_MESSAGE);
					}
				String Uname= JOptionPane.showInputDialog("Please enter your name");
				
			try {
				new File_Cast(Uname, stat_name);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			}
		}
	}
			
	
	
	/*
	 *Decode Class - handles the Decode menu item
	 */
	private class Decode implements ActionListener
	{
		/*
		 *handles the click event
		 *@param e The ActionEvent Object
		 */
		public void actionPerformed(ActionEvent e)
		{
			decode_view(); //show the decode view
			
			//start path of displayed File Chooser
			JFileChooser chooser = new JFileChooser("./");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setFileFilter(new Image_Filter());
			int returnVal = chooser.showOpenDialog(view);
			if (returnVal == JFileChooser.APPROVE_OPTION){
				File directory = chooser.getSelectedFile();
				try{
					String image = directory.getPath();
					stat_name = directory.getName();
					stat_path = directory.getPath();
					stat_path = stat_path.substring(0,stat_path.length()-stat_name.length()-1);
					stat_name = stat_name.substring(0, stat_name.length()-4);
					image_input.setIcon(new ImageIcon(ImageIO.read(new File(image))));
				}	
				catch(Exception except) {
				//message if opening fails
				JOptionPane.showMessageDialog(view, "The File cannot be opened!", 
					"Error!", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}
	
	/*
	 *Exit Class - handles the Exit menu item
	 */
	private class Exit implements ActionListener
	{
		/*
		 *handles the click event
		 *@param e The ActionEvent Object
		 */
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0); //exit the program
		}
	}
	
	/*
	 *Encode Button Class - handles the Encode Button item
	 */
	private class EncodeButton implements ActionListener
	{
		/*
		 *handles the click event
		 *@param e The ActionEvent Object
		 */
		public void actionPerformed(ActionEvent e)
		{
			if (input.getText().length() <= 0){
				JOptionPane.showMessageDialog(view,"Please insert a message to be decoded",
						"Error", JOptionPane.INFORMATION_MESSAGE);
				return;
				
			}
			//start path of displayed File Chooser
			JFileChooser chooser = new JFileChooser("./");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setFileFilter(new Image_Filter());
			int returnVal = chooser.showOpenDialog(view);
			if (returnVal == JFileChooser.APPROVE_OPTION){
				File directory = chooser.getSelectedFile();
				String password = JOptionPane.showInputDialog("Please enter a password");
				try{
					String text = input.getText();
					String ext  = Image_Filter.getExtension(directory);
					String name = directory.getName();
					String path = directory.getPath();
					path = path.substring(0,path.length()-name.length()-1);
					name = name.substring(0, name.length()-4);
					
					String stegan = JOptionPane.showInputDialog(view,
									"Enter output file name:", "File name",
									JOptionPane.PLAIN_MESSAGE);
					
					if(model.encode(path,name,ext,stegan,text,password))
					{
						JOptionPane.showMessageDialog(view, "The Image was encoded Successfully!", 
							"Success!", JOptionPane.INFORMATION_MESSAGE);
						
					}
					else
					{
						JOptionPane.showMessageDialog(view, "The Image could not be encoded!", 
							"Error!", JOptionPane.INFORMATION_MESSAGE);
					}
					//display the new image
					decode_view();
					image_input.setIcon(new ImageIcon(ImageIO.read(new File(path + "/" + stegan + ".png"))));
					stat_path = path;
					stat_name = stegan;		
				}
				catch(Exception except) {
				//msg if opening fails
				JOptionPane.showMessageDialog(view, "The File cannot be opened!", 
					"Error!", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		
	}
	
	/*
	 *Decode Button Class - handles the Decode Button item
	 */
	private class DecodeButton implements ActionListener
	{
		/*
		 *handles the click event
		 *@param e The ActionEvent Object
		 */
		public void actionPerformed(ActionEvent e)
		{
			
			String message[] = model.decode(stat_path, stat_name);
			System.out.println(stat_path + ", " + stat_name);
			if(message[0] != "hello")
			{
				encode_view();
				JOptionPane.showMessageDialog(view, "The Image was decoded Successfully!", 
							"Success!", JOptionPane.INFORMATION_MESSAGE);
				
				System.out.println(message[1]);
				System.out.println(message[0]);
				
				String password = JOptionPane.showInputDialog("What is the password?");
				
				if( password.length() == message[0].length()){
				input.setText(message[1]);
				JOptionPane.showMessageDialog(view, message[1], 
				"Here is your message", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			else
			{
				JOptionPane.showMessageDialog(view, "The Image could not be decoded! Or you entered an incorrect password!", 
							"Error!", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	/*
	 *Updates the variables to an initial state
	 */
	public void update()
	{
		input.setText("");			//clear textarea
		image_input.setIcon(null);	//clear image
		stat_path = "";				//clear path
		stat_name = "";				//clear name
	}
	
	/*
	 *Main Method for testing
	 */
	public static void main(String args[]) throws Exception
	{
		
		
		new Steganography_Controller(
					
									new Steganography_View("Steganography"),
									new Steganography()
									 
									);
	}
}


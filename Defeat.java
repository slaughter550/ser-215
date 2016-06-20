import javax.swing.*;
import java.io.*;
import javax.imageio.*;

public class Defeat{
	public static void main(String[] args){
		JFrame F = new JFrame("Defeat");
		
		try{
			F.setContentPane(new JLabel(new ImageIcon(ImageIO.read(ClassLoader.getSystemResource("Defeat.png")))));
		}catch(IOException e)
		{
			System.out.println("Image Doesn't exist");
		}
		F.setResizable(false);
		F.pack();
		F.setVisible(true);
		F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
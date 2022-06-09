import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Images implements Media{

	private String ImagePath;
	private ArrayList<String> ImageTags= new ArrayList<String>(); 
	
	public Images(String p,ArrayList<String> T) {
		ImagePath=p;
		ImageTags=T;
	}
	
	
	public ArrayList<String> getImageT() {
		return ImageTags;
	}

	
	public void setImageT(ArrayList<String> Arr) {
		ImageTags=Arr;
	}
	
	public void setPath(String P) {
		ImagePath=P;
	}
	
	public String gettPath() {
		return ImagePath;
	}


	@Override
	public void DisplayMedia() {
		
				File file = new File(ImagePath);
				BufferedImage bufferedImage;
				try {

					bufferedImage = ImageIO.read(file);
					Image image = bufferedImage.getScaledInstance(800, 600, Image.SCALE_DEFAULT);
					ImageIcon imageIcon = new ImageIcon(image);
					JFrame jFrame = new JFrame();

					jFrame.setLayout(new FlowLayout());

					jFrame.setSize(800, 600);
					JLabel jLabel = new JLabel();

					jLabel.setIcon(imageIcon);
					jFrame.add(jLabel);
					jFrame.setVisible(true);



				} catch (IOException e) {

					e.printStackTrace();
				}

			}


	@Override
	public void printMediaInfo() {
		System.out.println("Image Path: "+ImagePath);
		for(String T:ImageTags) {
		System.out.format(" [%s] ", T);
		}
		
	}


	@Override
	public void resizeMedia(InputStream input, Path target, int width, int height) {
		 BufferedImage originalImage;
		try {
			originalImage = ImageIO.read(input);

	        Image newResizedImage = originalImage
	              .getScaledInstance(width, height, Image.SCALE_SMOOTH);

	        String s = target.getFileName().toString();
	        String fileExtension = s.substring(s.lastIndexOf(".") + 1);

	        if (newResizedImage instanceof BufferedImage) {
	            
	            ImageIO.write((BufferedImage) newResizedImage,fileExtension, target.toFile());
	        }else {

	        // Create a buffered image with transparency
	        BufferedImage bi = new BufferedImage(newResizedImage.getWidth(null), newResizedImage.getHeight(null),BufferedImage.TYPE_INT_ARGB);

	        Graphics2D graphics2D = bi.createGraphics();
	        graphics2D.drawImage(newResizedImage, 0, 0, null);
	        graphics2D.dispose();

	        ImageIO.write(bi,fileExtension, target.toFile());
	    }
	        
	        
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	      
		
	}
	
}

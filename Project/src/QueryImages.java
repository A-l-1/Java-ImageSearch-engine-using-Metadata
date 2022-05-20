import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class QueryImages {

	private ArrayList<String> UserTags= new ArrayList<String>(); //array to store the user tags
	private ArrayList<String> OutputImages= new ArrayList<String>(); //images to store the result




	public boolean AddTags(String tag) {
		System.out.println(tag);
		if(!UserTags.contains(tag)) {
			UserTags.add(tag);
			return true;
		}

		return false;

	}

	public boolean deleteTags(String tag) {

		if(UserTags.remove(tag)) {

			return true;
		}

		return false;

	}

	public boolean emptytags() {
		if(UserTags.isEmpty())
			return true;
		return false;
	}


	public boolean CompareTags(ArrayList<String> tags) { //comparing user tags with the gallery images
		
		tags=(ArrayList<String>) tags.stream().map(String::toLowerCase).collect(Collectors.toList());
		UserTags=(ArrayList<String>) UserTags.stream().map(String::toLowerCase).collect(Collectors.toList());

		if(!UserTags.isEmpty()) {

			if(tags.containsAll(UserTags)) {
				return true;
			}
			return false;

		}else
			return false;
	}


	public void DisplayImages() { //function to display the images

		if(OutputImages.isEmpty()) {
			System.out.println("No matching Images !!");
		}else {


			for(String im:OutputImages) {
				File file = new File(im);
				BufferedImage bufferedImage;
				try {

					bufferedImage = ImageIO.read(file);
					ImageIcon imageIcon = new ImageIcon(bufferedImage);
					JFrame jFrame = new JFrame();

					jFrame.setLayout(new FlowLayout());

					jFrame.setSize(500, 500);
					JLabel jLabel = new JLabel();

					jLabel.setIcon(imageIcon);
					jFrame.add(jLabel);
					jFrame.setVisible(true);

					

				} catch (IOException e) {

					e.printStackTrace();
				}

			}
		}
	}



	public void DisplayTags() {
		System.out.print("User tags: ");
		for(String a:UserTags) {
			System.out.format("[%s]",a);
		}
		System.out.println(" ");
	}



	public void Extractxml(String xml) {

		OutputImages.clear();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			// optional, but recommended
			// process XML securely, avoid attacks like XML External Entities (XXE)
			dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

			// parse XML file
			DocumentBuilder db = dbf.newDocumentBuilder();

			Document doc = db.parse(new File(xml));

			// optional, but recommended
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();


			// get <Image>
			NodeList list = doc.getElementsByTagName("Image");

			for (int temp = 0; temp < list.getLength(); temp++) {

				Node node = list.item(temp);

				if (node.getNodeType() == Node.ELEMENT_NODE) {

					Element element = (Element) node;

					String imagepath = element.getElementsByTagName("path").item(0).getTextContent();
					String key = element.getElementsByTagName("keyword").item(0).getTextContent();

					//System.out.println(imagepath);
					//System.out.println(key);
					//System.out.println("");


					if(!OutputImages.contains(imagepath)) {
						String[] keys=key.split(";");
						ArrayList<String> tags= new ArrayList<String>(); 
						for(int j=0;j<keys.length;j++) {
							tags.add(keys[j]);
						}

						
						if(CompareTags(tags)) {
							if(!OutputImages.contains(imagepath))
								OutputImages.add(imagepath);
						}
					}
				}



			}


		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}


	
	
	
	
}

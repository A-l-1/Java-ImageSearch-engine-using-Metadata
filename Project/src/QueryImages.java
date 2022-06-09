
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;


import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class QueryImages implements Comparable<ArrayList<String>>  {

	private ArrayList<String> UserTags= new ArrayList<String>(); //array to store the user tags
	


	public boolean AddTags(String tag) {

		if(!UserTags.contains(tag.toLowerCase())) {
			UserTags.add(tag.toLowerCase());
			return true;
		}

		return false;

	}

	
	public ArrayList<String> gettUserAr() {
		return UserTags;
	}

	
	public void setUserAr(ArrayList<String> Arr) {
		UserTags=Arr;
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



	public void DisplayUserTags() {
		System.out.print("User tags: ");
		for(String a:UserTags) {
			System.out.format("[%s]",a);
		}
		System.out.println(" ");
	}



	public void Extractxml(String xml) { //function that read xml files and retrieve tags

		
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
					
					Images im1=new Images(imagepath,SplitImageTags(key));
					
					if(compareTo(im1.getImageT())==1) { //adding images that match the user tags to display them
						im1.DisplayMedia();
						
					}
				}

			}


		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	


	@Override

	public int compareTo(ArrayList<String> o) {
		o=(ArrayList<String>) o.stream().map(String::toLowerCase).collect(Collectors.toList());

		if(o.containsAll(UserTags)) {
			return 1;
		}else
			return 0;
	}

	
	public ArrayList<String> SplitImageTags(String T) {
		
		String[] key=T.split(";");
		ArrayList<String> keys= new ArrayList<String>(); 
		for(int j=0;j<key.length;j++) {
			keys.add(key[j]);
		}
		
		return keys;
		
	}

	


}

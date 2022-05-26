import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class MetaReader {


	private File Folder;
	
	
	public MetaReader(String fold) {
		
		Folder =new File(fold);
		
	}
	
	
	
	
	public void createxml() { // function to create xml file using the images and keywords inside a folder

		try {
			String xmlFilePath = "Default-xml.xml";
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

			Document document = documentBuilder.newDocument();

			// root element
			Element root = document.createElement("Annotation");
			document.appendChild(root);

			for(File file:Folder.listFiles()) {


				Metadata metadata;
				try {

					if(!file.toString().contains(".DS_Store")) {

						metadata = ImageMetadataReader.readMetadata(file);

						for (Directory directory : metadata.getDirectories()) {

							for (Tag tag : directory.getTags()) {

								if(tag.getTagName().contains("Keywords")) {

									Element Object = document.createElement("Image");
									root.appendChild(Object);

									Element name = document.createElement("path");
									name.appendChild(document.createTextNode(file.toString()));
									Object.appendChild(name);

									Element key = document.createElement("keyword");
									key.appendChild(document.createTextNode(tag.getDescription()));
									Object.appendChild(key);

								}

							}



							if (directory.hasErrors()) {
								for (String error : directory.getErrors()) {
									System.err.format("ERROR: %s", error);
								}
							}
						}
					}
				} catch (ImageProcessingException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}

			}


			// create the xml file
			//transform the DOM Object to an XML File
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(xmlFilePath));

			// If you use
			// StreamResult result = new StreamResult(System.out);
			// the output will be pushed to the standard output ...
			// You can use that for debugging 

			transformer.transform(domSource, streamResult);

			System.out.println("Done creating XML File");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}


	}

	

}



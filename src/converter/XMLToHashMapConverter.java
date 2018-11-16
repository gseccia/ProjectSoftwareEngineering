package converter;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class XMLToHashMapConverter {

	public static HashMap<String,ArrayList<String>> getLayersFromXml() throws ParserConfigurationException, SAXException, IOException {
		// output list to feed the json
		HashMap<String, ArrayList<String>> data = new HashMap<>();
		
		File[] list = new File(System.getProperty("user.dir") + "/resource/maps/").listFiles();
		ArrayList<String> map_files = new ArrayList<String>();
		String pattern = ".*\\.tmx";
        if(list!=null)
        for (File fil : list)
        {
            if (fil.isDirectory())
            {
            	for(File f : fil.listFiles())
            	{
            		if (f.getName().matches(pattern))
            		{
                        map_files.add(fil.getName() + "/" + f.getName());
            		}
            	}
            }
            else if (fil.getName().matches(pattern))
            {
                map_files.add(fil.getName());
            }
        }
		
		for(int i = 0; i<map_files.size(); i++)
		{
		
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			File f =  new File(System.getProperty("user.dir") + "/resource/maps/"+ map_files.get(i));
			Document doc = builder.parse(f);
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("layer");
			ArrayList<String> layersName = new ArrayList<>();
			for (int temp = 0; temp < nList.getLength(); temp++) {
	            Node nNode = nList.item(temp);
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	                Element eElement = (Element) nNode;
	                layersName.add(eElement.getAttribute("name"));
	            }
			}
			data.put(map_files.get(i), layersName);
		}
		return data;
	}
}


package com.jivko.env_manager.Services;


import com.jivko.env_manager.Models.XammpModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

public class ModelService {
    private HashMap<String, XammpModel> modelContainer;

    public ModelService() {
        // init model container
        try {
            this.modelContainer = new HashMap<>();
            //check for existing model file
            File modelFile = this.getModelFile();

            if(modelFile.exists() && !modelFile.isDirectory()){
                //fill model container
                this.fillModelContainer(modelFile);
            } else {
                //build new model file
                this.buildNewModelFile(modelFile);
                this.fillModelContainer(modelFile);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private File getModelFile() throws UnsupportedEncodingException {
        File executable = new File(URLDecoder.decode(getClass().getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8"));
        String path = executable.getParentFile().getAbsolutePath()+"\\model.xml";
        return new File(path);
    }

    private void buildNewModelFile(File modelFile) {
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            Element xamppList = doc.createElement("modelList");

            doc.appendChild(xamppList);

            Element baseXampp = doc.createElement("xampp");
            Element baseXamppDir = doc.createElement("dir");
            baseXamppDir.setTextContent("C:\\xampp");
            Element baseXamppApache = doc.createElement("apache");
            baseXamppApache.setTextContent("0");
            Element baseXamppMysql = doc.createElement("mysql");
            baseXamppMysql.setTextContent("0");
            baseXampp.appendChild(baseXamppDir);
            baseXampp.appendChild(baseXamppApache);
            baseXampp.appendChild(baseXamppMysql);

            xamppList.appendChild(baseXampp);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(modelFile);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private void fillModelContainer(File modelFile) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(modelFile);

            doc.getDocumentElement().normalize();


            NodeList modelList = doc.getElementsByTagName("xampp");

            for (int i = 0; i < modelList.getLength(); i++) {
                Node xamppElement = modelList.item(i);
                NodeList xamppChilds = xamppElement.getChildNodes();

                XammpModel model = new XammpModel();
                for (int k = 0; k < xamppChilds.getLength(); k++) {
                    Node child = xamppChilds.item(k);
                    if(child.getNodeName().equals("dir")) {
                        model.setDir(child.getTextContent());
                    } else if (child.getNodeName().equals("apache")) {
                        model.setApacheStatus(!child.getTextContent().equals("0"));
                    } else {
                        model.setSqlStatus(!child.getTextContent().equals("0"));
                    }
                }
                this.modelContainer.put(model.getDir(), model);
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }


    }

    public ArrayList<String> getDirs() {
        ArrayList<String> result = new ArrayList<String>();

        for (String dir : this.modelContainer.keySet()) {
            result.add(dir);
        }
        return result;
    }

    public Boolean addDir(String dir) {

        if(this.modelContainer.containsKey(dir)) {
            return false;
        }

        XammpModel model = new XammpModel();
        model.setDir(dir);
        model.setApacheStatus(false);
        model.setSqlStatus(false);

        this.modelContainer.put(dir, model);

        return true;
    }
}

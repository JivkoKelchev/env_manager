//package com.jivko.Switcher.Services;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import org.xml.sax.SAXException;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerException;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class DirectoryService {
//
//
//
//    public Boolean addDir(String dir) {
//        try {
//
//            File fXmlFile = new File(
//                    getClass().getClassLoader().getResource("xampp.xml").getFile()
//            );
//            String filepath = fXmlFile.getAbsolutePath();
//
//            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//            Document doc = dBuilder.parse(fXmlFile);
//
//            //optional, but recommended
//            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
//            doc.getDocumentElement().normalize();
//
//            Element newNode = doc.createElement("dir");
//            newNode.setTextContent(dir);
//            doc.getDocumentElement().appendChild(newNode);
//
//
//            // write the content into xml file
//            TransformerFactory transformerFactory = TransformerFactory.newInstance();
//            Transformer transformer = transformerFactory.newTransformer();
//            DOMSource source = new DOMSource(doc);
//            StreamResult result = new StreamResult(new File(filepath));
//            transformer.transform(source, result);
//
//            return true;
//
//        } catch (ParserConfigurationException pce) {
//            pce.printStackTrace();
//            return false;
//        } catch (TransformerException tfe) {
//            tfe.printStackTrace();
//            return false;
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//            return false;
//        } catch (SAXException sae) {
//            sae.printStackTrace();
//            return false;
//        }
//    }
//
//    public Boolean deleteDir(String dir) {
//        return false;
//    }
//}

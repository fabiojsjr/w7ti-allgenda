package com.technique.engine.xml;

import com.technique.engine.util.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Vector;

public class XMLReader {

    @SuppressWarnings("unchecked")
    public static HashMap getAllNodes(String sid, String mainTag, String fileName)
            throws ExceptionWarning {
        HashMap ret = new HashMap();
        Document xmlDocument = getXMLDocumentFromFile(fileName);

        NodeList mainNodes = xmlDocument.getElementsByTagName(mainTag);
        if (mainNodes.getLength() == 0) {
            throw new ExceptionWarning("Could not find tag group " + mainTag + " in the system parameter file " + fileName + "!");
        }

        Node mainNode = mainNodes.item(0);
        NodeList nodes = mainNode.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node groupNode = nodes.item(i);
            NodeList nodesAttr = groupNode.getChildNodes();
            for (int j = 0; j < nodesAttr.getLength(); j++) {
                Node attrNode = nodesAttr.item(j);
                if (attrNode.getNodeType() == Node.ELEMENT_NODE && attrNode.getFirstChild() != null) {
                    ret.put(groupNode.getNodeName() + "#" + attrNode.getNodeName(), attrNode.getTextContent());
                }
            }
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    public static Vector getNodesCollection(String mainTag, String fileName, Object valueObject)
            throws ExceptionWarning {
        Vector ret = new Vector();
        Document xmlDocument = getXMLDocumentFromFile(fileName);

        NodeList mainNodes = xmlDocument.getElementsByTagName(mainTag);
        if (mainNodes.getLength() == 0) {
            throw new ExceptionWarning("Could not find tag group " + mainTag + " in the system parameter file " + fileName + "!");
        }

        Node mainNode = mainNodes.item(0);
        NodeList nodes = mainNode.getChildNodes();
        Class c = valueObject.getClass();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node groupNode = nodes.item(i);
            if (groupNode.getNodeType() == Node.ELEMENT_NODE) {
                NamedNodeMap nodeMap = groupNode.getAttributes();
                Object o;
                try {
                    o = c.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new ExceptionWarning("Could not create an instance of " + c.getName() + "!", e);
                }
                for (int j = 0; j < nodeMap.getLength(); j++) {
                    Node nodeAttr = nodeMap.item(j);
                    String name = nodeAttr.getNodeName();
                    name = name.substring(0, 1).toUpperCase() + name.substring(1);
                    String value = nodeAttr.getNodeValue();
                    try {
                        Method m = c.getDeclaredMethod("set" + name, String.class);
                        m.invoke(o, value);
                    } catch (Exception ignored) {
                    }
                }
                ret.add(o);
            }
        }
        return ret;
    }

    public static String getNodeValueFromFile(File file, String group, String attribute)
            throws ExceptionWarning, ExceptionInfo {
        try (FileInputStream fis = new FileInputStream(file)) {
            return getNodeValueFromStream(fis, group, attribute);
        } catch (FileNotFoundException e) {
            throw new ExceptionWarning("File not found!", e);
        } catch (IOException e) {
            throw new ExceptionWarning("IO Exception", e);
        }
    }

    public static String getNodeValueFromFile(String fileName, String group, String attribute)
            throws ExceptionWarning, ExceptionInfo {
        File f = FileUtil.findFile(fileName);
        return getNodeValueFromFile(f, group, attribute);
    }

    public static String getNodeValueFromStream(InputStream stream, String group, String attribute)
            throws ExceptionWarning, ExceptionInfo {
        try {
            Document xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
            return getNodeValueFromXMLDocument(xmlDoc, group, attribute);
        } catch (IOException e) {
            throw new ExceptionWarning("IO Exception", e);
        } catch (SAXException e) {
            throw new ExceptionWarning("XML parsing Exception", e);
        } catch (Exception e) {
            throw new ExceptionWarning("Parsing failure", e);
        }
    }

    public static String getNodeValueFromXMLDocument(Document xmlDocument, String group, String attribute)
            throws ExceptionWarning, ExceptionInfo {
        NodeList nodes = xmlDocument.getElementsByTagName(group);
        if (nodes.getLength() == 0) return null;

        Node parentNode = nodes.item(0);
        NodeList nodeList = parentNode.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node childNode = nodeList.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE && attribute.equals(childNode.getNodeName())) {
                return childNode.getTextContent();
            }
        }
        return null;
    }

    public static Document getXMLDocumentFromFile(File file) throws ExceptionWarning {
        try (FileInputStream fis = new FileInputStream(file)) {
            return getXMLDocumentFromStream(fis);
        } catch (IOException e) {
            throw new ExceptionWarning("IO Exception", e);
        }
    }

    public static Document getXMLDocumentFromFile(String fileName) throws ExceptionWarning {
        File f = FileUtil.findFile(fileName);
        return getXMLDocumentFromFile(f);
    }

    public static Document getXMLDocumentFromStream(InputStream stream) throws ExceptionWarning {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
        } catch (Exception e) {
            throw new ExceptionWarning("Error parsing XML", e);
        }
    }

    public static Document getXMLDocumentFromString(String xmlString) throws ExceptionWarning {
        try (InputStream is = new ByteArrayInputStream(xmlString.getBytes())) {
            return getXMLDocumentFromStream(is);
        } catch (IOException e) {
            throw new ExceptionWarning("IO Exception", e);
        }
    }

    public static Document getXMLDocumentFromString(StringBuffer xmlString) throws ExceptionWarning {
        return getXMLDocumentFromString(xmlString.toString());
    }

    private XMLReader() {
    }
}

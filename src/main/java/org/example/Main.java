package org.example;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        List<Employee> list = parseXML("data.xml");

        String json = listToJson(list);

        writeString(json);
    }

    private static void writeString(String json) throws IOException {
        try(FileWriter file = new FileWriter("new_data.json")) {
            file.write(json);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String listToJson(List<Employee> list) {
        Type listType = new TypeToken<List<T>>() {}.getType();
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        String json = gson.toJson(list, listType);
        return  json;
    }

    private static List<Employee> parseXML(String s) throws ParserConfigurationException, IOException, SAXException {
        List<Employee> staff = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(s));
        Node root = doc.getDocumentElement();
        System.out.println("Root element is " + root.getNodeName());
        staff = read(root);
        System.out.println(staff);
        return staff;
    }

    private static List<Employee> read(Node root) {
        NodeList nodeList = root.getChildNodes();
        List<Employee> staff = new ArrayList<>();
        Employee employee = new Employee();

        for(int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (Node.ELEMENT_NODE == node.getNodeType()) {
                System.out.println("Recent element is " + node.getNodeName());
                System.out.println("Value is: " + node.getNodeValue());


//                Element element = (Element) node;
//                NamedNodeMap map = element.getAttributes();
//
//                for(int a = 0; a < map.getLength(); a++) {
//                    String attrName = map.item(a).getNodeName();
//                    String attrValue = map.item(a).getNodeValue();
//                    System.out.println("Attribute: " + attrName + "; value: " + attrValue);
//                }

                read(node);

            }
        }
        return staff;
    }
}
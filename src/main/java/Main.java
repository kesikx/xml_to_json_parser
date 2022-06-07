import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        String inputFileName = "data.xml";
        String outputFileName = "data.json";
        List<Employee> employeeList = parseXML(inputFileName);
        String json = listToJson(employeeList);
        writeString(json, outputFileName);
    }

    private static List<Employee> parseXML(String filename) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(filename));
        Node root = doc.getDocumentElement();
        return read(root);
    }

    private static String listToJson(List<Employee> list) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        return gson.toJson(list, listType);
    }

    private static void writeString(String string, String filename) {
        try (FileWriter file = new FileWriter(filename)) {
            file.write(string);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Employee> read(Node node) {
        List<Employee> employeeList = new ArrayList<>();
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node_ = nodeList.item(i);
            if (Node.ELEMENT_NODE == node_.getNodeType()) {
                Element element = (Element) node_;
                String id = element.getElementsByTagName("id").item(0).getTextContent();
                String age = element.getElementsByTagName("age").item(0).getTextContent();
                String country = element.getElementsByTagName("country").item(0).getTextContent();
                String firstName = element.getElementsByTagName("firstName").item(0).getTextContent();
                String lastName = element.getElementsByTagName("lastName").item(0).getTextContent();
                employeeList.add(Employee.builder()
                        .id(Long.parseLong(id))
                        .age(Integer.parseInt(age))
                        .country(country)
                        .firstName(firstName)
                        .lastName(lastName)
                        .build()
                );
            }
        }
        return employeeList;
    }
}

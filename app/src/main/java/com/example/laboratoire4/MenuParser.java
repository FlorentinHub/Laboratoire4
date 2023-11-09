package com.example.laboratoire4;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MenuParser {

    public static List<Repas> parseMenu(InputStream inputStream) {
        List<Repas> repasList = new ArrayList<>();

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);
            doc.getDocumentElement().normalize();

            NodeList repasNodes = doc.getElementsByTagName("repas");

            for (int i = 0; i < repasNodes.getLength(); i++) {
                Node repasNode = repasNodes.item(i);
                if (repasNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element repasElement = (Element) repasNode;

                    int numero = Integer.parseInt(getTagValue("numero", repasElement));
                    String nom = getTagValue("nom", repasElement);
                    String description = getTagValue("description", repasElement);
                    String categorie = getTagValue("categorie", repasElement);
                    double prix = Double.parseDouble(getTagValue("prix", repasElement));

                    Repas repas = new Repas(numero, nom, description, categorie, prix);
                    repasList.add(repas);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return repasList;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
}


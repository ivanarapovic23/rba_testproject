package com.rba.test.WikiResults;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Program {
    public static void main(String[] args) {
        try {
            String projectPath = new File("").getAbsolutePath();
            String jsonFilePath = projectPath + "/results.json";

            ObjectMapper jsonMapper = new ObjectMapper();
            JsonNode rootNode = jsonMapper.readTree(new FileReader(jsonFilePath));

            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
            xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            String xml = xmlMapper.writeValueAsString(rootNode);

            try (FileWriter xmlFile = new FileWriter("results.xml")) {
                xmlFile.write(xml);
                System.out.println("Datoteka je spremljena.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

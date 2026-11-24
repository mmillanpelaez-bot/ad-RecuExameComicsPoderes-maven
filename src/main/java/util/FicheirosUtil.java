package util;

import model.Autor;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FicheirosUtil {

    public static List<String> readFicheiro(String rutaFicheiro) {
        List<String> lista = new ArrayList<>();
        File file = new File(rutaFicheiro);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    lista.add(linea.trim());
                }
            } catch (IOException e) {
                System.out.println("Erro lendo o ficheiro: " + e.getMessage());
            }
        } else {
            System.out.println("Non existe o ficheiro: " + rutaFicheiro);
        }
        return lista;
    }

    public static void serializarAutor(Autor autor, String rutaFicheiro) {
        File file = new File(rutaFicheiro);
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(autor);
            System.out.println("Autor serializado correctamente en " + rutaFicheiro);
        } catch (IOException e) {
            System.out.println("Erro serializando autor: " + e.getMessage());
        }
    }

    public static void exportarAutorXML(Autor autor, String rutaFicheiro) {
        XMLOutputFactory xof = XMLOutputFactory.newInstance();
        try (FileWriter fw = new FileWriter(rutaFicheiro)) {
            XMLStreamWriter xsw = xof.createXMLStreamWriter(fw);
            xsw.writeStartDocument("UTF-8", "1.0");
            xsw.writeStartElement("autor");
            xsw.writeAttribute("id", String.valueOf(autor.getId()));

            xsw.writeStartElement("nome");
            xsw.writeCharacters(autor.getNome());
            xsw.writeEndElement();

            xsw.writeStartElement("apelidos");
            xsw.writeCharacters(autor.getApelidos());
            xsw.writeEndElement();

            xsw.writeStartElement("nacionalidade");
            xsw.writeCharacters(autor.getNacionalidade());
            xsw.writeEndElement();

            xsw.writeEndElement();
            xsw.writeEndDocument();
            xsw.flush();
            xsw.close();
            System.out.println("Autor exportado a XML correctamente en " + rutaFicheiro);
        } catch (IOException | XMLStreamException e) {
            System.out.println("Erro exportando autor a XML: " + e.getMessage());
        }
    }
}

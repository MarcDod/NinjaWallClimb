package de.marcdoderer.XML.Util;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class XMLFileHandler {

    private Document doc;
    private File file;

    public enum Formatation {
        DEFAULT, RAW, PRETTY, COMPACT
    }

    public XMLFileHandler(File file) throws JDOMException, IOException {
        this.file = file;
        reload();
    }

    public void reload() throws JDOMException, IOException {
        this.doc = new SAXBuilder().build(this.file);
    }

    public void save(Formatation formatation) throws IOException {
        Format format;
        switch (formatation) {
            case DEFAULT:
                format = Format.getRawFormat();
                break;
            case RAW:
                format = Format.getRawFormat();
                break;
            case PRETTY:
                format = Format.getPrettyFormat();
                break;
            case COMPACT:
                format = Format.getCompactFormat();
                break;
            default:
                format = Format.getRawFormat();
        }
        XMLOutputter out = new XMLOutputter(format);
        out.output(this.doc,new FileWriter(file));
    }

    public Document getDoc() {
        return doc;
    }
}

package de.marcdoderer.XML.Util;

import org.jdom2.Element;

public abstract class XMLDatatype {
    public XMLDatatype(Object... creationArgs){

    }

    public XMLDatatype(Element representingElement){

    }

    public abstract Element getElement();
}

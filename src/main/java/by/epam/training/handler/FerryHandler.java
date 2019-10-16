package by.epam.training.handler;

import by.epam.training.thread.FerryThread;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class FerryHandler extends DefaultHandler {
    private int carrying;
    private int square;
    private String currentElement;
    private FerryThread ferry;

    public FerryThread getFerry() {
        return ferry;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        currentElement = qName;
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (currentElement.equals("carrying")) {
            carrying = Integer.parseInt(new String(ch, start, length));
        } else if (currentElement.equals("square")) {
            square = Integer.parseInt(new String(ch, start, length));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        currentElement = "";
        if (qName.equals("ferry")) {
           ferry = FerryThread.getInstance(carrying, square);
        }
    }
}

package by.epam.training.handler;

import by.epam.training.entity.Car;
import by.epam.training.thread.CarThread;
import by.epam.training.util.IdGenerator;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class CarHandler extends DefaultHandler {
    private int weight;
    private int square;
    private String currentElement;
    private List<CarThread> cars;

    public CarHandler() {
        cars = new ArrayList<>();
    }

    public List<CarThread> getCars() {
        return cars;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        currentElement = qName;
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (currentElement.equals("weight")) {
            weight = Integer.parseInt(new String(ch, start, length));
        } else if (currentElement.equals("square")) {
            square = Integer.parseInt(new String(ch, start, length));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        currentElement = "";
        if (qName.equals("car")) {
            cars.add(new CarThread(new Car(IdGenerator.getAndIncrementId(), weight, square)));
        }
    }
}

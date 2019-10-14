package by.epam.training.handler;

import by.epam.training.entity.Car;
import by.epam.training.entity.CarType;
import by.epam.training.thread.CarThread;
import by.epam.training.util.IdGenerator;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import static by.epam.training.entity.CarType.LORRY;
import static by.epam.training.entity.CarType.PASSENGER_CAR;

public class CarHandler extends DefaultHandler {
    private CarType type;
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
        switch (currentElement) {
            case "type":
                if (new String(ch, start, length).equalsIgnoreCase(PASSENGER_CAR.toString())) {
                    type = PASSENGER_CAR;
                } else {
                    type = LORRY;
                }
                break;
            case "weight":
                weight = Integer.parseInt(new String(ch, start, length));
                break;
            case "square":
                square = Integer.parseInt(new String(ch, start, length));
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        currentElement = "";
        if (qName.equals("car")) {
            cars.add(new CarThread(new Car(IdGenerator.getAndIncrementId(), type, weight, square)));
        }
    }
}

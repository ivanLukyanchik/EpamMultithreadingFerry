package by.epam.training.parser;

import by.epam.training.exception.CustomException;
import by.epam.training.handler.CarHandler;
import by.epam.training.thread.CarThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

public class CarsSaxParser {
    private static Logger log = LogManager.getLogger(CarsSaxParser.class);

    public List<CarThread> parse(String fileName) throws CustomException {
        return buildListCars(fileName);
    }

    private List<CarThread> buildListCars(String fileName) throws CustomException {
        CarHandler carHandler = new CarHandler();
        try {
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser parser = parserFactory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            reader.setContentHandler(carHandler);
            reader.parse(ClassLoader.getSystemResource(fileName).getFile());
        } catch (IOException e) {
            log.error("Cars SAX : Wrong filepath!");
            throw new CustomException("SAX : Wrong filepath!", e);
        } catch (SAXException | ParserConfigurationException e) {
            log.error("Cars SAX Parsing failure!");
            throw new CustomException("SAX : Parsing failure!", e);
        } catch (Exception e) {
            throw new CustomException("Unexpected error with path: " + fileName, e);
        }
        log.info("Cars SAX : parsed successfully");
        return carHandler.getCars();
    }
}

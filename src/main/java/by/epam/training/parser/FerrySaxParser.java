package by.epam.training.parser;

import by.epam.training.exception.CustomException;
import by.epam.training.handler.FerryHandler;
import by.epam.training.thread.FerryThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class FerrySaxParser {
    private static Logger log = LogManager.getLogger(FerrySaxParser.class);

    public FerryThread parse(String fileName) throws CustomException {
        return buildListFerries(fileName);
    }

    private FerryThread buildListFerries(String fileName) throws CustomException {
        FerryHandler ferryHandler = new FerryHandler();
        try {
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser parser = parserFactory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            reader.setContentHandler(ferryHandler);
            reader.parse(ClassLoader.getSystemResource(fileName).getFile());
        } catch (IOException e) {
            log.error("Ferry SAX : Wrong filepath!");
            throw new CustomException("SAX : Wrong filepath!", e);
        } catch (SAXException | ParserConfigurationException e) {
            log.error("Ferry SAX Parsing failure!");
            throw new CustomException("SAX : Parsing failure!", e);
        } catch (Exception e) {
            throw new CustomException("Unexpected error with path: " + fileName, e);
        }
        log.info("Ferry SAX : parsed successfully");
        return ferryHandler.getFerry();
    }
}

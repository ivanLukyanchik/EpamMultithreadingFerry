package by.epam.training;

import by.epam.training.entity.CarType;
import by.epam.training.entity.Ferry;
import by.epam.training.exception.CustomException;
import by.epam.training.parser.CarsSaxParser;
import by.epam.training.parser.FerrySaxParser;
import by.epam.training.thread.CarThread;
import by.epam.training.thread.FerryThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.*;

public class Main {
    private static Logger log = LogManager.getLogger(Main.class);
    private static final String CARS_PATH = "data/cars.xml";
    private static final String FERRY_PATH = "data/ferry.xml";
    private static int carrying = Ferry.DEFAULT_CARRYING;
    private static int square = Ferry.DEFAULT_SQUARE;

    public static void main(String[] args) {
        System.out.println(CarType.LORRY.toString());
        CarsSaxParser carsSaxParser = new CarsSaxParser();
        FerrySaxParser ferriesSaxParser = new FerrySaxParser();
        List<CarThread> cars = null;
        List<FerryThread> ferries = null;
        try {
            cars = carsSaxParser.parse(CARS_PATH);
            ferries = ferriesSaxParser.parse(FERRY_PATH);
        } catch (CustomException e) {
            log.error(e);
        }
        if (ferries != null) {
            Ferry ferry = ferries.get(0).getFerry();
            carrying = ferry.getCarrying();
            square = ferry.getSquare();
        }
        ExecutorService carService = Executors.newCachedThreadPool();
        ScheduledExecutorService ferryService = Executors.newScheduledThreadPool(1);
        ferryService.scheduleAtFixedRate(FerryThread.getInstance(carrying, square),0,1, TimeUnit.SECONDS);
        if (cars != null) {
            for (CarThread car : cars) {
                carService.submit(car);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    log.error(e);
                }
            }
        }
        ferryService.shutdown();
        carService.shutdown();
    }
}

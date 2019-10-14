package by.epam.training.thread;

import by.epam.training.entity.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.epam.training.entity.Ferry.DEFAULT_CARRYING;
import static by.epam.training.entity.Ferry.DEFAULT_SQUARE;

public class CarThread implements Runnable {
    private static Logger log = LogManager.getLogger(CarThread.class);
    private Car car;

    public CarThread(Car car) {
        this.car = car;
    }

    Car getCar() {
        return car;
    }

    @Override
    public void run() {
        log.info(String.format("Car №%d drove up to the ferry.", car.getId()));
        FerryThread.getInstance(DEFAULT_CARRYING, DEFAULT_SQUARE).registerCarAndSleep(this);
        log.info(String.format("Car №%d was transported.", car.getId()));
    }
}

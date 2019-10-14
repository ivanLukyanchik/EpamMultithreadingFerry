package by.epam.training.thread;

import by.epam.training.entity.Ferry;
import by.epam.training.state.CarState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class FerryThread implements Runnable {
    private static Logger log = LogManager.getLogger(FerryThread.class);
    private static FerryThread instance;
    private static AtomicBoolean isCreated = new AtomicBoolean();
    private static ReentrantLock lock = new ReentrantLock();
    private Ferry ferry;
    private CopyOnWriteArrayList<CarThread> registeredCars = new CopyOnWriteArrayList<>();

    private FerryThread(Ferry ferry) {
        this.ferry = ferry;
    }

    public static FerryThread getInstance(int carrying, int square) {
        if (!isCreated.get()) {
            lock.lock(); // block until condition holds
            try {
                if (instance == null) {
                    instance = new FerryThread(new Ferry(carrying, square));
                    isCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Ferry getFerry() {
        return ferry;
    }

    @Override
    public void run() {
        while (registeredCars.size() > 0) {
            int currentCarrying = ferry.getCarrying();
            int currentSquare = ferry.getSquare();
            int loadedCarsIndex = 0;
            while (true) {
                for (CarThread carThread : registeredCars) {
                    if (carThread.getCar().getCarState() == CarState.LOADED) {
                        continue;
                    }
                    if (currentCarrying >= carThread.getCar().getWeight() && currentSquare >= carThread.getCar().getSquare()) {
                        carThread.getCar().setCarState(CarState.LOADED);
                        log.info(String.format("Car №%d was loaded on a ferry.", carThread.getCar().getId()));
                        currentCarrying -= carThread.getCar().getWeight();
                        currentSquare -= carThread.getCar().getSquare();
                    } else {
                        log.info(String.format("Car №%d doesn't fit on a ferry.", carThread.getCar().getId()));
                    }
                }
                int currentLoadedCarsIndex = 0;
                for (CarThread car : registeredCars) {
                    if (car.getCar().getCarState() == CarState.LOADED)
                        currentLoadedCarsIndex++;
                }
                if (loadedCarsIndex == currentLoadedCarsIndex) {
                    break;
                } else {
                    loadedCarsIndex = currentLoadedCarsIndex;
                }
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    log.error(e);
                }
            }
            log.info("The ferry set off!");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                log.error(e);
            }
            log.info("The ferry is on the other side!");
            for (CarThread carThread : registeredCars) {
                if (carThread.getCar().getCarState() == CarState.LOADED) {
                    carThread.getCar().setCarState(CarState.TRANSPORTED);
                }
            }
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
               log.error(e);
            }
           log.info("The ferry is back and ready for loading!");
        }
    }

    void registerCarAndSleep(CarThread carThread) {
        registeredCars.add(carThread);
        while (carThread.getCar().getCarState() != CarState.TRANSPORTED) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                log.error(e);
            }
        }
        for (CarThread car : registeredCars) {
            if (car.getCar().getCarState() == CarState.TRANSPORTED) {
                registeredCars.remove(car);
            }
        }
    }
}

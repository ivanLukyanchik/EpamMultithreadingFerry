package by.epam.training.util;

public class IdGenerator {
    private static long id = 0;

    public static long getAndIncrementId() {
        long currentId = id;
        id++;
        return currentId;
    }
}

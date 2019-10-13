package by.epam.training.entity;

public class Ferry {
    private int carrying;
    private int square;
    public static final int DEFAULT_CARRYING = 8000;
    public static final int DEFAULT_SQUARE = 60;

    public Ferry(int carrying, int square) {
        this.carrying = carrying;
        this.square = square;
    }

    public int getCarrying() {
        return carrying;
    }

    public int getSquare() {
        return square;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ferry ferry = (Ferry) o;

        if (carrying != ferry.carrying) return false;
        return square == ferry.square;
    }

    @Override
    public int hashCode() {
        int result = carrying;
        result = 31 * result + square;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ferry{");
        sb.append("carrying=").append(carrying);
        sb.append(", square=").append(square);
        sb.append('}');
        return sb.toString();
    }
}

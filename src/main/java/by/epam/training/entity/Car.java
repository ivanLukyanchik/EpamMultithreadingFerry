package by.epam.training.entity;

import by.epam.training.state.CarState;

public class Car {
    private long id;
    private CarType type;
    private int weight;
    private int square;
    private CarState carState;

    public Car(long id, CarType type, int weight, int square) {
        this.id = id;
        this.type = type;
        this.weight = weight;
        this.square = square;
        this.carState = CarState.LOAD_WAITING;
    }

    public long getId() {
        return id;
    }

    public int getWeight() {
        return weight;
    }

    public int getSquare() {
        return square;
    }

    public CarState getCarState() {
        return carState;
    }

    public void setCarState(CarState carState) {
        this.carState = carState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        if (id != car.id) return false;
        if (weight != car.weight) return false;
        if (square != car.square) return false;
        if (type != car.type) return false;
        return carState == car.carState;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + weight;
        result = 31 * result + square;
        result = 31 * result + (carState != null ? carState.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Car{");
        sb.append("id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", weight=").append(weight);
        sb.append(", square=").append(square);
        sb.append(", carState=").append(carState);
        sb.append('}');
        return sb.toString();
    }
}

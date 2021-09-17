package fr.tonychouteau.weatherwidget.weather.definition.wind;

public class Wind {

    public double speed;
    public WindDirection direction;

    public Wind(double speed, int angle) {
        this.speed = speed;
        this.direction = new WindDirection(angle);
    }
}

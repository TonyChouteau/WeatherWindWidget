package fr.tonychouteau.weatherwidget.weather.definition.wind;

public class WindDirection {

    //=================================
    // Static
    //=================================

    static private int SIZE_SHORT = 10;
    static public double[] DIRECTIONS_ANGLE_SHORT = new double[SIZE_SHORT];
    static public String[] DIRECTIONS_NAME_SHORT = {
            "N", "NE", "E", "SE", "S", "SO", "O", "NO", "N"
    };

    static private int SIZE = 18;
    static public double[] DIRECTIONS_ANGLE = new double[SIZE];
    static public String[] DIRECTIONS_NAME = {  "N",
            "NNE",  "NE",   "ENE", "E",
            "ESE",  "SE",   "SSE",  "S",
            "SSO",  "SO",   "OSO",  "O",
            "ONO",  "NO",   "NNO",  "N"
    };

    static {
        for (int i = 0; i < SIZE_SHORT; i ++) {
            DIRECTIONS_ANGLE_SHORT[i] = -22.5 + i * 45;
        }
        for (int i = 0; i < SIZE; i ++) {
            DIRECTIONS_ANGLE[i] = -11.25 + i * 22.5;
        }
    }

    //=================================
    // Non-Static
    //=================================

    public int angle;
    public String name;
    public String nameShort;

    public WindDirection(int angle) {
        this.angle = angle % 360;

        int i = 0;
        while (this.angle > DIRECTIONS_ANGLE[i]) {
            this.name = DIRECTIONS_NAME[i];
            i++;
        }
        i = 0;
        while (this.angle > DIRECTIONS_ANGLE_SHORT[i]) {
            this.nameShort = DIRECTIONS_NAME_SHORT[i];
            i++;
        }
    }

}
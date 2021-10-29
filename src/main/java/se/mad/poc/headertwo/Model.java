package se.mad.poc.headertwo;

public class Model {
    private String value;
    private String valueTwo;

    public Model(String value, String valueTwo) {
        this.value = value;
        this.valueTwo = valueTwo;
    }

    public String getValue() {
        return value;
    }

    public String getValueTwo() {
        return valueTwo;
    }

    @Override
    public String toString() {
        return "Model{" +
                "value='" + value + '\'' +
                ", valueTwo='" + valueTwo + '\'' +
                '}';
    }
}

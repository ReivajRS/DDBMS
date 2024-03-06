package Models;

public class Condition<T> {
    private String attribute;
    private T value;
    private String comparison;

    public Condition(String attribute,T value, String comparison){
        this.attribute = attribute;
        this.value = value;
        this.comparison = comparison;
    }

    public String getAttribute() {
        return attribute;
    }

    public T getValue() {
        return value;
    }

    public String getComparison() {
        return comparison;
    }
}

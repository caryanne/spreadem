package com.xii.spreadem;

public class Variable {

    private String Name;
    private String Function;
    private double Value;
    private double Change;
    private double ChangeProduct;
    private String ValueNotify;
    private int Auto;
    private long LastUpdate;

    @Override
    public String toString() {
        return Name + ": " + Value + " = " + Function + "\n";
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFunction() {
        return Function;
    }

    public void setFunction(String function) {
        Function = function;
    }

    public double getValue() {
        return Value;
    }

    public void setValue(double value) {
        Change = value - Value;
        ChangeProduct = value / Value;
        Value = value;
    }

    public String getValueNotify() {
        return ValueNotify;
    }

    public void setValueNotify(String valueNotify) {
        ValueNotify = valueNotify;
    }

    public int getAuto() {
        return Auto;
    }

    public void setAuto(int auto) {
        Auto = auto;

    }

    public double getChange() {
        return Change;
    }

    public double getChangeProduct() {
        return ChangeProduct;
    }

    public double getLastUpdate() {
        return LastUpdate;
    }

    public void didUpdate() {
        LastUpdate = System.currentTimeMillis();
    }

    public Variable(String name, String function, double value,
                    String valueNotify, int auto) {
        super();
        Name = name;
        Function = function;
        Value = value;
        Change = 0.0;
        ChangeProduct = 1.0;
        ValueNotify = valueNotify;
        Auto = auto;
    }

}

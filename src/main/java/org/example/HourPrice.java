package org.example;

public class HourPrice implements Comparable<HourPrice> {
    String hour;
    int price;

    public HourPrice(String hour, int price) {
        this.hour = hour;
        this.price = price;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return hour + ", " + price;
    }

    public int compareTo(HourPrice otherHourPrice) {
        if (this.price < otherHourPrice.getPrice()) {
            return -1;
        } else {
            return 1;
        }
    }
    public String printHourPrice() {
        return this.hour +" " + this.price + " öre";
    }
}

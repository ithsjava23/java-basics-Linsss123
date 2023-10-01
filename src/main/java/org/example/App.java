package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {
        Locale swedishLocale = new Locale("sv", "SE");
        Locale.setDefault(swedishLocale);
        Scanner scanner = new Scanner(System.in);
        NumberFormat nf_in = NumberFormat.getNumberInstance(swedishLocale);

        String[] hours = {"00-01", "01-02", "02-03", "03-04", "04-05", "05-06", "06-07", "07-08", "08-09", "09-10", "10-11", "11-12",
                "12-13", "13-14", "14-15", "15-16", "16-17", "17-18", "18-19", "19-20", "20-21", "21-22", "22-23", "23-24"};
        String choice;
        String elpriser;
        String[] priceList = new String[24];
        List<HourPrice> hourPrices = new ArrayList<>();
        do {
            String menu = """
                    Elpriser
                    ========
                    1. Inmatning
                    2. Min, Max och Medel
                    3. Sortera
                    4. Bästa Laddningstid (4h)
                    e. Avsluta
                    """;

            System.out.print(menu);
            choice = scanner.nextLine();
            //Add code for choices 1 to 4
            if (choice.equals("1")) {
                hourPrices.clear();
                System.out.println("Skriv in elpriser");
                for (int i = 0; i < 24; i++) {
                    System.out.print(hours[i] + ": ");
                    priceList[i] = scanner.nextLine();
                    hourPrices.add(new HourPrice(hours[i], Integer.parseInt(priceList[i])));
                }
                System.out.println(hourPrices);


            } else if (choice.equals("2")) {
                int meanSum;
                float mean;
                HourPrice minPrice = null, maxPrice = null;
                meanSum = 0;
                for (HourPrice hourPrice : hourPrices) {
                    if (minPrice == null) {
                        minPrice = hourPrice;
                        maxPrice = hourPrice;
                    } else {
                        if (minPrice.getPrice() > hourPrice.getPrice()) {
                            minPrice = hourPrice;
                        }
                        if (maxPrice.getPrice() < hourPrice.getPrice()) {
                            maxPrice = hourPrice;
                        }
                    }
                    meanSum += hourPrice.getPrice();
                }

                mean = (float) meanSum / hourPrices.size();
                BigDecimal meanRounded = new BigDecimal(mean).setScale(2, RoundingMode.HALF_UP);
                System.out.print("Lägsta pris: " + minPrice + " öre/kWh\n");
                System.out.print("Högsta pris: " + maxPrice + " öre/kWh\n");

                nf_in.setMinimumFractionDigits(2);
                System.out.print("Medelpris: " + nf_in.format(meanRounded) + " öre/kWh\n");

            } else if (choice.equals("3")) {
                Collections.sort(hourPrices, Collections.reverseOrder());
                for (HourPrice hourPrice : hourPrices) {
                    System.out.print(hourPrice.printHourPrice() + "\n");
                }


            } else if (choice.equals("4")) {
                int bestPrice = 0, currentPrice;
                HourPrice hourPrice = null;
                for (int i = 0; i < 21; i++) {
                    currentPrice = hourPrices.get(i).getPrice() + hourPrices.get(i + 1).getPrice() +
                            hourPrices.get(i + 2).getPrice() + hourPrices.get(i + 3).getPrice();
                    if (i == 0 || currentPrice < bestPrice) {
                        bestPrice = currentPrice;
                        hourPrice = hourPrices.get(i);
                    }
                }
                float meanPrice = (float) bestPrice / 4;

                System.out.print("Påbörja laddning klockan " + hourPrice.getHour().substring(0, 2) + "\n");
                System.out.println("Medelpris 4h: " + nf_in.format(meanPrice) + " öre/kWh\n");
            }
        } while (!choice.equalsIgnoreCase("e"));
    }
}
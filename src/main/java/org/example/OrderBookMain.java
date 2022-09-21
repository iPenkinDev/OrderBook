package org.example;


import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class OrderBookMain {
    private TreeMap<Integer, Integer> bidMap = new TreeMap<>();
    private TreeMap<Integer, Integer> askMap = new TreeMap<>();
    private static String order;
    private static StringBuilder data = new StringBuilder();


    public static void setData(StringBuilder data) {
        OrderBookMain.data = data;
    }

    public static void main(String[] args) {
        OrderBookMain orderBookMain = new OrderBookMain();
        orderBookMain.run();

    }


    public void run() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/input.txt"))) {
            while (bufferedReader.ready()) {
                order = bufferedReader.readLine();
                char action = order.charAt(0);
                switch (action) {
                    case 'o':
                        remove(order);
                        break;
                    case 'u':
                        update(order);
                        break;
                    case 'q':
                        query(order);
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output.txt"))) {
            bufferedWriter.write(data.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void update(String order) {
        String[] splitData = split(order);
        int price = Integer.parseInt(splitData[1]);
        int size = Integer.parseInt(splitData[2]);
        TreeMap<Integer, Integer> orders = splitData[3].equals("ask") ? askMap : bidMap;

        if (size == 0) {
            orders.remove(price);
        } else {
            orders.put(price, size);
        }

    }

    private void remove(String order) {
        String[] marketOrder = split(order);
        int marketOrderSize = Integer.parseInt(marketOrder[2]);
        Map.Entry<Integer, Integer> orderEntry;
        if (marketOrder[1].equals("buy")) {
            do {

                orderEntry = askMap.firstEntry();
                if (orderEntry.getValue() <= marketOrderSize) {
                    askMap.remove(orderEntry.getKey());
                    marketOrderSize -= orderEntry.getValue();
                } else {
                    askMap.put(orderEntry.getKey(), orderEntry.getValue() - marketOrderSize);
                    return;
                }
            } while (true);
        } else {
            do {

                orderEntry = bidMap.lastEntry();
                if (orderEntry.getValue() <= marketOrderSize) {
                    bidMap.remove(orderEntry.getKey());
                    marketOrderSize -= orderEntry.getValue();
                } else {
                    bidMap.put(orderEntry.getKey(), orderEntry.getValue() - marketOrderSize);
                    return;
                }
            } while (true);
        }


    }

    private void query(String order) {
        String[] splitData = split(order);
        String command = splitData[1];
        switch (command) {
            case "best_bid":
                Map.Entry<Integer, Integer> bestBid = bidMap.lastEntry();
                if (bestBid != null) {
                    data.append(bestBid.getKey()).append(',').append(bestBid.getValue()).append("\n");
                }
                break;

            case "best_ask":
                Map.Entry<Integer, Integer> bestAsk = askMap.firstEntry();
                if (bestAsk != null) {
                    data.append(bestAsk.getKey()).append(',').append(bestAsk.getValue()).append("\n");
                }
                break;
            case "size":
                int price = Integer.parseInt(splitData[2]);
                Integer size = askMap.containsKey(price) ? askMap.get(price) : bidMap.get(price);
                if (size == null) {
                    size = 0;
                }
                data.append(size).append("\n");
        }
    }

    private String[] split(String order) {
        String[] array = new String[4];
        int begin = 0;
        int index = 0;
        for (int i = 0; i < order.length(); i++) {
            if (order.charAt(i) == ',') {
                array[index++] = order.substring(begin, i);
                begin = i + 1;
            }
        }
        array[index] = order.substring(begin);
        return array;
    }

}

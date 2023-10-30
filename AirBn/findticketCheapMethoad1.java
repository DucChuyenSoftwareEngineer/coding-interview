"""
Problem:

You are given a huge list of airline ticket prices between different cities around the
world on a given day. These are all direct flights. Each element in the list has the
format (source_city, destination, price).

Consider a user who is willing to take up to k connections from their origin city A to
their destination B. Find the cheapest fare possible for this journey and print the
itinerary for that journey.

For example, our traveler wants to go from JFK to LAX with up to 3 connections, and our
input flights are as follows:

[
    ('JFK', 'ATL', 150),
    ('ATL', 'SFO', 400),
    ('ORD', 'LAX', 200),
    ('LAX', 'DFW', 80),
    ('JFK', 'HKG', 800),
    ('ATL', 'ORD', 90),
    ('JFK', 'LAX', 500),
]
Due to some improbably low flight prices, the cheapest itinerary would be
JFK -> ATL -> ORD -> LAX, costing $440.
"""


import java.util.*;

class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        Map<Integer, List<int[]>> prices = new HashMap<>();
        for (int[] f : flights) {
            if (!prices.containsKey(f[0])) prices.put(f[0], new ArrayList<>());
            prices.get(f[0]).add(new int[]{f[1], f[2]});
        }
        Queue<int[]> pq = new PriorityQueue<>((a, b) -> (Integer.compare(a[0], b[0])));
        pq.add(new int[]{0, src, K + 1});
        while (!pq.isEmpty()) {
            int[] top = pq.remove();
            int price = top[0];
            int city = top[1];
            int stops = top[2];
            if (city == dst) return price;
            if (stops > 0) {
                List<int[]> adj = prices.getOrDefault(city, new ArrayList<>());
                for (int[] a : adj) {
                    pq.add(new int[]{price + a[1], a[0], stops - 1});
                }
            }
        }
        return -1;
    }
}





import java.util.*;

public class CheapestItinerary {
    public static void main(String[] args) {
        List<Flight> flights = new ArrayList<>();
        flights.add(new Flight("JFK", "ATL", 150));
        flights.add(new Flight("ATL", "SFO", 400));
        flights.add(new Flight("ORD", "LAX", 200));
        flights.add(new Flight("LAX", "DFW", 80));
        flights.add(new Flight("JFK", "HKG", 800));
        flights.add(new Flight("ATL", "ORD", 90));
        flights.add(new Flight("JFK", "LAX", 500));

        String origin = "JFK";
        String destination = "LAX";
        int maxConnections = 3;

        Itinerary cheapestItinerary = findCheapestItinerary(flights, origin, destination, maxConnections);
        System.out.println(cheapestItinerary);
    }

    public static Itinerary findCheapestItinerary(List<Flight> flights, String origin, String destination, int maxConnections) {
        Map<String, List<Flight>> flightMap = new HashMap<>();
        for (Flight flight : flights) {
            flightMap.putIfAbsent(flight.source, new ArrayList<>());
            flightMap.get(flight.source).add(flight);
        }

        Queue<Itinerary> queue = new PriorityQueue<>(Comparator.comparingInt(itinerary -> itinerary.cost));
        queue.offer(new Itinerary(origin, 0));

        while (!queue.isEmpty()) {
            Itinerary currentItinerary = queue.poll();
            if (currentItinerary.cities.size() - 1 > maxConnections) continue;
            if (currentItinerary.getLastCity().equals(destination)) return currentItinerary;

            List<Flight> nextFlights = flightMap.get(currentItinerary.getLastCity());
            if (nextFlights == null) continue;

            for (Flight nextFlight : nextFlights) {
                if (currentItinerary.cities.contains(nextFlight.destination)) continue;
                Itinerary nextItinerary = new Itinerary(currentItinerary);
                nextItinerary.addCity(nextFlight.destination, nextFlight.price);
                queue.offer(nextItinerary);
            }
        }

        return null;
    }
}

class Itinerary {
    List<String> cities;
    int cost;

    public Itinerary(String origin, int cost) {
        this.cities = new ArrayList<>();
        this.cities.add(origin);
        this.cost = cost;
    }

    public Itinerary(Itinerary other) {
        this.cities = new ArrayList<>(other.cities);
        this.cost = other.cost;
    }

    public void addCity(String city, int price) {
        cities.add(city);
        cost += price;
    }

    public String getLastCity() {
        return cities.get(cities.size() - 1);
    }

    @Override
    public String toString() {
        return "Itinerary{" +
                "cities=" + cities +
                ", cost=" + cost +
                '}';
    }
}

class Flight {
    String source;
    String destination;
    int price;

    public Flight(String source, String destination, int price) {
        this.source = source;
        this.destination = destination;
        this.price = price;
    }
}

import java.util.*;

public class FlightItinerary {
    public static void main(String[] args) {
        List<Flight> flights = new ArrayList<>();
        flights.add(new Flight("JFK", "ATL", 150));
        flights.add(new Flight("ATL", "SFO", 400));
        flights.add(new Flight("ORD", "LAX", 200));
        flights.add(new Flight("LAX", "DFW", 80));
        flights.add(new Flight("JFK", "HKG", 800));
        flights.add(new Flight("ATL", "ORD", 90));
        flights.add(new Flight("JFK", "LAX", 500));

        String source = "JFK";
        String destination = "LAX";
        int maxConnections = 3;

        List<Flight> cheapestItinerary = findCheapestItinerary(flights, source, destination, maxConnections);

        if (cheapestItinerary != null) {
            int totalCost = 0;
            for (Flight flight : cheapestItinerary) {
                System.out.println(flight.source + " -> " + flight.destination + " : $" + flight.price);
                totalCost += flight.price;
            }
            System.out.println("Total Cost: $" + totalCost);
        } else {
            System.out.println("No valid itinerary found.");
        }
    }

    static class Flight {
        String source;
        String destination;
        int price;

        public Flight(String source, String destination, int price) {
            this.source = source;
            this.destination = destination;
            this.price = price;
        }
    }

    public static List<Flight> findCheapestItinerary(List<Flight> flights, String source, String destination, int maxConnections) {
        List<Flight> cheapestItinerary = new ArrayList<>();
        List<Flight> currentItinerary = new ArrayList<>();
        int[] minCost = { Integer.MAX_VALUE };

        findCheapestItineraryHelper(flights, source, destination, maxConnections, currentItinerary, cheapestItinerary, minCost);

        return cheapestItinerary;
    }

    public static void findCheapestItineraryHelper(List<Flight> flights, String currentCity, String destination, int maxConnections, List<Flight> currentItinerary, List<Flight> cheapestItinerary, int[] minCost) {
        if (currentCity.equals(destination)) {
            if (currentItinerary.size() <= maxConnections && currentItinerary.stream().mapToInt(f -> f.price).sum() < minCost[0]) {
                minCost[0] = currentItinerary.stream().mapToInt(f -> f.price).sum();
                cheapestItinerary.clear();
                cheapestItinerary.addAll(currentItinerary);
            }
            return;
        }

        if (currentItinerary.size() > maxConnections) {
            return;
        }

        for (Flight flight : flights) {
            if (flight.source.equals(currentCity) && !currentItinerary.contains(flight)) {
                currentItinerary.add(flight);
                findCheapestItineraryHelper(flights, flight.destination, destination, maxConnections, currentItinerary, cheapestItinerary, minCost);
                currentItinerary.remove(currentItinerary.size() - 1);
            }
        }
    }
}
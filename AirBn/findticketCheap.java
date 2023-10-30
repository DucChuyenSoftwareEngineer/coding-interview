


import java.util.*;

public class Solution {
    public static void main(String[] args) {
        // Define the input flights as a list of lists
        List<List<Object>> flights = new ArrayList<>();
        flights.add(Arrays.asList("JFK", "ATL", 150));
        flights.add(Arrays.asList("ATL", "SFO", 400));
        flights.add(Arrays.asList("ORD", "LAX", 200));
        flights.add(Arrays.asList("LAX", "DFW", 80));
        flights.add(Arrays.asList("JFK", "HKG", 800));
        flights.add(Arrays.asList("ATL", "ORD", 90));
        flights.add(Arrays.asList("JFK", "LAX", 500));

        // Define the origin, destination, and maximum number of connections
        String origin = "JFK";
        String destination = "LAX";
        int k = 3;

        // Create an empty list to store the final itinerary
        List<String> itinerary = new ArrayList<>();

        // Call the findCheapestPrice method to find the minimum cost and update the itinerary
        int minCost = findCheapestPrice(flights, origin, destination, k, itinerary);

        // Print the result
        System.out.println("The cheapest itinerary from " + origin + " to " + destination + " with up to " + k + " connections is: ");
        for (String city : itinerary) {
            System.out.print(city + " -> ");
        }
        System.out.println("costing $" + minCost);
    }

    public static int findCheapestPrice(List<List<Object>> flights, String src, String dst, int K, List<String> itinerary) {
        // Create a prices map to store the flight information in an easily accessible format
        Map<String, Map<String, Integer>> prices = new HashMap<>();
        for (List<Object> f : flights) {
            if (!prices.containsKey(f.get(0))) {
                prices.put((String) f.get(0), new HashMap<>());
            }
            prices.get(f.get(0)).put((String) f.get(1), (Integer) f.get(2));
        }

        // Create a priority queue to keep track of the cheapest flight prices and their corresponding itineraries
        PriorityQueue<City> pq = new PriorityQueue<>((a, b) -> a.price - b.price);
        
        // Add the origin city to the priority queue with a price of 0 and 0 stops
        pq.add(new City(src, 0, 0));

        // Loop until the priority queue is empty
        while (!pq.isEmpty()) {
            // Remove the cheapest flight from the priority queue
            City top = pq.remove();

            // If this city is our destination city, we have found our cheapest itinerary
            if (top.city.equals(dst)) {
                // Update the itinerary list and return the price
                itinerary.addAll(top.path);
                return top.price;
            }

            // If this flight has more than K stops, skip it and continue to the next iteration of the loop
            if (top.stops > K) continue;

            // Get all the flights that are directly reachable from this city using our prices map
            Map<String, Integer> adj = prices.getOrDefault(top.city, new HashMap<>());
            for (String a : adj.keySet()) {
                // Create a new City object with an updated price, number of stops, and itinerary
                List<String> newPath = new ArrayList<>(top.path);
                newPath.add(a);
                pq.add(new City(a, top.price + adj.get(a), top.stops + 1, newPath));
            }
        }

        // If we exhaust all possible itineraries without finding a valid one, return -1
        return -1;
    }

    static class City {
        int price;
        int stops;
        String city;
        List<String> path;

        public City(String city, int price, int stops) {
            this.city = city;
            this.price = price;
            this.stops = stops;
            this.path = new ArrayList<>();
            this.path.add(city);
        }

        public City(String city, int price, int stops, List<String> path) {
            this.city = city;
            this.price = price;
            this.stops = stops;
            this.path = path;
        }
    }
}




import java.util.*;

public class Graph {
    private Map<String, List<String>> adjVertices;

    public Graph() {
        this.adjVertices = new HashMap<>();
    }

    void addVertex(String label) {
        adjVertices.putIfAbsent(label, new ArrayList<>());
    }

    void addEdge(String label1, String label2) {
        adjVertices.get(label1).add(label2);
        adjVertices.get(label2).add(label1);
    }

    // Other graph methods (removeVertex, removeEdge, getAdjVertices)...
}


Graph facebookFriends = new Graph();
facebookFriends.addVertex("Person1");
facebookFriends.addVertex("Person2");
facebookFriends.addEdge("Person1", "Person2");  // Person1 and Person2 are now friends
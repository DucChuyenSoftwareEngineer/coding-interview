function findCheapestPrice(flights, src, dst, K) {
    let prices = {};
    let graph = {};
    for (let flight of flights) {
        let [from, to, price] = flight;
        if (!graph[from]) graph[from] = {};
        graph[from][to] = price;
    }
    let pq = [[0, src, K + 1]];
    while (pq.length) {
        let [price, city, stops] = pq.shift();
        if (stops < 0) continue;
        if (!prices[city] || prices[city][0] > price) {
            prices[city] = [price, stops];
            for (let c in graph[city]) {
                pq.push([price + graph[city][c], c, stops - 1]);
            }
        }
    }
    return prices[dst] ? prices[dst][0] : -1;
}

let flights = [
    ['JFK', 'ATL', 150],
    ['ATL', 'SFO', 400],
    ['ORD', 'LAX', 200],
    ['LAX', 'DFW', 80],
    ['JFK', 'HKG', 800],
    ['ATL', 'ORD', 90],
    ['JFK', 'LAX', 500]
];

console.log(findCheapestPrice(flights, 'JFK', 'LAX', 3)); //440

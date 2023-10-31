# coding-interview
     flights.add(Arrays.asList("JFK", "ATL", 150));
        flights.add(Arrays.asList("ATL", "SFO", 400));
        flights.add(Arrays.asList("ORD", "LAX", 200));
        flights.add(Arrays.asList("LAX", "DFW", 80));
        flights.add(Arrays.asList("JFK", "HKG", 800));
        flights.add(Arrays.asList("ATL", "ORD", 90));
        flights.add(Arrays.asList("JFK", "LAX", 500));





        JFK -> LAX

// map prices:

        JFK: 
              ATL:150
              HKG:800
              LAX:500
        ATL:
              SFO:400
              ORD:90
        ORD:
              LAX:200
        LAX:
              DFW:80



PriorityQueue: 1.0
       JFK,0,0,[]

PriorityQueue: 1.1
       ATL,150,1,[JFK,ATL]
       HKG,800,1,[JFK,HKG]
       LAX,500,1,[JFK,LAX]

     top: JFK,0,0,[]

PriorityQueue: 2.0
       LAX,500,1,[JFK,LAX]
       HKG,800,1,[JFK,HKG]

    top: ATL,150,1,[JFK,ATL]

PriorityQueue: 2.1
       ORD,240,2,[JFK,ATL,ORD]
       SFO,550,2,[JFK,ATL,SFO] 
       LAX,500,1,[JFK,LAX]
       HKG,800,1,[JFK,HKG]

PriorityQueue: 3.0
       LAX,500,1,[JFK,LAX]
       SFO,550,2,[JFK,ATL,SFO]
       HKG,800,1,[JFK,HKG]

   top: ORD,240,2,[JFK,ATL,ORD]

PriorityQueue: 3.1

       LAX,440,2,[JFK,ATL,ORD,LAX]
       LAX,500,1,[JFK,LAX]
       SFO,550,2,[JFK,ATL,SFO]
       HKG,800,1,[JFK,HKG]
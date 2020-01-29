# autocomplete-service

Engineering reasoning and assumption
------------------------------------
The server statistics is a central component in a distributed deployment, in this solution it is injected to the auto complete service to support different types of implementations, default implementation is holding the data in memory which also fits testing phase.

Instructions on how to deploy \ run the service
-----------------------------------------------
1. Prerequisites: apache-maven-3.6.3 (http://tinyurl.com/vmuon48), JDK1.8(http://tinyurl.com/yxz36gux)
2. clone/download this repository
3. Build the project: open cmd in the project folder and type: `mvn install`
4. Run and test: to start the server, open cmd in the project folder and type: `mvn cargo:run`
5. GET - /dictionary?prefix='xxx' - returns a json array with all the matching words: open browser window with the following address: http://localhost:8080/autocomplete/service/dictionary?prefix=AA
6. GET - /statistics - returns a json object with SERVER statistics: open browser window with the following address: http://localhost:8080/autocomplete/service/statistics


Discussion
----------
1 **Performance - analysis and ways to improve**  
  1.1 **The solution** sorts the diciotnary once and utilizes a binary search in each query. We pick up the index of the prefix returned by the binary search and return all the words starting from that index.
The complexcity of this solultion is *O(log(n) x len(prefix))* because each comparison in the worst case is O( len(prefix) ).    
####*The below illustration depicts the binary-search solution:*  
![binary-search](/binary-search.jpg)    
  1.2 **Alternative solution** would be to create a tree structure that maps all the possible substrings of the words in the dictionary, holding in each node list of refrences to the all the word indexes in the dictionary, acommulated until that node.
The complexcity of this solultion is constant time *len(prefix) x O(1)*.  
One would argue that scanning 300K words using binary search is fast enough not to justify the second approach, but the decision needs to be taken based on a benchmark and a defined SLA.    
####*The below illustration depicts the more optimized tree based solution:*  
![AutoComplete_Opt](/AutoComplete_Opt.jpg)  

2 **Scalability - how would you scale up your solution and what are the implications**  
A scalable solution would be to deploy the service in a cluster of docker containers, let kubernetes orchestrate the containers at runtime adding and removing nodes based on the CPU and memory usage thresholds, and place a load balancer in front of the service nodes. 
  ####*The below illustration depicts the suggested distributed solution:*  
![deployment](/Deployment.jpg)    
  2.1 **Using Reactive Streams to improve the overall performance and scalability of the application**  
[Project Reactor](https://projectreactor.io/) is a powerful foundational library for building reactive and efficient applications on the JVM based on the [Reactive Streams Specification](https://github.com/reactive-streams/reactive-streams-jvm).    
Reactive types are not intended to allow you to process your requests or data faster, in fact they will introduce a small overhead compared to regular blocking processing. Their strength lies in their capacity to serve more request concurrently, and to handle operations with latency, such as requesting data from a remote server, more efficiently.
With the right implementation, the use of streams of API, can greatly improve the throughput, and the overall performance and scalability of the application.

3 **High availability - how would you make your solution always (99.99% of the time) available**  
By applying the deplyment approach described above, and using more than one geo-region of a cloud provider for deployment, we can make the solution highly available.

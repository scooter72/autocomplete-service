# autocomplete-service

ASHACHIM

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
1. Performance - analysis and ways to improve 
The solution utilizes binary search in an array of words to pick up the index of the prefix argument and returns all the words starting with the prefix from that index. 
Another solution would be to map the first letter of each word to 26 dictionaries which in turn maps the second letter and so on and so forth, this approach will retuen result in constant time and not log n, but one would argue that scanning 300K words using binary search is fast enough not to justify the second approach.

2. Scalability - how would you scale up your solution and what are the implications
A scalable solution would be to deploy the service in docker containers, let kubernetes orchestrate the containers at runtime adding and removing nodes based on the requests load, and place a load balancer in front of the service nodes.

3. High availability - how would you make your solution always (99.99% of the time) available
By applying the deplyment approach described above, and using more than one geo region of a cloud provider for deployment we can achieve high availability.

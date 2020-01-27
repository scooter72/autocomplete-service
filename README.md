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
1. **Performance - analysis and ways to improve**  
The solution sorts the diciotnary once, utilizes binary search in each query. We pick up the index of the prefix argument and returns all the words starting with the prefix from that index.
The complexcity of this solultion is *O(log(n) x len(prefix))* because each comparison in the worst case is O( len(prefix) ).
The below illustration depicts the binary-search solution:  
![binary-search](/binary-search.jpg)    
Alternative solution would be to create a tree structure that maps all the possible substrings in the words in the dictionary holding in each refrence to the word id in the dictionary that was acommulated until that node, this approach will return result in constant time *len(prefix)xO(1)*. One would argue that scanning 300K words using binary search is fast enough not to justify the second approach.
The decision needs to be taken based on a benchmark.
The below illustration depicts the binary-search solution:  
![AutoComplete_Opt](/AutoComplete_Opt.jpg)  
2. **Scalability - how would you scale up your solution and what are the implications**  
A scalable solution would be to deploy the service in docker containers, let kubernetes orchestrate the containers at runtime adding and removing nodes based on the CPU and memory usage thresholds, and place a load balancer in front of the service nodes.

3. **High availability - how would you make your solution always (99.99% of the time) available**  
By applying the deplyment approach described above, and using more than one geo region of a cloud provider for deployment, we make the solution highly availability.

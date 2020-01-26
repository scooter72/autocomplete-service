# autocomplete-service

Engineering reasoning and assumption
------------------------------------


Instructions on how to deploy \ run the service
-----------------------------------------------
1. Prequisites: apache-maven-3.6.3, JDK1.8
2. clone\download this repository
3. Build the project: open cmd in the project folder and type: mvn install
4. Run and test: open cmd in the project folder and type: mvn liberty:run
5. Display service matching words rest api response: open browser window with the following address: http://localhost:9080/autocomplete/service/dictionary?prefix=AA
6. Display service statistics rest api response: open browser window with the following address: http://localhost:9080/autocomplete/service/statistics


Discussion - add a section where you discuss the following topic with respect to your solution:
----------
1. Performance - analysis and ways to improve 
2. Scalability - how would you scale up your solution and what are the implications
3. High availability - how would you make your solution always (99.99% of the time) available

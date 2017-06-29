```` Verto Analytics Data Analysis ````
Application Based on JAVA 8 & Spring Boot

- Application Requirement 
    * JDK 1.8
    * Apache Maven 
      
- Application setup

    * Unzip and place it to your working directory
    * go to ${YOUR-WORKING-DIRECTORY}/verto-analytics
    * mvn clean package
    * java -jar ${YOUR-WORKING-DIRECTORY}/verto-analytics/target/verto-analytics-0.0.1-SNAPSHOT.jar
    
Application End Points :

Documentation using swagger2 - http://localhost:8080/swagger-ui.html#/string-set-controller

Operation Implemented :


1. "upload" -- upload a set of strings to the server, verify it's validity (it must be a set, so no duplicate strings are allowed)

2. "search" -- find all the sets containing given string

3. "delete" -- delete a previously uploaded set of strings from the server

4. "statistics" -- return the statistics for one of the previously uploaded sets (number of strings, length of the shortest string, length of the longest string, average length, median length)

5. "most_common" -- find a string which belongs to the largest number of sets; if there are few such strings, return them all, sorted alphabetically.

6. "longest" -- find the longest string in all the sets; if there are few such strings, return them all, sorted alphabetically.

7. "exactly_in" -- find the string which belongs to exactly given number of sets; if there are few such strings, return them all, sorted alphabetically.

8. "create_intersection" — create (and add to the server) a new set which is an intersection of two previously uploaded sets

9. Implement one more operation

"longest_chain" -- from the previously uploaded sets find the longest chain of strings such that:

- every next string starts with the same character as the previous one ends with

- every next string belongs to the same set as previous one, except one jump to another set is allowed

- specific string from specific set may be used only once

Example:

Set 1: foo oomph hgh

Set 2: hij jkl jkm lmn

Set 3: abc cde cdf fuf fgh

The longest chain is: abc - cdf - fuf - fgh - (set changed here) - hij - jkl - lmn

*Note : Put Extra efforts on unit test, clean code, code coverage, like you are shipping to your production environment.

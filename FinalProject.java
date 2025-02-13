/* Final Project
 * 5/4/2017
 * Nick Smith
 */
import java.util.*;
import java.lang.*;
import java.net.*;
import java.io.*;
import java.util.regex.*;
import java.net.MalformedURLException;
import java.io.IOException;
public class FinalProject {
  public static void main(String[] args) throws MalformedURLException, IOException{
    Deque<String> email = new ArrayDeque<String>();
    Deque<String> unvisitedLinks = new ArrayDeque<String>();
    Deque<String> visitedLinks = new ArrayDeque<String>();
    /* I decided to use the ArrayDeque structure because it has many benefits.
     * I heard about this structure from Kyle (he only mentioned it, he did not tell me how it worked).
     * I also did not ask Kyle for any help on this project. We were just talking about the project generally, nothing detailed.
     * The ArrayDeque is an array implementation that uses a Deque interface.
     * It grows the array size to support whatever capacity is needed.
     * According to the java docs it is also faster than a stack when used as a stack,
     * and faster than a LinkedList when used as a queue.
     * This was also an incredibly easy data structure to work with.
     * This structure has multiple different elements of different data structures within one.
     * I used this structure for all three items that were being collected (email ids, unvisited, and visited links).
     * It felt like the most flexible and easy to use structure for all three, and saw no issue using just ArrayDeque.
     * The ability to use methods seen in queues applied to an array made this data structure work really well to what the project called for, without using a lot of code.
     * Here is the link for the javadocs on ArrayDeque:
     * https://docs.oracle.com/javase/7/docs/api/java/util/ArrayDeque.html */
    
    int arrayRun = 1; //used to keep the while loop running
    Scanner userURL = new Scanner(System.in); //user inputs URL
    System.out.println("Please note that you may encounter some IO exceptions from HTTP response codes (403 and 429 are examples), these normally are from websites that will not let the program visit the site.");
    System.out.println("Enter a URL: ");
    unvisitedLinks.add(userURL.next());
    
    // Variables, URL, regular expressions, and a scanner for data that will be processed in the while loop
    String link = unvisitedLinks.peek();
    InputStream url = null;
    URL myURL = new URL(link);
    String pageToken = null;
    String patternString = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$"; //http://stackoverflow.com/questions/8204680/java-regex-email
    String linkString = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"; //http://stackoverflow.com/questions/163360/regular-expression-to-match-urls-in-java
    Pattern emailPattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
    Pattern linkPattern = Pattern.compile(linkString, Pattern.CASE_INSENSITIVE);
    Pattern href = Pattern.compile("\"([^\"]*)\""); //http://stackoverflow.com/questions/1473155/how-to-get-data-between-quotes-in-java
    Scanner urlReader = new Scanner(myURL.openStream());
    
    // while loop for adding email ids, unvisited and visited links. Majority of the program runs in this while loop
    while(arrayRun == 1){
    while(urlReader.hasNext()){
      pageToken = urlReader.next();
      Matcher emailMatch = emailPattern.matcher(pageToken);
      Matcher linkMatch = linkPattern.matcher(pageToken);
      if(emailMatch.find() && !email.contains(pageToken)){
        email.add(pageToken);
      }
      if(linkMatch.find() && !unvisitedLinks.contains(pageToken)){
        Matcher hrefMatch = href.matcher(pageToken);
        if(hrefMatch.find()){
        unvisitedLinks.add(hrefMatch.group(1));
        }else{
         unvisitedLinks.add(pageToken); 
        }
      }
  }
    arrayRun = 0;
    
    visitedLinks.add(unvisitedLinks.peek());
    unvisitedLinks.removeFirst();
    
    // while loop that asks to visit another site, or exits when there are no more sites stored
    while(arrayRun == 0){
     if(unvisitedLinks.isEmpty()){
     System.out.println("There are no more links to visit.");
     arrayRun = 2;
     }else{
    Scanner keepRunning = new Scanner(System.in);
    System.out.println("Would you like to scan another site?");
    String run = keepRunning.next().toLowerCase();
    
    if(run.equals("yes")){
     myURL = new URL(unvisitedLinks.peek());
     urlReader = new Scanner(myURL.openStream());
     arrayRun = 1;
    }if(run.equals("no")){
      arrayRun = 2;
    }if(!run.equals("yes") && !run.equals("no")){
     System.out.println("Invalid input, please enter yes or no."); 
    }
     }
    }
    }
    
    // End of the program that prints out the email ids, sites that were visited, and sites that were not visited
    System.out.println("Email IDs scanned:");
    System.out.println(email);
    System.out.println();
    System.out.println("Sites Visited:");
    System.out.println(visitedLinks);
    System.out.println();
    System.out.println("Sites Not Visited: ");
    System.out.println(unvisitedLinks);
    
}
}
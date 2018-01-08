import java.io.*;
import java.util.*;

public class ApartmentList {
	static Integer count = 0;
        static Queue<String> Friends = new LinkedList<String>();
	static HashMap<Integer, LinkedHashSet<String>> List = new HashMap<>();
	
	
	public static void main (String[] args) {

		/*Giving path of the file i.e. having the list of all words and reading it */
		File file = new File("/Users/abhishekrane/Desktop/dictionary.txt");
		BufferedReader in = null;
		try{
		in = new BufferedReader(new FileReader(file));
		}
		catch (FileNotFoundException e){
	        e.printStackTrace();
		}
		
		
		/*  Read each word and store in HashMAp */
		String str;
		LinkedHashSet temp;
		ArrayList<String> listOfWords = new ArrayList<String>();
	        String socialNetworkForWord = "LISTY";
		try {
		     while ((str = in.readLine()) != null) {
			   if(str.trim() != null && str.trim() != "")
			     listOfWords.add(str);
			     if(!List.containsKey(str.length())) {
                               temp = new LinkedHashSet<String>();
                               temp.add(str);
                               List.put(str.length(), temp);
                              }
                       temp = List.get(str.length());
                       temp.add(str);
				
                //finding friends for LISTY word and adding to queue
		        if (!(Math.abs((str.length() - socialNetworkForWord.length())) > 2)){
		           if ((levenshteinDistance(socialNetworkForWord, str)) == 1){
                           Friends.add(str);
			   }
		        }
		     }
		//calling test function	
		   test();
		} catch (IOException e) {
		  e.printStackTrace();
		  }
	   System.out.println(count);
	 }
	
	
	
	
	//poll the queue in FIFO manner and calling searchFriend()
	public static void test (){
		while(!Friends.isEmpty()) {
                String findFriend = Friends.poll();
                searchFriends(findFriend);
                System.out.println(findFriend); 
                }    
	}
	
	
	 //Search for friends and adding to Queue
   public static void searchFriends(String findFriend) {
		 
      //Select only words having difference +-1 with the current word
	        LinkedHashSet smallerWords = List.get(findFriend.length()-1);
  		LinkedHashSet sameWords = List.get(findFriend.length());
  		LinkedHashSet largerWords = List.get(findFriend.length()+1);
  	
  	  // storing words in iterator and iterate over it to find friends of each word
  		Iterator<String> itr = smallerWords != null ? smallerWords.iterator():null;
                Iterator<String> itr1 = sameWords != null ? sameWords.iterator():null;
                Iterator<String> itr2 = largerWords != null ? largerWords.iterator():null;
        
        
                if(itr != null) {
                  while(itr.hasNext()) {
                  String word = itr.next();
                    if ((levenshteinDistance(findFriend, word)) == 1){
		       Friends.add(word);
                       itr.remove();
                       count++;
                     }
                  }
                }
       	   
       	
       	      if(itr1 != null) {
                 while(itr1.hasNext()) {
                 String word = itr1.next();
                     if ((levenshteinDistance(findFriend, word)) == 1){
			Friends.add(word);
                        itr1.remove();
                        count++;
                     }
                 }
       	      }
           
       	        if(itr2 != null) {
                   while(itr2.hasNext()) {
                       String word = itr2.next();
                       if ((levenshteinDistance(findFriend, word)) == 1){
   			   Friends.add(word);
                           itr2.remove();
                           count++;
                       }
                   }
                 }
	 
       	
	 }
	 
	 
	 
	 // levenshteinDistance to find the minimum Distance and friends
	public static int levenshteinDistance(String s, String t){

		// for all i and j, d[i,j] will hold the Levenshtein distance between
		// the first i characters of s and the first j characters of t;
		// note that d has (m+1)*(n+1) values
		int[][] arr = new int[s.length()+1][t.length()+1];



		// source prefixes can be transformed into empty string by
		// dropping all characters
		for (int i=1; i<= s.length(); i++){
			arr[i][0] = i;
		}

		// target prefixes can be reached from empty source prefix
		// by inserting every characters
		for (int i=1; i<= t.length(); i++){
			arr[0][i] = i;
		}
		
		arr[0][0] = 0;

		for (int i=1; i<= s.length(); i++){
			for (int j=1; j<= t.length(); j++){
				if (s.charAt(i-1)== t.charAt(j-1)){
					arr[i][j] = arr[i-1][j-1];
				}

				else{
					arr[i][j] = Math.min(Math.min(arr[i-1][j] + 1, arr[i][j-1] + 1),
							arr[i-1][j-1] + 1);
				}
			}
		}

		return arr[s.length()][t.length()];
	}
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class to find songs that match friends' song lists for possible pairings 
 * @author Alice
 *
 */
public class DuetFinder {

	private List<JSONObject> myGood;
	private List<JSONObject> myMaybe;

	private List<JSONObject> friend1Good;
	private List<JSONObject> friend1Maybe;

	private List<JSONObject> friend2Good;
	private List<JSONObject> friend2Maybe;
	
	private List<JSONObject> friend3Good;
	private List<JSONObject> friend3Maybe;
	
	private List<String> myGoodSongsArtists = new ArrayList<>();
	private List<String> myGoodSongs = new ArrayList<>();
	private List<String> myGoodArtists = new ArrayList<>();
	private List<String> myMaybeSongsArtists = new ArrayList<>();
	private List<String> myMaybeSongs = new ArrayList<>();
	private List<String> myMaybeArtists = new ArrayList<>();

	private List<String> friend1GoodSongsArtists = new ArrayList<>();
	private List<String> friend1GoodSongs = new ArrayList<>();
	private List<String> friend1GoodArtists = new ArrayList<>();
	private List<String> friend1MaybeSongsArtists = new ArrayList<>();
	private List<String> friend1MaybeSongs = new ArrayList<>();
	private List<String> friend1MaybeArtists = new ArrayList<>();
	
	private List<String> friend2GoodSongsArtists = new ArrayList<>();
	private List<String> friend2GoodSongs = new ArrayList<>();
	private List<String> friend2GoodArtists = new ArrayList<>();
	private List<String> friend2MaybeSongsArtists = new ArrayList<>();
	private List<String> friend2MaybeSongs = new ArrayList<>();
	private List<String> friend2MaybeArtists = new ArrayList<>();
	
	private List<String> friend3GoodSongsArtists = new ArrayList<>();
	private List<String> friend3GoodSongs = new ArrayList<>();
	private List<String> friend3GoodArtists = new ArrayList<>();
	private List<String> friend3MaybeSongsArtists = new ArrayList<>();
	private List<String> friend3MaybeSongs = new ArrayList<>();
	private List<String> friend3MaybeArtists = new ArrayList<>();

	private List<String> rankedGoodSongsName = new ArrayList<>();
	private List<String> rankedGoodSongsArtist = new ArrayList<>();
	
	private List<String> rankedMaybeSongsName = new ArrayList<>();
	private List<String> rankedMaybeSongsArtist = new ArrayList<>();
	
	private List<String> rankedBothSongsName = new ArrayList<>();
	private List<String> rankedBothSongsArtist = new ArrayList<>();
	
	private UserSongs me;
	private UserSongs friend1;
	private UserSongs friend2;
	private UserSongs friend3;

	/*
	private User friend1;
	private User friend2;
	 */

	/**
	 * Constructs a friend duet finder and initializes
	 */
	public DuetFinder() {
		me = new UserSongs();
		friend1 = new UserSongs();
		friend2 = new UserSongs();
		friend3 = new UserSongs();
		addSongs();				// technically not needed, should be in Firebase
		getSongs();
		convertAllJsonLists();	// technically we don't use this in DuetFinder
		convertAllJsonLists2();
		findGoodMatches();
		findMaybeMatches();
		findBothMatches();
	}

	private void addSongs() {

		/*
		 * Goal:
		 * 	Thriller:		 		  everyone's good song 		= 4 good
		 * 	Come Together: 	 		  everyone's maybe song		= 4 maybe 
		 *  Total Eclipse:   		  myGood + friend1Good  		= 2 good
		 * 	Born to be Wild: 		  myMaybe + friend2Maybe 	= 2 maybe
		 *  Kiss: 			 		  myMaybe, friend3Good 		= 2 maybe-good
		 *  Never Gonna Give You Up:  myMaybe 					= 0
		 *  Every Breath You Take: 	  friend1Good				= 0
		 *  Faith: 					  friend2Good				= 0
		 *  Bohemian Rhapsody:        friend3Good				= 0
		 */
		
		
		// SAMPLE SO WE CAN TEST ALGORITHM -- CAN DELETE BC ALREADY IN FIREBASE
		me.addSong("Thriller", "Michael Jackson", 1);
		me.addSong("Come Together", "The Beatles", 2);
		me.addSong("Total Eclipse of the Heart", "Bonnie Tyler", 1);
		me.addSong("Girls Just Wanna Have Fun", "Cyndi Lauper", 1);
		me.addSong("Never Gonna Give You Up", "Rick Astley", 2);
		me.addSong("Kiss", "Prince", 2);
		me.addSong("Born to be Wild", "Steppenwolf", 2);	
		me.addSong("Whip It", "DEVO", 3);
		me.addSong("Every Breath You Take", "The Police", 3);

		// SAMPLE OF FRIENDS TO KEEP FOR APP DEMO -- NEED TO MAKE SURE SONG TITLES
		// SAME AS LASTFM 
		friend1.addSong("Thriller", "Michael Jackson", 1);
		friend1.addSong("Come Together", "The Beatles", 2);
		friend1.addSong("Total Eclipse of the Heart", "Bonnie Tyler", 1);
		friend1.addSong("Every Breath You Take", "The Police", 1);
		friend1.addSong("Piano Man", "Billy Joel", 2);
		friend1.addSong("Bohemian Rhapsody", "Queen", 2);
		friend1.addSong("Don't Go Breaking My Heart", "Elton John & Kiki Dee", 3);
		friend1.addSong("Never Gonna Give You Up", "Rick Astley", 3);
	
		friend2.addSong("Thriller", "Michael Jackson", 1);
		friend2.addSong("Come Together", "The Beatles", 2);
		friend2.addSong("Kiss", "Prince", 3);
		friend2.addSong("Faith", "George Michael", 1);
		friend2.addSong("Born to be Wild", "Steppenwolf", 2);
		friend2.addSong("Never Gonna Give You Up", "Rick Astley", 3);


		friend3.addSong("Thriller", "Michael Jackson", 1);
		friend3.addSong("Come Together", "The Beatles", 2);
		friend3.addSong("Bohemian Rhapsody", "Queen", 1);
		friend3.addSong("Kiss", "Prince", 1);
		friend3.addSong("Piano Man", "Billy Joel", 2);
		friend3.addSong("Don't Go Breaking My Heart", "Elton John & Kiki Dee", 3);
		friend3.addSong("Never Gonna Give You Up", "Rick Astley", 3);

		
		/*		
		friend1Good = friend1.getGoodSongs();
		friend1Maybe = friend1.getMaybeSongs();
		friend2Good = friend2.getGoodSongs();
		friend2Maybe = friend2.getMaybeSongs();
		friend3Good = friend3.getGoodSongs();
		friend3Maybe = friend3.getMaybeSongs();
		 */		

	}

	
	private void getSongs() {
		myGood = me.getList(1);
		myMaybe = me.getList(2);

		/*		THIS IS SILENCED BECAUSE WE NEED SOMEWAY TO GET FRIEND'S LISTS
		constructor: User(String uid, String email, String name)
 		this should be looped to populate comparison, takes in an int for # friends
		friend1 = new User("1","friend1@gmail.com","Friend1");	//how do we pass this info into a constructor?
		friend2 = new User("2","friend2@gmail.com","Friend2");
		 */
		
		friend1Good = friend1.getList(1);
		friend1Maybe = friend1.getList(2);

		friend2Good = friend2.getList(1);
		friend2Maybe = friend2.getList(2);

		friend3Good = friend3.getList(1);
		friend3Maybe = friend3.getList(2);

	}

	/**
	 * Helper function to convert JSONObject elements into arrays
	 * @param jsonList a list of type JSONObject
	 * @param stringSongList a list of type String
	 * @param stringArtistList a list of type String
	 */
	private void jsonConverter(List<JSONObject> jsonList, List<String> stringSongList, List<String> stringArtistList) {
	
		try {
			if (jsonList.size() != 0) {

				for (int i=0; i<jsonList.size();i++) {
									
					stringSongList.add(jsonList.get(i).get("name").toString());
					stringArtistList.add(jsonList.get(i).get("artist").toString());
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Helper function to convert all List<JSONObject> into appropriate arrays
	 */
	private void convertAllJsonLists() {
		
		jsonConverter(myGood, myGoodSongs, myGoodArtists);
		jsonConverter(friend1Good, friend1GoodSongs, friend1GoodArtists);
		jsonConverter(friend2Good, friend2GoodSongs, friend2GoodArtists);
		jsonConverter(friend3Good, friend3GoodSongs, friend3GoodArtists);
		
		jsonConverter(myMaybe, myMaybeSongs, myMaybeArtists);
		jsonConverter(friend1Maybe, friend1MaybeSongs, friend1MaybeArtists);
		jsonConverter(friend2Maybe, friend2MaybeSongs, friend2MaybeArtists);
		jsonConverter(friend3Maybe, friend3MaybeSongs, friend3MaybeArtists);
		
	}
	
	private void jsonConverter2(List<JSONObject> jsonList, List<String> stringSongs) {
		
		try {
			if (jsonList.size() != 0) {

				for (int i=0; i<jsonList.size();i++) {
					
					String name = jsonList.get(i).get("name").toString();
					String artist = jsonList.get(i).get("artist").toString();
					
					stringSongs.add(name + " ^^^ " + artist);
					
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Helper function to convert all List<JSONObject> into a concatenated array
	 */
	public void convertAllJsonLists2() {
		
		jsonConverter2(myGood, myGoodSongsArtists);
		jsonConverter2(friend1Good, friend1GoodSongsArtists);
		jsonConverter2(friend2Good, friend2GoodSongsArtists);
		jsonConverter2(friend3Good, friend3GoodSongsArtists);
		
		jsonConverter2(myMaybe, myMaybeSongsArtists);
		jsonConverter2(friend1Maybe, friend1MaybeSongsArtists);
		jsonConverter2(friend2Maybe, friend2MaybeSongsArtists);
		jsonConverter2(friend3Maybe, friend3MaybeSongsArtists);
		
	}
	
	
	public void findGoodMatches() {
		
		HashMap<String,Integer> ranking = new HashMap<>();
	   
		//initialize sets
	    Set myGoodSet = new HashSet();
	    myGoodSet.addAll(myGoodSongsArtists);
	    
	    Set friend1GoodSet = new HashSet();
	    friend1GoodSet.addAll(friend1GoodSongsArtists);
	    
	    Set friend2GoodSet = new HashSet();
	    friend2GoodSet.addAll(friend2GoodSongsArtists);
	    
	    Set friend3GoodSet = new HashSet();
	    friend3GoodSet.addAll(friend3GoodSongsArtists);
	    
	   //find match
	    ranking = findMatch(myGoodSet,friend1GoodSet,friend2GoodSet,friend3GoodSet,
	    		myGoodSongsArtists,friend1GoodSongsArtists,friend2GoodSongsArtists,friend3GoodSongsArtists);
	    
//	    System.out.println("good: " + ranking);
	    
	    //convert ranking back to Lists
	    convertRankingsToList(ranking, rankedGoodSongsName, rankedGoodSongsArtist);
	    
	}
	
	
	
	public void findMaybeMatches() {

		HashMap<String,Integer> ranking = new HashMap<>();
		
		//initialize sets
	    Set myMaybeSet = new HashSet();
	    myMaybeSet.addAll(myMaybeSongsArtists);
	    
	    Set friend1MaybeSet = new HashSet();
	    friend1MaybeSet.addAll(friend1MaybeSongsArtists);
	    
	    Set friend2MaybeSet = new HashSet();
	    friend2MaybeSet.addAll(friend2MaybeSongsArtists);
	    
	    Set friend3MaybeSet = new HashSet();
	    friend3MaybeSet.addAll(friend3MaybeSongsArtists);
	    
	  //find match
	    ranking = findMatch(myMaybeSet,friend1MaybeSet,friend2MaybeSet,friend3MaybeSet,
	    		myMaybeSongsArtists,friend1MaybeSongsArtists,friend2MaybeSongsArtists,friend3MaybeSongsArtists);
	    
//	    System.out.println("maybe: " + ranking);
	    
	  //convert ranking back to Lists
	    convertRankingsToList(ranking, rankedMaybeSongsName, rankedMaybeSongsArtist);
		
	}


	public void findBothMatches() {

		HashMap<String,Integer> ranking = new HashMap<>();
		
		//initialize myGood, friendMaybe sets
	    Set myGoodSet = new HashSet();
	    myGoodSet.addAll(myGoodSongsArtists);
	    
	    Set friend1MaybeSet = new HashSet();
	    friend1MaybeSet.addAll(friend1MaybeSongsArtists);
	    
	    Set friend2MaybeSet = new HashSet();
	    friend2MaybeSet.addAll(friend2MaybeSongsArtists);
	    
	    Set friend3MaybeSet = new HashSet();
	    friend3MaybeSet.addAll(friend3MaybeSongsArtists);
	   
	    
	    //find match
	    ranking = findMatch(myGoodSet,friend1MaybeSet,friend2MaybeSet,friend3MaybeSet,
	    		myGoodSongsArtists,friend1MaybeSongsArtists,friend2MaybeSongsArtists,friend3MaybeSongsArtists);
	    
//	    System.out.println("myGood,friendMaybe: " + ranking);
	    
	    HashMap<String,Integer> ranking2 = new HashMap<>();
		   
		//initialize sets
	    Set myMaybeSet = new HashSet();
	    myMaybeSet.addAll(myMaybeSongsArtists);
	    
	    Set friend1GoodSet = new HashSet();
	    friend1GoodSet.addAll(friend1GoodSongsArtists);
	    
	    Set friend2GoodSet = new HashSet();
	    friend2GoodSet.addAll(friend2GoodSongsArtists);
	    
	    Set friend3GoodSet = new HashSet();
	    friend3GoodSet.addAll(friend3GoodSongsArtists);
	    
	   //find match
	    ranking2 = findMatch(myMaybeSet,friend1GoodSet,friend2GoodSet,friend3GoodSet,
	    		myMaybeSongsArtists,friend1GoodSongsArtists,friend2GoodSongsArtists,friend3GoodSongsArtists);
	    
//	    System.out.println("myMaybe,friendGood: " + ranking);
 
	  //convert ranking back to Lists
	    convertRankingsToList(ranking, rankedBothSongsName, rankedBothSongsArtist);
	    convertRankingsToList(ranking2, rankedBothSongsName, rankedBothSongsArtist);

		
	}

	/**
	 * Helper function to convert rankings back into Lists
	 */
	public void convertRankingsToList(HashMap<String,Integer> hash, 
			List<String> name, List<String> artist) {
		
		int max = 4;		// currently hardcoded, but should be an argument passed to constructor
		
		//get concatenated keys
		String[] tempArray = new String[hash.size()];		
		hash.keySet().toArray(tempArray);
		
		//find max and split info into song name and artist
		for (int i=0 ; i<hash.size(); i++) {
			String strSong = tempArray[i];
			int value = hash.get(tempArray[i]);
			
			if (hash.get(tempArray[i]) == max) {
				
				String[] parts = strSong.split("^^^");
				System.out.println(Arrays.toString(parts));
				
				name.add(parts[1]);
				artist.add(parts[2]);
				max--;
				
			} else {
				continue;
			}
		}
		
	}
	
	
	/**
	 * Helper function that finds matches based on predefined Sets
	 */
	public HashMap<String,Integer> findMatch(Set mySet, Set friend1Set, Set friend2Set, Set friend3Set,
			List<String> mySongs, List<String> friend1Songs, 
			List<String> friend2Songs, List<String> friend3Songs) {
		
		HashMap<String,Integer> ranking = new HashMap<>();

		Set meFriend1Intersect = new TreeSet(mySet);
		meFriend1Intersect.retainAll(friend1Set);
		
		Set meFriend2Intersect = new TreeSet(mySet);
		meFriend2Intersect.retainAll(friend2Set);
		
		Set meFriend3Intersect = new TreeSet(mySet);
		meFriend3Intersect.retainAll(friend3Set);
		
		
		if (meFriend1Intersect.size() != 0) {
			for (int i=0; i<meFriend1Intersect.size(); i++) {
				ranking.put((String) meFriend1Intersect.toArray()[i], 2);
			}
		} 
		
		
		if (meFriend2Intersect.size() != 0) {
			for (int i=0; i<meFriend2Intersect.size(); i++) {
				String song = (String) meFriend2Intersect.toArray()[i]; 
				if (ranking.containsKey(song)) {
					ranking.put(song, 3);
				} else {
					ranking.put(song, 2);
				}	
			}
		} 
		
		if (meFriend3Intersect.size() != 0) {
			for (int i=0; i<meFriend3Intersect.size(); i++) {
				String song = (String) meFriend3Intersect.toArray()[i]; 
				if (ranking.containsKey(song)) {
					ranking.put(song, 4);
				} else {
					ranking.put(song, 2);
				}	
			}
		} 
		
		return ranking;
	}

	/**
	 * @return the rankedGoodSongsName
	 */
	public List<String> getRankedGoodSongsName() {
		return rankedGoodSongsName;
	}

	/**
	 * @return the rankedGoodSongsArtist
	 */
	public List<String> getRankedGoodSongsArtist() {
		return rankedGoodSongsArtist;
	}

	/**
	 * @return the rankedMaybeSongsName
	 */
	public List<String> getRankedMaybeSongsName() {
		return rankedMaybeSongsName;
	}

	/**
	 * @return the rankedMaybeSongsArtist
	 */
	public List<String> getRankedMaybeSongsArtist() {
		return rankedMaybeSongsArtist;
	}

	/**
	 * @return the rankedBothSongsName
	 */
	public List<String> getRankedBothSongsName() {
		return rankedBothSongsName;
	}

	/**
	 * @return the rankedBothSongsArtist
	 */
	public List<String> getRankedBothSongsArtist() {
		return rankedBothSongsArtist;
	}
	
	

}

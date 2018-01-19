
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class WordAment {
	
	static String[][] letterArray = new String[4][4];
	static Set<String> words;
	static Map<Integer, Map<Character,Set<String>>> dictionaryMap;
	public static void main(String[] args) throws Exception
	{
		
		double startTime = System.currentTimeMillis();
		words = new HashSet<>();
		
		dictionaryMap = new TreeMap<>();	
		readLetters();
		
		read();
		
		System.out.println("Processing Data....");
		wordCombinations();
		
		double endTime = System.currentTimeMillis();
		
		displayWords();
		System.out.println("total seconds"+(endTime-startTime)/1000);
	}
	
	public static void displayWords()
	{
		//Collections.sort(words,new WordSorter()));
		
		List<String> sortedWords = new ArrayList<>();
		sortedWords.addAll(words);
		Collections.sort(sortedWords, new WordSorter());
		Iterator<String> wordsIterator = sortedWords.iterator();
		
		while(wordsIterator.hasNext())
		{
			System.out.println(wordsIterator.next());
		}
	}
	
	public static void readLetters() throws IOException
	{
		InputStreamReader streamReader = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(streamReader);
		int count = 0;
		
		while(count<4)
		{
			
			System.out.println("Enter the letters in the next row");
			
			String word = reader.readLine();
			String[] rowWords = word.split(" ");
			
			letterArray[count] = rowWords;
			count++;
		}
		
		System.out.println("Input Accepted");
	/*	
		for(int ix=0; ix < letterArray.length; ix++)
		{
			for(int jx=0; jx < letterArray.length; jx++)
			{
				System.out.print(letterArray[ix][jx]);
			}
			System.out.println();
		}
		*/
		
		
	}
	
	
	public static Set<String> wordCombinations()
	{
		//letterArray = new String[][]{{"a","b","c","d"},{"e","f","g","h"},{"i","j","k","l"},{"m","n","o","p"}};
	
		int index = 0;
			
		for(int ix=0; ix < letterArray.length; ix++)
		{
			
			for(int jx=0; jx < letterArray.length; jx++)
			{
				Set<String> indexes = new HashSet<>();
				String temp = letterArray[ix][jx];
				
				List<String> tempList = new ArrayList<>();
				tempList.add(temp);
				indexes.add(ix+""+jx);
				//System.out.println(ix);
				//System.out.println(indexes);
				
				if(isValidTraversal(temp))
				{
		        traverse(ix, jx, temp,indexes);
				}
		       // break;
		        System.gc();
			}
		}
		
		
		System.out.println(letterArray);
		System.out.println(words.size());
		
		return words;
	
	}

	
	
	public static void traverse(int x, int y,String word,Set<String> indexes)
	{	
		//tempwordslist.addAll(wordslist);
		Set<String> tempIndexes = new HashSet<>();
		tempIndexes.addAll(indexes);
		//for(String word : tempwordslist)
		//{
		for(int i=(x!=0)?x-1:x; i<= ((x!=3)?x+1:x); i++)
		{
			for(int j=(y!=0)?y-1:y; j<= ((y!=3)?y+1:y); j++)
			{
				String tempindexString = i+""+j;
				
				/*if(i==3 & j==3)
				{
					System.out.println(word+"################"+letterArray[i][j]);
				}*/
				if(!(indexes.contains(tempindexString)))
				{
					String data = word+letterArray[i][j];
					
					tempIndexes.add(i+""+j);
					
					
					
					/*if(data.length()==16)
					{
						System.out.println("#################");
					}*/
					//System.out.println("#"+indexes+i+j);
					if(isValidTraversal(data))
					{
						//System.out.println(data);
						//words.add(data);
						traverse(i, j, data,tempIndexes);
					
					}
					
					
					//indexes.add(i+""+j);
					tempIndexes = new HashSet<>();
					tempIndexes.addAll(indexes);
					
				}
				
				//System.out.println(wordsToIterate);
				
			}
			
		//}
		
		}
	}
	
	public static void checkWords()
	{
		int count = 0;
		
		/*Iterator<String> iterator = words.descendingIterator();
		while(iterator.hasNext())
		{
			String value = iterator.next();
			if(dictionaryWords.contains(value))
			{
				System.out.println(value);
			}
			//System.out.println("running word"+ wordCombination);
		}*/
	}
	
	
	public static List<Character> getCharacterList(String word)
	{
		char[] characterWords = word.toCharArray();
		//System.out.println(Arrays.asList(characterWords).get(0));
		List<Character> characters = new ArrayList<>();
		
	    for(char character:characterWords)
	    {
	    	characters.add(character);
	    }
		
		return characters;
	}
	
	public void checkValidWord()
	{
		
	}
	
	
	public static void read()
	{
		//Set<String> wordCombinations = wordCombinations();;
		
		List<String> words = new ArrayList<>();
		int count =0;
	
			
		
		//List<Character> characters = getCharacterList(wordCombination);
		
		File file =new File("words.txt");
		try(
				FileReader reader = new FileReader(file);
				BufferedReader bufferwords = new BufferedReader(reader);
				)
		{
			String temp = null;
			while((temp = bufferwords.readLine())!=null)
			{	
				temp = temp.toLowerCase();
				Map<Character,Set<String>> characterMaps = null;
				Set<String> wordList = null;
				if((characterMaps = dictionaryMap.get(temp.length())) == null)
				{
					characterMaps = new HashMap<>();
				}
				if((wordList = characterMaps.get(temp.charAt(0))) == null)
				{
					wordList = new HashSet<>();
				}
					
				wordList.add(temp);
				characterMaps.put(temp.charAt(0),wordList);
				dictionaryMap.put(temp.length(), characterMaps);
				count++;
				
				//System.out.println(characterMaps);
			}
		}
		
		catch(Exception e)
		{
			System.out.println(e);
			System.out.println("error");
		}
		
		
		System.out.println("Words read successfully");
		System.out.println("Total Words: "+ count);
		/*for(Integer mapdata: dictionaryMap.keySet())
		{
			//System.out.println("Word Length: "+ mapdata+"total maps"+ dictionaryMap.get(mapdata).keySet()); 
			Map<Character,Set<String>> characterMaps = dictionaryMap.get(mapdata);
			for(Character character : dictionaryMap.get(mapdata).keySet())
			{
				System.out.println("Word Length: "+ mapdata+" Character: "+character+ " total words: "+ characterMaps.get(character).size()); 
			}
		}*/
	}
	
	public static boolean isValidTraversal(String word)
	{
		boolean isTraversalValid = false;
		for(int i = word.length(); i < 16;i++)
		{
		   StringBuffer buffer = new StringBuffer();
		   Map<Character,Set<String>> charMaps = dictionaryMap.get(i);
		   buffer.append(charMaps.get(word.charAt(0)));
		   if(buffer.indexOf(word)!=-1)
		   {
			   isTraversalValid = true;
			   if(word.length()>2)
			   {
			   WordChecker checker = new WordChecker(word);
			   Thread thread = new Thread(checker);
			   thread.start();
			   }
			   break;
		   }
		}
		return isTraversalValid;
	}	
}

class WordSorter implements Comparator<String>
{

	@Override
	public int compare(String str1, String str2) {
		// TODO Auto-generated method stub
		if(str1.length()>str2.length())
		{
			return 1;
		}
		else if(str1.length()==str2.length())
		{
			return 0;
		}

			return -1;
			
	}
	
}

class WordChecker implements Runnable
{
	String word;
	public WordChecker(String word)
	{
      this.word = word;
       
	}
	
	@Override
	public void run()
	{
		 Map<Character,Set<String>> characterMaps =  WordAment.dictionaryMap.get(word.length());
		 Set<String> words =  characterMaps.get(word.charAt(0));
		 if(words.contains(word))
	        {
	        	//System.out.println("word found:" + word);
	        	WordAment.words.add(word);
	        }
			 //System.out.println("run"+ word);
	}
}

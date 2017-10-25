package comp5216.sydney.edu.au.SI.Tag;

import android.content.Context;

import com.google.cloud.android.speech.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class KeyWord {

	private ArrayList<Word> words = new ArrayList<Word>();
	private static ArrayList<String> cW = new ArrayList<String>();
	private String text;


	private static Context context;
	public static void setContext(Context con){
		context = con;
	}

	public KeyWord(String str) {
        try {
			setCommonWords();
		} catch (IOException e) {
			e.printStackTrace();
		}
            text = str;
	    	getKeyWords();

	}

    private Comparator<Word> comparator = new Comparator<Word>() {
		public int compare(Word w1, Word w2) {
			if (w1.count != w2.count) {
				return w2.count - w1.count;
			} else {
				return w2.value.compareTo(w1.value);
			}
		}
	};

	private void getKeyWords() {
		String[] wordsFromInput = text.split("\\s|\\.|-|'|<|\\?|,|\"|\\(|\\)|\\[|\\]|\\;");
		for (String w : wordsFromInput) {
			if (w.length()>1&&!cW.contains(w.toLowerCase())) {
				boolean isNotExisted = true;
				for (Word checkingWord : words) {
					if (checkingWord.getValue().equals(w.toLowerCase())) {
						checkingWord.addCount();
						isNotExisted = false;
					}
				}
				if (isNotExisted)
					words.add(new Word(w.toLowerCase(), 1));
			}
		}

		Collections.sort(words, comparator);
	}

	public String getTheKeyWord(int num){
        return words.get(num).value;
    }

	public String toString() {
		String str = "";
		for (int i = 0; i<words.size();i++) {
			str += words.get(i).value + " " + words.get(i).count + "\n";
		}
		return str;
	}

	public void setCommonWords() throws FileNotFoundException, IOException {
		InputStream is = context.getResources().openRawResource(R.raw.commonwords);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = "";
		while ((line=br.readLine())!=null){
			cW.add(line);
		}
		br.close();

	}

	public int getSize(){
		return words.size();
	}



}

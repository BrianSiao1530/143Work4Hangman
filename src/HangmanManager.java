import java.util.*;

public class HangmanManager{
    private String ans = "";
    private String previous = "";
    private int guessLeft;
    private int record ;
    private Set<String> partOfDictionary = new TreeSet<>();
    private Set<Character> guessed = new TreeSet<>();
    private Map<String , Set<String>> classification = new TreeMap<>();
    private int guessedLength;
    public HangmanManager(Collection<String> dictionary,
                          int length, int max) {
        this.guessLeft = max;
        this.guessedLength = length;
        if (length < 1 || max < 0) {
            throw new IllegalArgumentException();
        }
        for (String words: dictionary){
            if(words.length() == length){
                this.partOfDictionary.add(words);
            }
        }
        for(int i = 0; i <= length - 2; i++){
            this.ans = this.ans + "-" + " ";
        }
        this.ans = ans +"-";
    }
    public Set<String> words(){
        return this.partOfDictionary;
    }
    public int guessesLeft(){
        return this.guessLeft;
    }
    public Set<Character> guesses(){
    return guessed;
    }
    public String pattern(){
        return ans;
    }
    public int record(char guess){
        record = 0;
        previous = ans;
        if(guessLeft < 1 || partOfDictionary.isEmpty() || guessed.contains(guess)){
            throw new IllegalArgumentException("");
        }
        this.guessed.add(guess);
        Iterator<String> word = partOfDictionary.iterator();
        while(word.hasNext()) {
            String testWord = word.next();
            classification = createPattern(classification, testWord, this.guessedLength, guess);
        }
        Set<String> keyOfMap ;
        Set<String> chosenWord = new HashSet<>();
        Set<String> tempValue;
        String tempKey;
        keyOfMap = classification.keySet();
        Iterator<String> in = keyOfMap.iterator();
        while(in.hasNext()){
            tempKey = in.next();
            tempValue = classification.get(tempKey);
            if (chosenWord.size() < tempValue.size()){
                chosenWord = classification.get(tempKey);
                this.ans = tempKey;
            }
        }
        classification.clear();
        partOfDictionary = chosenWord;
        if(ans.equalsIgnoreCase(previous)) {
            guessLeft = guessLeft - 1;
        }
        compare(previous);
        return record;
    }
   // 創出一個MAP(已經分好類了)
    public Map<String, Set<String>> createPattern(Map<String , Set<String>> classification, String word, int length, char guess){
        String store = pat(word, length, guess);
        if(!classification.containsKey(store)){
            Set<String> sortedWord = new TreeSet<>();
            classification.put(store, sortedWord);
            sortedWord.add(word);
        }else {
            Set<String> sortedWord = classification.get(store);
            sortedWord.add(word);
        }
        return classification;
    }
    //回傳現在猜的樣子
    public String pat(String input, int length, char guess){
        String store = "";
        int num = (length * 2 - 2);
        String word = toDash(input, length);
        for (int i = 0; i <= num; i ++) {
            if (word.charAt(i) == guess ) {
                store = store + guess ;
            }else {
                store = store + ans.charAt(i);
            }
        }
        return store;
    }
    public String toDash(String word, int length){
        String temp = "";
        for (int i = 0; i <= length - 2; i++) {
              temp = temp + word.charAt(i) + " ";
        }
        temp = temp + word.charAt(length - 1);
        return temp;
    }
    public void compare(String answer){
        if(answer.equalsIgnoreCase(this.ans)){
            record = 0;
        }
        else {
            for (int i = 0; i <= ans.length() - 1; i++) {
                if (ans.charAt(i) != answer.charAt(i)) {
                    record += 1;
                }
            }
        }
    }
}

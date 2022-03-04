// Alex Bohnson, Jason Wild, Joel Fletcher, Steven Sipes
// 3/4/2022
// CS 145
// LAB 5 - Grammar Solver

import java.util.*;

public class GrammarSolver {

    private SortedMap<String, List<String[]>> grammar;
    private Random random;

    public GrammarSolver(List<String> grammar) {

        this.grammar = buildGrammar(grammar);
        this.random = new Random();

        Iterator<String> itr = this.grammar.keySet().iterator();
        while(itr.hasNext()) {
            String curr = itr.next();
            System.out.print(curr + ": ");
            for(String[] s : this.grammar.get(curr)) {

                if(s.length > 0) {
                    System.out.print(" ");
                    System.out.print(s[0]);
                    for (int i = 1; i < s.length; i++) {
                        System.out.print("|" + s[i]);
                    }
                }
            }
            System.out.println();
        }
    }

//    Returns whether or not the given symbol exists in the grammar.

    public boolean grammarContains(String symbol) {
//        String [] input = symbol.split(" ");
//
//        Iterator<String> itr = grammar.keySet().iterator();
//        while (itr.hasNext()){
//            for (String s : input) {
//                if (s.equals(itr.next())) {
//                    return true;
//                }
//            }
//        }
//        return false;
        return grammar.containsKey(symbol);
    }

//    Returns the given symbol the given number of times.

    public String[] generate(String symbol, int times) {
        if(!grammarContains(symbol) || times < 0) {
            throw new IllegalArgumentException();
        }
        String[] output= new String[times];

        for(int i = 0 ; i < output.length; i++) {
            output[i] = grammarCrawler(symbol);

                System.out.println("Output is completed: " + output[i].trim());
            System.out.println();

        }

        return output;
    }

    public String grammarCrawler(String symbol) {
//        String[] splitSymbol = symbol.split(" ");
//        for(String s : splitSymbol) {
//            System.out.println("It's " + grammarContains(symbol) + " that the grammar contains "  + symbol);
        String output = "";
            if(!grammarContains(symbol)) {
                System.out.println("Found it :" + symbol);
                return symbol;
            } else {
                System.out.print("Crawling through " + symbol + ": ");
                for(int i = 0; i < grammar.get(symbol).size(); i++) {
                    for(int j = 0; j < grammar.get(symbol).get(i).length; j++) {
                        System.out.print(grammar.get(symbol).get(i)[j] + ", ");

                    }
                    System.out.print(" | ");
                }
                System.out.println();
                int randomSelector1 = random.nextInt(grammar.get(symbol).size()); // size of arraylist of string[]s
                String[] currentArray = grammar.get(symbol).get(randomSelector1); // random array within
                for (int i = 0; i < currentArray.length; i++) {
                    String current = currentArray[i];
                    output += grammarCrawler(current) + " ";
                }
            }
//        }

        return output.trim();
    }

//    Returns a string containing all the symbols in the grammar, separated by
//    commas alphabetically, and enclosed in brackets.

    public String getSymbols() {
        String output = "[";
        Set<String> unsortedNT = grammar.keySet();

        SortedSet<String> sortedNT = new TreeSet<>();
        sortedNT.addAll(unsortedNT);

        Iterator<String> itr = sortedNT.iterator();
        output += itr.next();

        while(itr.hasNext()) {
            output += ", " + itr.next();
        }

        return output + "]";
    }


//    Given the list of words, returns a grammar Map containing the non-terminal and
//    a list of all the contained rules or words.

    private SortedMap<String, List<String[]>> buildGrammar(List<String> list) {
        SortedMap<String, List<String[]>> grammar = new TreeMap<>();

        for(String word: list) {
            String[] splitNonTerminal = word.trim().split(":");
            System.out.println(splitNonTerminal[0] + " and " + splitNonTerminal[1]);
            grammar.put(splitNonTerminal[0].trim(), formatRules(splitNonTerminal[1]));
        }
        return grammar;
    }

//    Given a line of text, returns a list of individual words (separated by pipes) with
//    any white space removed.

    private List<String[]> formatRules(String strings) {

        String[] noPipes = strings.split("[|]");
        System.out.println("noSpace is " + noPipes.length + " long");
        for(String s : noPipes){
            System.out.println(s.trim() + " ");
        }
        List<String[]> rules = new ArrayList<>();

        for(int i = 0; i < noPipes.length; i++) {

            System.out.println("Adding " + noPipes[i].trim());
            String[] noSpaces = noPipes[i].trim().split("[ \t]+");

            rules.add(noSpaces);
        }
        return rules;
    }

}

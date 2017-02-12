import java.util.Scanner;
import java.util.Set;

/**
 * 
 * @author victoriajuan
 * 
 * MyClient takes a self insert associator for words 
 * as specified by THESAURUS_FILE and input from standard.in and
 * outputs the spruced up version of the user's input by selecting
 * synonyms for all input words that have synonyms.
 * 
 * This Client program is dependent on TextAssociator
 */
public class MyClient {

	public static void main(String[] args) {
		TextAssociator tAssociator = new TextAssociator();
		String[] english = {"dream","life", "sweet", "secret","street","panda", "sweet", "uncle", "weather", "weed", 
				"world", "information", "text","remove", "chicken", "kitchen", "beef", "juice", "joke",
				"windows", "help", "search", "source", "file", "singer", "math", "computer"};
		String[] chinese ={"梦想","生活", "甜的", "秘密","街道","熊猫", "甜的", "叔叔", "天气", "杂草", 
				"世界", "信息", "文本","去除", "鸡", "厨房", "牛肉", "果汁", "玩笑",
				"视窗", "帮", "搜索", "资源", "文件", "歌手", "数学", "计算机"};
		
		for(int i = 0; i < english.length; i++){
			tAssociator.addNewWord(english[i].toLowerCase());
			tAssociator.addAssociation(english[i].toLowerCase(), chinese[i]);
		}
		
		Scanner scan= new Scanner(System.in);
		String inputString = "";
		
		System.out.println("English and Chinese Translator");
		System.out.println("dream,life, sweet, secret, street, panda, sweet, uncle, weather, weed, world, information, text,remove");
		System.out.println("chicken, kitchen, beef, juice, joke, windows, help, search, source, file, singer, math, computer");
		System.out.println();
		
		while (true) {
			System.out.print("Choose from above given words to see the translation(space between words): ");
			inputString = scan.nextLine();
			if (inputString.equals("exit")) {
				break;
			}
			String[] tokens  = inputString.split(" ");
			String result = "";
			for (String token : tokens) {
				Set<String> words = tAssociator.getAssociations(token.toLowerCase());
				if (words == null) {
					result += " " + token;
				} else {
					result += " " + words;
				}
			}
			System.out.println(result.trim());
			System.out.println();	
		}
		
	}

}
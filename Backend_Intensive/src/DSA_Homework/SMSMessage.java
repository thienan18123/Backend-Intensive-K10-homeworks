package DSA_Homework;

public class SMSMessage {
	public int solution (String S, int K) {
		int messages = 0;
		int currentLength = 0;
		
		String[] words = S.split(" "); //O(n)
		
		for (String word : words) {
			if (word.length() > K) {
				return -1;
			}
			
		}
		
		//0(n)
		int needed =0;
		for (String word : words) {
			if (currentLength <= 0) {
				needed = word.length();
			} else {
				needed = currentLength + 1 + word.length();
			}
			if (needed <= K ) {
				currentLength = needed;
			} else {
				messages++;
				currentLength = word.length();
			}
			
		}
		messages++;
		return messages;
	}

	public static void main(String[] args) {
		
		
		SMSMessage message = new SMSMessage();
		
		System.out.println(message.solution("SMS messages are really short", 12));
		System.out.println(message.solution("toi that la tuyet", 7));

	}

}

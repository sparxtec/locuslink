package com.locuslink.dto.OpenAI;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatCompletionResponse {

	private List<Choice> choices;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class  Choice {
		private int index;
		private ChatMessage message;		
	}		
	
}


//{
//"id": "chatcmpl-123",
//"object": "chat.completion",
//"created": 1677652288,
//"model": "gpt-3.5-turbo-0613",
//"system_fingerprint": "fp_44709d6fcb",
//"choices": [{
//  "index": 0,
//  "message": {
//    "role": "assistant",
//    "content": "\n\nHello there, how may I assist you today?",
//  },
//  "finish_reason": "stop"
//}],
//"usage": {
//  "prompt_tokens": 9,
//  "completion_tokens": 12,
//  "total_tokens": 21
//}
//}
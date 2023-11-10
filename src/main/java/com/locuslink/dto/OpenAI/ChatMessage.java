package com.locuslink.dto.OpenAI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
	private String role;
	private String content;	
}



//curl https://api.openai.com/v1/chat/completions \
//	  -H "Content-Type: application/json" \
//	  -H "Authorization: Bearer $OPENAI_API_KEY" \
//	  -d '{
//	    "model": "gpt-3.5-turbo",
//	    "messages": [
//	      {
//	        "role": "system",
//	        "content": "You are a helpful assistant."
//	      },
//	      {
//	        "role": "user",
//	        "content": "Hello!"
//	      }
//	    ]
//	  }'
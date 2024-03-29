package com.basedeveloper.jellylinkserver;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class Resource {
	@PostMapping("abc")
	public String testPost(@RequestBody Map<String, Object> data)
	{
		String name = (String) data.get("name");
		return name;
	}
}

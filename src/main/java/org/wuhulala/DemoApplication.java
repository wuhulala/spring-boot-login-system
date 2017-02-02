package org.wuhulala;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	//在intellij idea 中，需要在maven中 新建 Run configuration，对应的command-line 为 ：clean install spring-boot:repackage

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

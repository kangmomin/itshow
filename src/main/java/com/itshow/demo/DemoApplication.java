package com.itshow.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.File;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "userAuditorAware")
public class DemoApplication {

	public static void main(String[] args) {
		String path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\"; //폴더 경로
		File Folder = new File(path);

		// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
		if (!Folder.exists()) {
			try{
				Folder.mkdir(); //폴더 생성합니다.
			}
			catch(Exception e){
				e.getStackTrace();
			}
		}

		SpringApplication.run(DemoApplication.class, args);
	}

}

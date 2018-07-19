package com.generate.letter.generateLetter.scheduler;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LetterScheduler {

	@Value("${default.pdf.path}")
	String defaultPdfPath;

	@Scheduled(cron = "${delete.letter.CronExpression}")
	public void deleteLetterScheduler() {

		File folder = new File(defaultPdfPath);
		if (folder.exists() && folder.isDirectory()) {

			for (File f : folder.listFiles()) {
				if (f.delete()) {
					System.out.println("'" + f.getName() + "' deleted successfully");
				} else {
					System.out.println("Fail to delete '" + f.getName() + "'");
				}
			}
		}
	}

}

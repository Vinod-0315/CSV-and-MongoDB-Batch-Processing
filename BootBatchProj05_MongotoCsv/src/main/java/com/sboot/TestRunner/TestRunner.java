package com.sboot.TestRunner;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("runner")
public class TestRunner implements CommandLineRunner {

	@Autowired
	@Qualifier("Mongojob")
	private Job job;
	
	@Autowired
	private JobLauncher launch;
	
	
	
	@Override
	public void run(String... args) throws Exception {
		
		//prepere job
		
		try {
		JobParameters params=new JobParametersBuilder().addDate("startDate", new Date()).toJobParameters();
		
		JobExecution run = launch.run(job, params);
		System.out.println("job execution satatus:"+run.getExitStatus());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

}

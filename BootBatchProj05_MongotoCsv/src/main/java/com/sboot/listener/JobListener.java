package com.sboot.listener;

import java.util.Date;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component("jobListener")
public class JobListener implements JobExecutionListener {
	
	Long startTime,endTime;
	
	public void beforeJob(JobExecution jobExecution)
	{
	    System.out.println(new Date());  
	    startTime=System.currentTimeMillis();
	    System.out.println("before job:"+"startTime");
	    System.out.println("get status:"+jobExecution.getStatus());
	}
	
	public  void afterJob(JobExecution jobExecution)
	{
		System.out.println(new Date());
		endTime=System.currentTimeMillis();
		System.out.println("job excution time:"+(endTime-startTime));
		System.out.println("after job:"+ jobExecution.getStatus());
		System.out.println("job exit status:"+jobExecution.getExitStatus());
		
	}

}

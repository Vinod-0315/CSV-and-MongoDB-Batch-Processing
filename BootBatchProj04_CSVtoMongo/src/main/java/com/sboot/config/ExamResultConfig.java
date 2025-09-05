package com.sboot.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.sboot.entity.ExamResult;
import com.sboot.listener.JobListener;
import com.sboot.processor.ExamProcessor;

@Configuration
@EnableBatchProcessing
public class ExamResultConfig {
	
	
	@Autowired
	private ExamProcessor processor;
	
	@Autowired
	private MongoTemplate template;
	
	
	@Autowired
	private JobListener listener;
	
	
	@Bean(name="reader")
	public FlatFileItemReader<ExamResult> createReader()
	{
		return new  FlatFileItemReaderBuilder<ExamResult>().name("file-reader").resource(new ClassPathResource("topbrains.csv")).linesToSkip(1).delimited().delimiter(",").names("id","dob","percentage","semester","gender","email","Name").targetType(ExamResult.class).build();
	}
	
	 
	@Bean(name="writer")
	public MongoItemWriter<ExamResult> createWriter()
	{
		 MongoItemWriter<ExamResult> writer=new MongoItemWriter<ExamResult>();
		 writer.setCollection("sembrains");
		 writer.setTemplate(template);
		 return writer;
	}
	
	@Bean(name="step")
	public Step createStep(JobRepository repo,PlatformTransactionManager trans)
	{
		return new StepBuilder("step",repo).<ExamResult,ExamResult>chunk(10, trans).reader(createReader()).processor(processor).writer(createWriter()).build();
	}

	
	@Bean(name="ExamResultjob")
	public Job createJob(JobRepository repo,Step step)
	{
		
		return new JobBuilder("ExamResultjob",repo).incrementer(new RunIdIncrementer()).listener(listener).start(step).build();
	}

	
}

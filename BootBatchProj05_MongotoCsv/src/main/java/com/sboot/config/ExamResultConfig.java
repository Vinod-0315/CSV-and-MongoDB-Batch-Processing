package com.sboot.config;



import java.util.Collections;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.MongoCursorItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.PlatformTransactionManager;

import com.mongodb.client.model.Sorts;
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
	
	
	@Bean(name="Mongoreader")
	public MongoCursorItemReader<ExamResult> createReader()
	{
		   MongoCursorItemReader<ExamResult> reader= new  MongoCursorItemReader<ExamResult>();
		   reader.setTemplate(template);
		   reader.setCollection("superbrains");
		   reader.setQuery(Query.query(Criteria.where("percentage").gte(90.00)));
		   reader.setTargetType(ExamResult.class);
		   reader.setSort(Collections.singletonMap("_id", Sort.Direction.ASC));
		   reader.setBatchSize(20);
		   return reader;
	}
	
	 
	@Bean(name = "Mongowriter") // writer name as required
	public FlatFileItemWriter<ExamResult> writer() {
	    return new FlatFileItemWriterBuilder<ExamResult>()
	            .name("file-reader")
	            .resource(new FileSystemResource("superbrains.csv"))
	            .delimited()
	            .delimiter(",")
	            .names("id", "dob", "percentage", "semester", "gender", "email", "name") // match your class fields
	            //.headerCallback(writer -> writer.write("id,dob,percentage,semester,gender,email,name"))
	            .build();
	}

	
	@Primary
	@Bean(name="Mongostep")
	public Step createStep(JobRepository repo,PlatformTransactionManager trans)
	{
		return new StepBuilder("Mongostep",repo).<ExamResult,ExamResult>chunk(10, trans).reader(createReader()).processor(processor).writer(writer()).build();
	}

	
	@Bean(name="Mongojob")
	public Job createJob1(JobRepository repo,Step step)
	{
		
		return new JobBuilder("Mongojob",repo).incrementer(new RunIdIncrementer()).listener(listener).start(step).build();
	}

	
}

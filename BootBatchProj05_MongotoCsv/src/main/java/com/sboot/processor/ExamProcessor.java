package com.sboot.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;


import com.sboot.entity.ExamResult;

@Component("examresultProcessor")
public class ExamProcessor implements ItemProcessor<ExamResult, ExamResult> {

	@Override
	public ExamResult process(ExamResult item) throws Exception {
		if(item.getGender().equalsIgnoreCase("Female"))
		{
			return item;
		}
		return null;
	}

}

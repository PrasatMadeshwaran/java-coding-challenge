package com.crewmeister.cmcodingchallenge.commons.fileprocessor;

import com.crewmeister.cmcodingchallenge.data.entity.CurrencyDailyRate;
import com.crewmeister.cmcodingchallenge.exception.FileParsingException;

import java.sql.Date;
import java.util.List;

public interface CurrencyRateFileParser {

	public List<CurrencyDailyRate> parseXml(String fileName) throws FileParsingException;
	
	public List<CurrencyDailyRate> parseXml(String fileName, Date filterDate) throws FileParsingException;

	public Boolean verifyFile(String fileName, Date filterDate) throws FileParsingException;
}

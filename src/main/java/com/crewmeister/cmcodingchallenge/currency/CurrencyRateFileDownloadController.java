package com.crewmeister.cmcodingchallenge.currency;

import com.crewmeister.cmcodingchallenge.commons.fileprocessor.CurrencyRateFileDownloader;
import com.crewmeister.cmcodingchallenge.commons.fileprocessor.CurrencyRateFileParser;
import com.crewmeister.cmcodingchallenge.commons.utility.CurrencyConstant;
import com.crewmeister.cmcodingchallenge.commons.utility.CurrencyDailyRateUtiliity;
import com.crewmeister.cmcodingchallenge.data.entity.CurrencyDailyRate;
import com.crewmeister.cmcodingchallenge.data.repository.StatusRepository;
import com.crewmeister.cmcodingchallenge.data.service.DailyCurrencyRateService;
import com.crewmeister.cmcodingchallenge.exception.RateFileNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * CurrencyRateFileDownloadController to download euro rates against currency and storing in
 * files, process and persists.
 */
@RestController
@RequestMapping("/api")
@ApiIgnore
public class CurrencyRateFileDownloadController {

	private static final Logger LOGGER = LogManager.getLogger(CurrencyRateFileDownloadController.class.getName());

	@Autowired
	private CurrencyRateFileDownloader currencyRateFileDownloader;

	@Autowired
	private CurrencyRateFileParser dailyRateXmlParser;

	@Autowired
	private DailyCurrencyRateService dailyCurrencyRateService;

	@Autowired
	private StatusRepository statusRepository;

	/**
	 * Service for uploading daily Rate in to App.
	 * 
	 * @param fileName Input file name to download and process
	 * 
	 */
	@GetMapping("/file/{fileName}")
	public ResponseEntity<String> getFile(@PathVariable String fileName) {
		LOGGER.info("Downloading File {}." + fileName);
		Integer statusCode = currencyRateFileDownloader.downloadfile(fileName);

		if (200 == statusCode) {
			try {
				List<Object[]> currencyStatus = dailyCurrencyRateService.findMaximumRateDateByCurrency();
				Boolean isRecordsUpdated = false;
				Date lastRateEntryDate = null;
				Date currentDate = null;
				String processingCurrency = null;
				for (Object[] arr : currencyStatus) {
					if (fileName.contains(String.valueOf(arr[0]))) {
						processingCurrency = String.valueOf(arr[0]);
						LOGGER.info("processingCurrency : " + processingCurrency);
						lastRateEntryDate = CurrencyDailyRateUtiliity.getDateValue(String.valueOf(arr[1]));
						LOGGER.info("lastRateEntryDate : " + lastRateEntryDate);
						currentDate = CurrencyDailyRateUtiliity.getLastWorkingDate(CurrencyDailyRateUtiliity.getCurrentDate());
						isRecordsUpdated = (lastRateEntryDate.toLocalDate().compareTo(currentDate.toLocalDate()) == 0);
						break;
					}
				}

				List<CurrencyDailyRate> currencyDailyRateList = new ArrayList<>();
				if (lastRateEntryDate == null) {
					currencyDailyRateList = dailyRateXmlParser.parseXml(fileName);
				} else if (!isRecordsUpdated) {
					while (lastRateEntryDate.compareTo(currentDate) != 0) {
						lastRateEntryDate = CurrencyDailyRateUtiliity.addDays(lastRateEntryDate, 1);
						List<CurrencyDailyRate> fetchedRateList = dailyRateXmlParser.parseXml(fileName, lastRateEntryDate);
						currencyDailyRateList.addAll(fetchedRateList);
					}
				}

				if (!currencyDailyRateList.isEmpty()) {
					List<CurrencyDailyRate> savedRecordList = dailyCurrencyRateService.saveAllRates(currencyDailyRateList);
					statusRepository.updateStatus(savedRecordList.get(0).getSourceCurrency(),
							CurrencyConstant.RATE_PROCESSING_STATUS_READY);
					LOGGER.info("Total record processed size : " + savedRecordList.size());
				} else {
					statusRepository.updateStatus(processingCurrency, CurrencyConstant.RATE_PROCESSING_STATUS_READY);
					LOGGER.info("Data base is upto dated");
				}

			} catch (Exception exception) {
				LOGGER.error("ERORO" + exception);
				LOGGER.error("ERORO" + exception.getLocalizedMessage());

				LOGGER.error("Unable to read File {}." + fileName, exception);
				//throw new RateFileNotFoundException("Unable to read File " + fileName, exception);
			}
		} else {

			LOGGER.error("Unable to download File {}." + fileName);
			throw new RateFileNotFoundException("Unable to download File " + fileName);
		}

		return new ResponseEntity<String>(fileName + " downloaded successfully", HttpStatus.OK);
	}

	@GetMapping("/status")
	public ResponseEntity<Map<String, String>> getRateProcessingStatus() {
		return new ResponseEntity<Map<String, String>>(statusRepository.getAllStatus(), HttpStatus.OK);
	}

}

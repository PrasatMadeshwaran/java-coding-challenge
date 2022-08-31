package com.crewmeister.cmcodingchallenge.commons.utility;

public final class CurrencyConstant {

	private CurrencyConstant() {

	}

	public static final String COMMA = ",";
	public static final String API_DOC_CURRENCIES_VALUE = "Get List of Available Currencies";
	public static final String API_DOC_CURRENCY_TAG = "currencies";
	public static final String API_DOC_ALL_RATES_VALUE = "Get all EUR-FX exchange rates at all available dates";
	public static final String API_DOC_ALL_RATES_TAG = "rates";
	public static final String API_DOC_RATES_BY_DATE_VALUE ="Get the EUR-FX exchange rate at particular day";
	public static final String API_DOC_RATES_BY_DATE_TAG ="rates";
	public static final String API_DOC_CONVERT_CURRENCY_VALUE ="Get a foreign exchange amount for a given currency converted to EUR on a particular day";
	public static final String API_DOC_CONVERT_CURRENCY_TAG ="convertcurrency";

	public static final String API_DOC_NOT_AUTH = "Not Authorized!";
	public static final String API_DOC_FORBIDDEN = "Forbidden!";
	public static final String API_DOC_NOT_FOUND = "Not Found!";
	public static final String API_DOC_SUCCESS = "Success|OK";
	
	public static final String SERVER_ERROR_RESPONSE ="Server Error";
	public static final String RECORD_NOT_FOUND__RESPONSE ="Record Not Found";
	
	public static final String DEFAULT_RATE_SORT_FIELD = "id";
	public static final String XML_ID_ATTRIBUTE = "id";
	public static final String XML_VALUE_ATTRIBUTE = "value";
	public static final String XML_STANDARD_CURRENCY_PATH = "//generic:SeriesKey/generic:Value[@id='BBK_STD_CURRENCY']/@value";
	public static final String XML_FX_CURRENCY_PATH = "//generic:SeriesKey/generic:Value[@id='BBK_ERX_PARTNER_CURRENCY']/@value";
	public static final String XML_FILTER_RATE_WITH_DATE = "//generic:Series/generic:Obs[./generic:ObsDimension[@value='%s']]";
	public static final String XML_GET_PREPARED_DATE = "//message:Header/message:Prepared/text()";

	public static final String XML_ROOT_NODE= "generic:Obs";
	public static final String XML_RATE_DATE_TAG="generic:ObsDimension";
	public static final String XML_EXCHANGE_RATE_TAG="generic:ObsValue";
	public static final String XML_ATTRIBUTE_ROOT_TAG="generic:Attributes";
	public static final String XML_VALUE_ROOT_TAG="generic:Value";

	
	public static final String RATE_FILE_EXTENSION = ".xml";
	public static final String RATE_FILE_STATUS_ELEMENT ="OBS_STATUS";
	public static final String RATE_FILE_DIFF_ELEMENT ="BBK_DIFF";
	public static final String RATE_NONHOLIDAY_FLAG = "N";
	
	public static final String DEFAULT_FX_RATE = "0.0";
	public static final int ZERO = 0;
	
	public static final String RESPONSE_NO_RECORD_FOUND= "No rates found";
	public static final String REQUEST_VALIDATION_CURRENCY_MISSING = "Currency code missing!";
	public static final String REQUEST_INVALID_CURRENCY = "Invalid currency code!";

	public static final String REQUEST_VALIDATION_INVALID_AMOUNT = "Amount must be greater than or equal to 1";
	public static final String REQUEST_VALIDATION_CURRENCY_INVALID = "Currency code missing";
	public static final String REQUEST_VALIDATION_INVALID_SORT = "Invalid sort type";
	
	public static final String RESPONSE_NO_RECORD_FOUND_FOR_DATE= "No rates found for date ";
	public static final String RESPONSE_CURRENCY = " currency  ";
	public static final String RESPONSE_NO_RECORD_FOR_HOLIDAY=  "No rates available for Holiday ";
	
	public static final String TIMESTAMP = "timestamp";
	public static final String TIME_ZONE = "GMT+1";
	public static final String MESSAGE = "message";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	public static final String  RATE_PROCESSOR_USER = "DAILY_RATE_PROCESSOR";
	
	public static final String  RATE_PROCESSING_STATUS_LOADING = "LOADING";
	public static final String  RATE_PROCESSING_STATUS_READY = "READY";


}

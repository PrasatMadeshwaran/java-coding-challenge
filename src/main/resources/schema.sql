DROP TABLE IF EXISTS DAILY_RATE;

CREATE TABLE DAILY_RATE (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  std_currency VARCHAR(3) NOT NULL,
  erx_partner_currency VARCHAR(3) NOT NULL,
  rate_date DATE NOT NULL,
  obs_status CHAR(1),
  exchange_rate DECIMAL(20, 4),
  bbk_diff DECIMAL(20, 4),
  updated_timestamp TIMESTAMP(6) NOT NULL,
  updated_by VARCHAR(50) NOT NULL
);


COMMENT ON COLUMN DAILY_RATE.id
  IS 'Auto generated Identity column. Primary key';

COMMENT ON COLUMN DAILY_RATE.std_currency
  IS 'FX exchange currency column';
  
COMMENT ON COLUMN DAILY_RATE.erx_partner_currency
  IS 'Source partner currency <EUR> column';
  
COMMENT ON COLUMN DAILY_RATE.rate_date
  IS 'Daily Rate date column';
  
COMMENT ON COLUMN DAILY_RATE.exchange_rate
  IS 'Currency exchange rate column';
  
COMMENT ON COLUMN DAILY_RATE.obs_status
  IS 'Status column for Holidays, Values K represents holiday. On holiday exchange_rate will not be available';
  
COMMENT ON COLUMN DAILY_RATE.bbk_diff
  IS 'Daily exchange rate difference. On holiday exchange rate difference will not be available';
  
COMMENT ON COLUMN DAILY_RATE.updated_by
  IS 'Record updated user details for Auditing purpose';
  
COMMENT ON COLUMN DAILY_RATE.updated_timestamp
  IS 'Record updated time stamp for Auditing purpose';
CREATE TABLE `t_trading_companies` (
  `CompanyID` varchar(5) NOT NULL,
  `Countries` varchar(160) DEFAULT NULL,
  `Budget` varchar(50) DEFAULT NULL,
  `Bid` varchar(50) DEFAULT NULL,
  `Category` varchar(160) DEFAULT NULL,
  PRIMARY KEY (`CompanyID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `t_trading_companies` (`CompanyID`, `Countries`, `Budget`, `Bid`, `Category`)
VALUES
	('C1', 'US, FR', '1$', '10 cent', 'Automobile, Finance'),
	('C2', 'IN, US', '2$', '30cent', 'Finance, IT'),
	('C3', 'US, RU', '3$', '5 cent', 'IT, Automobile');

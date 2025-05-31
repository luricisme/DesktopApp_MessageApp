CREATE TABLE `chat_application`.`user` (
  `UserID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `UserName` varchar(255) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `chat_application`.`user_account` (
  `UserID` int(10) unsigned NOT NULL,
  `UserName` varchar(255) DEFAULT NULL,
  `Gender` char(1) NOT NULL DEFAULT '',
  `Image` longblob,
  `ImageString` varchar(255) DEFAULT '',
  `Status` char(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`UserID`),
  CONSTRAINT `user_account_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
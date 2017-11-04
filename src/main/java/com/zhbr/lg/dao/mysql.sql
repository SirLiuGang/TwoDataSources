--mysql
DROP TABLE IF EXISTS `exercises`;
CREATE TABLE `exercises`  (
  `GUID` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `SSFL` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `SSWD` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `txfl` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `tm` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `xxa` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `xxb` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `xxc` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `xxd` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `da` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `js` text CHARACTER SET utf8 COLLATE utf8_general_ci
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
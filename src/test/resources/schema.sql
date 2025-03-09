-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: crm_dentista
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

DROP TABLE IF EXISTS `usr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usr` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ativo` bit(1) NOT NULL,
  `dataFim` datetime(6) DEFAULT NULL,
  `dataInicio` datetime(6) NOT NULL,
  `email` varchar(45) NOT NULL,
  `papel` tinyint NOT NULL,
  `senha` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);
--
-- Table structure for table `clt`
--

DROP TABLE IF EXISTS `clt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clt` (
  `cpf` varchar(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `rg` varchar(12) DEFAULT NULL,
  `sexo` tinyint NOT NULL,
  `telefone` varchar(11) NOT NULL,
  `USR_id` bigint DEFAULT NULL,
  PRIMARY KEY (`cpf`),
  UNIQUE KEY `UKklmeite66vwb7dedysg3iq553` (`USR_id`),
  CONSTRAINT `FKdh81mf81ytnt769qk7plj23e7` FOREIGN KEY (`USR_id`) REFERENCES `usr` (`id`)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `clt_ctt`
--

DROP TABLE IF EXISTS `clt_ctt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clt_ctt` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `contato` varchar(45) NOT NULL,
  `tipo` varchar(45) NOT NULL,
  `CLT_cpf` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKoym4plybk59w9qd5k2y6srf1t` (`CLT_cpf`),
  CONSTRAINT `FKoym4plybk59w9qd5k2y6srf1t` FOREIGN KEY (`CLT_cpf`) REFERENCES `clt` (`cpf`)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dtr`
--

DROP TABLE IF EXISTS `dtr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dtr` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cro` varchar(6) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `USR_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK40imgg0ivruoobiv3ftomwmo6` (`USR_id`),
  CONSTRAINT `FKs2ekc3wlriqktaa253o9gphjm` FOREIGN KEY (`USR_id`) REFERENCES `usr` (`id`)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cst`
--

DROP TABLE IF EXISTS `cst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cst` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dataFim` datetime(6) NOT NULL,
  `dataInicio` datetime(6) NOT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `status` varchar(10) NOT NULL,
  `tipo` varchar(30) NOT NULL,
  `valor` double NOT NULL,
  `CLT_cpf` varchar(11) DEFAULT NULL,
  `DTR_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl2cnev4a5i1m39c7a9dhs90m9` (`CLT_cpf`),
  KEY `FKjmp7d589s8ho5ori1h2ixxhr0` (`DTR_id`),
  CONSTRAINT `FKjmp7d589s8ho5ori1h2ixxhr0` FOREIGN KEY (`DTR_id`) REFERENCES `dtr` (`id`),
  CONSTRAINT `FKl2cnev4a5i1m39c7a9dhs90m9` FOREIGN KEY (`CLT_cpf`) REFERENCES `clt` (`cpf`)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hst_cst`
--

DROP TABLE IF EXISTS `hst_cst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hst_cst` (
  `CST_id` bigint NOT NULL,
  `timestampAlt` datetime(6) NOT NULL,
  `dataFim` datetime(6) NOT NULL,
  `dataInicio` datetime(6) NOT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `status` varchar(10) NOT NULL,
  `tipo` varchar(30) NOT NULL,
  PRIMARY KEY (`CST_id`,`timestampAlt`),
  CONSTRAINT `FKmabujrxqwtkeyuae7mpy76wrj` FOREIGN KEY (`CST_id`) REFERENCES `cst` (`id`)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hst_dtr`
--

DROP TABLE IF EXISTS `hst_dtr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hst_dtr` (
  `DTR_id` bigint NOT NULL,
  `timestamp_alt` varbinary(255) NOT NULL,
  `camposAlt` varchar(45) NOT NULL,
  `operacao` varchar(255) NOT NULL,
  PRIMARY KEY (`DTR_id`,`timestamp_alt`),
  CONSTRAINT `FKsku63mrenudq914lay9fx3iib` FOREIGN KEY (`DTR_id`) REFERENCES `dtr` (`id`)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hst_usr`
--

DROP TABLE IF EXISTS `hst_usr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hst_usr` (
  `timestamp_alt` datetime(6) NOT NULL,
  `USR_id` bigint NOT NULL,
  `camposAlt` varchar(45) DEFAULT NULL,
  `operacao` varchar(255) NOT NULL,
  PRIMARY KEY (`timestamp_alt`,`USR_id`),
  KEY `FK9sdk8bj69fg24vprcpo0m4asu` (`USR_id`),
  CONSTRAINT `FK9sdk8bj69fg24vprcpo0m4asu` FOREIGN KEY (`USR_id`) REFERENCES `usr` (`id`)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `prc_sd`
--

DROP TABLE IF EXISTS `prc_sd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prc_sd` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `adicional` bit(1) NOT NULL,
  `nome` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
);

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usr`
--
--
-- Table structure for table `clt_prc`
--

DROP TABLE IF EXISTS `clt_prc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clt_prc` (
  `resposta_adicional` varchar(255) DEFAULT NULL,
  `CLT_cpf` varchar(11) NOT NULL,
  `PRC_id` bigint NOT NULL,
  PRIMARY KEY (`CLT_cpf`,`PRC_id`),
  KEY `FK4nn4luy6lkufqn0vcoc91kyql` (`PRC_id`),
  CONSTRAINT `FK4nn4luy6lkufqn0vcoc91kyql` FOREIGN KEY (`PRC_id`) REFERENCES `prc_sd` (`id`),
  CONSTRAINT `FKofpn58i9nn2e6d1j09354ifj9` FOREIGN KEY (`CLT_cpf`) REFERENCES `clt` (`cpf`)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-09 18:17:11
INSERT INTO prc_sd (adicional, nome) VALUES (0, 'Pressao Alta'),(1 , 'Sensibilidade');
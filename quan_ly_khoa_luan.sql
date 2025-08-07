CREATE DATABASE  IF NOT EXISTS `quan_ly_khoa_luan` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `quan_ly_khoa_luan`;
-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: quan_ly_khoa_luan
-- ------------------------------------------------------
-- Server version	8.0.40

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

--
-- Table structure for table `diem`
--

DROP TABLE IF EXISTS `diem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diem` (
  `id` int NOT NULL AUTO_INCREMENT,
  `diem` double DEFAULT NULL,
  `thanhvien_id` int DEFAULT NULL,
  `khoa_luan_id` int DEFAULT NULL,
  `tieu_chi_id` int DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `thanhvien_id` (`thanhvien_id`),
  KEY `khoa_luan_id` (`khoa_luan_id`),
  KEY `tieu_chi_id` (`tieu_chi_id`),
  CONSTRAINT `diem_ibfk_1` FOREIGN KEY (`thanhvien_id`) REFERENCES `thanh_vien` (`id`),
  CONSTRAINT `diem_ibfk_2` FOREIGN KEY (`khoa_luan_id`) REFERENCES `khoa_luan` (`id`),
  CONSTRAINT `diem_ibfk_3` FOREIGN KEY (`tieu_chi_id`) REFERENCES `tieu_chi` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diem`
--

LOCK TABLES `diem` WRITE;
/*!40000 ALTER TABLE `diem` DISABLE KEYS */;
INSERT INTO `diem` VALUES (1,2.5,1,1,1,'2025-08-15 08:00:00'),(2,3.5,1,1,2,'2025-08-15 08:00:00'),(3,2,1,1,3,'2025-08-15 08:00:00'),(4,3,2,1,1,'2025-08-15 08:00:00'),(5,3.5,2,1,2,'2025-08-15 08:00:00'),(6,2.5,2,1,3,'2025-08-15 08:00:00'),(7,2,4,2,1,'2025-08-15 08:00:00'),(8,3,4,2,2,'2025-08-15 08:00:00'),(9,2.5,4,2,3,'2025-08-15 08:00:00');
/*!40000 ALTER TABLE `diem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `giang_vien`
--

DROP TABLE IF EXISTS `giang_vien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `giang_vien` (
  `id` int NOT NULL,
  `hoc_ham` char(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `giang_vien_ibfk_1` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `giang_vien`
--

LOCK TABLES `giang_vien` WRITE;
/*!40000 ALTER TABLE `giang_vien` DISABLE KEYS */;
INSERT INTO `giang_vien` VALUES (3,'Thạc sĩ'),(4,'Tiến sĩ'),(5,'Phó Giáo sư'),(6,'Giáo sư'),(7,'Tiến sĩ'),(11,'Thạc sĩ'), (12,'Phó Giáo sư');
/*!40000 ALTER TABLE `giang_vien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hoi_dong`
--

DROP TABLE IF EXISTS `hoi_dong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hoi_dong` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ngay_bao_ve` datetime DEFAULT NULL,
  `noi_dung_bao_ve` char(255) DEFAULT NULL,
  `da_khoa` TINYINT(1) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoi_dong`
--

LOCK TABLES `hoi_dong` WRITE;
/*!40000 ALTER TABLE `hoi_dong` DISABLE KEYS */;
INSERT INTO `hoi_dong` VALUES (1,'2025-08-01 08:00:00','Bảo vệ khóa luận đợt 1', 0),(2,'2025-08-15 08:00:00','Bảo vệ khóa luận đợt 2', 0);
/*!40000 ALTER TABLE `hoi_dong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khoa_luan`
--

DROP TABLE IF EXISTS `khoa_luan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khoa_luan` (
  `id` int NOT NULL AUTO_INCREMENT,
  `chu_de` char(255) DEFAULT NULL,
  `sinh_vien_id` int DEFAULT NULL,
  `gvhd1` int DEFAULT NULL,
  `gvhd2` int DEFAULT NULL,
  `gvpb` int DEFAULT NULL,
  `hoidong_id` int DEFAULT NULL,
  `ngay_tao` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sinh_vien_id` (`sinh_vien_id`),
  KEY `gvhd1` (`gvhd1`),
  KEY `gvhd2` (`gvhd2`),
  KEY `gvpb` (`gvpb`),
  KEY `hoidong_id` (`hoidong_id`),
  CONSTRAINT `khoa_luan_ibfk_1` FOREIGN KEY (`sinh_vien_id`) REFERENCES `sinh_vien` (`id`),
  CONSTRAINT `khoa_luan_ibfk_2` FOREIGN KEY (`gvhd1`) REFERENCES `giang_vien` (`id`),
  CONSTRAINT `khoa_luan_ibfk_3` FOREIGN KEY (`gvhd2`) REFERENCES `giang_vien` (`id`),
  CONSTRAINT `khoa_luan_ibfk_4` FOREIGN KEY (`gvpb`) REFERENCES `giang_vien` (`id`),
  CONSTRAINT `khoa_luan_ibfk_5` FOREIGN KEY (`hoidong_id`) REFERENCES `hoi_dong` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khoa_luan`
--

LOCK TABLES `khoa_luan` WRITE;
/*!40000 ALTER TABLE `khoa_luan` DISABLE KEYS */;
INSERT INTO `khoa_luan` VALUES (1,'Ứng dụng AI trong y tế',9,3,4,5,1, '2025-06-01 09:00:00'),(2,'Hệ thống nhận diện khuôn mặt',10,4,3,5,2, '2025-06-15 14:00:00');
/*!40000 ALTER TABLE `khoa_luan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sinh_vien`
--

DROP TABLE IF EXISTS `sinh_vien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sinh_vien` (
  `id` int NOT NULL,
  `khoa` char(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `sinh_vien_ibfk_1` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sinh_vien`
--

LOCK TABLES `sinh_vien` WRITE;
/*!40000 ALTER TABLE `sinh_vien` DISABLE KEYS */;
INSERT INTO `sinh_vien` VALUES (9,'Công nghệ thông tin'),(10,'Khoa học máy tính'), (13,'Khoa học máy tính'),(14,'Khoa học máy tính');
/*!40000 ALTER TABLE `sinh_vien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thanh_vien`
--

DROP TABLE IF EXISTS `thanh_vien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thanh_vien` (
  `id` int NOT NULL AUTO_INCREMENT,
  `gv_id` int DEFAULT NULL,
  `hoi_dong_id` int DEFAULT NULL,
  `vai_tro` enum('chu_tich','thu_ky','phan_bien','uy_vien','thanh_vien') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `gv_id` (`gv_id`, `hoi_dong_id`),
  KEY `hoi_dong_id` (`hoi_dong_id`),
  CONSTRAINT `thanh_vien_ibfk_1` FOREIGN KEY (`gv_id`) REFERENCES `giang_vien` (`id`),
  CONSTRAINT `thanh_vien_ibfk_2` FOREIGN KEY (`hoi_dong_id`) REFERENCES `hoi_dong` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thanh_vien`
--

LOCK TABLES `thanh_vien` WRITE;
/*!40000 ALTER TABLE `thanh_vien` DISABLE KEYS */;
INSERT INTO `thanh_vien` VALUES (1,3,1,'chu_tich'),(2,4,1,'thu_ky'),(3,5,1,'phan_bien'),(4,6,2,'chu_tich'),(5,7,2,'uy_vien');
/*!40000 ALTER TABLE `thanh_vien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tieu_chi`
--

DROP TABLE IF EXISTS `tieu_chi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tieu_chi` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ten_tc` char(100) DEFAULT NULL,
  `noi_dung` char(255) DEFAULT NULL,
  `diem_toi_da` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tieu_chi`
--

LOCK TABLES `tieu_chi` WRITE;
/*!40000 ALTER TABLE `tieu_chi` DISABLE KEYS */;
INSERT INTO `tieu_chi` VALUES (1,'Tính mới','Ý tưởng và giải pháp sáng tạo',3),(2,'Kỹ thuật','Áp dụng công nghệ và giải pháp kỹ thuật',4),(3,'Trình bày','Trình bày, slide, trả lời câu hỏi',3);
/*!40000 ALTER TABLE `tieu_chi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` char(50) NOT NULL,
  `password` char(100) NOT NULL,
  `email` char(100) NOT NULL,
  `role` enum('admin','giaovu','giangvien','sinhvien') NOT NULL,
  `avatar` char(255) DEFAULT NULL,
  `fullname` char(100) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin1','$2a$10$OEuVx5ol.wrvnC33MWA9x.s6eO5uCk6e7XyyCwefv8FVZlxox2ynW','admin1@univ.edu','admin',NULL,'Quản trị viên 1',1),
(2,'giaovu1','123','giaovu1@univ.edu','giaovu',NULL,'Giáo vụ 1',1),
(3,'gv01','123','gv01@univ.edu','giangvien',NULL,'ThS. Nguyễn Văn A',1),
(4,'gv02','123','gv02@univ.edu','giangvien',NULL,'TS. Trần Thị B',1),
(5,'gv03','123','gv03@univ.edu','giangvien',NULL,'PGS. Lê Văn C',1),
(6,'gv04','123','gv04@univ.edu','giangvien',NULL,'GS. Phạm Văn D',1),
(7,'gv05','123','gv05@univ.edu','giangvien',NULL,'TS. Lương Thị E',1),
(8,'gv06','123','gv06@univ.edu','giangvien',NULL,'ThS. Tống Thị M',1),
(9,'sv01','123','sv01@univ.edu','sinhvien',NULL,'Sinh viên Dương',1),
(10,'sv02','123','sv02@univ.edu','sinhvien',NULL,'Sinh viên Phương',1),
(11,'gv07','123','2251050024hao@ou.edu.vn','giangvien',NULL,'ThS. Lê Thị Lam',1),
(12,'gv08','123','nguyenvanbuu1104@gmail.com','giangvien',NULL,'ThS. Nguyễn Văn Thanh',1),
(13,'sv03','123','sv03@univ.edu','sinhvien',NULL,'Sinh viên Hiếu',1),
(14,'sv04','123','testerprtg264@gmail.com','sinhvien',NULL,'Sinh viên Hào',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-18 16:07:47

--
-- Table structure for table `thong_bao`
--

DROP TABLE IF EXISTS `thong_bao`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thong_bao` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nguoi_nhan_id` INT NOT NULL,
  `loai_thong_bao` ENUM('email', 'sms') NOT NULL,
  `tieu_de` VARCHAR(255) NOT NULL,
  `noi_dung` TEXT NOT NULL,
  `trang_thai` ENUM('chua_doc', 'da_doc') DEFAULT 'chua_doc',
  `ngay_gui` DATETIME DEFAULT CURRENT_TIMESTAMP,
  
  -- Các cột liên kết đến các đối tượng nghiệp vụ
  `khoa_luan_id` INT,
  
  PRIMARY KEY (`id`),
  KEY `fk_thong_bao_user` (`nguoi_nhan_id`),
  CONSTRAINT `fk_thong_bao_user` FOREIGN KEY (`nguoi_nhan_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  
  -- Thêm khóa ngoại tới bảng `khoa_luan`
  KEY `fk_thong_bao_khoa_luan` (`khoa_luan_id`),
  CONSTRAINT `fk_thong_bao_khoa_luan` FOREIGN KEY (`khoa_luan_id`) REFERENCES `khoa_luan` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

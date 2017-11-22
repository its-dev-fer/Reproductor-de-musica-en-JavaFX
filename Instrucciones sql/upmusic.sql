-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Nov 22, 2017 at 03:57 AM
-- Server version: 5.7.19
-- PHP Version: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `upmusic`
--

-- --------------------------------------------------------

--
-- Table structure for table `canciones`
--

DROP TABLE IF EXISTS `canciones`;
CREATE TABLE IF NOT EXISTS `canciones` (
  `ID_Cancion` int(11) NOT NULL AUTO_INCREMENT,
  `ID_Genero` int(11) NOT NULL,
  `ID_Playlist` int(11) NOT NULL,
  `Caratula` varchar(50) COLLATE utf8_spanish_ci NOT NULL COMMENT 'Ruta de la caratula',
  `Titulo` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `Artista` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `Album` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `Descripcion` varchar(50) COLLATE utf8_spanish_ci NOT NULL COMMENT 'Nombre del genero',
  `ruta` varchar(75) COLLATE utf8_spanish_ci NOT NULL COMMENT 'ruta donde se encuentra alojada la cancion',
  `background` varchar(75) COLLATE utf8_spanish_ci NOT NULL COMMENT 'Ruta de la imagen con blur',
  PRIMARY KEY (`ID_Cancion`),
  KEY `ID_Genero` (`ID_Genero`),
  KEY `ID_Playlist` (`ID_Playlist`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Dumping data for table `canciones`
--

INSERT INTO `canciones` (`ID_Cancion`, `ID_Genero`, `ID_Playlist`, `Caratula`, `Titulo`, `Artista`, `Album`, `Descripcion`, `ruta`, `background`) VALUES
(4, 2, 1, 'C:\\Users\\fer_i\\Music\\Caratulas\\The Strokes.jpg', 'Reptilia', 'The Strokes', 'Room on Fire', 'Rock', 'C:\\Users\\fer_i\\Music\\Reptilia - The Strokes [500kbps_M4A].m4a', 'C:\\Users\\fer_i\\Music\\Blurry covers\\The Strokes.jpg'),
(5, 3, 1, 'C:\\Users\\fer_i\\Music\\Caratulas\\Black Eyed Peas.jpg', 'I Gotta Feelling', 'The Black Eyed Peas', 'I Gotta Feeling (Single)', 'Electrónica', 'C:\\Users\\fer_i\\Music\\I Gotta Feeling - The Black Eyed [258kbps_M4A].m4a', 'C:\\Users\\fer_i\\Music\\Blurry covers\\Black Eyed Peas.jpg'),
(6, 2, 1, 'C:\\Users\\fer_i\\Music\\Caratulas\\Saves the Day.jpg', 'See You', 'Saves The Day', 'Unknow', 'Rock', 'C:\\Users\\fer_i\\Music\\Saves The Day - See You.mp3', 'C:\\Users\\fer_i\\Music\\Blurry covers\\Saves the Day.jpg'),
(7, 4, 1, 'C:\\Users\\fer_i\\Music\\Caratulas\\Michael Buble.jpg', 'You Don´t Know me', 'Michael Buble', 'It\'s Time', 'Jazz', 'C:\\Users\\fer_i\\Music\\You Don_t Know Me - Michael Buble [500kbps_M4A].m4a', 'C:\\Users\\fer_i\\Music\\Blurry covers\\Michael Buble.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `genero`
--

DROP TABLE IF EXISTS `genero`;
CREATE TABLE IF NOT EXISTS `genero` (
  `ID_Genero` int(32) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(50) COLLATE utf8_spanish_ci NOT NULL COMMENT 'Nombre del genero',
  PRIMARY KEY (`ID_Genero`),
  UNIQUE KEY `Descripcion` (`Descripcion`),
  KEY `Descripcion_2` (`Descripcion`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Dumping data for table `genero`
--

INSERT INTO `genero` (`ID_Genero`, `Descripcion`) VALUES
(3, 'Electrónica'),
(4, 'Jazz'),
(1, 'Pop'),
(2, 'Rock');

-- --------------------------------------------------------

--
-- Table structure for table `peticiones`
--

DROP TABLE IF EXISTS `peticiones`;
CREATE TABLE IF NOT EXISTS `peticiones` (
  `Id_peticion` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre_cancion` varchar(25) COLLATE utf8_spanish_ci NOT NULL COMMENT 'Nombre de la cancion',
  PRIMARY KEY (`Id_peticion`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Dumping data for table `peticiones`
--

INSERT INTO `peticiones` (`Id_peticion`, `Nombre_cancion`) VALUES
(1, 'dd'),
(2, 'Mind'),
(3, 'Mind'),
(4, 'null'),
(5, 'DROP TABLE;'),
(6, 'XD'),
(7, 'Before I Forget'),
(8, 'i gotta feeling'),
(9, 'ypu don´t know me');

-- --------------------------------------------------------

--
-- Table structure for table `playlist`
--

DROP TABLE IF EXISTS `playlist`;
CREATE TABLE IF NOT EXISTS `playlist` (
  `ID_Playlist` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID de la playlist',
  `ID_Usuario` int(11) NOT NULL,
  `Descripcion` varchar(50) COLLATE utf8_spanish_ci NOT NULL COMMENT 'Nombre de la playlist',
  PRIMARY KEY (`ID_Playlist`),
  KEY `ID_Usuario` (`ID_Usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Dumping data for table `playlist`
--

INSERT INTO `playlist` (`ID_Playlist`, `ID_Usuario`, `Descripcion`) VALUES
(1, 1, 'Playlist de Fernando');

-- --------------------------------------------------------

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE IF NOT EXISTS `usuarios` (
  `ID_Usuario` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID del usuario',
  `Nombre_usuario` varchar(50) COLLATE utf8_spanish_ci NOT NULL COMMENT 'Nombre del usuario',
  `Contrasenia` varchar(10) COLLATE utf8_spanish_ci NOT NULL COMMENT 'Password del usuario',
  `Correo` varchar(50) COLLATE utf8_spanish_ci NOT NULL COMMENT 'Correo electronico',
  `Premium` tinyint(1) NOT NULL COMMENT 'Sera true cuando el usuario sea premium',
  `ID_Playlist` int(11) DEFAULT NULL COMMENT 'Este id servira para enlazarla con sus propias playlists',
  PRIMARY KEY (`ID_Usuario`),
  UNIQUE KEY `Nombre_usuario` (`Nombre_usuario`),
  UNIQUE KEY `Correo` (`Correo`),
  UNIQUE KEY `Nombre_usuario_2` (`Nombre_usuario`,`Correo`),
  KEY `ID_Playlist` (`ID_Playlist`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Dumping data for table `usuarios`
--

INSERT INTO `usuarios` (`ID_Usuario`, `Nombre_usuario`, `Contrasenia`, `Correo`, `Premium`, `ID_Playlist`) VALUES
(1, 'IDS4B', 'IDS4B', '163189@ids.upchiapas.edu.mx', 1, NULL),
(2, 'Fernando', '163189', 'fer@fer.com', 0, NULL),
(3, 'Javier', '123456', 'javier@javier.com', 0, NULL),
(5, 'prueba1', 'prueba1', 'prueba1@pruebas.com', 0, NULL);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `canciones`
--
ALTER TABLE `canciones`
  ADD CONSTRAINT `canciones_ibfk_1` FOREIGN KEY (`ID_Playlist`) REFERENCES `playlist` (`ID_Playlist`) ON UPDATE CASCADE,
  ADD CONSTRAINT `canciones_ibfk_2` FOREIGN KEY (`ID_Genero`) REFERENCES `genero` (`ID_Genero`) ON UPDATE CASCADE;

--
-- Constraints for table `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `usuarios_ibfk_1` FOREIGN KEY (`ID_Playlist`) REFERENCES `playlist` (`ID_Playlist`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

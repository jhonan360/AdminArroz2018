-- phpMyAdmin SQL Dump
-- version 4.7.3
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 15-03-2018 a las 02:25:03
-- Versión del servidor: 5.7.19
-- Versión de PHP: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `admiarroz`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `bateria`
--

CREATE TABLE `bateria` (
  `idBateria` int(11) NOT NULL,
  `nombre` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `bateria`
--

INSERT INTO `bateria` (`idBateria`, `nombre`) VALUES
(1, 'A'),
(2, 'B');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `departamentos`
--

CREATE TABLE `departamentos` (
  `idDepartamento` int(11) NOT NULL COMMENT 'Llave primaria de la tabla departamentos, auto incremental ',
  `nombre` varchar(40) NOT NULL COMMENT 'Nombre del departamento'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `departamentos`
--

INSERT INTO `departamentos` (`idDepartamento`, `nombre`) VALUES
(1, 'Amazonas'),
(2, 'Antioquia'),
(3, 'Arauca'),
(4, 'Atlántico'),
(5, 'Bolívar'),
(6, 'Boyacá'),
(7, 'Caldas'),
(8, 'Caquetá'),
(9, 'Casanare'),
(10, 'Cauca'),
(11, 'Cesar'),
(12, 'Chocó'),
(13, 'Córdoba'),
(14, 'Cundinamarca'),
(15, 'Güainia'),
(16, 'Guaviare'),
(17, 'Huila'),
(18, 'La Guajira'),
(19, 'Magdalena'),
(20, 'Meta'),
(21, 'Nariño'),
(22, 'Norte de Santander'),
(23, 'Putumayo'),
(24, 'Quindo'),
(25, 'Risaralda'),
(26, 'San Andrés y Providencia'),
(27, 'Santander'),
(28, 'Sucre'),
(29, 'Tolima'),
(30, 'Valle del Cauca'),
(31, 'Vaupés'),
(32, 'Vichada');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalleliquidacion`
--

CREATE TABLE `detalleliquidacion` (
  `idDetalleLiquidacion` int(11) NOT NULL,
  `idTiquete` int(11) NOT NULL,
  `idLiquidaciones` int(11) DEFAULT NULL,
  `humedad` decimal(5,2) DEFAULT NULL,
  `impureza` decimal(5,2) DEFAULT NULL,
  `castigoHumedad` decimal(4,3) DEFAULT NULL,
  `castigoImpureza` decimal(4,3) DEFAULT NULL,
  `pesoCompra` decimal(7,2) DEFAULT NULL,
  `valorCarga` decimal(14,2) NOT NULL,
  `valorKilo` decimal(14,2) DEFAULT NULL,
  `valorTotal` decimal(14,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `detalleliquidacion`
--

INSERT INTO `detalleliquidacion` (`idDetalleLiquidacion`, `idTiquete`, `idLiquidaciones`, `humedad`, `impureza`, `castigoHumedad`, `castigoImpureza`, `pesoCompra`, `valorCarga`, `valorKilo`, `valorTotal`) VALUES
(1, 1, 3, '26.00', '3.00', '0.970', '1.000', '13709.00', '120000.00', '960.00', '13160640.00'),
(2, 8, 3, '20.00', '12.00', '1.000', '0.910', '3172.00', '120000.00', '960.00', '3045120.00'),
(3, 10, 4, '22.00', '3.00', '1.000', '1.000', '3875.00', '120000.00', '960.00', '3720000.00'),
(4, 12, 4, '27.00', '5.00', '0.960', '0.980', '1246.00', '120000.00', '960.00', '1196160.00'),
(5, 3, 5, '29.00', '5.00', '0.930', '0.980', '4758.00', '120000.00', '960.00', '4567680.00'),
(6, 4, 4, '2.40', '4.00', '1.000', '0.990', '450.00', '120000.00', '960.00', '432000.00'),
(7, 6, 4, '9.99', '10.00', '1.000', '0.930', '2757.00', '120000.00', '960.00', '2646720.00'),
(8, 17, 6, '30.00', '5.00', '0.920', '0.980', '1552.00', '170000.00', '1360.00', '2110720.00'),
(9, 2, NULL, NULL, NULL, NULL, NULL, NULL, '135000.00', NULL, NULL),
(10, 5, NULL, NULL, NULL, NULL, NULL, NULL, '120000.00', NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleado`
--

CREATE TABLE `empleado` (
  `idEmpleado` int(11) NOT NULL,
  `user` varchar(30) NOT NULL,
  `idMunicipio` int(11) NOT NULL,
  `ccEmpleado` varchar(15) NOT NULL,
  `nombres` varchar(45) NOT NULL,
  `apellidos` varchar(45) NOT NULL,
  `direccion` varchar(255) NOT NULL,
  `telefono` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `empleado`
--

INSERT INTO `empleado` (`idEmpleado`, `user`, `idMunicipio`, `ccEmpleado`, `nombres`, `apellidos`, `direccion`, `telefono`) VALUES
(1, 'x', 1033, '1106896645', 'Jhonan Smith', 'Vargas Herrán', 'Cra 29 # 11-95', '31338385186'),
(2, 'b', 5, '1105689625', 'Lizeth Fernanda', 'Ramirez Cortes', 'Manzana E casa 7 Barrio La Esperanza', '3223006069'),
(3, 'l', 639, '14232121', 'Javier Ivan', 'Arevalo', 'Cra 69 # 24', '3122453211'),
(4, 'g', 1021, '28710378', 'Beatriz ', 'Gonzalez', 'Espinal', '3138084236'),
(5, 'c', 1022, '65692470', 'Ana Delfina', 'Cortes Barrero', 'Espinal', '3142772941'),
(6, 'lizeth', 1021, '9313529', 'Lizeth ', 'Ramirez', 'sssss', '3108976543');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `entradas`
--

CREATE TABLE `entradas` (
  `idEntradas` int(11) NOT NULL,
  `idTiqueteVarios` int(11) NOT NULL,
  `cantidad` decimal(5,2) NOT NULL,
  `descripcion` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `entradas`
--

INSERT INTO `entradas` (`idEntradas`, `idTiqueteVarios`, `cantidad`, `descripcion`) VALUES
(1, 10, '12.00', 'asassa'),
(2, 14, '112.00', 'dccd'),
(3, 14, '22.00', 'fer'),
(4, 15, '12.00', 'Bultos'),
(5, 15, '23.00', 'Bultos de Arroz'),
(6, 16, '24.00', 'liso'),
(7, 17, '12.00', 'fdfds'),
(8, 17, '21.00', 'sdsf'),
(9, 21, '35.00', 'Bultos basura'),
(10, 21, '44.00', 'dfd'),
(11, 22, '23.00', 'sdsa'),
(12, 22, '33.00', 'RSC');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `etapa`
--

CREATE TABLE `etapa` (
  `idHistorialEtapa` int(11) NOT NULL,
  `idProcedimiento` int(11) NOT NULL,
  `etapa` enum('secamiento','seco') NOT NULL,
  `fecha` date NOT NULL,
  `hora` time NOT NULL,
  `humedad` decimal(5,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `funciones`
--

CREATE TABLE `funciones` (
  `idFunciones` int(11) NOT NULL,
  `funcion` varchar(255) NOT NULL,
  `detalle` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `funcionesprivilegios`
--

CREATE TABLE `funcionesprivilegios` (
  `idFuncionesPrivilegio` int(11) NOT NULL,
  `idFunciones` int(11) NOT NULL,
  `idPrivilegios` int(11) NOT NULL,
  `estado` enum('activo','inactivo') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `laboratorio`
--

CREATE TABLE `laboratorio` (
  `idLaboratorio` int(11) NOT NULL,
  `idTiquete` int(11) NOT NULL,
  `user` varchar(30) NOT NULL,
  `estado` enum('abierto','cerrado') NOT NULL,
  `fecha` datetime NOT NULL,
  `humedad` decimal(5,2) NOT NULL,
  `impureza` decimal(5,2) NOT NULL,
  `integralRes` decimal(5,2) NOT NULL,
  `cascarillaRes` decimal(5,2) NOT NULL,
  `blancoRes` decimal(5,2) NOT NULL,
  `partidoRes` decimal(5,2) NOT NULL,
  `enteroRes` decimal(5,2) NOT NULL,
  `yeso` decimal(5,2) DEFAULT NULL,
  `danado` decimal(5,2) DEFAULT NULL,
  `ip` decimal(5,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `liquidaciones`
--

CREATE TABLE `liquidaciones` (
  `idLiquidaciones` int(11) NOT NULL,
  `fecha` datetime NOT NULL,
  `humedadIdeal` decimal(5,2) NOT NULL,
  `impurezaIdeal` decimal(5,2) NOT NULL,
  `kilosNeto` decimal(7,2) NOT NULL,
  `kilosCompra` decimal(7,2) NOT NULL,
  `subTotal` decimal(14,2) NOT NULL,
  `fomArrocero` decimal(3,2) NOT NULL,
  `valorFomArrocero` decimal(14,2) NOT NULL,
  `impuesto` enum('retefuente','comision bolsa') NOT NULL,
  `porcenImpuesto` decimal(9,6) NOT NULL,
  `valorImpuesto` decimal(14,2) NOT NULL,
  `descuentoAnticipo` decimal(14,2) NOT NULL,
  `estado` enum('en proceso','aprobado') NOT NULL,
  `netoPagar` decimal(14,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `liquidaciones`
--

INSERT INTO `liquidaciones` (`idLiquidaciones`, `fecha`, `humedadIdeal`, `impurezaIdeal`, `kilosNeto`, `kilosCompra`, `subTotal`, `fomArrocero`, `valorFomArrocero`, `impuesto`, `porcenImpuesto`, `valorImpuesto`, `descuentoAnticipo`, `estado`, `netoPagar`) VALUES
(3, '2018-02-08 11:40:19', '24.00', '3.00', '17575.00', '16881.00', '16205760.00', '0.50', '81028.00', 'retefuente', '1.500000', '243086.00', '0.00', 'aprobado', '15881646.00'),
(4, '2018-02-08 11:37:56', '24.00', '3.00', '8625.00', '8328.00', '7994880.00', '0.50', '39974.00', 'comision bolsa', '0.208948', '16705.00', '0.00', 'en proceso', '7938201.00'),
(5, '2018-02-09 01:39:32', '24.00', '3.00', '5200.00', '4758.00', '4567680.00', '0.50', '22838.00', 'comision bolsa', '0.208948', '9544.00', '100000.00', 'aprobado', '4435298.00'),
(6, '2018-02-10 11:39:58', '24.00', '3.00', '1720.00', '1552.00', '2110720.00', '0.50', '10553.00', 'retefuente', '1.500000', '31660.00', '0.00', 'en proceso', '2068507.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lote`
--

CREATE TABLE `lote` (
  `idLote` int(11) NOT NULL,
  `idMunicipio` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `descripcion` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `lote`
--

INSERT INTO `lote` (`idLote`, `idMunicipio`, `nombre`, `descripcion`) VALUES
(1, 3, 'Santa ana', 'Zona con mayor producción'),
(2, 6, 'La joya', 'Alto indice de Proteinas y minerales'),
(3, 3, 'Las delicias', 'Alto indice de fosforo'),
(4, 1021, 'Madrid', 'Zona agricultora'),
(5, 1021, 'Colegio', 'Alto indice de fosoforo'),
(6, 1021, 'La caimanera', 'Alto indice de fosoforo'),
(7, 1021, 'Patio Bonito', 'Alto indice de fosoforo'),
(8, 1022, 'Palocabildo', 'Alto indice de fosoforo'),
(9, 1096, 'Barzalosa', 'Zona agricola'),
(10, 1024, 'La cajita', 'Alto indice de fosoforo'),
(11, 1021, 'Baja', 'parte baja del Espinal');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `marca`
--

CREATE TABLE `marca` (
  `idMarca` int(11) NOT NULL,
  `marca` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `marca`
--

INSERT INTO `marca` (`idMarca`, `marca`) VALUES
(1, 'NO APLICA'),
(2, 'AGRALE'),
(3, 'ALFA ROMEO'),
(4, 'AUDI'),
(5, 'BMW'),
(6, 'CHERY'),
(7, 'CHEVROLET'),
(8, 'CHRYSLER'),
(9, 'CITROEN'),
(10, 'DACIA'),
(11, 'DAEWO'),
(12, 'DAIHATSU'),
(13, 'DODGE'),
(14, 'FERRARI'),
(15, 'FIAT'),
(16, 'FORD'),
(17, 'GALLOPER'),
(18, 'HEIBAO'),
(19, 'HONDA'),
(20, 'HYUNDAI'),
(21, 'ISUZU'),
(22, 'JAGUAR'),
(23, 'JEEP'),
(24, 'KIA'),
(25, 'LADA'),
(26, 'LAND ROVER'),
(27, 'LEXUS'),
(28, 'MASERATI'),
(29, 'MAZDA'),
(30, 'MERCEDES BENZ'),
(31, 'MG'),
(32, 'MINI'),
(33, 'MITSUBISHI'),
(34, 'NISSAN'),
(35, 'PEUGEOT'),
(36, 'PORSCHE'),
(37, 'RAM'),
(38, 'RENAULT'),
(39, 'ROVER'),
(40, 'SAAB'),
(41, 'SEAT'),
(42, 'SMART'),
(43, 'SSANGYONG'),
(44, 'SUBARU'),
(45, 'SUZUKI'),
(46, 'TATA'),
(47, 'TOYOTA'),
(48, 'VOLKSWAGEN'),
(49, 'VOLVO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `muetraestufa`
--

CREATE TABLE `muetraestufa` (
  `idmuetraestufa` int(11) NOT NULL,
  `idLaboratorio` int(11) NOT NULL,
  `muestreo` int(11) NOT NULL,
  `hora` time NOT NULL,
  `humedad` decimal(5,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `municipios`
--

CREATE TABLE `municipios` (
  `idMunicipio` int(11) NOT NULL,
  `idDepartamento` int(11) NOT NULL,
  `nombre` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `municipios`
--

INSERT INTO `municipios` (`idMunicipio`, `idDepartamento`, `nombre`) VALUES
(1, 1, 'Leticia'),
(2, 1, 'Puerto Nariño'),
(3, 2, 'Abejorral'),
(4, 2, 'Abriaquí'),
(5, 2, 'Alejandria'),
(6, 2, 'Amagá'),
(7, 2, 'Amalfi'),
(8, 2, 'Andes'),
(9, 2, 'Angelópolis'),
(10, 2, 'Angostura'),
(11, 2, 'Anorí'),
(12, 2, 'Anzá'),
(13, 2, 'Apartadó'),
(14, 2, 'Arboletes'),
(15, 2, 'Argelia'),
(16, 2, 'Armenia'),
(17, 2, 'Barbosa'),
(18, 2, 'Bello'),
(19, 2, 'Belmira'),
(20, 2, 'Betania'),
(21, 2, 'Betulia'),
(22, 2, 'Bolívar'),
(23, 2, 'Briceño'),
(24, 2, 'Burítica'),
(25, 2, 'Caicedo'),
(26, 2, 'Caldas'),
(27, 2, 'Campamento'),
(28, 2, 'Caracolí'),
(29, 2, 'Caramanta'),
(30, 2, 'Carepa'),
(31, 2, 'Carmen de Viboral'),
(32, 2, 'Carolina'),
(33, 2, 'Caucasia'),
(34, 2, 'Cañasgordas'),
(35, 2, 'Chigorodó'),
(36, 2, 'Cisneros'),
(37, 2, 'Cocorná'),
(38, 2, 'Concepción'),
(39, 2, 'Concordia'),
(40, 2, 'Copacabana'),
(41, 2, 'Cáceres'),
(42, 2, 'Dabeiba'),
(43, 2, 'Don Matías'),
(44, 2, 'Ebéjico'),
(45, 2, 'El Bagre'),
(46, 2, 'Entrerríos'),
(47, 2, 'Envigado'),
(48, 2, 'Fredonia'),
(49, 2, 'Frontino'),
(50, 2, 'Giraldo'),
(51, 2, 'Girardota'),
(52, 2, 'Granada'),
(53, 2, 'Guadalupe'),
(54, 2, 'Guarne'),
(55, 2, 'Guatapé'),
(56, 2, 'Gómez Plata'),
(57, 2, 'Heliconia'),
(58, 2, 'Hispania'),
(59, 2, 'Itagüí'),
(60, 2, 'Ituango'),
(61, 2, 'Jardín'),
(62, 2, 'Jericó'),
(63, 2, 'La Ceja'),
(64, 2, 'La Estrella'),
(65, 2, 'La Pintada'),
(66, 2, 'La Unión'),
(67, 2, 'Liborina'),
(68, 2, 'Maceo'),
(69, 2, 'Marinilla'),
(70, 2, 'Medellín'),
(71, 2, 'Montebello'),
(72, 2, 'Murindó'),
(73, 2, 'Mutatá'),
(74, 2, 'Nariño'),
(75, 2, 'Nechí'),
(76, 2, 'Necoclí'),
(77, 2, 'Olaya'),
(78, 2, 'Peque'),
(79, 2, 'Peñol'),
(80, 2, 'Pueblorrico'),
(81, 2, 'Puerto Berrío'),
(82, 2, 'Puerto Nare'),
(83, 2, 'Puerto Triunfo'),
(84, 2, 'Remedios'),
(85, 2, 'Retiro'),
(86, 2, 'Ríonegro'),
(87, 2, 'Sabanalarga'),
(88, 2, 'Sabaneta'),
(89, 2, 'Salgar'),
(90, 2, 'San Andrés de Cuerquía'),
(91, 2, 'San Carlos'),
(92, 2, 'San Francisco'),
(93, 2, 'San Jerónimo'),
(94, 2, 'San José de Montaña'),
(95, 2, 'San Juan de Urabá'),
(96, 2, 'San Luís'),
(97, 2, 'San Pedro'),
(98, 2, 'San Pedro de Urabá'),
(99, 2, 'San Rafael'),
(100, 2, 'San Roque'),
(101, 2, 'San Vicente'),
(102, 2, 'Santa Bárbara'),
(103, 2, 'Santa Fé de Antioquia'),
(104, 2, 'Santa Rosa de Osos'),
(105, 2, 'Santo Domingo'),
(106, 2, 'Santuario'),
(107, 2, 'Segovia'),
(108, 2, 'Sonsón'),
(109, 2, 'Sopetrán'),
(110, 2, 'Tarazá'),
(111, 2, 'Tarso'),
(112, 2, 'Titiribí'),
(113, 2, 'Toledo'),
(114, 2, 'Turbo'),
(115, 2, 'Támesis'),
(116, 2, 'Uramita'),
(117, 2, 'Urrao'),
(118, 2, 'Valdivia'),
(119, 2, 'Valparaiso'),
(120, 2, 'Vegachí'),
(121, 2, 'Venecia'),
(122, 2, 'Vigía del Fuerte'),
(123, 2, 'Yalí'),
(124, 2, 'Yarumal'),
(125, 2, 'Yolombó'),
(126, 2, 'Yondó (Casabe)'),
(127, 2, 'Zaragoza'),
(128, 3, 'Arauca'),
(129, 3, 'Arauquita'),
(130, 3, 'Cravo Norte'),
(131, 3, 'Fortúl'),
(132, 3, 'Puerto Rondón'),
(133, 3, 'Saravena'),
(134, 3, 'Tame'),
(135, 4, 'Baranoa'),
(136, 4, 'Barranquilla'),
(137, 4, 'Campo de la Cruz'),
(138, 4, 'Candelaria'),
(139, 4, 'Galapa'),
(140, 4, 'Juan de Acosta'),
(141, 4, 'Luruaco'),
(142, 4, 'Malambo'),
(143, 4, 'Manatí'),
(144, 4, 'Palmar de Varela'),
(145, 4, 'Piojo'),
(146, 4, 'Polonuevo'),
(147, 4, 'Ponedera'),
(148, 4, 'Puerto Colombia'),
(149, 4, 'Repelón'),
(150, 4, 'Sabanagrande'),
(151, 4, 'Sabanalarga'),
(152, 4, 'Santa Lucía'),
(153, 4, 'Santo Tomás'),
(154, 4, 'Soledad'),
(155, 4, 'Suan'),
(156, 4, 'Tubará'),
(157, 4, 'Usiacuri'),
(158, 5, 'Achí'),
(159, 5, 'Altos del Rosario'),
(160, 5, 'Arenal'),
(161, 5, 'Arjona'),
(162, 5, 'Arroyohondo'),
(163, 5, 'Barranco de Loba'),
(164, 5, 'Calamar'),
(165, 5, 'Cantagallo'),
(166, 5, 'Cartagena'),
(167, 5, 'Cicuco'),
(168, 5, 'Clemencia'),
(169, 5, 'Córdoba'),
(170, 5, 'El Carmen de Bolívar'),
(171, 5, 'El Guamo'),
(172, 5, 'El Peñon'),
(173, 5, 'Hatillo de Loba'),
(174, 5, 'Magangué'),
(175, 5, 'Mahates'),
(176, 5, 'Margarita'),
(177, 5, 'María la Baja'),
(178, 5, 'Mompós'),
(179, 5, 'Montecristo'),
(180, 5, 'Morales'),
(181, 5, 'Norosí'),
(182, 5, 'Pinillos'),
(183, 5, 'Regidor'),
(184, 5, 'Río Viejo'),
(185, 5, 'San Cristobal'),
(186, 5, 'San Estanislao'),
(187, 5, 'San Fernando'),
(188, 5, 'San Jacinto'),
(189, 5, 'San Jacinto del Cauca'),
(190, 5, 'San Juan de Nepomuceno'),
(191, 5, 'San Martín de Loba'),
(192, 5, 'San Pablo'),
(193, 5, 'Santa Catalina'),
(194, 5, 'Santa Rosa '),
(195, 5, 'Santa Rosa del Sur'),
(196, 5, 'Simití'),
(197, 5, 'Soplaviento'),
(198, 5, 'Talaigua Nuevo'),
(199, 5, 'Tiquisio (Puerto Rico)'),
(200, 5, 'Turbaco'),
(201, 5, 'Turbaná'),
(202, 5, 'Villanueva'),
(203, 5, 'Zambrano'),
(204, 6, 'Almeida'),
(205, 6, 'Aquitania'),
(206, 6, 'Arcabuco'),
(207, 6, 'Belén'),
(208, 6, 'Berbeo'),
(209, 6, 'Beteitiva'),
(210, 6, 'Boavita'),
(211, 6, 'Boyacá'),
(212, 6, 'Briceño'),
(213, 6, 'Buenavista'),
(214, 6, 'Busbanza'),
(215, 6, 'Caldas'),
(216, 6, 'Campohermoso'),
(217, 6, 'Cerinza'),
(218, 6, 'Chinavita'),
(219, 6, 'Chiquinquirá'),
(220, 6, 'Chiscas'),
(221, 6, 'Chita'),
(222, 6, 'Chitaraque'),
(223, 6, 'Chivatá'),
(224, 6, 'Chíquiza'),
(225, 6, 'Chívor'),
(226, 6, 'Ciénaga'),
(227, 6, 'Coper'),
(228, 6, 'Corrales'),
(229, 6, 'Covarachía'),
(230, 6, 'Cubará'),
(231, 6, 'Cucaita'),
(232, 6, 'Cuitiva'),
(233, 6, 'Cómbita'),
(234, 6, 'Duitama'),
(235, 6, 'El Cocuy'),
(236, 6, 'El Espino'),
(237, 6, 'Firavitoba'),
(238, 6, 'Floresta'),
(239, 6, 'Gachantivá'),
(240, 6, 'Garagoa'),
(241, 6, 'Guacamayas'),
(242, 6, 'Guateque'),
(243, 6, 'Guayatá'),
(244, 6, 'Guicán'),
(245, 6, 'Gámeza'),
(246, 6, 'Izá'),
(247, 6, 'Jenesano'),
(248, 6, 'Jericó'),
(249, 6, 'La Capilla'),
(250, 6, 'La Uvita'),
(251, 6, 'La Victoria'),
(252, 6, 'Labranzagrande'),
(253, 6, 'Macanal'),
(254, 6, 'Maripí'),
(255, 6, 'Miraflores'),
(256, 6, 'Mongua'),
(257, 6, 'Monguí'),
(258, 6, 'Moniquirá'),
(259, 6, 'Motavita'),
(260, 6, 'Muzo'),
(261, 6, 'Nobsa'),
(262, 6, 'Nuevo Colón'),
(263, 6, 'Oicatá'),
(264, 6, 'Otanche'),
(265, 6, 'Pachavita'),
(266, 6, 'Paipa'),
(267, 6, 'Pajarito'),
(268, 6, 'Panqueba'),
(269, 6, 'Pauna'),
(270, 6, 'Paya'),
(271, 6, 'Paz de Río'),
(272, 6, 'Pesca'),
(273, 6, 'Pisva'),
(274, 6, 'Puerto Boyacá'),
(275, 6, 'Páez'),
(276, 6, 'Quipama'),
(277, 6, 'Ramiriquí'),
(278, 6, 'Rondón'),
(279, 6, 'Ráquira'),
(280, 6, 'Saboyá'),
(281, 6, 'Samacá'),
(282, 6, 'San Eduardo'),
(283, 6, 'San José de Pare'),
(284, 6, 'San Luís de Gaceno'),
(285, 6, 'San Mateo'),
(286, 6, 'San Miguel de Sema'),
(287, 6, 'San Pablo de Borbur'),
(288, 6, 'Santa María'),
(289, 6, 'Santa Rosa de Viterbo'),
(290, 6, 'Santa Sofía'),
(291, 6, 'Santana'),
(292, 6, 'Sativanorte'),
(293, 6, 'Sativasur'),
(294, 6, 'Siachoque'),
(295, 6, 'Soatá'),
(296, 6, 'Socha'),
(297, 6, 'Socotá'),
(298, 6, 'Sogamoso'),
(299, 6, 'Somondoco'),
(300, 6, 'Sora'),
(301, 6, 'Soracá'),
(302, 6, 'Sotaquirá'),
(303, 6, 'Susacón'),
(304, 6, 'Sutamarchán'),
(305, 6, 'Sutatenza'),
(306, 6, 'Sáchica'),
(307, 6, 'Tasco'),
(308, 6, 'Tenza'),
(309, 6, 'Tibaná'),
(310, 6, 'Tibasosa'),
(311, 6, 'Tinjacá'),
(312, 6, 'Tipacoque'),
(313, 6, 'Toca'),
(314, 6, 'Toguí'),
(315, 6, 'Topagá'),
(316, 6, 'Tota'),
(317, 6, 'Tunja'),
(318, 6, 'Tunungua'),
(319, 6, 'Turmequé'),
(320, 6, 'Tuta'),
(321, 6, 'Tutasá'),
(322, 6, 'Ventaquemada'),
(323, 6, 'Villa de Leiva'),
(324, 6, 'Viracachá'),
(325, 6, 'Zetaquirá'),
(326, 6, 'Úmbita'),
(327, 7, 'Aguadas'),
(328, 7, 'Anserma'),
(329, 7, 'Aranzazu'),
(330, 7, 'Belalcázar'),
(331, 7, 'Chinchiná'),
(332, 7, 'Filadelfia'),
(333, 7, 'La Dorada'),
(334, 7, 'La Merced'),
(335, 7, 'La Victoria'),
(336, 7, 'Manizales'),
(337, 7, 'Manzanares'),
(338, 7, 'Marmato'),
(339, 7, 'Marquetalia'),
(340, 7, 'Marulanda'),
(341, 7, 'Neira'),
(342, 7, 'Norcasia'),
(343, 7, 'Palestina'),
(344, 7, 'Pensilvania'),
(345, 7, 'Pácora'),
(346, 7, 'Risaralda'),
(347, 7, 'Río Sucio'),
(348, 7, 'Salamina'),
(349, 7, 'Samaná'),
(350, 7, 'San José'),
(351, 7, 'Supía'),
(352, 7, 'Villamaría'),
(353, 7, 'Viterbo'),
(354, 8, 'Albania'),
(355, 8, 'Belén de los Andaquíes'),
(356, 8, 'Cartagena del Chairá'),
(357, 8, 'Curillo'),
(358, 8, 'El Doncello'),
(359, 8, 'El Paujil'),
(360, 8, 'Florencia'),
(361, 8, 'La Montañita'),
(362, 8, 'Milán'),
(363, 8, 'Morelia'),
(364, 8, 'Puerto Rico'),
(365, 8, 'San José del Fragua'),
(366, 8, 'San Vicente del Caguán'),
(367, 8, 'Solano'),
(368, 8, 'Solita'),
(369, 8, 'Valparaiso'),
(370, 9, 'Aguazul'),
(371, 9, 'Chámeza'),
(372, 9, 'Hato Corozal'),
(373, 9, 'La Salina'),
(374, 9, 'Maní'),
(375, 9, 'Monterrey'),
(376, 9, 'Nunchía'),
(377, 9, 'Orocué'),
(378, 9, 'Paz de Ariporo'),
(379, 9, 'Pore'),
(380, 9, 'Recetor'),
(381, 9, 'Sabanalarga'),
(382, 9, 'San Luís de Palenque'),
(383, 9, 'Sácama'),
(384, 9, 'Tauramena'),
(385, 9, 'Trinidad'),
(386, 9, 'Támara'),
(387, 9, 'Villanueva'),
(388, 9, 'Yopal'),
(389, 10, 'Almaguer'),
(390, 10, 'Argelia'),
(391, 10, 'Balboa'),
(392, 10, 'Bolívar'),
(393, 10, 'Buenos Aires'),
(394, 10, 'Cajibío'),
(395, 10, 'Caldono'),
(396, 10, 'Caloto'),
(397, 10, 'Corinto'),
(398, 10, 'El Tambo'),
(399, 10, 'Florencia'),
(400, 10, 'Guachené'),
(401, 10, 'Guapí'),
(402, 10, 'Inzá'),
(403, 10, 'Jambaló'),
(404, 10, 'La Sierra'),
(405, 10, 'La Vega'),
(406, 10, 'López (Micay)'),
(407, 10, 'Mercaderes'),
(408, 10, 'Miranda'),
(409, 10, 'Morales'),
(410, 10, 'Padilla'),
(411, 10, 'Patía (El Bordo)'),
(412, 10, 'Piamonte'),
(413, 10, 'Piendamó'),
(414, 10, 'Popayán'),
(415, 10, 'Puerto Tejada'),
(416, 10, 'Puracé (Coconuco)'),
(417, 10, 'Páez (Belalcazar)'),
(418, 10, 'Rosas'),
(419, 10, 'San Sebastián'),
(420, 10, 'Santa Rosa'),
(421, 10, 'Santander de Quilichao'),
(422, 10, 'Silvia'),
(423, 10, 'Sotara (Paispamba)'),
(424, 10, 'Sucre'),
(425, 10, 'Suárez'),
(426, 10, 'Timbiquí'),
(427, 10, 'Timbío'),
(428, 10, 'Toribío'),
(429, 10, 'Totoró'),
(430, 10, 'Villa Rica'),
(431, 11, 'Aguachica'),
(432, 11, 'Agustín Codazzi'),
(433, 11, 'Astrea'),
(434, 11, 'Becerríl'),
(435, 11, 'Bosconia'),
(436, 11, 'Chimichagua'),
(437, 11, 'Chiriguaná'),
(438, 11, 'Curumaní'),
(439, 11, 'El Copey'),
(440, 11, 'El Paso'),
(441, 11, 'Gamarra'),
(442, 11, 'Gonzalez'),
(443, 11, 'La Gloria'),
(444, 11, 'La Jagua de Ibirico'),
(445, 11, 'La Paz (Robles)'),
(446, 11, 'Manaure Balcón del Cesar'),
(447, 11, 'Pailitas'),
(448, 11, 'Pelaya'),
(449, 11, 'Pueblo Bello'),
(450, 11, 'Río de oro'),
(451, 11, 'San Alberto'),
(452, 11, 'San Diego'),
(453, 11, 'San Martín'),
(454, 11, 'Tamalameque'),
(455, 11, 'Valledupar'),
(456, 12, 'Acandí'),
(457, 12, 'Alto Baudó (Pie de Pato)'),
(458, 12, 'Atrato (Yuto)'),
(459, 12, 'Bagadó'),
(460, 12, 'Bahía Solano (Mútis)'),
(461, 12, 'Bajo Baudó (Pizarro)'),
(462, 12, 'Belén de Bajirá'),
(463, 12, 'Bojayá (Bellavista)'),
(464, 12, 'Cantón de San Pablo'),
(465, 12, 'Carmen del Darién (CURBARADÓ)'),
(466, 12, 'Condoto'),
(467, 12, 'Cértegui'),
(468, 12, 'El Carmen de Atrato'),
(469, 12, 'Istmina'),
(470, 12, 'Juradó'),
(471, 12, 'Lloró'),
(472, 12, 'Medio Atrato'),
(473, 12, 'Medio Baudó'),
(474, 12, 'Medio San Juan (ANDAGOYA)'),
(475, 12, 'Novita'),
(476, 12, 'Nuquí'),
(477, 12, 'Quibdó'),
(478, 12, 'Río Iró'),
(479, 12, 'Río Quito'),
(480, 12, 'Ríosucio'),
(481, 12, 'San José del Palmar'),
(482, 12, 'Santa Genoveva de Docorodó'),
(483, 12, 'Sipí'),
(484, 12, 'Tadó'),
(485, 12, 'Unguía'),
(486, 12, 'Unión Panamericana (ÁNIMAS)'),
(487, 13, 'Ayapel'),
(488, 13, 'Buenavista'),
(489, 13, 'Canalete'),
(490, 13, 'Cereté'),
(491, 13, 'Chimá'),
(492, 13, 'Chinú'),
(493, 13, 'Ciénaga de Oro'),
(494, 13, 'Cotorra'),
(495, 13, 'La Apartada y La Frontera'),
(496, 13, 'Lorica'),
(497, 13, 'Los Córdobas'),
(498, 13, 'Momil'),
(499, 13, 'Montelíbano'),
(500, 13, 'Monteria'),
(501, 13, 'Moñitos'),
(502, 13, 'Planeta Rica'),
(503, 13, 'Pueblo Nuevo'),
(504, 13, 'Puerto Escondido'),
(505, 13, 'Puerto Libertador'),
(506, 13, 'Purísima'),
(507, 13, 'Sahagún'),
(508, 13, 'San Andrés Sotavento'),
(509, 13, 'San Antero'),
(510, 13, 'San Bernardo del Viento'),
(511, 13, 'San Carlos'),
(512, 13, 'San José de Uré'),
(513, 13, 'San Pelayo'),
(514, 13, 'Tierralta'),
(515, 13, 'Tuchín'),
(516, 13, 'Valencia'),
(517, 14, 'Agua de Dios'),
(518, 14, 'Albán'),
(519, 14, 'Anapoima'),
(520, 14, 'Anolaima'),
(521, 14, 'Apulo'),
(522, 14, 'Arbeláez'),
(523, 14, 'Beltrán'),
(524, 14, 'Bituima'),
(525, 14, 'Bogotá D.C.'),
(526, 14, 'Bojacá'),
(527, 14, 'Cabrera'),
(528, 14, 'Cachipay'),
(529, 14, 'Cajicá'),
(530, 14, 'Caparrapí'),
(531, 14, 'Carmen de Carupa'),
(532, 14, 'Chaguaní'),
(533, 14, 'Chipaque'),
(534, 14, 'Choachí'),
(535, 14, 'Chocontá'),
(536, 14, 'Chía'),
(537, 14, 'Cogua'),
(538, 14, 'Cota'),
(539, 14, 'Cucunubá'),
(540, 14, 'Cáqueza'),
(541, 14, 'El Colegio'),
(542, 14, 'El Peñón'),
(543, 14, 'El Rosal'),
(544, 14, 'Facatativá'),
(545, 14, 'Fosca'),
(546, 14, 'Funza'),
(547, 14, 'Fusagasugá'),
(548, 14, 'Fómeque'),
(549, 14, 'Fúquene'),
(550, 14, 'Gachalá'),
(551, 14, 'Gachancipá'),
(552, 14, 'Gachetá'),
(553, 14, 'Gama'),
(554, 14, 'Girardot'),
(555, 14, 'Granada'),
(556, 14, 'Guachetá'),
(557, 14, 'Guaduas'),
(558, 14, 'Guasca'),
(559, 14, 'Guataquí'),
(560, 14, 'Guatavita'),
(561, 14, 'Guayabal de Siquima'),
(562, 14, 'Guayabetal'),
(563, 14, 'Gutiérrez'),
(564, 14, 'Jerusalén'),
(565, 14, 'Junín'),
(566, 14, 'La Calera'),
(567, 14, 'La Mesa'),
(568, 14, 'La Palma'),
(569, 14, 'La Peña'),
(570, 14, 'La Vega'),
(571, 14, 'Lenguazaque'),
(572, 14, 'Machetá'),
(573, 14, 'Madrid'),
(574, 14, 'Manta'),
(575, 14, 'Medina'),
(576, 14, 'Mosquera'),
(577, 14, 'Nariño'),
(578, 14, 'Nemocón'),
(579, 14, 'Nilo'),
(580, 14, 'Nimaima'),
(581, 14, 'Nocaima'),
(582, 14, 'Pacho'),
(583, 14, 'Paime'),
(584, 14, 'Pandi'),
(585, 14, 'Paratebueno'),
(586, 14, 'Pasca'),
(587, 14, 'Puerto Salgar'),
(588, 14, 'Pulí'),
(589, 14, 'Quebradanegra'),
(590, 14, 'Quetame'),
(591, 14, 'Quipile'),
(592, 14, 'Ricaurte'),
(593, 14, 'San Antonio de Tequendama'),
(594, 14, 'San Bernardo'),
(595, 14, 'San Cayetano'),
(596, 14, 'San Francisco'),
(597, 14, 'San Juan de Río Seco'),
(598, 14, 'Sasaima'),
(599, 14, 'Sesquilé'),
(600, 14, 'Sibaté'),
(601, 14, 'Silvania'),
(602, 14, 'Simijaca'),
(603, 14, 'Soacha'),
(604, 14, 'Sopó'),
(605, 14, 'Subachoque'),
(606, 14, 'Suesca'),
(607, 14, 'Supatá'),
(608, 14, 'Susa'),
(609, 14, 'Sutatausa'),
(610, 14, 'Tabio'),
(611, 14, 'Tausa'),
(612, 14, 'Tena'),
(613, 14, 'Tenjo'),
(614, 14, 'Tibacuy'),
(615, 14, 'Tibirita'),
(616, 14, 'Tocaima'),
(617, 14, 'Tocancipá'),
(618, 14, 'Topaipí'),
(619, 14, 'Ubalá'),
(620, 14, 'Ubaque'),
(621, 14, 'Ubaté'),
(622, 14, 'Une'),
(623, 14, 'Venecia (Ospina Pérez)'),
(624, 14, 'Vergara'),
(625, 14, 'Viani'),
(626, 14, 'Villagómez'),
(627, 14, 'Villapinzón'),
(628, 14, 'Villeta'),
(629, 14, 'Viotá'),
(630, 14, 'Yacopí'),
(631, 14, 'Zipacón'),
(632, 14, 'Zipaquirá'),
(633, 14, 'Útica'),
(634, 15, 'Inírida'),
(635, 16, 'Calamar'),
(636, 16, 'El Retorno'),
(637, 16, 'Miraflores'),
(638, 16, 'San José del Guaviare'),
(639, 17, 'Acevedo'),
(640, 17, 'Agrado'),
(641, 17, 'Aipe'),
(642, 17, 'Algeciras'),
(643, 17, 'Altamira'),
(644, 17, 'Baraya'),
(645, 17, 'Campoalegre'),
(646, 17, 'Colombia'),
(647, 17, 'Elías'),
(648, 17, 'Garzón'),
(649, 17, 'Gigante'),
(650, 17, 'Guadalupe'),
(651, 17, 'Hobo'),
(652, 17, 'Isnos'),
(653, 17, 'La Argentina'),
(654, 17, 'La Plata'),
(655, 17, 'Neiva'),
(656, 17, 'Nátaga'),
(657, 17, 'Oporapa'),
(658, 17, 'Paicol'),
(659, 17, 'Palermo'),
(660, 17, 'Palestina'),
(661, 17, 'Pital'),
(662, 17, 'Pitalito'),
(663, 17, 'Rivera'),
(664, 17, 'Saladoblanco'),
(665, 17, 'San Agustín'),
(666, 17, 'Santa María'),
(667, 17, 'Suaza'),
(668, 17, 'Tarqui'),
(669, 17, 'Tello'),
(670, 17, 'Teruel'),
(671, 17, 'Tesalia'),
(672, 17, 'Timaná'),
(673, 17, 'Villavieja'),
(674, 17, 'Yaguará'),
(675, 17, 'Íquira'),
(676, 18, 'Albania'),
(677, 18, 'Barrancas'),
(678, 18, 'Dibulla'),
(679, 18, 'Distracción'),
(680, 18, 'El Molino'),
(681, 18, 'Fonseca'),
(682, 18, 'Hatonuevo'),
(683, 18, 'La Jagua del Pilar'),
(684, 18, 'Maicao'),
(685, 18, 'Manaure'),
(686, 18, 'Riohacha'),
(687, 18, 'San Juan del Cesar'),
(688, 18, 'Uribia'),
(689, 18, 'Urumita'),
(690, 18, 'Villanueva'),
(691, 19, 'Algarrobo'),
(692, 19, 'Aracataca'),
(693, 19, 'Ariguaní (El Difícil)'),
(694, 19, 'Cerro San Antonio'),
(695, 19, 'Chivolo'),
(696, 19, 'Ciénaga'),
(697, 19, 'Concordia'),
(698, 19, 'El Banco'),
(699, 19, 'El Piñon'),
(700, 19, 'El Retén'),
(701, 19, 'Fundación'),
(702, 19, 'Guamal'),
(703, 19, 'Nueva Granada'),
(704, 19, 'Pedraza'),
(705, 19, 'Pijiño'),
(706, 19, 'Pivijay'),
(707, 19, 'Plato'),
(708, 19, 'Puebloviejo'),
(709, 19, 'Remolino'),
(710, 19, 'Sabanas de San Angel (SAN ANGEL)'),
(711, 19, 'Salamina'),
(712, 19, 'San Sebastián de Buenavista'),
(713, 19, 'San Zenón'),
(714, 19, 'Santa Ana'),
(715, 19, 'Santa Bárbara de Pinto'),
(716, 19, 'Santa Marta'),
(717, 19, 'Sitionuevo'),
(718, 19, 'Tenerife'),
(719, 19, 'Zapayán (PUNTA DE PIEDRAS)'),
(720, 19, 'Zona Bananera (PRADO - SEVILLA)'),
(721, 20, 'Acacías'),
(722, 20, 'Barranca de Upía'),
(723, 20, 'Cabuyaro'),
(724, 20, 'Castilla la Nueva'),
(725, 20, 'Cubarral'),
(726, 20, 'Cumaral'),
(727, 20, 'El Calvario'),
(728, 20, 'El Castillo'),
(729, 20, 'El Dorado'),
(730, 20, 'Fuente de Oro'),
(731, 20, 'Granada'),
(732, 20, 'Guamal'),
(733, 20, 'La Macarena'),
(734, 20, 'Lejanías'),
(735, 20, 'Mapiripan'),
(736, 20, 'Mesetas'),
(737, 20, 'Puerto Concordia'),
(738, 20, 'Puerto Gaitán'),
(739, 20, 'Puerto Lleras'),
(740, 20, 'Puerto López'),
(741, 20, 'Puerto Rico'),
(742, 20, 'Restrepo'),
(743, 20, 'San Carlos de Guaroa'),
(744, 20, 'San Juan de Arama'),
(745, 20, 'San Juanito'),
(746, 20, 'San Martín'),
(747, 20, 'Uribe'),
(748, 20, 'Villavicencio'),
(749, 20, 'Vista Hermosa'),
(750, 21, 'Albán (San José)'),
(751, 21, 'Aldana'),
(752, 21, 'Ancuya'),
(753, 21, 'Arboleda (Berruecos)'),
(754, 21, 'Barbacoas'),
(755, 21, 'Belén'),
(756, 21, 'Buesaco'),
(757, 21, 'Chachaguí'),
(758, 21, 'Colón (Génova)'),
(759, 21, 'Consaca'),
(760, 21, 'Contadero'),
(761, 21, 'Cuaspud (Carlosama)'),
(762, 21, 'Cumbal'),
(763, 21, 'Cumbitara'),
(764, 21, 'Córdoba'),
(765, 21, 'El Charco'),
(766, 21, 'El Peñol'),
(767, 21, 'El Rosario'),
(768, 21, 'El Tablón de Gómez'),
(769, 21, 'El Tambo'),
(770, 21, 'Francisco Pizarro'),
(771, 21, 'Funes'),
(772, 21, 'Guachavés'),
(773, 21, 'Guachucal'),
(774, 21, 'Guaitarilla'),
(775, 21, 'Gualmatán'),
(776, 21, 'Iles'),
(777, 21, 'Imúes'),
(778, 21, 'Ipiales'),
(779, 21, 'La Cruz'),
(780, 21, 'La Florida'),
(781, 21, 'La Llanada'),
(782, 21, 'La Tola'),
(783, 21, 'La Unión'),
(784, 21, 'Leiva'),
(785, 21, 'Linares'),
(786, 21, 'Magüi (Payán)'),
(787, 21, 'Mallama (Piedrancha)'),
(788, 21, 'Mosquera'),
(789, 21, 'Nariño'),
(790, 21, 'Olaya Herrera'),
(791, 21, 'Ospina'),
(792, 21, 'Policarpa'),
(793, 21, 'Potosí'),
(794, 21, 'Providencia'),
(795, 21, 'Puerres'),
(796, 21, 'Pupiales'),
(797, 21, 'Ricaurte'),
(798, 21, 'Roberto Payán (San José)'),
(799, 21, 'Samaniego'),
(800, 21, 'San Bernardo'),
(801, 21, 'San Juan de Pasto'),
(802, 21, 'San Lorenzo'),
(803, 21, 'San Pablo'),
(804, 21, 'San Pedro de Cartago'),
(805, 21, 'Sandoná'),
(806, 21, 'Santa Bárbara (Iscuandé)'),
(807, 21, 'Sapuyes'),
(808, 21, 'Sotomayor (Los Andes)'),
(809, 21, 'Taminango'),
(810, 21, 'Tangua'),
(811, 21, 'Tumaco'),
(812, 21, 'Túquerres'),
(813, 21, 'Yacuanquer'),
(814, 22, 'Arboledas'),
(815, 22, 'Bochalema'),
(816, 22, 'Bucarasica'),
(817, 22, 'Chinácota'),
(818, 22, 'Chitagá'),
(819, 22, 'Convención'),
(820, 22, 'Cucutilla'),
(821, 22, 'Cáchira'),
(822, 22, 'Cácota'),
(823, 22, 'Cúcuta'),
(824, 22, 'Durania'),
(825, 22, 'El Carmen'),
(826, 22, 'El Tarra'),
(827, 22, 'El Zulia'),
(828, 22, 'Gramalote'),
(829, 22, 'Hacarí'),
(830, 22, 'Herrán'),
(831, 22, 'La Esperanza'),
(832, 22, 'La Playa'),
(833, 22, 'Labateca'),
(834, 22, 'Los Patios'),
(835, 22, 'Lourdes'),
(836, 22, 'Mutiscua'),
(837, 22, 'Ocaña'),
(838, 22, 'Pamplona'),
(839, 22, 'Pamplonita'),
(840, 22, 'Puerto Santander'),
(841, 22, 'Ragonvalia'),
(842, 22, 'Salazar'),
(843, 22, 'San Calixto'),
(844, 22, 'San Cayetano'),
(845, 22, 'Santiago'),
(846, 22, 'Sardinata'),
(847, 22, 'Silos'),
(848, 22, 'Teorama'),
(849, 22, 'Tibú'),
(850, 22, 'Toledo'),
(851, 22, 'Villa Caro'),
(852, 22, 'Villa del Rosario'),
(853, 22, 'Ábrego'),
(854, 23, 'Colón'),
(855, 23, 'Mocoa'),
(856, 23, 'Orito'),
(857, 23, 'Puerto Asís'),
(858, 23, 'Puerto Caicedo'),
(859, 23, 'Puerto Guzmán'),
(860, 23, 'Puerto Leguízamo'),
(861, 23, 'San Francisco'),
(862, 23, 'San Miguel'),
(863, 23, 'Santiago'),
(864, 23, 'Sibundoy'),
(865, 23, 'Valle del Guamuez'),
(866, 23, 'Villagarzón'),
(867, 24, 'Armenia'),
(868, 24, 'Buenavista'),
(869, 24, 'Calarcá'),
(870, 24, 'Circasia'),
(871, 24, 'Cordobá'),
(872, 24, 'Filandia'),
(873, 24, 'Génova'),
(874, 24, 'La Tebaida'),
(875, 24, 'Montenegro'),
(876, 24, 'Pijao'),
(877, 24, 'Quimbaya'),
(878, 24, 'Salento'),
(879, 25, 'Apía'),
(880, 25, 'Balboa'),
(881, 25, 'Belén de Umbría'),
(882, 25, 'Dos Quebradas'),
(883, 25, 'Guática'),
(884, 25, 'La Celia'),
(885, 25, 'La Virginia'),
(886, 25, 'Marsella'),
(887, 25, 'Mistrató'),
(888, 25, 'Pereira'),
(889, 25, 'Pueblo Rico'),
(890, 25, 'Quinchía'),
(891, 25, 'Santa Rosa de Cabal'),
(892, 25, 'Santuario'),
(893, 26, 'Providencia'),
(894, 27, 'Aguada'),
(895, 27, 'Albania'),
(896, 27, 'Aratoca'),
(897, 27, 'Barbosa'),
(898, 27, 'Barichara'),
(899, 27, 'Barrancabermeja'),
(900, 27, 'Betulia'),
(901, 27, 'Bolívar'),
(902, 27, 'Bucaramanga'),
(903, 27, 'Cabrera'),
(904, 27, 'California'),
(905, 27, 'Capitanejo'),
(906, 27, 'Carcasí'),
(907, 27, 'Cepita'),
(908, 27, 'Cerrito'),
(909, 27, 'Charalá'),
(910, 27, 'Charta'),
(911, 27, 'Chima'),
(912, 27, 'Chipatá'),
(913, 27, 'Cimitarra'),
(914, 27, 'Concepción'),
(915, 27, 'Confines'),
(916, 27, 'Contratación'),
(917, 27, 'Coromoro'),
(918, 27, 'Curití'),
(919, 27, 'El Carmen'),
(920, 27, 'El Guacamayo'),
(921, 27, 'El Peñon'),
(922, 27, 'El Playón'),
(923, 27, 'Encino'),
(924, 27, 'Enciso'),
(925, 27, 'Floridablanca'),
(926, 27, 'Florián'),
(927, 27, 'Galán'),
(928, 27, 'Girón'),
(929, 27, 'Guaca'),
(930, 27, 'Guadalupe'),
(931, 27, 'Guapota'),
(932, 27, 'Guavatá'),
(933, 27, 'Guepsa'),
(934, 27, 'Gámbita'),
(935, 27, 'Hato'),
(936, 27, 'Jesús María'),
(937, 27, 'Jordán'),
(938, 27, 'La Belleza'),
(939, 27, 'La Paz'),
(940, 27, 'Landázuri'),
(941, 27, 'Lebrija'),
(942, 27, 'Los Santos'),
(943, 27, 'Macaravita'),
(944, 27, 'Matanza'),
(945, 27, 'Mogotes'),
(946, 27, 'Molagavita'),
(947, 27, 'Málaga'),
(948, 27, 'Ocamonte'),
(949, 27, 'Oiba'),
(950, 27, 'Onzaga'),
(951, 27, 'Palmar'),
(952, 27, 'Palmas del Socorro'),
(953, 27, 'Pie de Cuesta'),
(954, 27, 'Pinchote'),
(955, 27, 'Puente Nacional'),
(956, 27, 'Puerto Parra'),
(957, 27, 'Puerto Wilches'),
(958, 27, 'Páramo'),
(959, 27, 'Rio Negro'),
(960, 27, 'Sabana de Torres'),
(961, 27, 'San Andrés'),
(962, 27, 'San Benito'),
(963, 27, 'San Gíl'),
(964, 27, 'San Joaquín'),
(965, 27, 'San José de Miranda'),
(966, 27, 'San Miguel'),
(967, 27, 'San Vicente del Chucurí'),
(968, 27, 'Santa Bárbara'),
(969, 27, 'Santa Helena del Opón'),
(970, 27, 'Simacota'),
(971, 27, 'Socorro'),
(972, 27, 'Suaita'),
(973, 27, 'Sucre'),
(974, 27, 'Suratá'),
(975, 27, 'Tona'),
(976, 27, 'Valle de San José'),
(977, 27, 'Vetas'),
(978, 27, 'Villanueva'),
(979, 27, 'Vélez'),
(980, 27, 'Zapatoca'),
(981, 28, 'Buenavista'),
(982, 28, 'Caimito'),
(983, 28, 'Chalán'),
(984, 28, 'Colosó (Ricaurte)'),
(985, 28, 'Corozal'),
(986, 28, 'Coveñas'),
(987, 28, 'El Roble'),
(988, 28, 'Galeras (Nueva Granada)'),
(989, 28, 'Guaranda'),
(990, 28, 'La Unión'),
(991, 28, 'Los Palmitos'),
(992, 28, 'Majagual'),
(993, 28, 'Morroa'),
(994, 28, 'Ovejas'),
(995, 28, 'Palmito'),
(996, 28, 'Sampués'),
(997, 28, 'San Benito Abad'),
(998, 28, 'San Juan de Betulia'),
(999, 28, 'San Marcos'),
(1000, 28, 'San Onofre'),
(1001, 28, 'San Pedro'),
(1002, 28, 'Sincelejo'),
(1003, 28, 'Sincé'),
(1004, 28, 'Sucre'),
(1005, 28, 'Tolú'),
(1006, 28, 'Tolú Viejo'),
(1007, 29, 'Alpujarra'),
(1008, 29, 'Alvarado'),
(1009, 29, 'Ambalema'),
(1010, 29, 'Anzoátegui'),
(1011, 29, 'Armero (Guayabal)'),
(1012, 29, 'Ataco'),
(1013, 29, 'Cajamarca'),
(1014, 29, 'Carmen de Apicalá'),
(1015, 29, 'Casabianca'),
(1016, 29, 'Chaparral'),
(1017, 29, 'Coello'),
(1018, 29, 'Coyaima'),
(1019, 29, 'Cunday'),
(1020, 29, 'Dolores'),
(1021, 29, 'Espinal'),
(1022, 29, 'Falan'),
(1023, 29, 'Flandes'),
(1024, 29, 'Fresno'),
(1025, 29, 'Guamo'),
(1026, 29, 'Herveo'),
(1027, 29, 'Honda'),
(1028, 29, 'Ibagué'),
(1029, 29, 'Icononzo'),
(1030, 29, 'Lérida'),
(1031, 29, 'Líbano'),
(1032, 29, 'Mariquita'),
(1033, 29, 'Melgar'),
(1034, 29, 'Murillo'),
(1035, 29, 'Natagaima'),
(1036, 29, 'Ortega'),
(1037, 29, 'Palocabildo'),
(1038, 29, 'Piedras'),
(1039, 29, 'Planadas'),
(1040, 29, 'Prado'),
(1041, 29, 'Purificación'),
(1042, 29, 'Rioblanco'),
(1043, 29, 'Roncesvalles'),
(1044, 29, 'Rovira'),
(1045, 29, 'Saldaña'),
(1046, 29, 'San Antonio'),
(1047, 29, 'San Luis'),
(1048, 29, 'Santa Isabel'),
(1049, 29, 'Suárez'),
(1050, 29, 'Valle de San Juan'),
(1051, 29, 'Venadillo'),
(1052, 29, 'Villahermosa'),
(1053, 29, 'Villarrica'),
(1054, 30, 'Alcalá'),
(1055, 30, 'Andalucía'),
(1056, 30, 'Ansermanuevo'),
(1057, 30, 'Argelia'),
(1058, 30, 'Bolívar'),
(1059, 30, 'Buenaventura'),
(1060, 30, 'Buga'),
(1061, 30, 'Bugalagrande'),
(1062, 30, 'Caicedonia'),
(1063, 30, 'Calima (Darién)'),
(1064, 30, 'Calí'),
(1065, 30, 'Candelaria'),
(1066, 30, 'Cartago'),
(1067, 30, 'Dagua'),
(1068, 30, 'El Cairo'),
(1069, 30, 'El Cerrito'),
(1070, 30, 'El Dovio'),
(1071, 30, 'El Águila'),
(1072, 30, 'Florida'),
(1073, 30, 'Ginebra'),
(1074, 30, 'Guacarí'),
(1075, 30, 'Jamundí'),
(1076, 30, 'La Cumbre'),
(1077, 30, 'La Unión'),
(1078, 30, 'La Victoria'),
(1079, 30, 'Obando'),
(1080, 30, 'Palmira'),
(1081, 30, 'Pradera'),
(1082, 30, 'Restrepo'),
(1083, 30, 'Riofrío'),
(1084, 30, 'Roldanillo'),
(1085, 30, 'San Pedro'),
(1086, 30, 'Sevilla'),
(1087, 30, 'Toro'),
(1088, 30, 'Trujillo'),
(1089, 30, 'Tulúa'),
(1090, 30, 'Ulloa'),
(1091, 30, 'Versalles'),
(1092, 30, 'Vijes'),
(1093, 30, 'Yotoco'),
(1094, 30, 'Yumbo'),
(1095, 30, 'Zarzal'),
(1096, 31, 'Carurú'),
(1097, 31, 'Mitú'),
(1098, 31, 'Taraira'),
(1099, 32, 'Cumaribo'),
(1100, 32, 'La Primavera'),
(1101, 32, 'Puerto Carreño'),
(1102, 32, 'Santa Rosalía');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nivel`
--

CREATE TABLE `nivel` (
  `idnivel` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `nivel`
--

INSERT INTO `nivel` (`idnivel`) VALUES
(0),
(1),
(2),
(3),
(4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `parametros`
--

CREATE TABLE `parametros` (
  `idParametros` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `valor` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `parametros`
--

INSERT INTO `parametros` (`idParametros`, `nombre`, `valor`) VALUES
(1, 'Retefuente', '1.50'),
(2, 'Comisión Bolsa', '0.208948'),
(3, 'Fomento Arrocero', '0.50'),
(4, 'Humedad', '24'),
(5, 'Impureza', '3');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personalexterno`
--

CREATE TABLE `personalexterno` (
  `idPersonalExterno` int(11) NOT NULL,
  `idMunicipio` int(11) NOT NULL,
  `tipo` enum('agricultor','conductor') NOT NULL,
  `cedula` varchar(15) NOT NULL,
  `nombres` varchar(45) NOT NULL,
  `apellidos` varchar(45) NOT NULL,
  `direccion` varchar(45) NOT NULL,
  `telefono` varchar(12) NOT NULL,
  `telefono2` varchar(12) DEFAULT NULL,
  `telefono3` varchar(12) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `personalexterno`
--

INSERT INTO `personalexterno` (`idPersonalExterno`, `idMunicipio`, `tipo`, `cedula`, `nombres`, `apellidos`, `direccion`, `telefono`, `telefono2`, `telefono3`) VALUES
(1, 1021, 'agricultor', '1105689625', 'Jose Hernanda', 'Orjuela Vasquez', 'La caimanera', '3145433423', '', ''),
(2, 1022, 'agricultor', '1105680851', 'Jonathan David', 'Perez Guzman', 'Calle 2 ##7-23 B/San Rafael', '3156057003', '2484369', ''),
(3, 1023, 'agricultor', '93135529', 'Edgar de Jesus ', 'Ramirez Perdomo', 'Vereda La Joya', '3202924407', '', ''),
(4, 1025, 'agricultor', '28710378', 'Juan Pablo', 'Capera Cortes', 'Vereda Santa Ana', '3123420087', '2483456', '2487023'),
(5, 1021, 'agricultor', '65692470', 'Jorge Diego', 'Trujillo Guzman', 'Manzana D Casa 18 B/ San Francisco', '3214652051', '', ''),
(6, 1023, 'agricultor', '110768956', 'Edwin Mauricio', 'Melo Cartagena', 'Calle 6 #4-32', '3208007887', '', ''),
(7, 1021, 'agricultor', '1106789667', 'Ana Beatriz', 'Aguirre Borja', 'Vereda Coello', '3214534545', '2486087', ''),
(8, 1021, 'agricultor', '65456786', 'Holman Geovanny', 'Reyes Buitrago', 'Cra 4 #4-57', '3234524545', '', ''),
(9, 1021, 'agricultor', '93134567', 'Carlos Andres', 'Ospina Buitrago', 'Calle 6 #12-34', '3004323345', '', ''),
(10, 1021, 'agricultor', '1101089667', 'Edwin Oswaldo', 'Perea Rojas', 'Vereda Pasoancho', '3224534545', '', ''),
(11, 1021, 'conductor', '2205680851', 'Jorge Andres', 'Barragan Barreto', 'Mza 1 casa 23 B/ La Magdalena', '3242386756', '', ''),
(12, 1022, 'conductor', '98366453', 'Cristhian Alberto', 'Vasquez Villarraga', 'Cra 8 #11-22', '314415500', '', ''),
(13, 1023, 'conductor', '72345678', 'Victor Andres', 'Orjuela Vargas', 'Mza 6 casa 9 B/Belen', '3214567800', '', ''),
(14, 1021, 'conductor', '11010204543', 'Luis Alejandro', 'Sandoval Castro', 'Vda Colegio', '305903234', '', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `privilegios`
--

CREATE TABLE `privilegios` (
  `idPrivilegios` int(11) NOT NULL,
  `idnivel` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `privilegios`
--

INSERT INTO `privilegios` (`idPrivilegios`, `idnivel`, `nombre`) VALUES
(1, 0, 'basculista'),
(2, 0, 'laboratorista'),
(3, 0, 'contador'),
(4, 1, 'supervisor'),
(5, 2, 'gerente'),
(6, 3, 'administrador'),
(7, 4, 'auditor');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `procedimiento`
--

CREATE TABLE `procedimiento` (
  `idProcedimiento` int(11) NOT NULL,
  `idSilos` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `hora` time NOT NULL,
  `estado` enum('proceso','finalizado') NOT NULL,
  `tipoAlmacenamiento` enum('silo','trincho') DEFAULT NULL,
  `observacion` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `secadora`
--

CREATE TABLE `secadora` (
  `idSecadora` int(11) NOT NULL,
  `idBateria` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `estado` enum('encendido','apagado') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `secadora`
--

INSERT INTO `secadora` (`idSecadora`, `idBateria`, `nombre`, `estado`) VALUES
(1, 1, 'A1', 'apagado'),
(2, 1, 'A2', 'apagado'),
(3, 1, 'A3', 'apagado'),
(4, 2, 'B4', 'apagado'),
(5, 2, 'B5', 'apagado'),
(6, 2, 'B6', 'apagado'),
(7, 2, 'B7', 'apagado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `silos`
--

CREATE TABLE `silos` (
  `idSilos` int(11) NOT NULL,
  `idSecadora` int(11) NOT NULL,
  `numero` int(11) NOT NULL,
  `kilos` decimal(7,2) NOT NULL,
  `estado` enum('vacio','lleno','contenido') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `silos`
--

INSERT INTO `silos` (`idSilos`, `idSecadora`, `numero`, `kilos`, `estado`) VALUES
(1, 1, 1, '0.00', 'vacio'),
(2, 1, 2, '0.00', 'vacio'),
(3, 1, 3, '0.00', 'vacio'),
(4, 1, 4, '0.00', 'vacio'),
(5, 1, 5, '0.00', 'vacio'),
(6, 2, 6, '0.00', 'vacio'),
(7, 2, 7, '0.00', 'vacio'),
(8, 2, 8, '0.00', 'vacio'),
(9, 2, 9, '0.00', 'vacio'),
(10, 2, 10, '0.00', 'vacio'),
(11, 2, 11, '0.00', 'vacio'),
(12, 2, 12, '0.00', 'vacio'),
(13, 2, 13, '0.00', 'vacio'),
(14, 2, 14, '0.00', 'vacio'),
(15, 2, 15, '0.00', 'vacio'),
(16, 3, 16, '0.00', 'vacio'),
(17, 3, 17, '0.00', 'vacio'),
(18, 3, 18, '0.00', 'vacio'),
(19, 3, 19, '0.00', 'vacio'),
(20, 3, 20, '0.00', 'vacio'),
(21, 7, 1, '0.00', 'vacio'),
(22, 7, 2, '0.00', 'vacio'),
(23, 7, 3, '0.00', 'vacio'),
(24, 7, 4, '0.00', 'vacio'),
(25, 7, 5, '0.00', 'vacio'),
(26, 7, 6, '0.00', 'vacio'),
(27, 6, 7, '0.00', 'vacio'),
(28, 6, 8, '0.00', 'vacio'),
(29, 6, 9, '0.00', 'vacio'),
(30, 6, 10, '0.00', 'vacio'),
(31, 6, 11, '0.00', 'vacio'),
(32, 6, 12, '0.00', 'vacio'),
(33, 5, 13, '0.00', 'vacio'),
(34, 5, 14, '0.00', 'vacio'),
(35, 5, 15, '0.00', 'vacio'),
(36, 5, 16, '0.00', 'vacio'),
(37, 5, 17, '0.00', 'vacio'),
(38, 5, 18, '0.00', 'vacio'),
(39, 4, 19, '0.00', 'vacio'),
(40, 4, 20, '0.00', 'vacio'),
(41, 4, 21, '0.00', 'vacio'),
(42, 4, 22, '0.00', 'vacio'),
(43, 4, 23, '0.00', 'vacio'),
(44, 4, 24, '0.00', 'vacio');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipodearroz`
--

CREATE TABLE `tipodearroz` (
  `idTipoDeArroz` int(11) NOT NULL,
  `idVariedad` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `descripcion` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tipodearroz`
--

INSERT INTO `tipodearroz` (`idTipoDeArroz`, `idVariedad`, `nombre`, `descripcion`) VALUES
(1, 1, 'Rojo', 'Es uno de los arroces más comunes, \\nde grano corto o medio.'),
(2, 2, 'Pady', 'Originario de las regiones del Himalaya y muy utilizado en la cocina india.'),
(3, 1, 'Partidos', 'E de grano largo, y además de tener\\n un mayor número de proteínas que\\n el arroz, requiere de más tiempo\\n para ser cocinado.');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tiquete`
--

CREATE TABLE `tiquete` (
  `idTiquete` int(11) NOT NULL,
  `idAgricultor` int(11) NOT NULL,
  `idTipoDeArroz` int(11) NOT NULL,
  `idLote` int(11) DEFAULT NULL,
  `idVehiculo` int(11) DEFAULT NULL,
  `idConductor` int(11) DEFAULT NULL,
  `user` varchar(30) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `kilosBrutos` decimal(7,2) DEFAULT NULL,
  `destare` decimal(7,2) DEFAULT NULL,
  `kilosNetos` decimal(7,2) DEFAULT NULL,
  `observacion` text,
  `empaque` enum('granel','bultos') DEFAULT NULL,
  `humedadUno` decimal(5,2) NOT NULL,
  `impurezaUno` decimal(5,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tiquete`
--

INSERT INTO `tiquete` (`idTiquete`, `idAgricultor`, `idTipoDeArroz`, `idLote`, `idVehiculo`, `idConductor`, `user`, `fecha`, `kilosBrutos`, `destare`, `kilosNetos`, `observacion`, `empaque`, `humedadUno`, `impurezaUno`) VALUES
(1, 10, 2, 1, 1, 11, 'b', '2018-01-28 01:27:27', '15250.00', '1171.00', '14079.00', 'hello', NULL, '2.20', '10.00'),
(2, 7, 2, 2, 2, 13, 'b', '2018-01-24 00:00:00', '1655.00', '1076.00', '579.00', 'qwer', 'granel', '9.99', '10.00'),
(3, 1, 1, 3, 1, 14, 'b', '2018-01-23 00:00:00', '4321.00', '5000.00', '5200.00', 'Trincho 2', NULL, '1.20', '2.00'),
(4, 8, 2, 1, 2, 11, 'b', '2018-01-28 03:55:53', '1574.00', '1119.00', '455.00', 'oooo', NULL, '2.40', '4.00'),
(5, 7, 3, 2, 3, 12, 'b', '2018-01-26 11:25:31', '4573.00', '861.00', '3712.00', 'Trincho 3', NULL, '9.99', '10.00'),
(6, 8, 2, 1, 1, 11, 'b', '2018-01-28 09:33:23', '4275.00', '1304.00', '2971.00', 'asdfg', NULL, '9.99', '10.00'),
(7, 10, 2, 1, 3, 14, 'b', '2018-01-29 01:03:13', '2177.00', '0.00', '0.00', 'wewew', NULL, '13.00', '12.00'),
(8, 10, 2, 1, 2, 13, 'b', '2018-02-05 11:05:37', '4485.00', '989.00', '3496.00', 'aaa', NULL, '11.00', '10.00'),
(9, 5, 2, NULL, NULL, 12, 'l', NULL, NULL, NULL, NULL, NULL, NULL, '24.00', '3.00'),
(10, 8, 1, 1, 3, 13, 'b', '2018-02-06 01:39:16', '4839.00', '964.00', '3875.00', 'qwert', NULL, '22.00', '3.00'),
(11, 9, 1, NULL, NULL, 14, 'l', NULL, NULL, NULL, NULL, NULL, NULL, '23.00', '4.00'),
(12, 8, 2, 1, 2, 11, 'b', '2018-02-06 02:28:09', '2554.00', '1230.00', '1324.00', 'sw', NULL, '27.00', '5.00'),
(13, 2, 1, 1, 1, 14, 'b', '2018-02-06 01:47:40', '2883.00', '0.00', '0.00', 'as', NULL, '22.00', '2.00'),
(14, 2, 1, 1, 1, 12, 'b', '2018-02-06 02:47:17', '4039.00', '0.00', '0.00', '', NULL, '22.00', '3.00'),
(15, 3, 2, NULL, NULL, 13, 'l', NULL, NULL, NULL, NULL, NULL, NULL, '24.00', '4.00'),
(16, 7, 2, NULL, NULL, 11, 'l', NULL, NULL, NULL, NULL, NULL, NULL, '26.00', '3.00'),
(17, 9, 2, 1, 2, 11, 'b', '2018-02-10 11:02:46', '2804.00', '1084.00', '1720.00', '', NULL, '30.00', '5.00'),
(18, 4, 3, NULL, NULL, 11, 'l', NULL, NULL, NULL, NULL, NULL, NULL, '25.00', '4.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tiqueteensilos`
--

CREATE TABLE `tiqueteensilos` (
  `idTiqueteSilos` int(11) NOT NULL,
  `idTiquete` int(11) NOT NULL,
  `idSilos` int(11) NOT NULL,
  `kilos` decimal(7,2) NOT NULL,
  `estado` enum('secamiento','trilla') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tiqueteVarios`
--

CREATE TABLE `tiqueteVarios` (
  `idTiqueteVarios` int(11) NOT NULL,
  `idConductor` int(11) NOT NULL,
  `user` varchar(30) NOT NULL,
  `idVehiculo` int(11) NOT NULL,
  `fecha` datetime NOT NULL,
  `destino` text,
  `observacion` text,
  `kilosBrutos` decimal(7,2) NOT NULL,
  `destare` decimal(7,2) DEFAULT NULL,
  `kilosNetos` decimal(7,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tiqueteVarios`
--

INSERT INTO `tiqueteVarios` (`idTiqueteVarios`, `idConductor`, `user`, `idVehiculo`, `fecha`, `destino`, `observacion`, `kilosBrutos`, `destare`, `kilosNetos`) VALUES
(1, 11, 'b', 1, '2018-01-23 00:00:00', 'Trincho1', 'En movimiento', '1567.00', '1387.00', '180.00'),
(2, 12, 'b', 3, '2018-01-24 00:00:00', 'Selva', 'Trincho 15', '3461.00', '0.00', '0.00'),
(3, 13, 'b', 3, '2018-01-24 00:00:00', 'ere', 'ere', '4366.00', '0.00', '0.00'),
(4, 13, 'b', 3, '2018-01-24 00:00:00', 'eerer', 'erer', '3709.00', '0.00', '0.00'),
(5, 13, 'b', 3, '2018-01-24 00:00:00', 'qqqq', 'wwww', '3487.00', '0.00', '0.00'),
(6, 13, 'b', 2, '2018-01-25 00:00:00', 'Trincho 6', 'klklkl', '3050.00', '0.00', '0.00'),
(7, 13, 'b', 3, '2018-01-25 00:00:00', 'prueba J', 'prueba js', '2092.00', '0.00', '0.00'),
(8, 14, 'b', 3, '2018-01-25 00:00:00', 'ok', 'ok', '1610.00', '0.00', '0.00'),
(9, 13, 'b', 1, '2018-01-25 00:00:00', 'ok no ', 'ok si', '0.00', '0.00', '0.00'),
(10, 13, 'b', 3, '2018-01-25 00:00:00', 'ok', 'qwer', '1922.00', '0.00', '0.00'),
(11, 12, 'b', 3, '2018-01-25 00:00:00', 'waka', 'waka', '0.00', '0.00', '0.00'),
(12, 12, 'b', 2, '2018-01-25 00:00:00', 'kkkkk', 'kkoko', '0.00', '0.00', '0.00'),
(13, 14, 'b', 2, '2018-01-25 00:00:00', 'gfghfhg', 'regr', '3491.00', '0.00', '0.00'),
(14, 12, 'b', 3, '2018-01-30 12:23:47', 'fgfg', 'ghfghfgh', '4617.00', '1229.00', '3388.00'),
(15, 13, 'b', 2, '2018-01-29 09:47:12', 'Trincho 4', 'Cargamento basura.', '4962.00', '1291.00', '3671.00'),
(16, 13, 'b', 3, '2018-01-26 11:32:30', 'Bodega 2', 'Para secamiento', '2113.00', '943.00', '1170.00'),
(17, 13, 'b', 2, '2018-01-28 09:25:43', '', 'weer', '1801.00', '0.00', '0.00'),
(18, 13, 'b', 1, '2018-01-29 03:20:27', 'Trincho 8', 'Listo para almacenamiento', '4212.00', '0.00', '0.00'),
(19, 13, 'b', 1, '2018-01-29 03:20:59', 'Trincho 8', 'Listo para almacenamiento', '4212.00', '0.00', '0.00'),
(20, 11, 'b', 2, '2018-01-29 09:25:30', 'fernanda', 'lerolero', '4997.00', '1001.00', '3996.00'),
(21, 13, 'b', 1, '2018-01-29 09:32:02', 'Trincho lola', 'Listo para secamientosss', '2488.00', '1082.00', '1406.00'),
(22, 11, 'b', 1, '2018-01-30 12:21:28', 'sdsa', 'Epaguetti', '4632.00', '1175.00', '3457.00'),
(23, 14, 'b', 3, '2018-01-29 09:32:41', 'tyetryt', '43534', '2040.00', '0.00', '0.00'),
(24, 12, 'b', 2, '2018-01-29 09:44:04', 'trg', 'tyhytf', '4299.00', '1208.00', '3091.00'),
(25, 13, 'b', 1, '2018-01-29 09:47:41', '6', '0oo', '1593.00', '0.00', '0.00'),
(26, 14, 'b', 1, '2018-01-29 09:47:50', '787', '', '1581.00', '870.00', '711.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `user` varchar(30) NOT NULL,
  `idPrivilegios` int(11) NOT NULL,
  `contrasena` tinytext NOT NULL,
  `estado` enum('activo','inactivo') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`user`, `idPrivilegios`, `contrasena`, `estado`) VALUES
('b', 1, 'SUuFcdv+8nRxQzY85TcIeA==', 'activo'),
('c', 3, 'agkkACcpxr4wjFD6vObWAQ==', 'activo'),
('g', 5, 'agkkACcpxr4wjFD6vObWAQ==', 'activo'),
('l', 2, 'SUuFcdv+8nRxQzY85TcIeA==', 'activo'),
('lizeth', 1, 'qP8SMR9mMaJzMGfGo4s0dA==', 'activo'),
('x', 6, 'SUuFcdv+8nRxQzY85TcIeA==', 'activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `variedad`
--

CREATE TABLE `variedad` (
  `idVariedad` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `variedad`
--

INSERT INTO `variedad` (`idVariedad`, `nombre`) VALUES
(1, 'thana'),
(2, 'fedearroz');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vehiculo`
--

CREATE TABLE `vehiculo` (
  `idVehiculo` int(11) NOT NULL,
  `idMarca` int(11) NOT NULL,
  `modelo` varchar(45) DEFAULT NULL,
  `placa` varchar(6) NOT NULL,
  `color` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `vehiculo`
--

INSERT INTO `vehiculo` (`idVehiculo`, `idMarca`, `modelo`, `placa`, `color`) VALUES
(1, 5, '2012', 'ZHX01C', 'AZUL'),
(2, 6, '2015', 'KSA13B', 'NEGRO'),
(3, 2, '2000', 'KMW12G', 'ROJO');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `bateria`
--
ALTER TABLE `bateria`
  ADD PRIMARY KEY (`idBateria`);

--
-- Indices de la tabla `departamentos`
--
ALTER TABLE `departamentos`
  ADD PRIMARY KEY (`idDepartamento`);

--
-- Indices de la tabla `detalleliquidacion`
--
ALTER TABLE `detalleliquidacion`
  ADD PRIMARY KEY (`idDetalleLiquidacion`),
  ADD UNIQUE KEY `idTiquete_UNIQUE` (`idTiquete`),
  ADD KEY `fk_liquidaciones_has_tiquete_tiquete1_idx` (`idTiquete`),
  ADD KEY `fk_detalleliquidacion_liquidaciones1_idx` (`idLiquidaciones`);

--
-- Indices de la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD PRIMARY KEY (`idEmpleado`),
  ADD UNIQUE KEY `ccEmpleado_UNIQUE` (`ccEmpleado`),
  ADD KEY `fk_empleado_usuario1_idx` (`user`),
  ADD KEY `fk_empleado_municipios1` (`idMunicipio`);

--
-- Indices de la tabla `entradas`
--
ALTER TABLE `entradas`
  ADD PRIMARY KEY (`idEntradas`),
  ADD KEY `fk_entradas_tiqueteVarios1_idx` (`idTiqueteVarios`);

--
-- Indices de la tabla `etapa`
--
ALTER TABLE `etapa`
  ADD PRIMARY KEY (`idHistorialEtapa`),
  ADD KEY `fk_procedimiento_has_etapas_procedimiento1_idx` (`idProcedimiento`);

--
-- Indices de la tabla `funciones`
--
ALTER TABLE `funciones`
  ADD PRIMARY KEY (`idFunciones`);

--
-- Indices de la tabla `funcionesprivilegios`
--
ALTER TABLE `funcionesprivilegios`
  ADD PRIMARY KEY (`idFuncionesPrivilegio`),
  ADD KEY `fk_funciones_has_privilegios_privilegios1_idx` (`idPrivilegios`),
  ADD KEY `fk_funciones_has_privilegios_funciones1_idx` (`idFunciones`);

--
-- Indices de la tabla `laboratorio`
--
ALTER TABLE `laboratorio`
  ADD PRIMARY KEY (`idLaboratorio`),
  ADD KEY `fk_laboratorio_tiquete1_idx` (`idTiquete`),
  ADD KEY `fk_laboratorio_usuario1_idx` (`user`);

--
-- Indices de la tabla `liquidaciones`
--
ALTER TABLE `liquidaciones`
  ADD PRIMARY KEY (`idLiquidaciones`);

--
-- Indices de la tabla `lote`
--
ALTER TABLE `lote`
  ADD PRIMARY KEY (`idLote`),
  ADD KEY `fk_zona_municipios1_idx` (`idMunicipio`);

--
-- Indices de la tabla `marca`
--
ALTER TABLE `marca`
  ADD PRIMARY KEY (`idMarca`);

--
-- Indices de la tabla `muetraestufa`
--
ALTER TABLE `muetraestufa`
  ADD PRIMARY KEY (`idmuetraestufa`),
  ADD KEY `fk_muetraestufa_laboratorio1_idx` (`idLaboratorio`);

--
-- Indices de la tabla `municipios`
--
ALTER TABLE `municipios`
  ADD PRIMARY KEY (`idMunicipio`),
  ADD KEY `fk_municipios_departamentos1` (`idDepartamento`);

--
-- Indices de la tabla `nivel`
--
ALTER TABLE `nivel`
  ADD PRIMARY KEY (`idnivel`);

--
-- Indices de la tabla `parametros`
--
ALTER TABLE `parametros`
  ADD PRIMARY KEY (`idParametros`);

--
-- Indices de la tabla `personalexterno`
--
ALTER TABLE `personalexterno`
  ADD PRIMARY KEY (`idPersonalExterno`),
  ADD UNIQUE KEY `ccConductor_UNIQUE` (`cedula`),
  ADD KEY `fk_personalExterno_municipios1_idx` (`idMunicipio`);

--
-- Indices de la tabla `privilegios`
--
ALTER TABLE `privilegios`
  ADD PRIMARY KEY (`idPrivilegios`),
  ADD KEY `fk_privilegios_nivel1_idx` (`idnivel`);

--
-- Indices de la tabla `procedimiento`
--
ALTER TABLE `procedimiento`
  ADD PRIMARY KEY (`idProcedimiento`),
  ADD KEY `fk_procedimiento_silos1_idx` (`idSilos`);

--
-- Indices de la tabla `secadora`
--
ALTER TABLE `secadora`
  ADD PRIMARY KEY (`idSecadora`),
  ADD KEY `fk_seccion_bateria1_idx` (`idBateria`);

--
-- Indices de la tabla `silos`
--
ALTER TABLE `silos`
  ADD PRIMARY KEY (`idSilos`),
  ADD KEY `fk_silos_secadora1_idx` (`idSecadora`);

--
-- Indices de la tabla `tipodearroz`
--
ALTER TABLE `tipodearroz`
  ADD PRIMARY KEY (`idTipoDeArroz`),
  ADD KEY `fk_tipodearroz_variedad1_idx` (`idVariedad`);

--
-- Indices de la tabla `tiquete`
--
ALTER TABLE `tiquete`
  ADD PRIMARY KEY (`idTiquete`),
  ADD KEY `fk_tiquete_usuario1_idx` (`user`),
  ADD KEY `fk_tiquete_vehiculo1_idx` (`idVehiculo`),
  ADD KEY `fk_tiquete_personalExterno1_idx` (`idConductor`),
  ADD KEY `fk_tiquete_personalExterno2_idx` (`idAgricultor`),
  ADD KEY `fk_tiquete_tipodearroz1_idx` (`idTipoDeArroz`),
  ADD KEY `fk_tiquete_lote1_idx` (`idLote`);

--
-- Indices de la tabla `tiqueteensilos`
--
ALTER TABLE `tiqueteensilos`
  ADD PRIMARY KEY (`idTiqueteSilos`),
  ADD KEY `fk_tiquete_has_silos_silos1_idx` (`idSilos`),
  ADD KEY `fk_tiquete_has_silos_tiquete1_idx` (`idTiquete`);

--
-- Indices de la tabla `tiqueteVarios`
--
ALTER TABLE `tiqueteVarios`
  ADD PRIMARY KEY (`idTiqueteVarios`),
  ADD KEY `fk_tiqueteVarios_vehiculo1_idx` (`idVehiculo`),
  ADD KEY `fk_tiqueteVarios_usuario1_idx` (`user`),
  ADD KEY `fk_tiqueteVarios_personalExterno1_idx` (`idConductor`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`user`),
  ADD KEY `fk_usuario_privilegios1_idx` (`idPrivilegios`);

--
-- Indices de la tabla `variedad`
--
ALTER TABLE `variedad`
  ADD PRIMARY KEY (`idVariedad`);

--
-- Indices de la tabla `vehiculo`
--
ALTER TABLE `vehiculo`
  ADD PRIMARY KEY (`idVehiculo`),
  ADD UNIQUE KEY `placa_UNIQUE` (`placa`),
  ADD KEY `fk_vehiculo_marca1_idx` (`idMarca`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `bateria`
--
ALTER TABLE `bateria`
  MODIFY `idBateria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `detalleliquidacion`
--
ALTER TABLE `detalleliquidacion`
  MODIFY `idDetalleLiquidacion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT de la tabla `empleado`
--
ALTER TABLE `empleado`
  MODIFY `idEmpleado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT de la tabla `entradas`
--
ALTER TABLE `entradas`
  MODIFY `idEntradas` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT de la tabla `etapa`
--
ALTER TABLE `etapa`
  MODIFY `idHistorialEtapa` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `funciones`
--
ALTER TABLE `funciones`
  MODIFY `idFunciones` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `funcionesprivilegios`
--
ALTER TABLE `funcionesprivilegios`
  MODIFY `idFuncionesPrivilegio` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `laboratorio`
--
ALTER TABLE `laboratorio`
  MODIFY `idLaboratorio` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `liquidaciones`
--
ALTER TABLE `liquidaciones`
  MODIFY `idLiquidaciones` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT de la tabla `lote`
--
ALTER TABLE `lote`
  MODIFY `idLote` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT de la tabla `marca`
--
ALTER TABLE `marca`
  MODIFY `idMarca` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;
--
-- AUTO_INCREMENT de la tabla `muetraestufa`
--
ALTER TABLE `muetraestufa`
  MODIFY `idmuetraestufa` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `parametros`
--
ALTER TABLE `parametros`
  MODIFY `idParametros` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT de la tabla `personalexterno`
--
ALTER TABLE `personalexterno`
  MODIFY `idPersonalExterno` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT de la tabla `privilegios`
--
ALTER TABLE `privilegios`
  MODIFY `idPrivilegios` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT de la tabla `procedimiento`
--
ALTER TABLE `procedimiento`
  MODIFY `idProcedimiento` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `secadora`
--
ALTER TABLE `secadora`
  MODIFY `idSecadora` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT de la tabla `silos`
--
ALTER TABLE `silos`
  MODIFY `idSilos` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;
--
-- AUTO_INCREMENT de la tabla `tipodearroz`
--
ALTER TABLE `tipodearroz`
  MODIFY `idTipoDeArroz` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `tiquete`
--
ALTER TABLE `tiquete`
  MODIFY `idTiquete` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT de la tabla `tiqueteensilos`
--
ALTER TABLE `tiqueteensilos`
  MODIFY `idTiqueteSilos` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `tiqueteVarios`
--
ALTER TABLE `tiqueteVarios`
  MODIFY `idTiqueteVarios` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;
--
-- AUTO_INCREMENT de la tabla `variedad`
--
ALTER TABLE `variedad`
  MODIFY `idVariedad` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `vehiculo`
--
ALTER TABLE `vehiculo`
  MODIFY `idVehiculo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalleliquidacion`
--
ALTER TABLE `detalleliquidacion`
  ADD CONSTRAINT `fk_detalleliquidacion_liquidaciones1` FOREIGN KEY (`idLiquidaciones`) REFERENCES `liquidaciones` (`idLiquidaciones`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_liquidaciones_has_tiquete_tiquete1` FOREIGN KEY (`idTiquete`) REFERENCES `tiquete` (`idTiquete`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD CONSTRAINT `fk_empleado_municipios1` FOREIGN KEY (`idMunicipio`) REFERENCES `municipios` (`idMunicipio`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_empleado_usuario1` FOREIGN KEY (`user`) REFERENCES `usuario` (`user`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `entradas`
--
ALTER TABLE `entradas`
  ADD CONSTRAINT `fk_entradas_tiqueteVarios1` FOREIGN KEY (`idTiqueteVarios`) REFERENCES `tiqueteVarios` (`idTiqueteVarios`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `etapa`
--
ALTER TABLE `etapa`
  ADD CONSTRAINT `fk_procedimiento_has_etapas_procedimiento1` FOREIGN KEY (`idProcedimiento`) REFERENCES `procedimiento` (`idProcedimiento`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `funcionesprivilegios`
--
ALTER TABLE `funcionesprivilegios`
  ADD CONSTRAINT `fk_funciones_has_privilegios_funciones1` FOREIGN KEY (`idFunciones`) REFERENCES `funciones` (`idFunciones`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_funciones_has_privilegios_privilegios1` FOREIGN KEY (`idPrivilegios`) REFERENCES `privilegios` (`idPrivilegios`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `laboratorio`
--
ALTER TABLE `laboratorio`
  ADD CONSTRAINT `fk_laboratorio_tiquete1` FOREIGN KEY (`idTiquete`) REFERENCES `tiquete` (`idTiquete`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_laboratorio_usuario1` FOREIGN KEY (`user`) REFERENCES `usuario` (`user`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `lote`
--
ALTER TABLE `lote`
  ADD CONSTRAINT `fk_zona_municipios1` FOREIGN KEY (`idMunicipio`) REFERENCES `municipios` (`idMunicipio`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `muetraestufa`
--
ALTER TABLE `muetraestufa`
  ADD CONSTRAINT `fk_muetraestufa_laboratorio1` FOREIGN KEY (`idLaboratorio`) REFERENCES `laboratorio` (`idLaboratorio`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `municipios`
--
ALTER TABLE `municipios`
  ADD CONSTRAINT `fk_municipios_departamentos1` FOREIGN KEY (`idDepartamento`) REFERENCES `departamentos` (`idDepartamento`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `personalexterno`
--
ALTER TABLE `personalexterno`
  ADD CONSTRAINT `fk_personalExterno_municipios1` FOREIGN KEY (`idMunicipio`) REFERENCES `municipios` (`idMunicipio`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `privilegios`
--
ALTER TABLE `privilegios`
  ADD CONSTRAINT `fk_privilegios_nivel1` FOREIGN KEY (`idnivel`) REFERENCES `nivel` (`idnivel`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `procedimiento`
--
ALTER TABLE `procedimiento`
  ADD CONSTRAINT `fk_procedimiento_silos1` FOREIGN KEY (`idSilos`) REFERENCES `silos` (`idSilos`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `secadora`
--
ALTER TABLE `secadora`
  ADD CONSTRAINT `fk_seccion_bateria1` FOREIGN KEY (`idBateria`) REFERENCES `bateria` (`idBateria`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `silos`
--
ALTER TABLE `silos`
  ADD CONSTRAINT `fk_silos_secadora1` FOREIGN KEY (`idSecadora`) REFERENCES `secadora` (`idSecadora`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `tipodearroz`
--
ALTER TABLE `tipodearroz`
  ADD CONSTRAINT `fk_tipodearroz_variedad1` FOREIGN KEY (`idVariedad`) REFERENCES `variedad` (`idVariedad`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `tiquete`
--
ALTER TABLE `tiquete`
  ADD CONSTRAINT `fk_tiquete_lote1` FOREIGN KEY (`idLote`) REFERENCES `lote` (`idLote`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_tiquete_personalExterno1` FOREIGN KEY (`idConductor`) REFERENCES `personalexterno` (`idPersonalExterno`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_tiquete_personalExterno2` FOREIGN KEY (`idAgricultor`) REFERENCES `personalexterno` (`idPersonalExterno`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_tiquete_tipodearroz1` FOREIGN KEY (`idTipoDeArroz`) REFERENCES `tipodearroz` (`idTipoDeArroz`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_tiquete_usuario1` FOREIGN KEY (`user`) REFERENCES `usuario` (`user`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_tiquete_vehiculo1` FOREIGN KEY (`idVehiculo`) REFERENCES `vehiculo` (`idVehiculo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `tiqueteensilos`
--
ALTER TABLE `tiqueteensilos`
  ADD CONSTRAINT `fk_tiquete_has_silos_silos1` FOREIGN KEY (`idSilos`) REFERENCES `silos` (`idSilos`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_tiquete_has_silos_tiquete1` FOREIGN KEY (`idTiquete`) REFERENCES `tiquete` (`idTiquete`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `tiqueteVarios`
--
ALTER TABLE `tiqueteVarios`
  ADD CONSTRAINT `fk_tiqueteVarios_personalExterno1` FOREIGN KEY (`idConductor`) REFERENCES `personalexterno` (`idPersonalExterno`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_tiqueteVarios_usuario1` FOREIGN KEY (`user`) REFERENCES `usuario` (`user`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_tiqueteVarios_vehiculo1` FOREIGN KEY (`idVehiculo`) REFERENCES `vehiculo` (`idVehiculo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `fk_usuario_privilegios1` FOREIGN KEY (`idPrivilegios`) REFERENCES `privilegios` (`idPrivilegios`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `vehiculo`
--
ALTER TABLE `vehiculo`
  ADD CONSTRAINT `fk_vehiculo_marca1` FOREIGN KEY (`idMarca`) REFERENCES `marca` (`idMarca`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 02-04-2018 a las 07:01:39
-- Versión del servidor: 10.1.30-MariaDB
-- Versión de PHP: 7.2.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `pos`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pos_info`
--

CREATE TABLE `pos_info` (
  `info_id` int(11) NOT NULL,
  `info_name` varchar(100) NOT NULL,
  `info_num` int(10) NOT NULL,
  `info_address` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pos_info`
--

INSERT INTO `pos_info` (`info_id`, `info_name`, `info_num`, `info_address`) VALUES
(1, 'Tienda de Poas', 87486547, '450 mts sur del parque central de San Pedro de Poas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pos_inventory`
--

CREATE TABLE `pos_inventory` (
  `inv_id` int(11) NOT NULL,
  `inv_code` int(11) NOT NULL,
  `inv_name` varchar(100) NOT NULL,
  `inv_desc` varchar(150) NOT NULL,
  `inv_cant` int(10) NOT NULL,
  `inv_price` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pos_inventory`
--

INSERT INTO `pos_inventory` (`inv_id`, `inv_code`, `inv_name`, `inv_desc`, `inv_cant`, `inv_price`) VALUES
(6, 6, 'nombreProductoed', '...', 20, 1850),
(8, 9090, 'nuevo producto de lo mejor', 'Esto es de los mejores', 3, 10500);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pos_invoice`
--

CREATE TABLE `pos_invoice` (
  `invoice_id` int(11) NOT NULL,
  `invoice_cust_name` varchar(100) NOT NULL,
  `invoice_cust_address` varchar(250) NOT NULL,
  `invoice_cust_id` varchar(15) NOT NULL,
  `invoice_cust_tel` int(20) NOT NULL,
  `invoice_iv` int(11) NOT NULL,
  `invoice_total` int(11) NOT NULL,
  `invoice_total_iv` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pos_invoice`
--

INSERT INTO `pos_invoice` (`invoice_id`, `invoice_cust_name`, `invoice_cust_address`, `invoice_cust_id`, `invoice_cust_tel`, `invoice_iv`, `invoice_total`, `invoice_total_iv`) VALUES
(1, '123', '123', '123', 123, 2000, 2260, 260),
(2, 'Jose Luna', 'San Pedro de poas', '87486547', 2724556, 2000, 2260, 260),
(3, '123', '123', '123', 123, 2000, 2260, 260),
(4, '123', '123', '123', 123, 2000, 2260, 260),
(5, '123', '123', '123', 123, 2000, 2260, 260),
(6, '123', '123', '123', 123, 2000, 2260, 260),
(7, '123', '123', '123', 123, 2000, 2260, 260),
(8, '123', '123', '123', 123, 2000, 2260, 260),
(9, '123', '123', '123', 123, 2000, 2260, 260),
(10, '123', '123', '123', 123, 14000, 15820, 1820),
(11, '123', '123', '123', 123, 60000, 67800, 7800),
(12, '123', '123', '123', 123, 84000, 94920, 10920),
(13, 'asd', 'asd', 'asd', 123, 6000, 6780, 780),
(14, 'asd', 'asdasd', 'asd', 123, 24000, 27120, 3120),
(15, 'asd', 'asd', 'asd', 123, 2000, 2260, 260),
(16, '123', '123', '123', 123, 2000, 2260, 260),
(17, '123', '123', '123', 123, 2000, 2260, 260),
(18, '123', '123', '123', 123, 2000, 2260, 260),
(19, '123', '123', '123', 123, 2000, 2260, 260),
(20, 'nombre', 'desc desc ', '2724556', 87486547, 24000, 27120, 3120),
(21, '123', '123', '123', 123, 2000, 2260, 260),
(22, 'Jose Luna Gonzalez', '400mts sur parque san pedro de poas', '2724556', 87486547, 16000, 18080, 2080),
(23, '123', '123', '123', 123, 2000, 2260, 260),
(24, '123', '123', '123', 123, 2000, 2260, 260),
(25, '123', '123', '123', 123, 2000, 2260, 260),
(26, '123', '123', '123', 123, 2000, 2260, 260),
(27, '123', '123', '123', 123, 2000, 2260, 260),
(28, '123', '123', '123', 123, 2000, 2260, 260),
(29, '123', '123', '123', 123, 2000, 2260, 260),
(30, '123', '123', '123', 123, 2000, 2260, 260),
(31, '123', '123', '123', 123, 2000, 2260, 260),
(32, 'Luna', '123', '123', 123, 2000, 2260, 260),
(33, 'Luna', '123', '123', 123, 2000, 2260, 260),
(34, 'nombre', 'direccion', '2724556', 87486547, 2000, 2260, 260),
(35, '123', '123', '123', 123, 2000, 2260, 260),
(36, '123', '123', '123', 123, 2000, 2260, 260),
(37, '123', '123', '123', 123, 2000, 2260, 260),
(38, '123', '123', '123', 123, 2000, 2260, 260),
(39, '123', '123', '123', 123, 2000, 2260, 260),
(40, '123', '123', '123', 123, 2000, 2260, 260),
(41, 'Jose Luna', 'Poas, Alajuela', '2724556', 87486547, 100050, 113057, 13007),
(42, 'Jose Luna', 'Poas, Alajuela', '2724556', 87486547, 100050, 113057, 13007),
(43, 'Jose Luna', 'Poas, Alajuela', '2724556', 87486547, 100050, 113057, 13007),
(44, '123', '123', '123', 123, 63600, 71868, 8268),
(45, '123', '123', '123', 123, 63600, 71868, 8268);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pos_invoice_detail`
--

CREATE TABLE `pos_invoice_detail` (
  `detail_id` int(11) NOT NULL,
  `invoice_id_product` int(11) NOT NULL,
  `detail_id_invoice` int(10) NOT NULL,
  `detail_cant` int(10) NOT NULL,
  `detail_total` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pos_invoice_detail`
--

INSERT INTO `pos_invoice_detail` (`detail_id`, `invoice_id_product`, `detail_id_invoice`, `detail_cant`, `detail_total`) VALUES
(1, 0, 9, 1, 2000),
(2, 0, 10, 7, 14000),
(3, 0, 11, 12, 24000),
(4, 0, 11, 6, 36000),
(5, 0, 12, 12, 24000),
(6, 0, 12, 10, 60000),
(7, 0, 13, 1, 6000),
(8, 23, 19, 1, 2000),
(9, 6, 20, 3, 6000),
(10, 7, 20, 3, 18000),
(11, 6, 21, 1, 2000),
(12, 6, 22, 8, 16000),
(13, 6, 23, 1, 2000),
(14, 6, 24, 1, 2000),
(15, 6, 25, 1, 2000),
(16, 6, 26, 1, 2000),
(17, 6, 27, 1, 2000),
(18, 6, 28, 1, 2000),
(19, 6, 29, 1, 2000),
(20, 6, 30, 1, 2000),
(21, 6, 31, 1, 2000),
(22, 6, 32, 1, 2000),
(23, 6, 33, 1, 2000),
(24, 6, 34, 1, 2000),
(25, 6, 35, 1, 2000),
(26, 6, 36, 1, 2000),
(27, 6, 37, 1, 2000),
(28, 6, 38, 1, 2000),
(29, 6, 39, 1, 2000),
(30, 6, 40, 1, 2000),
(31, 8, 41, 9, 94500),
(32, 6, 41, 3, 5550),
(33, 8, 42, 9, 94500),
(34, 6, 42, 3, 5550),
(35, 8, 43, 9, 94500),
(36, 6, 43, 3, 5550),
(37, 8, 44, 5, 52500),
(38, 6, 44, 6, 11100),
(39, 8, 45, 5, 52500),
(40, 6, 45, 6, 11100);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pos_users`
--

CREATE TABLE `pos_users` (
  `user_id` int(11) NOT NULL,
  `user_name` varchar(20) NOT NULL,
  `user_password` varchar(20) NOT NULL,
  `user_permission` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pos_users`
--

INSERT INTO `pos_users` (`user_id`, `user_name`, `user_password`, `user_permission`) VALUES
(1, 'user', 'pass', 0),
(2, 'admin', 'admin', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `pos_info`
--
ALTER TABLE `pos_info`
  ADD PRIMARY KEY (`info_id`);

--
-- Indices de la tabla `pos_inventory`
--
ALTER TABLE `pos_inventory`
  ADD PRIMARY KEY (`inv_id`);

--
-- Indices de la tabla `pos_invoice`
--
ALTER TABLE `pos_invoice`
  ADD PRIMARY KEY (`invoice_id`);

--
-- Indices de la tabla `pos_invoice_detail`
--
ALTER TABLE `pos_invoice_detail`
  ADD PRIMARY KEY (`detail_id`);

--
-- Indices de la tabla `pos_users`
--
ALTER TABLE `pos_users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `pos_info`
--
ALTER TABLE `pos_info`
  MODIFY `info_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `pos_inventory`
--
ALTER TABLE `pos_inventory`
  MODIFY `inv_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `pos_invoice`
--
ALTER TABLE `pos_invoice`
  MODIFY `invoice_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;

--
-- AUTO_INCREMENT de la tabla `pos_invoice_detail`
--
ALTER TABLE `pos_invoice_detail`
  MODIFY `detail_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT de la tabla `pos_users`
--
ALTER TABLE `pos_users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

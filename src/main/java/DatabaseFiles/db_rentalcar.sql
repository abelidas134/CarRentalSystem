-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 11, 2026 at 03:50 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_rentalcar`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL,
  `admin_user` varchar(11) DEFAULT NULL,
  `Admin_pass` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`admin_id`, `admin_user`, `Admin_pass`) VALUES
(1, 'admin123', '0000');

-- --------------------------------------------------------

--
-- Table structure for table `car`
--

CREATE TABLE `car` (
  `car_id` varchar(10) NOT NULL,
  `car_status_id` int(11) NOT NULL,
  `plate_no` varchar(15) NOT NULL,
  `car_name` varchar(100) NOT NULL,
  `rate` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `car_maintenance_history`
--

CREATE TABLE `car_maintenance_history` (
  `history_id` int(11) NOT NULL,
  `car_id` varchar(10) DEFAULT NULL,
  `repair_date` varchar(50) NOT NULL,
  `repairing` varchar(255) NOT NULL,
  `next_service_date` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `car_status`
--

CREATE TABLE `car_status` (
  `car_status_id` int(11) NOT NULL,
  `car_status` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `car_status`
--

INSERT INTO `car_status` (`car_status_id`, `car_status`) VALUES
(1, 'AVAILABLE'),
(2, 'RESERVED'),
(3, 'RENTED'),
(4, 'UNDER MAINTENANCE');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `cus_id` int(11) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`cus_id`, `username`, `password`) VALUES
(1, 'user123', '0000');

-- --------------------------------------------------------

--
-- Table structure for table `customer_book`
--

CREATE TABLE `customer_book` (
  `cus_book_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `phone` varchar(11) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `drivers_license` varchar(100) NOT NULL,
  `address` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `method_of_payment`
--

CREATE TABLE `method_of_payment` (
  `method_pay_id` int(10) NOT NULL,
  `pay_methods` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `method_of_payment`
--

INSERT INTO `method_of_payment` (`method_pay_id`, `pay_methods`) VALUES
(1, 'Cash'),
(2, 'Card'),
(3, 'QR Code');

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE `payments` (
  `pay_id` int(10) NOT NULL,
  `reservation_id` varchar(10) DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  `method_pay_id` int(11) DEFAULT NULL,
  `pay_status_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `payment_status`
--

CREATE TABLE `payment_status` (
  `pay_status_id` int(10) NOT NULL,
  `status_pay` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payment_status`
--

INSERT INTO `payment_status` (`pay_status_id`, `status_pay`) VALUES
(1, 'PAID'),
(2, 'UNPAID');

-- --------------------------------------------------------

--
-- Table structure for table `reservation`
--

CREATE TABLE `reservation` (
  `reservation_id` varchar(20) NOT NULL,
  `cus_book_id` int(11) DEFAULT NULL,
  `car_id` varchar(10) DEFAULT NULL,
  `pickup_date` date NOT NULL,
  `dropoff_date` date NOT NULL,
  `car_status_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_id`);

--
-- Indexes for table `car`
--
ALTER TABLE `car`
  ADD PRIMARY KEY (`car_id`),
  ADD KEY `car_status_id` (`car_status_id`);

--
-- Indexes for table `car_maintenance_history`
--
ALTER TABLE `car_maintenance_history`
  ADD PRIMARY KEY (`history_id`),
  ADD KEY `car_id` (`car_id`);

--
-- Indexes for table `car_status`
--
ALTER TABLE `car_status`
  ADD PRIMARY KEY (`car_status_id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`cus_id`);

--
-- Indexes for table `customer_book`
--
ALTER TABLE `customer_book`
  ADD PRIMARY KEY (`cus_book_id`),
  ADD UNIQUE KEY `phone` (`phone`),
  ADD UNIQUE KEY `drivers_license` (`drivers_license`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `method_of_payment`
--
ALTER TABLE `method_of_payment`
  ADD PRIMARY KEY (`method_pay_id`);

--
-- Indexes for table `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`pay_id`),
  ADD KEY `reservation_id` (`reservation_id`),
  ADD KEY `method_pay_id` (`method_pay_id`),
  ADD KEY `pay_status_id` (`pay_status_id`);

--
-- Indexes for table `payment_status`
--
ALTER TABLE `payment_status`
  ADD PRIMARY KEY (`pay_status_id`);

--
-- Indexes for table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`reservation_id`),
  ADD KEY `cus_book_id` (`cus_book_id`),
  ADD KEY `car_id` (`car_id`),
  ADD KEY `car_status_id` (`car_status_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `admin_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `car_maintenance_history`
--
ALTER TABLE `car_maintenance_history`
  MODIFY `history_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `car_status`
--
ALTER TABLE `car_status`
  MODIFY `car_status_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `cus_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `customer_book`
--
ALTER TABLE `customer_book`
  MODIFY `cus_book_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `method_of_payment`
--
ALTER TABLE `method_of_payment`
  MODIFY `method_pay_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `payments`
--
ALTER TABLE `payments`
  MODIFY `pay_id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `payment_status`
--
ALTER TABLE `payment_status`
  MODIFY `pay_status_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `car`
--
ALTER TABLE `car`
  ADD CONSTRAINT `car_ibfk_1` FOREIGN KEY (`car_status_id`) REFERENCES `car_status` (`car_status_id`);

--
-- Constraints for table `car_maintenance_history`
--
ALTER TABLE `car_maintenance_history`
  ADD CONSTRAINT `car_maintenance_history_ibfk_1` FOREIGN KEY (`car_id`) REFERENCES `car` (`car_id`);

--
-- Constraints for table `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`reservation_id`) REFERENCES `reservation` (`reservation_id`),
  ADD CONSTRAINT `payments_ibfk_2` FOREIGN KEY (`method_pay_id`) REFERENCES `method_of_payment` (`method_pay_id`),
  ADD CONSTRAINT `payments_ibfk_3` FOREIGN KEY (`pay_status_id`) REFERENCES `payment_status` (`pay_status_id`);

--
-- Constraints for table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`cus_book_id`) REFERENCES `customer_book` (`cus_book_id`),
  ADD CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`car_id`) REFERENCES `car` (`car_id`),
  ADD CONSTRAINT `reservation_ibfk_3` FOREIGN KEY (`car_status_id`) REFERENCES `car_status` (`car_status_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

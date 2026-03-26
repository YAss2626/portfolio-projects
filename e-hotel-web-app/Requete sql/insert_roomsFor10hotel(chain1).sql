INSERT INTO public.chambre(
	"ID", price, commodity, capacity, view, extension, problems, hotel_id, room_num, floor, hotelchain_id, room_type)
	VALUES
	--POUR LES 10 PREMIER HOTEL IDhotel_chain=>1
	(1, 100, 'Wi-Fi, TV', 1, 'City View', 'No', 'None', 1, 101, 1, 1, 'Single'),
	(2, 150, 'Wi-Fi, TV, Mini-bar', 2, 'Garden View', 'No', 'None', 1, 102, 1, 1, 'Double'),
	(3, 200, 'Wi-Fi, TV, Mini-bar, Balcony', 2, 'Sea View', 'Yes', 'None', 1, 103, 2, 1, 'Suite'),
	(4, 250, 'Wi-Fi, TV, Mini-bar, Jacuzzi', 1, 'City View', 'Yes', 'None', 1, 104, 2, 1, 'Deluxe'),
	(5, 300, 'Wi-Fi, TV, Mini-bar, Jacuzzi, Balcony', 2, 'Sea View', 'Yes', 'None', 1, 105, 3, 1, 'Penthouse'),
	
	(6, 110, 'Wi-Fi, TV', 1, 'City View', 'No', 'None', 2, 201, 1, 1, 'Single'),
	(7, 160, 'Wi-Fi, TV, Mini-bar', 2, 'Garden View', 'No', 'None', 2, 202, 1, 1, 'Double'),
	(8, 120, 'Wi-Fi, TV', 1, 'City View', 'No', 'None', 2, 206, 2, 1, 'Single'),
	(9, 170, 'Wi-Fi, TV, Mini-bar', 2, 'Garden View', 'No', 'None', 2, 207, 2, 1, 'Double'),
	(10, 220, 'Wi-Fi, TV, Mini-bar, Balcony', 2, 'Sea View', 'Yes', 'None', 3, 208, 3, 1, 'Suite'),
	
	-- Chambres pour Hôtel 3
	(13, 130, 'Wi-Fi, Tv', 1, 'City View', 'No', 'None', 3, 301, 1, 1, 'Single'),
	(14, 180, 'Wi-Fi, TV, Mini-bar', 2, 'Garden View', 'No', 'None', 3, 302, 1, 1, 'Double'),
	(15, 230, 'Wi-Fi, TV, Mini-bar, Balcony', 2, 'Sea View', 'Yes', 'None', 3, 303, 2, 1, 'Suite'),
	(16, 280, 'Wi-Fi, TV, Mini-bar, Jacuzzi', 1, 'City View', 'Yes', 'None', 3, 304, 2, 1, 'Deluxe'),
	(17, 330, 'Wi-Fi, TV, Mini-bar, Jacuzzi, Balcony', 2, 'Sea View', 'Yes', 'None', 3, 305, 3, 1, 'Penthouse'),
	

	-- Chambres pour Hôtel 4
	(19, 140, 'Wi-Fi, TV', 1, 'City View', 'No', 'None', 4, 401, 1, 1, 'Single'),
	(20, 190, 'Wi-Fi, TV, Mini-bar', 2, 'Garden View', 'No', 'None', 4, 402, 1, 1, 'Double'),
	(21, 240, 'Wi-Fi, TV, Mini-bar, Balcony', 2, 'Sea View', 'Yes', 'None', 4, 403, 2, 1, 'Suite'),
	(22, 290, 'Wi-Fi, TV, Mini-bar, Jacuzzi', 1, 'City View', 'Yes', 'None', 4, 404, 2, 1, 'Deluxe'),
	(23, 340, 'Wi-Fi, TV, Mini-bar, Jacuzzi, Balcony', 2, 'Sea View', 'Yes', 'None', 4, 405, 3, 1, 'Penthouse'),
	
	-- Chambres pour Hôtel 5
	(24, 150, 'Wi-Fi, TV', 1, 'City View', 'No', 'None', 5, 501, 1, 1, 'Single'),
	(25, 200, 'Wi-Fi, TV, Mini-bar', 2, 'Garden View', 'No', 'None', 5, 502, 1, 1, 'Double'),
	(26, 250, 'Wi-Fi, TV, Mini-bar, Balcony', 2, 'Sea View', 'Yes', 'None', 5, 503, 2, 1, 'Suite'),
	(27, 300, 'Wi-Fi, TV, Mini-bar, Jacuzzi', 1, 'City View', 'Yes', 'None', 5, 504, 2, 1, 'Deluxe'),
	(28, 350, 'Wi-Fi, TV, Mini-bar, Jacuzzi, Balcony', 2, 'Sea View', 'Yes', 'None', 5, 505, 3, 1, 'Penthouse'),
	-- Chambres pour Hôtel 6
	(29, 160, 'Wi-Fi, TV', 1, 'City View', 'No', 'None', 6, 601, 1, 1, 'Single'),
	(30, 210, 'Wi-Fi, TV, Mini-bar', 2, 'Garden View', 'No', 'None', 6, 602, 1, 1, 'Double'),
	(31, 260, 'Wi-Fi, TV, Mini-bar, Balcony', 2, 'Sea View', 'Yes', 'None', 6, 603, 2, 1, 'Suite'),
	(32, 310, 'Wi-Fi, TV, Mini-bar, Jacuzzi', 1, 'City View', 'Yes', 'None', 6, 604, 2, 1, 'Deluxe'),
	(33, 360, 'Wi-Fi, TV, Mini-bar, Jacuzzi, Balcony', 2, 'Sea View', 'Yes', 'None', 6, 605, 3, 1, 'Penthouse'),

	-- Chambres pour Hôtel 7
	(34, 170, 'Wi-Fi, TV', 1, 'City View', 'No', 'None', 7, 701, 1, 1, 'Single'),
	(35, 220, 'Wi-Fi, TV, Mini-bar', 2, 'Garden View', 'No', 'None', 7, 702, 1, 1, 'Double'),
	(36, 270, 'Wi-Fi, TV, Mini-bar, Balcony', 2, 'Sea View', 'Yes', 'None', 7, 703, 2, 1, 'Suite'),
	(37, 320, 'Wi-Fi, TV, Mini-bar, Jacuzzi', 1, 'City View', 'Yes', 'None', 7, 704, 2, 1, 'Deluxe'),
	(38, 370, 'Wi-Fi, TV, Mini-bar, Jacuzzi, Balcony', 2, 'Sea View', 'Yes', 'None', 7, 705, 3, 1, 'Penthouse'),

-- Chambres pour Hôtel 8
(39, 180, 'Wi-Fi, TV', 1, 'City View', 'No', 'None', 8, 801, 1, 1, 'Single'),
(40, 230, 'Wi-Fi, TV, Mini-bar', 2, 'Garden View', 'No', 'None', 8, 802, 1, 1, 'Double'),
(41, 280, 'Wi-Fi, TV, Mini-bar, Balcony', 2, 'Sea View', 'Yes', 'None', 8, 803, 2, 1, 'Suite'),
(42, 330, 'Wi-Fi, TV, Mini-bar, Jacuzzi', 1, 'City View', 'Yes', 'None', 8, 804, 2, 1, 'Deluxe'),
(43, 380, 'Wi-Fi, TV, Mini-bar, Jacuzzi, Balcony', 2, 'Sea View', 'Yes', 'None', 8, 805, 3, 1, 'Penthouse'),


-- Chambres pour Hôtel 9
(44, 190, 'Wi-Fi, TV', 1, 'City View', 'No', 'None', 9, 901, 1, 1, 'Single'),
(45, 240, 'Wi-Fi, TV, Mini-bar', 2, 'Garden View', 'No', 'None', 9, 902, 1, 1, 'Double'),
(46, 290, 'Wi-Fi, TV, Mini-bar, Balcony', 2, 'Sea View', 'Yes', 'None', 9, 903, 2, 1, 'Suite'),
(47, 340, 'Wi-Fi, TV, Mini-bar, Jacuzzi', 1, 'City View', 'Yes', 'None', 9, 904, 2, 1, 'Deluxe'),
(48, 390, 'Wi-Fi, TV, Mini-bar, Jacuzzi, Balcony', 2, 'Sea View', 'Yes', 'None', 9, 905, 3, 1, 'Penthouse'),

-- Chambres pour Hôtel 10
(49, 200, 'Wi-Fi, TV', 1, 'City View', 'No', 'None', 10, 1001, 1, 1, 'Single'),
(50, 250, 'Wi-Fi, TV, Mini-bar', 2, 'Garden View', 'No', 'None', 10, 1002, 1, 1, 'Double'),
(51, 300, 'Wi-Fi, TV, Mini-bar, Balcony', 2, 'Sea View', 'Yes', 'None', 10, 1003, 2, 1, 'Suite'),
(52, 350, 'Wi-Fi, TV, Mini-bar, Jacuzzi', 1, 'City View', 'Yes', 'None', 10, 1004, 2, 1, 'Deluxe'),
(53, 400, 'Wi-Fi, TV, Mini-bar, Jacuzzi, Balcony', 2, 'Sea View', 'Yes', 'None', 10, 1005, 3, 1, 'Penthouse');
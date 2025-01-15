CREATE VIEW HotelRoomCapacity AS
SELECT h.nom_hotel, c.room_type, AVG(c.capacity) AS average_capacity
FROM chambre c
JOIN "Hotel" h ON c.hotel_id = h."ID"
GROUP BY h.nom_hotel, c.room_type;

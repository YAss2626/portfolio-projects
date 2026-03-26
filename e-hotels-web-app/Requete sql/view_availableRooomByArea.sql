CREATE VIEW AvailableRoomsByArea AS
SELECT h.nom_hotel, COUNT(c."ID") AS available_rooms
FROM chambre c
JOIN "Hotel" h ON c.hotel_id = h."ID"
WHERE c."ID" NOT IN (SELECT room_id FROM "reservations ")
GROUP BY h.nom_hotel;

SELECT "Hotel"."ID", "Hotel".nom_hotel, "Hotel".adress, "Hotel".category, 
"Hotel".hotel_chain_id
FROM "Hotel" 
JOIN "hotel_chain" ON "hotel_chain"."id " = "Hotel".hotel_chain_id;

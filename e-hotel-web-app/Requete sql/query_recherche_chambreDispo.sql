SELECT * FROM public.chambre
WHERE "ID" NOT IN (
    SELECT room_id FROM public."reservations "
    WHERE arriving_date <= '2024-05-01' AND departing_date >= '2024-04-20'
)
AND capacity >= 2 AND price <= 200;

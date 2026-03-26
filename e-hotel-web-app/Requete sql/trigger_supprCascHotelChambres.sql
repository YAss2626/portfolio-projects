CREATE OR REPLACE FUNCTION delete_hotels_and_rooms()
RETURNS TRIGGER AS $$
BEGIN
    DELETE FROM public."Hotel" WHERE hotel_chain_id = OLD."id ";
    -- Suppression des chambres liées aux hôtels supprimés gérée par ON DELETE CASCADE dans les FK
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER delete_chain_cascade
AFTER DELETE ON public.hotel_chain
FOR EACH ROW EXECUTE FUNCTION delete_hotels_and_rooms();
--Vérification de la contrainte de prix positif pour la table chambre :

CREATE OR REPLACE FUNCTION check_price_positive()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.price < 0 THEN
        RAISE EXCEPTION 'Le prix de la chambre doit être positif';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_price_before_insert_or_update
BEFORE INSERT OR UPDATE ON public.chambre
FOR EACH ROW EXECUTE FUNCTION check_price_positive();

--Suppression en cascade des hôtels et chambres lors de la suppression d'une chaîne hôtelière :
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


-- Si un hôtel est supprimé, toutes les chambres et réservations 
--associées à cet hôtel doivent également être supprimées.
CREATE OR REPLACE FUNCTION delete_associated_rooms_and_reservations()
RETURNS TRIGGER AS $$
BEGIN
    -- Suppression des réservations associées aux chambres de l'hôtel supprimé
    DELETE FROM public."reservations "
    WHERE room_id IN (
        SELECT "ID" FROM public.chambre
        WHERE hotel_id = OLD."ID"
    );
    
    -- Suppression des chambres de l'hôtel supprimé
    DELETE FROM public.chambre
    WHERE hotel_id = OLD."ID";
    
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trigger_delete_associated_rooms_and_reservations
BEFORE DELETE ON public."Hotel"
FOR EACH ROW EXECUTE FUNCTION delete_associated_rooms_and_reservations();

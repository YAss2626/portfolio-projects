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

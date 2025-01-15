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

--Index sur la table des réservations par client_id
CREATE INDEX idx_reservations_client_id ON public."reservations "(client_id);


--Sur le prix des chambres pour accélérer les recherches par prix :
CREATE INDEX idx_chambre_price ON public.chambre(price);

--Sur l'ID de la chaîne hôtelière dans la table hôtel pour les jointures :
CREATE INDEX idx_hotel_hotel_chain_id ON public."Hotel"(hotel_chain_id);



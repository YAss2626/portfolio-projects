UPDATE public.chambre
SET price = CASE WHEN 150 >= 0 THEN 150 ELSE price END
WHERE "ID" = 1;

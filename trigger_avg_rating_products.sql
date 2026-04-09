-- funzione sql
CREATE OR REPLACE FUNCTION update_product_avg_rating()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE products
    SET average_rating = (
        SELECT AVG(rating)
        FROM reviews
        WHERE product_id = NEW.product_id
    )
    WHERE id = NEW.product_id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger su insert
CREATE TRIGGER trg_update_avg_rating_insert
AFTER INSERT ON reviews
FOR EACH ROW
EXECUTE FUNCTION update_product_avg_rating();

-- trigger su update
CREATE TRIGGER trg_update_avg_rating_update
AFTER UPDATE ON reviews
FOR EACH ROW
EXECUTE FUNCTION update_product_avg_rating();

--  trigger su delete
CREATE OR REPLACE FUNCTION update_product_avg_rating_delete()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE products
    SET average_rating = (
        SELECT AVG(rating)
        FROM reviews
        WHERE product_id = OLD.product_id
    )
    WHERE id = OLD.product_id;

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_avg_rating_delete
AFTER DELETE ON reviews
FOR EACH ROW
EXECUTE FUNCTION update_product_avg_rating_delete();
ALTER TABLE saved_events
ADD CONSTRAINT unique_save UNIQUE (user_id, event_id);
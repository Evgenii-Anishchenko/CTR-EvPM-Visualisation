CREATE TABLE IF NOT EXISTS public.visit_event
(
    uid uuid         NOT NULL,
    tag varchar(255) NULL,
    CONSTRAINT visit_event_pkey PRIMARY KEY (uid)
);
CREATE TABLE IF NOT EXISTS public.chart_unit_entity
(
    id            bigserial      NOT NULL,
    clicks        int8           NULL,
    ctr           numeric(38, 8) NULL,
    evpm          numeric(38, 8) NULL,
    tag           varchar(255)   NULL,
    time_interval int2           NULL,
    "timestamp"   timestamp(6)   NULL,
    "views"       int8           NULL,
    CONSTRAINT chart_unit_entity_pkey PRIMARY KEY (id),
    CONSTRAINT chart_unit_entity_time_interval_check CHECK (((time_interval >= 0) AND (time_interval <= 3)))
);
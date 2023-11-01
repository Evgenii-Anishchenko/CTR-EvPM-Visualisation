CREATE TABLE IF NOT EXISTS public.site_aggr_entity
(
    site_id varchar(255)   NOT NULL,
    ctr     numeric(38, 8) NULL,
    evpm    numeric(38, 8) NULL,
    tag     varchar(255)   NULL,
    updated timestamp(6)   NULL,
    "views" int8           NULL,
    CONSTRAINT site_aggr_entity_pkey PRIMARY KEY (site_id)
);
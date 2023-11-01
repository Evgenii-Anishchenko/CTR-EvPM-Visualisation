CREATE TABLE IF NOT EXISTS public.mm_dma_aggr_entity
(
    mm_dma  varchar(255)   NOT NULL,
    ctr     numeric(38, 8) NULL,
    evpm    numeric(38, 8) NULL,
    tag     varchar(255)   NULL,
    updated timestamp(6)   NULL,
    "views" int8           NULL,
    CONSTRAINT mm_dma_aggr_entity_pkey PRIMARY KEY (mm_dma)
);
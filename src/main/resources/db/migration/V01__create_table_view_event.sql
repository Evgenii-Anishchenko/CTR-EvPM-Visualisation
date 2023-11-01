CREATE TABLE IF NOT EXISTS public.view_event
(
    uid         uuid         NOT NULL,
    reg_time    timestamp(6) NULL,
    fc_imp_chk  int4         NOT NULL,
    fc_time_chk int4         NOT NULL,
    utmtr       int4         NOT NULL,
    mm_dma      varchar(255) NULL,
    os_name     varchar(255) NULL,
    model       varchar(255) NULL,
    hardware    varchar(255) NULL,
    site_id     varchar(255) NULL,
    CONSTRAINT view_event_pkey PRIMARY KEY (uid)
);
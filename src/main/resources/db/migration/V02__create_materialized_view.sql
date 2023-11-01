CREATE MATERIALIZED VIEW IF NOT EXISTS public.joined_data AS
SELECT v.uid         AS view_uid,
       v.fc_imp_chk  AS view_fc_imp_chk,
       v.fc_time_chk AS view_fc_time_chk,
       v.hardware    AS view_hardware,
       v.mm_dma      AS view_mm_dma,
       v.model       AS view_model,
       v.os_name     AS view_os_name,
       v.reg_time    AS view_reg_time,
       v.site_id     AS view_site_id,
       v.utmtr       AS view_utmtr,
       c.tag         AS visit_tag
FROM public.view_event v
         LEFT JOIN
     public.visit_event c
     ON
         v.uid = c.uid;

CREATE INDEX idx_view_reg_time ON public.joined_data(view_reg_time);
CREATE UNIQUE INDEX idx_unique_view_uid ON public.joined_data(view_uid);
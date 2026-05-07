alter table dubbing_jobs add column if not exists original_transcript_asset varchar(255);
alter table dubbing_segments add column if not exists segment_index integer;
alter table dubbing_segments add column if not exists start_time double precision;
alter table dubbing_segments add column if not exists end_time double precision;
alter table dubbing_segments add column if not exists original_text text;
alter table dubbing_segments add column if not exists translated_text text;
alter table dubbing_segments add column if not exists failure_reason varchar(300);
create index if not exists idx_dubbing_segments_job_id_segment_index on dubbing_segments(job_id, segment_index);
create unique index if not exists uq_dubbing_segments_job_id_segment_index on dubbing_segments(job_id, segment_index);

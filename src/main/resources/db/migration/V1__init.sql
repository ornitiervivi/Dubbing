create table dubbing_jobs (id varchar(64) primary key, original_filename varchar(255), content_type varchar(120), size_bytes bigint, storage_key varchar(255), status varchar(40), created_at timestamp);
create table dubbing_segments (id varchar(64) primary key, job_id varchar(64), adapted_text text, status varchar(40));
create table voice_profiles (id varchar(64) primary key, display_name varchar(255), consent_accepted boolean, status varchar(40), created_at timestamp);

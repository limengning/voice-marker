-- public.project definition

-- Drop table

-- DROP TABLE public.project;

CREATE TABLE public.project (
	id int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
	create_time timestamp(0) NOT NULL,
	create_by varchar NULL,
	update_time timestamp(0) NULL,
	update_by varchar NULL,
	"name" varchar NOT NULL,
	description varchar NULL,
	CONSTRAINT project_pk PRIMARY KEY (id)
);

-- public.file definition

-- Drop table

-- DROP TABLE public.file;

CREATE TABLE public.file (
	id int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
	create_time timestamp(0) NOT NULL,
	create_by varchar NULL,
	update_time timestamp(0) NULL,
	update_by varchar NULL,
	project_id int4 NULL,
	"name" varchar NOT NULL,
	src varchar NOT NULL,
	local_path varchar NULL,
	"size" int8 NULL,
	duration int4 NULL,
	CONSTRAINT file_pk PRIMARY KEY (id)
);


-- public.file foreign keys

ALTER TABLE public.file ADD CONSTRAINT file_fk FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE;

-- public.mark definition

-- Drop table

-- DROP TABLE public.mark;

CREATE TABLE public.mark (
	id int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
	create_time timestamp(0) NOT NULL,
	create_by varchar NULL,
	update_time timestamp(0) NULL,
	update_by varchar NULL,
	file_id int4 NOT NULL,
	start_point float4 NULL,
	end_point float4 NULL,
	"comment" varchar NULL,
	region_id varchar NOT NULL,
	"locked" bool NOT NULL DEFAULT false,
	CONSTRAINT mark_pk PRIMARY KEY (id)
);


-- public.mark foreign keys

ALTER TABLE public.mark ADD CONSTRAINT mark_fk FOREIGN KEY (file_id) REFERENCES file(id) ON DELETE CASCADE;


-- public.mark_field definition

-- Drop table

-- DROP TABLE public.mark_field;

CREATE TABLE public.mark_field (
                                       id int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
                                       create_time timestamp(0) NOT NULL,
                                       create_by varchar NULL,
                                       update_time timestamp(0) NULL,
                                       update_by varchar NULL,
                                       project_id int4 NOT NULL,
                                       field_name varchar NOT NULL,
                                       field_display_text varchar NOT NULL,
                                       field_type int4 NOT NULL,
                                       required bool NOT NULL,
                                       data_source varchar NULL,
                                       sort int4 NULL,
                                       CONSTRAINT mark_field_pk PRIMARY KEY (id)
);

-- public.mark_field foreign keys

ALTER TABLE public.mark_field ADD CONSTRAINT mark_field_fk FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE;




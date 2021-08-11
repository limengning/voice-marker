
-- public.mark_form definition

-- Drop table

-- DROP TABLE public.mark_form;

CREATE TABLE public.mark_form (
	id int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
	create_time timestamp(0) NOT NULL,
	create_by varchar NULL,
	update_time timestamp(0) NULL,
	update_by varchar NULL,
	name varchar NULL,
	CONSTRAINT mark_form_pk PRIMARY KEY (id)
);


-- public.mark_field definition

-- Drop table

-- DROP TABLE public.mark_field;

CREATE TABLE public.mark_field (
	id int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
	create_time timestamp(0) NOT NULL,
	create_by varchar NULL,
	update_time timestamp(0) NULL,
	update_by varchar NULL,
	form_id int4 NOT NULL,
	field_name varchar NOT NULL,
	field_display_text varchar NOT NULL,
	field_type int4 NOT NULL,
	required bool NOT NULL,
	data_source varchar NULL,
	sort int4 NULL,
	CONSTRAINT mark_field_pk PRIMARY KEY (id)
);

-- public.mark_field foreign keys

ALTER TABLE public.mark_field ADD CONSTRAINT mark_field_fk FOREIGN KEY (form_id) REFERENCES mark_form(id) ON DELETE CASCADE;

-- public.project add mark_form_id column
ALTER TABLE public.project ADD COLUMN mark_form_id NULL;

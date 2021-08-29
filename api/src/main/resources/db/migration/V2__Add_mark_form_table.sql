
-- public.tpl_mark_form definition

-- Drop table

-- DROP TABLE public.tpl_mark_form;

CREATE TABLE public.tpl_mark_form (
	id int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
	create_time timestamp(0) NOT NULL,
	create_by varchar NULL,
	update_time timestamp(0) NULL,
	update_by varchar NULL,
	name varchar NULL,
	CONSTRAINT tpl_mark_form_pk PRIMARY KEY (id)
);


-- public.tpl_mark_field definition

-- Drop table

-- DROP TABLE public.tpl_mark_field;

CREATE TABLE public.tpl_mark_field (
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
	CONSTRAINT tpl_mark_field_pk PRIMARY KEY (id)
);

-- public.mark_field foreign keys

ALTER TABLE public.tpl_mark_field ADD CONSTRAINT tpl_mark_field_fk FOREIGN KEY (form_id) REFERENCES tpl_mark_form(id) ON DELETE CASCADE;


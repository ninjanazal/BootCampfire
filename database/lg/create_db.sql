-- CREATE roles
CREATE TABLE IF NOT EXISTS public.roles (
	id SERIAL PRIMARY KEY,
	type character varying(20) COLLATE pg_catalog."default" NOT NULL,
	description character varying COLLATE pg_catalog."default"
);

-- CREATE genders
CREATE TABLE IF NOT EXISTS public.genders (
	id SERIAL PRIMARY KEY,
	type character varying COLLATE pg_catalog."default" NOT NULL
);

-- CREATE users
CREATE TABLE IF NOT EXISTS public.users (
	id SERIAL PRIMARY KEY,
	
);
-- CREATE roles
DROP TABLE if EXISTS public.roles;

CREATE TABLE IF NOT EXISTS public.roles (
	id SERIAL PRIMARY KEY,
	description VARCHAR(20) UNIQUE NOT NULL
);


-- CREATE genders
DROP TABLE if EXISTS public.genders;

CREATE TABLE IF NOT EXISTS public.genders (
	id SERIAL PRIMARY KEY,
	description VARCHAR NOT NULL
);


-- CREATE users
DROP TABLE if EXISTS public.users;


CREATE TABLE IF NOT EXISTS public.users (
	id SERIAL PRIMARY KEY,
	role_id INTEGER REFERENCES roles(id) NOT NULL,
	gender_id INTEGER REFERENCES genders(id) NOT NULL,
	email VARCHAR(255) UNIQUE NOT NULL,
	age INTEGER NOT NULL CHECK (age > 0),
	hashed_password VARCHAR NOT NULL
);


-- CREATE session
DROP TABLE if EXISTS public.sessions;


CREATE TABLE IF NOT EXISTS public.sessions (
	id VARCHAR(255) PRIMARY KEY ,
	user_id INTEGER REFERENCES users(id) NOT NULL,
	permission_lvl VARCHAR(20) NOT NULL CHECK (permission_lvl IN ('read-write', 'read-only')),
	created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
	expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
	refresh_token VARCHAR(255) NOT NULL
);
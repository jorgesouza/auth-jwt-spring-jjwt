CREATE TABLE IF NOT EXISTS public.roles
(
    id          SERIAL PRIMARY KEY,
    description VARCHAR(50) UNIQUE NOT NULL
);
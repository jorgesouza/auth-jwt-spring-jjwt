CREATE TABLE IF NOT EXISTS public.user_group
(
    id          SERIAL PRIMARY KEY,
    description VARCHAR(50) UNIQUE NOT NULL
);
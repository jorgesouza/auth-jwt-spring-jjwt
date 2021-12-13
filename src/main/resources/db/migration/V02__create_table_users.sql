CREATE TABLE IF NOT EXISTS public.users
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(50)  NOT NULL,
    email         VARCHAR(50)  UNIQUE NOT NULL,
    password      VARCHAR(255) NOT NULL,
    user_group_id INTEGER      NOT NULL,
    created_at    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_user_group FOREIGN KEY (user_group_id) REFERENCES user_group (id)
);
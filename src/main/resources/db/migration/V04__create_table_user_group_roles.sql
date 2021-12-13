CREATE TABLE IF NOT EXISTS public.user_group_roles
(
    user_group_id INTEGER NOT NULL,
    role_id       INTEGER NOT NULL,

    PRIMARY KEY (user_group_id, role_id),
    CONSTRAINT fk_user_group FOREIGN KEY (user_group_id) REFERENCES user_group (id),
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles (id)
);
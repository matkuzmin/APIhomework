-- liquibase formatted sql

-- changeset matvey:1
create index name_index on stuent(name)
-- changeset matvey:2
create index name_color_index on faculty(name,color)
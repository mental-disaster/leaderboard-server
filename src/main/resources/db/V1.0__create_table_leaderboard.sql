create table leaderboard
(
    id          char(36)     not null primary key,
    name        varchar(255) not null,
    score       bigint       not null,
    recorded_at datetime     not null default current_timestamp()
);
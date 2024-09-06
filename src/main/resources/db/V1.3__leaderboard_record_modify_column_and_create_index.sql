alter table leaderboard_record modify column group_code varchar(7) collate utf8_bin;
create index idx_leaderboard_record_group_code on leaderboard_record(group_code);
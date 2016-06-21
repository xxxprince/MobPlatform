drop table if exists AU_Workitem_Info;
create table MD_Service(
   serviceId	int(64) not null auto_increment,
   account_name         varchar(64) not null,
   password             varchar(32) not null,
   pid                  varchar(64) not null,
   is_base_account      varchar(8) not null,
   system_id            varchar(8) not null,
   user_id              varchar(64) not null,
   primary key (serviceId)
);
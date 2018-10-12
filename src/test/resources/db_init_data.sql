-- database version 3.1.48 (lightweight version)

--ENTITIES

-- ATTR_NAMES - list of possible attributes
create table attr_names (
	id integer not null,
	attr_name varchar(384) not null,  --full name of attribute
	type varchar(256) not null,       --type of attribute data (string,number,array...)
	constraint attnam_pk primary key(id),
	constraint attnam_u unique (attr_name),
	constraint attfullnam_u unique (friendly_name,namespace)
);

-- EXT_SOURCES - external sourcces from which we can gain data about users
create table ext_sources (
	id integer not null,
	name varchar(256) not null,    --name of source
	type varchar(64),              --type of source (LDAP/IdP...)
	constraint usrsrc_pk primary key(id),
	constraint usrsrc_u unique (name)
);

-- VOS - virtual organizations
create table vos (
	id integer not null,
	name varchar(128) not null,   -- full name of VO
	short_name varchar(32) not null, -- commonly used name
	constraint vo_pk primary key (id),
	constraint vo_u unique (name)
);

-- SERVICES - provided services, their atomic form
create table services (
	id integer not null,
	name varchar(128) not null,    --name of service
	description varchar(1024),
	delay integer not null default 10,
	recurrence integer not null default 2,
	enabled char(1) not null default '1',
	script varchar(256) not null,
	constraint serv_pk primary key(id),
	constraint serv_u unique(name)
);

-- USERS - information about user as real person
create table users (
	id integer not null,
	first_name varchar(64),   -- christening name
	last_name varchar(64),    -- family name
	middle_name varchar(64),   -- second name
	title_before varchar(40),  -- academic degree used before name
	title_after varchar(40),   -- academic degree used after name
	service_acc char(1) default '0' not null, --is it service account?
	sponsored_acc char(1) default '0' not null, --is it sponsored account?
	constraint usr_pk primary key (id),
	constraint usr_srvacc_chk check (service_acc in ('0','1'))
);

-- FACILITIES - sources, devices - includes clusters,hosts,storages...
create table facilities (
	id integer not null,
	name varchar(128) not null, --unique name of facility
	dsc varchar(1024),
	constraint fac_pk primary key(id),
	constraint fac_name_u unique (name)
);

-- HOSTS - detail information about hosts and cluster nodes
create table hosts (
	id integer not null,
	hostname varchar(128) not null,  --full name of machine
	facility_id integer not null,    --identifier of facility containing the host (facilities.id)
	dsc varchar(1024),  --description
	constraint host_pk primary key (id),
	constraint host_fac_fk foreign key(facility_id) references facilities(id)
);

-- RESOURCES - facility assigned to VO
create table resources (
	id integer not null,
	facility_id integer not null, --facility identifier (facility.id)
	name varchar(128) not null,   --name of resource
	dsc varchar(1024),            --purpose and description
	vo_id integer not null,   --identifier of VO (vos.id)
	constraint rsrc_pk primary key (id),
	constraint rsrc_fac_fk foreign key (facility_id) references facilities(id),
	constraint rsrc_vo_fk foreign key (vo_id) references vos(id)
);

-- GROUPS - groups of users
create table groups (
	id integer not null,
	name text not null,         --group name
	dsc varchar(1024),          --purpose and description
	vo_id integer not null,     --identifier of VO (vos.id)
	parent_group_id integer,    --in case of subgroup identifier of parent group (groups.id)
	constraint grp_pk primary key (id),
	constraint grp_nam_vo_parentg_u unique (name,vo_id,parent_group_id),
	constraint grp_vos_fk foreign key (vo_id) references vos(id),
	constraint grp_grp_fk foreign key (parent_group_id) references groups(id)
);

-- USER_EXT_SOURCES - external source from which user come (identification of user in his home system)
create table user_ext_sources (
	id integer not null,
	user_id integer not null,          --identifier of user (users.id)
	login_ext varchar(1300) not null,   --logname from his home system
	ext_sources_id integer not null,   --identifier of ext. source (ext_sources.id)
	loa integer,                       --level of assurance
	last_access timestamp default statement_timestamp() not null, --time of last user's access (to Perun) by using this external source
	constraint usrex_p primary key(id),
	constraint usrex_u unique (ext_sources_id,login_ext),
	constraint usrex_usr_fk foreign key (user_id) references users(id),
	constraint usrex_usersrc_fk foreign key(ext_sources_id) references ext_sources(id)
);

-- MEMBERS - members of VO
create table members (
	id integer not null,
	user_id integer not null,  --user's identifier (users.id)
	vo_id integer not null,    --identifier of VO (vos.id)
	sponsored boolean default false not null,
	constraint mem_pk primary key(id),
	constraint mem_user_fk foreign key(user_id) references users(id),
	constraint mem_vo_fk foreign key(vo_id) references vos(id),
	constraint mem_user_vo_u unique (vo_id, user_id)
);

--ATTR_VALUES

-- EXT_SOURCES_ATTRIBUTES - values of attributes of external sources
create table ext_sources_attributes (
	ext_sources_id integer not null,   --identifier of ext. source (ext_sources.id)
	attr_name varchar(128) not null,   --name of attribute at ext. source
	attr_value varchar(4000),          --value of attribute
	constraint usrcatt_usrc_fk foreign key (ext_sources_id) references ext_sources(id)
);

-- FACILITY_ATTR_VALUES - attribute values assigned to facility
create table facility_attr_values (
	facility_id integer not null,   --identifier of facility (facilities.id)
	attr_id integer not null,       --identifier of attribute (attr_names.id)
	attr_value varchar(4000),       --attribute valuer
	attr_value_text text,           --attribute value in case it is very long text
	constraint facattval_pk primary key (facility_id,attr_id),
	constraint facattval_nam_fk foreign key (attr_id) references attr_names(id),
	constraint facattval_fac_fk foreign key (facility_id) references facilities (id)
);

-- GROUP_ATTR_VALUES - attribute values assigned to groups
create table group_attr_values (
	group_id integer not null,     --identifier of group (groups.id)
	attr_id integer not null,      --identifier of attribute (attr_names.id)
	attr_value varchar(4000),      --attribute value
	attr_value_text text,   --value of attribute if it is very long text
	modified_by_uid integer,
	constraint grpattval_pk primary key (group_id,attr_id),
	constraint grpattval_grp_fk foreign key (group_id) references groups(id),
	constraint grpattval_attr_fk foreign key (attr_id) references attr_names(id)
);

-- HOST_ATTR_VALUES - values of attributes assigned to hosts
create table host_attr_values (
	host_id integer not null,  --identifier of host (hosts.id)
	attr_id integer not null,  --identifier of attributes (attr_names.id)
	attr_value varchar(4000),  --value of attribute
	attr_value_text text,   --value of attribute if it is very long text
	constraint hostav_pk primary key (host_id,attr_id),
	constraint hostav_host_fk foreign key (host_id) references hosts(id),
	constraint hostav_attr_fk foreign key (attr_id) references attr_names(id)
);

-- MEMBER_ATTR_VALUES - values of attributes assigned to members
create table member_attr_values (
	member_id integer not null,   --identifier of member (members.id)
	attr_id integer not null,     --identifier of attribute (attr_names.id)
	attr_value varchar(4000),     --attribute value
	attr_value_text text,         --attribute value in case it is very long text
	constraint memattval_pk primary key (member_id,attr_id),
	constraint memattval_mem_fk foreign key (member_id) references members(id),
	constraint memattval_attr_fk foreign key (attr_id) references attr_names(id)
);

-- RESOURCE_ATTR_VALUES - attribute values assigned to resources
create table resource_attr_values (
	resource_id integer not null,   --identifier of resource (resources.id)
	attr_id integer not null,       --identifier of attribute (attr_names.id)
	attr_value varchar(4000),       --attribute value
	attr_value_text text,           --attribute value in case it is very long text
	constraint resatval_pk primary key (resource_id,attr_id),
	constraint resatval_res_fk foreign key(resource_id) references resources(id),
	constraint resatval_resatnam_fk foreign key(attr_id) references attr_names(id)
);

-- SERVICE_REQUIRED_ATTRS - list of attributes required by the service
create table service_required_attrs (
	service_id integer not null,   --identifier of service (services.id)
	attr_id integer not null,      --identifier of attribute (attr_names.id)
	constraint srvreqattr_pk primary key (service_id,attr_id),
	constraint srvreqattr_srv_fk foreign key(service_id) references services(id),
	constraint srvreqattr_attr_fk foreign key(attr_id) references attr_names(id)
);

-- USER_ATTR_VALUES - values of attributes assigned to users
create table user_attr_values (
	user_id integer not null,  --identifier of user (users.id)
	attr_id integer not null,  --identifier of attribute (attr_names.id)
	attr_value varchar(4000),  --attribute value
	attr_value_text text,      --attribute value in case it is very long text
	constraint usrav_pk primary key(user_id,attr_id),
	constraint usrav_usr_fk foreign key (user_id) references users(id),
	constraint usrav_accattnam_fk foreign key (attr_id) references attr_names(id)
);

-- VO_ATTR_VALUES - attributes specific for VO
create table vo_attr_values (
	vo_id integer not null,    --identifier of VO (vos.id)
	attr_id integer not null,  --identifier of attribute (attr_names.id)
	attr_value varchar(4000),  --attribute value
	attr_value_text text,      --attribute value in case it is very long text
	constraint voattval_pk primary key (vo_id,attr_id),
	constraint voattval_nam_fk foreign key (attr_id) references attr_names(id),
	constraint voattval_vo_fk foreign key (vo_id) references vos (id)
);

--RELATIONS

-- GROUP_RESOURCE_ATTR_VALUES - attribute values assigned to groups and resources
create table group_resource_attr_values (
	group_id integer not null,     --identifier of group (groups.id)
	resource_id integer not null,  --identifier of resource (resources.id)
	attr_id integer not null,      --identifier of attribute (attr_names.id)
	attr_value varchar(4000),      --attribute value
	attr_value_text text,          --attribute value in case it is very long text
	constraint grpresav_pk primary key (group_id,resource_id,attr_id),
	constraint grpresav_grp_fk foreign key (group_id) references groups(id),
	constraint grpresav_res_fk foreign key (resource_id) references resources(id),
	constraint grpresav_attr_fk foreign key (attr_id) references attr_names(id)
);

-- MEMBER_GROUP_ATTR_VALUES - values of attributes assigned to members in groups
create table member_group_attr_values (
	member_id integer not null,   --identifier of member (members.id)
	group_id integer not null, --identifier of group (groups.id)
	attr_id integer not null,     --identifier of attribute (attr_names.id)
	attr_value varchar(4000),     --attribute value
	attr_value_text text,         --attribute value in case it is very long text
	constraint memgav_pk primary key(member_id,group_id,attr_id),
	constraint memgav_mem_fk foreign key (member_id) references members(id),
	constraint memgav_grp_fk foreign key (group_id) references groups(id),
	constraint memgav_accattnam_fk foreign key (attr_id) references attr_names(id)
);

-- MEMBER_RESOURCE_ATTR_VALUES - values of attributes assigned to members on resources
create table member_resource_attr_values (
	member_id integer not null,   --identifier of member (members.id)
	resource_id integer not null, --identifier of resource (resources.id)
	attr_id integer not null,     --identifier of attribute (attr_names.id)
	attr_value varchar(4000),     --attribute value
	attr_value_text text,         --attribute value in case it is very long text
	constraint memrav_pk primary key(member_id,resource_id,attr_id),
	constraint memrav_mem_fk foreign key (member_id) references members(id),
	constraint memrav_rsrc_fk foreign key (resource_id) references resources(id),
	constraint memrav_accattnam_fk foreign key (attr_id) references attr_names(id)
);

-- MEMBER_RESOURCE_ATTR_U_VALUES - unique attribute values
CREATE TABLE member_resource_attr_u_values (
	member_id INT NOT NULL,
	resource_id INT NOT NULL,
	attr_id INT NOT NULL,
	attr_value VARCHAR(4000),
	UNIQUE (attr_id, attr_value),
	FOREIGN KEY (member_id,resource_id,attr_id) REFERENCES member_resource_attr_values ON DELETE CASCADE
);

-- USER_FACILITY_ATTR_VALUES - values of attributes assigned to users on facilities
create table user_facility_attr_values (
	user_id integer not null,     --identifier of user (users.id)
	facility_id integer not null, --identifier of facility (facilities.id)
	attr_id integer not null,     --identifier of attribute (attr_names.id)
	attr_value varchar(4000),     --attribute value
	attr_value_text text,         --attribute value in case it is very long text
	constraint usrfacav_u primary key(user_id,facility_id,attr_id),
	constraint usrfacav_mem_fk foreign key (user_id) references users(id),
	constraint usrfacav_fac_fk foreign key (facility_id) references facilities(id),
	constraint usrfacav_accattnam_fk foreign key (attr_id) references attr_names(id)
);





INSERT INTO ext_sources(id, name, type) VALUES (1, 'ext_source1', 'LDAP');
INSERT INTO ext_sources(id, name, type) VALUES (2, 'ext_source2', 'LDAP');
INSERT INTO ext_sources(id, name, type) VALUES (3, 'ext_source3', 'IdP');
INSERT INTO ext_sources(id, name, type) VALUES (4, 'ext_source4', 'IdP');

INSERT INTO facilities(id, name, dsc) VALUES (1, 'facility1', 'dsc1');
INSERT INTO facilities(id, name, dsc) VALUES (2, 'facility2', 'dsc2');
INSERT INTO facilities(id, name, dsc) VALUES (3, 'facility3', 'dsc3');

INSERT INTO vos(id, name, short_name) VALUES (1, 'virtual_organization1', 'vo1');
INSERT INTO vos(id, name, short_name) VALUES (1, 'virtual_organization2', 'vo2');
INSERT INTO vos(id, name, short_name) VALUES (1, 'virtual_organization3', 'vo3');

INSERT INTO services(id, name, description, delay, recurrence, enabled, script)
VALUES (1, 'service1', 'dsc1', 1, 1, '1', 'script1');
INSERT INTO services(id, name, description, delay, recurrence, enabled, script)
VALUES (2, 'service2', 'dsc2', 2, 2, '0', 'script2');
INSERT INTO services(id, name, description, delay, recurrence, enabled, script)
VALUES (3, 'service3', 'dsc3', 3, 3, '0', 'script3');

INSERT INTO users(id, first_name, last_name, middle_name, title_before, title_after, service_acc, sponsored_acc)
VALUES (1, 'first_name1', 'last_name1', 'middle_name1', 'title_before1', 'title_after1', '1', '0' );
INSERT INTO users(id, first_name, last_name, middle_name, title_before, title_after, service_acc, sponsored_acc)
VALUES (2, 'first_name2', 'last_name2', 'middle_name2', 'title_before2', 'title_after2', '0', '0' );
INSERT INTO users(id, first_name, last_name, middle_name, title_before, title_after, service_acc, sponsored_acc)
VALUES (3, 'first_name3', 'last_name3', 'middle_name3', 'title_before3', 'title_after3', '0', '1' );

INSERT INTO hosts(id, hostname, facility_id, dsc) VALUES (1, 'hostname1', 1, 'dsc1');
INSERT INTO hosts(id, hostname, facility_id, dsc) VALUES (2, 'hostname2', 2, 'dsc2');
INSERT INTO hosts(id, hostname, facility_id, dsc) VALUES (3, 'hostname3', 3, 'dsc3');

INSERT INTO groups(id, name, dsc, vo_id, parent_group_id) VALUES (1, 'group1', 'dsc1', 1, null);
INSERT INTO groups(id, name, dsc, vo_id, parent_group_id) VALUES (2, 'group2', 'dsc2', 2, null);
INSERT INTO groups(id, name, dsc, vo_id, parent_group_id) VALUES (3, 'group3', 'dsc3', 3, null);
INSERT INTO groups(id, name, dsc, vo_id, parent_group_id) VALUES (4, 'child_group1', 'dsc4', 1, 1);
INSERT INTO groups(id, name, dsc, vo_id, parent_group_id) VALUES (5, 'child_group2', 'dsc4', 2, 2);
INSERT INTO groups(id, name, dsc, vo_id, parent_group_id) VALUES (6, 'child_group3', 'dsc4', 3, 3);

INSERT INTO user_ext_sources(id, user_id, login_ext, ext_sources_id, loa, last_access)
VALUES (1, 1, 'login_ext1', 1, 1, ???);
INSERT INTO user_ext_sources(id, user_id, login_ext, ext_sources_id, loa, last_access)
VALUES (2, 2, 'login_ext2', 2, 2, ???);
INSERT INTO user_ext_sources(id, user_id, login_ext, ext_sources_id, loa, last_access)
VALUES (3, 3, 'login_ext3', 3, 0, ???);

INSERT INTO resources(id, facility_id, name, dsc, vo_id) VALUES (1, 1, 'resource1', 'dsc1', 1);
INSERT INTO resources(id, facility_id, name, dsc, vo_id) VALUES (2, 2, 'resource2', 'dsc2', 2);
INSERT INTO resources(id, facility_id, name, dsc, vo_id) VALUES (3, 3, 'resource3', 'dsc3', 3);

INSERT INTO attr_names(id, attr_name, type) VALUES (1, 'facility_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (2, 'facility_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (3, 'facility_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (4, 'facility_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (5, 'facility_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (6, 'facility_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (7, 'facility_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (8, 'group_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (9, 'group_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (10, 'group_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (11, 'group_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (12, 'group_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (13, 'group_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (14, 'group_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (15, 'host_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (16, 'host_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (17, 'host_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (18, 'host_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (19, 'host_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (20, 'host_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (21, 'host_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (22, 'member_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (23, 'member_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (24, 'member_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (25, 'member_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (26, 'member_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (27, 'member_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (28, 'member_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (29, 'resource_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (30, 'resource_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (31, 'resource_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (32, 'resource_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (33, 'resource_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (34, 'resource_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (35, 'resource_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (36, 'service_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (37, 'service_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (38, 'service_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (39, 'service_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (40, 'service_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (41, 'service_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (42, 'service_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (43, 'user_ext_source_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (44, 'user_ext_source_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (45, 'user_ext_source_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (46, 'user_ext_source_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (47, 'user_ext_source_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (48, 'user_ext_source_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (49, 'user_ext_source_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (50, 'user_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (51, 'user_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (52, 'user_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (53, 'user_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (54, 'user_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (55, 'user_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (56, 'user_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (57, 'vo_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (58, 'vo_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (59, 'vo_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (60, 'vo_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (61, 'vo_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (62, 'vo_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (63, 'vo_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (64, 'group_resource_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (65, 'group_resource_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (66, 'group_resource_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (67, 'group_resource_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (68, 'group_resource_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (69, 'group_resource_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (70, 'group_resource_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (71, 'member_group_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (72, 'member_group_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (73, 'member_group_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (74, 'member_group_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (75, 'member_group_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (76, 'member_group_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (77, 'member_group_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (78, 'member_resource_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (79, 'member_resource_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (80, 'member_resource_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (81, 'member_resource_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (82, 'member_resource_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (83, 'member_resource_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (84, 'member_resource_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (85, 'user_facility_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (86, 'user_facility_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (87, 'user_facility_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (88, 'user_facility_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (89, 'user_facility_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (90, 'user_facility_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (91, 'user_facility_attr_larray', 'java.lang.LargeArrayList');
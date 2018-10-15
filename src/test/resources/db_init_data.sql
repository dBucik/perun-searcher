INSERT INTO ext_sources(id, name, type) VALUES (1, 'ext_source1', 'LDAP');
INSERT INTO ext_sources(id, name, type) VALUES (2, 'ext_source2', 'LDAP');
INSERT INTO ext_sources(id, name, type) VALUES (3, 'ext_source3', 'IdP');
INSERT INTO ext_sources(id, name, type) VALUES (4, 'ext_source4', 'IdP');

INSERT INTO facilities(id, name, dsc) VALUES (1, 'facility1', 'dsc1');
INSERT INTO facilities(id, name, dsc) VALUES (2, 'facility2', 'dsc2');
INSERT INTO facilities(id, name, dsc) VALUES (3, 'facility3', 'dsc3');

INSERT INTO vos(id, name, short_name) VALUES (1, 'virtual_organization1', 'vo1');
INSERT INTO vos(id, name, short_name) VALUES (2, 'virtual_organization2', 'vo2');
INSERT INTO vos(id, name, short_name) VALUES (3, 'virtual_organization3', 'vo3');

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
VALUES (1, 1, 'login_ext1', 1, 0, (TIMESTAMP '2018-01-01 08:00:00'));
INSERT INTO user_ext_sources(id, user_id, login_ext, ext_sources_id, loa, last_access)
VALUES (2, 2, 'login_ext2', 2, 1, (TIMESTAMP '2018-01-02 08:00:00'));
INSERT INTO user_ext_sources(id, user_id, login_ext, ext_sources_id, loa, last_access)
VALUES (3, 3, 'login_ext3', 3, 2, (TIMESTAMP '2018-01-03 08:00:00'));

INSERT INTO members(id, vo_id, user_id, sponsored) VALUES (1, 1, 1, true);
INSERT INTO members(id, vo_id, user_id, sponsored) VALUES (2, 2, 2, false);
INSERT INTO members(id, vo_id, user_id, sponsored) VALUES (3, 3, 3, true);

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

INSERT INTO attr_names(id, attr_name, type) VALUES (36, 'user_ext_source_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (37, 'user_ext_source_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (38, 'user_ext_source_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (39, 'user_ext_source_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (40, 'user_ext_source_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (41, 'user_ext_source_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (42, 'user_ext_source_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (43, 'user_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (44, 'user_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (45, 'user_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (46, 'user_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (47, 'user_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (48, 'user_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (49, 'user_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (50, 'vo_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (51, 'vo_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (52, 'vo_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (53, 'vo_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (54, 'vo_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (55, 'vo_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (56, 'vo_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (57, 'group_resource_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (58, 'group_resource_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (59, 'group_resource_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (60, 'group_resource_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (61, 'group_resource_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (62, 'group_resource_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (63, 'group_resource_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (64, 'member_group_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (65, 'member_group_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (66, 'member_group_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (67, 'member_group_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (68, 'member_group_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (69, 'member_group_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (70, 'member_group_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (71, 'member_resource_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (72, 'member_resource_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (73, 'member_resource_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (74, 'member_resource_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (75, 'member_resource_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (76, 'member_resource_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (77, 'member_resource_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (78, 'user_facility_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (79, 'user_facility_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (80, 'user_facility_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (81, 'user_facility_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (82, 'user_facility_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (83, 'user_facility_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (84, 'user_facility_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO ext_sources_attributes(ext_sources_id, attr_name, attr_value) VALUES (1, 'ext_source_attr1', 'value1');

INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 'value1', null);
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 2, '1', null);
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 3, 'true', null);
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 4, null, '1,2');
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 5, null, 'key1:value1,key2:value2');
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 6, null, 'long_value1');
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 7, null, '1,2');

INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 1, 'value2', null);
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, '2', null);
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 3, 'false', null);
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 4, null, '3,4');
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 5, null, 'key3:value3,key4:value4');
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 6, null, 'long_value2');
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 7, null, '3,4');

INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (3, 1, 'value3', null);
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (3, 2, '3', null);
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 'true', null);
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (3, 4, null, '5,6');
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (3, 5, null, 'key5:value5,key6:value6');
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (3, 6, null, 'long_value3');
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (3, 7, null, '5,6');

INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (1, 8, 'value1', null);
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (1, 9, '1', null);
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (1, 10, 'true', null);
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (1, 11, null, '1,2');
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (1, 12, null, 'key1:value1,key2:value2');
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (1, 13, null, 'long_value1');
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (1, 14, null, '1,2');

INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (2, 8, 'value2', null);
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (2, 9, '2', null);
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (2, 10, 'false', null);
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (2, 11, null, '3,4');
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (2, 12, null, 'key3:value3,key4:value4');
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (2, 13, null, 'long_value2');
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (2, 14, null, '3,4');

INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (3, 8, 'value3', null);
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (3, 9, '3', null);
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (3, 10, 'true', null);
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (3, 11, null, '5,6');
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (3, 12, null, 'key5:value5,key6:value6');
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (3, 13, null, 'long_value3');
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (3, 14, null, '5,6');

INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (1, 15, 'value1', null);
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (1, 16, '1', null);
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (1, 17, 'true', null);
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (1, 18, null, '1,2');
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (1, 19, null, 'key1:value1,key2:value2');
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (1, 20, null, 'long_value1');
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (1, 21, null, '1,2');

INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (2, 15, 'value2', null);
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (2, 16, '2', null);
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (2, 17, 'false', null);
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (2, 18, null, '3,4');
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (2, 19, null, 'key3:value3,key4:value4');
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (2, 20, null, 'long_value2');
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (2, 21, null, '3,4');

INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (3, 15, 'value3', null);
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (3, 16, '3', null);
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (3, 17, 'true', null);
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (3, 18, null, '5,6');
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (3, 19, null, 'key5:value5,key6:value6');
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (3, 20, null, 'long_value3');
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (3, 21, null, '5,6');

INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (1, 22, 'value1', null);
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (1, 23, '1', null);
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (1, 24, 'true', null);
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (1, 25, null, '1,2');
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (1, 26, null, 'key1:value1,key2:value2');
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (1, 27, null, 'long_value1');
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (1, 28, null, '1,2');

INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (2, 22, 'value2', null);
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (2, 23, '2', null);
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (2, 24, 'false', null);
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (2, 25, null, '3,4');
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (2, 26, null, 'key3:value3,key4:value4');
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (2, 27, null, 'long_value2');
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (2, 28, null, '3,4');

INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (3, 22, 'value3', null);
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (3, 23, '3', null);
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (3, 24, 'true', null);
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (3, 25, null, '5,6');
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (3, 26, null, 'key5:value5,key6:value6');
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (3, 27, null, 'long_value3');
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (3, 28, null, '5,6');

INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 29, 'value1', null);
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 30, '1', null);
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 31, 'true', null);
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 32, null, '1,2');
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 33, null, 'key1:value1,key2:value2');
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 34, null, 'long_value1');
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 35, null, '1,2');

INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 29, 'value2', null);
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 30, '2', null);
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 31, 'false', null);
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 32, null, '3,4');
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 33, null, 'key3:value3,key4:value4');
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 34, null, 'long_value2');
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 35, null, '3,4');

INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (3, 29, 'value3', null);
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (3, 30, '3', null);
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (3, 31, 'true', null);
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (3, 32, null, '5,6');
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (3, 33, null, 'key5:value5,key6:value6');
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (3, 34, null, 'long_value3');
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (3, 35, null, '5,6');

INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (1, 36, 'value1', null);
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (1, 37, '1', null);
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (1, 38, 'true', null);
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (1, 39, null, '1,2');
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (1, 40, null, 'key1:value1,key2:value2');
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (1, 41, null, 'long_value1');
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (1, 42, null, '1,2');

INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (2, 36, 'value2', null);
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (2, 37, '2', null);
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (2, 38, 'false', null);
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (2, 39, null, '3,4');
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (2, 40, null, 'key3:value3,key4:value4');
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (2, 41, null, 'long_value2');
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (2, 42, null, '3,4');

INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (3, 36, 'value3', null);
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (3, 37, '3', null);
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (3, 38, 'true', null);
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (3, 39, null, '5,6');
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (3, 40, null, 'key5:value5,key6:value6');
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (3, 41, null, 'long_value3');
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (3, 42, null, '5,6');

INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (1, 43, 'value1', null);
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (1, 44, '1', null);
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (1, 45, 'true', null);
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (1, 46, null, '1,2');
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (1, 47, null, 'key1:value1,key2:value2');
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (1, 48, null, 'long_value1');
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (1, 49, null, '1,2');

INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (2, 43, 'value2', null);
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (2, 44, '2', null);
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (2, 45, 'false', null);
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (2, 46, null, '3,4');
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (2, 47, null, 'key3:value3,key4:value4');
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (2, 48, null, 'long_value2');
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (2, 49, null, '3,4');

INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (3, 43, 'value3', null);
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (3, 44, '3', null);
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (3, 45, 'true', null);
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (3, 46, null, '5,6');
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (3, 47, null, 'key5:value5,key6:value6');
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (3, 48, null, 'long_value3');
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (3, 49, null, '5,6');

INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (1, 50, 'value1', null);
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (1, 51, '1', null);
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (1, 52, 'true', null);
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (1, 53, null, '1,2');
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (1, 54, null, 'key1:value1,key2:value2');
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (1, 55, null, 'long_value1');
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (1, 56, null, '1,2');

INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (2, 50, 'value2', null);
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (2, 51, '2', null);
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (2, 52, 'false', null);
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (2, 53, null, '3,4');
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (2, 54, null, 'key3:value3,key4:value4');
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (2, 55, null, 'long_value2');
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (2, 56, null, '3,4');

INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (3, 50, 'value3', null);
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (3, 51, '3', null);
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (3, 52, 'true', null);
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (3, 53, null, '5,6');
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (3, 54, null, 'key5:value5,key6:value6');
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (3, 55, null, 'long_value3');
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (3, 56, null, '5,6');

INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 57, 'value1', null);
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 58, '1', null);
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 59, 'true', null);
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 60, null, '1,2');
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 61, null, 'key1:value1,key2:value2');
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 62, null, 'long_value1');
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 63, null, '1,2');

INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 57, 'value2', null);
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 58, '2', null);
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 59, 'false', null);
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 60, null, '3,4');
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 61, null, 'key3:value3,key4:value4');
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 62, null, 'long_value2');
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 63, null, '3,4');

INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 57, 'value3', null);
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 58, '3', null);
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 59, 'false', null);
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 60, null, '5,6');
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 61, null, 'key5:value5,key6:value6');
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 62, null, 'long_value3');
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 63, null, '5,6');

INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 57, 'value1', null);
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 58, '1', null);
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 59, 'true', null);
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 60, null, '1,2');
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 61, null, 'key1:value1,key2:value2');
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 62, null, 'long_value1');
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 63, null, '1,2');

INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 57, 'value2', null);
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 58, '2', null);
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 59, 'false', null);
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 60, null, '3,4');
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 61, null, 'key3:value3,key4:value4');
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 62, null, 'long_value2');
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 63, null, '3,4');

INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 57, 'value3', null);
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 58, '3', null);
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 59, 'false', null);
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 60, null, '5,6');
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 61, null, 'key5:value5,key6:value6');
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 62, null, 'long_value3');
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 63, null, '5,6');

INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 57, 'value1', null);
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 58, '1', null);
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 59, 'true', null);
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 60, null, '1,2');
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 61, null, 'key1:value1,key2:value2');
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 62, null, 'long_value1');
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 63, null, '1,2');

INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 57, 'value2', null);
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 58, '2', null);
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 59, 'false', null);
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 60, null, '3,4');
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 61, null, 'key3:value3,key4:value4');
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 62, null, 'long_value2');
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 63, null, '3,4');

INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 57, 'value3', null);
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 58, '3', null);
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 59, 'false', null);
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 60, null, '5,6');
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 61, null, 'key5:value5,key6:value6');
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 62, null, 'long_value3');
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 63, null, '5,6');

INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 57, 'value1', null);
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 58, '1', null);
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 59, 'true', null);
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 60, null, '1,2');
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 61, null, 'key1:value1,key2:value2');
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 62, null, 'long_value1');
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 63, null, '1,2');

INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 57, 'value2', null);
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 58, '2', null);
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 59, 'false', null);
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 60, null, '3,4');
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 61, null, 'key3:value3,key4:value4');
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 62, null, 'long_value2');
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 63, null, '3,4');

INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 57, 'value3', null);
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 58, '3', null);
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 59, 'false', null);
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 60, null, '5,6');
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 61, null, 'key5:value5,key6:value6');
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 62, null, 'long_value3');
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (3, 3, 63, null, '5,6');

INSERT INTO vo_ext_sources(vo_id, ext_sources_id) VALUES (1, 1);
INSERT INTO vo_ext_sources(vo_id, ext_sources_id) VALUES (2, 2);
INSERT INTO vo_ext_sources(vo_id, ext_sources_id) VALUES (3, 3);
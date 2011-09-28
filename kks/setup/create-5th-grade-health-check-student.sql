insert into kks_collection_class (
  id
  ,type_code
  ,name
  ,description
  ,consent_type
) VALUES (
	5
  ,'kks.kehitysasialaji.5lk.koulu.terveystarkastus.oppilas'   -- type_code
  ,'5. Luokan kouluterveystarkastus, Oppilaan lomake'  -- name
  ,''  -- description
  ,'kks.suostumus.5lk.koulu.terveystarkastus'  -- consent_type
);

insert into kks_group (
  group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,collection_class_id
) VALUES (
   60   -- group_id
  ,1   -- sort_order
  ,''  -- name
  ,''  -- description
  ,'healthcareregistry'  -- register
  ,5
);

insert into kks_group (
   group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,parent_id
  ,collection_class_id
) VALUES (
   61   -- group_id
  ,1   -- sort_order
  ,'Koulunkäynti/Kaverit'  -- name
  ,''  -- description
  ,'healthcareregistry'  -- register
  ,60   -- parent_id
  ,5
);

insert into kks_group (
   group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,parent_id
  ,collection_class_id
) VALUES (
   62   -- group_id
  ,2   -- sort_order
  ,'Elämäntavat'  -- name
  ,''  -- description
  ,'healthcareregistry'  -- register
  ,60   -- parent_id
  ,5
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   201   -- entry_class_id
  ,1   -- sort_order
  ,'Miten koulunkäyntisi on sujunut?'  -- name
  ,'Oppilaan koulunkäynti ja kaverit'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,61   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   202   -- entry_class_id
  ,2   -- sort_order
  ,'Viihdytkö koulussa?'  -- name
  ,'Oppilaan koulunkäynti ja kaverit'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,61   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   203   -- entry_class_id
  ,3   -- sort_order
  ,'Oletko tyytyväinen koulumenestykseesi?'  -- name
  ,'Oppilaan koulunkäynti ja kaverit'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,61   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   204   -- entry_class_id
  ,4   -- sort_order
  ,'Pidätkö opettajastasi?'  -- name
  ,'Oppilaan koulunkäynti ja kaverit'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,61   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   205   -- entry_class_id
  ,5   -- sort_order
  ,'Kuinka paljon käytät aikaa läksyjen tekoon? Saatko tarvittaessa apua läksyjen tekoon?'  -- name
  ,'Oppilaan koulunkäynti ja kaverit'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,61   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   224   -- entry_class_id
  ,5   -- sort_order
  ,'Onko sinulla kavereita kotona ja koulussa?'  -- name
  ,'Oppilaan koulunkäynti ja kaverit'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,61   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   206   -- entry_class_id
  ,6   -- sort_order
  ,'Kiusataanko, syrjitäänkö tai ärsytetäänkö sinua koulussa tai kaveripiirissä? Miten?'  -- name
  ,'Oppilaan koulunkäynti ja kaverit'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,61   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   207   -- entry_class_id
  ,7   -- sort_order
  ,'Miten selviät kiusaamistilanteesta? Miten kiusaamistilanteet on hoidettu? Miten kiusaamistilanteet pitäisi mielestäsi hoitaa?'  -- name
  ,'Oppilaan koulunkäynti ja kaverit'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,61   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   208   -- entry_class_id
  ,8   -- sort_order
  ,'Kiusaatko itse? Miksi?'  -- name
  ,'Oppilaan koulunkäynti ja kaverit'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,61   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   209   -- entry_class_id
  ,9   -- sort_order
  ,'Paljonko ulkoilet päivittäin (tunteina)'  -- name
  ,'Oppilaan vapaa-aika ja liikunta'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   210   -- entry_class_id
  ,10   -- sort_order
  ,'Paljonko katsot TV:tä (tunteina)'  -- name
  ,'Oppilaan vapaa-aika ja liikunta'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   211   -- entry_class_id
  ,11   -- sort_order
  ,'Paljonko pelaat erilaisia pelejä (tunteina)?'  -- name
  ,'Oppilaan vapaa-aika ja liikunta'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   212   -- entry_class_id
  ,12   -- sort_order
  ,'Mitä yleensä teet vapaa-aikanasi? Onko sinulla jokin harrastus?'  -- name
  ,'Oppilaan hygienia'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   213   -- entry_class_id
  ,13   -- sort_order
  ,'Miten hoidat henkilökohtaisen hygieniasi?'  -- name
  ,'Oppilaan hygienia'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   214   -- entry_class_id
  ,14   -- sort_order
  ,'Kuinka usein harjaat hampaasi?'  -- name
  ,'Oppilaan hygienia'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
);


insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   215   -- entry_class_id
  ,15   -- sort_order
  ,'Oletko havainnut itsessäsi murrosiän merkkejä kuten iho-ongelmia, hikoilun lisääntymistä jne?'  -- name
  ,'Oppilaan hygienia'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   216   -- entry_class_id
  ,16   -- sort_order
  ,'KYSYMYS TYTÖILLE: Onko kuukautisesi alkaneet? Milloin?'  -- name
  ,'Oppilaan hygienia'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   217   -- entry_class_id
  ,17   -- sort_order
  ,'Mitä syöt aamuisin ennen kouluun tuloa?'  -- name
  ,'Oppilaan ruokailu'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   218   -- entry_class_id
  ,18   -- sort_order
  ,'Syötkö kouluruoan?'  -- name
  ,'Oppilaan ruokailu'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   219   -- entry_class_id
  ,19   -- sort_order
  ,'Miten nukut? Häiritseekö joku asia untasi?'  -- name
  ,'Oppilaan elämäntavat'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   220   -- entry_class_id
  ,20   -- sort_order
  ,'Miten suhtaudut tupakkaan ja päihteisiin?'  -- name
  ,'Oppilaan elämäntavat'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   221   -- entry_class_id
  ,21   -- sort_order
  ,'Oletko mielestäsi terve? Miksi et?'  -- name
  ,'Oppilaan elämäntavat'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   222   -- entry_class_id
  ,22   -- sort_order
  ,'Oletko yleensä iloinen ja tyytyväinen itseesi? Miksi et?'  -- name
  ,'Oppilaan elämäntavat'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   223   -- entry_class_id
  ,23   -- sort_order
  ,'Huolestuttaako sinua jokin asia tällä hetkellä itsessäsi, kaveripiirissä, kotona tai koulussa? Mikä?'  -- name
  ,'Oppilaan elämäntavat'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   201   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   20   -- entry_class_id
  ,66   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   202   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   202   -- entry_class_id
  ,66   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   203   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   203   -- entry_class_id
  ,66   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   204   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   204   -- entry_class_id
  ,66   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   205   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   205   -- entry_class_id
  ,66   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   206   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   206   -- entry_class_id
  ,66   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   207   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   207   -- entry_class_id
  ,66   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   224   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   224   -- entry_class_id
  ,66   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   208   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   208   -- entry_class_id
  ,67   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   209   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   209   -- entry_class_id
  ,67   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   210   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   210   -- entry_class_id
  ,67   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   211   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   211   -- entry_class_id
  ,67   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   212   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   212   -- entry_class_id
  ,67   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   213   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   213   -- entry_class_id
  ,67   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   214   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   214  -- entry_class_id
  ,67   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   215   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   215   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   216   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   216   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   217   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   217   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   218   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   218   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   219   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   219   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   220   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   220   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   221   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   221   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   222   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   222   -- entry_class_id
  ,40   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   223   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   223   -- entry_class_id
  ,40   -- tag_id
);


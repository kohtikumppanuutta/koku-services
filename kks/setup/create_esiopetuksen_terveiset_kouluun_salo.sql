insert into kks_collection_class (
  id
  ,type_code
  ,name
  ,description
  ,consent_type
) VALUES (
	90
  ,'kks.kehitysasialaji.esiopetuksen_terveiset_kouluun'   -- type_code
  ,'Esiopetuksen terveiset kouluun'  -- name
  ,''  -- description
  ,'kks.suostumus.esiopetuksen_terveiset_kouluun'  -- concent_type
);


insert into kks_group (
  group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,accountable
  ,collection_class_id
) VALUES (
   700   -- group_id
  ,1   -- sort_order
  ,'Päivähoito'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,90
);

insert into kks_group (
   group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,accountable
  ,collection_class_id
) VALUES (
   706   -- group_id
  ,1   -- sort_order
  ,'Huoltaja/esikoululainen'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,90
);


insert into kks_group (
   group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,accountable
  ,parent_id
  ,collection_class_id
) VALUES (
   701   -- group_id
  ,2   -- sort_order
  ,'Sosiaaliset taidot/kaverisuhteet'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,700   -- parent_id
  ,90
);


insert into kks_group (
   group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,accountable
  ,parent_id
  ,collection_class_id
) VALUES (
   702   -- group_id
  ,1   -- sort_order
  ,''  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,700   -- parent_id
  ,90
);


insert into kks_group (
   group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,accountable
  ,parent_id
  ,collection_class_id
) VALUES (
   703   -- group_id
  ,3   -- sort_order
  ,'Kielelliset valmiudet'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,700   -- parent_id
  ,90
);

insert into kks_group (
   group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,accountable
  ,parent_id
  ,collection_class_id
) VALUES (
   704   -- group_id
  ,4   -- sort_order
  ,'Matemaattiset valmiudet'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,700   -- parent_id
  ,90
);

insert into kks_group (
   group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,accountable
  ,parent_id
  ,collection_class_id
) VALUES (
   705   -- group_id
  ,5   -- sort_order
  ,''  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,700   -- parent_id
  ,90
);

insert into kks_group (
   group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,accountable
  ,parent_id
  ,collection_class_id
) VALUES (
   707   -- group_id
  ,1   -- sort_order
  ,''  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,706   -- parent_id
  ,90
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
   900   -- entry_class_id
  ,1   -- sort_order
  ,'Vahvuudet:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,701   -- entry_group
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
   901   -- entry_class_id
  ,2   -- sort_order
  ,'Tuen tarve:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,701   -- entry_group
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
   902   -- entry_class_id
  ,3   -- sort_order
  ,'Hyväksi koetut toimintamallit:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,701   -- entry_group
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
   903   -- entry_class_id
  ,4   -- sort_order
  ,'Omatoimisuus:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,702   -- entry_group
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
   904   -- entry_class_id
  ,5   -- sort_order
  ,'Keskittyminen:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,702   -- entry_group
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
   905   -- entry_class_id
  ,6   -- sort_order
  ,'Vahvuudet :'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,703   -- entry_group
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
   906   -- entry_class_id
  ,7   -- sort_order
  ,'Tuen tarve:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,703   -- entry_group
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
   907   -- entry_class_id
  ,8   -- sort_order
  ,'Hyväksi koetut toimintamallit:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,703   -- entry_group
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
   908   -- entry_class_id
  ,9   -- sort_order
  ,'Vahvuudet:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,704   -- entry_group
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
   909   -- entry_class_id
  ,10   -- sort_order
  ,'Tuen tarve:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,704   -- entry_group
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
   910   -- entry_class_id
  ,11   -- sort_order
  ,'Hyväksi koetut toimintamallit:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,704   -- entry_group
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
   911   -- entry_class_id
  ,12   -- sort_order
  ,'Muuta:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,705   -- entry_group
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
   912   -- entry_class_id
  ,13   -- sort_order
  ,'Esikoululaisen terveiset opettajalle:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,707   -- entry_group
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   900   -- entry_class_id
  ,12   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   900   -- entry_class_id
  ,27   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   901   -- entry_class_id
  ,12   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   901   -- entry_class_id
  ,39   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   902   -- entry_class_id
  ,12   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   902   -- entry_class_id
  ,68   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   903   -- entry_class_id
  ,12   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   903   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   904   -- entry_class_id
  ,12   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   904   -- entry_class_id
  ,36   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   904   -- entry_class_id
  ,37   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   905   -- entry_class_id
  ,12   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   905   -- entry_class_id
  ,27   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   906   -- entry_class_id
  ,12   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   906   -- entry_class_id
  ,39   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   907   -- entry_class_id
  ,12   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   907   -- entry_class_id
  ,68   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   908   -- entry_class_id
  ,12   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   908   -- entry_class_id
  ,27   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   909   -- entry_class_id
  ,12   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   909   -- entry_class_id
  ,39   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   910   -- entry_class_id
  ,12   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   910   -- entry_class_id
  ,68   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   911   -- entry_class_id
  ,12   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   911   -- entry_class_id
  ,69   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   912   -- entry_class_id
  ,12   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   912   -- entry_class_id
  ,53   -- tag_id
);


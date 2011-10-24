

insert into kks_collection_class (
  id
  ,type_code
  ,name
  ,description
  ,consent_type
) VALUES (
	6
  ,'kks.kehitysasialaji.5lk_koulu_terveystarkastus.terveydenhoitaja'   -- type_code
  ,'5. Luokan kouluterveystarkastus, Tietoja terveystarkastuksesta'  -- name
  ,''  -- description
  ,'kks.suostumus.5lk_koulu_terveystarkastus'  -- consent_type
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
   70   -- group_id
  ,1   -- sort_order
  ,''  -- name
  ,''  -- description
  ,'healthcareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,6
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
   71   -- group_id
  ,1   -- sort_order
  ,'Oppilaan terveystarkastuksen tiedot'  -- name
  ,''  -- description
  ,'healthcareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,70   -- parent_id
  ,6
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
   251   -- entry_class_id
  ,1   -- sort_order
  ,'Pituus (cm)'  -- name
  ,'Oppilaan terveystarkastuksen tiedot'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,71   -- entry_group
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
   252   -- entry_class_id
  ,2   -- sort_order
  ,'Paino (kg)'  -- name
  ,'Oppilaan terveystarkastuksen tiedot'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,71   -- entry_group
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
   253   -- entry_class_id
  ,3   -- sort_order
  ,'Ryhti'  -- name
  ,'Oppilaan terveystarkastuksen tiedot'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,71   -- entry_group
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
   254   -- entry_class_id
  ,4   -- sort_order
  ,'Näkö ilman laseja'  -- name
  ,'Oppilaan terveystarkastuksen tiedot'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,71   -- entry_group
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
   255   -- entry_class_id
  ,5   -- sort_order
  ,'Näkö laseilla'  -- name
  ,'Oppilaan terveystarkastuksen tiedot'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,71   -- entry_group
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
   256   -- entry_class_id
  ,6   -- sort_order
  ,'Värinäkö'  -- name
  ,'Oppilaan terveystarkastuksen tiedot'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,71   -- entry_group
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
   257   -- entry_class_id
  ,7   -- sort_order
  ,'Kuulo'  -- name
  ,'Oppilaan terveystarkastuksen tiedot'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,71   -- entry_group
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
   258   -- entry_class_id
  ,8   -- sort_order
  ,'Verenpaine'  -- name
  ,'Oppilaan terveystarkastuksen tiedot'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,71   -- entry_group
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
   259   -- entry_class_id
  ,9   -- sort_order
  ,'Rokotukset'  -- name
  ,'Oppilaan terveystarkastuksen tiedot'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,71   -- entry_group
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
   260   -- entry_class_id
  ,10   -- sort_order
  ,'Muut tutkimukset'  -- name
  ,'Oppilaan terveystarkastuksen tiedot'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,71   -- entry_group
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
   261   -- entry_class_id
  ,11   -- sort_order
  ,'Huomitoivaa'  -- name
  ,'Oppilaan terveystarkastuksen tiedot'  -- description
  ,0   -- multi_value
  ,'MULTI_SELECT'  -- data_type
  ,'Kasvu ja kehitys, Ravitsemus, Vapaa-aika ja harrastukset, Uni/Lepo, Liikunta, Murrosikä, Puhtaus, Päihteet, Kaverisuhteet, Kiusaaminen, Mieliala, Ruutuaika'  -- value_spaces
  ,71   -- entry_group
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
   262   -- entry_class_id
  ,12   -- sort_order
  ,'Tarkennuksia huomioitaviin asioihin'  -- name
  ,'Oppilaan terveystarkastuksen tiedot'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,71   -- entry_group
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
   263   -- entry_class_id
  ,13   -- sort_order
  ,'Ammatinvalinta-asioihin liittyvä palaute on annettu'  -- name
  ,'Oppilaan terveystarkastuksen tiedot'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'Kyllä,Ei'  -- value_spaces
  ,71   -- entry_group
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
   264   -- entry_class_id
  ,14   -- sort_order
  ,'Jatkolähete on tehty? Kenelle?'  -- name
  ,'Oppilaan terveystarkastuksen tiedot'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,71   -- entry_group
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
   265   -- entry_class_id
  ,15   -- sort_order
  ,'Huoltajaa pyydetään ottamaan yhteyttä kouluterveydenhuoltoon'  -- name
  ,'Oppilaan terveystarkastuksen tiedot'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'Kyllä,Ei'  -- value_spaces
  ,71   -- entry_group
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
   266   -- entry_class_id
  ,16   -- sort_order
  ,'Tarkastuksessa on annettu terveyden edistämiseen liittyvää materiaalia'  -- name
  ,'Oppilaan terveystarkastuksen tiedot'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'Kyllä,Ei'  -- value_spaces
  ,71   -- entry_group
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
   267   -- entry_class_id
  ,17   -- sort_order
  ,'Tarkastuksessa on käyty terveyden edistämiseen liittyvä keskustelu'  -- name
  ,'Oppilaan terveystarkastuksen tiedot'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'Kyllä,Ei'  -- value_spaces
  ,71   -- entry_group
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
   268   -- entry_class_id
  ,18   -- sort_order
  ,'Jatkoseuranta'  -- name
  ,'Oppilaan terveystarkastuksen tiedot'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,71   -- entry_group
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
   269   -- entry_class_id
  ,19   -- sort_order
  ,'Koululääkärin tarkastukseen varattu aika ja paikka'  -- name
  ,'Oppilaan terveystarkastuksen tiedot'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,71   -- entry_group
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   251   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   251   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   252   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   252   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   253   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   253   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   254   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   254   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   255   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   255   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   256   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   256   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   257   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   257   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   258   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   258   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   259   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   259   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   260   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   260   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   261   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   261   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   262   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   262   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   263   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   263   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   264   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   264   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   265   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   265   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   266   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   266   -- entry_class_id
  ,67   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   267   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   267   -- entry_class_id
  ,67   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   268   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   268   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   269   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   269   -- entry_class_id
  ,41   -- tag_id
);
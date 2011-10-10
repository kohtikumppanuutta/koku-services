insert into kks_collection_class (
  id
  ,type_code
  ,name
  ,description
  ,consent_type
) VALUES (
	1
  ,'kks.kehitysasialaji.vasu'   -- type_code
  ,'Varhaiskasvatussuunnitelma'  -- name
  ,''  -- description
  ,'kks.suostumus.vasu'  -- concent_type
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
   10   -- group_id
  ,1   -- sort_order
  ,'Huoltaja'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,1
);

-- Ryhmat

insert into kks_group (
  group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,accountable
  ,collection_class_id
) VALUES (
   11   -- group_id
  ,2   -- sort_order
  ,'Päivähoito'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,1
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
   12   -- group_id
  ,1   -- sort_order
  ,'Lapsen hyvinvointi'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,10   -- parent_id
  ,1
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
   13   -- group_id
  ,2   -- sort_order
  ,'Vuorovaikutus'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,10   -- parent_id
  ,1
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
   14   -- group_id
  ,3   -- sort_order
  ,'Kodin ja päivähoidon yhteistyö'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,10   -- parent_id
  ,1
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
   15   -- group_id
  ,3   -- sort_order
  ,''  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,11 -- group id
  ,1
);

-- Huoltaja

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
   2   -- entry_class_id
  ,1   -- sort_order
  ,'Miten lapsesi on viihtynyt päivähoidossa'  -- name
  ,'Kuvaa lapsen hyvinvointia'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,12   -- entry_group
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
   3   -- entry_class_id
  ,2   -- sort_order
  ,'Mitä lapsesi kertoo päivähoidosta kotona'  -- name
  ,'Kuvaa lapsen hyvinvointia'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,12   -- entry_group
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
   4   -- entry_class_id
  ,3   -- sort_order
  ,'Lapsellesi läheiset ja tärkeät ihmiset'  -- name
  ,'Kuvaa lapsen hyvinvointia'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,12   -- entry_group
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
   5   -- entry_class_id
  ,4   -- sort_order
  ,'Onko sinulla perushoitoon liittyen jotain ajatuksia tai kysyttävää'  -- name
  ,'Pukeminen ja riisuminen, hygienia, ruokailu, ulkoilu, lepohetki'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,12   -- entry_group
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
   6   -- entry_class_id
  ,5   -- sort_order
  ,'Mietityttääkö sinua jokin asia lapsesi kehityksessä'  -- name
  ,'mm. pelot, arkuus, sosiaaliset taidot, puhe, motoriikka, siisteyskasvatus, perhetilanne, kasvatuskysymykset'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,12   -- entry_group
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
   7   -- entry_class_id
  ,6   -- sort_order
  ,'Miten lapsesi suhtautuu uusiin tilanteisiin ja asioihin'  -- name
  ,'Kuvaa lapsen hyvinvointia'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,12   -- entry_group
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
   8   -- entry_class_id
  ,7   -- sort_order
  ,'Onko lapsellasi pelkoja'  -- name
  ,'Kuvaa lapsen hyvinvointia'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,12   -- entry_group
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
   9   -- entry_class_id
  ,8   -- sort_order
  ,'Oletko jostain asiasta huolissasi lapseesi liittyen'  -- name
  ,'mm. pelot, arkuus, sosiaaliset taidot, puhe, motoriikka, siisteyskasvatus, perhetilanne, kasvatuskysymykset'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,12   -- entry_group
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
   10   -- entry_class_id
  ,9   -- sort_order
  ,'Mitkä asiat erityisesti tuottavat iloa lapsessasi'  -- name
  ,'Kuvaa lapsen hyvinvointia'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,12   -- entry_group
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
   11   -- entry_class_id
  ,10   -- sort_order
  ,'Mistä asioista lapsesi on kiinnostunut'  -- name
  ,'Kuvaa lapsen hyvinvointia'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,12   -- entry_group
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
   12   -- entry_class_id
  ,11   -- sort_order
  ,'Miten lapsesi toiveet huomioidaan'  -- name
  ,'Kuvaa lapsen hyvinvointia'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,12   -- entry_group
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
   13   -- entry_class_id
  ,12   -- sort_order
  ,'Mitä ajatuksia lapsesi päivähoitoon tulo- ja lähtötilanteet sinussa herättävät'  -- name
  ,'Kuvaa lapsen vuorovaikutusta'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,13   -- entry_group
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
   14   -- entry_class_id
  ,13   -- sort_order
  ,'Miten ilo tai suru näkyy lapsessasi'  -- name
  ,'Kuvaa lapsen vuorovaikutusta'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,13   -- entry_group
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
   15   -- entry_class_id
  ,14   -- sort_order
  ,'Milloin lapsesi haluaa olla yksin'  -- name
  ,'Kuvaa lapsen vuorovaikutusta'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,13   -- entry_group
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
   16   -- entry_class_id
  ,15   -- sort_order
  ,'Onko lapsellasi kavereita kotona? päivähoidossa?'  -- name
  ,'Kuvaa lapsen vuorovaikutusta'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,13   -- entry_group
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
   17   -- entry_class_id
  ,16   -- sort_order
  ,'Miten lapsesi suhtautuu toisiin lapsiin? Lapsesi ryhmän jäsenenä?'  -- name
  ,'Kuvaa lapsen vuorovaikutusta'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,13   -- entry_group
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
   18   -- entry_class_id
  ,17   -- sort_order
  ,'Miten lapsesi osaa ottaa toisen lapsen tunteet huomioon?'  -- name
  ,'Kuvaa lapsen vuorovaikutusta'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,13   -- entry_group
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
   19   -- entry_class_id
  ,18   -- sort_order
  ,'Miten lapsesi suhtautuu aikuisiin?'  -- name
  ,'Kuvaa lapsen vuorovaikutusta'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,13   -- entry_group
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
   20   -- entry_class_id
  ,19   -- sort_order
  ,'Missä tilanteissa lapsesi pyytää apua?'  -- name
  ,'Kuvaa lapsen vuorovaikutusta'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,13   -- entry_group
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
   21   -- entry_class_id
  ,20   -- sort_order
  ,'Miten lapsesi käyttäytyy ristiriitatilanteissa?'  -- name
  ,'Kuvaa lapsen vuorovaikutusta'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,13   -- entry_group
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
   22   -- entry_class_id
  ,21   -- sort_order
  ,'Mitä lapsesi leikkii? Kenen kanssa?'  -- name
  ,'Kuvaa lapsen vuorovaikutusta'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,13   -- entry_group
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
   23   -- entry_class_id
  ,22   -- sort_order
  ,'Miten lapsesi ilmaisee itseään?'  -- name
  ,'Kuvaa lapsen vuorovaikutusta'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,13   -- entry_group
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
   27   -- entry_class_id
  ,26   -- sort_order
  ,'Millaisia asioita toivoisit kerrottavan päivittäin lapsesi arjesta päivähoidossa'  -- name
  ,'Kuvaa kodin ja päivähoidon yhteistyötä'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,14   -- entry_group
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
   28   -- entry_class_id
  ,27   -- sort_order
  ,'Miten yhteistyö hoidetaan molempiin vanhempiin'  -- name
  ,'Kuvaa kodin ja päivähoidon yhteistyötä'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,14   -- entry_group
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
   31   -- entry_class_id
  ,28   -- sort_order
  ,'Millaisia yhteistyön tapoja toivotte'  -- name
  ,'Kuvaa kodin ja päivähoidon yhteistyötä'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,14   -- entry_group
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
   29   -- entry_class_id
  ,29   -- sort_order
  ,'Minkälaiset asiat teille on kasvatuksessa tärkeitä? mitä toivotte meiltä'  -- name
  ,'Kuvaa kodin ja päivähoidon yhteistyötä'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,14   -- entry_group
);

-- Paivahoito

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
   33   -- entry_class_id
  ,1   -- sort_order
  ,'Yhteiset tavoitteet liittyen lapsen hyvinvointiin'  -- name
  ,'Kirjataan yhteiset tavoitteet liittyen lapsen hyvinvointiin'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,15   -- entry_group
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
   24   -- entry_class_id
  ,2   -- sort_order
  ,'Yhteiset tavoitteet liittyen vuorovaikutukseen?'  -- name
  ,'Kirjataan yhteiset tavoitteet liittyen lapsen vuorovaikutukseen'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,15   -- entry_group
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
   25   -- entry_class_id
  ,3   -- sort_order
  ,'Yhteiset tavoitteet liittyen yhteistyöhön?'  -- name
  ,'Kirjataan yhteiset tavoitteet liittyen yhteistyöhön'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,15   -- entry_group
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
   26   -- entry_class_id
  ,4   -- sort_order
  ,'Kommentit, havainnot ja tavoitteet?'  -- name
  ,'Kirjatut kommentit, havainnot ja tavoitteet'  -- description
  ,1   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,15   -- entry_group
);


-- insert relations

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   2   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   2   -- entry_class_id
  ,55   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   2   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   2   -- entry_class_id
  ,40   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   3   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   3   -- entry_class_id
  ,55   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   3   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   3   -- entry_class_id
  ,40   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   4   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   4   -- entry_class_id
  ,55   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   4   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   5   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   5   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   5   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   6   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   6   -- entry_class_id
  ,40   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   6   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   6   -- entry_class_id
  ,32   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   6   -- entry_class_id
  ,20   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   6   -- entry_class_id
  ,25   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   7   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   7   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   7   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   8   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   8   -- entry_class_id
  ,32   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   8   -- entry_class_id
  ,40   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   9   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   9   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   9   -- entry_class_id
  ,32   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   9   -- entry_class_id
  ,20   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   9   -- entry_class_id
  ,25   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   9   -- entry_class_id
  ,40   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   10   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   10   -- entry_class_id
  ,32   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   10   -- entry_class_id
  ,41   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   10   -- entry_class_id
  ,20   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   10   -- entry_class_id
  ,25   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   10   -- entry_class_id
  ,35   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   10   -- entry_class_id
  ,33   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   11   -- entry_class_id
  ,11   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   11   -- entry_class_id
  ,27   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   11   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   12   -- entry_class_id
  ,13   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   12   -- entry_class_id
  ,27   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   12   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   33   -- entry_class_id
  ,54   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   33   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   33   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   13   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   13   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   13   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   14   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   14   -- entry_class_id
  ,32   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   14   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   15   -- entry_class_id
  ,12   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   15   -- entry_class_id
  ,32   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   15   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   16   -- entry_class_id
  ,13   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   16   -- entry_class_id
  ,32   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   16   -- entry_class_id
  ,48   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   16   -- entry_class_id
  ,35   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   16   -- entry_class_id
  ,33   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   17   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   17   -- entry_class_id
  ,32   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   17   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   17   -- entry_class_id
  ,28   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   18   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   18   -- entry_class_id
  ,28   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   18   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   19   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   19   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   19   -- entry_class_id
  ,32   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   20   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   20   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   20   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   21   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   22   -- entry_class_id
  ,12   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   22   -- entry_class_id
  ,28   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   22   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   23   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   23   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   23   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   24   -- entry_class_id
  ,54   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   24   -- entry_class_id
  ,55   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   24   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   25   -- entry_class_id
  ,55   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   25   -- entry_class_id
  ,54   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   26   -- entry_class_id
  ,54   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   26   -- entry_class_id
  ,57   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   26   -- entry_class_id
  ,58   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   26   -- entry_class_id
  ,12   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   27   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   27   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   27   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   28   -- entry_class_id
  ,13   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   28   -- entry_class_id
  ,33   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   28   -- entry_class_id
  ,48   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   29   -- entry_class_id
  ,13   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   29   -- entry_class_id
  ,33   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   29   -- entry_class_id
  ,48   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   31   -- entry_class_id
  ,13   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   31   -- entry_class_id
  ,33   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   31   -- entry_class_id
  ,48   -- tag_id
);
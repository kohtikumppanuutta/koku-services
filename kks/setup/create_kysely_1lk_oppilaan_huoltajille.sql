insert into kks_collection_class (
  id
  ,type_code
  ,name
  ,description
  ,consent_type
) VALUES (
	100
  ,'kks.kehitysasialaji.kysely_1lk_oppilaan_huoltajille'   -- type_code
  ,'Kysely 1. lk:n oppilaan huoltajille'  -- name
  ,''  -- description
  ,''  -- concent_type
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
   750   -- group_id
  ,1   -- sort_order
  ,''  -- name
  ,''  -- description
  ,'healthcareregistry'  -- register
  ,'guardian' -- accountable
  ,100
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
   751   -- group_id
  ,2   -- sort_order
  ,'Lapsen tilanne'  -- name
  ,'Merkitkää valintanne jokaisen väittämän kohdalla siihen sarakkeeseen, joka kuvastaa omaa  näkemystänne lapsenne tilanteesta'  -- description
  ,'healthcareregistry'  -- register
  ,'guardian' -- accountable
  ,750   -- parent_id
  ,100
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
   752   -- group_id
  ,3   -- sort_order
  ,'Perheen tilanne'  -- name
  ,'Merkitkää kyllä jokaisen väittämän kohdalle, joka kuvastaa omaa näkemystänne perheenne tilanteesta'  -- description
  ,'healthcareregistry'  -- register
  ,'guardian' -- accountable
  ,750   -- parent_id
  ,100
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
   753   -- group_id
  ,4   -- sort_order
  ,'Lisätietoja'  -- name
  ,''  -- description
  ,'healthcareregistry'  -- register
  ,'guardian' -- accountable
  ,750   -- parent_id
  ,100
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
   754   -- group_id
  ,5   -- sort_order
  ,'Terveydentila'  -- name
  ,''  -- description
  ,'healthcareregistry'  -- register
  ,'guardian' -- accountable
  ,750   -- parent_id
  ,100
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
   755   -- group_id
  ,5   -- sort_order
  ,'Suostumukset'  -- name
  ,''  -- description
  ,'healthcareregistry'  -- register
  ,'guardian' -- accountable
  ,750   -- parent_id
  ,100
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
   756   -- group_id
  ,1   -- sort_order
  ,''  -- name
  ,''  -- description
  ,'healthcareregistry'  -- register
  ,'guardian' -- accountable
  ,750   -- parent_id
  ,100
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
   1000   -- entry_class_id
  ,1   -- sort_order
  ,'Suunniteltu hoitojärjestely kouluajan ulkopuolella ensimmäisen lukuvuoden aikana:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,755   -- entry_group
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
   1001   -- entry_class_id
  ,1   -- sort_order
  ,'Yö- tai päiväkastelua:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyllä,oppimassa,ei'  -- value_spaces
  ,751   -- entry_group
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
   1002   -- entry_class_id
  ,2   -- sort_order
  ,'Pukee ylleen ilman apua:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyllä,oppimassa,ei'  -- value_spaces
  ,751   -- entry_group
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
   1003   -- entry_class_id
  ,3   -- sort_order
  ,'Syö hyvin ja monipuolisesti:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyllä,oppimassa,ei'  -- value_spaces
  ,751   -- entry_group
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
   1004   -- entry_class_id
  ,4   -- sort_order
  ,'Kestää pettymyksiä:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyllä,oppimassa,ei'  -- value_spaces
  ,751   -- entry_group
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
   1005   -- entry_class_id
  ,5   -- sort_order
  ,'Puhuu vieraiden kanssa:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyllä,oppimassa,ei'  -- value_spaces
  ,751   -- entry_group
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
   1006   -- entry_class_id
  ,6   -- sort_order
  ,'Leikkii toisten kanssa:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyllä,oppimassa,ei'  -- value_spaces
  ,751   -- entry_group
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
   1007   -- entry_class_id
  ,7   -- sort_order
  ,'Pitää puoliaan toisten lasten joukossa:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyllä,oppimassa,ei'  -- value_spaces
  ,751   -- entry_group
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
   1008   -- entry_class_id
  ,8   -- sort_order
  ,'Pelaa yksinkertaisia pelejä sääntöjä noudattaen:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyllä,oppimassa,ei'  -- value_spaces
  ,751   -- entry_group
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
   1009   -- entry_class_id
  ,9   -- sort_order
  ,'Jää kylään tai kerhoon helposti:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyllä,oppimassa,ei'  -- value_spaces
  ,751   -- entry_group
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
   1010   -- entry_class_id
  ,10   -- sort_order
  ,'Pystyy tarvittaessa istumaan rauhallisesti paikoillaan:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyllä,oppimassa,ei'  -- value_spaces
  ,751   -- entry_group
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
   1011   -- entry_class_id
  ,11   -- sort_order
  ,'Riittävästi yhteistä aikaa:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'MULTI_SELECT'  -- data_type
  ,'kyllä'  -- value_spaces
  ,752   -- entry_group
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
   1012   -- entry_class_id
  ,12   -- sort_order
  ,'Yhteiset säännöt:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'MULTI_SELECT'  -- data_type
  ,'kyllä'  -- value_spaces
  ,752   -- entry_group
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
   1013   -- entry_class_id
  ,13   -- sort_order
  ,'Tapana kehua perheenjäseniä:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'MULTI_SELECT'  -- data_type
  ,'kyllä'  -- value_spaces
  ,752   -- entry_group
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
   1014   -- entry_class_id
  ,14   -- sort_order
  ,'Enimmäkseen sopuisa ilmapiiri:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'MULTI_SELECT'  -- data_type
  ,'kyllä'  -- value_spaces
  ,752   -- entry_group
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
   1015   -- entry_class_id
  ,15   -- sort_order
  ,'Turvallista kertoa päivän tapahtumista:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'MULTI_SELECT'  -- data_type
  ,'kyllä'  -- value_spaces
  ,752   -- entry_group
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
   1016   -- entry_class_id
  ,16   -- sort_order
  ,'Yhteinen ruokahetki päivittäin:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'MULTI_SELECT'  -- data_type
  ,'kyllä'  -- value_spaces
  ,752   -- entry_group
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
   1017   -- entry_class_id
  ,17   -- sort_order
  ,'Pitkäaikaissairautta:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'MULTI_SELECT'  -- data_type
  ,'kyllä'  -- value_spaces
  ,752   -- entry_group
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
   1018   -- entry_class_id
  ,18   -- sort_order
  ,'Masennusta:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'MULTI_SELECT'  -- data_type
  ,'kyllä'  -- value_spaces
  ,752   -- entry_group
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
   1019   -- entry_class_id
  ,19   -- sort_order
  ,'Päihteidenkäyttöä:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'MULTI_SELECT'  -- data_type
  ,'kyllä'  -- value_spaces
  ,752   -- entry_group
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
   1020   -- entry_class_id
  ,20   -- sort_order
  ,'Väkivaltaisuutta:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'MULTI_SELECT'  -- data_type
  ,'kyllä'  -- value_spaces
  ,752   -- entry_group
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
   1021   -- entry_class_id
  ,21   -- sort_order
  ,'Taloudellisia huolia:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'MULTI_SELECT'  -- data_type
  ,'kyllä'  -- value_spaces
  ,752   -- entry_group
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
   1022   -- entry_class_id
  ,22   -- sort_order
  ,'Muita perheen tilanteeseen vaikuttavia ajankohtaisia asioita:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,752   -- entry_group
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
   1023   -- entry_class_id
  ,23   -- sort_order
  ,'Lapseni vahvuuksia:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,753   -- entry_group
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
   1024   -- entry_class_id
  ,24   -- sort_order
  ,'Huolta aiheuttavia asioita:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,753   -- entry_group
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
   1025   -- entry_class_id
  ,25   -- sort_order
  ,'Onko kouluvalmiutta selvitelty:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyllä,ei'  -- value_spaces
  ,753   -- entry_group
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
   1026   -- entry_class_id
  ,26   -- sort_order
  ,'Lapsenne nykyinen terveydentila:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'hyvä,tyydyttävä,huono'  -- value_spaces
  ,754   -- entry_group
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
   1027   -- entry_class_id
  ,27   -- sort_order
  ,'Sairaudet, sairaalahoidot, tapaturmat:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,754   -- entry_group
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
   1028   -- entry_class_id
  ,28   -- sort_order
  ,'Jatkuva tai toistuva lääkitys, mikä?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,754   -- entry_group
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
   1029   -- entry_class_id
  ,29   -- sort_order
  ,'Yliherkkyydet: Lääkeaine:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,754   -- entry_group
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
   1030   -- entry_class_id
  ,30   -- sort_order
  ,'Yliherkkyydet: Muu aine:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,754   -- entry_group
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
   1031   -- entry_class_id
  ,31   -- sort_order
  ,'Erityisruokavalio (erillinen lomake erityisruokavaliosta on toimitettava koulu-terveydenhoitajalle jokaisen lukuvuoden alussa):'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyllä,ei'  -- value_spaces
  ,754   -- entry_group
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
   1032   -- entry_class_id
  ,32   -- sort_order
  ,'Saako lapsellenne antaa kouluiässä tarpeelliset perus- ja tehosterokotukset?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyllä,ei'  -- value_spaces
  ,755   -- entry_group
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
   1033   -- entry_class_id
  ,33   -- sort_order
  ,'Saako koulun lääkäri tai terveydenhoitaja kertoa kappaleen "terveystietoja" sisällön opettajalle?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyllä,ei'  -- value_spaces
  ,755   -- entry_group
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1000   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1000   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1001   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1001   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1002   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1002   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1003   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1003   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1004   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1004   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1005   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1005   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1006   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1006   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1007   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1007   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1008   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1008   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1008   -- entry_class_id
  ,36   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1008   -- entry_class_id
  ,37   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1009   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1009   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1009   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1010   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1010   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1010   -- entry_class_id
  ,36   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1010   -- entry_class_id
  ,37   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1011   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1011   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1012   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1012   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1013   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1013   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1013   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1014   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1014   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1014   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1015   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1015   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1015   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1016   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1016   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1016   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1017   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1017   -- entry_class_id
  ,40   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1017   -- entry_class_id
  ,43   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1017   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1018   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1018   -- entry_class_id
  ,40   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1018   -- entry_class_id
  ,43   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1018   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1019   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1019   -- entry_class_id
  ,40   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1019   -- entry_class_id
  ,41   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1019   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1020   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1020   -- entry_class_id
  ,40   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1020   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1020   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1021   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1021   -- entry_class_id
  ,40   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1021   -- entry_class_id
  ,48   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1022   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1022   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1023   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1023   -- entry_class_id
  ,27   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1024   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1024   -- entry_class_id
  ,40   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1025   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1025   -- entry_class_id
  ,39   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1026   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1026   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1027   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1027   -- entry_class_id
  ,43   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1028   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1028   -- entry_class_id
  ,47   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1029   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1029   -- entry_class_id
  ,44   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1030   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1030   -- entry_class_id
  ,44   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1031   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1031   -- entry_class_id
  ,42   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1032   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1032   -- entry_class_id
  ,70   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1033   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1033   -- entry_class_id
  ,71   -- tag_id
);


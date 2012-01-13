insert into kks_collection_class (
  id
  ,type_code
  ,name
  ,description
  ,consent_type
) VALUES (
	50
  ,'kks.kehitysasialaji.lapsen.hoitosuunnitelma'   -- type_code
  ,'Lapsen hoitosuunnitelma (Salo)'  -- name
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
   500   -- group_id
  ,1   -- sort_order
  ,''  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,50
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
   501   -- group_id
  ,1   -- sort_order
  ,''  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,500   -- parent_id
  ,50
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
   502   -- group_id
  ,1   -- sort_order
  ,'Luvat'  -- name
  ,'Lastensuojelulakiin (13.4.2007/417) ja neuvola-asetukseen (380/2009) perustuen teemme yhteistyötä neuvolan, koulun ja muiden yhteistyötahojen kanssa. Perhepäivähoito: Mikäli lapsesi on vanhin ryhmässä, ja nuoremmille lapsille tarvitaan hoitopaikkaa, siirtyy hän vanhimpana päiväkotiin'  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,500   -- parent_id
  ,50
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
   500   -- entry_class_id
  ,1   -- sort_order
  ,'Osoite ja puhelinnumero'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,501   -- entry_group
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
   501   -- entry_class_id
  ,2   -- sort_order
  ,'Allergiat, lääkehoito ym. huomioitavaa'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,501   -- entry_group
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
   502   -- entry_class_id
  ,3   -- sort_order
  ,'Viimeisin neuvolakäynti'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,501   -- entry_group
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
   503   -- entry_class_id
  ,4   -- sort_order
  ,'Äiti/muu huoltaja'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,501   -- entry_group
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
   504   -- entry_class_id
  ,5   -- sort_order
  ,'Äiti/muu huoltaja, matkapuhelin'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,501   -- entry_group
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
   505   -- entry_class_id
  ,6   -- sort_order
  ,'Äiti/muu huoltaja, työpuhelin'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,501   -- entry_group
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
   506   -- entry_class_id
  ,7   -- sort_order
  ,'Äiti/muu huoltaja, sähköposti'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,501   -- entry_group
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
   507   -- entry_class_id
  ,8   -- sort_order
  ,'Äiti/muu huoltaja'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,501   -- entry_group
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
   508   -- entry_class_id
  ,9   -- sort_order
  ,'Isä/muu huoltaja, matkapuhelin'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,501   -- entry_group
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
   509   -- entry_class_id
  ,10   -- sort_order
  ,'Isä/muu huoltaja, työpuhelin'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,501   -- entry_group
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
   510   -- entry_class_id
  ,11  -- sort_order
  ,'Isä/muu huoltaja, sähköposti'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,501   -- entry_group
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
   511   -- entry_class_id
  ,12   -- sort_order
  ,'18 v. täyttänyt varahakija ja puhelinnumero'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,501   -- entry_group
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
   512   -- entry_class_id
  ,13   -- sort_order
  ,'Päivittäinen hoitoaika'  -- name
  ,'Päivittäinen hoitoaika (ma,ti,ke,to,pe)'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,501   -- entry_group
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
   513   -- entry_class_id
  ,14   -- sort_order
  ,'Huoltajien työajat'  -- name
  ,'Huoltajien päivittäiset työajat'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,501   -- entry_group
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
   514   -- entry_class_id
  ,15   -- sort_order
  ,'Hoidon alkamispäivä'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,501   -- entry_group
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
   515   -- entry_class_id
  ,16   -- sort_order
  ,'Ruokailu'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'MULTI_SELECT'  -- data_type
  ,'aamupala,lounas,välipala,päivällinen,iltapala'  -- value_spaces
  ,501   -- entry_group
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
   516   -- entry_class_id
  ,17   -- sort_order
  ,'Lastani saa valokuvata, esim sanomalehtikuvat'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyllä,ei'  -- value_spaces
  ,502   -- entry_group
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
   517   -- entry_class_id
  ,18   -- sort_order
  ,'Lapseni kuvia saa laittaa varhaiskasvatuksen kotisivuille'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyllä,ei'  -- value_spaces
  ,502   -- entry_group
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
   518   -- entry_class_id
  ,19   -- sort_order
  ,'Lapseni saa osallistua varhaiskasvatuksen retkiin'  -- name
  ,'Teemme retkiä lähiseudulla. Retket tehdään yleisillä kulkuneuvoilla. '  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyllä,ei'  -- value_spaces
  ,502   -- entry_group
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
   519   -- entry_class_id
  ,20   -- sort_order
  ,'Hätätapauksessa lasta saa kuljettaa henkilöautolla'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyllä,ei'  -- value_spaces
  ,502   -- entry_group
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
   520   -- entry_class_id
  ,21   -- sort_order
  ,'Lapseni saa osallistua seurakunnan järjestämiin tilaisuuksiin'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyllä,ei'  -- value_spaces
  ,502   -- entry_group
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   500   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   500   -- entry_class_id
  ,63   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   501   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   501   -- entry_class_id
  ,47   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   502   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   502   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   503   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   503   -- entry_class_id
  ,63   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   504   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   504   -- entry_class_id
  ,63   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   505   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   505   -- entry_class_id
  ,63   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   506   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   506   -- entry_class_id
  ,63   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   507   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   507   -- entry_class_id
  ,63   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   508   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   508   -- entry_class_id
  ,63   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   509   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   509   -- entry_class_id
  ,63   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   510   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   510   -- entry_class_id
  ,63   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   511   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   511   -- entry_class_id
  ,63   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   512   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   512   -- entry_class_id
  ,60   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   513   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   513   -- entry_class_id
  ,60   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   514   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   514   -- entry_class_id
  ,60   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   515   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   515   -- entry_class_id
  ,60   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   516   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   516   -- entry_class_id
  ,64   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   517   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   517   -- entry_class_id
  ,64   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   518   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   518   -- entry_class_id
  ,64   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   519   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   519   -- entry_class_id
  ,64   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   520   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   520   -- entry_class_id
  ,64   -- tag_id
);








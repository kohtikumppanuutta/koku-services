insert into kks_collection_class (
  id
  ,type_code
  ,name
  ,description
  ,consent_type
) VALUES (
	4
  ,'kks.kehitysasialaji.5lk.koulu.terveystarkastus.huoltaja'   -- type_code
  ,'5. Luokan kouluterveystarkastus, Huoltajan lomake'  -- name
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
   50   -- group_id
  ,1   -- sort_order
  ,''  -- name
  ,''  -- description
  ,'koulun asiakasrekisteri'  -- register
  ,3
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
   51   -- group_id
  ,1   -- sort_order
  ,'Koulunkäynti/Kaverit'  -- name
  ,''  -- description
  ,'koulun asiakasrekisteri'  -- register
  ,50   -- parent_id
  ,4
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
   52   -- group_id
  ,2   -- sort_order
  ,'Oppilaan ja hänen perheensä nykytilanne'  -- name
  ,''  -- description
  ,'koulun asiakasrekisteri'  -- register
  ,50   -- parent_id
  ,4
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
   53   -- group_id
  ,3   -- sort_order
  ,'Oppilaan ja perheen elämäntavat ja terveystottumukset'  -- name
  ,''  -- description
  ,'koulun asiakasrekisteri'  -- register
  ,50   -- parent_id
  ,4
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
   54   -- group_id
  ,4   -- sort_order
  ,'Terveydentila'  -- name
  ,''  -- description
  ,'koulun asiakasrekisteri'  -- register
  ,50   -- parent_id
  ,4
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
   55   -- group_id
  ,5   -- sort_order
  ,'Murrosikä'  -- name
  ,''  -- description
  ,'koulun asiakasrekisteri'  -- register
  ,50   -- parent_id
  ,4
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
   56   -- group_id
  ,6   -- sort_order
  ,'Perheen terveys'  -- name
  ,''  -- description
  ,'koulun asiakasrekisteri'  -- register
  ,50   -- parent_id
  ,4
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
   57   -- group_id
  ,7   -- sort_order
  ,'Toiveet'  -- name
  ,''  -- description
  ,'koulun asiakasrekisteri'  -- register
  ,50   -- parent_id
  ,4
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
   151   -- entry_class_id
  ,1   -- sort_order
  ,'Miten koulunkäynti on sujunut huoltajan mielestä? Paljonko oppilas käyttää aikaa kotitehtävien hoitamiseen?'  -- name
  ,'Oppilaan koulunkäynnin sujuminen'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,51   -- entry_group
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
   152   -- entry_class_id
  ,2   -- sort_order
  ,'Miten yhteistyö on sujunut opettajien kanssa? Onko oppilas saanut erityisopetusta, tukiopetusta?'  -- name
  ,'Oppilaan koulunkäynnin sujuminen'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,51   -- entry_group
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
   153   -- entry_class_id
  ,3   -- sort_order
  ,'Onko oppilaalla kavereita koulussa ja lähipiirissä? Kiusataanko oppilasta?'  -- name
  ,'Oppilaan koulunkäynnin sujuminen'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,51   -- entry_group
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
   154   -- entry_class_id
  ,4   -- sort_order
  ,'Miten oppilas kulkee koulumatkat? Koulumatkan turvallisuus?'  -- name
  ,'Oppilaan koulunkäynnin sujuminen'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,51   -- entry_group
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
   155   -- entry_class_id
  ,5   -- sort_order
  ,'Millaisissa asioissa oppilas on hyvä vanhempien mielestä?'  -- name
  ,'Oppilaaan ja perheen nykytilanne'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,52   -- entry_group
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
   156   -- entry_class_id
  ,6   -- sort_order
  ,'Mitkä ovat perheenne vahvuudet? Missä asioissa perheenne on hyvä?'  -- name
  ,'Oppilaaan ja perheen nykytilanne'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,52   -- entry_group
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
   157   -- entry_class_id
  ,7   -- sort_order
  ,'Oppilaan hyvinvointiin ja kouluselviytymiseen vaikuttavat kaikki perheen huolet. Onko perheessä esimerkiksi sairautta,mielenterveysongelmia, päihteiden käyttöä, väkivaltaa, sosiaalisia, taloudellisia tai muita huolia? Haluatteko keskustella joistakin huolista tarkemmin? Missä asioissa ajattelette tarvitsevanne tukea?'  -- name
  ,'Oppilaaan ja perheen nykytilanne'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,52   -- entry_group
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
   158   -- entry_class_id
  ,8   -- sort_order
  ,'Onko oppilas yleensä valikoiva syömisessään?'  -- name
  ,'Oppilaan ruokailutottumukset'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,53   -- entry_group
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
   159   -- entry_class_id
  ,9   -- sort_order
  ,'Onko oppilaalla erityisruokavalio?'  -- name
  ,'Tarkempi selvitys ruokavaliosta tehdään erillisellä lomakkeella tarkastuksen yhteydessä'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,''  -- value_spaces
  ,53   -- entry_group
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
   160   -- entry_class_id
  ,10   -- sort_order
  ,'Syökö oppilas välipaloja? Mitä ne ovat?'  -- name
  ,'Oppilaan ruokailutottumukset'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,53   -- entry_group
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
   161   -- entry_class_id
  ,11   -- sort_order
  ,'Syödäänkö perheessä lämmin ateria?'  -- name
  ,'Oppilaan ruokailutottumukset'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,53   -- entry_group
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
   162   -- entry_class_id
  ,12   -- sort_order
  ,'Oppilaan nukkumaanmenoaika arkisin (klo)?'  -- name
  ,'Oppilaan lepotottumukset'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,53   -- entry_group
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
   163   -- entry_class_id
  ,13   -- sort_order
  ,'Oppilaan nukkumaanmenoaika (klo) / unen pituus tunteina viikonloppuisin (h)'  -- name
  ,'Oppilaan lepotottumukset'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,53   -- entry_group
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
   164   -- entry_class_id
  ,14   -- sort_order
  ,'Häiritseekö jokin asia oppilaan unta?'  -- name
  ,'Oppilaan lepotottumukset'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,53   -- entry_group
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
   165   -- entry_class_id
  ,15   -- sort_order
  ,'Oppilaan ulkoilu keskimäärin (tuntia päivässä)'  -- name
  ,'Oppilaan vapaa-aika ja liikunta'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,53   -- entry_group
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
   166   -- entry_class_id
  ,16   -- sort_order
  ,'Oppilaan tv-katselu  (tuntia päivässä)'  -- name
  ,'Oppilaan vapaa-aika ja liikunta'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,53   -- entry_group
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
   167   -- entry_class_id
  ,17   -- sort_order
  ,'Erilaiset video- /tietokonepelit (tuntia päivässä)'  -- name
  ,'Oppilaan vapaa-aika ja liikunta'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,53   -- entry_group
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
   168   -- entry_class_id
  ,18   -- sort_order
  ,'Oppilaan harrastukset?'  -- name
  ,'Oppilaan vapaa-aika ja liikunta'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,53   -- entry_group
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
   169   -- entry_class_id
  ,19   -- sort_order
  ,'Miten perheessä vietetään yhteistä aikaa?'  -- name
  ,'Oppilaan vapaa-aika ja liikunta'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,53   -- entry_group
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
   170   -- entry_class_id
  ,20   -- sort_order
  ,'Tupakoivatko oppilaan vanhemmat?'  -- name
  ,'Tupakka, alkoholi, päihtet'  -- description
  ,0   -- multi_value
  ,'MULTI_SELECT'  -- data_type
  ,'Äiti polttaa, Isä polttaa'  -- value_spaces
  ,53   -- entry_group
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
   171   -- entry_class_id
  ,21   -- sort_order
  ,'Käyttävätkö oppilaan vanhemmat päihteitä?'  -- name
  ,'Tupakka, alkoholi, päihtet'  -- description
  ,0   -- multi_value
  ,'MULTI_SELECT'  -- data_type
  ,'Äiti käyttää, Isä käyttää'  -- value_spaces
  ,53   -- entry_group
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
   172   -- entry_class_id
  ,22   -- sort_order
  ,'Jos vanhemmat käyttää päihteitä, niin mitä päihteitä ja kuinka paljon?'  -- name
  ,'Tupakka, alkoholi, päihtet'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,53   -- entry_group
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
   173   -- entry_class_id
  ,23   -- sort_order
  ,'Onko oppilas vanhempiensa mielestä terve? Miksi ei?'  -- name
  ,'Oppilaan terveydentila'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,54   -- entry_group
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
   174   -- entry_class_id
  ,24   -- sort_order
  ,'Onko oppilaalla toistuvia oireita? Mitä?'  -- name
  ,'Oppilaan terveydentila'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,54   -- entry_group
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
   175   -- entry_class_id
  ,25   -- sort_order
  ,'Onko oppilaalla jokin pitkäaikaissairaus? Mikä?'  -- name
  ,'Oppilaan terveydentila'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,54   -- entry_group
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
   176   -- entry_class_id
  ,26   -- sort_order
  ,'Onko oppilas ollut viimeaikoina sairaalahoidossa? Mistä syystä?'  -- name
  ,'Oppilaan terveydentila'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,54   -- entry_group
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
   177   -- entry_class_id
  ,27   -- sort_order
  ,'Onko oppilaalla ollut toistuvia lääkärissäkäyntejä viimeaikoina? Mistä syystä?'  -- name
  ,'Oppilaan terveydentila'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,54   -- entry_group
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
   178   -- entry_class_id
  ,28   -- sort_order
  ,'Onko oppilaalla jokin yliherkkyys? Mikä ja minkälaisia oireita se aiheuttaa?'  -- name
  ,'Oppilaan terveydentila'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,54   -- entry_group
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
   179   -- entry_class_id
  ,29   -- sort_order
  ,'Onko oppilas saanut jotakin kuntoutusta? Mitä?'  -- name
  ,'Oppilaan terveydentila'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,54   -- entry_group
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
   180   -- entry_class_id
  ,30   -- sort_order
  ,'Jatkuuko jokin kuntoutus nyt? Mikä?'  -- name
  ,'Oppilaan terveydentila'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,54   -- entry_group
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
   181   -- entry_class_id
  ,31   -- sort_order
  ,'Onko oppilaalla merkkejä alkavasta murrosiästä? Millaisia?'  -- name
  ,'Oppilaan murrosikä'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,55   -- entry_group
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
   182   -- entry_class_id
  ,32   -- sort_order
  ,'Minkä ikäisenä vanhempien murrosikä alkoi, kasvun kiihtyminen, äidin kuukautisten alkamisikä?'  -- name
  ,'Oppilaan murrosikä'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,55   -- entry_group
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
   183   -- entry_class_id
  ,33   -- sort_order
  ,'Oletteko keskustelleet seurusteluun liittyvistä asioista kotona? Ovatko asiat ajankohtaisia?'  -- name
  ,'Oppilaan murrosikä'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,55   -- entry_group
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
   184   -- entry_class_id
  ,34   -- sort_order
  ,'Oletteko keskustelleet tupakkaan ja päihteisiin liittyvistä asioista kotona? Ovatko asiat ajankohtaisia?'  -- name
  ,'Oppilaan murrosikä'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,55   -- entry_group
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
   185   -- entry_class_id
  ,35   -- sort_order
  ,'Onko perheen jäsenillä pitkäaikaista sairautta? Mitä?'  -- name
  ,'Oppilaan perheen terveys'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,56   -- entry_group
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
   186   -- entry_class_id
  ,36   -- sort_order
  ,'Odotuksia ja toivomuksia kouluterveydenhuollolta esimerkiksi elämänkatsomukseen liittyen?'  -- name
  ,'Toiveet tarkastukselle'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,57   -- entry_group
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
   187   -- entry_class_id
  ,37   -- sort_order
  ,'Mihin haluaisitte erityisesti kiinnitettävän huomiota tarkastuksessa'  -- name
  ,'Toiveet tarkastukselle'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,57   -- entry_group
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   151   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   151   -- entry_class_id
  ,66   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   152   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   152   -- entry_class_id
  ,66   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   153   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   153   -- entry_class_id
  ,66   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   154   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   154   -- entry_class_id
  ,66   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   155   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   155   -- entry_class_id
  ,66   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   156   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   156   -- entry_class_id
  ,66   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   157   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   157   -- entry_class_id
  ,40   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   158   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   158   -- entry_class_id
  ,51   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   158   -- entry_class_id
  ,67   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   159   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   159   -- entry_class_id
  ,51   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   159   -- entry_class_id
  ,67   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   160   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   160   -- entry_class_id
  ,51   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   160   -- entry_class_id
  ,67   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   161   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   161   -- entry_class_id
  ,51   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   161   -- entry_class_id
  ,67   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   162   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   162   -- entry_class_id
  ,57   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   163   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   163   -- entry_class_id
  ,57   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   164   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   164   -- entry_class_id
  ,57   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   165   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   165   -- entry_class_id
  ,57   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   166   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   166   -- entry_class_id
  ,57   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   167   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   167   -- entry_class_id
  ,57   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   168   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   168   -- entry_class_id
  ,57   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   169   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   169   -- entry_class_id
  ,57   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   170   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   170   -- entry_class_id
  ,57   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   171   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   171   -- entry_class_id
  ,57   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   172   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   172   -- entry_class_id
  ,57   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   173   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   173   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   174   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   174   -- entry_class_id
  ,41   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   175   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   175   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   176   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   176   -- entry_class_id
  ,41   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   177   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   177   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   178   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   178   -- entry_class_id
  ,41   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   179   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   179   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   180   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   180   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   181   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   181   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   182   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   182   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   183   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   183   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   184   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   184   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   185   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   185   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   186   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   186   -- entry_class_id
  ,17   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   187   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   187   -- entry_class_id
  ,17   -- tag_id
);


insert into kks_collection_class (
  id
  ,type_code
  ,name
  ,description
  ,consent_type
) VALUES (
	130
  ,'kks.kehitysasialaji.lapsen_esiopetussuunnitelma.heinola'   -- type_code
  ,'Lapsen esiopetussuunnitelma'  -- name
  ,'Esiopetus on leikin ja toiminnan avulla tapahtuvaa tavoitteellista toimintaa.<br/> 
	Esiopetussuunnitelman tekeminen on osa perheen ja esiopetuksen välistä kasvatuskumppanuutta. Kasvatuskumppanuus lähtee lapsen tarpeista ja siinä yhdistyvät lapselle kahden tärkeän tahon, vanhempien ja varhaiskasvatuksen kasvattajien tiedot ja kokemukset.<br/>
Esiopetussuunnitelma tehdään yhteistyössä perheen ja esiopetuksesta vastaavan aikuisen kanssa. Tämän avulla sovitaan yhteistyössä perheen kanssa lapsen kasvatustavoitteista ja seurataan lapsen kehitystä esiopetusvuoden aikana. Lähtökohtana on, että suunnitelmat ovat aktiivisessa käytössä ja niiden sisältö on sekä vanhempien että koko esiopetushenkilöstön tiedossa ja arvioitavissa.<br/>
Teemme esiopetuksessa moniammatillista yhteistyötä taataksemme laadukkaan esiopetuksen. Yhteistyökumppaneita ovat mm. neuvola, koulu, psykologit ja terapeutit. Ensisijainen yhteistyökumppani on kuitenkin aina lapsen oma perhe. Esiin tulevat asiat ovat luottamuksellisia ja tietoja voidaan antaa ulkopuolisille vain vanhempien luvalla ja heidän kanssa keskustellen.<br/>Esiopetussuunnitelman koontisivu lähetetään vanhempien suostumuksella lapsen tulevaan kouluun.<br/>
'  -- description
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
   900   -- group_id
  ,1   -- sort_order
  ,'Huoltaja'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,130
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
   901   -- group_id
  ,1   -- sort_order
  ,'Esiopetus'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'employee' -- accountable
  ,130
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
   902   -- group_id
  ,1   -- sort_order
  ,'Päivittäistoiminnot'  -- name
  ,'Esimerkiksi:<br/>
  <ul>
	<li>Ruokailu</li>
	<li>Omista tavaroista huolehtiminen</li> 
	<li>Pukeutuminen ja riisuminen</li>
	<li>Wc-käyttäytyminen, omasta hygieniasta huolehtiminen</li> 
	<li>Päivälepo</li>
	<li>Päivärytmin omaksuminen</li>
	<li>Miten toiminnot sujuvat, mitä huomioitavaa, omatoimisuus toiminnoissa</li>
  </ul>'  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,900   -- parent_id
  ,130
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
   903   -- group_id
  ,2   -- sort_order
  ,'Sosiaaliset taidot ja tunneilmaisu'  -- name
  ,'Esimerkiksi:<br/>
  <ul>
	<li>Suhtautuu aikuisiin luottavaisesti</li>
	<li>Tunteiden nimeäminen ja niiden ilmaisun kontrolloiminen sekä pettymysten sietäminen</li>
	<li>Ratkaisee erimielisyyksiä myönteisin keinoin</li>
	<li>Vuoron odottaminen, sääntöjen noudattaminen</li>
	<li>Vuorottelee välineiden käytössä</li>
	<li>Itsensä arvostaminen</li>
	<li>Hyvät tavat</li>
	<li>Yhdessä leikkimisen taidot; valmius neuvotella ja tehdä kompromisseja sekä solmia ystävyyssuhteita</li>
	<li>Leikin sisältö ja kesto/pitkäjänteisyys, leikkivälineet</li>
	<li>Roolit leikissä ja mielikuvitus</li>
  </ul>'  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,900   -- parent_id
  ,130
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
   904   -- group_id
  ,3   -- sort_order
  ,'Työskentelytaidot'  -- name
  ,'Esimerkiksi:<br/>
  <ul>
	<li>Osallistuminen, yrittäminen ja suhtautuminen uusiin tilanteisiin ja asioihin</li>
	<li>Lapsen käyttäytyminen oppimistilanteissa (sekä yksilö- että ryhmätilanteissa; ohjeiden vastaanottaminen, toimiminen niiden mukaan ja tehtävien loppuun suorittaminen) sekä vapaassa ryhmätilanteessa (noudattaa sääntöjä peleissä ja leikeissä)</li>
	<li>Tarkkaavaisuus (ylläpitäminen, siirtäminen, oman toiminnan ohjaaminen)</li>
	<li>Pystyy itsenäiseen työskentelyyn</li>
  </ul>'  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,900   -- parent_id
  ,130
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
   905   -- group_id
  ,4   -- sort_order
  ,'Kielelliset valmiudet'  -- name
  ,'Esimerkiksi:<br/>
  <ul>
	<li>Kielen/puheen tuottaminen; kertoo ja kuvailee, puheen sujuvuus ja selkeys, sana- ja käsitevarasto ja kielen rakenteet, vertailee asioita</li>
	<li>Kielen/puheen ymmärtäminen; kuunteleminen ja kuullun ymmärtäminen sekä muistaminen, ajan ja paikan käsitteet, päättely</li>
	<li>Kiinnostus lukemisen ja kirjoittamisen oppimiseen (esim. tutustuminen kirjaimiin ja alkuäänteisiin, rytmi, riimit, oman nimen kirjoittaminen)</li>
	<li>Vuorovaikutus</li>
  </ul>'  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,900   -- parent_id
  ,130
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
   906   -- group_id
  ,5   -- sort_order
  ,'Matemaattiset valmiudet, looginen ajattelu ja päättely'  -- name
  ,'Esimerkiksi:<br/>
  <ul>
	<li>Tutustuminen matemaattisiin käsitteisiin (esim.yhtä suuri, enemmän, keskimmäinen)</li>
	<li>Luettelee ja tunnistaa luvut 1-10 sekä ymmärtää kuva-numerovastaavuuden</li>
	<li>Ajan, paikan ja tilan hahmottaminen (esim.viikonpäivät)</li>
	<li>Matemaattisen ajattelun ja ongelmanratkaisukyvyn kehittäminen</li>
  </ul>'  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,900   -- parent_id
  ,130
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
   907   -- group_id
  ,6   -- sort_order
  ,'Motoriikka ja hahmotus'  -- name
  ,'Esimerkiksi:<br/>
  <ul>
	<li>Lapsen ja perheen suhtautuminen liikuntaan</li>
	<li>Oman kehonhallinta ja kehonosien nimeäminen ja tunnistaminen</li>
	<li>Perusliikuntataidot (esim. kävely, juoksu, hiihto, luistelu, pyöräily, uinti)</li> 
	<li>Erilaisten työskentelyvälineiden hallinta (esim. liikuntavälineet, sakset, kynä, napit, nauhat, helmet, ruokailuvälineet)</li> 
	<li>Tilan hallinta ja hahmottaminen (esim. liikkuminen tilassa, rakentelu, palapelit, labyrintit)</li>
	<li>Vakiintunut kätisyys (oikea tai vasen)</li>
  </ul>'  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,900   -- parent_id
  ,130
);

-- Esiopetus

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
   908   -- group_id
  ,1   -- sort_order
  ,'Päivittäistoiminnot'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'employee' -- accountable
  ,901   -- parent_id
  ,130
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
   909   -- group_id
  ,2   -- sort_order
  ,'Sosiaaliset taidot ja tunneilmaisu'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'employee' -- accountable
  ,901   -- parent_id
  ,130
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
   910   -- group_id
  ,3   -- sort_order
  ,'Työskentelytaidot'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'employee' -- accountable
  ,901   -- parent_id
  ,130
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
   911   -- group_id
  ,4   -- sort_order
  ,'Kielelliset valmiudet'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'employee' -- accountable
  ,901   -- parent_id
  ,130
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
   912   -- group_id
  ,5   -- sort_order
  ,'Matemaattiset valmiudet, looginen ajattelu ja päättely'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'employee' -- accountable
  ,901   -- parent_id
  ,130
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
   913   -- group_id
  ,6   -- sort_order
  ,'Motoriikka ja hahmotus'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'employee' -- accountable
  ,901   -- parent_id
  ,130
);

-- Kentät

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
   1250   -- entry_class_id
  ,1   -- sort_order
  ,'Mitkä tämän osa-alueen asiat ovat mielestäsi oleellisia eskarivuonna sinun lapsesi kannalta?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,902   -- entry_group
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
   1251   -- entry_class_id
  ,1   -- sort_order
  ,'Mitkä tämän osa-alueen asiat ovat mielestäsi oleellisia eskarivuonna sinun lapsesi kannalta?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,903   -- entry_group
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
   1252   -- entry_class_id
  ,1   -- sort_order
  ,'Mitkä tämän osa-alueen asiat ovat mielestäsi oleellisia eskarivuonna sinun lapsesi kannalta?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,904   -- entry_group
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
   1253   -- entry_class_id
  ,1   -- sort_order
  ,'Mitkä tämän osa-alueen asiat ovat mielestäsi oleellisia eskarivuonna sinun lapsesi kannalta?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,905  -- entry_group
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
   1254   -- entry_class_id
  ,1   -- sort_order
  ,'Mitkä tämän osa-alueen asiat ovat mielestäsi oleellisia eskarivuonna sinun lapsesi kannalta?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,906   -- entry_group
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
   1255   -- entry_class_id
  ,1   -- sort_order
  ,'Mitkä tämän osa-alueen asiat ovat mielestäsi oleellisia eskarivuonna sinun lapsesi kannalta?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,907   -- entry_group
);

-- Esiopetus

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
   1256   -- entry_class_id
  ,1   -- sort_order
  ,'Eskarin huomioita:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,908   -- entry_group
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
   1257   -- entry_class_id
  ,1   -- sort_order
  ,'Eskarin huomioita:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,909   -- entry_group
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
   1258   -- entry_class_id
  ,1   -- sort_order
  ,'Eskarin huomioita:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,910   -- entry_group
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
   1259   -- entry_class_id
  ,1   -- sort_order
  ,'Eskarin huomioita:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,911   -- entry_group
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
   1260   -- entry_class_id
  ,1   -- sort_order
  ,'Eskarin huomioita:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,912   -- entry_group
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
   1261   -- entry_class_id
  ,1   -- sort_order
  ,'Eskarin huomioita:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,913   -- entry_group
);

-- huoltaja

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1250   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1250   -- entry_class_id
  ,33   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1251   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1251   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1252   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1252   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1253   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1253   -- entry_class_id
  ,25   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1254   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1254   -- entry_class_id
  ,31   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1255   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1255   -- entry_class_id
  ,21   -- tag_id
);


-- Esiopetus


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1256   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1256   -- entry_class_id
  ,33   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1257   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1257   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1258   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1258   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1259   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1259   -- entry_class_id
  ,25   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1260   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1260   -- entry_class_id
  ,31   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1261   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1261   -- entry_class_id
  ,21   -- tag_id
);

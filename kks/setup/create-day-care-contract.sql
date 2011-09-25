insert into kks_collection_class (
  id
  ,type_code
  ,name
  ,description
  ,consent_type
) VALUES (
	3
  ,'kks.kehitysasialaji.sopimus.päiväkotihoito'   -- type_code
  ,'Päiväkotihoidon Hoitosopimus'  -- name
  ,''  -- description
  ,'Päiväkotihoidon hoitosopimus'  -- concent_type
);

insert into kks_group (
  group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,collection_class_id
) VALUES (
   30   -- group_id
  ,1   -- sort_order
  ,''  -- name
  ,''  -- description
  ,'päivähoidon asiakasrekisteri'  -- register
  ,3
);

insert into kks_group (
  group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,collection_class_id
) VALUES (
   31   -- group_id
  ,2   -- sort_order
  ,'Ilta- ja vuorohoitosopimuksessa lisäksi'  -- name
  ,''  -- description
  ,'päivähoidon asiakasrekisteri'  -- register
  ,3
);

insert into kks_group (
  group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,collection_class_id
) VALUES (
   32   -- group_id
  ,3   -- sort_order
  ,'Perhepäivähoidon sopimuksessa lisäksi'  -- name
  ,''  -- description
  ,'päivähoidon asiakasrekisteri'  -- register
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
   33   -- group_id
  ,1   -- sort_order
  ,'Vanhempi'  -- name
  ,''  -- description
  ,'päivähoidon asiakasrekisteri'  -- register
  ,30   -- parent_id
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
   34   -- group_id
  ,2   -- sort_order
  ,'Hoitoaika'  -- name
  ,''  -- description
  ,'päivähoidon asiakasrekisteri'  -- register
  ,30   -- parent_id
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
   35   -- group_id
  ,3   -- sort_order
  ,'Lapsen hyvinvointi'  -- name
  ,''  -- description
  ,'päivähoidon asiakasrekisteri'  -- register
  ,30   -- parent_id
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
   36   -- group_id
  ,4   -- sort_order
  ,'Päiväkodin johtaja'  -- name
  ,''  -- description
  ,'päivähoidon asiakasrekisteri'  -- register
  ,30   -- parent_id
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
   37   -- group_id
  ,5   -- sort_order
  ,'Lapsen sairastuminen'  -- name
  ,'Huoltajat itse järjestävät sairaan lapsen hoidon. Päivähoidon henkilökunnalla on oikeus kirjoittaa todistus lapsen sairastumisesta työnantajalle sairastumispäivän osalta (Kvtes: perhevapaat § 10).'  -- description
  ,'päivähoidon asiakasrekisteri'  -- register
  ,30   -- parent_id
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
   38   -- group_id
  ,6   -- sort_order
  ,'Luvat'  -- name
  ,''  -- description
  ,'päivähoidon asiakasrekisteri'  -- register
  ,30   -- parent_id
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
   39   -- group_id
  ,7   -- sort_order
  ,'Uskontokasvatus'  -- name
  ,''  -- description
  ,'päivähoidon asiakasrekisteri'  -- register
  ,30   -- parent_id
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
   40   -- group_id
  ,8   -- sort_order
  ,'Päivähoitomatkat'  -- name
  ,'Henkilöt, jotka voivat hakea lapseni hoitopaikasta huoltajien lisäksi. Pääsääntöisesti aikuinen hakee lapsen päivähoidosta. Päivähoitohenkilökunnan velvollisuus on varmistaa, että lasta noutavalla henkilöllä on oikeus noutaa lapsi. Mikäli joku muu kuin huoltaja tai alla mainittu varahakija hakee lapsen päivähoidosta, huoltajat ilmoittavat siitä päivähoidon henkilökunnalle kirjallisesti. Erityistapauksissa, joissa huoltajat esittävät lapsen hakijaksi alaikäistä sisarusta tai muuta vastaavaa henkilöä tai toivovat lapsen kulkevan yksin, päivähoitohenkilökunta arvioi yhdessä huoltajien kanssa keskustellen lapsen kotimatkan turvallisuuden ja sopii siitä kirjallisesti'  -- description
  ,'päivähoidon asiakasrekisteri'  -- register
  ,30   -- parent_id
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
   41   -- group_id
  ,1   -- sort_order
  ,'Hoitoaika'  -- name
  ,''  -- description
  ,'päivähoidon asiakasrekisteri'  -- register
  ,31   -- parent_id
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
   42   -- group_id
  ,1   -- sort_order
  ,'Hoitaja'  -- name
  ,''  -- description
  ,'päivähoidon asiakasrekisteri'  -- register
  ,32   -- parent_id
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
   43   -- group_id
  ,2   -- sort_order
  ,'Varahoitopaikka'  -- name
  ,''  -- description
  ,'päivähoidon asiakasrekisteri'  -- register
  ,32   -- parent_id
  ,3
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
   100   -- entry_class_id
  ,1   -- sort_order
  ,'Huoltaja 1) nimi,työtilanne/työpaikka'  -- name
  ,'Lapsen huoltajan työtilanne'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,33   -- entry_group
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
   101   -- entry_class_id
  ,2   -- sort_order
  ,'Huoltajan 1) Puh.kotiin/työpaikalle'  -- name
  ,'Lapsen huoltajan puhelintiedot'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,33   -- entry_group
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
   102   -- entry_class_id
  ,3   -- sort_order
  ,'Huoltaja 1) Haluan käyttää sähköpostia yhtenä viestivälineenä'  -- name
  ,'Lasten henkilökohtaisia asioita ei sovita eikä henkilötietoja käsitellä koskaan sähköpostitse, mutta päiväkodin tai lapsiryhmän yleisiä asioita voidaan tiedottaa sähköpostitse.'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'Kyllä,En'  -- value_spaces
  ,33   -- entry_group
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
   103   -- entry_class_id
  ,4   -- sort_order
  ,'Huoltaja 2) nimi,työtilanne/työpaikka'  -- name
  ,'Lapsen huoltajan työtilanne'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,33   -- entry_group
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
   104   -- entry_class_id
  ,5   -- sort_order
  ,'Huoltajan 2) Puh.kotiin/työpaikalle'  -- name
  ,'Lapsen huoltajan puhelintiedot'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,33   -- entry_group
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
   105   -- entry_class_id
  ,6   -- sort_order
  ,'Huoltaja 2) Haluan käyttää sähköpostia yhtenä viestivälineenä'  -- name
  ,'Lasten henkilökohtaisia asioita ei sovita eikä henkilötietoja käsitellä koskaan sähköpostitse, mutta päiväkodin tai lapsiryhmän yleisiä asioita voidaan tiedottaa sähköpostitse.'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'Kyllä,En'  -- value_spaces
  ,33   -- entry_group
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
   106   -- entry_class_id
  ,7   -- sort_order
  ,'Huoltajuus'  -- name
  ,'Huoltajuuden tyyppi'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'Yksinhuoltaja, Yhteishuoltajuus'  -- value_spaces
  ,33   -- entry_group
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
   107   -- entry_class_id
  ,8   -- sort_order
  ,'Hoitosuhde alkaa'  -- name
  ,'Hoitosuhteen alkamispäivä'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,34   -- entry_group
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
   108   -- entry_class_id
  ,9   -- sort_order
  ,'Hoitoaika päivittäin'  -- name
  ,'(esim. klo 8:00-16:00)'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,34   -- entry_group
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
   109   -- entry_class_id
  ,10   -- sort_order
  ,'Perheelle on kerrottu päivähoitopaikkatakuun ja kesäajan maksuttomuuden periaatteista sekä avoimen varhaiskasvatuksen  palveluista sekä sopimuspäivämahdollisuudesta'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'MULTI_SELECT'  -- data_type
  ,'Kyllä'  -- value_spaces
  ,34   -- entry_group
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
   110   -- entry_class_id
  ,11   -- sort_order
  ,'Sopimuspäivät per kk'  -- name
  ,'Sopimuspäivät varataan etukäteen, päiviä voi vaihtaa ainoastaan työstä tai opiskelusta johtuvista syistä. Varatut sopimuspäivät ovat voimassa 5 kuukautta.'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,34   -- entry_group
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
   111   -- entry_class_id
  ,12   -- sort_order
  ,'Sovitut hoitopäivät'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'MULTI_SELECT'  -- data_type
  ,'ma,ti,ke,to,pe'  -- value_spaces
  ,34   -- entry_group
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
   112   -- entry_class_id
  ,13   -- sort_order
  ,'Sairaudet ja allergiat'  -- name
  ,'(pitkäaikaissairaudet, lääkitykset, ruokavaliot)'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,35   -- entry_group
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
   113   -- entry_class_id
  ,14   -- sort_order
  ,'Muut huomioitavat asiat'  -- name
  ,'(esim. lapsen erityisen tuen tarpeeseen liittyvät asiat, mahdolliset lausunnot, kuntoutuspalvelut tai apuvälineet)'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,35   -- entry_group
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
   114   -- entry_class_id
  ,15   -- sort_order
  ,'Päiväkodin johtaja'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,36   -- entry_group
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
   115   -- entry_class_id
  ,16   -- sort_order
  ,'Johtajan yhteystiedot'  -- name
  ,'(sähköpostiosoite ja puh)'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,36   -- entry_group
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
   116   -- entry_class_id
  ,17   -- sort_order
  ,'Lapsen sairastuessa otetaan ensi sijaisesti yhteyttä:'  -- name
  ,'Lapsen yhteyshenkilö'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,37   -- entry_group
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
   117   -- entry_class_id
  ,18   -- sort_order
  ,'1. Annan / annamme  päivähoidon henkilökunnalle luvan valokuvata / videokuvata lastamme päiväkodissa ja retkillä päivähoidon omaan käyttöön'  -- name
  ,'Luvat'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'saa,ei saa'  -- value_spaces
  ,38   -- entry_group
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
   118   -- entry_class_id
  ,19   -- sort_order
  ,'2. Lapsemme valokuvia ja piirustuksia saa julkaista lapsen Digitaalisessa kasvun kansiossa (tämä ei ole käytössä kaikissa päiväkodeissa)'  -- name
  ,'Luvat'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'saa,ei saa'  -- value_spaces
  ,38   -- entry_group
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
   119   -- entry_class_id
  ,20   -- sort_order
  ,'3. Lapsemme valokuvia ja piirustuksia saa julkaista päivähoidon intranet ja internet-sivuilla. Yksityisyyssuojakäytäntöjen mukaan lapsen nimeä ja valokuvaa ei intranetissa ja internetissä yhdistetä, ellei asiasta ole erikseen sovittu.'  -- name
  ,'Luvat'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'saa,ei saa'  -- value_spaces
  ,38   -- entry_group
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
   120   -- entry_class_id
  ,21   -- sort_order
  ,'4. Annan / annamme luvan päivähoidon ulkopuolisen tiedotusvälineen edustajan  valokuvata / videokuvata lastamme tv- ja radio-ohjelmiin'  -- name
  ,'Luvat'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'saa,ei saa'  -- value_spaces
  ,38   -- entry_group
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
   121   -- entry_class_id
  ,22   -- sort_order
  ,'5. Annan / annamme luvan päivähoidon ulkopuolisen tiedotusvälineen edustajan . valokuvata / videokuvata lastamme artikkeleihin (sanomalehdet, aikakausilehdet verkkojulkaisuineen)'  -- name
  ,'Luvat'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'saa,ei saa'  -- value_spaces
  ,38   -- entry_group
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
   122   -- entry_class_id
  ,23   -- sort_order
  ,'6. Annan / annamme luvan päivähoidon ulkopuolisen tiedotusvälineen edustajan haastatella lastamme lehtiä, tv- ja radio-ohjelmia varten'  -- name
  ,'Luvat'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'saa,ei saa'  -- value_spaces
  ,38   -- entry_group
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
   123   -- entry_class_id
  ,24   -- sort_order
  ,'7. Annan / annamme lapsemme osallistua seuraaviin toimintoihin: TV:n ja kuvatallenteiden (videot, dvd:t)  katsominen'  -- name
  ,'Luvat'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'saa,ei saa'  -- value_spaces
  ,38   -- entry_group
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
   124   -- entry_class_id
  ,25   -- sort_order
  ,'8. Annan / annamme lapsemme osallistua päivähoidossa tehtäviin retkiin'  -- name
  ,'Luvat'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'saa,ei saa'  -- value_spaces
  ,38   -- entry_group
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
   125   -- entry_class_id
  ,26   -- sort_order
  ,'Perheen kanssa on keskusteltu päivähoidon uskontokasvatukseen liittyvät asiat'  -- name
  ,'Uskonkasvatus'  -- description
  ,0   -- multi_value
  ,'MULTI_SELECT'  -- data_type
  ,'Kyllä'  -- value_spaces
  ,39   -- entry_group
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
   126   -- entry_class_id
  ,27   -- sort_order
  ,'Annamme lapsemme osallistua uskontokasvatukseen'  -- name
  ,'Luvat'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'Kyllä,Ei'  -- value_spaces
  ,39   -- entry_group
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
   127   -- entry_class_id
  ,28   -- sort_order
  ,'Uskontokunta'  -- name
  ,'Uskontokunta'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,39   -- entry_group
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
   128   -- entry_class_id
  ,29   -- sort_order
  ,'Varahakija 1'  -- name
  ,'Lapsen varahakija'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,40   -- entry_group
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
   129   -- entry_class_id
  ,30   -- sort_order
  ,'Varahakija 2'  -- name
  ,'Lapsen varahakija'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,40   -- entry_group
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
   130   -- entry_class_id
  ,31   -- sort_order
  ,'Varahakija 3'  -- name
  ,'Lapsen varahakija'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,40   -- entry_group
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
   131   -- entry_class_id
  ,32   -- sort_order
  ,'Varahakija 4'  -- name
  ,'Lapsen varahakija'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,40   -- entry_group
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
   132   -- entry_class_id
  ,33   -- sort_order
  ,'Varahakija 5'  -- name
  ,'Lapsen varahakija'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,40   -- entry_group
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
   133   -- entry_class_id
  ,34   -- sort_order
  ,'1. Vanhemman työvuorot MA-PE'  -- name
  ,'(esim. 06:00-14:00)'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,41   -- entry_group
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
   134   -- entry_class_id
  ,35   -- sort_order
  ,'1. Vanhemman työvuorot LA'  -- name
  ,'(esim. 06:00-14:00)'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,41   -- entry_group
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
   135   -- entry_class_id
  ,36   -- sort_order
  ,'1. Vanhemman työvuorot SU'  -- name
  ,'(esim. 06:00-14:00)'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,41   -- entry_group
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
   136   -- entry_class_id
  ,37   -- sort_order
  ,'2. Vanhemman työvuorot MA-PE'  -- name
  ,'(esim. 06:00-14:00)'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,41   -- entry_group
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
   137   -- entry_class_id
  ,38   -- sort_order
  ,'2. Vanhemman työvuorot LA'  -- name
  ,'(esim. 06:00-14:00)'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,41   -- entry_group
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
   138   -- entry_class_id
  ,39   -- sort_order
  ,'2. Vanhemman työvuorot SU'  -- name
  ,'(esim. 06:00-14:00)'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,41   -- entry_group
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
   139   -- entry_class_id
  ,40   -- sort_order
  ,'Lapsen hoittoaika MA-PE'  -- name
  ,'(esim. 06:00-14:00)'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,41   -- entry_group
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
   140   -- entry_class_id
  ,41   -- sort_order
  ,'Lapsen hoittoaika LA'  -- name
  ,'(esim. 06:00-14:00)'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,41   -- entry_group
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
   141   -- entry_class_id
  ,42   -- sort_order
  ,'Lapsen hoittoaika SU'  -- name
  ,'(esim. 06:00-14:00)'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,41   -- entry_group
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
   142   -- entry_class_id
  ,43   -- sort_order
  ,'Hoidon tarve'  -- name
  ,'Lapsen hoidon tarve'  -- description
  ,0   -- multi_value
  ,'MULTI_SELECT'  -- data_type
  ,'Arki+ilta,Ilta+lauantai,Ilta+lauantai+sunnuntai,yö'  -- value_spaces
  ,41   -- entry_group
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
   143   -- entry_class_id
  ,44   -- sort_order
  ,'Perhepäivähoitaja'  -- name
  ,'Lapsen perhepäivähoitaja'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,42   -- entry_group
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
   144   -- entry_class_id
  ,45   -- sort_order
  ,'Osoite'  -- name
  ,'Lapsen perhepäivähoitajan osoite'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,42   -- entry_group
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
   145   -- entry_class_id
  ,46   -- sort_order
  ,'Puh.'  -- name
  ,'Lapsen perhepäivähoitajan puhelinnumero'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,42   -- entry_group
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
   146   -- entry_class_id
  ,47   -- sort_order
  ,'Päiväkoti'  -- name
  ,'Lapsen varahoitopaikka'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,43   -- entry_group
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
   147   -- entry_class_id
  ,48   -- sort_order
  ,'Osoite'  -- name
  ,'Lapsen varahoitopaikan osoite'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,43   -- entry_group
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
   148   -- entry_class_id
  ,49   -- sort_order
  ,'Puh.'  -- name
  ,'Lapsen varahoitopaikan puhelinnumero'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,43   -- entry_group
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   100   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   100   -- entry_class_id
  ,61   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   101   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   101   -- entry_class_id
  ,61   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   102   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   102   -- entry_class_id
  ,61   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   103   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   103   -- entry_class_id
  ,61   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   104   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   104   -- entry_class_id
  ,61   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   105   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   106   -- entry_class_id
  ,61   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   107   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   107   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   108   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   108   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   109   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   109   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   110   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   110   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   111   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   111   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   112   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   112   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   113   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   113   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   114   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   114   -- entry_class_id
  ,63   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   115   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   115   -- entry_class_id
  ,63   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   116   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   116   -- entry_class_id
  ,60   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   117   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   117   -- entry_class_id
  ,64   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   118   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   118   -- entry_class_id
  ,64   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   119   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   119   -- entry_class_id
  ,64   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   120   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   120   -- entry_class_id
  ,64   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   121   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   121   -- entry_class_id
  ,64   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   122   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   122   -- entry_class_id
  ,64   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   123   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   123   -- entry_class_id
  ,64   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   124   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   124   -- entry_class_id
  ,64   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   125   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   125   -- entry_class_id
  ,60   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   126   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   126   -- entry_class_id
  ,64   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   127   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   127   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   128   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   128   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   129   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   129   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   130   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   130   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   131   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   131   -- entry_class_id
  ,62   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   132   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   132   -- entry_class_id
  ,62   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   133   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   133   -- entry_class_id
  ,62   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   134   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   134   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   135   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   135   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   136   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   136   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   137   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   137   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   138   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   138   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   139   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   139   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   140   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   140   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   141   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   141   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   142   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   142   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   143   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   143   -- entry_class_id
  ,63   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   143   -- entry_class_id
  ,65   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   144   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   144   -- entry_class_id
  ,63   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   144   -- entry_class_id
  ,65   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   145   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   145   -- entry_class_id
  ,63   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   145   -- entry_class_id
  ,65   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   146   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   146   -- entry_class_id
  ,63   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   146   -- entry_class_id
  ,65   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   147   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   147   -- entry_class_id
  ,63   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   147   -- entry_class_id
  ,65   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   148   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   148   -- entry_class_id
  ,63   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   148   -- entry_class_id
  ,65   -- tag_id
);


-- Contains useful queries and commands for KKS data


-- Select collection class entries and ids (change id for different collections)
select kks_entry_class.entry_class_id,kks_entry_class.name from kks_group join kks_entry_class ON kks_group.group_id = kks_entry_class.entry_group where kks_group.collection_class_id = 1;

-- Select group fields for certain collection class (change id for different collections)
select kks_group.name, kks_entry_class.name from kks_group join kks_entry_class ON kks_group.group_id = kks_entry_class.entry_group where kks_group.collection_class_id = 1;

-- Delete collections (run all delete commands)
delete from kks_value;
delete from kks_entry_tags;
delete from kks_entry;
delete from kks_collection;

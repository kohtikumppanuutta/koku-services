
select kks_group.name, kks_entry_class.name from kks_group join kks_entry_class ON kks_group.group_id = kks_entry_class.entry_group where kks_group.collection_class_id = 1;
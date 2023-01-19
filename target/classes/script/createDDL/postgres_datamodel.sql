

Table manufacturer {
  manufacturer_id int [pk]
  name varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
 }
 
Table material_item_type {
  mat_item_type_id int [pk] 
  type_code varchar
  type_desc varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}

Table material_test_type {
  mat_test_type_id int [pk] 
  type_code varchar
  type_desc varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}



Table material_test {
  mat_test_id int [pk, increment] 
  mat_test_type_id int
  mat_item_id int 
  test_desc varchar
  attributes_json varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}
Ref fk_mt_mtt { 
material_test_type.(mat_test_type_id)  - material_test.(mat_test_type_id) 
}
Ref fk_mt_mi { 
  material_test.(mat_item_id) > material_item.(mat_item_id)
}



Table material_item_attribute {
  mat_item_attr_id int [pk, increment] 
  mat_item_id int
  attributes_json varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}
Ref fk_mia_mi { 
  material_item_attribute.(mat_item_id) > material_item.(mat_item_id)
}


Table material_item {
  mat_item_id int [pk, increment]
  mat_item_type_id int
  manufacturer_id int

  mat_item_num varchar
  model_num  varchar
  item_desc varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}
Ref fk_mi_manufacturer { 
   manufacturer.(manufacturer_id) - material_item.(manufacturer_id)
}
Ref fk_mi_mit { 
   material_item_type.(mat_item_type_id) - material_item.(mat_item_type_id)
}


Table user_locuslink {
    user_locuslink_id int [pk, increment]
    user_type_code varchar
    login_name varchar
    first_name varchar
    last_name_bus_name varchar
    active_flag varchar
	  add_by varchar
	  add_ts timestamp
    update_by varchar
  	update_ts timestamp
}

Table asset_test_type {
  asset_test_type_id int [pk] 
  type_code varchar
  type_desc varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}

Table asset_type {
  asset_type_id int [pk] 
  type_code varchar
  type_desc varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}


Table document_type {
  doc_type_id int [pk] 
  doc_type_code varchar
  doc_type_desc varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}


Table universal_catalog {
  ucat_pkId int [pk, increment]
  universal_catalog_id varchar
  asset_type_id int
  asset_number varchar
  asset_name  varchar
  asset_desc varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}

Ref fk_ucat_at { 
   asset_type.(asset_type_id) - universal_catalog.(asset_type_id)
}



Table asset_template {
  asset_template_id int [pk, increment]
  asset_type_id int
  asset_template_desc varchar
  
  unique_attributes_json varchar 
  additional_attributes_json varchar
  
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}

Ref fk_atemplate_at { 
   asset_type.(asset_type_id) - asset_template.(asset_type_id)
}



Table asset_test {
  asset_test_id int [pk, increment] 
  asset_test_type_id int
  ucat_pkId int 
  test_desc varchar
  attributes_json varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}
Ref fk_att_at { 
asset_test_type.(asset_test_type_id)  - asset_test.(asset_test_type_id) 
}
Ref fk_at_ucat { 
  asset_test.(ucat_pkId) > universal_catalog.(ucat_pkId)
}


Table asset_attribute {
  asset_attr_id int [pk, increment] 
  ucat_pkId int
  unique_attributes_json varchar 
  additional_attributes_json varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}
Ref fk_aa_ucat { 
  asset_attribute.(ucat_pkId) > universal_catalog.(ucat_pkId)
}

Table asset_attachment {
  asset_attach_id int [pk, increment] 
  ucat_pkId int
  doc_type_id int 
  attributes_json varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}
Ref fk_attach_ucat { 
  asset_attachment.(ucat_pkId) > universal_catalog.(ucat_pkId)
}
Ref fk_dt_attach { 
    document_type.(doc_type_id)  - asset_attachment.(doc_type_id) 
}



Table manufacturer {
  manufacturer_id int [pk]
  name varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
 }

Table traceability_type {
  traceability_type_id int [pk] 
  traceability_type_code varchar
  traceability_type_desc varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}

Table unique_asset {
  unique_asset_pkId_id int [pk, increment] 
  unique_asset_id varchar
  
  ucat_pkId int
  manufacturer_id int
  traceability_type_id int
  
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}
Ref fk_ua_ucat { 
  unique_asset.(ucat_pkId) > universal_catalog.(ucat_pkId)
}
Ref fk_ua_mfg { 
   manufacturer.(manufacturer_id) - unique_asset.(manufacturer_id)
}
Ref fk_ua_tt { 
   traceability_type.(traceability_type_id) - unique_asset.(traceability_type_id)
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
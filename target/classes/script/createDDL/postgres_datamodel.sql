

Table product_test_type {
  product_test_type_id int [pk] 
  type_code varchar
  type_desc varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}

Table product_type {
  product_type_id int [pk] 
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
  product_type_id int
  product_number varchar
  product_name  varchar
  product_desc varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}

Ref fk_ucat_pt { 
   product_type.(product_type_id) - universal_catalog.(product_type_id)
}



Table product_template {
  product_template_id int [pk, increment]
  product_type_id int
  product_template_desc varchar
  
  unique_attributes_json varchar 
  additional_attributes_json varchar
  
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}

Ref fk_ptemplate_pt { 
   product_type.(product_type_id) - product_template.(product_type_id)
}



Table product_test {
  product_test_id int [pk, increment] 
  product_test_type_id int
  ucat_pkId int 
  test_desc varchar
  attributes_json varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}
Ref fk_ptt_pt { 
product_test_type.(product_test_type_id)  - product_test.(product_test_type_id) 
}
Ref fk_pt_ucat { 
  product_test.(ucat_pkId) > universal_catalog.(ucat_pkId)
}


Table product_attribute {
  product_attr_id int [pk, increment] 
  ucat_pkId int
  unique_attributes_json varchar 
  additional_attributes_json varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}
Ref fk_pa_ucat { 
  product_attribute.(ucat_pkId) > universal_catalog.(ucat_pkId)
}

Table product_attachment {
  product_attach_id int [pk, increment] 
  ucat_pkId int
  doc_type_id int 
  attributes_json varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}
Ref fk_attach_ucat { 
  product_attachment.(ucat_pkId) > universal_catalog.(ucat_pkId)
}
Ref fk_dt_attach { 
    document_type.(doc_type_id)  - product_attachment.(doc_type_id) 
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
  unique_product_pkId_id int [pk, increment] 
  unique_product_id varchar
  
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



Table product_type {
  product_type_pkid int [pk] 
  type_code varchar
  type_desc varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}

Table product_type_gs1 {
  product_type_gs1_pkid int [pk] 
  product_type_pkid int
  gs1_ai int
  gs1_dataTitle varchar
  gs1_dataContent_json varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}
Ref fk_ptgs1_pt { 
  product_type_gs1.( product_type_pkid) > product_type.(product_type_pkid)
}


Table document_type {
  doc_type_pkid int [pk] 
  doc_type_code varchar
  doc_type_desc varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}


Table universal_catalog {
  ucat_pkid int [pk, increment]
  universal_catalog_id varchar
  product_type_pkid int
  product_number varchar
  product_name  varchar
  product_desc varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}
Ref fk_ucat_pt { 
   product_type.(product_type_pkid) - universal_catalog.(product_type_pkid)
}



Table product_template {
  product_template_pkid int [pk, increment]
  ucat_pkid int 
  product_template_desc varchar
  unique_attributes_json varchar 
  additional_attributes_json varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}
Ref fk_ptemplate_pt { 
   universal_catalog.(ucat_pkid) - product_template.(ucat_pkid)
}


Table product_attribute {
  product_attr_pkid int [pk, increment] 
  ucat_pkid int
  
   attributes_json varchar
  //unique_attributes_json varchar 
 // additional_attributes_json varchar
  
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}
Ref fk_pa_ucat { 
  product_attribute.(ucat_pkid) > universal_catalog.(ucat_pkid)
}

Table product_attachment {
  product_attach_pkid int [pk, increment] 
  unique_asset_pkid int 
  doc_type_pkid int 
  attributes_json varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}

Ref fk_dt_attach { 
    document_type.(doc_type_pkid)  - product_attachment.(doc_type_pkid) 
}
Ref fk_attach_ua { 
  product_attachment.(unique_asset_pkid) > unique_asset.(unique_asset_pkid)
}



Table manufacturer {
  manufacturer_pkid int [pk]
  name varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
 }

Table traceability_type {
  traceability_type_pkid int [pk] 
  traceability_type_code varchar
  traceability_type_desc varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}

Table unique_asset {
  unique_asset_pkid int [pk, increment] 
  unique_asset_id varchar
  ucat_pkid int
  manufacturer_pkid int
  traceability_type_pkid int
  customer_pkid int
  trace_code varchar
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}
Ref fk_ua_ucat { 
  unique_asset.(ucat_pkid) > universal_catalog.(ucat_pkid)
}
Ref fk_ua_tt { 
   traceability_type.(traceability_type_pkid) - unique_asset.(traceability_type_pkid)
}
Ref fk_ua_cust { 
  unique_asset.(customer_pkid) > customer.(customer_pkid)
}
Ref kucat_mfg { 
   manufacturer.(manufacturer_pkid) - unique_asset.(manufacturer_pkid)
}


Table asset_published {
    asset_published_pkid int [pk, increment]
    unique_asset_pkid int 
    published_date timestamp
    active_flag varchar
    comment varchar
	  add_by varchar
	  add_ts timestamp
    update_by varchar
  	update_ts timestamp
}
Ref fk_pa_ua { 
   unique_asset.(unique_asset_pkid) - asset_published.(unique_asset_pkid)
}


Table purchase_order {
  po_pkid int [pk, increment] 

  customer_pkid int
  project_pkid int
  
  po_date timestamp
  po_number varchar
  po_desc varchar
   
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}
Ref fk_po_proj { 
  purchase_order.(project_pkid) > project.(project_pkid)
}


Table purchase_order_line {
  po_line_pkid int [pk, increment] 
  po_pkid int
  unique_asset_pkid int
  po_line_number varchar
  po_line_desc varchar
  add_by varchar
  update_by varchar
  update_ts timestamp
}
Ref fk_pol_po { 
  purchase_order_line.(po_pkid) > purchase_order.(po_pkid)
}
Ref fk_po_ua { 
   unique_asset.(unique_asset_pkid) - purchase_order_line.(unique_asset_pkid)
}



Table customer {
    customer_pkid int [pk, increment]
    
    customer_type_code varchar
    customer_name varchar
    
    active_flag varchar
	  add_by varchar
	  add_ts timestamp
    update_by varchar
  	update_ts timestamp
}


Table project {
  project_pkid int [pk, increment] 
  
  customer_pkid int 
  
  project_type_code varchar
  project_name varchar
  
  add_by varchar
  add_ts timestamp
  update_by varchar
  update_ts timestamp
}

Ref fk_project_customer { 
   customer.(customer_pkid) - project.(customer_pkid)
}



Table user_locuslink {
    user_locuslink_pkid int [pk, increment]
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

CREATE TABLE "universal_cat_selected_attributes" (
  "ucat_selected_attributes__pkid" INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  
  "ucat_pkid" int,
  "uid_pal_pkid" int,
   
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);


ALTER TABLE "universal_cat_selected_attributes" ADD CONSTRAINT "fk_ucatsa_ucat" FOREIGN KEY ("ucat_pkid") REFERENCES "universal_catalog" ("ucat_pkid");
ALTER TABLE "universal_cat_selected_attributes" ADD CONSTRAINT "fk_ucasat_upal" FOREIGN KEY ("uid_pal_pkid") REFERENCES "uid_product_attribute_list" ("uid_pal_pkid");



CREATE TABLE "uid_gen_template" (
  "uid_gen_template_pkid" int PRIMARY KEY,
  
  "industry_pkid" int not null,
  "sub_industry_pkid" int not null,  
  "product_type_pkid" int not null,
  "product_sub_type_pkid" int not NULL,   
  
  "uid_template_length" int not null,       
  "uid_gen_formatted_template" varchar not null,
 
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

ALTER TABLE "uid_gen_template" ADD CONSTRAINT "fk_ugt_i" FOREIGN KEY ("industry_pkid") REFERENCES "industry" ("industry_pkid");
ALTER TABLE "uid_gen_template" ADD CONSTRAINT "fk_ugt_si" FOREIGN KEY ("sub_industry_pkid") REFERENCES "sub_industry" ("sub_industry_pkid");
ALTER TABLE "uid_gen_template" ADD CONSTRAINT "fk_ugt_pt" FOREIGN KEY ("product_type_pkid") REFERENCES "product_type" ("product_type_pkid");
ALTER TABLE "uid_gen_template" ADD CONSTRAINT "fk_ugt_pst" FOREIGN KEY ("product_sub_type_pkid") REFERENCES "product_sub_type" ("product_sub_type_pkid");


CREATE TABLE "uid_product_attribute_list" (
  "uid_pal_pkid" int PRIMARY KEY,
  
  "industry_pkid" int not null,
  "sub_industry_pkid" int not null,  
  "product_type_pkid" int not null,
  "product_sub_type_pkid" int NULL,   
  "uid_gen_seq" int not NULL, 
  "uid_pal_name" varchar,
  "uid_pal_attributes_json" varchar,
 
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

ALTER TABLE "uid_product_attribute_list" ADD CONSTRAINT "fk_upal_si" FOREIGN KEY ("sub_industry_pkid") REFERENCES "sub_industry" ("sub_industry_pkid");
ALTER TABLE "uid_product_attribute_list" ADD CONSTRAINT "fk_upal_i" FOREIGN KEY ("industry_pkid") REFERENCES "industry" ("industry_pkid");
ALTER TABLE "uid_product_attribute_list" ADD CONSTRAINT "fk_upal_pt" FOREIGN KEY ("product_type_pkid") REFERENCES "product_type" ("product_type_pkid");



CREATE TABLE "industry" (
  "industry_pkid" int PRIMARY KEY,
  "uid" varchar,
  "industry_code" varchar,
  "industry_desc" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "sub_industry" (
  "sub_industry_pkid" int PRIMARY KEY,
   "industry_pkid" int,
   "uid" varchar,      
  "sub_industry_code" varchar,
  "sub_industry_desc" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "product_type" (
  "product_type_pkid" int PRIMARY KEY,
   "sub_industry_pkid" int,
  "uid" varchar,   
  "type_code" varchar,
  "type_desc" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "product_sub_type" (
  "product_sub_type_pkid" int PRIMARY KEY,
  "product_type_pkid" int,
  "uid" varchar,   
  "sub_type_code" varchar,
  "sub_type_desc" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);


CREATE TABLE "product_type_gs1" (
  "product_type_gs1_pkid" int PRIMARY KEY,
  "product_type_pkid" int,
  "gs1_ai" int,
  "gs1_dataName" varchar,
  "gs1_barcodeLabel" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);


CREATE TABLE "document_type" (
  "doc_type_pkid" int PRIMARY KEY,
  "doc_type_code" varchar,
  "doc_type_desc" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "universal_catalog" (
  "ucat_pkid" INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "universal_catalog_id" varchar,  
--  "uid_pal_pkid" int,  
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);



CREATE TABLE "product_template" (
  "product_template_pkid" INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "ucat_pkid" int,
  "product_template_desc" varchar,
  "unique_attributes_json" varchar,
  "additional_attributes_json" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "product_attribute" (
  "product_attr_pkid" INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "ucat_pkid" int,
   "attributes_json" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "product_attachment" (
  "product_attach_pkid" INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "unique_asset_pkid" int,
  "doc_type_pkid" int,
  "filename_fullpath" varchar,
  "attributes_json" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "manufacturer" (
  "manufacturer_pkid" int PRIMARY KEY,
  "name" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "traceability_type" (
  "traceability_type_pkid" int PRIMARY KEY,
  "traceability_type_code" varchar,
  "traceability_type_desc" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "unique_asset" (
  "unique_asset_pkid" INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "unique_asset_id" varchar,
  "ucat_pkid" int,
  "manufacturer_pkid" int,
  "customer_pkid" int,    
  "traceability_type_pkid" int,
    
  "trace_code" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "asset_published" (
  "asset_published_pkid" INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "unique_asset_pkid" int,
  "published_date" timestamp,
  "active_flag" varchar,
  "comment" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "purchase_order" (
  "po_pkid" INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "customer_pkid" int,
  "project_pkid" int,
  "po_date" timestamp,
  "po_number" varchar,
  "po_desc" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "purchase_order_line" (
  "po_line_pkid" INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "po_pkid" int,
  "unique_asset_pkid" int,
  "po_line_number" varchar,
  "po_line_desc" varchar,
  "add_by" varchar,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "customer" (
  "customer_pkid" INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "customer_type_code" varchar,
  "customer_name" varchar,
  "active_flag" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "project" (
  "project_pkid" INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "customer_pkid" int,
  "project_type_code" varchar,
  "project_name" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "user_locuslink" (
  "user_locuslink_pkid" INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "user_type_code" varchar,
  "login_name" varchar,
  "first_name" varchar,
  "last_name_bus_name" varchar,
  "active_flag" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);




ALTER TABLE "product_type" ADD CONSTRAINT "fk_pt_si" FOREIGN KEY ("sub_industry_pkid") REFERENCES "sub_industry" ("sub_industry_pkid");

ALTER TABLE "sub_industry" ADD CONSTRAINT "fk_si_i" FOREIGN KEY ("industry_pkid") REFERENCES "industry" ("industry_pkid");

ALTER TABLE "product_sub_type" ADD CONSTRAINT "fk_pst_pt" FOREIGN KEY ("product_type_pkid") REFERENCES "product_type" ("product_type_pkid");


ALTER TABLE "product_template" ADD CONSTRAINT "fk_ptemplate_pt" FOREIGN KEY ("ucat_pkid") REFERENCES "universal_catalog" ("ucat_pkid");

ALTER TABLE "product_attribute" ADD CONSTRAINT "fk_pa_ucat" FOREIGN KEY ("ucat_pkid") REFERENCES "universal_catalog" ("ucat_pkid");

ALTER TABLE "product_type_gs1" ADD CONSTRAINT "fk_ptgs1_pt" FOREIGN KEY ("product_type_pkid") REFERENCES "product_type" ("product_type_pkid");




ALTER TABLE "product_attachment" ADD CONSTRAINT "fk_dt_attach" FOREIGN KEY ("doc_type_pkid") REFERENCES "document_type" ("doc_type_pkid");

ALTER TABLE "product_attachment" ADD CONSTRAINT "fk_attach_ua" FOREIGN KEY ("unique_asset_pkid") REFERENCES "unique_asset" ("unique_asset_pkid");

ALTER TABLE "unique_asset" ADD CONSTRAINT "fk_ua_ucat" FOREIGN KEY ("ucat_pkid") REFERENCES "universal_catalog" ("ucat_pkid");

ALTER TABLE "unique_asset" ADD CONSTRAINT "fk_ua_tt" FOREIGN KEY ("traceability_type_pkid") REFERENCES "traceability_type" ("traceability_type_pkid");

ALTER TABLE "unique_asset" ADD CONSTRAINT "fk_ua_cust" FOREIGN KEY ("customer_pkid") REFERENCES "customer" ("customer_pkid");

ALTER TABLE "unique_asset" ADD CONSTRAINT "kucat_mfg" FOREIGN KEY ("manufacturer_pkid") REFERENCES "manufacturer" ("manufacturer_pkid");

ALTER TABLE "asset_published" ADD CONSTRAINT "fk_pa_ua" FOREIGN KEY ("unique_asset_pkid") REFERENCES "unique_asset" ("unique_asset_pkid");

ALTER TABLE "purchase_order" ADD CONSTRAINT "fk_po_proj" FOREIGN KEY ("project_pkid") REFERENCES "project" ("project_pkid");

ALTER TABLE "purchase_order_line" ADD CONSTRAINT "fk_pol_po" FOREIGN KEY ("po_pkid") REFERENCES "purchase_order" ("po_pkid");

ALTER TABLE "purchase_order_line" ADD CONSTRAINT "fk_po_ua" FOREIGN KEY ("unique_asset_pkid") REFERENCES "unique_asset" ("unique_asset_pkid");

ALTER TABLE "project" ADD CONSTRAINT "fk_project_customer" FOREIGN KEY ("customer_pkid") REFERENCES "customer" ("customer_pkid");

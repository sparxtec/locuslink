CREATE TABLE "product_type" (
  "product_type_pkid" int PRIMARY KEY,
  "type_code" varchar,
  "type_desc" varchar,
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
  "product_type_pkid" int,
  "product_number" varchar,
  "product_name" varchar,
  "product_desc" varchar,
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
  "unique_attributes_json" varchar,
  "additional_attributes_json" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "product_attachment" (
  "product_attach_pkid" INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "unique_asset_pkid" int,
  "doc_type_pkid" int,
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
  "traceability_type_pkid" int,
  "customer_pkid" int,
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

ALTER TABLE "universal_catalog" ADD CONSTRAINT "fk_ucat_pt" FOREIGN KEY ("product_type_pkid") REFERENCES "product_type" ("product_type_pkid");

ALTER TABLE "product_template" ADD CONSTRAINT "fk_ptemplate_pt" FOREIGN KEY ("ucat_pkid") REFERENCES "universal_catalog" ("ucat_pkid");

ALTER TABLE "product_attribute" ADD CONSTRAINT "fk_pa_ucat" FOREIGN KEY ("ucat_pkid") REFERENCES "universal_catalog" ("ucat_pkid");

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

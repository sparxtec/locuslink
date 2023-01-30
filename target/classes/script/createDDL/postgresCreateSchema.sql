CREATE TABLE "product_test_type" (
  "product_test_type_id" int PRIMARY KEY,
  "type_code" varchar,
  "type_desc" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "product_type" (
  "product_type_id" int PRIMARY KEY,
  "type_code" varchar,
  "type_desc" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "document_type" (
  "doc_type_id" int PRIMARY KEY,
  "doc_type_code" varchar,
  "doc_type_desc" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "universal_catalog" (
  "ucat_pkId" INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "universal_catalog_id" varchar,
  "product_type_id" int,
  "product_number" varchar,
  "product_name" varchar,
  "product_desc" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "product_template" (
  "product_template_id" INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "product_type_id" int,
  "product_template_desc" varchar,
  "unique_attributes_json" varchar,
  "additional_attributes_json" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "product_test" (
  "product_test_id" INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "product_test_type_id" int,
  "ucat_pkId" int,
  "test_desc" varchar,
  "attributes_json" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "product_attribute" (
  "product_attr_id" INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "ucat_pkId" int,
  "unique_attributes_json" varchar,
  "additional_attributes_json" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "product_attachment" (
  "product_attach_id" INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "ucat_pkId" int,
  "doc_type_id" int,
  "attributes_json" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "manufacturer" (
  "manufacturer_id" int PRIMARY KEY,
  "name" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "traceability_type" (
  "traceability_type_id" int PRIMARY KEY,
  "traceability_type_code" varchar,
  "traceability_type_desc" varchar,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "unique_asset" (
  "unique_product_pkId_id" INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "unique_product_id" varchar,
  "ucat_pkId" int,
  "manufacturer_id" int,
  "traceability_type_id" int,
  "add_by" varchar,
  "add_ts" timestamp,
  "update_by" varchar,
  "update_ts" timestamp
);

CREATE TABLE "user_locuslink" (
  "user_locuslink_id" INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
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

ALTER TABLE "universal_catalog" ADD CONSTRAINT "fk_ucat_pt" FOREIGN KEY ("product_type_id") REFERENCES "product_type" ("product_type_id");

ALTER TABLE "product_template" ADD CONSTRAINT "fk_ptemplate_pt" FOREIGN KEY ("product_type_id") REFERENCES "product_type" ("product_type_id");

ALTER TABLE "product_test" ADD CONSTRAINT "fk_ptt_pt" FOREIGN KEY ("product_test_type_id") REFERENCES "product_test_type" ("product_test_type_id");

ALTER TABLE "product_test" ADD CONSTRAINT "fk_pt_ucat" FOREIGN KEY ("ucat_pkId") REFERENCES "universal_catalog" ("ucat_pkId");

ALTER TABLE "product_attribute" ADD CONSTRAINT "fk_pa_ucat" FOREIGN KEY ("ucat_pkId") REFERENCES "universal_catalog" ("ucat_pkId");

ALTER TABLE "product_attachment" ADD CONSTRAINT "fk_attach_ucat" FOREIGN KEY ("ucat_pkId") REFERENCES "universal_catalog" ("ucat_pkId");

ALTER TABLE "product_attachment" ADD CONSTRAINT "fk_dt_attach" FOREIGN KEY ("doc_type_id") REFERENCES "document_type" ("doc_type_id");

ALTER TABLE "unique_asset" ADD CONSTRAINT "fk_ua_ucat" FOREIGN KEY ("ucat_pkId") REFERENCES "universal_catalog" ("ucat_pkId");

ALTER TABLE "unique_asset" ADD CONSTRAINT "fk_ua_mfg" FOREIGN KEY ("manufacturer_id") REFERENCES "manufacturer" ("manufacturer_id");

ALTER TABLE "unique_asset" ADD CONSTRAINT "fk_ua_tt" FOREIGN KEY ("traceability_type_id") REFERENCES "traceability_type" ("traceability_type_id");


######################## IDENTITY Tables  WORKS  2-8-2022  ################################################

#  "SERIAL" generates a sequence, and the settings for the table to use it.
# This is apostgress shortcut MACRO

drop table item;
drop table item_type;
drop table manufacturer;
drop table user_locuslink;

CREATE TABLE manufacturer (
	manufacturer_id  INT not null, 			
	name VARCHAR(120) NOT NULL,			
	add_by  VARCHAR(20) NOT NULL,
	add_ts TIMESTAMP NOT NULL,		
    update_by  VARCHAR(20) NOT NULL,
	update_ts TIMESTAMP,
	PRIMARY KEY(manufacturer_id)
);
		
CREATE TABLE item_type (
	item_type_id INT NOT NULL, 	
	item_type_code VARCHAR(8) NOT NULL,
	type_desc VARCHAR(120) NOT NULL,	
	add_by  VARCHAR(20) NOT NULL,
	add_ts TIMESTAMP NOT NULL,		
    update_by  VARCHAR(20) NOT NULL,
	update_ts TIMESTAMP,
	PRIMARY KEY(item_type_id)
);

CREATE TABLE item(
   item_id INT GENERATED ALWAYS AS IDENTITY,   
	manufacturer_id INT NOT NULL, 
	item_attr_id  INT NOT NULL, 				
	item_type_Id INT NOT NULL, 	
	item_num VARCHAR(10) NOT NULL,		
	model_num  VARCHAR(60) NOT NULL,				
	item_desc VARCHAR(120) NOT NULL,				
	add_by VARCHAR(20) NOT NULL,
	add_ts TIMESTAMP NOT NULL,		
    update_by VARCHAR(20) NOT NULL,
	update_ts TIMESTAMP,
	PRIMARY KEY(item_id),
	CONSTRAINT fk_item_manufacturer
      FOREIGN KEY(manufacturer_id) 
	     REFERENCES manufacturer(manufacturer_id),
	 CONSTRAINT item_type_Id
      FOREIGN KEY(item_type_Id) 
	     REFERENCES item_type(item_type_Id)
);

CREATE TABLE user_locuslink(
    user_locuslink_id INT GENERATED ALWAYS AS IDENTITY, 
    user_type_code VARCHAR(8) NOT NULL,    	
    login_name VARCHAR(20) NOT NULL,  
    first_name VARCHAR(60),
    last_name_bus_name VARCHAR(60),     
    active_flag VARCHAR(1) NOT NULL,         
	add_by VARCHAR(20) NOT NULL,
	add_ts TIMESTAMP NOT NULL,		
    update_by VARCHAR(20) NOT NULL,
	update_ts TIMESTAMP,
	PRIMARY KEY(user_locuslink_id)
);


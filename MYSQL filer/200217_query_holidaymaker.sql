

############################################################################
# VARs
SET @LOOP_INT = 0;

SET @max_customers_per_room = 4;

SET @max_customers = ( SELECT max(id) FROM customers );
SET @max_rooms = ( SELECT max(id) FROM rooms );
SET @max_cities = ( SELECT MAX( id ) FROM cities );
SET @max_travelers = ( SELECT MAX( id ) FROM travelers );


############################################################################  
#@MAX_AMOUNT_OF_CITIES := ( SELECT MAX( cities.id ) FROM cities; )
# CUSTOMERS: create names

/*ALTER TABLE customers
	AUTO_INCREMENT = 1;

REPLACE customers() VALUES( 1, "Faris");
REPLACE customers() VALUES( 2, "Erik");
REPLACE customers() VALUES( 3, "Nils");
REPLACE customers() VALUES( 4, "Victor");
REPLACE customers() VALUES( 5, "Patrik");
REPLACE customers() VALUES( 6, "Jim");
REPLACE customers() VALUES( 7, "Tobbe");
REPLACE customers() VALUES( 8, "Mantas");
REPLACE customers() VALUES( 9, "Shala");
REPLACE customers() VALUES( 10, "Matthias");
REPLACE customers() VALUES( 11, "Helena");
REPLACE customers() VALUES( 12, "Sebbe");
REPLACE customers() VALUES( 13, "Tobias");
REPLACE customers() VALUES( 14, "Alberts");
REPLACE customers() VALUES( 15, "Sandra");
REPLACE customers() VALUES( 16, "Anna");
REPLACE customers() VALUES( 17, "Lisa");
REPLACE customers() VALUES( 18, "Johan W");
REPLACE customers() VALUES( 19, "Benjamin");
REPLACE customers() VALUES( 20, "Ammar");
REPLACE customers() VALUES( 21, "Aling");
REPLACE customers() VALUES( 22, "Soue");
REPLACE customers() VALUES( 23, "Chang");
REPLACE customers() VALUES( 24, "Arnold");
REPLACE customers() VALUES( 25, "Sture");
REPLACE customers() VALUES( 26, "Hamid");
REPLACE customers() VALUES( 27, "Shoa");
REPLACE customers() VALUES( 28, "Annie");
REPLACE customers() VALUES( 29, "Stao");
REPLACE customers() VALUES( 30, "Kurre");
REPLACE customers() VALUES( 31, "Ammar");
*/

############################################################################
# CITIES: ADD/Change places and positions

/*
REPLACE cities() VALUES( 1, "Marrakech, Marocko", POINT( 31.6258257, -7.9891608 ));
REPLACE cities() VALUES( 2, "Petra, Jordan", POINT( 30.3273975, 35.4464006 ));
REPLACE cities() VALUES( 3, "Sousse, Tunis", POINT( 35.82267, 10.631523 ));
REPLACE cities() VALUES( 4, "Sharia, UAE", POINT( 25.346504, 55.432464 ));
REPLACE cities() VALUES( 5, "Soca, Slovenia", POINT( 46.089446, -7.9891608 ));
REPLACE cities() VALUES( 6, "Lauterbrunnen, Schweiz", POINT( 46.5939, 7.9078 ));
REPLACE cities() VALUES( 7, "Villach, Austria", POINT( 46.61148, 13.860039 ));
REPLACE cities() VALUES( 8, "Leipzig, Germany", POINT( 51.349966, 12.352579 ));
REPLACE cities() VALUES( 9, "Tokyo, Japan", POINT( 40.6943, 139.7514 ));
REPLACE cities() VALUES( 10, "New York, United States", POINT( 46.089446, -73.9249 ));
REPLACE cities() VALUES( 11, "Mexico City, Mexico", POINT( 19.4424, -99.1310 ));
REPLACE cities() VALUES( 12, "Mumbai, India", POINT( 19.0170, 72.8570 ));
REPLACE cities() VALUES( 13, "São Paulo, Brazil", POINT( -23.5587, -46.6250	 ));
*/


############################################################################
# ROOMS: auto_increment from 1
# ROOMS: Create random room size


ALTER TABLE rooms
	AUTO_INCREMENT = 1;
/*
INSERT INTO rooms( city, room_size, booking_start, booking_end, max_amount_of_customer, facilities_restaurant,
							facilities_pool, facilities_evening_entertainment, facilities_children_club, additional_services_board_half,
							additional_services_board_full, additional_services_extra_bed, price_per_night, rating,
							distance_to_city, distance_to_beach, availability)
	VALUES(
		FLOOR( 1 + RAND() * @max_cities),
		FLOOR( 14 + RAND() * 50 ),
		NOW(),
		ADDDATE( NOW(), FLOOR(1 + RAND() * 22) ),
		FLOOR( 2 + RAND() * @max_customers_per_room ),
		FLOOR( 0 + RAND() * 2 ),
		FLOOR( 0 + RAND() * 2 ),
		FLOOR( 0 + RAND() * 2 ),
		FLOOR( 0 + RAND() * 2 ),
		FLOOR( 0 + RAND() * 2 ),
		FLOOR( 0 + RAND() * 2 ),
		FLOOR( 0 + RAND() * 2 ),
		( 99 + RAND() * 900 ),
		FLOOR( 1 + RAND() * 10 ),
		FLOOR( 1 + RAND() * 30 ),
		FLOOR( 1 + RAND() * 30 ),
		1);
		*/

############################################################################
# ROOMS: change to available depending on date_end

/*
UPDATE rooms
	SET availability = 0
	WHERE booking_end >= NOW();

UPDATE rooms
	SET availability = 1
	WHERE booking_end <= NOW();
	*/
	
############################################################################
# ROOMS: give max amount of customers based on room size

/*
UPDATE rooms
	SET max_amount_of_customer = 2
	WHERE room_size <= 20;

UPDATE rooms
	SET max_amount_of_customer = 4
	WHERE room_size <= 40
	AND room_size > 20;
	
UPDATE rooms
	SET max_amount_of_customer = 6
	WHERE room_size > 40;
*/

############################################################################
# CUSTOMERS: give rooms to customers

	/*
UPDATE customers
	SET room = FLOOR( 1 + RAND() * @max_rooms ) AS random_room
	WHERE id = FLOOR( 1 + RAND() * @max_customers )
	AND rooms.id AS current_room = random_room;
	AND current_room.max_amount_of_customer <= MAX( max_amount_of_customer );
*/

############################################################################
# CUSTOMERS: Delete
/*delete from customers
	WHERE customer_name = "erik";*/
	
#SELECT * FROM rooms WHERE facilities_restaurant = 1;


ALTER TABLE travelers
	AUTO_INCREMENT = 1;
	
/*INSERT INTO travelers( person01, person02, person03, person04, person05, person06, person07, person08, 
							person09, person10 )
	VALUES(
		FLOOR( 0 + RAND() * @max_persons),
		FLOOR( 0 + RAND() * @max_persons),
		FLOOR( 0 + RAND() * @max_persons),
		FLOOR( 0 + RAND() * @max_persons),
		FLOOR( 0 + RAND() * @max_persons),
		FLOOR( 0 + RAND() * @max_persons),
		FLOOR( 0 + RAND() * @max_persons),
		FLOOR( 0 + RAND() * @max_persons),
		FLOOR( 0 + RAND() * @max_persons),
		FLOOR( 0 + RAND() * @max_persons));*/




############################################################################
# TRAVEKLERS: Delete

#UPDATE customers SET room = 4 WHERE id = 1;

#UPDATE customers SET room = null WHERE id = 1;


############################################################################
# ROOMS: update date

/*
UPDATE rooms
	SET booking_start = NOW()
	WHERE id = 1;
	
UPDATE rooms
	SET booking_end = '2014-03-16'
	WHERE id = 1;
	
SELECT *
	FROM rooms;*/

























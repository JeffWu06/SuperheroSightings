use superherosightings;

insert into superpower (powerdescription)
values ('Telekinesis'), ('Enhanced physical abilities'), ('Accelerated healing'), ('Expert tactician'), ('Scientific genius');

insert into superhuman (alterego, description, villain)
values ('Captain America', 'American super soldier, leader of the Avengers', false),
('Black Widow', 'Former female KGB agent, now member of Avengers', false),
('Dr. Arnin Zola', 'Genius biochemist who''s consciousness exists within a supercomputer', true),
('Professor X', 'Wheelchair bound professor with mutation affording telekinetic powers', false),
('Wolverine', 'Burly, unpleasant man with retractable claws', false),
('Deadpool', 'Sarcastic, masked mercenary in red and black suit wielding dual katanas', false),
('Psylocke', 'Powerful mutant distinguishable by her purple hair', true);

insert into superhumansuperpower(SuperhumanID, SuperpowerID)
values (1, 2), (1, 4), (2, 4), (3, 5), (4, 1), (5, 3), (5, 4), (6, 3), (6, 4), (7, 1), (7, 4);

insert into location(locationname, locationdescription, street, city, state, zip, country, latitude, longitude)
values ('Avengers HQ', 'Former Stark Industries facility in upstate NY', '318 Cooper Cir N', 'Peachtree City', 'GA', '30269', 'US', 33.345736, -84.55588),
('Swiss Alps facility', 'Built in the mountains of the Swiss Alps', 'Schulgassli 6', 'Grindelwald', '', 'J2FV+9F', 'CH', 46.623396, 8.043725),
('Charles Xavior School for the Gifted', 'School run by Dr. Xavior for high-achieving mutant children', '1407 Graymalkin Lane', 'North Salem', 'NY', '10560', 'US', 41.336528, -73.597721),
('Statue of Liberty', 'Actual statue on Liberty Island in NYC', 'Liberty Island', 'New York', 'NY', '10004', 'US', 40.689167, -74.044444),
('Hatley Castle', 'Alternative School for the Gifted exterior facade', '2005 Sooke Rd', 'Victoria', 'BC', 'Y9B 5Y2', 'CA', 48.434456, -123.472711),
('Tower City Center', 'Shopping mall in DC area', '230 W Huron Rd', 'Cleveland', 'OH', '44113', 'US', 41.496818, -81.693392),
('Starbucks', 'Starbucks on Robertson and Beverly in WeHo', '164 N Robertson Blvd', 'West Hollywood', 'CA', '90048', 'US', 34.077088, -118.383593);

insert into organization (organizationname, orgdescription, phone, email, locationid, villain)
values ('Avengers', 'Earth''s mightiest heroes protecting earth from domestic and extraterrestrial threats', '18005551234', 'info@avengers.com', 1, false),
('Hydra', 'Former Nazi-backed organization that believes imposition of a fascist regime is needed to protect citizens of Earth from themselves', '', 'contactus@hydra.com', 2, true),
('X-Men', 'Organization to further peaceful coexistence of humans and mutants', '', 'charles@xaviorschoolforyoungsters.org', 3, false),
('Brotherhood of Mutants', 'Adversaries of the X-men; believe in mutant superiority', '', 'erik@bom.com', 4, true),
('X-Force', 'Group of mutant mercenaries', '', 'weasel@sistermargaretschool.com', 5, false);

insert into superhumanorganization (superhumanid, organizationid)
values (1, 1), (2, 1), (3, 2), (4, 3), (5, 3), (5, 5), (6, 5), (7, 4);

insert into sighting (sightingdate, locationid)
values ('2014-04-04', 6), ('2000-07-14', 4), ('2018-05-18', 5), ('2016-05-27', 7), ('2014-04-06', 1);

insert into superhumansighting (superhumanid, sightingid)
values (1, 1), (2, 1), (5, 2), (6, 3), (7, 4), (1, 5);
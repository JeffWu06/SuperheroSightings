use superherosightings;
select h.AlterEgo, s.SightingDate, l.LocationName
from SuperhumanSighting ss
inner join Superhuman h on ss.SuperhumanID = h.SuperhumanID
inner join Sighting s on ss.SightingID = s.SightingID
inner join Location l on s.LocationID = l.LocationID
where l.LocationID = 6
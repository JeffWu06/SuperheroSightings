use superherosightings;
select h.AlterEgo, l.LocationName
from Location l
inner join Sighting s on l.LocationID = s.LocationID
inner join SuperhumanSighting ss on s.SightingID = ss.SightingID
inner join Superhuman h on ss.SuperhumanID = h.SuperhumanID
where s.SightingDate = '2014-04-04'
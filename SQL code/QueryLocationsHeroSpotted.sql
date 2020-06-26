use superherosightings;
select l.LocationName, s.SightingDate
from Location l
inner join Sighting s on l.LocationID = s.LocationID
inner join SuperhumanSighting ss on s.SightingID = ss.SightingID
inner join Superhuman h on ss.SuperhumanID = h.SuperhumanID
where h.AlterEgo = 'Captain America'
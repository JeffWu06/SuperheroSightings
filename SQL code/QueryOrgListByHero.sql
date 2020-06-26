use superherosightings;
select o.OrganizationName
from Organization o
inner join SuperhumanOrganization so on o.OrganizationID = so.OrganizationID
inner join Superhuman h on so.SuperhumanID = h.SuperhumanID
where h.AlterEgo = 'Wolverine'
use superherosightings;
select h.AlterEgo
from Superhuman h
inner join SuperhumanOrganization so on h.SuperhumanID = so.SuperhumanID
inner join Organization o on so.OrganizationID = o.OrganizationID
where o.OrganizationName = 'Brotherhood of Mutants'
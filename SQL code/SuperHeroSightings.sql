drop database if exists SuperHeroSightings;
create database SuperHeroSightings;
use SuperHeroSightings;

create table if not exists Superpower
	(
    SuperpowerID int not null auto_increment,
    PowerDescription varchar(30) not null,
    primary key(SuperpowerID)
    )ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;
    
create table if not exists Superhuman
	(
    SuperhumanID int not null auto_increment,
    AlterEgo varchar(30) not null,
    Description varchar(90) not null,
    Villain boolean not null,
    primary key(SuperhumanID)
    )ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;
    
create table if not exists SuperhumanSuperpower
	(
    SuperhumanID int not null,
    SuperpowerID int not null,
    primary key(SuperhumanID, SuperpowerID)
    );
    
alter table SuperhumanSuperpower
add constraint fk_SuperhumanSuperpower_Superhuman foreign key(SuperhumanID) references Superhuman(SuperhumanID)
on delete no action, 
add constraint fk_SuperhumanSuperpower_Superpower foreign key(SuperpowerID) references Superpower(SuperpowerID)
on delete no action;
    
create table if not exists Location
	(
    LocationID bigint not null auto_increment,
    LocationName varchar(40) not null,
    LocationDescription varchar(90) not null,
    Street varchar(52) not null,
    City varchar(46) not null,
    State varchar(2) null,
    Zip varchar(10) not null,
    Country varchar(2) not null,
    Latitude decimal(8, 6) not null,
    Longitude decimal(9, 6) not null,
    primary key(LocationID)
    )ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;
    
create table if not exists Organization
	(
    OrganizationID int not null auto_increment,
    OrganizationName varchar(30) not null,
    OrgDescription varchar(140) not null,
    Phone varchar(15) null,
    Email varchar(40) null,
    LocationID bigint not null,
    Villain boolean not null,
    primary key(OrganizationID)
    )ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;
    
alter table Organization
add constraint fk_Organization_Location foreign key(LocationID) references Location(LocationID)
on delete no action;

/*Begin add Picture functionality.*/
create table if not exists Picture
	(
    PictureId bigint not null,
    Title varchar(40) not null,
    Filename varchar(70) not null,
    primary key(PictureId)
    );
/* End add picture functionality.*/

create table if not exists Sighting
	(
    SightingID bigint not null auto_increment,
    SightingDate date not null,
    LocationID bigint not null,
    PictureId bigint null, /*To link picture*/
    primary key(SightingID)
    )ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;
    
alter table Sighting
add constraint fk_Sighting_Location foreign key(LocationID) references Location(LocationID)
on delete no action;

create table if not exists SuperhumanSighting
	(
    SuperhumanID int not null,
    SightingID bigint not null,
    primary key(SuperhumanID, SightingID)
    );
    
alter table SuperhumanSighting
add constraint fk_SuperhumanSighting_Superhuman foreign key(SuperhumanID) references Superhuman(SuperhumanID)
on delete no action, 
add constraint fk_SuperhumanSighting_Sighting foreign key(SightingID) references Sighting(SightingID)
on delete no action;

create table if not exists SuperhumanOrganization
	(
    SuperhumanID int not null,
    OrganizationID int not null,
    primary key(SuperhumanID, OrganizationID)
    );

alter table SuperhumanOrganization
add constraint fk_SuperhumanOrganization_Superhuman foreign key(SuperhumanID) references Superhuman(SuperhumanID)
on delete no action,
add constraint fk_SuperhumanOrganization_Organization foreign key(OrganizationID) references Organization(OrganizationID)
on delete no action;
    
    

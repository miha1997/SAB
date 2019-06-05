
CREATE TABLE [Artikal]
( 
	[IdArtikal]          integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[Naziv]              varchar(100)  NULL ,
	[Cena]               decimal(3)  NULL ,
	[NaStanju]           integer  NULL ,
	[IdProdavnica]       integer  NOT NULL ,
	CONSTRAINT [XPKArtikal] PRIMARY KEY  CLUSTERED ([IdArtikal] ASC)
)
go

CREATE TABLE [Grad]
( 
	[IdGrad]             integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[Naziv]              varchar(100)  NULL ,
	CONSTRAINT [XPKGrad] PRIMARY KEY  CLUSTERED ([IdGrad] ASC)
)
go

CREATE TABLE [Kupac]
( 
	[IdKupac]            integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[ImePrezime]         varchar(100)  NULL ,
	[IdGrad]             integer  NOT NULL ,
	CONSTRAINT [XPKKupac] PRIMARY KEY  CLUSTERED ([IdKupac] ASC)
)
go

CREATE TABLE [NaplataKupac]
( 
	[IdRacun]            integer  NOT NULL ,
	[IdNarudzbina]       integer  NOT NULL ,
	[Datum]              datetime  NULL ,
	CONSTRAINT [XPKNaplataKupac] PRIMARY KEY  CLUSTERED ([IdRacun] ASC,[IdNarudzbina] ASC)
)
go

CREATE TABLE [NaplataProdavnica]
( 
	[IdProdavnica]       integer  NOT NULL ,
	[IdNarudzbina]       integer  NOT NULL ,
	[Datum]              datetime  NULL ,
	CONSTRAINT [XPKNaplataProdavnica] PRIMARY KEY  CLUSTERED ([IdProdavnica] ASC,[IdNarudzbina] ASC)
)
go

CREATE TABLE [Narudzbina]
( 
	[IdNarudzbina]       integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[Status]             varchar(100)  NULL ,
	[IdKupac]            integer  NOT NULL ,
	[Suma]               decimal(3)  NULL ,
	[DatumSlanja]        datetime  NULL ,
	[DatumStizanja]      datetime  NULL ,
	[TrenutniGrad]       integer  NULL ,
	[Progres]            integer  NULL ,
	CONSTRAINT [XPKNarudzbina] PRIMARY KEY  CLUSTERED ([IdNarudzbina] ASC)
)
go

CREATE TABLE [Prodavnica]
( 
	[IdProdavnica]       integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[Naziv]              varchar(100)  NULL ,
	[IdGrad]             integer  NOT NULL ,
	[Procenat]           decimal(3)  NULL ,
	CONSTRAINT [XPKProdavnica] PRIMARY KEY  CLUSTERED ([IdProdavnica] ASC)
)
go

CREATE TABLE [Put]
( 
	[IdGradDo]           integer  NOT NULL ,
	[IdGradOd]           integer  NOT NULL ,
	[Cena]               integer  NULL ,
	CONSTRAINT [XPKPut] PRIMARY KEY  CLUSTERED ([IdGradDo] ASC,[IdGradOd] ASC)
)
go

CREATE TABLE [Racun]
( 
	[IdKupac]            integer  NOT NULL ,
	[Stanje]             decimal(3)  NULL ,
	CONSTRAINT [XPKRacun] PRIMARY KEY  CLUSTERED ([IdKupac] ASC)
)
go

CREATE TABLE [Stavka]
( 
	[IdNarudzbina]       integer  NOT NULL ,
	[IdArtikal]          integer  NOT NULL ,
	[Kolicina]           integer  NULL ,
	[Suma]               decimal(3)  NULL ,
	CONSTRAINT [XPKStavka] PRIMARY KEY  CLUSTERED ([IdNarudzbina] ASC,[IdArtikal] ASC)
)
go


ALTER TABLE [Artikal]
	ADD CONSTRAINT [R_5] FOREIGN KEY ([IdProdavnica]) REFERENCES [Prodavnica]([IdProdavnica])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Kupac]
	ADD CONSTRAINT [R_7] FOREIGN KEY ([IdGrad]) REFERENCES [Grad]([IdGrad])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [NaplataKupac]
	ADD CONSTRAINT [R_21] FOREIGN KEY ([IdRacun]) REFERENCES [Racun]([IdKupac])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [NaplataKupac]
	ADD CONSTRAINT [R_22] FOREIGN KEY ([IdNarudzbina]) REFERENCES [Narudzbina]([IdNarudzbina])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [NaplataProdavnica]
	ADD CONSTRAINT [R_19] FOREIGN KEY ([IdProdavnica]) REFERENCES [Prodavnica]([IdProdavnica])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [NaplataProdavnica]
	ADD CONSTRAINT [R_20] FOREIGN KEY ([IdNarudzbina]) REFERENCES [Narudzbina]([IdNarudzbina])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Narudzbina]
	ADD CONSTRAINT [R_16] FOREIGN KEY ([IdKupac]) REFERENCES [Kupac]([IdKupac])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Prodavnica]
	ADD CONSTRAINT [R_3] FOREIGN KEY ([IdGrad]) REFERENCES [Grad]([IdGrad])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Put]
	ADD CONSTRAINT [R_13] FOREIGN KEY ([IdGradDo]) REFERENCES [Grad]([IdGrad])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Put]
	ADD CONSTRAINT [R_14] FOREIGN KEY ([IdGradOd]) REFERENCES [Grad]([IdGrad])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Racun]
	ADD CONSTRAINT [R_15] FOREIGN KEY ([IdKupac]) REFERENCES [Kupac]([IdKupac])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Stavka]
	ADD CONSTRAINT [R_17] FOREIGN KEY ([IdNarudzbina]) REFERENCES [Narudzbina]([IdNarudzbina])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Stavka]
	ADD CONSTRAINT [R_18] FOREIGN KEY ([IdArtikal]) REFERENCES [Artikal]([IdArtikal])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

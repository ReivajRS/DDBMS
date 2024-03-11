CREATE DATABASE DBConfiguration
GO
USE DBConfiguration
GO

CREATE TABLE Fragment(
IdFragment INT PRIMARY KEY IDENTITY(1,1),
DistributedTable VARCHAR(100) NOT NULL,
DBMS VARCHAR(100) NOT NULL,
DB VARCHAR(100) NOT NULL,
URI VARCHAR(500) NOT NULL,
AccessUser VARCHAR(100) NOT NULL,
AccessPassword VARCHAR(100) NOT NULL,
Criteria VARCHAR(100) NOT NULL,
CriteriaValue VARCHAR(100) NOT NULL,
Attributes VARCHAR(1000) NOT NULL
)

INSERT INTO Fragment VALUES
('Clientes', 'SQL Server', 'tesebada', 'localhost', 'sa', 'sa', 'Zona', 'Norte', 'IdCliente,Nombre,Estado,Credito,Deuda'),
('Clientes', 'Mongo DB', 'tesebada', 'mongodb+srv://carlosdaniel:7rLrp3XKjgVR5WmM@cluster0.szlfm.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0', 'carlosdaniel', '7rLrp3XKjgVR5WmM', 'Zona', 'Centro', 'IdCliente,Nombre,Estado,Credito,Deuda'),
('Clientes', 'Neo4j', 'tesebada', 'neo4j+s://883e4884.databases.neo4j.io', 'neo4j', '_aItXcqUubEUCoCcH6YLZwMro2SysElEYlRYTJupsI4', 'Zona', 'Sur', 'IdCliente,Nombre,Estado,Credito,Deuda')
--('Cliente', 'SQL Server', 'tesebada', 'localhost', 'Zona', 'IdCliente,Nombre,Estado,Credito,Deuda'),
--('Cliente', 'Mongo DB', 'tesebada', 'mongodb+srv://carlosdaniel:7rLrp3XKjgVR5WmM@cluster0.szlfm.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0', 'Zona', 'IdCliente,Nombre,Estado,Credito,Deuda'),
--('Cliente', 'Neo4j', 'tesebada', 'neo4j+s://883e4884.databases.neo4j.io', 'Zona', 'IdCliente,Nombre,Estado,Credito,Deuda')

CREATE TABLE CLIENTES(
    IDCLIENTE INT PRIMARY KEY ,
    NOMBRE VARCHAR(100) NOT NULL,
    ESTADO VARCHAR(100) NOT NULL,
    CREDITO MONEY NOT NULL,
    DEUDA MONEY NOT NULL
)

CREATE TABLE STATE_ZONE(
    STATE VARCHAR(50) PRIMARY KEY,
    ZONE VARCHAR(20) NOT NULL
)
GO
INSERT INTO STATE_ZONE VALUES
('BAJA CALIFORNIA SUR','NORTE'),
('BAJA CALIFORNIA','NORTE'),
('SONORA','NORTE'),
('CHIHUAHUA','NORTE'),
('COAHUILA','NORTE'),
('NUEVO LEON','NORTE'),
('TAMAULIPAS','NORTE'),
('SINALOA','NORTE'),
('DURANGO','NORTE'),
('ZACATECAS','NORTE'),
('SAN LUIS POTOSI','NORTE'),
('NAYARIT','CENTRO'),
('JALISCO','CENTRO'),
('AGUASCALIENTES','CENTRO'),
('GUANAJUATO','CENTRO'),
('QUERETARO','CENTRO'),
('MICHOACAN','CENTRO'),
('COLIMA','CENTRO'),
('ESTADO DE MEXICO','CENTRO'),
('HIDALGO','CENTRO'),
('TLAXCALA','CENTRO'),
('CDMX','CENTRO'),
('MORELOS','CENTRO'),
('PUEBLA','SUR'),
('GUERRERO','SUR'),
('VERACRUZ','SUR'),
('OAXACA','SUR'),
('CHIAPAS','SUR'),
('TABASCO','SUR'),
('CAMPECHE','SUR'),
('YUCATAN','SUR'),
('QUINTANA ROO','SUR')

SELECT ZONE FROM STATE_ZONE WHERE STATE = 'sinaloa'

CREATE TABLE IDService(
ID INT PRIMARY KEY
)

GO

ALTER PROCEDURE SP_GetID @ID INT OUTPUT
AS
BEGIN
	BEGIN TRAN
		SELECT @ID = ID FROM IDService UPDLOCK
		UPDATE IDService SET ID = ID + 1
	COMMIT TRAN
END
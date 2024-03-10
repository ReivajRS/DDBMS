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

INSERT INTO Fragmento VALUES
('Cliente', 'SQL Server', 'tesebada', 'localhost', 'Zona', 'IdCliente,Nombre,Estado,Credito,Deuda'),
('Cliente', 'Mongo DB', 'tesebada', 'mongodb+srv://carlosdaniel:7rLrp3XKjgVR5WmM@cluster0.szlfm.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0', 'Zona', 'IdCliente,Nombre,Estado,Credito,Deuda'),
('Cliente', 'Neo4j', 'tesebada', 'neo4j+s://883e4884.databases.neo4j.io', 'Zona', 'IdCliente,Nombre,Estado,Credito,Deuda')

CREATE TABLE IDService(
ID INT PRIMARY KEY
)

GO

CREATE PROCEDURE SP_GetID @ID INT OUTPUT
AS
BEGIN
	BEGIN TRAN
		SELECT @ID = ID + 1 FROM IDService WITH UPDLOCK
		UPDATE IDService SET ID = ID + 1
	COMMIT TRAN
END
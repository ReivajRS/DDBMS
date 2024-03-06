CREATE DATABASE DBConfiguration
GO
USE DBConfiguration
GO
CREATE TABLE Fragmento(
IdFragmento INT PRIMARY KEY,
TablaDistribuida VARCHAR(100) NOT NULL,
Gestor VARCHAR(100) NOT NULL,
BaseDeDatos VARCHAR(100) NOT NULL,
Servidor VARCHAR(1000) NOT NULL,
CriterioFragmentacion VARCHAR(100) NOT NULL,
Atributos VARCHAR(1000) NOT NULL
)

INSERT INTO Fragmento VALUES
(1, 'Cliente', 'SQLServer', 'tesebada', 'localhost', 'Zona', 'IdCliente, Nombre, Estado, Credito, Deuda'),
(2, 'Cliente', 'MongoDB', 'tesebada', 'mongodb+srv://carlosdaniel:7rLrp3XKjgVR5WmM@cluster0.szlfm.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0', 'Zona', 'IdCliente, Nombre, Estado, Credito, Deuda'),
(3, 'Cliente', 'Neo4j', 'tesebada', 'neo4j+s://883e4884.databases.neo4j.io', 'Zona', 'IdCliente, Nombre, Estado, Credito, Deuda')
CREATE DATABASE IF NOT EXISTS gimnasio
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_0900_ai_ci;
USE gimnasio;

CREATE USER IF NOT EXISTS 'usuario_admin'@'%' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON gimnasio.* TO 'usuario_admin'@'%';
FLUSH PRIVILEGES;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS usuario_membresia;
DROP TABLE IF EXISTS membresia_plan;
DROP TABLE IF EXISTS rol;
DROP TABLE IF EXISTS usuario;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE usuario (
  id_usuario BIGINT NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  nombre VARCHAR(100) NOT NULL,
  apellidos VARCHAR(150) NULL,
  email VARCHAR(120) NOT NULL,
  telefono VARCHAR(25) NULL,
  password VARCHAR(255) NOT NULL,
  ruta_imagen VARCHAR(1024) NULL,
  activo TINYINT(1) NOT NULL DEFAULT 0,
  codigo_confirmacion VARCHAR(64) NULL,
  creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_usuario),
  UNIQUE KEY uk_usuario_username (username),
  UNIQUE KEY uk_usuario_email (email)
) ENGINE=InnoDB;

CREATE TABLE rol (
  id_rol BIGINT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(20) NOT NULL,
  id_usuario BIGINT NOT NULL,
  PRIMARY KEY (id_rol),
  UNIQUE KEY uk_rol_usuario_nombre (id_usuario, nombre),
  KEY idx_rol_usuario (id_usuario),
  CONSTRAINT fk_rol_usuario
    FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario)
    ON DELETE CASCADE
    ON UPDATE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE membresia_plan (
  id_plan BIGINT NOT NULL AUTO_INCREMENT,
  codigo VARCHAR(30) NOT NULL,
  nombre VARCHAR(80) NOT NULL,
  descripcion_corta VARCHAR(255) NULL,
  precio_mensual DECIMAL(10,2) NOT NULL,
  activo TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (id_plan),
  UNIQUE KEY uk_plan_codigo (codigo),
  CHECK (precio_mensual >= 0)
) ENGINE=InnoDB;

CREATE TABLE usuario_membresia (
  id_usu_plan BIGINT NOT NULL AUTO_INCREMENT,
  id_usuario BIGINT NOT NULL,
  id_plan BIGINT NOT NULL,
  fecha_inicio DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fecha_fin DATETIME NULL,
  estado ENUM('ACTIVA','PAUSADA','CANCELADA') NOT NULL DEFAULT 'ACTIVA',
  renovacion_auto TINYINT(1) NOT NULL DEFAULT 1,
  creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_usu_plan),
  KEY idx_um_usuario (id_usuario),
  KEY idx_um_plan (id_plan),
  CONSTRAINT fk_um_usuario
    FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT fk_um_plan
    FOREIGN KEY (id_plan) REFERENCES membresia_plan (id_plan)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
) ENGINE=InnoDB;

INSERT INTO usuario (username, nombre, apellidos, email, telefono, password, ruta_imagen, activo, codigo_confirmacion) VALUES
 ('gbonilla','Gabriel','Bonilla Zúñiga','gabozu97@gmail.com','84657047','123',NULL,1,NULL),
 ('julloa','Jasson','Ulloa Madriz','jasson2682@gmail.com','83525137','456',NULL,1,NULL),
 ('mjimenez','Miguel','Jimenez Valverde','miguel123@gmail.com','87519654','789',NULL,1,NULL);

INSERT INTO rol (nombre, id_usuario) VALUES
 ('ADMIN',1),('PERSONAL',1),('USUARIO',1),
 ('PERSONAL',2),('USUARIO',2),
 ('USUARIO',3);

INSERT INTO membresia_plan (codigo, nombre, descripcion_corta, precio_mensual, activo) VALUES
 ('GENERAL','Plan General','Acceso al piso de pesas y máquinas',15000.00,1),
 ('PREMIUM','Plan Premium','General + clases dirigidas y boxeo',25000.00,1),
 ('CLASES','Solo Clases','Acceso a todas las clases grupales',18000.00,1);

INSERT INTO usuario_membresia (id_usuario, id_plan, estado, renovacion_auto) VALUES
 (1,(SELECT id_plan FROM membresia_plan WHERE codigo='PREMIUM'),'ACTIVA',1),
 (2,(SELECT id_plan FROM membresia_plan WHERE codigo='GENERAL'),'ACTIVA',1),
 (3,(SELECT id_plan FROM membresia_plan WHERE codigo='CLASES'),'ACTIVA',1);

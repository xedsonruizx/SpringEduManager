-- ============================================================
--  SpringEduManager - Proyecto Final Modulo 6
--  Script de creacion de la BASE DE DATOS
--
--  Solo crea el esquema. Las tablas (estudiantes, usuarios) las
--  genera Hibernate automaticamente al arrancar la aplicacion,
--  gracias a spring.jpa.hibernate.ddl-auto=update.
--
--  Ejecutar una unica vez en MySQL antes de levantar la app.
-- ============================================================

CREATE DATABASE IF NOT EXISTS springedumanager
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

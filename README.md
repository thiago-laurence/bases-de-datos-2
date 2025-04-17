# Viajes turisticos - Tours

Proyecto Backend para la venta de viajes y servicios turisticos, gestión de usuarios, reviews, y proveedores de servicios.

Arquitectura de software:
- Desarrollo del proyecto mediante Maven, framework Spring, ORM Hibernate, y DBMS MySQL.
- Patrón de diseño Repository, divido en: Capa de presentación (Controller), Capa de lógica de negocio (Service), y Capa de acceso a datos (Repository).
- Patrón de diseño para la instanciación del Servicio, como Inyección de dependencias.
- Testing completo para las operaciones ofrecidas por el Servicio.
- Implementación de los servicios mediante HQL y Spring Data JPA.
- Modelo lógico [Entidad-Relación](https://github.com/thiago-laurence/buffets/tree/main/entidad-relacion.png) implementado.

<div align="center">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" height="60" alt="mongodb logo"  />

  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" height="60" alt="mongodb logo"  />

  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/maven/maven-original.svg" height="60" alt="mongodb logo"  />
  
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/hibernate/hibernate-original.svg" height="60" alt="Redis logo"  />

  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mysql/mysql-original.svg" height="60" alt="Redis logo"  />

</div>

---

## NoSQL
Proyectos de investigación e implementacion de consultas sobre Sistemas gestores de bases de datos NoSQL, tales como [MongoDB y Redis](https://github.com/thiago-laurence/buffets/tree/main/nosql).

- **MongoDB:** re-implementacion de Modelos a Documentos y consultas HQL a consultas mediante Agreggation framework.
- **Redis:** implementacion de caché en la aplicacion para optimización de acceso a datos sobre base de datos MySQL.

<div align="center">

  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mongodb/mongodb-original.svg" height="60" alt="mongodb logo"  />
  
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/redis/redis-original.svg" height="60" alt="Redis logo"  />

</div>

--- 
## Docker

Definición de los tres tipos de bases de datos como servicios MySQL, MongoDB y Redis respectivamente, a través de _docker compose_ para ejecución local del proeycto.

```bash
  docker-compose up -d --build
```

<div align="center">

  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/docker/docker-original.svg" height="60" alt="docker logo"  />

</div>
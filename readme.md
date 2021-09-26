# API REST Endereços!

CRUD (Create, Read, Update, Delete) de entidade endereço em padrão REST, utilizando Spring Boot e banco de dados PostgreSQL.


## Entidade Endereço

-   id*
-   streetName*
-   number*
-   complement
-   neighbourhood*
-   city*
-   state*
-   country*
-   zipcode*
-   latitude
-   longitude
-   deleted

**Obs.:** Os atributos marcados com * devem ser obrigatórios

## Properties
**application.properties**

	server.servlet.context-path=/endereco
    springdoc.api-docs.path=/api-docs
    springdoc.swagger-ui.path=/swagger-ui-custom.html
    google.maps.api-code=
    server.error.include-stacktrace=ON_PARAM
    
    spring.datasource.url=
    spring.datasource.username=
    spring.datasource.password=
    spring.jpa.properties.hibernate.dialect=
    
    spring.jpa.hibernate.ddl-auto = update

## Endpoints

- **GET:** /{id} \
Buscar endereço por ID.
- **GET:** /listAll \
Buscar todos os endereços não excluídos.
- **POST:** / \
Criar novo endereço
- **PUD:** / \
Atualizar endereço
- **DELETE:** / \
Realiza exclusão lógica de endereço
- **GET:** /api-docs \
JSON de documentação
- **GET:** /swagger-ui/index.html?configUrl=/endereco/api-docs/swagger-config#/ \
Documentação em Swagger

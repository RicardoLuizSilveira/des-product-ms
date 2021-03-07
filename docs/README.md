[![Build Status](https://travis-ci.com/RicardoLuizSilveira/des-product-ms.svg?branch=main)](https://travis-ci.com/RicardoLuizSilveira/des-product-ms)
# des-product-ms

GitPages
https://ricardoluizsilveira.github.io/des-product-ms/

Heroku APP
https://des-product-ms.herokuapp.com/

DockerHub

drive with jar
https://drive.google.com/drive/folders/15n8VRNMsCDEh2yqdgwZK9dVzlhx3s1Fb?usp=sharing


------

## Requisitos

### Table "Product"
{
"id": "string",
"name": "string",
"description": "string",
"price": 59.99
}


### Repository methods
- saveProduct
    - updateProduct

- deleteById

- findById
- findAll
- complex search
    - q 	deverá bater o valor contra os campos name e description
    - min_price 	deverá bater o valor ">=" contra o campo price
    - max_price 	deverá bater o valor "<=" contra o campo price

### Business constraints
- Campos obrigatórios durante a criação e alteração:
    - name,
    - description e
    - price

- Positive fields
    - price o valor deve ser positivo.

### Endpoints do controller

Verbo HTTP 	    Resource path 	  Descrição
POST 	          /products 	      Criação de um produto
PUT 	          /products/{id} 	  Atualização de um produto
GET 	          /products/{id} 	  Busca de um produto por ID
GET 	          /products 	      Lista de produtos
GET 	          /products/search 	Lista de produtos filtrados
DELETE 	        /products/{id} 	  Deleção de um produto


## docker hub
https://hub.docker.com/r/ricardosilveira/des-product-ms

docker pull ricardosilveira/des-product-ms:1.0.0

docker run -it --rm -p9999:9999 --name ms-product ricardosilveira/des-product-ms:1.0.0



docker tag local-image:tagname new-repo:tagname
docker push new-repo:tagname

docker push ricardosilveira/des-product-ms:tagname


## build a Spring boot image with maven
./mvnw spring-boot:build-image

docker run -it --rm -p9999:9999 --name ms-product product:0.0.1-SNAPSHOT


## swagger
http://localhost:9999/swagger-ui/
# RECFLIX

<h1 align="center">
	<img width="300" src="https://raw.githubusercontent.com/UtkarshGupta-CS/recflix/master/public/assets/images/recflixlogo.png" alt="RECFLIX">
	<br>
</h1>

**A movie streaming system that uses an integrated architecture of movie recommender system which considers user behaviour, interactions, and understanding patterns developed to recommend movies to the user. This architecture analyses and transforms user behaviour into implicit ratings.**

## Usage

### Manually

- recflix-frontend

      		npm install
      		npm start

- recflix-backend

      		cd recflix-backend
      		mvn jetty:run

- recflix-recommendation-engine

      		cd recflix_recommendation_engine
      		mvn package
      		java -jar target/recflix_recommendation_engine-jar-with-dependencies.jar

### Using Docker

    	docker-compose up

### TODO

- [ ] Added check for returning passwords User type in graphql schema

### Collaborators

- [**Utkarsh Gupta**](https://github.com/UtkarshGupta-CS), <https://twitter.com/utkarshgupta97>
- [**Anand Samuel**](https://github.com/AndyPSam)
- [**Sumit Kumar**](https://github.com/sumit1202)

## License

Licensed under [MIT](./LICENSE).

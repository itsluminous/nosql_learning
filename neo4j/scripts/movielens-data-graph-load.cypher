CREATE CONSTRAINT FOR (u:User) REQUIRE u.userId IS UNIQUE;
CREATE CONSTRAINT FOR (m:Movie) REQUIRE m.movieId IS UNIQUE;
CREATE CONSTRAINT FOR (g:Genre) REQUIRE g.name IS UNIQUE;

CREATE INDEX FOR (r:Rating) ON (r.userId);
CREATE INDEX FOR (r:Rating) ON (r.movieId);

//load predefined genres
CREATE  (: Genre {identity: 1, name: "Action"}),
		(: Genre {identity: 2, name: "Adventure"}),
		(: Genre {identity: 3, name: "Animation"}),
		(: Genre {identity: 4, name: "Children's"}),
		(: Genre {identity: 5, name: "Comedy"}),
		(: Genre {identity: 6, name: "Crime"}),
		(: Genre {identity: 7, name: "Documentary"}),
		(: Genre {identity: 8, name: "Drama"}),
		(: Genre {identity: 9, name: "Fantasy"}),
		(: Genre {identity: 10, name: "Film-Noir"}),
		(: Genre {identity: 11, name: "Horror"}),
		(: Genre {identity: 12, name: "Musical"}),
		(: Genre {identity: 13, name: "Mystery"}),
		(: Genre {identity: 14, name: "Romance"}),
		(: Genre {identity: 15, name: "Sci-Fi"}),
		(: Genre {identity: 16, name: "Thriller"}),
		(: Genre {identity: 17, name: "War"}),
		(: Genre {identity: 18, name: "Western"});
		

//load movies
LOAD CSV WITH HEADERS FROM 'file:///Users/prakashkumar/repo/rdbms_2_nosql/datasets/movielens/movielens/movies.csv' AS line
CREATE (m:Movie {movieId: toInteger(line.id)})
SET m.title = line.title
SET m.genres = line.genres
SET m.id = toInteger(line.id);

//load users
LOAD CSV WITH HEADERS FROM 'file:///Users/prakashkumar/repo/rdbms_2_nosql/datasets/movielens/movielens/users.csv' AS line
CREATE (u:User {userId: toInteger(line.id), id: toInteger(line.id), age: line.age_group, gender: line.gender, occupation: line.occupation, zipCode: line.zip_code});


//load rating and rating relationship
// USING PERIODIC COMMIT 10000 //without this line, we get an outofmemory exception
LOAD CSV WITH HEADERS FROM 'file:///Users/prakashkumar/repo/rdbms_2_nosql/datasets/movielens/movielens/ratings.csv' AS line
MATCH (m:Movie {movieId : toInteger(line.mId)})
MATCH (u:User {userId: toInteger(line.uId)})
CREATE (r:Rating {rate: TOFLOAT(line.rate), ts: line.ts}),
	   (u)-[:GAVE]->(r),
       (r)-[:TO]->(m);


//evolve the data to have a genre with relationship to the movie
MATCH (movie:Movie)
WHERE coalesce(movie.genres, "-") <> "-"
WITH SPLIT(movie.genres, "|") as parts, movie as m
UNWIND parts as x
MATCH (g: Genre {name: x})
MERGE (m)-[:IS_A]->(g)
REMOVE m.genres;


// delete all ratings - remove all relationships and then delete
MATCH (n:Rating) DETACH DELETE n;
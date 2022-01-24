curl --header "Content-Type: application/json" -X POST -d '{"title":"mon titre","description":"ma description "}' http://localhost:8080/questions

curl --header "Content-Type: application/json" -X POST -d '{"text": "voici ma réponse"}' http://localhost:8080/questions/1000/answers
curl -X GET http://localhost:8080/questions/1000/answers

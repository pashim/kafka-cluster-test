docker-compose up -d;

sleep 5

docker exec kafka-test_mongo1_1 /scripts/rs-init.sh;
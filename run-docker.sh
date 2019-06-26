echo "Creating new JAR-File..."
mvn clean install
echo "Removing current container..."
docker container rm -f outputadapter
echo "Removing current image..."
docker image rm -f outputadapter
echo "Building new image and starting container..."
docker-compose up -d

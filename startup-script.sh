echo "Creating required files.."
mkdir -p ./src/main/resources/files
echo '[]' >> ./src/main/resources/users.json

echo "Checking java version.."
java -version

echo "Running Editor.."
java -jar OnlineTextEditor-0.0.1-SNAPSHOT.jar
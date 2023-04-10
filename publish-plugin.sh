export CERTIFICATE_CHAIN=`cat .env/certificate-chain`
export PRIVATE_KEY=`cat .env/private-key`
export PRIVATE_KEY_PASSWORD=`cat .env/private-key-password`
export PUBLISH_TOKEN=`cat .env/publish-token`
export DATAGRIP_DIR=`cat .env/datagrip-dir`
./gradlew publishPlugin
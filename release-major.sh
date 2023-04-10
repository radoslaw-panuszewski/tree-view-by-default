cp version.properties version.properties.backup
./gradlew incrementSemanticVersion --major
VERSION=$(awk -F= '{if (NF>1) print $2}' < version.properties)
echo "Releasing version: $VERSION"
./publish-tag-and-push.sh
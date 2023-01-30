./gradlew incrementSemanticVersion --minor
VERSION=$(awk -F= '{if (NF>1) print $2}' < version.properties)
echo "Releasing version: $VERSION"
./publish-plugin.sh
git add .
git commit -m "Release: $VERSION"
git tag -a "$VERSION" -m "Release: $VERSION"
git push origin "$VERSION"
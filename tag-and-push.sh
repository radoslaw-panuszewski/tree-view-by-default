git add .
git commit -m "Release: $VERSION"
git tag -a "$VERSION" -m "Release: $VERSION"
git push
git push origin "$VERSION"
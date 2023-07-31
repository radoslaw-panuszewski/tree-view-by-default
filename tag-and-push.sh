git add .
git commit -m "Release: $VERSION"
if git tag -a "$VERSION" -m "Release: $VERSION"
then
  git push
  git push origin "$VERSION"
else
  git reset --hard HEAD^
fi
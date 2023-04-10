if ./publish-plugin.sh
then
  ./tag-and-push.sh
  rm -f version.properties.backup
else
  echo "Plugin publication failed, skip tagging..."
  rm -f version.properties
  mv version.properties.backup version.properties
fi
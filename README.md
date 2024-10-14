# <img src="src/main/resources/META-INF/pluginIcon.svg" width="35" style="margin:-4px 0px" /> Tree View By Default

![Version](https://img.shields.io/jetbrains/plugin/v/20941)
![Version](https://img.shields.io/jetbrains/plugin/d/20941)
![Version](https://img.shields.io/jetbrains/plugin/r/rating/20941)

If you use document databases (like MongoDB), it is more convenient to preview the query 
results in `Tree` mode instead of the standard `Table` mode. Unfortunately, DataGrip does 
not provide any way to set `Tree` as default presentation mode, so you have to switch it 
manually every time.

This plugin comes to rescue!

What it does:
* Changes default presentation mode of the query results to `Tree`
* If there is only one result, it is automatically expanded
* You can copy value of the leaf node without the containing JSON via `Cmd+Option+C`

It is recommended to use this plugin in DataGrip, but it should also work in other IDEs with database support.

The plugin is available in JetBrains marketplace: https://plugins.jetbrains.com/plugin/20941-tree-view-by-default

## Demo
![alt](doc/demo.gif)

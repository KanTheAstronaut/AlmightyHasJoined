# AlmightyHasJoined
## Overview
This plugin allows you to set custom join and quit messages with a few cool features.

## Commands
- /almightyreload - almighty.reload - Reloads the configuration file


## Permissions
- almighty.join - Custom message on join
- almighty.quit - Custom message on quit
- almighty.reload - /almightyreload - Reloads the configuration file


## Config.yml
- %player% is replaced with the player name
- %world% is replaced with the world name (WONT WORK WITH BUNGEE)
- %time% is replaced with the current ingame time (WONT WORK WITH BUNGEE)
- Color codes work!!!

###### Code (YAML):
```
joinmsg: '&6THE ALMIGHTY %player% JOINED!'
quitmsg: '&6THE ALMIGHTY %player% LEFT!'
onlyonjoin: true
onlyonquit: false
disablejoinmsg: false
disablequitmsg: false
makemsgprivate: false
```
joinmsg - Sets the join message
quitmsg - Sets the quit message
onlyonjoin - Sends message only on join
onlyonquit - Sends message only on quit
disablejoinmsg - Removes the public join message (does not affect private join messages)
disablequitmsg - Removes the public quit message (does not affect private quit messages)
makemsgprivate - Sends message only to the player (useless for quit messages as the player wont receive the message)

# FlyMachineFallSaver
Small client mod, automatic send configured chat command or leave from server when player has fallen from fly machine

## Using
Use chat command `/start`\
After this, mod will save current Y coord and start checking player's Y coord. If current Y coord become less than saved (player has fallen) - mod will send a configured command or leave the server

To stop use `/stop`\
To configure mod use command `/config leave` or `/config command "/some-cool-command"`
> [!NOTE]
> Configuration is global, doesn't matter which server or world you're currently playing on. It means if you use `/config command "/hello"` on server1 then mod will use `/hello` command even on server2 and in singleplayer

# FlyMachineFallSaver
Small client mod, automatic send configured chat command or leave from server when player has fallen from fly machine

## Using
Use chat command `/f_start`\
After this, mod will save current Y coord and start checking player's Y coord. If current Y coord become less than saved (player has fallen) - mod will send a configured command or leave the server

To stop use `/f_stop`\
To configure mod use command `/f_config leave` or `/f_config command "some-cool-command-without-slash"`
> [!NOTE]
> Configuration is global, doesn't matter which server or world you're currently playing on. It means if you've used `/config command "hello"` on server, mod will use `/hello` command on any other server and in singleplayer

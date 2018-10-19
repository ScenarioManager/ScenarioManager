# Scenario Manager

I know that you're thinking "*another* scenario manager ... \*sigh*" but this one is special; allow me to explain. I will go into more detail below, but here are the highlights:

* Support for scenarios written in multiple languages.
* Perfect integration possible with each individual server that runs it.
* In-game configurable scenarios. 
* Support for multiple types of scenarios.

---
 
It supports multiple languages; finally, the Skript developers and the Java/Kotlin/Scala developers can work together. More information on how to use each language: [Java](#), [Skript](#), [Kotlin](#), [Scala](#).

It can integrate very nicely with the server. Most scenarios only do stuff while a game is running (exceptions listed below). Scenarios only affect worlds that have been marked as game worlds to the plugin; no more time bombs going off in spawn. Moreover, Scenarios only affect players that have been marked as game players. Scenarios can be enabled and disabled in the middle of a game with no issues; although this is a nice feature, for the sake of game quality *please* enable the proper scenarios before the game starts. All scenarios follow a customizable prefix format.

Scenarios can have configs. Every scenario's config can be edited in game. Unlike scenarios these should not be changed after a game has stared. While you shouldn't change a scenario's config after the game has started, it is not blocked. In some cases, the change may work but in others it will not so just make sure you have these settings right before you start the game.

This scenario manager has support for multiple types of scenarios. It supports your standard scenarios (Cut Clean, Hastey Boys, Sky High, etc), team assigning scenarios (Red vs Blue, Sky High, etc), and world updating scenarios (Big Crack, etc).

## Video Tutorial

I would like to include a video guide on how to add this to a server with skript. If anyone has a server and they would like to go through the installation and integration of this plugin on the server with me, send me a message on  discord so we can make it happen. my discord username is `DarkPaladin#0309`. This message will be removed as soon as I find someone to do this with.

## Installation

1. Download the latest version [here][download latest].
2. Move the downloaded jar to the `plugins` directory of your server.
3. *(recommended)* Install the scenario manager commands plugin; instructions can be found [here](#). If you do not install this plugin, you will need to install another plugin/skript to interact with the scenario manager or make your own.
4. *(optional)* Install the ScenarioManagerSk plugin; instructions can be found [here](#). If you do not install this plugin, you will not be able to install scenarios written in skript. 
5. Read and complete the necessary steps in server integration.

## Server Integration

While I try to keep the amount of work to set this up at a minimum, there are a few things
that should be done to greatly improve the experience of using this scenario manager. This should be done in a separate plugin/skript than any scenario.

### Java

1. Get the instance of the scenario manager with `ScenarioManagerInstace.getScenarioManager()`
1. GameWorldProvider
2. GamePlayerProvider
3. GameProvider
4. TeamProvider

### Skript

This is not currently supported in the skript addon.

### Kotlin/Scala

No docs yet but it is basically the same as java. Just know how your language does interoperability with Java. Note, in Kotlin the ScenarioManager instance is accessed with just `scenarioManager` instead of `ScenarioManagerInstance.getScenarioManager()`.

## Verified Scenarios

Below is a list of verified scenario. When a scenario is verified, it means a few things:

1. It is going to work.
2. All features have been tested by the creator in real games. The number of games the scenario is tested depends on the complexity of the scenario.
3. It supports being enabled or disabled in the middle of a game.

What it does **not** mean:

1. It is going to work with *all other* scenarios. If two scenarios seem like they may have conflicts, you should defiantly test the combination together before hosing a public match.
2. The configuration settings can be changed while a game is running and work.

In order to have your scenario added as a verified scenario, [edit this file][edit readme] and add your scenario to the list alphabetically. Make the commit message `Add <scenario name> as a verified scenario`. Make the target branch `verify-scenario/<scenario name>`. Create merge request with the changes. The merge request must include the name of the scenario, the description of the scenario, a YouTube video of the scenario working in a UHC. If one video cannot show all of the features of the scenario, make multiple. If the video is not edited down to include only the relevant parts, include timestamps with short descriptions of what is being shown.

---

[PL] **Cut Clean**: *All ores and food are dropped in their smelted variety.* [[Repository](https://gitlab.com/CalebBassham/cutclean)]

[PL] [1.13+] **Time Bomb**: *When a player dies, their items will be placed in a chest that explodes after 30 seconds.* [[Repository](https://gitlab.com/CalebBassham/timebomb)]

## *Do you need help?*

If you are having issues with setting up the scenario manager with your server, writing new scenarios for this scenario manager, or converting your scenarios to work with this scenario manager, feel free to message me on discord. My username is `DarkPaladin#0309`. You can also find me on the Reddit UHC discord. If you have found a bug with the scenario manager, please open a new issue, describe the problem in as much detail as possible, and include any error messages from the console. If you do not want to create an account, you can alternatively send an email to [incoming+CalebBassham/ScenarioManager@incoming.gitlab.com][service desk]. If you have found a problem with a specific scenario, please open an issue on their respective repositories. Do not report issues with specific scenarios using this scenario manager here!

[edit readme]: https://gitlab.com/CalebBassham/ScenarioManager/edit/readme/README.md?commit_message=Add%20<scenario%20name>%20as%20a%20verified%20scenario
[service desk]: mailto:incoming+CalebBassham/ScenarioManager@incoming.gitlab.com
[download latest]: https://gitlab.com/CalebBassham/ScenarioManager/-/jobs/artifacts/master/raw/build/libs/ScenarioManager.jar?job=package
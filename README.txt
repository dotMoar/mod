
Source installation information for modders
-------------------------------------------
A learning-first Minecraft Forge 1.20.1 mod focused on practicing Java and core modding concepts,
then building features inspired by classic magic mods like Astral Sorcery and Thaumcraft—without
copying their code or assets.
This is a clean-room project: all implementation is original, and all assets are either original or openly licensed.
The code is released under MIT and the assets under an open Creative Commons license (e.g., CC BY 4.0)
with proper attribution.

Setup Process:
==============================

Step 1: Open your command-line and browse to the folder where you extracted the zip file.
Step 2: You're left with a choice.
1. Select your build.gradle file and have it import.
2. Run the following command: `./gradlew genIntellijRuns`
3. Refresh the Gradle Project in IDEA if required.

If at any point you are missing libraries in your IDE, or you've run into problems you can
run `gradlew --refresh-dependencies` to refresh the local cache. `gradlew clean` to reset everything
(this does not affect your code) and then start the process again.

Mapping Names:
=============================
By default, the MDK is configured to use the official mapping names from Mojang for methods and fields
in the Minecraft codebase. These names are covered by a specific license. All modders should be aware of this
license, if you do not agree with it you can change your mapping names to other crowdsourced names in your
build.gradle. For the latest license text, refer to the mapping file itself, or the reference copy here:
https://github.com/MinecraftForge/MCPConfig/blob/master/Mojang.md

Additional Resources:
=========================
Community Documentation: https://docs.minecraftforge.net/en/1.20.1/gettingstarted/
LexManos' Install Video: https://youtu.be/8VEdtQLuLO0
Forge Forums: https://forums.minecraftforge.net/
Forge Discord: https://discord.minecraftforge.net/

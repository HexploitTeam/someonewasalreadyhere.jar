# Someone Was Already Here — NeoForge 1.20.1 Mod

A psychological horror simulation engine for Minecraft. Introduces systemic horror via an "Awareness" counter that drives environmental changes and entity behaviors — no scripted jump scares.

## Project Setup

- **Language**: Java 17+ (GraalVM 22.3 available)
- **Build System**: Gradle (wrapper at `./gradlew`)
- **Mod Loader**: NeoForge 1.20.1 (forge version 47.1.106)
- **Mod ID**: `someonewasalreadyhere`

## Building

Run the **Build Mod** workflow, or manually:

```bash
./gradlew build
```

Output jar: `build/libs/someonewasalreadyhere-1.0.0.jar`

The first build downloads Minecraft + NeoForge assets and takes several minutes. Subsequent builds are much faster due to caching.

## Installing the Mod

1. Install [NeoForge 1.20.1](https://neoforged.net/) in your Minecraft launcher.
2. Copy `build/libs/someonewasalreadyhere-1.0.0.jar` to your `mods/` folder.
3. Launch Minecraft with the NeoForge profile.

## Sound Assets

Place `.ogg` sound files into `src/main/resources/assets/someonewasalreadyhere/sounds/`. See `PLACEHOLDER.txt` in that folder for the full list of required sounds.

## User Preferences

- Keep build workflow as console type (no web frontend — this is a Minecraft mod)

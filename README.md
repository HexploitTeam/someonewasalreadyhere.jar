# Someone Was Already Here — NeoForge 1.20.1 Mod

A fully functional psychological horror simulation engine for Minecraft.

## Setup

1. Install Java 17+ and Gradle.
2. Open the folder in IntelliJ IDEA or Eclipse with the ForgeGradle plugin.
3. Run `./gradlew genIntellijRuns` (or `genEclipseRuns`).
4. Run the `runClient` or `runServer` Gradle task.

## Sound Assets Required

Place `.ogg` files into `src/main/resources/assets/someonewasalreadyhere/sounds/`.
See the `PLACEHOLDER.txt` file in that folder for the full list.

## Systems Overview

| System | Description |
|---|---|
| **Awareness** | Global 0–1000 counter driving all horror escalation. Decays over time. |
| **CLM Chat** | Classifies player chat and responds in-character; raises awareness. |
| **Custodian** | Periodically modifies world blocks near players (seals lava, paves paths). |
| **Echo** | Records player movement/actions; spawns EchoHunters at past locations. |
| **Silence** | Chunk-level silence field; applies slowness + muffled audio client-side. |
| **Events** | 26 scheduled events triggered by awareness or chat intent. |
| **Phantom** | PhantomPlayer entity triggers fog + render-distance reduction on clients. |

## Entities (21 unique types)

Observer · EchoHunter · Caretaker · ArchiveHound · PhantomPlayer · Archivist ·
HollowMimic · WindowWatcher · Surveyor · StaticShepherd · Sleeper ·
TunnelListener · MaintenanceDrone · Incomplete · ForgottenMiner · MirrorWalker ·
QuietHost · ObserverPrime · ResidualEcho · Cartographer · Curator

## Items (22)

DistortedCompass · SoundLantern · StabilizerCharm · AnalogRecorder ·
MemoryShard · BrokenWatch · RepairInjector · AnomalyScanner · EchoLens ·
SignalJammer · ResidualTape · ArchiveBadge · DistortionShard · NoiseGenerator ·
MemoryLantern · RepairSpike · ObservationMask · StolenBlueprint ·
StaticBattery · EchoFragmentTotem · QuietBloom · BrokenFrequencyRadio

## Dimensions

- `someonewasalreadyhere:the_archive` — underground stone-brick void dimension
- `someonewasalreadyhere:the_quiet` — featureless flat plains with constant false dawn

## Architecture Notes

- All server-side data is persisted via `SavedData` (AwarenessWorldData).
- Player capabilities (EchoCapability) survive death and dimension transfer.
- Network packets (SimpleChannel) handle client-side effects: fog, silence audio, error screen.
- The Options mixin forces render distance to 4 while PhantomPlayer is active.
- No jump scares. All horror is systemic and environmental.

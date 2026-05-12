# AGANT.md

Ce fichier sert de memoire rapide pour travailler sur le mod `patrick-le-stegosaure`.

## Vue d'ensemble

- Type de projet : mod Fabric Minecraft
- Nom du mod : `patrick-le-stegosaure`
- Version du mod : `1.0.0`
- Version Minecraft : `26.1.2`
- Fabric Loader minimal : `0.19.2`
- Fabric API : `0.148.0+26.1.2`
- Java requise : `25`

## Dossiers importants

- Code principal : [src/main/java/com/patricklestegosaure](F:/document/Documents/devloppement%20perso/patrick-le-stegosaure-template-26.1.2/src/main/java/com/patricklestegosaure)
- Code client : [src/client/java/com/patricklestegosaure/client](F:/document/Documents/devloppement%20perso/patrick-le-stegosaure-template-26.1.2/src/client/java/com/patricklestegosaure/client)
- Assets et lang : [src/main/resources/assets/patrick-le-stegosaure](F:/document/Documents/devloppement%20perso/patrick-le-stegosaure-template-26.1.2/src/main/resources/assets/patrick-le-stegosaure)
- Data pack du mod : [src/main/resources/data/patrick-le-stegosaure](F:/document/Documents/devloppement%20perso/patrick-le-stegosaure-template-26.1.2/src/main/resources/data/patrick-le-stegosaure)
- Exports Blockbench utilisateur : [blockbench_exports](F:/document/Documents/devloppement%20perso/patrick-le-stegosaure-template-26.1.2/blockbench_exports)

## Fichiers coeur

- Initialiseur mod : [PatrickLeStegosaure.java](F:/document/Documents/devloppement%20perso/patrick-le-stegosaure-template-26.1.2/src/main/java/com/patricklestegosaure/PatrickLeStegosaure.java)
- Registre items : [ModItems.java](F:/document/Documents/devloppement%20perso/patrick-le-stegosaure-template-26.1.2/src/main/java/com/patricklestegosaure/registry/ModItems.java)
- Registre blocs : [ModBlocks.java](F:/document/Documents/devloppement%20perso/patrick-le-stegosaure-template-26.1.2/src/main/java/com/patricklestegosaure/registry/ModBlocks.java)
- Registre dimensions : [ModDimensions.java](F:/document/Documents/devloppement%20perso/patrick-le-stegosaure-template-26.1.2/src/main/java/com/patricklestegosaure/registry/ModDimensions.java)
- Registre entites : [ModEntityTypes.java](F:/document/Documents/devloppement%20perso/patrick-le-stegosaure-template-26.1.2/src/main/java/com/patricklestegosaure/registry/ModEntityTypes.java)
- Portail : [PatrickPortalBlock.java](F:/document/Documents/devloppement%20perso/patrick-le-stegosaure-template-26.1.2/src/main/java/com/patricklestegosaure/block/PatrickPortalBlock.java)
- Cle du portail : [PatrickKeyItem.java](F:/document/Documents/devloppement%20perso/patrick-le-stegosaure-template-26.1.2/src/main/java/com/patricklestegosaure/item/PatrickKeyItem.java)
- Logique monde et progression : [PatrickWorldEvents.java](F:/document/Documents/devloppement%20perso/patrick-le-stegosaure-template-26.1.2/src/main/java/com/patricklestegosaure/world/PatrickWorldEvents.java)
- Sauvegarde de progression : [PatrickWorldState.java](F:/document/Documents/devloppement%20perso/patrick-le-stegosaure-template-26.1.2/src/main/java/com/patricklestegosaure/world/PatrickWorldState.java)

## Systeme actuel

- Un portail craftable permet d'aller de l'Overworld vers `patrick_world`.
- La cle episode 1 active le portail.
- La cle episode 2 active aussi le portail.
- Si `Pouet` est deja sauve et que le joueur traverse le portail avec la cle episode 2 en main, le joueur va vers la zone episode 2.
- Un portail retour existe dans le monde de Patrick.
- Le monde de Patrick contient :
  - un hub
  - une arene Thierry
  - une cage de Pouet
  - une zone episode 2 avec meteorite
  - un chateau de Pascal
  - une cage pour le petit renard

## Entites importantes

- `PatrickEntity`
  - compagnon du joueur
  - suit le joueur
  - attaque Thierry et Pascal
  - enorme vie et degats recus limites pour donner un effet quasi immortel
- `ThierryEntity`
  - boss hostile du niveau 1
  - version amie au niveau 2
  - plusieurs phases de combat
- `PascalEntity`
  - boss du niveau 2
  - plusieurs phases avec pieges et invocations
- `PouetEntity`
  - captif puis libere apres victoire niveau 1
- `SaucisseEntity`
  - ami calme pres du hub

## Progression sauvegardee

Etat stocke dans `PatrickWorldState` :

- `hubBuilt`
- `arenaStarted`
- `pouetFreed`
- `episode2Built`
- `meteoriteReturned`
- `pascalStarted`
- `pascalDefeated`
- `foxFreed`
- `thierryHelpedPascalFight`
- position de retour Overworld

## Boss fights actuels

### Thierry

- beaucoup plus de vie qu'au depart
- 3 phases
- rugissement qui applique des malus
- onde de choc au sol
- charge en phase finale

### Pascal

- beaucoup plus de vie qu'au depart
- 3 phases
- malediction de chateau
- cases de magma piegees
- invocation de gardes
- frappe meteorite en phase avancee

## Crafts utiles

### Cadre du portail Patrick

```text
S S S
S E S
S S S
```

- `S` = mossy cobblestone
- `E` = emerald

### Cle Patrick

```text
E
G
S
```

- `E` = emerald
- `G` = gold ingot
- `S` = stick

### Cle episode 2

```text
  D
E R E
  G
```

- `D` = diamond
- `E` = emerald
- `R` = redstone
- `G` = gold ingot

## Commandes utiles

Compiler le mod :

```powershell
.\gradlew.bat build
```

Jar de sortie :

- [build/libs/patrick-le-stegosaure-1.0.0.jar](F:/document/Documents/devloppement%20perso/patrick-le-stegosaure-template-26.1.2/build/libs/patrick-le-stegosaure-1.0.0.jar)

## Points d'attention

- Ne pas toucher a `blockbench_exports/` sauf demande explicite.
- Le depot peut etre sale : ne jamais revert les changements utilisateur.
- Les mappings Fabric de cette version ne sont pas toujours ceux qu'on attend :
  - `Zombie` est dans `net.minecraft.world.entity.monster.zombie.Zombie`
  - certains champs internes changent de nom selon la version
- La compilation Gradle peut demander un acces reseau si la distribution manque.
- Les fichiers de langue FR et EN doivent etre gardes a jour a chaque nouvel item/bloc.

## Idees de prochaines etapes

- ajouter de vrais sons custom au lieu d'utiliser seulement les sons vanilla
- ajouter dialogues ou messages scenarises pour Patrick, Saucisse, Pouet, Thierry
- ajouter episode 3 avec nouvelle cle et nouveau biome/structure
- ajouter loot de boss et recompenses
- ajouter structure generation plus propre que la pose brute de blocs


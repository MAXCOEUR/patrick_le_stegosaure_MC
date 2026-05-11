# Exports Blockbench -> mod

Le but : modifier les mobs dans Blockbench, puis remettre le modele dans le mod.

Important : Fabric ne charge pas directement les fichiers `.bbmodel`. Le `.bbmodel` sert seulement a travailler dans Blockbench. Pour le mod, il faut exporter le modele en Java.

## 1. Ouvrir dans Blockbench
1. Ouvre Blockbench.
2. Fais `File > Import > Java Entity`.
3. Choisis un fichier dans `java_import`, par exemple `PascalModel.java`.
4. Dans `Textures`, importe le PNG correspondant depuis `textures/entity`, par exemple `pascal.png`.
5. Sauvegarde en `.bbmodel` si tu veux garder un projet Blockbench editable.

## 2. Remettre dans le mod
Quand tu as fini de modifier dans Blockbench :
1. Fais `File > Export > Export Java Entity`.
2. Garde le meme nom de classe que dans le mod, par exemple `PascalModel`.
3. Remplace le fichier correspondant dans :
   `src/client/java/com/patricklestegosaure/client/render/`

Correspondances :
- `PatrickModel.java` -> Patrick
- `ThierryModel.java` -> Thierry
- `SaucisseModel.java` -> Saucisse
- `PouetModel.java` -> Pouet
- `PascalModel.java` -> Pascal
- `PamoukModel.java` -> Pamouk
- `KalashModel.java` -> Kalash
- `BrigitteModel.java` -> Brigitte

Si Blockbench exporte une methode appelee `createBodyLayer`, il faudra la renommer en `createLayer`, car le client du mod appelle `PatrickModel::createLayer`, `PascalModel::createLayer`, etc.

## Si Blockbench demande du JSON
Utilise les fichiers dans `blockbench_json`.

Tu as deux extensions pour le meme contenu :
- `.bbmodel` : format natif Blockbench.
- `.json` : utile si ton bouton d'import affiche seulement les JSON.

Ouvre par exemple `blockbench_json/Pascal.json` avec `File > Open Model` ou `File > Import`, selon ce que Blockbench te propose.
Ensuite tu modifies, puis tu fais `File > Export > Export Java Entity` pour remettre le modele dans le mod.

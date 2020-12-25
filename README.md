# StripmineToolkit
 Repo for AG's Forge mod "The Strip-Miner's Toolchest"
 
## Requires Minecraft 1.16.4 and the requisite Forge installation
### Mod version uses Forge version 35.1.13 for 1.16.4
 
## Quick-Guide
* No plans for a Fabric port right now.
* Plugin is fairly compatibility-friendly and has passed all of my current inter-mod compatibility testing, but I'm not a magician. Please leave me an issue thread if you encounter a problem.
* I'll be submitting releases for this mod whenever I finish development on another set of major features. Next up is the last of the manual tools: the axe and hoe bulk tools.
* There's a possibility lower-end PCs will chug a bit due to how fast the higher tier tools can break blocks, but I've done the best I can to optimize everything.
* Please submit an issue thread if you encounter anything.
* Wiki tab will have crafting recipes, stats, and feature previews in it at some point, maybe even now.
* If you feel something is over/underpowered, let me know in an issue thread

## If you're here now, its because I gave you access. Please don't share source code, resources, or assets from the mod. I'll revoke access if you distribute code without my permission, as I hold it under reserved license.
 
**Working Features:**
* *New Tool:* Hammers and Dredges
   * Hammers can harvest any blocks that pickaxes can, only in a 3x3 area.
   * Dredges can harvest any blocks that shovels can, only in a 3x3 area.  
   * They come in 6 tiers: Stone, Iron, Gold, Diamond, Obsidian, and Netherite.
   * Require a hefty amount of resources to craft, with Obsidian requiring a special new block.
   * Repairable with anvils and their requisite blocks (excluding Netherite tiers which only need 1 ingot)
   * Compatible with all vanilla and modded enchantments for pickaxes/shovels, thru anvils and enchanting tables
* *New Block:* Obsidian Block
   * Crafted with 8 obsidian and 1 crying obsidian in the middle. Higher blast resistance than Obsidian. Smelt for Compressed Obsidian.
* *New Block:* Compressed Obsidian
   * Smelt Obsidian Blocks in a furnace (currently not compatible w/ blast furnace) to craft. Long smelting time due to high tool durability.
* *Other:*
   * Full compatibility for modded blocks, ores, etc
   * Full compatibility with NEI, JEI, and other recipe plugins
   * Optimized raycasting system for lower-end PCs

**Planned Features:**        **(bold currently in progress)**
   * **Scythes:** Bulk tilling, harvesting, and planting hoes with dynamic, contextual behaviors.
   * **Lumberjaxe:** Bulk chopping axe that can either fell an entire tree in one block break or break a 3x3 area of wood, your choice!
   * **Tiered Mining Radii:** Higher tier bulk tools will mine larger areas (Netherite a 5x5x2 area, for instance), lower as low as 3x2.
   * Material Compressor: block for producing Compressed Obsidian and other fun things. Will replace Obsidian Block when finished.
   * Alloy Table: for crafting tool parts
   * Tool Parts: an alternative, more time consuming, but cheaper method of crafting bulk tools. Required for future tools.
   * Shape Charge: TNT that explodes in an exact rectangular shape, leaving all block drops intact. 3 sizes.
   * Big Boi Bucket:- 3x3 bucket for getting big boi water. For the hydro homies.
   * Nethersaw: A nether-magic fueled chainsaw to deal insane amounts of damage at insane speeds. Uses fuel and has tiered blades.
   * Netherdrill: A dirt-chucking alternative to the Nethersaw. Rip n' tear, your way.

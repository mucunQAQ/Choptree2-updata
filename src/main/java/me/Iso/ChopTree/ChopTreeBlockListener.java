//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.Iso.ChopTree;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class ChopTreeBlockListener implements Listener {
    public static Player pubplayer = null;
    public static ChopTree plugin;
    private static final String[] logs;
    private static final String[] leaves;

    public ChopTreeBlockListener(ChopTree instance) {
        plugin = instance;
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.isCancelled()) {
            Block block = event.getBlock();
            if (Arrays.asList(logs).contains(block.getType().toString())) {
                if (this.denyPermission(event.getPlayer())) {
                    return;
                }

                if (this.denyActive(event.getPlayer())) {
                    return;
                }

                if (this.denyItem(event.getPlayer())) {
                    return;
                }

                event.setCancelled(true);
                if (this.chop(event.getBlock(), event.getPlayer(), event.getBlock().getWorld())) {
                    if (!plugin.moreDamageToTools && this.breaksTool(event.getPlayer(), event.getPlayer().getItemInHand())) {
                        event.getPlayer().getInventory().clear(event.getPlayer().getInventory().getHeldItemSlot());
                    }
                } else {
                    event.setCancelled(false);
                }
            }

        }
    }

    public boolean chop(Block block, Player player, World world) {
        List<Block> blocks = new LinkedList();
        Block highest = this.getHighestLog(block);
        if (this.isTree(highest, player, block)) {
            this.getBlocksToChop(block, highest, blocks);
            if (plugin.logsMoveDown) {
                this.moveDownLogs(block, blocks, world, player);
            } else {
                this.popLogs(block, blocks, world, player);
            }

            return true;
        } else {
            return false;
        }
    }

    public void getBlocksToChop(Block block, Block highest, List<Block> blocks) {
        while(true) {
            if (block.getY() <= highest.getY()) {
                if (!blocks.contains(block)) {
                    blocks.add(block);
                }

                this.getBranches(block, blocks, block.getRelative(BlockFace.NORTH));
                this.getBranches(block, blocks, block.getRelative(BlockFace.NORTH_EAST));
                this.getBranches(block, blocks, block.getRelative(BlockFace.EAST));
                this.getBranches(block, blocks, block.getRelative(BlockFace.SOUTH_EAST));
                this.getBranches(block, blocks, block.getRelative(BlockFace.SOUTH));
                this.getBranches(block, blocks, block.getRelative(BlockFace.SOUTH_WEST));
                this.getBranches(block, blocks, block.getRelative(BlockFace.WEST));
                this.getBranches(block, blocks, block.getRelative(BlockFace.NORTH_WEST));
                if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH))) {
                    this.getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH));
                }

                if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_EAST))) {
                    this.getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_EAST));
                }

                if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.EAST))) {
                    this.getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.EAST));
                }

                if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_EAST))) {
                    this.getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_EAST));
                }

                if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH))) {
                    this.getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH));
                }

                if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_WEST))) {
                    this.getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_WEST));
                }

                if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.WEST))) {
                    this.getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.WEST));
                }

                if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_WEST))) {
                    this.getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_WEST));
                }

                if (block.getData() == 3 || block.getData() == 7 || block.getData() == 11 || block.getData() == 15) {
                    if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH, 2))) {
                        this.getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH, 2));
                    }

                    if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_EAST, 2))) {
                        this.getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_EAST, 2));
                    }

                    if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.EAST, 2))) {
                        this.getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.EAST, 2));
                    }

                    if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_EAST, 2))) {
                        this.getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_EAST, 2));
                    }

                    if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH, 2))) {
                        this.getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH, 2));
                    }

                    if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_WEST, 2))) {
                        this.getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_WEST, 2));
                    }

                    if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.WEST, 2))) {
                        this.getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.WEST, 2));
                    }

                    if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_WEST, 2))) {
                        this.getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_WEST, 2));
                    }
                }

                if (!blocks.contains(block.getRelative(BlockFace.UP)) && Arrays.asList(logs).contains(block.getRelative(BlockFace.UP).getType().toString())) {
                    block = block.getRelative(BlockFace.UP);
                    continue;
                }
            }

            return;
        }
    }

    public void getBranches(Block block, List<Block> blocks, Block other) {
        if (!blocks.contains(other) && Arrays.asList(logs).contains(other.getType().toString())) {
            this.getBlocksToChop(other, this.getHighestLog(other), blocks);
        }

    }

    public Block getHighestLog(Block block) {
        boolean isLog = true;

        while(isLog) {
            if (!Arrays.asList(logs).contains(block.getRelative(BlockFace.UP).getType().toString()) && !Arrays.asList(logs).contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH).getType().toString()) && !Arrays.asList(logs).contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.EAST).getType().toString()) && !Arrays.asList(logs).contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH).getType().toString()) && !Arrays.asList(logs).contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.WEST).getType().toString()) && !Arrays.asList(logs).contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_EAST).getType().toString()) && !Arrays.asList(logs).contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_WEST).getType().toString()) && !Arrays.asList(logs).contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_EAST).getType().toString()) && !Arrays.asList(logs).contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_WEST).getType().toString())) {
                isLog = false;
            } else if (Arrays.asList(logs).contains(block.getRelative(BlockFace.UP).getType().toString())) {
                block = block.getRelative(BlockFace.UP);
            } else if (Arrays.asList(logs).contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH).getType().toString())) {
                block = block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH);
            } else if (Arrays.asList(logs).contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.EAST).getType().toString())) {
                block = block.getRelative(BlockFace.UP).getRelative(BlockFace.EAST);
            } else if (Arrays.asList(logs).contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH).getType().toString())) {
                block = block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH);
            } else if (Arrays.asList(logs).contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.WEST).getType().toString())) {
                block = block.getRelative(BlockFace.UP).getRelative(BlockFace.WEST);
            } else if (Arrays.asList(logs).contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_EAST).getType().toString())) {
                block = block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_EAST);
            } else if (Arrays.asList(logs).contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_WEST).getType().toString())) {
                block = block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_WEST);
            } else if (Arrays.asList(logs).contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_EAST).getType().toString())) {
                block = block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_EAST);
            } else if (Arrays.asList(logs).contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_WEST).getType().toString())) {
                block = block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_WEST);
            }
        }

        return block;
    }

    public boolean isTree(Block block, Player player, Block first) {
        if (!plugin.onlyTrees) {
            return true;
        } else {
            if (plugin.trees.containsKey(player)) {
                Block[] blockarray = (Block[])plugin.trees.get(player);

                for(int counter = 0; counter < Array.getLength(blockarray); ++counter) {
                    if (blockarray[counter] == block) {
                        return true;
                    }

                    if (blockarray[counter] == first) {
                        return true;
                    }
                }
            }

            int counter = 0;
            if (Arrays.asList(leaves).contains(block.getRelative(BlockFace.UP).getType().toString())) {
                ++counter;
            }

            if (Arrays.asList(leaves).contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.UP).getType().toString())) {
                ++counter;
            }

            if (Arrays.asList(leaves).contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH).getType().toString())) {
                ++counter;
            }

            if (Arrays.asList(leaves).contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH).getType().toString())) {
                ++counter;
            }

            if (Arrays.asList(leaves).contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.EAST).getType().toString())) {
                ++counter;
            }

            if (Arrays.asList(leaves).contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.WEST).getType().toString())) {
                ++counter;
            }

            if (Arrays.asList(leaves).contains(block.getRelative(BlockFace.DOWN).getType().toString())) {
                ++counter;
            }

            if (Arrays.asList(leaves).contains(block.getRelative(BlockFace.NORTH).getType().toString())) {
                ++counter;
            }

            if (Arrays.asList(leaves).contains(block.getRelative(BlockFace.EAST).getType().toString())) {
                ++counter;
            }

            if (Arrays.asList(leaves).contains(block.getRelative(BlockFace.SOUTH).getType().toString())) {
                ++counter;
            }

            if (Arrays.asList(leaves).contains(block.getRelative(BlockFace.WEST).getType().toString())) {
                ++counter;
            }

            if (counter >= 2) {
                return true;
            } else {
                if (block.getData() == 1) {
                    block = block.getRelative(BlockFace.UP);
                    if (Arrays.asList(leaves).contains(block.getRelative(BlockFace.UP).getType().toString())) {
                        ++counter;
                    }

                    if (Arrays.asList(leaves).contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.UP).getType().toString())) {
                        ++counter;
                    }

                    if (Arrays.asList(leaves).contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH).getType().toString())) {
                        ++counter;
                    }

                    if (Arrays.asList(leaves).contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH).getType().toString())) {
                        ++counter;
                    }

                    if (Arrays.asList(leaves).contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.EAST).getType().toString())) {
                        ++counter;
                    }

                    if (Arrays.asList(leaves).contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.WEST).getType().toString())) {
                        ++counter;
                    }

                    if (Arrays.asList(leaves).contains(block.getRelative(BlockFace.DOWN).getType().toString())) {
                        ++counter;
                    }

                    if (Arrays.asList(leaves).contains(block.getRelative(BlockFace.NORTH).getType().toString())) {
                        ++counter;
                    }

                    if (Arrays.asList(leaves).contains(block.getRelative(BlockFace.EAST).getType().toString())) {
                        ++counter;
                    }

                    if (Arrays.asList(leaves).contains(block.getRelative(BlockFace.SOUTH).getType().toString())) {
                        ++counter;
                    }

                    if (Arrays.asList(leaves).contains(block.getRelative(BlockFace.WEST).getType().toString())) {
                        ++counter;
                    }

                    if (counter >= 2) {
                        return true;
                    }
                }

                return false;
            }
        }
    }

    public void popLogs(Block block, List<Block> blocks, World world, Player player) {
        Material firstBlockType = block.getType();

        for(Block block1 : blocks) {
            if (!plugin.matchLogType || block1.getType().equals(firstBlockType)) {
                block1.breakNaturally();
            }

            if (plugin.popLeaves) {
                this.popLeaves(block1, firstBlockType);
            }

            if (plugin.moreDamageToTools && this.breaksTool(player, player.getItemInHand())) {
                player.getInventory().clear(player.getInventory().getHeldItemSlot());
                if (plugin.interruptIfToolBreaks) {
                    break;
                }
            }
        }

    }

    public void popLeaves(Block block, Material firstBlockType) {
        for(int y = -plugin.leafRadius; y < plugin.leafRadius + 1; ++y) {
            for(int x = -plugin.leafRadius; x < plugin.leafRadius + 1; ++x) {
                for(int z = -plugin.leafRadius; z < plugin.leafRadius + 1; ++z) {
                    Block target = block.getRelative(x, y, z);
                    if (Arrays.asList(leaves).contains(target.getType().toString()) && (!plugin.matchLogType || this.matchLeafType(target, firstBlockType))) {
                        target.breakNaturally();
                    }
                }
            }
        }

    }

    private boolean matchLeafType(Block leafBlock, Material firstBlockType) {
        Map<Material, Material> leafMatch = new HashMap();
        leafMatch.put(Material.ACACIA_LEAVES, Material.ACACIA_LOG);
        leafMatch.put(Material.BIRCH_LEAVES, Material.BIRCH_LOG);
        leafMatch.put(Material.DARK_OAK_LEAVES, Material.DARK_OAK_LOG);
        leafMatch.put(Material.JUNGLE_LEAVES, Material.JUNGLE_LOG);
        leafMatch.put(Material.OAK_LEAVES, Material.OAK_LOG);
        leafMatch.put(Material.SPRUCE_LEAVES, Material.SPRUCE_LOG);
        leafMatch.put(Material.CHERRY_LEAVES, Material.CHERRY_LOG);
        return ((Material)leafMatch.get(leafBlock.getType())).equals(firstBlockType);
    }

    public void moveDownLogs(Block block, List<Block> blocks, World world, Player player) {
        ItemStack item = new ItemStack(Material.DIRT, 1, (short)0, (Byte)null);
        item.setAmount(1);
        List<Block> downs = new LinkedList();

        for(Block block1 : blocks) {
            Block down = block1.getRelative(BlockFace.DOWN);
            if (down.getType() != Material.AIR && !Arrays.asList(leaves).contains(down.getType().toString())) {
                item.setType(block1.getType());
                item.setDurability((short)block1.getData());
                block1.setType(Material.AIR);
                world.dropItem(block1.getLocation(), item);
                if (plugin.moreDamageToTools && this.breaksTool(player, player.getItemInHand())) {
                    player.getInventory().clear(player.getInventory().getHeldItemSlot());
                }
            } else {
                down.setType(block1.getType());
                block1.setType(Material.AIR);
                downs.add(down);
            }
        }

        for(int counter = 0; counter < downs.size(); ++counter) {
            block = (Block)downs.get(counter);
            if (this.isLoneLog(block)) {
                downs.remove(block);
                block.breakNaturally();
                if (plugin.moreDamageToTools && this.breaksTool(player, player.getItemInHand())) {
                    player.getInventory().clear(player.getInventory().getHeldItemSlot());
                }
            }
        }

        if (plugin.popLeaves) {
            this.moveLeavesDown(blocks, block);
        }

        if (plugin.trees.containsKey(player)) {
            plugin.trees.remove(player);
        }

        if (!downs.isEmpty()) {
            Block[] blockarray = new Block[downs.size()];

            for(int counter = 0; counter < downs.size(); ++counter) {
                blockarray[counter] = (Block)downs.get(counter);
            }

            plugin.trees.put(player, blockarray);
        }
    }

    public void moveLeavesDown(List<Block> blocks, Block firstBlock) {
        List<Block> leaflist = new LinkedList();
        blocks.stream().forEach((block) -> {
            for(int y = -plugin.leafRadius; y < plugin.leafRadius + 1; ++y) {
                for(int x = -plugin.leafRadius; x < plugin.leafRadius + 1; ++x) {
                    for(int z = -plugin.leafRadius; z < plugin.leafRadius + 1; ++z) {
                        if (Arrays.asList(leaves).contains(block.getRelative(x, y, z).getType().toString()) && !leaflist.contains(block.getRelative(x, y, z))) {
                            leaflist.add(block.getRelative(x, y, z));
                        }
                    }
                }
            }

        });
        leaflist.stream().forEach((block) -> {
            if (block.getLocation().getBlockY() <= firstBlock.getLocation().getBlockY() + 2 || !block.getRelative(BlockFace.DOWN).getType().equals(Material.AIR) && !Arrays.asList(leaves).contains(block.getRelative(BlockFace.DOWN).getType().toString())) {
                block.breakNaturally();
            } else {
                block.getRelative(BlockFace.DOWN).setType(block.getType());
                block.setType(Material.AIR);
            }

        });
    }

    public boolean breaksTool(Player player, ItemStack item) {
        if (item != null && this.isTool(item.getType())) {
            short damage = item.getDurability();
            if (this.isAxe(item.getType())) {
                ++damage;
            } else {
                damage = (short)(damage + 2);
            }

            if (damage >= item.getType().getMaxDurability()) {
                return true;
            }

            item.setDurability(damage);
        }

        return false;
    }

    public boolean isTool(Material ID) {
        return ID == Material.WOODEN_AXE || ID == Material.WOODEN_HOE || ID == Material.WOODEN_PICKAXE || ID == Material.WOODEN_SHOVEL || ID == Material.STONE_AXE || ID == Material.STONE_HOE || ID == Material.STONE_PICKAXE || ID == Material.STONE_SHOVEL || ID == Material.IRON_AXE || ID == Material.IRON_HOE || ID == Material.IRON_PICKAXE || ID == Material.IRON_SHOVEL || ID == Material.GOLDEN_AXE || ID == Material.GOLDEN_HOE || ID == Material.GOLDEN_PICKAXE || ID == Material.GOLDEN_SHOVEL || ID == Material.DIAMOND_AXE || ID == Material.DIAMOND_HOE || ID == Material.DIAMOND_PICKAXE || ID == Material.DIAMOND_SHOVEL;
    }

    public boolean isAxe(Material ID) {
        return ID == Material.WOODEN_AXE || ID == Material.STONE_AXE || ID == Material.IRON_AXE || ID == Material.GOLDEN_AXE || ID == Material.DIAMOND_AXE;
    }

    public boolean isLoneLog(Block block) {
        if (Arrays.asList(logs).contains(block.getRelative(BlockFace.UP).getType().toString())) {
            return false;
        } else if (block.getRelative(BlockFace.DOWN).getType() != Material.AIR) {
            return false;
        } else if (this.hasHorizontalCompany(block)) {
            return false;
        } else if (this.hasHorizontalCompany(block.getRelative(BlockFace.UP))) {
            return false;
        } else {
            return !this.hasHorizontalCompany(block.getRelative(BlockFace.DOWN));
        }
    }

    public boolean hasHorizontalCompany(Block block) {
        if (Arrays.asList(logs).contains(block.getRelative(BlockFace.NORTH).getType().toString())) {
            return true;
        } else if (Arrays.asList(logs).contains(block.getRelative(BlockFace.NORTH_EAST).getType().toString())) {
            return true;
        } else if (Arrays.asList(logs).contains(block.getRelative(BlockFace.EAST).getType().toString())) {
            return true;
        } else if (Arrays.asList(logs).contains(block.getRelative(BlockFace.SOUTH_EAST).getType().toString())) {
            return true;
        } else if (Arrays.asList(logs).contains(block.getRelative(BlockFace.SOUTH).getType().toString())) {
            return true;
        } else if (Arrays.asList(logs).contains(block.getRelative(BlockFace.SOUTH_WEST).getType().toString())) {
            return true;
        } else {
            return Arrays.asList(logs).contains(block.getRelative(BlockFace.WEST).getType().toString()) ? true : Arrays.asList(logs).contains(block.getRelative(BlockFace.NORTH_WEST).getType().toString());
        }
    }

    public boolean denyPermission(Player player) {
        return !player.hasPermission("choptree.chop");
    }

    public boolean denyActive(Player player) {
        ChopTreePlayer ctPlayer = new ChopTreePlayer(plugin, player.getName());
        return !ctPlayer.isActive();
    }

    public boolean denyItem(Player player) {
        if (!plugin.useAnything) {
            ItemStack item = player.getItemInHand();
            return !plugin.allowedTools.contains(item.getType());
        } else {
            return false;
        }
    }

    public boolean interruptWhenBreak(Player player) {
        return plugin.interruptIfToolBreaks;
    }

    static {
        logs = new String[]{Material.ACACIA_LOG.toString(), Material.BIRCH_LOG.toString(), Material.DARK_OAK_LOG.toString(), Material.JUNGLE_LOG.toString(), Material.OAK_LOG.toString(), Material.SPRUCE_LOG.toString(), Material.CHERRY_LOG.toString()};
        leaves = new String[]{Material.ACACIA_LEAVES.toString(), Material.BIRCH_LEAVES.toString(), Material.DARK_OAK_LEAVES.toString(), Material.JUNGLE_LEAVES.toString(), Material.OAK_LEAVES.toString(), Material.SPRUCE_LEAVES.toString(), Material.CHERRY_LEAVES.toString()};
    }
}

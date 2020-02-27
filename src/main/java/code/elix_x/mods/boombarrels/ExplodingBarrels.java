package code.elix_x.mods.boombarrels;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.BlockItem;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber
public class ExplodingBarrels {

	public static final String MODID = "boombarrels";

	private static final Logger LOGGER = LogManager.getLogger();

	/*
	 * Things static deferred registering
	 */

	public static final String BOOMBARRELID = "exploding_barrel";

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, MODID);
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, MODID);

	public static final RegistryObject<ExplodingBarrelBlock> expBarrel = BLOCKS.reg(BOOMBARRELID, () -> new ExplodingBarrelBlock(true, false));
	
	static {
		ITEMS.register(BOOMBARRELID, () -> new BlockItem(expBarrel, new Item.Properties().group(ItemGroup.DECORATIONS)));
	}

	public ExplodingBarrels(){
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		modBus.addListener(this::setup);
		modBus.addListener(this::clientSetup);
		BLOCKS.register(modBus);
		ITEMS.register(modBus);
		MinecraftForge.EVENT_BUS.register(this);
	}

	/*
	 * Forge Events
	 */

	private void setup(final FMLCommonSetupEvent event){
		
	}

	private void clientSetup(final FMLClientSetupEvent event){
		
	}

	public static class ExplodingBarrelBlock extends Block {

		protected boolean boomProjectile;
		protected boolean boomMelee;

		public ExplodingBarrelBlock(boolean boomProjectile, boolean boomMelee){
			super(Block.Properties.create(Material.WOOD)); //TODO
			this.boomProjectile = boomProjectile;
			this.boomMelee = boomMelee;
		}

		//TODO

	}

}
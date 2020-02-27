package code.elix_x.mods.boombarrels;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.relauncher.Side;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber
public class ExplodingBarrels {

	public static final String MODID = "boombarrels";

	private static final Logger LOGGER = LogManager.getLogger();

	public static final ResourceLocation BOOMBARREL = new ResourceLocation(MODID, "exploding_barrel");

	@Instance(MODID)
	public static ExplodingBarrels INSTANCE;

	public ExplodingBarrels(){
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public List<Block> blocks = new ArrayList<>();
	public List<Item> items = new ArrayList<>();

	/*
	 * Initialization a-la once and for all (?)
	 * Basically initializes once under any circumstance bypassing all possible multi-threadedness
	 */

	private boolean initialized = false;
	private final Semaphore initSemaphore = new Semaphore(1);
	private static final INITCALLSC = 3;

	private void initialize(){
		initSemaphore.acquire();
		if(initialized) return;
		//Actual Initialization
		Block expBarrel = new ExplodingBarrelBlock(true, false).setRegistryName(BOOMBARREL);
		blocks.add(expBarrel);
		items.add(new ItemBlock(expBarrel).setRegistryName(BOOMBARREL));
		//Initialization complete
		initialized = true;
		initSemaphore.release(INITCALLSC - 1);
	}

	/*
	 * Forge Events
	 */

	private void setup(final FMLCommonSetupEvent event){
		initialize()
	}

	private void clientSetup(final FMLClientSetupEvent event){
		
	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event){
		initialize()
		blocks.forEach(event.getRegistry()::register);
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event){
		initialize()
		items.forEach(event.getRegistry()::register);
	}

	public static class ExplodingBarrelBlock extends Block {

		protected boolean boomProjectile;
		protected boolean boomMelee;

		public ExplodingBarrelBlock(boomProjectile, boomMelee){
			super(Material.WOOD);
			this.boomProjectile = boomProjectile;
			this.boomMelee = boomMelee;
			//TODO
			setCreativeTab(CreativeTabs.DECORATIONS);
		}

		//TODO

	}

	@Mod.EventBusSubscriber(modid = MODID, value = Side.CLIENT)
	public static class ClientModelRegister {

		@SubscribeEvent
		public static void registerModels(ModelRegistryEvent event){
			//TODO?
		}

	}

}
package minecrafttransportsimulator.blocks.tileentities.instances;

import minecrafttransportsimulator.baseclasses.Point3d;
import minecrafttransportsimulator.mcinterface.WrapperNBT;
import minecrafttransportsimulator.mcinterface.WrapperWorld;

/**Radio tile entity.
 *
 * @author don_bruce
 */
public class TileEntityRadio extends TileEntityDecor{
	
	public TileEntityRadio(WrapperWorld world, Point3d position, WrapperNBT data){
		super(world, position, data);
		//Set position here as we don't tick so the radio won't get update() calls.
		radio.position.setTo(position);
	}

	@Override
	public boolean hasRadio(){
		return true;
	}
}

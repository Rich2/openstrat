package ostrat

/** A skeleton package which may remain as a skeleton for some time. It has been included because 3d is an integral part of Openstrat's
 *  design. This package will depend on geom, pDisp and probably pGrid. However the 3d will implement a fundamentally 2d interface. Most of the 
 *  geographical maps are essentially 2d although they contain altitude data. Exceptions to this include the dungeon where a multi
 *  level dungeon might be wanted. The 3d will also be required to implement the 2d data display and the commands/ control functionality
 *  of the 2d abstract interface. There will be no "physics engine" in the api, lathugh it will include basci mechanics and posssible other
 *  physics types and functionality.
 *  
 *  Note there is no skeleton Wayland package, because this will be its own native sub project */
package object p3d
{
  
}
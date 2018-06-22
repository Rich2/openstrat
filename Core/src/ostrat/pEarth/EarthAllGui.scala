/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth

/** Not sure if this class is necessary */
abstract class EarthAllGui extends EarthGui
{
   val useless: Boolean = true   
  
  mapPanel.mouseUp = (v, b, s) => selected = s.headOption.fold[List[AnyRef]](Nil)(h => List((h, v)))
 // mapPanel.fMouseDragged = (v, b, s) => { selectedObj.foreach(_ => println("Drag"))}
}

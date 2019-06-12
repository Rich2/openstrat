/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth

/** Not sure if this class is necessary. A class for whole earth maps as opposed to for example, Old World maps.*/
abstract class EarthAllGui(title: String) extends EarthGui(title)
{
  val useless: Boolean = true   
  
  mapPanel.mouseUp = (v, b, s) => selected = s.headOption.fold[Arr[AnyRef]](Arr())(h => Arr((h, v)))
 // mapPanel.fMouseDragged = (v, b, s) => { selectedObj.foreach(_ => println("Drag"))}
}

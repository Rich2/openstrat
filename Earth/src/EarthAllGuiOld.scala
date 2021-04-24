/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth

/** Not sure if this class is necessary. A class for whole earth maps as opposed to for example, Old World maps.*/
abstract class EarthAllGuiOld(title: String) extends EarthGuiOld(title)
{
  val useless: Boolean = true   
  
  mapPanel.mouseUp = (b, s, v) => selected = s //.fHeadElse(Arr(_), Arr())// eadOption.fold[Arr[AnyRef]](Arr())(h => Arr((h, v)))
 // mapPanel.fMouseDragged = (v, b, s) => { selectedObj.foreach(_ => println("Drag"))}
}

/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pgui
import geom._

/** So currently this is a turn based class allowing the selection of objects within the map. That is not the case for the Planet App where the
 *  planets move too quickly for selection. The focus centre is un fixed unlike the planet App. */
abstract class UnfixedMapGui(title: String) extends MapGui(title)
{
  var selected: Anys = Anys()
  def inCmd: MouseCmd  
  def outCmd: MouseCmd
  def leftCmd: MouseCmd  
  def rightCmd: MouseCmd
  def downCmd: MouseCmd    
  def upCmd: MouseCmd 
   
  val bIn: PolygonCompound = clickButton("+")(inCmd)
  val bOut: PolygonCompound = clickButton("-")(outCmd)
  val bLeft: PolygonCompound = clickButton("Lt")(leftCmd)
  val bUp: PolygonCompound = clickButton("Up")(upCmd)
  val bDown: PolygonCompound = clickButton("Dn")(downCmd)
  val bRight: PolygonCompound = clickButton("Rt")(rightCmd)
   
  val guButs: Arr[PolygonCompound] = Arr(bIn, bOut, bLeft, bRight, bDown, bUp)
}
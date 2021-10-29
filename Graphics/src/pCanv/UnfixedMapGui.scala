/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCanv
import geom._

/** So currently this is a turn based class allowing the selection of objects within the map. That is not the case for the Planet App 
 *  where the planets move too quickly for selection. The focus centre is un fixed unlike the planet App. */
abstract class UnfixedMapGui(title: String) extends MapGui(title)
{
  var selected: List[AnyRef] = Nil
  def inCmd: MouseCmd  
  def outCmd: MouseCmd
  def leftCmd: MouseCmd  
  def rightCmd: MouseCmd
  def downCmd: MouseCmd    
  def upCmd: MouseCmd 
   
  val bIn = clickButtonOld("+", inCmd)   
  val bOut = clickButtonOld("-", outCmd)   
  val bLeft = clickButtonOld("Lt", leftCmd)
  val bUp = clickButtonOld("Up", upCmd)
  val bDown = clickButtonOld("Dn", downCmd)
  val bRight = clickButtonOld("Rt", rightCmd)
   
  val guButs: Arr[GraphicActiveSim] = Arr(bIn, bOut, bLeft, bRight, bDown, bUp)
}
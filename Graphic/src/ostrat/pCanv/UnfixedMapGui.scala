/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCanv

/** So currently this is a turn based class allowing the selection of objects within the map. That is not the case for the Planet App 
 *  where the planets move too quickly for selection. The focus centre is un fixed unlike the planet App. */
abstract class UnfixedMapGui(title: String) extends MapGui(title)
{
  var selected: List[AnyRef] = Nil
  def inCmd: MouseButton => Unit  
  def outCmd: MouseButton => Unit
  def leftCmd: MouseButton => Unit  
  def rightCmd: MouseButton => Unit  
  def downCmd: MouseButton => Unit    
  def upCmd: MouseButton => Unit 
   
  val bIn = clickButton("+", inCmd)   
  val bOut = clickButton("-", outCmd)   
  val bLeft = clickButton("\u2B05", leftCmd)   
  val bUp = clickButton("\u2B06", upCmd)   
  val bDown = clickButton("\u2B07", downCmd)   
  val bRight = clickButton("\u27A1", rightCmd)
   
  val guButs = Arr(bIn, bOut, bLeft, bRight, bDown, bUp)
}
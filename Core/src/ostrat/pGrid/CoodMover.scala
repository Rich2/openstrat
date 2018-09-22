/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid

trait CoodMover
{ var xCood: Int = 0
  var yCood: Int = 0
  def cood: Cood = Cood(xCood, yCood) 
  def cood_= (newCood: Cood): Unit = { xCood = newCood.x; yCood = newCood.y }
}
/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid

trait CoodMover
{ var xCood: Int
  var yCood: Int
  def cood: Cood = Cood(xCood, yCood) 
  def cood_= (newCood: Cood): Unit = { xCood = newCood.xi; yCood = newCood.yi }
  def setCood(newX: Int, newY: Int): Unit = { xCood = newX; yCood = newY }
}
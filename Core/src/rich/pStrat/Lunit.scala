/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package pStrat
import pGrid._

trait Lunit
{  var x: Int
   var y: Int
   def cood: Cood = Cood(x, y)
   def cood_= (newValue: Cood): Unit = { x = newValue.x; y = newValue.y }
   def setCood(xI: Int, yI: Int): Unit = { x = xI; y = yI }
   var dirn: HexDirn = HCen
   var offsetMagnitude: Double = 0
}
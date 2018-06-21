/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package pDisp
import geom._

trait RectangleGui extends MapGui//EuclidGui
{
   def rectMap: RectMap
   //def scale: Double
   
//   val mapLeft: Dist// = -10.km
//   val mapBottom: Dist// = -10.km
//   val mapRight: Dist// = 10.km
//   val mapTop: Dist// = 10.km
//   def mapWidth: Dist = mapRight - mapLeft
//   def mapHeight: Dist = mapTop - mapBottom
//   def xMapCen: Dist = (mapRight + mapLeft) / 2
//   def yMapCen: Dist = (mapTop + mapBottom) / 2
   //def mapCen: Dist2 = Dist2(xMapCen, yMapCen)
   
   //   val bFocusLeft = button1("\u2190", () => { adjFocus(-Vec2(moveInc, 0).antiMapRotate); repaintMap })
//   val bFocusRight = button1("\u2192", () => { adjFocus( Vec2(moveInc, 0).antiMapRotate); repaintMap })
//   val bFocusUp = button1("\u2191", () => { adjFocus(Vec2(0 , moveInc).antiMapRotate); repaintMap })
//   val bFocusDown = button1("\u2193", () => { adjFocus(- Vec2(0, moveInc).antiMapRotate); repaintMap })
   
}
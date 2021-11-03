/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pStrat
import geom._, pgui._

case class ColourGen(canv: CanvasPlatform) extends CanvasNoPanels("Colour Generator")
{
  var line = 0
  val n = 2
  
  def intMaker(i: Int): Int =
  { val fac = i.toDouble / n.toDouble
   (255 * fac).toInt
  }
  
  val cols: Arr[(PolygonCompound, TextGraphic)] = ijkToMap(0, n)(0, n)(0, n) { (r, g, b) =>
    val r1 = intMaker(r)
    val g1 = intMaker(g)
    val b1 = intMaker(b)
    def c1 = Colour.fromInts(r1, g1, b1)
    val c2 = Rect.colouredBordered(25, c1, 2).slateXY(left + 30, top - 20)
    def c3 = TextGraphic(commaedInts(r1, g1, b1), 15, left + 120 pp top - 20)
    (c2, c3)
  }
   
  val cols2 = cols.iFlatMap{ (pair, i) =>
    val offset = - 35 * i
    Arr(pair._1.slateY(offset), pair._2.slateY(offset))
  }
  repaint(cols2)
}
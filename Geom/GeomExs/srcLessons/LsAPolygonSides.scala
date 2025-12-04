/* Copyright 2018-24 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, Colour._

object LsAPolygonSides extends LessonStatic
{ override val title: String = "Polygon Sides"
  override def bodyStr: String = "Polygon sides."
  val hex: HexXlign = HexXlign(400)
  val hd: PolygonDraw = hex.draw()

  val hex2 = hex.vertsMultiply(4)
  val ccs = hex2.vertsMap(Circle.d(15, _).fill(Violet))

  val sides = hex.slateX(500).sides.iMap{ (i, ls) => ls.draw(2, ColourArr.rainbow(i)) }
  override def output: GraphicElems = RArr(hd) ++ sides ++ ccs
}

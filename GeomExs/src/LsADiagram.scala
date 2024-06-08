/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._

object LsADiagram extends GraphicsAE
{
  override def title: String = "Diagram"

  override def bodyStr: String = "Rectangle Diagrams"

  val rect = Rect(300, 200)
  val cen0 = -0 pp -400
  val x1 = 400
  val y2 = -100
  val cen1 = -x1 pp y2
  val arr1 = Arrow(cen0, cen1)
  val cen2 = x1 pp y2
  val arr2 = Arrow(cen0, cen2)

  override def output: GraphicElems = RArr(rect.slate(cen0).draw(), rect.slate(cen1).draw(), rect.slate(cen2).draw()) ++ arr1 ++ arr2
}
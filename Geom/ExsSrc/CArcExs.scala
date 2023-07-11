/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pgui._, Colour._

/** This is a temporary lesson: whilst Arcs get fixed. */
case class CArcExs(canv: CanvasPlatform) extends CanvasNoPanels("Arc Test")
{
  val myStuff: GraphicElems = iToFlatMap(374) { index =>
    val x = -600 + (index % 25) * 50
    val y = 325 - (index / 25) * 45

    val arcPos =
    { val radius = 20.0
      val longArcOffset = 0.025
      /** Angle of arc */
      val theta = Pi1 / 180 * index + longArcOffset
      val startPoint = x + radius * math.cos(longArcOffset) pp y + radius * math.sin(longArcOffset)
      val endPoint = x + radius * math.cos(theta + longArcOffset) pp y + radius * math.sin(theta + longArcOffset)
      CArc.pos(startPoint, x pp y, endPoint).draw(DeepSkyBlue)
    }

    val arcNeg =
    {
      val radius = 15.0
      val delta = Pi1/8
      val shortArcOffset = Pi1/360
      /** Angle of arc. */
      val theta = shortArcOffset + Pi1 / 180 * index
      val startPoint = x + radius * math.cos(theta) pp y + radius * math.sin(theta)
      val endPoint = x + radius * math.cos(theta + delta + delta) pp y + radius * math.sin(theta + delta + delta)
      CArc.neg(startPoint, x pp y, endPoint).draw(Orange)
    }

    RArr(arcPos, arcNeg, index.xyTextGraphic(12, x, y, Black))
  }

  repaint(myStuff)
}

//def getPointOnCircle(origin: pt2, radius: Angle)
// CArcDrawOld(x pp y, 0 pp 0, 0 pp 250)
// CArc3(x pp y, -141.421356237 pp 141.421356237, 0 pp 200).draw(Crimson)
// Arr(CArcDraw(CArc(x pp y, x+radius pp y, endAngle), 2, Blue))
//   var myStuff: GraphicElems = ijToMap(1, 1)(0,15) { (i, j) =>
//     val x = -400 + 25 + j * 50; // x coordinate
//     val y = 25 + i * 50; // y coordinate
//     val arcAngle = 0.1 + Pi/8*j; // angle of arc
//     //val origin = x pp y
//     val startPoint = x+radius pp y
//     val apex = x+radius*math.cos(arcAngle/2) pp y+radius*math.sin(arcAngle/2)
//     val endPoint = x+radius*math.cos(arcAngle) pp y+radius*math.sin(arcAngle)

//     CArc(startPoint, apex, endPoint).draw(DeepSkyBlue)

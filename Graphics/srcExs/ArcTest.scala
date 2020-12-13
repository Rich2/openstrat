package learn
import ostrat._, geom._, pCanv._, Colour._, scala.math.{Pi}

/** This is a temporary lesson: whilst Arcs get fixed. */
case class ArcTest(canv: CanvasPlatform) extends CanvasNoPanels("Arc Test")
{
  var radius = 20.0
  //var arcOrigin = Pt(0,0)
  val longArcOffset = 0.025
  val myStuff: GraphicElems = iToFlatMap(0, 374) { index =>
    val x = -600 + (index % 25) * 50
    val y = 325 - (index / 25) * 45
    val theta = Pi1/180*index+longArcOffset; // angle of arc
    val startPoint = x+radius*math.cos(longArcOffset) pp y+radius*math.sin(longArcOffset)
    val apex = x+radius*math.cos(theta/2+longArcOffset) pp y+radius*math.sin(theta/2+longArcOffset)
    val endPoint = x+radius*math.cos(theta+longArcOffset) pp y+radius*math.sin(theta+longArcOffset)
    val myArc = CArc3(startPoint, apex, endPoint)
    deb(index.toString+" s="+myArc.startDegs.toString+" a="+myArc.deltaDegs.toString)
    //deb(index.toString+" theta =" + theta + " startAngle="+myArc.startAngle)
    //deb(index.toString+" theta =" + theta + " angle="+myArc.angle)
    /** myStuff = myStuff ++ */ Arr(myArc.draw(DeepSkyBlue), TextGraphic(index.toString, x pp y, 12, Black))
  }
  val delta = Pi1/8

  radius = 15.0
  val shortArcOffset = Pi1/360

  var myStuff2: GraphicElems = iToMap(0, 374) { index =>
    val x = -600 + (index % 25) * 50
    val y = 325 - (index / 25) * 45
    val theta = shortArcOffset+Pi1/180*index; // angle of arc
    val startPoint = x+radius*math.cos(theta) pp y+radius*math.sin(theta)
    val apex = x+radius*math.cos(theta+delta) pp y+radius*math.sin(theta+delta)
    val endPoint = x+radius*math.cos(theta+delta+delta) pp y+radius*math.sin(theta+delta+delta)
    val myArc = CArc3(startPoint, apex, endPoint)
    deb(index.toString+" s="+myArc.startDegs.toString+" a="+myArc.deltaDegs.toString)
    myArc.draw(Orange)
  }
  repaint(myStuff ++ myStuff2)
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

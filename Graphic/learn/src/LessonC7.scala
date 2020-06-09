package learn
import ostrat._, geom._, pCanv._, Colour._
import scala.collection.mutable.Map

//** This lesson displays an interactive Bezier curve whose points can be dragged and also displays the syntax required to draw it 
case class LessonC7(canv: CanvasPlatform) extends CanvasNoPanels("Lesson C7: Exploring Beziers")
{ // defines the size of the circles that represent the points of the bezier
  val circleRadius = 5

  // bp (bezier points) holds the points that define the bezier to be displayed
  var bp:Map[String, Vec2] = Map("p1" -> Vec2(-50, 0), "p2" -> Vec2(50, 0), "c1" -> Vec2(-50, -125), "c2" -> Vec2(50, 25))

  // when one of the bezier points is being dragged, this will hold its corresponding key in bp
  var theDragee = "" 
  
  drawBezier()

  def drawBezier():Unit = 
  { val pt1 = Circle(circleRadius, bp("p1")).fill(Red) // the start point is represented as a red circle on screen
    val pt2 = Circle(circleRadius, bp("p2")).fill(Red) // the end point is represented as a red circle on screen
    val cp1 = Circle(circleRadius, bp("c1")).fill(Gray)// the control point for start point is represented as a grey circle on screen
    val cp2 = Circle(circleRadius, bp("c2")).fill(Grey)// the control point for end point is represented as a grey circle on screen
    val cl1 = LineDraw(bp("p1"), bp("c1"), 1, Grey)    // line between the start point and its control point
    val cl2 = LineDraw(bp("p2"), bp("c2"), 1, Grey)    // line between the end point and its control point

    val bez = BezierDraw(bp("p1"), bp("c1"), bp("c2"), bp("p2"), 2, Green) // the bezier to be displayed

    // this holds the syntax required to draw the current bezier (bez) (NB: replace ; with , )
    val txt = TextGraphic("BezierDraw(" + bp("p1") + ", " + bp("c1") + ", " + bp("c2") + ", " + bp("p2") + ", 2, Green)", 18, 0 vv 300)

    val elementsToPaint = Arr(txt, pt1, pt2, cp1, cp2, cl1, cl2, bez)

    repaint(elementsToPaint)
  }

  // update the dragged bezier point with its new position and then redraw the screen
  def dragging(newPosition:Vec2):Unit = if (theDragee != "") 
  { bp(theDragee) = newPosition
    drawBezier()
  }

  // test to see if drag operation has started
  canv.mouseDown = (position, button) => 
  { // if the mouseDown is on one of the represented bezier points then set theDragee to its corresponding key in bp
    if ((bp("c1") - position).magnitude <= circleRadius) theDragee = "c1"
    else if ((bp("c2") - position).magnitude <= circleRadius) theDragee = "c2"
    else if ((bp("p1") - position).magnitude <= circleRadius) theDragee = "p1"
    else if ((bp("p2") - position).magnitude <= circleRadius) theDragee = "p2"
    else theDragee = ""
    dragging(position)
  }

  // update the bezier when a point is being dragged
  canv.mouseDragged = (position, button) => dragging(position)

  mouseUp = (button, clickList, position) =>
  { dragging(position)    
    theDragee = "" // dragging has finished so reset theDragee
  }
}

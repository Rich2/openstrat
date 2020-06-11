package learn
import ostrat._, geom._, pCanv._, Colour._

/** This lesson displays an interactive Bezier curve whose points can be dragged and also displays the syntax required to draw it */
case class LessonC7(canv: CanvasPlatform) extends CanvasNoPanels("Lesson C7: Exploring Beziers")
{ /** defines the size of the circles that represent the points of the bezier */
  val circleRadius = 20
  case class Drag(var v: Vec2)
  
  /** the bezier points */
  val p1 = Drag(-100 vv 0) /** startr point */
  val p2 = Drag(100 vv  0) /** end point */
  val c1 = Drag(-100 vv -250) /** control point for start point */
  val c2 = Drag(100 vv 50)    /** control point for end point */

  /** when one of the bezier points is being dragged, this will indicate which */
  var theDragee: Option[Drag] = None 
  
  drawBezier()

  def drawBezier():Unit = 
  { val cf1 = Circle(circleRadius, p1.v).fill(Red) /** the start point is represented as a red circle on screen */
    val cf2 = Circle(circleRadius, p2.v).fill(Red) /** the end point is represented as a red circle on screen */
    val cp1 = Circle(circleRadius, c1.v).fill(Gray)/** the control point for start point is represented as a grey circle on screen */
    val cp2 = Circle(circleRadius, c2.v).fill(Grey)/** the control point for end point is represented as a grey circle on screen */
    val cl1 = LineDraw(p1.v, c1.v, 1, Grey)    /** line between the start point and its control point */
    val cl2 = LineDraw(p2.v, c2.v, 1, Grey)    /** line between the end point and its control point */

    val bez = BezierDraw(p1.v, c1.v, c2.v, p2.v, 2, Green) /** the bezier to be displayed */

    /** this holds the syntax required to draw the current bezier (bez) (NB: replace ; with , ) */
    val txt = TextGraphic("BezierDraw(" + p1.v + ", " + c1.v + ", " + c2.v + ", " + p2.v + ", 2, Green)", 18, 0 vv 300)

    val elementsToPaint = Arr(txt, cf1, cf2, cp1, cp2, cl1, cl2, bez)

    repaint(elementsToPaint)
  }

  /** test to see if drag operation has started */
  /** if the mouseDown is on one of the represented bezier points then set theDragee to its corresponding option */
  canv.mouseDown = (position, button) => 
  { val points = Array(p1, p2, c1, c2)
    theDragee = points.find(i => (i.v - position).magnitude <= circleRadius)
  }

  /** when a point is being dragged update the correspondin bezier point with its new position and then redraw the screen */
  canv.mouseDragged = (position, button) => theDragee match { case Some(drag) => drag.v = position; drawBezier() }

  /** dragging has finished so reset theDragee */
  mouseUp = (button, clickList, position) => theDragee = None 
}

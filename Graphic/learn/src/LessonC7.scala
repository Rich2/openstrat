package learn
import ostrat._, geom._, pCanv._, Colour._

/** This lesson displays an interactive Bezier curve whose points can be dragged and also displays the syntax required to draw it */
case class LessonC7(canv: CanvasPlatform) extends CanvasNoPanels("Lesson C7: Exploring Beziers")
{ /** defines the size of the circles that represent the points of the bezier */
  val circleRadius = 10
  case class DragCircle(var loc: Vec2, color: Colour)
  
  /** start point bezier. */
  val startPoint = DragCircle(-100 vv 0, Red)
  /** End point of bezier curve. */
  val endPoint = DragCircle(100 vv  0, Red)
  /** control point for start point */
  val controlStart = DragCircle(-100 vv -250, Gray)
  /** control point for end point */
  val controlEnd = DragCircle(100 vv 50, Gray) 
  
  val bezierPoints = Arr(startPoint, endPoint, controlStart, controlEnd)

  /** when one of the bezier points is being dragged, this will indicate which */
  var theDragee: Option[DragCircle] = None 
  
  drawBezier()

  def drawBezier():Unit =
  { val dragCircles = bezierPoints.map(dr => Circle(circleRadius, dr.loc).fill(dr.color))

    val startControlLine = LineDraw(startPoint.loc, controlStart.loc, 1, Grey)    /** line between the start point and its control point */
    val endControlLine = LineDraw(endPoint.loc, controlEnd.loc, 1, Grey)    /** line between the end point and its control point */

    val bezier = BezierDraw(startPoint.loc, controlStart.loc, controlEnd.loc, endPoint.loc, 2, Green) /** the bezier to be displayed */

    /** this holds the syntax required to draw the current bezier  (NB: replace ; with , ) */
    val txt = TextGraphic("BezierDraw(" + startPoint.loc + ", " + controlStart.loc + ", " + controlEnd.loc + ", " + endPoint.loc + ", 2, Green)", 18, 0 vv 300)
    
    val elementsToPaint = dragCircles ++ Arr(txt, startControlLine, endControlLine, bezier)

    repaint(elementsToPaint)
  }

  /* test to see if drag operation has started. if the mouseDown is on one of the represented bezier points then set theDragee to its corresponding
   option */
  canv.mouseDown = (position, _) => theDragee = bezierPoints.find(i => (i.loc - position).magnitude <= circleRadius)

  // When a point is being dragged update the correspondin bezier point with its new position and then redraw the screen. */
  canv.mouseDragged = (position, button) => theDragee match
  { case Some(drag) if (1 == 1) => drag.loc = position; drawBezier()
    case Some(drag) => drag.loc = position; drawBezier()
    case _ => theDragee = None
  }

  /** dragging has finished so reset theDragee */
  mouseUp = (button, clickList, position) => theDragee = None 
}

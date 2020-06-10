package learn
import ostrat._, geom._, pCanv._, Colour._
import scala.collection.mutable.Map

//** This lesson displays an interactive Bezier curve whose points can be dragged and also displays the syntax required to draw it 
case class LessonC7(canv: CanvasPlatform) extends CanvasNoPanels("Lesson C7: Exploring Beziers")
{ // defines the size of the circles that represent the points of the bezier
  val circleRadius = 20
  case class Drag(var v: Vec2)
  
  val p1 = Drag(-100 vv 0)
  val p2 = Drag(100 vv  0)
  val c1 = Drag(-100 vv -250)
  val c2 = Drag(100 vv 50)
  // bp (bezier points) holds the points that define the bezier to be displayed
  //var bp:Map[String, Vec2] = Map(", ", , )

  // when one of the bezier points is being dragged, this will hold its corresponding key in bp
  var theDragee: Option[Drag] = None 
  
  drawBezier()

  def drawBezier():Unit = 
  { val cf1 = Circle(circleRadius, p1.v).fill(Red) // the start point is represented as a red circle on screen
    val cf2 = Circle(circleRadius, p2.v).fill(Red) // the end point is represented as a red circle on screen
    val cp1 = Circle(circleRadius, c1.v).fill(Gray)// the control point for start point is represented as a grey circle on screen
    val cp2 = Circle(circleRadius, c2.v).fill(Grey)// the control point for end point is represented as a grey circle on screen
    val cl1 = LineDraw(p1.v, c1.v, 1, Grey)    // line between the start point and its control point
    val cl2 = LineDraw(p2.v, c2.v, 1, Grey)    // line between the end point and its control point

    val bez = BezierDraw(p1.v, c1.v, c2.v, p2.v, 2, Green) // the bezier to be displayed

    // this holds the syntax required to draw the current bezier (bez) (NB: replace ; with , )
    val txt = TextGraphic("BezierDraw(" + p1 + ", " + c1 + ", " + c2 + ", " + "p2" + ", 2, Green)", 18, 0 vv 300)

    val elementsToPaint = Arr(txt, cf1, cf2, cp1, cp2, cl1, cl2, bez)

    repaint(elementsToPaint)
  }

  // update the dragged bezier point with its new position and then redraw the screen
  def dragging(newPosition:Vec2):Unit = theDragee match 
  { case Some(drag) => { drag.v = newPosition; drawBezier() }
    case _ =>  
  }

  // test to see if drag operation has started
  // if the mouseDown is on one of the represented bezier points then set theDragee to its corresponding key in bp
  canv.mouseDown = (position, button) => 
  { position match
    { case p if (c1.v - p).magnitude <= circleRadius => theDragee = Some(c1)
      case p if (c2.v - position).magnitude <= circleRadius => theDragee = Some(c2)
      case p if (p1.v - position).magnitude <= circleRadius => theDragee = Some(p1)
      case p if (p2.v - position).magnitude <= circleRadius => theDragee = Some(p2)
      case _ => theDragee = None
    }
    dragging(position)
  }

  // update the bezier when a point is being dragged
  canv.mouseDragged = (position, button) => dragging(position)

  mouseUp = (button, clickList, position) =>
  { dragging(position)    
    theDragee = None // dragging has finished so reset theDragee
  }
}

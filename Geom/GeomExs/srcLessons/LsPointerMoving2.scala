/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, geom.*, pgui.*, Colour.*

object LsPointerMoving2 extends LessonGraphics
{ override def title: String = "Pointer moving 2"

  override def bodyStr: String = """Pointer mvoing 2."""

  override def canv: CanvasPlatform => Any = LsC8Canv(_)

/** This lesson displays an interactive Bézier curve whose points can be dragged and also displays the syntax required to draw it */
case class LsC8Canv(canv: CanvasPlatform) extends CanvasNoPanels("Lesson C8: More shape dragging.")
{ /** defines the size of the circles that represent the points of the bezier */
  val circleRadius = 15
  class Drag(x: Double, y: Double, val c: Colour)
  { var loc: Pt2 = Pt2(x, y)
  }
  
  /** start point bezier. */
  val p1 = Drag(-100, 0, Gray)
  /** End point of bezier curve. */
  val p2 = Drag(100,  0, Gray)
  /** control point for start point */
  val c1 = Drag(-100, -250, Red)
  /** control point for end point */
  val c2 = Drag(100, 50, Red)
  
  val cCen = Drag(0, 150, Violet)
  val cRad = Drag(-70, 150, Orange)
  
  val pts = RArr(p1, p2, c1, c2, cCen, cRad)

  /** when one of the bezier points is being dragged, this will indicate which */
  var theDragee: Option[Drag] = None 
  
  drawStuff()

  def drawStuff():Unit = 
  { val cds = pts.map(dr => Circle.d(circleRadius, dr.loc).fill(dr.c))
    
    val cl1 = LSeg2Draw(p1.loc, c1.loc, 2, Grey)    /** line between the start point and its control point */
    val cl2 = LSeg2Draw(p2.loc, c2.loc, 2, Grey)    /** line between the end point and its control point */

    /** this holds the syntax required to draw the current bezier (bez) (NB: replace ; with , ) */
    //val txt = TextGraphic("BezierDraw(" + p1.v + ", " + c1.v + ", " + c2.v + ", " + p2.v + ", 2, Green)", 18, 0 vv 300)
    val circ = Circle.d(cRad.loc.distTo(cCen.loc), cCen.loc).draw(2.0, Blue)
    val ell = Ellipse(200, 100).fill(SeaGreen)

    val elementsToPaint = cds ++ RArr(cl1, cl2, ell, circ, ell)

    repaint(elementsToPaint)
  }

  /* test to see if drag operation has started. if the mouseDown is on one of the represented bezier points then set theDragee to its corresponding
   option */
  canv.mouseDown = (position, _) => theDragee = pts.find(i => position.distTo(i.loc) <= circleRadius)

  // When a point is being dragged update the correspondin bezier point with its new position and then redraw the screen. */
  canv.mouseDragged = (position, button) => theDragee match
  { case Some(drag) => drag.loc = position; drawStuff()
    case _ => theDragee = None
  }

  /** dragging has finished so reset theDragee */
  mouseUp = (button, clickList, position) => theDragee = None 
}}
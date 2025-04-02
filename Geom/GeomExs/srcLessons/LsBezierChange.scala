/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, geom.*, pgui.*, Colour.*

object LsBezierChange extends LessonGraphics
{ override def title: String = "Bezier change"
  override def bodyStr: String = """Bezier change."""

  override def canv: CanvasPlatform => Any = LsC7Canv(_)

  /** This lesson displays an interactive Bézier curve whose points can be dragged and also displays the syntax required to draw it */
  case class LsC7Canv(canv: CanvasPlatform) extends CanvasNoPanels("Lesson C7: Exploring Beziers") {
    /** defines the size of the circles that represent the points of the bezier */
      val circleRadius = 10

    class DragCircle(xIn: Double, yIn: Double, val color: Colour)
    { var loc: Pt2 = Pt2(xIn, yIn)
    }

    /** start point bezier. */
    val startPoint = DragCircle(-250, 0, Red)

    /** End point of Bézier curve. */
    val endPoint = DragCircle(-50, 0, Red)

    /** control point for start point */
    val controlStart = DragCircle(-250, -250, Gray)

    var controlStartOffset = Vec2(0, -250)

    /** control point for end point */
    val controlEnd = DragCircle(-50, 150, Gray)

    var controlEndOffset = Vec2(0, 150)

    val bezierPoints = RArr(startPoint, endPoint, controlStart, controlEnd)

    val quadraticStart = DragCircle(50, 0, Red)
    val quadraticEnd = DragCircle(150, 0, Red)
    val quadraticControl = DragCircle(100, -150, Gray)
    val quadraticBezierPoints = RArr(quadraticStart, quadraticEnd, quadraticControl)

    /** when one of the bezier points is being dragged, this will indicate which */
    var theDragee: Option[DragCircle] = None

    val allBezierPoints = RArr(startPoint, endPoint, controlStart, controlEnd, quadraticStart, quadraticEnd, quadraticControl)

    drawBezier()

    def drawBezier(): Unit =
    { val dragCircles = bezierPoints.map(dc => Circle.d(circleRadius, dc.loc).fill(dc.color))

      /** line between the start point and its control point */
      val startControlLine = LineSegDraw(startPoint.loc, controlStart.loc, 1, Grey)

      /** line between the end point and its control point */
      val endControlLine = LineSegDraw(endPoint.loc, controlEnd.loc, 1, Grey)

      /** the bezier to be displayed */
      val bezier = Bezier(startPoint.loc, controlStart.loc, controlEnd.loc, endPoint.loc).draw(2, Green)

      /** this holds the syntax required to draw the current bezier  (NB: replace ; with , ) */
      val txt =
      { val text1 = "BezierDraw(" + startPoint.loc + ", " + controlStart.loc + ", " + controlEnd.loc + ", " + endPoint.loc + ", 2, Green)"
        TextFixed.xy(text1, 18, 0, 300, Green)
      }

      val elementsToPaint = dragCircles ++ RArr(txt, startControlLine, endControlLine, bezier)

      val quadraticBezier = Bezier(quadraticStart.loc, quadraticControl.loc, quadraticControl.loc, quadraticEnd.loc).draw(2, Blue)
      val quadraticDragCircles = quadraticBezierPoints.map(dc => Circle.d(circleRadius, dc.loc).fill(dc.color))
      val quadraticStartControlLine = LineSegDraw(quadraticStart.loc, quadraticControl.loc, 1, Grey)
      val quadraticEndControlLine = LineSegDraw(quadraticEnd.loc, quadraticControl.loc, 1, Grey)

      val txtQuad =
      { val text = "BezierDraw(" + quadraticStart.loc + ", " + quadraticControl.loc + ", " + quadraticControl.loc + ", " + quadraticEnd.loc + ", 2, Blue)"
        TextFixed.xy(text, 18, 0, -300, Blue)
      }

      repaint(elementsToPaint +% txtQuad ++ quadraticDragCircles +% quadraticBezier +% quadraticStartControlLine +% quadraticEndControlLine)
    }

    /* test to see if drag operation has started. if the mouseDown is on one of the represented bezier points then set theDragee to its corresponding
   option */
    canv.mouseDown = (position, _) => theDragee = allBezierPoints.find(i => i.loc.distTo(position) <= circleRadius)

    // When a point is being dragged update the corresponding bezier point with its new position and then redraw the screen.
    canv.mouseDragged = (position, button) => theDragee match
    {
      case Some(drag) if (drag == startPoint) => drag.loc = position; controlStart.loc = drag.loc + controlStartOffset; drawBezier()
      case Some(drag) if (drag == endPoint) => drag.loc = position; controlEnd.loc = drag.loc + controlEndOffset; drawBezier()
      case Some(drag) if (drag == controlStart) => drag.loc = position; controlStartOffset = drag.loc << startPoint.loc; drawBezier()
      case Some(drag) if (drag == controlEnd) => drag.loc = position; controlEndOffset = drag.loc << endPoint.loc; drawBezier()
      case Some(drag) => drag.loc = position; drawBezier()
      case _ => theDragee = None
    }

    /** dragging has finished so reset theDragee */
    mouseUp = (button, clickList, position) => theDragee = None
  }
}


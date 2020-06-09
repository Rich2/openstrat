case class LessonC7(canv: CanvasPlatform) extends CanvasNoPanels("Lesson C7: Exploring Beziers")
{ var theDragee = " "
  val circleRadius = 5
  var bp:Map[String, Vec2] = Map("p1" -> Vec2(-50, 0), "p2" -> Vec2(50, 0), "c1" -> Vec2(-50, -125), "c2" -> Vec2(50, 25))
  
  drawBezier()

  def drawBezier():Unit = 
  { val pt1 = Circle(circleRadius, bp("p1")).fill(Red)
    val pt2 = Circle(circleRadius, bp("p2")).fill(Red)
    val cp1 = Circle(circleRadius, bp("c1")).fill(Gray)
    val cp2 = Circle(circleRadius, bp("c2")).fill(Grey)
    val cl1 = LineDraw(bp("p1"), bp("c1"), 1, Grey)
    val cl2 = LineDraw(bp("p2"), bp("c2"), 1, Grey)
    val bez = BezierDraw(bp("p1"), bp("c1"), bp("c2"), bp("p2"), 2, Green)
    val txt = TextGraphic("BezierDraw(" + bp("p1") + ", " + bp("c1") + ", " + bp("c2") + ", " + bp("p2") + ", 2, Green)", 18, 0 vv 300)
    val repainter = Arr(txt, pt1, pt2, cp1, cp2, cl1, cl2, bez)
    repaint(repainter)
  }

  def dragging(newPosition:Vec2):Unit =
  { bp(theDragee) = newPosition
    drawBezier()
  }

  canv.mouseDown = (position, button) => 
  { if ((bp("c1") - position).magnitude <= circleRadius) theDragee = "c1"
    else if ((bp("c2") - position).magnitude <= circleRadius) theDragee = "c2"
    else if ((bp("p1") - position).magnitude <= circleRadius) theDragee = "p1"
    else if ((bp("p2") - position).magnitude <= circleRadius) theDragee = "p2"
    else theDragee = ""
    if (theDragee != "") dragging(position)
  }

  canv.mouseDragged = (position, button) => if (theDragee != "") dragging(position)

  mouseUp = (button, clickList, position) => 
  { dragging(position)    
    theDragee = ""
  }
}

package learn
import ostrat._, geom._, pCanv._, Colour._

case class LessonA11(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A11")
{
  val pointOnLineA = Vec2(-300,0)
  val pointOnLineB = Vec2(300,400)
  val lineOfReflection = Arr(LineDraw(pointOnLineA, pointOnLineB, 0.5, Red))
  val pointToReflect = -100 vv 200
  val reflectedPoint = pointToReflect.reflect(pointOnLineA, pointOnLineB)
  val crossAtPointToReflect = Cross(1, pointToReflect)
  val crossAtReflectedPoint = Cross(1, reflectedPoint)

  repaint(crossAtPointToReflect ++ lineOfReflection ++ crossAtReflectedPoint)
}


 
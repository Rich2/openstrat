package learn
import ostrat._, geom._, pCanv._, Colour._

case class LessonA11(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A11: Reflecting a point across a Line")
{
  val line1 = Line2(-300, 0, 300, 400)
  val redLine = line1.draw(0.25, Red)
  val p1 = -100 vv 200

  val c1 = Cross(1, p1)
  val c1r = Cross(1, p1.mirror(line1))
  val rect = Rectangle(200, 100, 200 vv 200)
  val r1 = rect.fill(Red)
  val r1r = rect.mirror(line1).fill(Orange)
  val cl1 = CircleOld(75, 0 vv -50)
  val ccl1 = cl1.fill(Red)
  val ccl1r = cl1.mirror(line1).fill(Orange)

  val r2 = Rectangle(180, 100, 150 vv -200)
  val cl2 = CircleOld(80, 110 vv - 300)
 // val sq = Square(100, 110 vv -400)

  val cn = Circle(80, 110, -290)
  val cnf = CircleFill(cn, Violet)
  val cn1 = cn.slate(20 vv 50)
  val na: Arr[GeomElem] = Arr(cl2, cn)
 // val na: Arr[Product with java.io.Serializable with GeomElem] = Arr(cl2, cn)

  val na1 = na.slate(20, 20)

  val ca = Arr(cn, cn)

  val na2 = na1.slate(20, 20)


  val a1 = Arr(r2, cl2)
 // val a2 = a1.mirrorY
  //val cc1 = a1.map(_.fi)

  val aa = Arr(ccl1, ccl1r, r1, r1r)
  repaint(aa ++ c1 ++ c1r +- redLine)
}
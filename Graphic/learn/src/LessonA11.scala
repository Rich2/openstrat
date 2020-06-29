/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pCanv._, Colour._

case class LessonA11(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A11: Reflecting a point across a Line")
{
  val line1 = LineSeg(-300, 0, 300, 400)
  val redLine = line1.draw(0.25, Red)
  val p1 = -100 vv 200

  val c1 = Cross(1, p1)
  val c1r = Cross(1, p1.mirror(line1))
  val rect = Rectangle(200, 100, 200 vv 200)
  val r1 = rect.fill(Red)
  val r1r = rect.reflect(line1).fill(Orange)
  val vRed = 0 vv -50
  val cl1 = Circle(75, vRed)
  val ccl1 = cl1.fill(Red)
  val rStr = TextGraphic(ccl1.svgStr, 20, vRed)
  val ccl1r = cl1.reflect(line1).fill(Orange)

  val r2 = Rectangle(180, 100, 150 vv -200)
  val cl2 = Circle(80, 110 vv - 300)
  val sq = Sqlign(100, 110, -400)

  val cn = Circle(80, 110, -290)
  val v1 = Vec2(0, -cl1.diameter)
  val cd1 = CircleDraw(cl1.slate(v1), 2, Green)
  val cd2 = CircleDraw(cl1.slate(v1.rotate(45.degs)), 2, Gold)
  val rStr2 = TextGraphic(cd1.svgStr, 20, cd1.cen)
  val cd3 = CircleDraw(cl1.slate(v1.rotate(-45.degs)), 2, Aquamarine)
  
  val cnf = CircleFill(cn, Violet)
  val cn1 = cn.slate(20 vv 50)
  //val na: Arr[GeomElem] = Arr(cl2, cn)
  val na = Arr(cl2, cn)

  val na1 = na.slate(20, 20)

  val ca: Arr[TransElem] = Arr(sq, cn)
  val ca2 = ca.slate(20, 20)
  
  val la = List(sq, cn)
 // val la2 = la.slate(20, 20)

  val na2 = na1.scale(20)
  
  val cab = cn
  val cc = Arr(cn, cab)
  val cc1 = cc.slate(2, 3)

  val a1 = Arr(r2, cl2)
  
  val e1 = EllipseClass(0, 0, 0,0 ,0 , 0)
  val ee = Arr(e1, cn)
  //val ee1 = ee.slate(0, 0)
 // val a2 = a1.mirrorY
  //val cc1 = a1.map(_.fi)

  val aa = Arr(ccl1, ccl1r, r1, r1r)
  repaint(aa ++ c1 ++ c1r +- cd1 +- cd2 +- cd3 +- rStr +- rStr2 +- redLine)
}
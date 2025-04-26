/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, geom.*, Colour.*

/** Reflection transformation lesson. */
object LsAReflect extends LessonStatic
{
  override def title: String = "Reflection"

  override def bodyStr: String = "Reflection transformation"

  val line1 = LSeg2(-300, 0, 300, 400)
  val redLine = line1.draw(0.25, Red)
  val p1 = Pt2(-100, 200)

  val c1 = Cross.draw(p1)
  val c1r = Cross.draw(p1.reflect(line1))
  val rect = Rect(200, 100, 200, 200)
  val r1 = rect.fill(Red)
  val r1r = rect.reflect(line1).fill(Orange)
  val vRed = Pt2(0, -50)
  val cl1: Circle = Circle.d(75, vRed)
  val ccl1 = cl1.fill(Red)
  val ccl1r = cl1.reflect(line1).fill(Orange)

  val r2 = Rect(180, 100, 150, -200)
  val cl2 = Circle(40, 110, - 300)
  val sq = Sqlign(100, 110, -400)

  val cn = Circle(40, 110, -290)
  val v1 = Pt2(0, -cl1.diameter)
  val cd1 = CircleDraw(cl1.slate(v1), 2, Green)
  val cd2 = CircleDraw(cl1.slate(v1.rotate(45.degsVec)), 2, Gold)

  val cd3 = CircleDraw(cl1.slate(v1.rotate(-45.degsVec)), 2, Aquamarine)
  
  val cnf = CircleFill(cn, Violet)
  val cn1 = cn.slate(20, 50)
  val na = RArr(cl2, cn)

  val na1 = na.slate(20, 20)

  val ca: RArr[Aff2Elem] = RArr(sq, cn)
  val ca2 = ca.slate(20, 20)
  
  val la = List(sq, cn)

  val na2 = na1.scale(20)
  
  val cab = cn
  val cc = RArr(cn, cab)
  val cc1 = cc.slate(2, 3)

  val a1 = RArr(r2, cl2)
  
  val e1 = Ellipse(50, 30)
  val ee = RArr(e1, cn)

  val aa = RArr(ccl1, ccl1r, r1, r1r)

  override def output: GraphicElems = aa +% c1 +% c1r +% cd1 +% cd2 +% cd3 +% redLine
}
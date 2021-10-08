/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pCanv._, Colour._

/** Lesson D4 Settings. */
case class LsD4(canv: CanvasPlatform) extends CanvasNoPanels("Lesson D4")
{  
  val v1 = Pt2(2.3, -9.8)
  val t4 = v1.str
  
  val v2: Pt2 = 4.6 pp 78.4
  
  val v3 = v1.addX(50)
  val v4 = v1.subX(300)
  val v5 = 4.4 pp 5.5
  val v6 = v5.addY(100)
  //So in this longer example, the semicolons and commas become more useful. You can't do this with toString
  val t1 = PolygonGen(v1, v2, v3, v4, v5, v6)
  val t2 = LinePath(v1, v2, v3, v4, v5, v6)
  val c1 = Colour.Azure  
  val t3 = t1.fill(c1)
  val s3 = "0xFFFFFFFF";
  val c3 = s3.parseTokens
  val c4 = s3.parseStatements
  val c5 = s3.findType[Colour]
  val tl1 = Rval(t1) - t2 - c1
  
  repaint(SText(200, tl1.str) +: MText(0, Strings(c1.toString, c3.toString, c4.toString, c5.toString)))
}
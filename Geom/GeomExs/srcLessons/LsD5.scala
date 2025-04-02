/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat.*, geom.*, pgui.*

object LsD5 extends LessonGraphics
{ override def title: String = "RSON Lesson 5"

  override def bodyStr: String = """Lesson C3. Pointer in object."""

  override def canv: CanvasPlatform => Any = LsD5Canv(_)

  /** Lesson D4 Settings. */
  case class LsD5Canv(canv: CanvasPlatform) extends CanvasNoPanels("Lesson D5") {
    val s1 = IntArr(10, 9, 8, 7)
    val s2 = Pt2Arr.dbls(4,3, 2.1,0.7, 500,-100, 0,0)

    //val ss = Sett("Arr", s1).ap("Ls", s2).str
    //val c1 = ss.findIntArray

    //val cc = TextGraphic.lines(Arr(c1).map(_.toString), lineSpacing = 1.5, posn = -250 vv -150, align = LeftAlign)
    //repaintOld(SText(200, ss) +: cc)
  }
}
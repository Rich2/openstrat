/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pgui._

object LsD5 extends LessonGraphics
{ override def title: String = "Pointer targeting Lesson"

  override def bodyStr: String = """Lesson C3. Pointer in object."""

  override def canv: CanvasPlatform => Any = LsD5Canv(_)

  /** Lesson D4 Settings. */
  case class LsD5Canv(canv: CanvasPlatform) extends CanvasNoPanels("Lesson D5") {
    val s1 = IntArr(10, 9, 8, 7)
    val s2 = Pt2Arr(4 pp 3, 2.1 pp 0.7, 500 pp -100, Pt2Z)

    //val ss = Sett("Arr", s1).ap("Ls", s2).str
    //val c1 = ss.findIntArray

    //val cc = TextGraphic.lines(Arr(c1).map(_.toString), lineSpacing = 1.5, posn = -250 vv -150, align = LeftAlign)


    //repaintOld(SText(200, ss) +: cc)
  }
}
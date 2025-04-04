/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, geom.*, pgui.*

object LsD3 extends LessonGraphics
{ override def title: String = "RSON Lesson 3"
  override def bodyStr: String = """Lesson C3. Pointer in object."""
  override def canv: CanvasPlatform => Any = LsD3Canv(_)
  
  /** D Series lessons deal with persistence */
  case class LsD3Canv(canv: CanvasPlatform) extends CanvasNoPanels("Lesson D3")
  { val r1 = Rval(5) - 2.1 - false - "Hello World!" - Pt2(2.3, -43.8) - Array(4, 5, 6)
    val topBlock: TextFixed = SText(300, r1.str)

    val s2 = Setting("Age", 5).ap("Average", -2.1).ap("Open", false).ap("Greeting", "Hello World!").ap("Posn", Pt2(2.3, -43.8)).str
    val middleBlock: TextFixed = SText(100, s2)

    val c0 = s2.findType[Boolean]
    val c1 = s2.findSetting[Boolean]("Open")

    /** Just a convenience method for the general one above. */
    val c2 = s2.findBoolSetting("Guilty")

    val c3 = s2.findIntSetting("Posn")

    /** Again as [[Pt2]] is such a commonly used type, special methods have been created for convenience. */
    val c4 = s2.findSettingPt2("Posn")

    val c5 = s2.findSettingPt2Else("MyPosn", Pt2(45, 1.2))

    /** Gives the result from the string, but has guard if setting not found. */
    val c6 = s2.findSettingPt2Else("Posn", Pt2(45, 1.2))

    val bottomBlock: RArr[TextFixed] =
      TextFixed.lines(RArr(c0, c1, c2, c3, c4, c5, c6).map(_.toString), lineSpacing = 1.5, posn = Pt2(-250, -150), align = LeftAlign)

    repaint(topBlock %: middleBlock %: bottomBlock)
  }
}
/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pgui._

/** D Series lessons deal with persistence */
case class LsD3(canv: CanvasPlatform) extends CanvasNoPanels("Lesson D3")
{
  val r1 = Rval(5) - 2.1 - false - "Hello World!" - (2.3 pp -43.8) - Array(4, 5, 6)
  val topBlock = SText(300 , r1.str)
  
  val s2 = Setting("Age", 5).ap("Average", -2.1).ap("Open", false).ap("Greeting", "Hello World!"). ap("Posn", 2.3 pp -43.8).str
  val middleBlock = SText(100, s2)
  
  val c0 = s2.findType[Boolean]
  val c1 = s2.findSettingT[Boolean]("Open")

  /** Just a convenince method for the general one above. */
  val c2 = s2.findSettingBool("Guilty")

  val c3 = s2.findSettingInt("Posn")

  /** Again as [[Pt2]] is such a commonly used type, special methods have been created for convenience. */
  val c4 = s2.findSettingPt2("Posn")

  val c5 = s2.findSettingPt2Else("MyPosn", 45 pp 1.2)

  /** Gives the result from the string, but has guard if setting not found. */
  val c6 = s2.findSettingPt2Else("Posn", 45 pp 1.2)
  
  val bottomBlock = TextGraphic.lines(Arr(c0, c1, c2, c3, c4, c5, c6).map(_.toString), lineSpacing = 1.5, posn = -250 pp -150, align = LeftAlign)
   
  repaint(topBlock %: middleBlock %: bottomBlock)
}
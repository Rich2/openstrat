/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pCanv._, Colour._

/** This lesson is working, but has what looks like a very dubious implementation. */
case class LsC4(canv: CanvasPlatform) extends CanvasNoPanels("Lesson C4")
{ val r0 = Rect(200, 100)
  val r1 = r0.slateXY(-300,300)
  val r2 = r0.slateXY(300, 300)
  val r3 = r0.slateXY(300, -300)
  val r4 = r0.slateXY(-300, -300)
  val rArr = Arr(r1, r2, r3, r4)
  var colour = Red
  def gArr = rArr.map(r => r.fillActive(colour, r.cenPt))
  val textPosn = 0 pp 0
  val startText = TextGraphic("Click on the rectangles to cycle the colour.", 28, textPosn)
  deb((gArr +- startText).elemsNum.toString)
  repaint(gArr +- startText)
  
  /** Note you can use what names you like. Here I put the types explicitly for clarity. When you are familiar with an anonymous function, you will
   *  probably want to use a short parameter list like (v, b, s).  */
  mouseUp = (button: MouseButton, selected: Arr[Any], posn: Pt2) => selected match
  {
    case ArrHead(cen: Pt2/*, tail*/) =>
    { colour = colour.nextFrom(Colours(Red, Orange, Green))
      repaint(gArr +- startText)
    }
      
    case _ =>  deb("Missed")
  }
}
/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pgui._

/** D Series lessons deal with persistence */
case class LsD1(canv: CanvasPlatform) extends CanvasNoPanels("Lesson D1")
{  
  val t1 = 5.str  
  val t2 = 2.2.str  
  val t3 = true.str//OK you're probably not noticing much advantage over toString yet except its shorter
  
  val v1 = Pt2(2.3, -9.8)
  val t4 = v1.str
  
  val v2: Pt2 = 4.6 pp 78.4
  val l1 = LineSeg(v1, v2)
  val t5 = l1.str//So note how there is a semicolon between the two points but a comma between the x and y values of each point. 
  
  val topStrs = Strings(t1, t2, t3, t4, t5)
  val topBlock = MText(200, topStrs)//So note we've created a couple of useful Function objects below for use in this lesson series.  
  
  repaint(topBlock)
}

object SText
{ def apply(y: Double, str: String)  = TextGraphic(str, 24, -250 pp y, align = LeftAlign)
}

object MText
{ def apply(y: Double, strs: Strings): Arr[TextGraphic]  = TextGraphic.lines(strs, lineSpacing = 1.5, posn = -250 pp y, align = LeftAlign)
}
/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, geom.*, pgui.*

object SText
{ def apply(y: Double, str: String) = TextFixed.xy(str, 24, -250, y, align = LeftAlign)
}

object MText
{ def apply(y: Double, strs: StrArr): RArr[TextFixed] = TextFixed.lines(strs, lineSpacing = 1.5, posn = Pt2(-250, y), align = LeftAlign)
}

object LsRson1 extends LessonGraphics
{ override def title: String = "RSON Lesson 1"
  override def bodyStr: String = """RSon Lesson 1."""
  override def canv: CanvasPlatform => Any = LsD1Canv(_)

  /** D Series lessons deal with persistence */
  case class LsD1Canv(canv: CanvasPlatform) extends CanvasNoPanels("Lesson D1")
  { val t1 = 5.str
    val t2 = 2.2.str

    /** OK you're probably not noticing much advantage over toString yet except its shorter. */
    val t3 = true.str

    val v1 = Pt2(2.3, -9.8)
    val t4 = v1.str

    val v2: Pt2 = Pt2(4.6, 78.4)
    val l1 = LSeg(v1, v2)

    /** So note how there is a semicolon between the two points but a comma between the x and y values of each point. */
    val t5 = l1.str

    val topStrs = StrArr(t1, t2, t3, t4, t5)
    val topBlock = MText(200, topStrs) //So note we've created a couple of useful Function objects below for use in this lesson series.

    repaint(topBlock)
  }
}
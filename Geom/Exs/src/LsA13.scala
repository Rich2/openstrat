/* Copyright Richard Oliver 2018-22 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pgui._, Colour._

/** Lesson A 13 Empty. */
case class LsA13(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A13")
{  
  val rx = 6
  val ry = 4
  val scale = 100
  val sq = Sqlign(2)
  val sq2 = Square(1, 45.degs)
  val s1 = ijToMap(-rx, rx, 2)(-ry, ry, 2){(x, y) =>
    val col = ife((x/2).isEven & (y/2).isEven | (x/2).isOdd & (y/2).isOdd, Red, Orange)
    sq.slateXY(x, y).fill(col)
  }
  val s2 = ijToMap(-rx + 1, rx - 1, 2)(-ry + 1, ry -1, 2){(x, y) =>

    sq2.slateXY(x * 1, y * 1).fill(Green)
  }
  val stuff = (s1 ++ s2).scale(scale)
  repaint(stuff)
}
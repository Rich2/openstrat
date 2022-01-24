/* Copyright Richard Oliver 2018-22 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pgui._, Colour._

/** Lesson A 13 Empty. */
case class LsA13(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A13")
{  
  val rx = 6
  val ry = 4
  val scale = 85
  val sq = Sqlign(2)
  val sq2 = Square(0.6, 45.degs)
  val s1 = ijToMap(-rx, rx, 2)(-ry, ry, 2){(x, y) =>
    val col = ife((x/2).isEven & (y/2).isEven | (x/2).isOdd & (y/2).isOdd, Red, Orange)
    sq.slateXY(x, y).fill(col)
  }
  val s2 = ijToMap(-rx - 1, rx + 1, 2)(-ry - 1, ry + 1, 2){(x, y) =>
    val sq3 = None match
    { case _ if x == - rx - 1 & y == ry + 1 => Polygon(sq2.v1, sq2.v2, sq2.cenPt)
      case _ if x == rx + 1 & y == ry + 1 => Polygon(sq2.v3, sq2.v2, sq2.cenPt)
      case _ if x == - rx - 1 & y == -ry - 1 => Polygon(sq2.v0, sq2.v1, sq2.cenPt)
      case _ if x == rx + 1 & y == -ry - 1 => Polygon(sq2.v3, sq2.v0, sq2.cenPt)
      case _ if x == rx + 1 => sq2.dropVert(1)
      case _ if x == - rx - 1 => sq2.dropVert(3)
      case _ if y == ry + 1 => sq2.dropVert(0)
      case _ if y == - ry - 1 => sq2.dropVert(2)
      case y => sq2
    }

    sq3.slateXY(x, y).fill(Green)
  }

  val s2t = iToMap(-rx + 1, rx - 1, 2){x =>
    sq2.slateXY(x, ry + 1).fill(Green)
  }
  val stuff = (s1 ++ s2 ).scale(scale)
  repaint(stuff)
}